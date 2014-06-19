package com.gerenvip.test.nine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class MyActivity extends Activity {

    private ImageView targView1;
    private ImageView targView2;
    private ImageView targView3;
    private int mScreenWith;
    private int mScreenHeight;
    private AnimatorSet set1;
    private AnimatorSet set2;
    private AnimatorSet set3;
    private boolean flag = false;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;
        mScreenWith = dm.widthPixels;
        targView1 = (ImageView) findViewById(R.id.iv_i);
        targView2 = (ImageView) findViewById(R.id.iv_u1);
        targView3 = (ImageView) findViewById(R.id.iv_u2);


        set1 = createAnima(targView1);
        set2 = createAnima(targView2);
        set3 = createAnima(targView3);

        set1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                targView2.setVisibility(View.INVISIBLE);
                targView3.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (flag) {
                    set2.start();
                }
            }
        });

        set2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (flag) {
                    set3.start();
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                targView2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        targView2.setVisibility(View.VISIBLE);
                    }
                }, 100);
            }
        });

        set3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (flag) {
                    set1.start();
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                targView3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        targView3.setVisibility(View.VISIBLE);
                    }
                }, 100);
            }
        });

    }

    private AnimatorSet createAnima(final ImageView targView) {
        final AnimatorSet set = new AnimatorSet();
        float longHeight = mScreenHeight * (float) 0.3;
        float shortHeight = longHeight - mScreenHeight * (float) 0.1;
        long downAnimTime = 300;
        long otherTime = 100;
        float positiveDegree = 30;
        float negativeDegree = -30;

        ObjectAnimator initAnim = ObjectAnimator.ofFloat(targView, "rotation", 0, negativeDegree);

        initAnim.setDuration(1);
        initAnim.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator traYdown = ObjectAnimator.ofFloat(targView, "translationY", 0, longHeight);
        traYdown.setDuration(downAnimTime);
        traYdown.setInterpolator(new AccelerateInterpolator());

        //======================
        ObjectAnimator traYUp = ObjectAnimator.ofFloat(targView, "translationY", longHeight, shortHeight);
        traYUp.setInterpolator(new AccelerateInterpolator());
        traYUp.setDuration(otherTime);

        ObjectAnimator roationz = ObjectAnimator.ofFloat(targView, "rotation", negativeDegree, 0);
        roationz.setDuration(otherTime);
        roationz.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                ViewHelper.setPivotX(targView, targView.getMeasuredWidth() / 2);
                ViewHelper.setPivotX(targView, targView.getMeasuredHeight());
            }
        });

        roationz.setInterpolator(new AccelerateInterpolator());
        //======================

        //--------------------------------
        ObjectAnimator roationr = ObjectAnimator.ofFloat(targView, "rotation", 0, positiveDegree);
        roationr.setDuration(otherTime);
        roationr.setInterpolator(new AccelerateInterpolator());
        roationr.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                ViewHelper.setPivotX(targView, targView.getMeasuredWidth() / 2);
                ViewHelper.setPivotX(targView, targView.getMeasuredHeight());
            }
        });

        ObjectAnimator traYdown2 = ObjectAnimator.ofFloat(targView, "translationY", shortHeight, longHeight);
        traYdown2.setDuration(otherTime);
        traYdown2.setInterpolator(new AccelerateInterpolator());
        //--------------------------------

        final ObjectAnimator roationz1 = ObjectAnimator.ofFloat(targView, "rotation", positiveDegree, 0);
        roationz1.setDuration(otherTime);
        roationz1.setInterpolator(new AccelerateInterpolator());
        roationz1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                ViewHelper.setPivotX(targView, targView.getMeasuredWidth() / 2);
                ViewHelper.setPivotX(targView, targView.getMeasuredHeight());
            }
        });

        set.play(initAnim).before(traYdown);
        set.play(traYdown).before(traYUp);
        set.playTogether(traYUp, roationz);
        set.play(roationz).before(traYdown2);
        set.playTogether(traYdown2, roationr);
        set.play(roationz1).after(traYdown2);
        return set;
    }

    private void resetPivot() {
        //ViewHelper.setPivotX(imageView, imageView.getWidth());
        //ViewHelper.setPivotY(imageView, imageView.getHeight());
    }

    public void start() {
        set1.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycleAnim();
    }

    private void recycleAnim() {
        Log.e("WW", "回收资源");
        set1.cancel();
        set2.cancel();
        set3.cancel();
    }

    public void startSecond(View view) {
        Intent intent = new Intent(this, SecondAnimActivity.class);
        startActivity(intent);
    }

    public void startThird(View view) {
        Intent intent = new Intent(this, ThridAnimActivity.class);
        startActivity(intent);
    }

    public void startRecord(View view) {
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }

    public void rotate(View view) {
        Intent intent = new Intent(this, RotateActivity.class);
        startActivity(intent);
    }

    public void stop(View view) {
        if (flag) {
            recycleAnim();
            flag = false;
        } else {
            start();
            flag = true;
        }
    }
}

