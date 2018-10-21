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

public class FarmBackSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder backgroundHolder;
    GameThread backgroundThread;
    Paint rectColor = new Paint();
    Context context;

    Paint rectPaint;
    Context myContext;

    ValueAnimator sky1Animator;
    ValueAnimator sky2Animator;
    AnimatorSet skySet;
    Boolean endSkyLoop = false;

    BackgroundElement road;
    BackgroundElement soil;
    BackgroundElement sky1;
    BackgroundElement sky2;
    BackgroundElement farmHouse;

    Bitmap roadBmp;
    Bitmap skyBmp;
    Bitmap farmHouseBmp;
    Bitmap soilBmp;


    float xPos, yPos;
    float startOfScreen, endOfScreen;
    float displayHeight, displayWidth;
    float lenghtToMoveY, lenghtToMoveX;


    public FarmBackSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;


        backgroundHolder = getHolder();
        backgroundHolder.addCallback(this);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;
        endOfScreen = displayWidth + 900;
        startOfScreen = -500;

        farmHouse = new BackgroundElement();
        farmHouse.setBackgroundWidth(displayWidth);
        farmHouse.setBackgroundHeight((65*(displayHeight/100)));
        farmHouse.setXPos(0);
        farmHouse.setYPos(0);
        farmHouse.setStartPosX(farmHouse.getXPos());
        farmHouse.setStartPosY(farmHouse.getYPos());

        sky1 = new BackgroundElement();
        sky1.setBackgroundWidth(displayWidth);
        sky1.setBackgroundHeight((65*(displayHeight/100)));
        sky1.setXPos(0);
        sky1.setYPos(0);
        sky1.setStartPosX(sky1.getXPos());
        sky1.setStartPosY(sky1.getYPos());

        sky2 = new BackgroundElement();
        sky2.setBackgroundWidth(displayWidth);
        sky2.setBackgroundHeight((65*(displayHeight/100)));
        sky2.setXPos(0);
        sky2.setYPos(0);
        sky2.setStartPosX(sky2.getXPos());
        sky2.setStartPosY(sky2.getYPos());

        road = new BackgroundElement();
        road.setBackgroundWidth(displayWidth);
        road.setBackgroundHeight((10*(displayHeight/100)));
        road.setXPos(0);
        road.setYPos((65*(displayHeight/100)));
        road.setStartPosX(road.getXPos());
        road.setStartPosY(road.getYPos());


        soil = new BackgroundElement();
        soil.setBackgroundWidth(displayWidth);
        soil.setBackgroundHeight((25*(displayHeight/100)));
        soil.setXPos(0);
        soil.setYPos((75*(displayHeight/100)));
        soil.setStartPosX(farmHouse.getXPos());
        soil.setStartPosY(farmHouse.getYPos());

        Bitmap origSkyBmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.townsky);
        skyBmp = Bitmap.createScaledBitmap(origSkyBmp, (int) sky1.getBackgroundWidth(), (int) sky1.getBackgroundHeight(), true);

        Bitmap origHouseBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.farmhouses);
        farmHouseBmp = Bitmap.createScaledBitmap(origHouseBmp, (int) farmHouse.getBackgroundWidth(), (int) farmHouse.getBackgroundHeight(), true);

        Bitmap origRoadBmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.farmroad);
        roadBmp = Bitmap.createScaledBitmap(origRoadBmp, (int) road.getBackgroundWidth(), (int) road.getBackgroundHeight(), true);

        Bitmap origForgroundBmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.soil);
        soilBmp = Bitmap.createScaledBitmap(origForgroundBmp, (int) soil.getBackgroundWidth(), (int) soil.getBackgroundHeight(), true);


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
            canvas.drawBitmap(farmHouseBmp, farmHouse.getXPos(), farmHouse.getYPos(), null);
            canvas.drawBitmap(roadBmp, road.getXPos(), road.getYPos(), null);
        canvas.drawBitmap(soilBmp, soil.getXPos(), soil.getYPos(), null);





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





