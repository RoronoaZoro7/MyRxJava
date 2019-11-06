package com.example.administrator.myapplication.writeObserver;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/10/30.
 */

public class WecharServer implements Observable {
    private static final String TAG = "WecharServer";
    String msg;
    List<Observer> list;

    public WecharServer() {
        this.list = new ArrayList<Observer>();
    }

    @Override
    public void add(Observer observer) {
        list.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        list.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer:list) {
            observer.upData(msg);
        }
    }

    public void pushMessage(String msg){
        this.msg = msg;
        Log.e(TAG,"微信服务号更新消息了： " + msg);
        notifyObservers();
    }
}
