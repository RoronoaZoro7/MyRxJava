package com.example.administrator.myapplication.standardObserver;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2019/10/30.
 */

public class StandardObserver {

    private static final String TAG = "StandardObserver";

    public static void main() {

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                emitter.onNext(132);
            }
        }).buffer(2).subscribe(new Observer<List<Object>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Object> objects) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                boolean disposed = emitter.isDisposed();
                if (!disposed) {
                    emitter.onNext("观察则模式   我是被观察者");
                    emitter.onNext("观察则模式   我是被观察者");
                    emitter.onNext("观察则模式   我是被观察者");
                    emitter.onNext("观察则模式   我是被观察者");
                    emitter.onNext("观察则模式   我是被观察者");
                }
                emitter.onComplete();
            }
        });

        Observer observer = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe" + d);
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Throwable" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }

        };

        Observer observer2 = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe2" + d);
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext2" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Throwable2" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete2");
            }

        };


        observable.subscribe(observer);

        observable.subscribe(observer2);



    }
}
