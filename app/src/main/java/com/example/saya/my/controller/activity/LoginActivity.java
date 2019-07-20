package com.example.saya.my.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.v;
import com.example.saya.my.R;
import com.example.saya.my.model.Model;
import com.example.saya.my.model.bean.UserInfo;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
//登录页面
public class LoginActivity extends Activity {
    private EditText et_login_name;
    private  EditText et_login_pwd;
    private Button bt_login_login;
    private TextView forget_password;
    private TextView bt_login_resist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化
        initView();
        //初始化监听
        initListener();

    }

    private void initListener() {
        bt_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        bt_login_resist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
            }
        });
    }


    //登录按钮业务
    private void login(){
        //1.获取输入的用户名和密码
        final String loginName = et_login_name.getText().toString();
        final String loginPwd = et_login_pwd.getText().toString();
        //2.校验输入的用户以及密码
        if(TextUtils.isEmpty(loginName)||TextUtils.isEmpty(loginPwd)){
            Toast.makeText(LoginActivity.this,"输入的用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        //登录逻辑  需要联网操作，则要开一个线程
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //去环信服务器登录
                EMClient.getInstance().login(loginName, loginPwd, new EMCallBack() {
                    //登录成功后的处理
                    @Override
                    public void onSuccess() {
                        //对模型层数据的处理
//                        Model.getInstance().loginSuccess();
                        Model.getInstance().loginSuccess(new UserInfo( loginName ));

                        //保存用户账号信息到本地数据库
//                        Model.getInstance().getUserAccountDao().addAccount(new UserInfo(loginName));
                        Model.getInstance().getUSerAccountDao().addAccount( new UserInfo( loginName ) );

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //提示登录成功
                                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                //跳转到主页面
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }

                    //登录失败的处理
                    @Override
                    public void onError(int i, final String s) {
                        //提示登录失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"登录失败"+s,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    //登录过程中的处理
                    @Override
                    public void onProgress(int progress, String status) {

                    }
                });
            }
        });

    }

    private void initView() {
        et_login_name = (EditText) findViewById(R.id.et_login_name);
        et_login_pwd = (EditText) findViewById(R.id.et_login_pwd);
        bt_login_login = (Button) findViewById(R.id.bt_login_login);
        forget_password = (TextView) findViewById(R.id.forget_password);
        bt_login_resist = (TextView) findViewById(R.id.bt_login_regist);
    }
}
