package com.gerenvip.test.nine;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Random;

/**
 * Created by wangwei_cs on 2014/6/17.
 */
public class RecordActivity extends Activity {

    private LinearLayout mContainer;
    private RecordingView mRecordingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        mContainer = (LinearLayout) findViewById(R.id.container);
        mRecordingView = new RecordingView(this);
        mContainer.addView(mRecordingView, 0);
    }

    public void start(View view) {
        int volume = UtilRandom(0, 100);
        Log.e("IUU", "button click volume=" + volume);
        mRecordingView.setVolume(volume);
    }

    int UtilRandom(int botton, int top) {
        return ((Math.abs(new Random().nextInt()) % (top + 1 - botton)) + botton);
    }
}
