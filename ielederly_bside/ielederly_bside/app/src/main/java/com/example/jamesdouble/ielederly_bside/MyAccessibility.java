package com.example.jamesdouble.ielederly_bside;

import android.accessibilityservice.AccessibilityService;  
import android.accessibilityservice.AccessibilityServiceInfo;  
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;  
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;
import android.os.Handler;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("NewApi")  
public class MyAccessibility extends AccessibilityService {  
    private static final String TAG = "MyAccessibility";  
    String[] PACKAGES = { "com.android.settings" };
    Handler mHandler;

    int keepactive;
    String inputstr = "";
    static int emotion =0;
    static boolean smssendyet;
    // 建立資料庫物件
    ItemDAO itemDAO;
    checktextDAO checkDAO;

    // 如果資料庫是空的，就建立一些範例資料
    // 這是為了方便測試用的，完成應用程式以後可以拿掉


    public void insertSQL(String text)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = new Date();
        String dts=sdf.format(now);
        Item insertitem = new Item(dts,text);
        insertitem = itemDAO.insert(insertitem);
        localcheck(text);    //檢查字串
    }

    public void localcheck(String check)
    {
        int get = care_text.checking(check,checkDAO);
        Log.e("emotion","emotion = "+String.valueOf(get));
        emotion += get;
        if(emotion > 19)
        {
            Log.e("emotion","DANGER");
            if(!smssendyet) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("+886937071410",
                        null, "Please contact your parents immediately",
                        PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(), 0),
                        null);
                smssendyet = true;
            }

            alarmphp insert = new alarmphp();  //通知小孩
            insert.insertToDatabase("1");
            emotion =0;
        }
        else if(emotion<-30)
        {
            Log.e("emotion","HAPPY");
            if(!smssendyet) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("+886937071410",
                        null, "You parents have a good mood",
                        PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(), 0),
                        null);
                smssendyet = true;
            }
            emotion =0;
        }
        else {
            Log.e("emotion", String.valueOf(emotion));
            smssendyet = false;
        }
    }

    final Runnable updateactive = new Runnable() {
        public void run() {
            // TODO Auto-generated method stub
            // 需要背景作的事
            if(keepactive>0) {
                normalactivephp insert = new normalactivephp();
                insert.insertToDatabase(String.valueOf(keepactive));
            }
            mHandler.postDelayed(updateactive, 30000);  //30秒
            keepactive =0;
        }
    };


    @Override  
    protected void onServiceConnected() {  
    	Log.i(TAG, "config success!");  
        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();  
        // accessibilityServiceInfo.packageNames = PACKAGES;  
        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;  
        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;  
        accessibilityServiceInfo.notificationTimeout = 1000;  
        setServiceInfo(accessibilityServiceInfo);


        mHandler = new Handler();  //update使用情況
        mHandler.post(updateactive);
    }  

    @Override  
    public void onAccessibilityEvent(AccessibilityEvent event) {  
        // TODO Auto-generated method stub  
        int eventType = event.getEventType();  
        String eventText = "";


       Log.e(TAG, "==============Start====================");
        switch (eventType) {  
        case AccessibilityEvent.TYPE_VIEW_CLICKED:            //點擊的東西
            itemDAO = new ItemDAO(this);
            checkDAO = new checktextDAO(this);
            eventText = "TYPE_VIEW_CLICKED";
            inputstr = event.getText().toString();
            Log.e(TAG, event.getText().toString());
            insertSQL(inputstr);
            itemDAO.close();
            checkDAO.close();
            break;  
        case AccessibilityEvent.TYPE_VIEW_FOCUSED:             //Edittext原本字串
            eventText = "TYPE_VIEW_FOCUSED";
            Log.e(TAG, event.getText().toString());
            break;  
        case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:  
            eventText = "TYPE_VIEW_LONG_CLICKED";  
            break;  
        case AccessibilityEvent.TYPE_VIEW_SELECTED:  
            eventText = "TYPE_VIEW_SELECTED";
            Log.e(TAG, event.getText().toString());
            break;  
        case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
            itemDAO = new ItemDAO(this);
            checkDAO = new checktextDAO(this);
            eventText = "TYPE_VIEW_TEXT_CHANGED!!!!!!!!!!!!!!!!!!!";  ///when you change the  text in the  texting block
            batteryLevel();//get  your battery info
            inputstr = event.getText().toString();
            insertSQL(inputstr);
            itemDAO.close();
            checkDAO.close();
            break;  
        case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:  
            eventText = "TYPE_WINDOW_STATE_CHANGED";  
            break;  
        case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:  
            eventText = "TYPE_NOTIFICATION_STATE_CHANGED";  
            break;  
        case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:  
            eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_END";  
            break;  
        case AccessibilityEvent.TYPE_ANNOUNCEMENT:  
            eventText = "TYPE_ANNOUNCEMENT";  
            break;  
        case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:  
            eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_START";  
            break;  
        case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:  
            eventText = "TYPE_VIEW_HOVER_ENTER";  
            break;  
        case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:  
            eventText = "TYPE_VIEW_HOVER_EXIT";  
            break;  
        case AccessibilityEvent.TYPE_VIEW_SCROLLED:  
            eventText = "TYPE_VIEW_SCROLLED";  
            break;  
        case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:  
            eventText = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
            Log.e(TAG, event.getText().toString());
            itemDAO = new ItemDAO(this);
            checkDAO = new checktextDAO(this);
            inputstr = event.getText().toString();
            insertSQL(inputstr);
            itemDAO.close();
            checkDAO.close();
            break;  
        case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:  
            eventText = "TYPE_WINDOW_CONTENT_CHANGED";  
            break;  
            

        }

        keepactive++;  //有在用手機

        Log.e(TAG, eventText);
        Log.e(TAG, "=============END=====================");

    }  
  
    @Override  
    public void onInterrupt() {  
        // TODO Auto-generated method stub  
  
    }  
    //==================
    private void batteryLevel() {
        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				context.unregisterReceiver(this);
                int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int level = -1;
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                
                Log.e(TAG,"Battery Level Remaining: " + level + "%");
			}
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);
    }



}


