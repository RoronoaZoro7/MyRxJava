package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class RetryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retry);
    }

    @Override
    protected void onResume() {
        super.onResume();
        main();
    }

    private static final String TAG = "RetryDemo";

    public static void main() {
        retry();
    }
    static int count = 0;//重连次数

    public static void retry() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                for (int i = 0; i < 100; i++) {
                    observableEmitter.onNext(i);
                    if (i == 2) {//模拟网络请求出错
                        observableEmitter.onError(new IOException("retry error"));
                    }
                }
                observableEmitter.onComplete();
            }
        }).retry(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                if (throwable instanceof IOException && count++ < 4) {
                    Log.e(TAG,"重连次数： " + count);
                    return true;
                }
                return false;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG,"integer: " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG,throwable.getMessage());
            }
        });
    }
}
