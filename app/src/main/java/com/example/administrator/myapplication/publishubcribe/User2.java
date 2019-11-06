package com.example.administrator.myapplication.publishubcribe;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * 具体的观察者
 */
public class User2 implements Observer {

    private static final String TAG = "User2";

    private String name;
    private String message;

    public User2(String name){
        this.name = name;
    }


    void read(){
        Log.e(TAG,name +"收到了推送消息: "+ message);
    }

    @Override
    public void update(Observable observable, Object o) {
                this.message = (String)o;
        read();
    }
}
