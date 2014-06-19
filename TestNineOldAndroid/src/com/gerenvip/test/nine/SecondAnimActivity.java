package com.gerenvip.test.nine;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by wangwei_cs on 2014/6/16.
 */
public class SecondAnimActivity extends Activity {

    private LoadingAnimatorView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        /*view = new LoadingAnimatorView(this);
        setContentView(view);*/
        view = (LoadingAnimatorView) findViewById(R.id.loadingview);
    }

    @Override
    public void onBackPressed() {
        view.flag = false;//结束绘制线程
        super.onBackPressed();
    }

}
