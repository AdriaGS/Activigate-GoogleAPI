package ipal.cnrs.fr.activigate_googleapi.HAR;

import android.content.Intent;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;


import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

import com.fitbit.authentication.AuthenticationHandler;
import com.fitbit.authentication.AuthenticationManager;
import com.fitbit.authentication.AuthenticationResult;

import ipal.cnrs.fr.activigate_googleapi.R;

public class MainActivity extends AppCompatActivity implements AuthenticationHandler {

    TextView sensingTV;
    TextView activityTV;

    HARManager harManager = new HARManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning TextView to variables
        sensingTV = (TextView) findViewById(R.id.sensingTextView);
        activityTV = (TextView) findViewById(R.id.activity_tv);

        //toSensingHistoric = new Intent(this, SecondaryActivity.class);
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, new IntentFilter(HARService.LOCAL_BROADCAST_NAME));

        updateTextView();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void onSensing(View view){
        if(!HARUtils.isSensing) {
            // Initialize the Human Activity Recognizer Manager
            harManager.init(this, 0);
            // Start the Sensing
            harManager.start(this);
            //Task task = activityRecognitionClient.requestActivityUpdates(TIMER, pendingIntent);
            HARUtils.isSensing = true;
            moveTaskToBack(true);
        }
        else {
            // Stop the Sensing
            harManager.stop(this);
            //Task task = activityRecognitionClient.removeActivityUpdates(pendingIntent);
            HARUtils.isSensing = false;
        }
        updateTextView();
    }

    // Broadcast Receiver to receive last activity that was sensed
    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateTextView();
        }
    };

    public void goFitbit(View view){
        AuthenticationManager.login(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AuthenticationManager.onActivityResult(requestCode, resultCode, data, (AuthenticationHandler) this);
    }

    public void onAuthFinished(AuthenticationResult authenticationResult) {
        if (authenticationResult.isSuccessful()) {
            Log.d("Authentication", "Succesfull");
        } else {
            Log.d("Authentication", "Not Succesfull");
        }
    }

    private void updateTextView() {

        if(HARUtils.sensingRecord.size() > 0) {
            if (HARUtils.isSensing) {
                sensingTV.setText("SENSING");
                activityTV.setText("Lasted sensed activity: " + HARUtils.getLastSensedValue());
            } else {
                sensingTV.setText("NOT SENSING");
                activityTV.setText("NO ACTIVITY RECOGNIZED YET");
            }
        }
    }
}
