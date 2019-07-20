package com.example.saya.my.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.saya.my.model.bean.UserInfo;
import com.example.saya.my.model.db.UserAccountDB;

//用户账号数据库的操作类
public class UserAccountDao {
    public final UserAccountDB mHelper ;
    public UserAccountDao(Context context){
         mHelper =new UserAccountDB(context);//参数上下文
    }

    //添加用户到数据库
    public void addAccount(UserInfo user){
        //获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();

        //执行添加操作
        ContentValues values = new ContentValues();
        values.put(UserAccountTable.COL_HXID,user.getHxid());//根据values对象进行封装，封装hxid
        values.put(UserAccountTable.COL_NAME,user.getName());
        values.put(UserAccountTable.COL_NICK,user.getNick());
        values.put(UserAccountTable.COL_PHOTO,user.getPhoto());
        db.replace(UserAccountTable.TAB_NAME,null,values);
    }

   //根据环信id获取所有用户信息
    public UserInfo getAccountByHxId(String hxid){
//        获取数据库对象；
         SQLiteDatabase db = mHelper.getReadableDatabase();

//        执行查询语句；
        String sql = "select * from " + UserAccountTable.TAB_NAME + " where " + UserAccountTable.COL_HXID + " =?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxid});
        UserInfo userinfo = null;
        if(cursor.moveToNext()){
            userinfo = new UserInfo();
            //封装对象
            userinfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HXID)));//将环信hxid封装，返回到setHxid中
            userinfo.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
            userinfo.setNick(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NICK)));
            userinfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));
        }

//        关闭资源；
        cursor.close();

//        返回数据；
        return userinfo;
    }
}
