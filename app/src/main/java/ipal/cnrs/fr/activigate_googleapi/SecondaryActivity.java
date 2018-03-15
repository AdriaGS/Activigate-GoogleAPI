package ipal.cnrs.fr.activigate_googleapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondaryActivity extends AppCompatActivity {

    BroadcastReceiver myReceiver;

    TextView tv;
    TextView confidencdeTv;
    ImageView activityImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harsensing);

        tv = (TextView) findViewById(R.id.tv);
        confidencdeTv = (TextView) findViewById(R.id.Confidence_tv);
        activityImage = (ImageView) findViewById(R.id.Activity_iv);

        myReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra(HARService.LOCAL_BROADCAST_EXTRA);
                String[] parts = message.split("/");
                String activity = parts[0];
                String confidence = parts[1];
                String activity_resource = activity.toLowerCase() + "_activity";

                final Resources res = getResources();

                int resID = res.getIdentifier(activity_resource , "drawable", getPackageName());
                activityImage.setImageResource(resID);

                tv.setText("");
                for(int i = HARManager.sensingRecord.size() - 1; i >= Math.max(HARManager.sensingRecord.size() - 10, 0); i--) {

                    tv.setText(tv.getText() + HARManager.sensingRecord.get(i) + "\n");
                }
                confidencdeTv.setText(confidence + "%");
            }
        };

    }

    @Override
    protected void onResume(){
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, new IntentFilter(HARService.LOCAL_BROADCAST_NAME));
        tv.setText("");
        for(int i = HARManager.sensingRecord.size() - 1; i >= Math.max(HARManager.sensingRecord.size() - 10, 0); i--) {

            tv.setText(tv.getText() + HARManager.sensingRecord.get(i) + "\n");
        }
        if(HARManager.sensingRecord.size() > 1) {
            String activity_resource = HARManager.sensingRecord.get(HARManager.sensingRecord.size() - 1).toLowerCase() + "_activity";
            final Resources res = getResources();

            int resID = res.getIdentifier(activity_resource, "drawable", getPackageName());
            activityImage.setImageResource(resID);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
    }

}
