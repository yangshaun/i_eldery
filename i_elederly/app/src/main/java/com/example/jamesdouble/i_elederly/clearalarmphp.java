package com.example.jamesdouble.i_elederly;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JamesDouble on 16/4/9.
 */
public class clearalarmphp {
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
                    HttpPost httpPost = new HttpPost("http://ielderly.azurewebsites.net/clear.php");

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
                Log.e("inset",result);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }
}
