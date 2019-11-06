package com.example.administrator.myapplication.writeObserver;


/**
 * Created by Administrator on 2019/10/30.
 * 被观察者 需要有三个方法
 * 添加，删除 通知观察者的方法
 */

public interface Observable {
    void add(Observer observer);
    void remove(Observer observer);
    void notifyObservers();
}
