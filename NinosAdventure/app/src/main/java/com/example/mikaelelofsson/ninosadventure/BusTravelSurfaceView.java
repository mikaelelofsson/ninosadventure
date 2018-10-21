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
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;


/**
 * Created by Mikael Elofsson on 2017-02-16.
 */

public class BusTravelSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder backgroundHolder;
    GameThread backgroundThread;
    Paint rectColor = new Paint();
    Context context;

    Paint rectPaint;
    Context myContext;

    AnimatorSet skySet;
    BackgroundElement road;
    BackgroundElement sky1;
    BackgroundElement sky2;

    FocusVehicle bus;
    Boolean endSkyLoop = false;

    ObjectAnimator sky1Animator;
    ObjectAnimator sky2Animator;
    Boolean toggleDoorBmp = true;

    Bitmap roadBmp;
    Bitmap skyBmp;
    Bitmap housesBmp;
    Bitmap foregroundBmp;
    Bitmap townCloseUpBmp;

    float xPos, yPos;
    float startOfScreen, endOfScreen;
    float displayHeight, displayWidth;

    StaticVariables sc;

    /*Vägen rör sig hela tiden från höger till vänster i bild en jämn hastighet  (x);
    *Bussen är placerad på vägen och hjulen rullar, hastigheten på hjulens rullning står
    * i relation till hastigheten som vägen rör sig på.
    *
    *Med ett slumartat intervall rör sig olika sorters föremål, från början tänkt att vara kossor.
    * När de befinner sig med en bestämd längd från bussen så början vägen att bromsa in för att
    * helt stå still precis innan det främmande föremålet befinner sig alldeles framför bussen.
    *
    * När föremålet på vägen försvinner, alltså lämnar sin y-position, så accelererar vägen ifrån
    * sin nuvarande hastighet (kan också vara 0 om vägen inte rör sig) till sin ursprungliga hastighet.
    * Vi måste därför kontinuerligt läsa av främmande föremål på vägen och se om de befinner sig frsmför
    * bussen eller inte.
    *
    *
    * busPos = busposY;
    * object = object;
    * int distanceWhenToSLowDown = 100;
    *
    * checkBusStatus ()
    * if (objectPosY - busPosY < distanceWhenToSlowDown) {
    * slowDownRoad();
    *
    * slowDownRoad () {
    * busVelocity -= busVelocity * -10%;
    * }
    *
    * accelerateRosd ()
    *
    *
    * animateRoad ()
    *
    *
    *
    */




    public BusTravelSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        sc = new StaticVariables();
        backgroundHolder = getHolder();
        backgroundHolder.addCallback(this);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
            displayWidth = size.x;
            displayHeight = size.y;
        } else {
            display.getSize(size);
            displayWidth = size.x;
            displayHeight = size.y;// correct for devices with hardware navigation buttons
        }


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
        road.setBackgroundHeight((22*(displayHeight/100)));
        road.setXPos(0);
        road.setYPos((65*(displayHeight/100)));
        road.setStartPosX(road.getXPos());
        road.setStartPosY(road.getYPos());





        bus = new FocusVehicle();
        bus.setNewPositions( (15*displayWidth/100) ,
                ( (float)63 * displayHeight/100)
        );
        bus.setWidth(40*displayWidth/100);
        bus.setHeight((23 * bus.getWidth() / 100));

        bus.setWheelPos(bus.leftWheel,(float)20.5,69);
        bus.setWheelSize(bus.leftWheel,9);
        bus.setWheelPos(bus.rightWheel,(float)71.5,69);
        bus.setWheelSize(bus.rightWheel, 9);
        bus.setDoorPos(bus.leftDoor, (float)84,18);
        bus.setDoorSize(bus.leftDoor,(float)5.3,313);
        bus.setDoorPos(bus.rightDoor, (float)89.3,(float)18);
        bus.setDoorSize(bus.rightDoor,(float)5.3,313);



        StaticVariables sc = new StaticVariables();
        sc.setLeftBusDoorXPs(bus.leftDoor.getXPos());
        sc.setRightBusDoorXPs(bus.rightDoor.getXPos() + bus.rightDoor.getWidth());
        sc.setBottomOfDoor(bus.leftDoor.getYPos() + bus.leftDoor.getHeight());
        sc.setTopOfDoor(bus.leftDoor.getYPos());




        skyBmp = createBitmap(sky1.getBackgroundWidth(),sky1.getBackgroundHeight(), R.drawable.townsky);
        roadBmp = createBitmap(road.getBackgroundWidth(),road.getBackgroundHeight(), R.drawable.townroad);



        bus.setBitmap(this, bus.getWidth(), bus.getHeight(), R.drawable.nybuss_scaled);
        bus.leftWheel.setBitmap(this, bus.leftWheel.getWidth(), bus.leftWheel.getHeight(), R.drawable.buswheel);
        bus.rightWheel.setBitmap(this, bus.rightWheel.getWidth(), bus.rightWheel.getHeight(), R.drawable.buswheel);
        bus.leftDoor.setBitmap(this, bus.leftDoor.getWidth(), bus.leftDoor.getHeight(), R.drawable.door1);
        bus.rightDoor.setBitmap(this, bus.rightDoor.getWidth(), bus.rightDoor.getHeight(), R.drawable.door2);



        this.myContext = context;
        rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);


    }


    public void exitAndContinue () {

       backgroundThread.setRunning(false);

    }

    public Bitmap createBitmap (float width, float height, int drawable) {
        Bitmap origBmp = BitmapFactory.decodeResource(this.getResources(), drawable);
        return Bitmap.createScaledBitmap(origBmp, (int)width, (int)height, true);
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

        road = null;
        sky1 = null;
        sky2 = null;
        bus = null;




    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);


            canvas.drawBitmap(skyBmp, sky1.getXPos(), sky1.getYPos(), null);
            canvas.drawBitmap(skyBmp, sky2.getXPos(), sky2.getYPos(), null);
            canvas.drawBitmap(roadBmp, road.getXPos(), road.getYPos(), null);



                Matrix matrixLeftWheel = bus.animateWheel(bus.leftWheel);
                Matrix matrixRightWheel = bus.animateWheel(bus.rightWheel);
                //bus.animatePosition((float)29*displayHeight/100,(float)31*displayHeight/100);

                bus.setWheelPos(bus.leftWheel, (float) 20.5, 69);
                bus.setWheelPos(bus.rightWheel, (float) 71.5, 69);
                bus.setDoorPos(bus.leftDoor, (float) 84, 18);
                bus.setDoorPos(bus.rightDoor, (float) 89.3, (float) 18);

                canvas.drawBitmap(bus.getBitmap(), bus.getXPos(), bus.getYPos(), null);
                canvas.drawBitmap(bus.leftWheel.getBitmap(), matrixLeftWheel, null);
                canvas.drawBitmap(bus.rightWheel.getBitmap(), matrixRightWheel, null);
                canvas.drawBitmap(bus.leftDoor.getBitmap(), bus.leftDoor.getXPos(), bus.leftDoor.getYPos(), bus.leftDoor.getDoorPaint1());
                canvas.drawBitmap(bus.rightDoor.getBitmap(), bus.rightDoor.getXPos(), bus.rightDoor.getYPos(), bus.rightDoor.getDoorPaint1());

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {


        int action = MotionEventCompat.getActionMasked(motionEvent);


        switch (action) {

            case MotionEvent.ACTION_DOWN:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();


            case MotionEvent.ACTION_MOVE:



                break;

            case MotionEvent.ACTION_UP: {



                break;
            }
        }
        return true;

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











