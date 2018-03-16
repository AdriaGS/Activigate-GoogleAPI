package ipal.cnrs.fr.activigate_googleapi.Activigate;

import android.app.Application;

/**
 * Created by adria on 16/3/18.
 */

public class MyApplication extends Application {
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}