package com.example.saya.my.controller.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saya.my.R;
import com.example.saya.my.model.Model;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class RegistActivity extends Activity {

    private EditText et_regist_name;
    private  EditText et_regist_pwd;
    private EditText et_regist_pwds;
    private Button bt_regist_regist;
    private TextView bt_regist_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        //初始化控件
        initView();
        //初始化监听
        initListener();
    }

    private void initListener() {
        //注册按钮的点击事件
        bt_regist_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();
            }
        });

        bt_regist_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //注册按钮业务
    private void regist(){
        //1.获取输入的用户以及密码
        final String registName = et_regist_name.getText().toString();
        final String registPwd = et_regist_pwd.getText().toString();
        final String confirmPwd = et_regist_pwds.getText().toString();

        //2.校验输入的用户以及密码
        if(TextUtils.isEmpty(registName)||TextUtils.isEmpty(registPwd)){
            Toast.makeText(RegistActivity.this,"输入的用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }else if(registName.length() < 6 ){
            Toast.makeText(RegistActivity.this,"用户昵称不能小于6位",Toast.LENGTH_SHORT).show();
            return;
        } else if(registPwd.length() < 6 ){
            Toast.makeText(RegistActivity.this,"密码不能小于6位",Toast.LENGTH_SHORT).show();
            return;
        } else if (!registPwd.equals(confirmPwd)){
            Toast.makeText(RegistActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        //3.去服务器注册账号
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    //去环信服务器注册账号
                    EMClient.getInstance().createAccount(registName,registPwd);
                    //更新页面提示
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegistActivity.this,"注册失败"+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void initView() {
        et_regist_name = (EditText) findViewById(R.id.et_regist_name);
        et_regist_pwd = (EditText) findViewById(R.id.et_regist_pwd);
        et_regist_pwds = (EditText) findViewById(R.id.et_regist_pwds);
        bt_regist_regist = (Button) findViewById(R.id.bt_regist_regist);
        bt_regist_login = (TextView) findViewById(R.id.bt_regist_login);
    }
}
