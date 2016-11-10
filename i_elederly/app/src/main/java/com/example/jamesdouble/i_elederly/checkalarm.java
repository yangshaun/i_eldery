package com.example.jamesdouble.i_elederly;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ImageView;

import java.util.Date;

/**
 * Created by JamesDouble on 16/4/8.
 */
public class checkalarm extends Service {

    private Handler handler = new Handler();
    public  boolean DANGER;
    LocalBroadcastManager broadcaster;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void setDANGER(boolean d){
        DANGER = d;
    }
    public void check() throws InterruptedException {

        Callthephp checking = new Callthephp();
        checking.checkDatabase();

        Log.e("alarm",String.valueOf(checking.DANGER));

        if(checking.DANGER) {
            Log.e("alarm","danger");
            Intent intent = new Intent("result");
            intent.putExtra("alarm", "danger");
            broadcaster.sendBroadcast(intent);
        }
    }



    @Override
    public void onCreate() {
        super.onCreate();
        handler.postDelayed(checking, 1000);
        broadcaster = LocalBroadcastManager.getInstance(this);  //通知ＵＩ
    }
    private Runnable checking = new Runnable() {
        public void run() {
            //log目前時間
            Log.e("save","check");
            try {
                check();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.postDelayed(this, 15000);
        }
    };

}
