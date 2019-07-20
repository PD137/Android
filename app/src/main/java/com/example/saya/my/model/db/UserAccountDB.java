package com.example.saya.my.model.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.saya.my.model.dao.UserAccountTable;
//创建用户的数据库
public class UserAccountDB extends SQLiteOpenHelper {

    //构成
    public UserAccountDB( Context context) {
        super(context, "account.db", null, 1);
    }

    //数据库创建的时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库表语句
        db.execSQL(UserAccountTable.CREATE_TABLR);
    }

    //数据库更新的时候调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
