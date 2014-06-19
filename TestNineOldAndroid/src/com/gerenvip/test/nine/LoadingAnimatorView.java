package com.gerenvip.test.nine;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


public class LoadingAnimatorView extends SurfaceView implements
        SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder;
    private Bitmap bitmap;
    private Paint paint1;//画红色的笔
    private Paint paint2;//开始的时候，按照灰色，将图片画在界面上
    public boolean flag = true;
    private int y = 100;
    private int mScreenHeight;
    private int mScreenWith;
    private int startDrawX;
    private int startDrawY;
    protected int mDrawableId;

    public LoadingAnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IuuPullLoadingTest);
        mDrawableId = a.getResourceId(R.styleable.IuuPullLoadingTest_drawablea, R.drawable.ic_launcher);
        init(context, mDrawableId);

    }

    public LoadingAnimatorView(Context context) {
        super(context);
        init(context, R.drawable.ic_launcher);

    }
    private void init(Context context, int drawableid) {
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;
        mScreenWith = dm.widthPixels;

        holder = this.getHolder();
        holder.addCallback(this);
        paint1 = new Paint();
        paint1.setColor(Color.RED);
        paint2 = new Paint();
        paint2.setColor(Color.GRAY);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), drawableid);
        bitmap = bitmap1.extractAlpha();// 获取一个透明图片
        y = bitmap.getHeight();//初始化y轴坐标
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        startDrawX = mScreenWith / 2 - width / 2;
        startDrawY = mScreenHeight/2;
    }

    //改变裁剪区域
    private void playAnimator() {
        if (y > 0) {
            y-=3;
        }
    }

    private void drawLoadingAnimator() {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            if(canvas != null){
                //canvas.drawBitmap(bitmap, 500, 100,null);
                canvas.drawColor(Color.parseColor("#EAEDED"));
                canvas.drawBitmap(bitmap, startDrawX , startDrawY, paint2);//将图片画到画布上
                canvas.save();
                //裁剪
                canvas.clipRect(startDrawX, y+startDrawY, bitmap.getWidth()+startDrawX,
                        bitmap.getHeight()+startDrawY);
                canvas.drawBitmap(bitmap, startDrawX, startDrawY, paint1);
                canvas.restore();
            }
            /*
             * Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
             * Rect dst = new Rect(100, 100, bitmap.getWidth()+100, y+100);
             * canvas.drawBitmap(bitmap, src, dst, paint2);
             */
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if (holder != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(this).start();//开启绘制线程
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    //绘制动画线程
    @Override
    public void run() {
        while (flag) {
            drawLoadingAnimator();
            playAnimator();
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}