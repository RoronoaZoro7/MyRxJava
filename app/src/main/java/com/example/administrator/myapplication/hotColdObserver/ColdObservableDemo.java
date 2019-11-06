package com.example.administrator.myapplication.hotColdObserver;

import android.util.Log;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.security.auth.Subject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * 冷： 观察者订阅了，才会开始执行发射数据流的代码
 * Observable  和 Observer 是一对一的关系
 * 对 Cold Observable 而已，有多个Observer的时候，
 * 它们各自的事件是独立的
 * 事件是什么？
 * 事件类型         作用
 * onNext()        观察者会回调它的onNext()方法
 * onError()        onError事件发送之后，其他事件不会继续发送
 * onComplete()     onComplete事件发送之后，其他事件不会继续发送
 */
public class ColdObservableDemo {

    private static final String TAG = "ColdObservableDemo";


    public static void main() {
        //TODO:

        

        //创建一个"冷"的被观察者
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(final ObservableEmitter<Long> emitter) throws Exception {
                Observable.interval(1000, TimeUnit.MILLISECONDS,
                        Schedulers.computation())
                        .take(10)
                        .subscribe(new io.reactivex.functions.Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                Log.e(TAG, Thread.currentThread().getName());
                                emitter.onNext(aLong);
                            }
                        });

            }
        }).map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //冷热的区别就在与下面两行代码，删除之后就是冷的
                //区别是冷的是一对一的关系，每一个观察者对于一个被观察者，数据全部重新发送
                //热的是一开始就发送数据，什么时候订阅，什么时候开始处理
                .publish();


        ((ConnectableObservable<Long>) observable).connect();

        observable.subscribe(new io.reactivex.functions.Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e(TAG, "consumer1: " + aLong + Thread.currentThread().getName());
            }
        });

        observable.subscribe(new io.reactivex.functions.Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e(TAG, "    consumer2: " + aLong + Thread.currentThread().getName());
            }
        });

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        observable.subscribe(new io.reactivex.functions.Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e(TAG, "            consumer3: " + aLong + Thread.currentThread().getName());
            }
        });

    }

}
