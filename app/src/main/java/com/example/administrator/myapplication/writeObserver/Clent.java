package com.example.administrator.myapplication.writeObserver;

import android.util.Log;

/**
 * Created by Administrator on 2019/10/30.
 */

public class Clent {

    private static final String TAG = "Clent";

    public static void main(){
        WecharServer wecharServer = new WecharServer();

        User user1 = new User("韩梅梅");
        User user2 = new User("李雷");

        wecharServer.add(user1);
        wecharServer.add(user2);

        wecharServer.pushMessage("观察者模式  发送消息");


        wecharServer.remove(user2);

        wecharServer.pushMessage("李雷取消关注了公众号，发送消息");

        Log.e(TAG,"=======================================================");

        WechartServer2 wechartServer2 = new WechartServer2();

        User2 user21 = new User2("韩梅梅");
        User2 user22 = new User2("李雷");

        wechartServer2.addObserver(user21);
        wechartServer2.addObserver(user22);

        wechartServer2.pushMessage("观察者模式 发送消息  jsk实现");

        wechartServer2.deleteObserver(user22);

        wechartServer2.pushMessage("李雷取消关注了公众号，发送消息");

    }
}
