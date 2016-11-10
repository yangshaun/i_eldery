package com.example.jamesdouble.i_elederly;

import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by james double on 2016/1/26.
 */
public class Callthephp {

    static boolean DANGER;
    int lock;
    public void checkDatabase() {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

               // String name = editTextName.getText().toString();
            //    String add = editTextAdd.getText().toString();
                lock = 1;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://ielderly.azurewebsites.net/check.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

                    HttpResponse response = httpClient.execute(httpPost);
                    //////////////////////////////////////////
                    HttpEntity entity = response.getEntity();

                    String reponseBody = EntityUtils.toString(entity);
                    return reponseBody;
                } catch (ClientProtocolException e) {
                 Log.e("error","error");
                } catch (IOException e) {
                  Log.e("error","error");
            }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result == "error") {

                }
                else {
                    if (Integer.valueOf(result) == 0)  //safe
                    {
                        Log.e("insert", result);
                        DANGER = false;
                    } else   //danger
                    {
                        DANGER = true;
                        Log.e("insert", result);
                        Log.e("insert", String.valueOf(DANGER));
                    }
                    lock = 0;
                    Log.e("insert", "lock=" + String.valueOf(lock));
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }
}
