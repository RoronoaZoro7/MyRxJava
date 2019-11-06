package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RepeatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);
    }

    @Override
    protected void onResume() {
        super.onResume();
        main();
    }



    private static final String TAG = "RepeatUntilDemo";

    public  void main(){
        repeatUntil();
    }

    public static void repeatUntil(){
        final long start = System.currentTimeMillis();
        Observable.interval(500, TimeUnit.MILLISECONDS)
                .take(5)//发送五次
                .subscribeOn(Schedulers.io())
                .repeatUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() throws Exception {
                        Log.e(TAG,"getAsBoolean: " + System.currentTimeMillis());
                        return System.currentTimeMillis() - start > 5000;
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG,"aLong: " + aLong);
                    }
                });
    }
}
