package com.example.administrator.myapplication.writeObserver;

import android.util.Log;

import com.example.administrator.myapplication.publishubcribe.WechartServer;

import java.util.*;

/**
 * Created by Administrator on 2019/10/30.
 */

public class WechartServer2 extends java.util.Observable {

    String msg;
    private static final String TAG = "WechartServer2";

    public void pushMessage(String msg){
        this.msg = msg;
        Log.e(TAG,"微信服务号有新的消息了");
        setChanged();
        notifyObservers(msg);

    }
}
