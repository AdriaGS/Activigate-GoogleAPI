package ipal.cnrs.fr.activigate_googleapi;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;


import java.util.List;

/**
 * Created by adriagil on 6/3/18.
 */

public class HARService extends IntentService {

    UploadHAR uploadActivity = new UploadHAR();
    public static String LOCAL_BROADCAST_NAME = "LOCAL_ACT_RECOGNITION";
    public static String LOCAL_BROADCAST_EXTRA = "RESULT";


    public HARService() {
        super("HARService");
    }

    public HARService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities( result.getProbableActivities(), result.getMostProbableActivity() );
            Log.d("Activity Recognized", result.getMostProbableActivity().toString());
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities, DetectedActivity mostProbableActivity) {
        StringBuilder str = new StringBuilder();
        DetectedActivity activity = mostProbableActivity;
        switch (activity.getType()) {
            case DetectedActivity.IN_VEHICLE: {
                str.append("InVehicle");
                break;
            }
            case DetectedActivity.ON_BICYCLE: {
                str.append("OnBicycle");
                break;
            }
            case DetectedActivity.ON_FOOT: {
                str.append("OnFoot");
                break;
            }
            case DetectedActivity.RUNNING: {
                str.append("Running");
                break;
            }
            case DetectedActivity.STILL: {
                str.append("Still");
                break;
            }
            case DetectedActivity.TILTING: {
                str.append("Tilting");
                break;
            }
            case DetectedActivity.WALKING: {
                str.append("Walking");
                break;
            }
            case DetectedActivity.UNKNOWN: {
                str.append("Unknown");
                break;
            }
        }

        HARManager.sensingRecord.add(str.toString());

        Intent intent = new Intent(LOCAL_BROADCAST_NAME);
        intent.putExtra(LOCAL_BROADCAST_EXTRA, str.toString());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

        uploadActivity.export2ICOST(str.toString());
    }
}
