package ipal.cnrs.fr.activigate_googleapi.HAR;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by adriagil on 6/3/18.
 */

public class HttpAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {

        return  UploadHAR.upload(urls[0], urls[1]);
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        Log.d("Data sent", "Data was Sent! " + result);
        if(!result.equals("Null")){
            HARUtils.json2Send.remove(HARUtils.json2Send.size() - 1);
        }
    }
}
