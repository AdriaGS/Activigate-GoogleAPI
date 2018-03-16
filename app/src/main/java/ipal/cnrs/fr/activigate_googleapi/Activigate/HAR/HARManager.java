package ipal.cnrs.fr.activigate_googleapi.Activigate.HAR;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.tasks.Task;

/**
 * Created by adria on 16/3/18.
 */

public class HARManager {

    // Stores the PendingIntent used to send activity recognition events back to the app
    private PendingIntent mActivityRecognitionPendingIntent = null;
    // Stores the current instantiation of the activity recognition client
    private ActivityRecognitionClient mActivityRecognitionClient;

    public void init(Context context, int interval){

        HARUtils.DETECTION_INTERVAL_SECONDS = interval;

        Intent intent = new Intent( context, HARService.class );
        mActivityRecognitionPendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mActivityRecognitionClient = new ActivityRecognitionClient(context);

    }

    public void start(Context context) {
        if (!servicesConnected(context)) {
            return;
        }
        // Pass the update request to the requester object
        Task task = mActivityRecognitionClient.requestActivityUpdates(HARUtils.DETECTION_INTERVAL_MILLISECONDS, mActivityRecognitionPendingIntent);
    }

    public void stop(Context context) {
        if (!servicesConnected(context)) {
            return;
        }
        // Pass the remove request to the remover object
        Task task = mActivityRecognitionClient.removeActivityUpdates(mActivityRecognitionPendingIntent);
    }

    private boolean servicesConnected(Context context) {
        // Check that Google Play services is available
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(context);
        if (result != ConnectionResult.SUCCESS) {
            return false;
        } else {
            return true;
        }
    }

}
