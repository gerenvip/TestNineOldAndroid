package com.gerenvip.test.nine;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by wangwei_cs on 2014/6/18.
 */
public class RotateActivity extends Activity{

    private ImageView mImageView;
    private  Matrix mHeaderImageMatrix;
    private  Animation mRotateAnimation;
    static final int ROTATION_ANIMATION_DURATION = 1200;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotate);
        mImageView = (ImageView) findViewById(R.id.im_rotate);
        mImageView.setScaleType(ImageView.ScaleType.MATRIX);
        mHeaderImageMatrix = new Matrix();
        mImageView.setImageMatrix(mHeaderImageMatrix);

        mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);

    }

    public void start_stop(View view) {
        if (flag) {

            mRotateAnimation.cancel();
            mImageView.setVisibility(View.INVISIBLE);
            flag = false;
        } else {
            mImageView.startAnimation(mRotateAnimation);
            //mRotateAnimation.start();
            mImageView.setVisibility(View.VISIBLE);
            flag = true;
        }
    }
}
