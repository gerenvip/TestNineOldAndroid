package com.gerenvip.test.nine;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * Created by wangwei_cs on 2014/6/17.
 */
public class ThridAnimActivity extends Activity {

    private PullToRefreshScrollView mPull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh_test);
        mPull = (PullToRefreshScrollView) findViewById(R.id.pull);
        mPull.setMode(PullToRefreshBase.Mode.BOTH);
      /*  mPull.getLoadingLayoutProxy().setLoadingDrawable(
                getResources().getDrawable(R.drawable.sending));*/
        mPull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                Message msg = new Message();
                mhandler.sendMessageDelayed(msg, 600);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                Message msg = new Message();
                mhandler.sendMessageDelayed(msg, 600);
            }
        });

    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mPull.onRefreshComplete();
        }
    };
}
