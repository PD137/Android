package com.example.saya.my.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.saya.my.model.dao.ContactTable;
import com.example.saya.my.model.dao.InviteTable;

//联系人和邀请信息总数据库
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name){
        super(context,name+".db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建联系人表
        db.execSQL(ContactTable.CREATE_TAB);

        //创建邀请信息的表
        db.execSQL(InviteTable.CREATE_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}