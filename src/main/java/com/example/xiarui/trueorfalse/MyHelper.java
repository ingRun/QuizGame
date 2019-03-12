package com.example.xiarui.trueorfalse;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {

    public MyHelper(Context context){
        super(context,"riddle.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table riddle(_id integer primary key autoincrement," +
                                "question varchar(100),answer varchar(20))");

        String[][] str = {{"1+1=3？","0"},{"IG是s8的冠军？","1"},{"中国在亚洲？","1"},{"非洲人也被称为战斗民族？","0"},
                {"世界上最高的山峰是珠穆朗玛峰？","1"},{"QQ和微信都是腾讯旗下的？","1"},
                {"switch在JAVA中是循环语句？","0"},{"相对论是爱因斯坦提出来的","1"},{"旭旭宝宝又称为大马猴","1"},
                {"H2O =电= H2 + O?","1"}};
        for(int i = 0 ; i < str.length;i++){
            sqLiteDatabase.execSQL("insert into riddle (question,answer) values(?,?)",str[i] );
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
