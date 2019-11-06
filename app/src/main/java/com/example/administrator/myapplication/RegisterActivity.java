package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.bt)
    Button mBt;

    private static int SECOND = 20;

    private static final String TAG = "RegisterActivity";
    private Observable<Boolean> observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        observable = RxView.clicks(mBt)
                .throttleFirst(SECOND, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Function<Object, Boolean>() {
                    @Override
                    public Boolean apply(Object o) throws Exception {
                        Log.e(TAG,"apply1:  "+ o.toString());
                        return false;
                    }
                }).doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.e(TAG,"accept2:  "+ aBoolean.toString());
                        mBt.setEnabled(aBoolean);
                    }
                });

        observable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .take(SECOND)
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                RxTextView.text(mBt).accept("剩余" + (SECOND - aLong-1) + "秒");
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(TAG,"accept3:  "+ throwable.toString());
                                Log.e(TAG, "Throwable内" + throwable.toString());
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.e(TAG,"apply4:  ");
                                RxTextView.text(mBt).accept("获取验证码");
                                RxView.enabled(mBt).accept(true);
                            }
                        });
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "Throwable外" + throwable.toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        observable.unsubscribeOn(AndroidSchedulers.mainThread());
    }
}
