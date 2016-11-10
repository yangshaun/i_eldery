package com.example.jamesdouble.ielederly_bside;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JamesDouble on 16/4/8.
 */
public class checktextDAO {

    public static final String TABLE_NAME = "caretext";
    public static final String KEY_ID = "_id";
    public static final String WEIGHT_COLUMN = "weight";
    public static final String TEXT_COLUMN = "text";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WEIGHT_COLUMN + " INTEGER NOT NULL, " +
                    TEXT_COLUMN + " INTEGER NOT NULL)";

    private SQLiteDatabase db;


    public checktextDAO(Context context) {
        db = MyDBHelper.getDatabase(context);
    }


    public void close() {
        db.close();
    }

    public void initinsert(int w,String s)
    {
        ContentValues cv = new ContentValues();
        cv.put(WEIGHT_COLUMN, w);
        cv.put(TEXT_COLUMN, s);

        long idr = db.insert(TABLE_NAME, null, cv);
    }




    public CheckItem insert(CheckItem item) {

        ContentValues cv = new ContentValues();

        cv.put(WEIGHT_COLUMN, item.weight);
        cv.put(TEXT_COLUMN, item.str);

        Log.e("insert",""+item.weight+","+item.str);

        long idr = db.insert(TABLE_NAME, null, cv);
        item.id = idr;
        return item;
    }


    // 讀取所有記事資料
    public Cursor getAll() {
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);
        return cursor;
    }

    // 取得指定編號的資料物件
    public Item get(long id) {
        // 準備回傳結果用的物件
        Item item = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            item = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return item;
    }

    // 把Cursor目前的資料包裝為物件
    public Item getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        Item result = new Item();

        result.id = (cursor.getLong(0));
        result.time = (cursor.getString(1));
        result.str = (cursor.getString(2));

        // 回傳結果
        return result;
    }

    // 取得資料數量
    public  int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }

    // 建立範例資料
    public void sample() {
        //先行定義時間格式


    }
}
