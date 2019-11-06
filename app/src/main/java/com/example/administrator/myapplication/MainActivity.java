package com.example.administrator.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myapplication.hotColdObserver.ColdObservableDemo;
import com.example.administrator.myapplication.operationObserver.OperationFlatMapOnserver;
import com.example.administrator.myapplication.operationObserver.OperationRxViewObserve;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    private ListView listView;
    private Button idbutton;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.id_list);
        idbutton = findViewById(R.id.id_button);
        //标准基础RxJava使用
//        StandardObserver.main();
        //手写观察者模式，和实现jdk的观察者模式
//        Clent.main();
        //冷热观察者 基于RxJava
        ColdObservableDemo.main();
        String[] array = new String[]{
                "flapMap",
                "登录将几个EditText设置为被观察者，合并观察输入框是否满足我们的要求",
                "注册发送验证码，倒计时",
                "输入框每一秒钟请求一次，第一次为空跳过搜索",
                "Cache模拟三级缓存",
                "遍历数组",
                "遍历集合",
                "延迟一秒之后周期性发送数据",
                "合并数据",
                "重复请求",
                "retry 网络请求出错 重新请求",
                "延迟两秒再发送请求"
        };
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(this);

        OperationRxViewObserve.clicks(idbutton)
//              某个时间内只能点击一次
                .throttleFirst(10000, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe" + d.toString());
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.e(TAG, "onNext" + o.toString());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError" + e.toString());

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");

                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        String item = (String) arrayAdapter.getItem(position);
        Toast.makeText(this, item, Toast.LENGTH_LONG).show();
        switch (position) {
            case 0://测试 flapMap 的用法
                OperationFlatMapOnserver.main();
                break;
            case 1://登录将几个EditText设置为被观察者，合并观察输入框是否满足我们的要求
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case 2://注册发送验证码，倒计时
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case 3://输入框发生改变，间隔一段时间后再去请求数据
                startActivity(new Intent(this, FilterActivity.class));
                break;
            case 4://模拟三级缓存 的操作
                startActivity(new Intent(this, CacheActivity.class));
                break;
            case 5://数组遍历
                startActivity(new Intent(this, FromArrayActivity.class));
                break;
            case 6://集合遍历
                startActivity(new Intent(this, FromIterableActivity.class));
                break;
            case 7://定时发送数据
                startActivity(new Intent(this, IntervalActivity.class));
                break;
            case 8://合并数据
                startActivity(new Intent(this, MargeActivity.class));
                break;
            case 9://发送消息 每个一段时间发送一条消息，控制消息的条数
                startActivity(new Intent(this, RepeatActivity.class));
                break;
            case 10://c出错重连
                startActivity(new Intent(this, RetryActivity.class));
                break;
            case 11://定时发送某个事件
                startActivity(new Intent(this, TimeActivity.class));
                break;
        }
    }
}
