package com.example.administrator.myapplication.writeObserver;

import android.util.Log;

/**
 * Created by Administrator on 2019/10/30.
 */

public class User implements Observer {
    private static final String TAG = "User";
    String name;
    String msg;

    public User(String name){
        this.name = name;
    }

    @Override
    public void upData(Object arg0) {
        this.msg = (String) arg0;
        Log.e(TAG,name+"收到了一条消息");
    }
}
