package com.example.saya.my.controller.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.saya.my.R;
import com.example.saya.my.controller.fragment.ChatFragment;
import com.example.saya.my.controller.fragment.ContactListFragment;
import com.example.saya.my.controller.fragment.FindFragment;
import com.example.saya.my.controller.fragment.SettingFragment;
//主布局页面
public class MainActivity extends FragmentActivity {

    private  RadioGroup rg_main;
    private  ChatFragment chatFragment;
    private  ContactListFragment contactListFragment;
    private  SettingFragment settingFragment;
    private FindFragment findFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDate();
        initListener();
    }

    private void initView() {
        rg_main = findViewById(R.id.rg_main);
    }

    private void initDate() {
        //创建三个Fragment对象
        chatFragment = new ChatFragment();
        contactListFragment = new ContactListFragment();
        settingFragment = new SettingFragment();
        findFragment = new FindFragment();

    }

    private void initListener() {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment = null;
                switch (checkedId){
                    //会话列表页面
                    case R.id.rb_main_chat:
                        fragment = chatFragment;
                        break;
                    //联系人列表页面
                    case R.id.rb_main_contact:
                        fragment = contactListFragment;
                        break;
                    //个人页面
                    case R.id.rb_main_setting:
                        fragment = settingFragment;
                        break;
                    //发现页面
                    case R.id.rb_main_find:
                        fragment = findFragment;
                        break;
                }
                //实现fragment切换的方法
                switchFragment(fragment);
            }
        });
        //默认选择会话列表页面
        rg_main.check(R.id.rb_main_chat);
    }

    //实现fragment切换的方法
    private void switchFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main,fragment).commit();
    }

}
