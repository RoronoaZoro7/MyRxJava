package com.example.administrator.myapplication.operationObserver;

import android.view.View;

import com.example.administrator.myapplication.operationObserver.myRxView.ViewClickObservable;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2019/11/5.
 */

public class OperationRxViewObserve  {
    private static final String TAG = "OperationRxViewObserve";

    public static Observable<Object> clicks(View view){
        return new ViewClickObservable(view);
    }


    public static void main(View view){
        OperationRxViewObserve.clicks(view)
                .throttleFirst(1000, TimeUnit.MILLISECONDS);
                
    }



}
