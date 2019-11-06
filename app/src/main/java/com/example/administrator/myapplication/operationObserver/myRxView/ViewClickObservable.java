package com.example.administrator.myapplication.operationObserver.myRxView;


import android.os.Looper;
import android.view.View;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2019/11/5.
 */

public class ViewClickObservable extends Observable<Object> {

    private final View view;

    public static final Object EVENT = new Object();


    public ViewClickObservable(View view) {
        this.view = view;
    }


    @Override
    protected void subscribeActual(Observer<? super Object> observer) {
        MyListener myListener = new MyListener(view, observer);
        observer.onSubscribe(myListener);
        view.setOnClickListener(myListener);
    }


    static final class MyListener implements Disposable, View.OnClickListener{

        private final View view;
        private final Observer<Object> observer;
        private final AtomicBoolean isDisposable = new AtomicBoolean();


        MyListener(View view, Observer<Object> observer) {
            this.view = view;
            this.observer = observer;
        }


        @Override
        public void onClick(View v) {
            if (!isDisposed()) {
                //在这里把按钮点击事件发射出去
                observer.onNext(EVENT);
            }
        }

        @Override
        public void dispose() {
            if(isDisposable.compareAndSet(false,true)){
                if(Looper.myLooper() == Looper.getMainLooper()){
                    view.setOnClickListener(null);
                }else{
                    AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
                        @Override
                        public void run() {
                            view.setOnClickListener(null);
                        }
                    });
                }
            }
        }

        @Override
        public boolean isDisposed() {
            return isDisposable.get();
        }
    }
}
