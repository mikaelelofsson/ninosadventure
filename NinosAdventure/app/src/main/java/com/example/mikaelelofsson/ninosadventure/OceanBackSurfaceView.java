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

public class OceanBackSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder backgroundHolder;
    GameThread backgroundThread;
    Paint rectColor = new Paint();
    Context context;

    Paint rectPaint;
    Context myContext;

    AnimatorSet skySet;
    BackgroundElement water;
    BackgroundElement bushes;
    BackgroundElement sky1;
    BackgroundElement sky2;
    BackgroundElement skyReflection1;
    BackgroundElement skyReflection2;
    Boolean endSkyLoop = false;

    ObjectAnimator sky1Animator;
    ObjectAnimator sky2Animator;
    ObjectAnimator skyReflection1Animator;
    ObjectAnimator skyReflection2Animator;

    Bitmap waterBmp;
    Bitmap bushesBmp;
    Bitmap skyBmp;
    Bitmap skyReflectionBmp;

    float xPos, yPos;
    float startOfScreen, endOfScreen;
    float displayHeight, displayWidth;


    public OceanBackSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;


        backgroundHolder = getHolder();
        backgroundHolder.addCallback(this);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;
        endOfScreen = displayWidth + 900;
        startOfScreen = -500;


        sky1 = new BackgroundElement();
        sky1.setBackgroundWidth(displayWidth);
        sky1.setBackgroundHeight((35*(displayHeight/100)));
        sky1.setXPos(0);
        sky1.setYPos(0);
        sky1.setStartPosX(sky1.getXPos());
        sky1.setStartPosY(sky1.getYPos());

        sky2 = new BackgroundElement();
        sky2.setBackgroundWidth(displayWidth);
        sky2.setBackgroundHeight((35*(displayHeight/100)));
        sky2.setXPos(0);
        sky2.setYPos(0);
        sky2.setStartPosX(sky2.getXPos());
        sky2.setStartPosY(sky2.getYPos());

        skyReflection1 = new BackgroundElement();
        skyReflection1.setBackgroundWidth(displayWidth);
        skyReflection1.setBackgroundHeight((65*(displayHeight/100)));
        skyReflection1.setXPos(0);
        skyReflection1.setYPos((35*(displayHeight/100)));
        skyReflection1.setStartPosX(skyReflection1.getXPos());
        skyReflection1.setStartPosY(skyReflection1.getYPos());

        skyReflection2 = new BackgroundElement();
        skyReflection2.setBackgroundWidth(displayWidth);
        skyReflection2.setBackgroundHeight((65*(displayHeight/100)));
        skyReflection2.setXPos(0);
        skyReflection2.setYPos((35*(displayHeight/100)));
        skyReflection2.setStartPosX(skyReflection2.getXPos());
        skyReflection2.setStartPosY(skyReflection2.getYPos());

        bushes = new BackgroundElement();
        bushes.setBackgroundWidth(displayWidth);
        bushes.setBackgroundHeight((5*(displayHeight/100)));
        bushes.setXPos(0);
        bushes.setYPos((35*(displayHeight/100)-bushes.getBackgroundHeight()));
        bushes.setStartPosX(bushes.getXPos());
        bushes.setStartPosY(bushes.getYPos());

        water = new BackgroundElement();
        water.setBackgroundWidth(displayWidth);
        water.setBackgroundHeight((65*(displayHeight/100)));
        water.setXPos(0);
        water.setYPos((35*(displayHeight/100)));
        water.setStartPosX(water.getXPos());
        water.setStartPosY(water.getYPos());


        Bitmap origSkyBmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.cloudover);
        skyBmp = Bitmap.createScaledBitmap(origSkyBmp, (int) sky1.getBackgroundWidth(), (int) sky1.getBackgroundHeight(), true);

        Bitmap origBushesBmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.bushes);
        bushesBmp = Bitmap.createScaledBitmap(origBushesBmp, (int) bushes.getBackgroundWidth(), (int) bushes.getBackgroundHeight(), true);

        Bitmap origSkyReflectionBmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.cloudunder);
        skyReflectionBmp = Bitmap.createScaledBitmap(origSkyReflectionBmp, (int) skyReflection2.getBackgroundWidth(), (int) skyReflection2.getBackgroundHeight(), true);

        Bitmap origRoadBmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.water);
        waterBmp = Bitmap.createScaledBitmap(origRoadBmp, (int) water.getBackgroundWidth(), (int) water.getBackgroundHeight(), true);








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

        backgroundThread = new GameThread(backgroundHolder, this);
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
        canvas.drawBitmap(waterBmp, water.getXPos(), water.getYPos(), null);
       canvas.drawBitmap(bushesBmp, bushes.getXPos(), bushes.getYPos(), null);
        canvas.drawBitmap(skyReflectionBmp, skyReflection1.getXPos(), skyReflection1.getYPos(), null);
        canvas.drawBitmap(skyReflectionBmp, skyReflection2.getXPos(), skyReflection2.getYPos(), null);





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

        k1 = Keyframe.ofFloat(0, skyReflection1.getXPos());
        k2 = Keyframe.ofFloat(.5f, 0-displayWidth);
        k3 = Keyframe.ofFloat(.49f, displayWidth);
        k4 = Keyframe.ofFloat(1, 0);

        final PropertyValuesHolder skyReflection1X = PropertyValuesHolder.ofKeyframe("xPos", k1, k2,k3,k4);
        skyReflection1Animator = ObjectAnimator.ofPropertyValuesHolder(skyReflection1, skyReflection1X);
        skyReflection1Animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                skyReflection1.setXPos((Float) skyReflection1Animator.getAnimatedValue());

            }
        });

        k1 = Keyframe.ofFloat(0, displayWidth);
        k2 = Keyframe.ofFloat(1f, 0-displayWidth);


        final PropertyValuesHolder skyReflection2X = PropertyValuesHolder.ofKeyframe("xPos", k1, k2);
        skyReflection2Animator = ObjectAnimator.ofPropertyValuesHolder(skyReflection2, skyReflection2X);
        skyReflection2Animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                skyReflection2.setXPos((Float) skyReflection2Animator.getAnimatedValue());

            }
        });

        skySet=new AnimatorSet();
        skySet.play(sky1Animator)
                .with(sky2Animator)
                .with(skyReflection1Animator)
                .with(skyReflection2Animator);
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











