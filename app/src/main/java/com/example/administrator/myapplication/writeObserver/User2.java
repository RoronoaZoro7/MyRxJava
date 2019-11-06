package com.example.administrator.myapplication.writeObserver;

import android.util.Log;

import java.util.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2019/10/30.
 */

public class User2 implements Observer {

    private static final String TAG = "User2";

    String name;
    String msg;

    public User2(String name){
        this.name = name;
    }
    @Override
    public void update(Observable o, Object arg) {
        this.msg = (String) arg;
        Log.e(TAG,name+"收到了消息 jdk实现的观察者模式");
    }
}
