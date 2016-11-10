package com.example.jamesdouble.ielederly_bside;

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
import org.apache.http.impl.entity.StrictContentLengthStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JamesDouble on 16/4/8.
 */
public class alarmphp {
    public void insertToDatabase(final String alarm) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                // String name = editTextName.getText().toString();
                //    String add = editTextAdd.getText().toString();
                Log.e("emotion","alarm="+ String.valueOf(alarm));
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("alarm", alarm));


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://ielderly.azurewebsites.net/alarm.php");

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

                Log.e("insert",result);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(alarm);
    }
}
