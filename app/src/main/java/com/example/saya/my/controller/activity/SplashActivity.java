package com.example.saya.my.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.example.saya.my.R;
import com.example.saya.my.model.Model;
import com.example.saya.my.model.bean.UserInfo;
import com.hyphenate.chat.EMClient;


//欢迎页
public class SplashActivity extends Activity {

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            //如果当前acticity已经退出，就不处理handler中的消息
            if(isFinishing()){
                return;
            }
            //判断进入主页面还是登陆页面
            toMainOrLogin();
        }
    };

    private void toMainOrLogin(){

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //判断当前账号是否已经登陆过
                if(EMClient.getInstance().isLoggedInBefore()){//登陆过
                    //获取当前登陆用户的信息
                    UserInfo account = Model.getInstance().getUSerAccountDao().getAccountByHxId(EMClient.getInstance().getCurrentUser());
                    if(account == null){
                        //跳转到登录页面
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else {
                        //登录成功后的方法
                        Model.getInstance().loginSuccess(account);

                        //跳转到主页面
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }else {//没登陆过
                    //跳转到登陆页面
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                //结束当前页面
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //发送2s钟延时消息
        handler.sendMessageDelayed(Message.obtain(),2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁消息
        handler.removeCallbacksAndMessages(null);
    }

}





/**
 * private void toMainOrLogin() {
 * //Thread不是很好，容易导致内存溢出不采用，所以创建一个线程池，统一进行管理，
 *         new Thread(){
 *             public void run(){
 *                 //EMClien环信的客户端，EMClient.getInstance()获取实例，判断环信是否登陆过
 *                 if(EMClient.getInstance().isLoggedInBefore()){
 *                     //获取到当前登录用户信息
 *
 *                     //登陆过
 *                     Intent intent=new Intent(SplashActivity.this,MainActivity.class);
 *                     startActivity(intent);
 *                 }else{
 *                     //没登陆过
 *                     Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
 *                 }
 *                 //结束当前页面
 *                 finish();
 *             }
 *         }.start();
 * }
 */

