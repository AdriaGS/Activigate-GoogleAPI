package ipal.cnrs.fr.activigate_googleapi.Activigate.HAR;

import java.util.ArrayList;

/**
 * Created by adria on 16/3/18.
 */

public class HARUtils {

    public static ArrayList<String> json2Send = new ArrayList<>();
    public static ArrayList<String> sensingRecord = new ArrayList<>();

    public static Boolean isSensing = false;

    public static final int MILLISECONDS_PER_SECOND = 1000;
    public static int DETECTION_INTERVAL_SECONDS = 0;
    public static final int DETECTION_INTERVAL_MILLISECONDS =
            MILLISECONDS_PER_SECOND * DETECTION_INTERVAL_SECONDS;

    public static String getLastSensedValue() {
        return sensingRecord.get(sensingRecord.size() - 1);
    }

    public static ArrayList<String> getJson2Send() {
        return json2Send;
    }

}
