package ipal.cnrs.fr.activigate_googleapi.HAR;

import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adria on 16/3/18.
 */

public class Result {

    private static int activity = 4;
    private static int GGMostProbableActivity = 4;
    private static int GGMostProbableActivityConfidence = 0;
    private static List<DetectedActivity> GGProbableActivities = new ArrayList<>();
    private static long lastUpdateTime = 0;


    public static int getActivity() {
        return activity;
    }

    public static void setActivity(int a) {
        activity = a;
    }

    public static int getGGMostProbableActivity() {
        return GGMostProbableActivity;
    }

    public static void setGGMostProbableActivity(int a) {
        GGMostProbableActivity = a;
    }

    public static List<DetectedActivity> getGGProbableActivities() {
        return GGProbableActivities;
    }

    public static void setGGProbableActivities(
            List<DetectedActivity> aList) {
        GGProbableActivities = aList;
    }

    public static long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public static void setLastUpdateTime(long t) {
        lastUpdateTime = t;
    }

    public static int getGGMostProbableActivityConfidence() {
        return GGMostProbableActivityConfidence;
    }

    public static void setGGMostProbableActivityConfidence(
            int c) {
        GGMostProbableActivityConfidence = c;
    }

    /**
     * Map detected activity types to strings
     *
     * @param activityType The detected activity type
     * @return A user-readable name for the type
     */
    public static String getNameFromType(int activityType) {
        switch(activityType) {
            case DetectedActivity.IN_VEHICLE:
                return "InVehicle";
            case DetectedActivity.ON_BICYCLE:
                return "OnBicycle";
            case DetectedActivity.ON_FOOT:
                return "OnFoot";
            case DetectedActivity.RUNNING:
                return "Running";
            case DetectedActivity.WALKING:
                return "Walking";
            case DetectedActivity.STILL:
                return "Still";
            case DetectedActivity.UNKNOWN:
                return "Unknown";
            case DetectedActivity.TILTING:
                return "Tilting";
        }
        return "unknown";
    }

}

