package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class FilterActivity extends AppCompatActivity {

    private String TAG = "FilterActivity";

    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ed = findViewById(R.id.ed);

        RxTextView.textChanges(ed)
                .debounce(1, TimeUnit.SECONDS)
                //跳过第1次请求 因为初始输入框的空字符状态
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(CharSequence charSequence) {
                        Log.e(TAG,"收到的字符： " + charSequence.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError： " + e.getMessage() );

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }
}
