/*package com.example.mikaelelofsson.ninosadventure;

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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import static com.example.mikaelelofsson.ninosadventure.AirportActivity.animationSurfaceView;

/**
 * Created by Mikael Elofsson on 2017-02-16.
 */
/*
public class AirportBackSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder backgroundHolder;
    GameThread backgroundThread;
    Paint rectColor = new Paint();
    Context context;

    Paint rectPaint;
    Context myContext;
    Boolean timeToTravel=false;



    ValueAnimator road1Animator;
    ValueAnimator road2Animator;
    ValueAnimator sky1Animator;
    ValueAnimator sky2Animator;
    AnimatorSet roadSet;
    AnimatorSet skySet;

    BackgroundElement road1;
    BackgroundElement road2;
    BackgroundElement sky1;
    BackgroundElement sky2;
    BackgroundElement houses;

    Bitmap road1Bmp;
    Bitmap road2Bmp;
    Bitmap housesBmp;
    Bitmap sky1Bmp;
    Bitmap sky2Bmp;
    Long roadSpeed =(long)3000;
    Long skySpeed = (long)6000;
    int takeOffCountDown = 0;

    float xPos, yPos;
    float startOfScreen, endOfScreen;
    float displayHeight, displayWidth;
    float lenghtToMoveY, lenghtToMoveX;


    public AirportBackSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;


        backgroundHolder = getHolder();
        backgroundHolder.addCallback(this);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;
        endOfScreen = displayWidth + 900;
        startOfScreen = -500;

        houses = new BackgroundElement();
        houses.setBackgroundWidth(displayWidth);
        houses.setBackgroundHeight((66*(displayHeight/100)));
        houses.setXPos(0);
        houses.setYPos(0);
        houses.setStartPosX(houses.getXPos());
        houses.setStartPosY(houses.getYPos());

        road1 = new BackgroundElement();
        road1.setBackgroundWidth(displayWidth);
        road1.setBackgroundHeight((34*(displayHeight/100)));
        road1.setXPos(0);
        road1.setYPos((displayHeight-road1.getBackgroundHeight()));
        road1.setStartPosX(road1.getXPos());
        road1.setStartPosY(road1.getYPos());

        road2 = new BackgroundElement();
        road2.setBackgroundWidth((displayWidth));
        road2.setBackgroundHeight((34*(displayHeight/100)));
        road2.setXPos(displayWidth);
        road2.setYPos((displayHeight-road2.getBackgroundHeight()));
        road2.setStartPosX(road2.getXPos());
        road2.setStartPosY(road2.getYPos());


        sky1 = new BackgroundElement();
        sky1.setBackgroundWidth(displayWidth);
        sky1.setBackgroundHeight((66*(displayHeight/100)));
        sky1.setXPos(0);
        sky1.setYPos(0);
        sky1.setStartPosX(houses.getXPos());
        sky1.setStartPosY(houses.getYPos());

        sky2 = new BackgroundElement();
        sky2.setBackgroundWidth(displayWidth);
        sky2.setBackgroundHeight((66*(displayHeight/100)));
        sky2.setXPos(0);
        sky2.setYPos(0);
        sky2.setStartPosX(houses.getXPos());
        sky2.setStartPosY(houses.getYPos());


        Bitmap origRoad1Bmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.road1);
        road1Bmp = Bitmap.createScaledBitmap(origRoad1Bmp, (int)road1.getBackgroundWidth(), (int)road1.getBackgroundHeight(), true);

        Bitmap origRoad2Bmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.road2);
        road2Bmp = Bitmap.createScaledBitmap(origRoad2Bmp, (int)road2.getBackgroundWidth(), (int)road2.getBackgroundHeight(), true);

        Bitmap origSky1Bmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.sky1);
        sky1Bmp = Bitmap.createScaledBitmap(origSky1Bmp, (int)sky1.getBackgroundWidth(), (int)sky1.getBackgroundHeight(), true);

        Bitmap origSky2Bmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.sky2);
        sky2Bmp = Bitmap.createScaledBitmap(origSky2Bmp, (int)sky2.getBackgroundWidth(), (int)sky2.getBackgroundHeight(), true);

        Bitmap origHouseBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.terminal);
        housesBmp = Bitmap.createScaledBitmap(origHouseBmp, (int)houses.getBackgroundWidth(), (int)houses.getBackgroundHeight(), true);


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
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
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

        if(timeToTravel) {
            canvas.drawBitmap(sky1Bmp, sky1.getXPos(), sky1.getYPos(), null);
            canvas.drawBitmap(sky2Bmp, sky2.getXPos(), sky2.getYPos(), null);
            canvas.drawBitmap(housesBmp, houses.getXPos(), houses.getYPos(), null);
            canvas.drawBitmap(road1Bmp, road1.getXPos(), road1.getYPos(), null);
            canvas.drawBitmap(road2Bmp, road2.getXPos(), road2.getYPos(), null);
        }
        canvas.drawBitmap(sky1Bmp, sky1.getXPos(), sky1.getYPos(), null);
        canvas.drawBitmap(housesBmp, houses.getXPos(), houses.getYPos(), null);
        canvas.drawBitmap(road1Bmp, road1.getXPos(), road1.getYPos(), null);




    }





void animateItems ()
{


   final ObjectAnimator housesAnimator = ObjectAnimator.ofFloat(houses,"xPos", houses.getXPos(),0-displayWidth);
    housesAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            houses.setXPos((Float) housesAnimator.getAnimatedValue());
            housesAnimator.setInterpolator(new AccelerateInterpolator());
            housesAnimator.setDuration(10000);

        }
    });

    housesAnimator.start();
}
    void startSkyLoops(long skySpeed, Interpolator interpolator) {

        timeToTravel = true;

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


        skySet = new AnimatorSet();


        skySet.play(sky1Animator)
                .with(sky2Animator);

        skySet.setInterpolator(interpolator);
        skySet.setDuration(skySpeed);
        skySet.addListener(skySetListener);
        skySet.start();




    }
     void startBgLoops(long roadSpeed, Interpolator interpolator) {

         timeToTravel = true;



         Keyframe k1 = Keyframe.ofFloat(0, road1.getXPos());
         Keyframe k2 = Keyframe.ofFloat(.5f, 0-displayWidth);
         Keyframe k3 = Keyframe.ofFloat(.49f, displayWidth);
         Keyframe k4 = Keyframe.ofFloat(1, 0);

        final PropertyValuesHolder hX = PropertyValuesHolder.ofKeyframe("xPos", k1, k2,k3,k4);
       road1Animator = ObjectAnimator.ofPropertyValuesHolder(road1, hX);
        road1Animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                road1.setXPos((Float) road1Animator.getAnimatedValue());

            }
        });


       k1 = Keyframe.ofFloat(0, displayWidth);
        k2 = Keyframe.ofFloat(1f, 0-displayWidth);


        final PropertyValuesHolder road2X = PropertyValuesHolder.ofKeyframe("xPos", k1, k2);
        road2Animator = ObjectAnimator.ofPropertyValuesHolder(road2,road2X);
        road2Animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                road2.setXPos((Float) road2Animator.getAnimatedValue());

            }
        });




        roadSet = new AnimatorSet();

        roadSet.play(road1Animator)
                .with(road2Animator);

        roadSet.setInterpolator(interpolator);
        roadSet.setDuration(roadSpeed);
        roadSet.addListener(road1Listener);
        roadSet.start();


    }



    ObjectAnimator.AnimatorListener road1Listener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {


        }

        @Override
        public void onAnimationEnd(Animator animation) {


            Interpolator interpolator = new LinearInterpolator();
            startBgLoops(roadSpeed,interpolator);

            takeOffCountDown+=1;

            if (roadSpeed >100) {
                if (roadSpeed > 1500) {
                    roadSpeed = roadSpeed - 1500;
                } else if (roadSpeed > 200 && roadSpeed <= 1500) {
                    roadSpeed = roadSpeed - 200;
                } else {
                    roadSpeed = (long) 100;
                }
            }
            if(takeOffCountDown==7)
            {animationSurfaceView.animateItems(true);}

           }




        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    ObjectAnimator.AnimatorListener skySetListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {


        }

        @Override
        public void onAnimationEnd(Animator animation) {

            Interpolator interpolator = new LinearInterpolator();
            startSkyLoops(skySpeed,interpolator);

            if(skySpeed >3000) {
                skySpeed = skySpeed - 3000;
            }
            else if (skySpeed > 400 && skySpeed <=3000){
                skySpeed = skySpeed -400;}
            else {
                skySpeed =(long)200;}
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };


}




*/
