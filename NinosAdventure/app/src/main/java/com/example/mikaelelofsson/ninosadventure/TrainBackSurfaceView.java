package com.example.mikaelelofsson.ninosadventure;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.LinearInterpolator;

/**
 * Created by Mikael Elofsson on 2017-02-16.
 */

public class TrainBackSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder backgroundHolder;
    GameThread backgroundThread;
    Paint rectColor = new Paint();
    Context context;

    Paint rectPaint;
    Context myContext;

    ValueAnimator sky1Animator;
    ValueAnimator sky2Animator;

    BackgroundElement real;
    BackgroundElement foreground;
    BackgroundElement sky1;
    BackgroundElement sky2;
    BackgroundElement trainHouse;

    Bitmap realBmp;
    Bitmap skyBmp;
    Bitmap trainHouseBmp;
    Bitmap foregroundBmp;

    AnimatorSet skySet;
    Boolean endSkyLoop = false;

    float xPos, yPos;
    float startOfScreen, endOfScreen;
    float displayHeight, displayWidth;
    float lenghtToMoveY, lenghtToMoveX;


    public TrainBackSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;


        backgroundHolder = getHolder();
        backgroundHolder.addCallback(this);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;
        endOfScreen = displayWidth + 900;
        startOfScreen = -500;

        trainHouse = new BackgroundElement();
        trainHouse.setBackgroundWidth(displayWidth);
        trainHouse.setBackgroundHeight((70*(displayHeight/100)));
        trainHouse.setXPos(0);
        trainHouse.setYPos(0);
        trainHouse.setStartPosX(trainHouse.getXPos());
        trainHouse.setStartPosY(trainHouse.getYPos());

        sky1 = new BackgroundElement();
        sky1.setBackgroundWidth(displayWidth);
        sky1.setBackgroundHeight((70*(displayHeight/100)));
        sky1.setXPos(0);
        sky1.setYPos(0);
        sky1.setStartPosX(sky1.getXPos());
        sky1.setStartPosY(sky1.getYPos());

        sky2 = new BackgroundElement();
        sky2.setBackgroundWidth(displayWidth);
        sky2.setBackgroundHeight((70*(displayHeight/100)));
        sky2.setXPos(0);
        sky2.setYPos(0);
        sky2.setStartPosX(sky2.getXPos());
        sky2.setStartPosY(sky2.getYPos());

        real = new BackgroundElement();
        real.setBackgroundWidth(displayWidth);
        real.setBackgroundHeight((10*(displayHeight/100)));
        real.setXPos(0);
        real.setYPos((70*(displayHeight/100)));
        real.setStartPosX(real.getXPos());
        real.setStartPosY(real.getYPos());


        foreground = new BackgroundElement();
        foreground.setBackgroundWidth(displayWidth);
        foreground.setBackgroundHeight((25*(displayHeight/100)));
        foreground.setXPos(0);
        foreground.setYPos((80*(displayHeight/100)));
        foreground.setStartPosX(trainHouse.getXPos());
        foreground.setStartPosY(trainHouse.getYPos());


        Bitmap origSkyBmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.townsky);
        skyBmp = Bitmap.createScaledBitmap(origSkyBmp, (int) sky1.getBackgroundWidth(), (int) sky1.getBackgroundHeight(), true);

        Bitmap origHouseBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.trainhouse);
        trainHouseBmp = Bitmap.createScaledBitmap(origHouseBmp, (int)trainHouse.getBackgroundWidth(), (int)trainHouse.getBackgroundHeight(), true);

        Bitmap origRoadBmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.real);
        realBmp = Bitmap.createScaledBitmap(origRoadBmp, (int)real.getBackgroundWidth(), (int)real.getBackgroundHeight(), true);

        Bitmap origForgroundBmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.trainforeground);
        foregroundBmp = Bitmap.createScaledBitmap(origForgroundBmp, (int)foreground.getBackgroundWidth(), (int)foreground.getBackgroundHeight(), true);


        this.myContext = context;
        rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);


    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        android.view.ViewGroup.LayoutParams lp = this.getLayoutParams();
        lp.width = (int) displayWidth;
        lp.height = (int) displayHeight;
        this.setLayoutParams(lp);

        backgroundThread = new GameThread(holder, this);
        backgroundThread.setRunning(true);
        backgroundThread.start();

        startSkyLoops();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        endSkyLoop = true;
        skySet.end();
        backgroundThread.setRunning(false);

        boolean retry = true;

        while(retry)
        {
            try
            {
                backgroundThread.join();
                retry = false;
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);

            canvas.drawBitmap(skyBmp, sky1.getXPos(), sky1.getYPos(), null);
            canvas.drawBitmap(skyBmp, sky2.getXPos(), sky2.getYPos(), null);
            canvas.drawBitmap(trainHouseBmp, trainHouse.getXPos(), trainHouse.getYPos(), null);
            canvas.drawBitmap(realBmp, real.getXPos(), real.getYPos(), null);
        canvas.drawBitmap(foregroundBmp, foreground.getXPos(), foreground.getYPos(), null);





    }

    void startSkyLoops() {


        Keyframe k1 = Keyframe.ofFloat(0, sky1.getXPos());
        Keyframe k2 = Keyframe.ofFloat(.5f, 0-displayWidth);
        Keyframe k3 = Keyframe.ofFloat(.49f, displayWidth);
        Keyframe k4 = Keyframe.ofFloat(1, 0);

        final PropertyValuesHolder sky1X = PropertyValuesHolder.ofKeyframe("xPos", k1, k2,k3,k4);
        sky1Animator = ObjectAnimator.ofPropertyValuesHolder(sky1, sky1X);
        sky1Animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sky1.setXPos((Float) sky1Animator.getAnimatedValue());

            }
        });


        k1 = Keyframe.ofFloat(0, displayWidth);
        k2 = Keyframe.ofFloat(1f, 0-displayWidth);


        final PropertyValuesHolder sky2X = PropertyValuesHolder.ofKeyframe("xPos", k1, k2);
        sky2Animator = ObjectAnimator.ofPropertyValuesHolder(sky2,sky2X);
        sky2Animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sky2.setXPos((Float) sky2Animator.getAnimatedValue());

            }
        });

        skySet=new AnimatorSet();
        skySet.play(sky1Animator)
                .with(sky2Animator);
        skySet.setInterpolator(new LinearInterpolator());
        skySet.setDuration(250000);
        skySet.addListener(skySetListener);
        skySet.start();


    }



    ObjectAnimator.AnimatorListener skySetListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {


        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if(!endSkyLoop)
            startSkyLoops();

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };


}





