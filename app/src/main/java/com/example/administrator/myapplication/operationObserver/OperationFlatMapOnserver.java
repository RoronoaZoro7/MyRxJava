package com.example.administrator.myapplication.operationObserver;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2019/11/4.
 */

public class OperationFlatMapOnserver {
    private static final String TAG = "OperationOnserver";

    public static void main() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
            // 采用flatMap（）变换操作符
        }).observeOn(Schedulers.io())
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        final List<String> list = new ArrayList<>();
                        Log.e(TAG, "flatMap:   " + integer);
                        // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                        // 最终合并，再发送给被观察者
                        for (int i = 10; i < 14; i++) {
                            list.add(integer + "_flatMap_" + i);
                        }
                        return Observable.fromIterable(list);
                    }
                }).flatMap(new Function<String, ObservableSource<Bean>>() {
            @Override
            public ObservableSource<Bean> apply(String s) throws Exception {
//                这里return一个网络请求的 observer
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bean>() {
            @Override
            public void accept(Bean s) throws Exception {
                Log.e(TAG, "subscribe:   " + s);
            }
        });
    }

    public class Bean {

    }
}
