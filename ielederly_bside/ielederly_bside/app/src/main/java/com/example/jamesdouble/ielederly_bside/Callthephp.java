package com.example.jamesdouble.ielederly_bside;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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

    public void insertToDatabase(final String text) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

               // String name = editTextName.getText().toString();
            //    String add = editTextAdd.getText().toString();
                Log.e("insert","alarm="+text);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("alarm", text));


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

                Log.e("insert",result);   //結果
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(text);
    }
}
