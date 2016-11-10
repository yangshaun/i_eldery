package com.example.jamesdouble.ielederly_bside;

import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by JamesDouble on 16/4/7.
 */
public class care_text {


    public static int checking(String input,checktextDAO checkDAO)
    {
        int checksum =0;
        Cursor cursor = checkDAO.getAll();

        int rows_num = cursor.getCount();
        //level 1 check////////////////////////////////////////////
        //Get the text file
        int cw =0;
        String ct ="";


        if(rows_num != 0) {
            cursor.moveToFirst();			//將指標移至第一筆資料
            for(int i=0; i<rows_num; i++) {
                cw = cursor.getInt(1);	//取得第0欄的資料，根據欄位type使用適當語法
                ct = cursor.getString(2);
                if(input.contains(ct)||ct.contains(input)) //互相包含
                {
                    checksum += cw;
                }

                cursor.moveToNext();		//將指標移至下一筆資料
            }
        }
        cursor.close();		//關閉Cursor
        Log.e("emotion","Done check sum="+String.valueOf(checksum));
        ////////level2 check/////////////////////////////////////
        //Get the text file
        return checksum;
    }
}
