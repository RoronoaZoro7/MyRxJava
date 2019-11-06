package com.example.administrator.myapplication.publishubcribe;

import android.util.Log;

import java.util.Observable;

/**
 * 具体的被观察者角色
 * 微信公众号服务
 */
public class WechartServer2 extends Observable {
    private static final String TAG = "WechartServer2";
    private String message;

    public void pushMessage(String msg) {
        this.message = msg;
        Log.e(TAG, "微信服务号更新消息了： " + msg);
        setChanged();
        //通知所有关注了本服务号的小伙伴
        notifyObservers(message);

    }


}
