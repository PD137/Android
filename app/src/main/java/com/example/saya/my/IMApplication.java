package com.example.saya.my;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.saya.my.model.Model;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;

public class IMApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化easeui
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false); //设置需要同意后才能接受邀请
        options.setAutoAcceptGroupInvitation(false); //设置需要同意后才能接受群邀请
        EaseUI.getInstance().init(this,options);//上下文

        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }

            private EaseUser getUserInfo(String username) {
                EaseUser easeUser = new EaseUser(username);

                if(username.equals(EMClient.getInstance().getCurrentUser())) {
                    easeUser.setAvatar("ease_default_avatar");
                    easeUser.setNickname(username);
                    return easeUser;
                }else {
                    easeUser.setAvatar("test");
                    easeUser.setNickname(username);
                    return easeUser;
                }
//                return easeUser;
            }
        });


    //初始化数据模型层类
        Model.getInstance().init(this);

        //初始化全局上下文
        mContext = this;
    }

    //获取全局上下文对象
    public static Context getGlobalApplication(){
        return mContext;
    }
}
