package com.example.jamesdouble.ielederly_bside;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    String result;
    Boolean whetherprocessfinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final checktextDAO checkDAO = new checktextDAO(getApplicationContext());
        Cursor cursor = checkDAO.getAll();
        final int rows_num = cursor.getCount();
        cursor.close();


        SharedPreferences runCheck = getSharedPreferences("hasRunBefore", 0); //load the preferences
        Boolean hasRun = runCheck.getBoolean("hasRun", false); //see if it's run before, default no
        if (!hasRun) {
            SharedPreferences settings = getSharedPreferences("hasRunBefore", 0);
            final SharedPreferences.Editor edit = settings.edit();
            edit.putBoolean("hasRun", true); //set to has run
            edit.commit();
            final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            Thread thread = new Thread() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            dialog.setMessage("初始化設定中.....");
                            dialog.setIndeterminate(true);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.show();
                        }
                    });
                    getNegativeWord negativeWord = new getNegativeWord();
                    result = negativeWord.executeQuery("難過 低落");
                    Log.e("#################", result+"sdsdsdsd");
                    if (result!=null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "初始化設定成功!", Toast.LENGTH_LONG).show();
                                String[] result_ary;
                                result_ary = result.split(" ");
                                if (rows_num == 0)  //初始化測資
                                {
                                    for (String e : result_ary) {
                                        Log.e("penis!!!!!", e);
                                        checkDAO.initinsert(5, e);
                                    }

                                }

                            }
                        });
                    }
                    else{
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                dialog.dismiss();
                                edit.putBoolean("hasRun", false); //set to has run
                                edit.commit();
                                Toast.makeText(MainActivity.this, "初始化設定失敗!", Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }
            };
            thread.start();
        }

        Intent i = new Intent(this, MyAccessibility.class);
        startService(i);
//        if (rows_num == 0)  //初始化測資
//        {
//            Log.e("init", "init insert");
//            //level 1 illness
//            checkDAO.initinsert(5, "痛");
//            checkDAO.initinsert(5, "酸");
//            checkDAO.initinsert(5, "麻");
//            checkDAO.initinsert(5, "症");
//            checkDAO.initinsert(5, "炎");
//            checkDAO.initinsert(5, "煩");
//            checkDAO.initinsert(5, "病");
//            checkDAO.initinsert(5, "損");
//            checkDAO.initinsert(5, "禍");
//            checkDAO.initinsert(5, "疣");
//            checkDAO.initinsert(5, "疾");
//            checkDAO.initinsert(5, "癢");
//            checkDAO.initinsert(5, "災");
//            checkDAO.initinsert(5, "抖");
//            checkDAO.initinsert(5, "害");
//            checkDAO.initinsert(5, "憂");
//            checkDAO.initinsert(5, "傷");
//            checkDAO.initinsert(5, "散光");
//            checkDAO.initinsert(5, "扭傷");
//            checkDAO.initinsert(5, "失去");
//            checkDAO.initinsert(5, "泌尿");
//            checkDAO.initinsert(5, "瘀青");
//            checkDAO.initinsert(5, "徵兆");
//            checkDAO.initinsert(5, "傷寒");
//            checkDAO.initinsert(5, "痴呆");
//            checkDAO.initinsert(5, "近視");
//            checkDAO.initinsert(5, "無聊");
//            checkDAO.initinsert(5, "咳嗽");
//            checkDAO.initinsert(5, "氣喘");
//            checkDAO.initinsert(5, "失眠");
//            checkDAO.initinsert(5, "挫折");
//            checkDAO.initinsert(5, "過敏");
//            checkDAO.initinsert(5, "嘔吐");
//            checkDAO.initinsert(5, "骨折");
//            checkDAO.initinsert(5, "頭痛");
//            checkDAO.initinsert(10, "癲癇");
//            checkDAO.initinsert(5, "傷風");
//            checkDAO.initinsert(5, "水痘");
//            checkDAO.initinsert(5, "疲勞");
//            checkDAO.initinsert(5, "陽痿");
//            checkDAO.initinsert(5, "恐慌");
//            checkDAO.initinsert(5, "自虐");
//            checkDAO.initinsert(5, "嗜睡");
//            checkDAO.initinsert(5, "早洩");
//            checkDAO.initinsert(5, "耳鳴");
//            checkDAO.initinsert(5, "噁心");
//            checkDAO.initinsert(5, "發燒");
//            checkDAO.initinsert(5, "盜汗");
//            checkDAO.initinsert(5, "中暑");
//            checkDAO.initinsert(5, "咳血");
//            checkDAO.initinsert(5, "胸痛");
//            checkDAO.initinsert(5, "血尿");
//            checkDAO.initinsert(5, "心悸");
//            checkDAO.initinsert(5, "失眠");
//            checkDAO.initinsert(5, "雞眼");
//            checkDAO.initinsert(5, "失禁");
//            checkDAO.initinsert(5, "暈眩");
//            checkDAO.initinsert(5, "壓力");
//            checkDAO.initinsert(5, "便秘");
//            checkDAO.initinsert(5, "水腫");
//            checkDAO.initinsert(5, "失憶");
//            checkDAO.initinsert(5, "焦慮");
//            checkDAO.initinsert(5, "躁鬱");
//            checkDAO.initinsert(5, "自閉");
//            checkDAO.initinsert(5, "厭食");
//            checkDAO.initinsert(10, "心肌");
//            checkDAO.initinsert(5, "腹瀉");
//            checkDAO.initinsert(5, "青光眼");
//            checkDAO.initinsert(5, "拉肚子");
//            checkDAO.initinsert(5, "白內障");
//            checkDAO.initinsert(5, "肝硬化");
//            checkDAO.initinsert(5, "腎積水");
//            checkDAO.initinsert(5, "高血壓");
//            checkDAO.initinsert(5, "結核病");
//            checkDAO.initinsert(5, "低血壓");
//            checkDAO.initinsert(5, "糖尿病");
//            checkDAO.initinsert(5, "流鼻涕");
//            checkDAO.initinsert(5, "心臟病");
//            checkDAO.initinsert(5, "破傷風");
//            checkDAO.initinsert(5, "灰指甲");
//            checkDAO.initinsert(5, "眼睛乾");
//            checkDAO.initinsert(5, "老花眼");
//            checkDAO.initinsert(5, "掉頭髮");
//            checkDAO.initinsert(5, "飛蚊症");
//            checkDAO.initinsert(5, "蜂窩性");
//            checkDAO.initinsert(5, "組織炎");
//            checkDAO.initinsert(5, "雄性禿");
//            checkDAO.initinsert(5, "偏頭痛");
//            checkDAO.initinsert(5, "失智症");
//            checkDAO.initinsert(5, "尿毒症");
//            checkDAO.initinsert(5, "眼壓高");
//            checkDAO.initinsert(5, "消化不良");
//            checkDAO.initinsert(5, "小便混濁");
//            checkDAO.initinsert(5, "靜脈曲張");
//            checkDAO.initinsert(5, "人格分裂");
//            checkDAO.initinsert(5, "被害妄想症");
//            checkDAO.initinsert(5, "虛弱");
//            checkDAO.initinsert(5, "坐骨神經");
//
//            checkDAO.initinsert(5, "亞斯伯格症");
//            checkDAO.initinsert(5, "扁條線發炎");
//            checkDAO.initinsert(5, "退化性關節炎");
//            checkDAO.initinsert(5, "骨質疏鬆症");
//            checkDAO.initinsert(5, "帕金森氏症");
//            checkDAO.initinsert(5, "嚴重急性呼吸道症候群");
//
//
//            //level 2 harmful
//            checkDAO.initinsert(20, "癌症徵兆");
//            checkDAO.initinsert(20, "癌症");
//            checkDAO.initinsert(20, "愛滋");
//            checkDAO.initinsert(20, "自殺");
//            checkDAO.initinsert(20, "想不開");
//            checkDAO.initinsert(20, "過世");
//            checkDAO.initinsert(20, "無法行動");
//            checkDAO.initinsert(20, "中風");
//            //////////////////////////////////////////////////////////
//            /////////////////////////////////////////////////////////
//
//            checkDAO.initinsert(-5, "旅行");
//            checkDAO.initinsert(-5, "快樂");
//            checkDAO.initinsert(-5, "自在");
//            checkDAO.initinsert(-5, "舒服");
//            checkDAO.initinsert(-5, "夢想");
//            checkDAO.initinsert(-5, "退休生活");
//            checkDAO.initinsert(-5, "社交舞");
//            checkDAO.initinsert(-5, "運動");
//            checkDAO.initinsert(-5, "健行");
//            checkDAO.initinsert(-5, "發財");
//            checkDAO.initinsert(-5, "愉悅");
//            checkDAO.initinsert(-5, "新年快樂");
//            checkDAO.initinsert(-5, "遊戲");
//            checkDAO.initinsert(-5, "搞笑");
//
//        }


    }
}
