package ipal.cnrs.fr.activigate_googleapi.Activigate.HAR;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

/**
 * Created by adriagil on 6/3/18.
 */

public class HARService extends IntentService {

    UploadHAR uploadActivity = new UploadHAR();
    private Markov m = new Markov();
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
            // Get the update
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            // Log the update
//            his.offerElement(result);
            // Get the most probable activity from the list of activities in the update
            DetectedActivity mostProbableActivity = result.getMostProbableActivity();
            // Get the confidence percentage for the most probable activity
            int confidence = mostProbableActivity.getConfidence();
            // Get the type of activity
            int activityType = mostProbableActivity.getType();

            Result.setGGMostProbableActivity(activityType);
            Result.setGGMostProbableActivityConfidence(confidence);
            Result.setGGProbableActivities(result.getProbableActivities());
            Result.setLastUpdateTime(System.currentTimeMillis());
            /**
             * Markov process
             */
            Result.setActivity(m.getMarkovPrediction(result));
            String currentActivity = Result.getNameFromType(Result.getActivity());
            Log.d("Activity Recognized", currentActivity);

            HARUtils.sensingRecord.add(currentActivity);

            Intent broadcastIntent = new Intent(LOCAL_BROADCAST_NAME);
            broadcastIntent.putExtra(LOCAL_BROADCAST_EXTRA, currentActivity);
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);

            uploadActivity.export2ICOST(currentActivity);
        }
    }
}
