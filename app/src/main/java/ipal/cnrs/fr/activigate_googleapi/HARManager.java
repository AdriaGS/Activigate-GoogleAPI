package ipal.cnrs.fr.activigate_googleapi;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

/**
 * Created by adria on 15/3/18.
 */

public class HARManager {

    public static ArrayList<String> json2Send = new ArrayList<>();
    public static ArrayList<String> sensingRecord = new ArrayList<>();

    static Boolean isSensing = false;
    Context mContext;



    public HARManager() {

    }

    public boolean getSensingStatus(){
        return isSensing;
    }

    public void changeSensingStatus() {
        if(isSensing){
            isSensing = false;
        }
        else {
            isSensing = true;
        }
    }

    public String getLastSensedValue() {
        return sensingRecord.get(sensingRecord.size() - 1);
    }

    public ArrayList<String> getJson2Send() {
        return json2Send;
    }

}
