package ipal.cnrs.fr.activigate_googleapi;

import android.app.PendingIntent;
import android.content.Intent;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import java.util.ArrayList;


import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {


    BroadcastReceiver myReceiver;
    final int TIMER = 3000; // 3 sec

    TextView sensingTV;
    TextView activityTV;

    PendingIntent pendingIntent;
    ActivityRecognitionClient activityRecognitionClient;
    Intent toSensingHistoric;

    HARManager harManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning TextView to variables
        sensingTV = (TextView) findViewById(R.id.sensingTextView);
        activityTV = (TextView) findViewById(R.id.activity_tv);

        harManager = new HARManager();
        // Creating pendingIntent and activityRecognitionClient for Google API activity recognition
        Intent intent = new Intent( this, HARService.class );
        pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        activityRecognitionClient = new ActivityRecognitionClient(this);

        //toSensingHistoric = new Intent(this, SecondaryActivity.class);

        // Logo Button in charge of starting and stopping the sensing activity
        Button button = (Button) findViewById(R.id.ReadingButton);
        button.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                if(!harManager.getSensingStatus()) {
                    Task task = activityRecognitionClient.requestActivityUpdates(TIMER, pendingIntent);
                    sensingTV.setText("SENSING");
                    harManager.changeSensingStatus();
                    moveTaskToBack(true);
                }
                else {
                    Task task = activityRecognitionClient.removeActivityUpdates(pendingIntent);
                    sensingTV.setText("NOT SENSING");
                    harManager.changeSensingStatus();
                }
            }
        });

        // TODO : Historic Button
        Button toHistoricButton = (Button) findViewById(R.id.toHistoricButton);
        toHistoricButton.setVisibility(View.INVISIBLE);
        /*toHistoricButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(toSensingHistoric);
            }
        });*/

        // Broadcast Receiver to receive last activity that was sensed
        myReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra(HARService.LOCAL_BROADCAST_EXTRA);
                sensingTV.setText("SENSING");
                activityTV.setText("Last sensed activity: " + message);
            }
        };

        // verify Play Services is active and up-to-date
        checkGooglePlayServicesAvailable(this);
    }

    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, new IntentFilter(HARService.LOCAL_BROADCAST_NAME));

        if(harManager.getSensingStatus()) {
            sensingTV.setText("SENSING");
            activityTV.setText("Lasted sensed activity: " + harManager.getLastSensedValue());
        }
        else {
            sensingTV.setText("NOT SENSING");
            activityTV.setText("NO ACTIVITY RECOGNIZED YET");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    public boolean checkGooglePlayServicesAvailable(Activity activity) {
        int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(activity);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(activity, result, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }

        return true;
    }
}
