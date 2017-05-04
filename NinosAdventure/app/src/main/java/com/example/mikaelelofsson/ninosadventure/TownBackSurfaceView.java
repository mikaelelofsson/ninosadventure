package com.example.mikaelelofsson.ninosadventure;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;


/**
 * Created by Mikael Elofsson on 2017-02-16.
 */

public class TownBackSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder backgroundHolder;
    GameThread backgroundThread;
    Paint rectColor = new Paint();
    Context context;

    Paint rectPaint;
    Context myContext;

    AnimatorSet skySet;
    BackgroundElement road;
    BackgroundElement foreground;
    BackgroundElement sky1;
    BackgroundElement sky2;
    BackgroundElement houses;
    BackgroundElement townCloseUp1;
    BackgroundElement townCloseUp2;
    FocusVehicle busCloseUp;
    Character nino;
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


    public TownBackSurfaceView(Context context, AttributeSet attrs) {
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


        houses = new BackgroundElement();
        houses.setBackgroundWidth(displayWidth);
        houses.setBackgroundHeight((65*(displayHeight/100)));
        houses.setXPos(0);
        houses.setYPos(0);
        houses.setStartPosX(houses.getXPos());
        houses.setStartPosY(houses.getYPos());

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


        foreground = new BackgroundElement();
        foreground.setBackgroundWidth(displayWidth);
        foreground.setBackgroundHeight((13*(displayHeight/100)));
        foreground.setXPos(0);
        foreground.setYPos((87*(displayHeight/100)));
        foreground.setStartPosX(houses.getXPos());
        foreground.setStartPosY(houses.getYPos());

        townCloseUp1 = new BackgroundElement();
        townCloseUp1.setBackgroundWidth(displayWidth);
        townCloseUp1.setBackgroundHeight(130 * displayHeight / 100);
        townCloseUp1.setXPos(0);
        townCloseUp1.setYPos(0);
        townCloseUp1.setStartPosX(houses.getXPos());
        townCloseUp1.setStartPosY(houses.getYPos());

        townCloseUp2 = new BackgroundElement();
        townCloseUp2.setBackgroundWidth(displayWidth);
        townCloseUp2.setBackgroundHeight(displayHeight);
        townCloseUp2.setXPos(townCloseUp1.getXPos() + townCloseUp1.getBackgroundWidth());
        townCloseUp2.setYPos(0);
        townCloseUp2.setStartPosX(houses.getXPos());
        townCloseUp2.setStartPosY(houses.getYPos());


        busCloseUp = new FocusVehicle();
        busCloseUp.setNewPositions( (-105*displayWidth/100) ,
                ( (float)7 * displayHeight/100)
        );
        busCloseUp.setWidth(200*displayWidth/100);
        busCloseUp.setHeight((23 * busCloseUp.getWidth() / 100));

        busCloseUp.setWheelPos(busCloseUp.leftWheel,(float)20.5,69);
        busCloseUp.setWheelSize(busCloseUp.leftWheel,9);
        busCloseUp.setWheelPos(busCloseUp.rightWheel,(float)71.5,69);
        busCloseUp.setWheelSize(busCloseUp.rightWheel, 9);
        busCloseUp.setDoorPos(busCloseUp.leftDoor, (float)84,18);
        busCloseUp.setDoorSize(busCloseUp.leftDoor,(float)5.3,313);
        busCloseUp.setDoorPos(busCloseUp.rightDoor, (float)89.3,(float)18);
        busCloseUp.setDoorSize(busCloseUp.rightDoor,(float)5.3,313);



        StaticVariables sc = new StaticVariables();
        sc.setLeftBusDoorXPs(busCloseUp.leftDoor.getXPos());
        sc.setRightBusDoorXPs(busCloseUp.rightDoor.getXPos() + busCloseUp.rightDoor.getWidth());
        sc.setBottomOfDoor(busCloseUp.leftDoor.getYPos() + busCloseUp.leftDoor.getHeight());
        sc.setTopOfDoor(busCloseUp.leftDoor.getYPos());




        skyBmp = createBitmap(sky1.getBackgroundWidth(),sky1.getBackgroundHeight(), R.drawable.townsky);
        housesBmp = createBitmap(houses.getBackgroundWidth(),houses.getBackgroundHeight(), R.drawable.townhouses);
        roadBmp = createBitmap(road.getBackgroundWidth(),road.getBackgroundHeight(), R.drawable.townroad);
        foregroundBmp = createBitmap(foreground.getBackgroundWidth(),foreground.getBackgroundHeight(), R.drawable.townforeground);
        townCloseUpBmp = createBitmap(townCloseUp1.getBackgroundWidth(),townCloseUp1.getBackgroundHeight(), R.drawable.towncloseup);


        busCloseUp.setBitmap(this,busCloseUp.getWidth(), busCloseUp.getHeight(), R.drawable.nybuss_scaled);
        busCloseUp.leftWheel.setBitmap(this,busCloseUp.leftWheel.getWidth(), busCloseUp.leftWheel.getHeight(), R.drawable.buswheel);
        busCloseUp.rightWheel.setBitmap(this,busCloseUp.rightWheel.getWidth(), busCloseUp.rightWheel.getHeight(), R.drawable.buswheel);
        busCloseUp.leftDoor.setBitmap(this,busCloseUp.leftDoor.getWidth(), busCloseUp.leftDoor.getHeight(), R.drawable.door1);
        busCloseUp.rightDoor.setBitmap(this,busCloseUp.rightDoor.getWidth(), busCloseUp.rightDoor.getHeight(), R.drawable.door2);



        this.myContext = context;
        rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);


    }

    public void setUpNino () {

        nino = new Character();
        nino.setHeight((int) (33 * displayHeight / 100));
        nino.setWidth(((60 * nino.getHeight() / 100)));

        nino.setNewPositions(sc.ninoInsideBusXPos,sc.ninoInsideBusYPos);
        nino.setStartPosX(nino.getXPos());
        nino.setStartPosY(nino.getYPos());

        nino.setArmPos(nino.leftArm,nino.LEFTARM_XFACTOR,nino.LEFTARM_YFACTOR);
        nino.setArmSize(nino.leftArm,70,70);
        nino.setArmPos(nino.rightArm,nino.RIGHTARM_XFACTOR,nino.RIGHTARM_YFACTOR);
        nino.setArmSize(nino.rightArm,70,70);

        nino.setEyePos(nino.leftEye,nino.LEFTEYE_XFACTOR,nino.LEFTEYE_YFACTOR);
        nino.setEyeSize(nino.leftEye,(float)100,20);
        nino.setEyePos(nino.rightEye,nino.RIGHTEYE_XFACTOR, nino.RIGHTEYE_YFACTOR);
        nino.setEyeSize(nino.rightEye,(float)100,20);

        nino.setBitmap(this,nino.getWidth(),nino.getHeight(), R.drawable.nino_body_scaled);
        nino.leftArm.setBitmap(this, nino.leftArm.getWidth(), nino.leftArm.getHeight(), R.drawable.nino_leftarm_scaled);
        nino.rightArm.setBitmap(this, nino.rightArm.getWidth(), nino.rightArm.getHeight(), R.drawable.nino_rightarm_scaled);
        nino.leftEye.setBitmap(this, nino.leftEye.getWidth(), nino.leftEye.getHeight(), R.drawable.eye_scaled);
        nino.rightEye.setBitmap(this, nino.rightEye.getWidth(), nino.rightEye.getHeight(), R.drawable.eye_scaled);
        sc.ninoOnTheBus = true;
        sc.removeCharacter = true;
    }

    public void exitAndContinue () {
        /*TownSurfaceView.gameThread.setRunning(false);
        boolean retry = true;


        while(retry)
        {
            try
            {
                TownSurfaceView.gameThread.join();
                retry = false;

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

       backgroundThread.setRunning(false);
        boolean retry2 = true;


        while(retry2)
        {
            try
            {
               backgroundThread.join();
                retry2 = false;

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

/*
        Intent intent = new Intent(myContext, TrainStationActivity.class);
        myContext.startActivity(intent);
        */
    }

    public Bitmap createBitmap (float width, float height, int drawable) {
        Bitmap origBmp = BitmapFactory.decodeResource(this.getResources(), drawable);
        return Bitmap.createScaledBitmap(origBmp, (int)width, (int)height, true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Toast.makeText(myContext, "Bakgrundstråd Created", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(myContext, "Bakgrundstråd Skroted", Toast.LENGTH_SHORT).show();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);

        if(!TownSurfaceView.closeUpView) {
            canvas.drawBitmap(skyBmp, sky1.getXPos(), sky1.getYPos(), null);
            canvas.drawBitmap(skyBmp, sky2.getXPos(), sky2.getYPos(), null);
            canvas.drawBitmap(housesBmp, houses.getXPos(), houses.getYPos(), null);
            canvas.drawBitmap(roadBmp, road.getXPos(), road.getYPos(), null);
            canvas.drawBitmap(foregroundBmp, foreground.getXPos(), foreground.getYPos(), null);
        }
        else if (!sc.timeForTakeOff) {

            canvas.drawBitmap(townCloseUpBmp, townCloseUp1.getXPos(), townCloseUp1.getYPos(), null);
            canvas.drawBitmap(busCloseUp.getBitmap(), busCloseUp.getXPos(), busCloseUp.getYPos(), null);
            canvas.drawBitmap(busCloseUp.leftWheel.getBitmap(), busCloseUp.leftWheel.getXPos(), busCloseUp.leftWheel.getYPos(), null);
            canvas.drawBitmap(busCloseUp.rightWheel.getBitmap(), busCloseUp.rightWheel.getXPos(), busCloseUp.rightWheel.getYPos(), null);

            if (sc.ninoOnTheBus) {
                canvas.drawBitmap(nino.leftArm.getBitmap(), nino.leftArm.getXPos(), nino.leftArm.getYPos(), null);
                canvas.drawBitmap(nino.rightArm.getBitmap(), nino.rightArm.getXPos(), nino.rightArm.getYPos(), null);
                canvas.drawBitmap(nino.getBitmap(), nino.getXPos(), nino.getYPos(), null);
                canvas.drawBitmap(nino.leftEye.getBitmap(), nino.leftEye.getXPos(), nino.leftEye.getYPos(), null);
                canvas.drawBitmap(nino.rightEye.getBitmap(), nino.rightEye.getXPos(), nino.rightEye.getYPos(), null);
            }

            if(!sc.doorsBeenPushed && !sc.ninoOnTheBus ) {

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (toggleDoorBmp) {
                    canvas.drawBitmap(busCloseUp.leftDoor.getBitmap(), busCloseUp.leftDoor.getXPos(), busCloseUp.leftDoor.getYPos(), busCloseUp.leftDoor.getDoorPaint1());
                    canvas.drawBitmap(busCloseUp.rightDoor.getBitmap(), busCloseUp.rightDoor.getXPos(), busCloseUp.rightDoor.getYPos(), busCloseUp.rightDoor.getDoorPaint1());
                    toggleDoorBmp = !toggleDoorBmp;
                } else {
                    canvas.drawBitmap(busCloseUp.leftDoor.getBitmap(), busCloseUp.leftDoor.getXPos(), busCloseUp.leftDoor.getYPos(), busCloseUp.leftDoor.getDoorPaint2());
                    canvas.drawBitmap(busCloseUp.rightDoor.getBitmap(), busCloseUp.rightDoor.getXPos(), busCloseUp.rightDoor.getYPos(), busCloseUp.rightDoor.getDoorPaint2());
                    toggleDoorBmp = !toggleDoorBmp;
                }

            }

            else {

                canvas.drawBitmap(busCloseUp.leftDoor.getBitmap(), busCloseUp.leftDoor.getXPos(), busCloseUp.leftDoor.getYPos(), busCloseUp.leftDoor.getDoorPaint1());
                canvas.drawBitmap(busCloseUp.rightDoor.getBitmap(), busCloseUp.rightDoor.getXPos(), busCloseUp.rightDoor.getYPos(), busCloseUp.rightDoor.getDoorPaint1());
            }



            endSkyLoop=true;
           // updateBackgroundPositions();

           // canvas.drawBitmap(townCloseUpBmp, townCloseUp2.getXPos(), townCloseUp2.getYPos(), null);
        }
        else {

            nino.setArmPos(nino.leftArm, nino.LEFTARM_XFACTOR, nino.LEFTARM_YFACTOR);
            nino.setArmPos(nino.rightArm, nino.RIGHTARM_XFACTOR, nino.RIGHTARM_YFACTOR);
            nino.setEyePos(nino.leftEye, nino.LEFTEYE_XFACTOR, nino.LEFTEYE_YFACTOR);
            nino.setEyePos(nino.rightEye, nino.RIGHTEYE_XFACTOR, nino.RIGHTEYE_YFACTOR);

                Matrix matrixLeftWheel = busCloseUp.animateWheel(busCloseUp.leftWheel);
                Matrix matrixRightWheel = busCloseUp.animateWheel(busCloseUp.rightWheel);
                //busCloseUp.animatePosition((float)29*displayHeight/100,(float)31*displayHeight/100);

                canvas.drawBitmap(townCloseUpBmp, townCloseUp1.getXPos(), townCloseUp1.getYPos(), null);

                busCloseUp.setWheelPos(busCloseUp.leftWheel, (float) 20.5, 69);
                busCloseUp.setWheelPos(busCloseUp.rightWheel, (float) 71.5, 69);
                busCloseUp.setDoorPos(busCloseUp.leftDoor, (float) 84, 18);
                busCloseUp.setDoorPos(busCloseUp.rightDoor, (float) 89.3, (float) 18);

                canvas.drawBitmap(busCloseUp.getBitmap(), busCloseUp.getXPos(), busCloseUp.getYPos(), null);
                canvas.drawBitmap(busCloseUp.leftWheel.getBitmap(), matrixLeftWheel, null);
                canvas.drawBitmap(busCloseUp.rightWheel.getBitmap(), matrixRightWheel, null);

                canvas.drawBitmap(nino.leftArm.getBitmap(), nino.leftArm.getXPos(), nino.leftArm.getYPos(), null);
                canvas.drawBitmap(nino.rightArm.getBitmap(), nino.rightArm.getXPos(), nino.rightArm.getYPos(), null);
                canvas.drawBitmap(nino.getBitmap(), nino.getXPos(), nino.getYPos(), null);
                canvas.drawBitmap(nino.leftEye.getBitmap(), nino.leftEye.getXPos(), nino.leftEye.getYPos(), null);
                canvas.drawBitmap(nino.rightEye.getBitmap(), nino.rightEye.getXPos(), nino.rightEye.getYPos(), null);

                canvas.drawBitmap(busCloseUp.leftDoor.getBitmap(), busCloseUp.leftDoor.getXPos(), busCloseUp.leftDoor.getYPos(), busCloseUp.leftDoor.getDoorPaint1());
                canvas.drawBitmap(busCloseUp.rightDoor.getBitmap(), busCloseUp.rightDoor.getXPos(), busCloseUp.rightDoor.getYPos(), busCloseUp.rightDoor.getDoorPaint1());
        }



    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {


        int action = MotionEventCompat.getActionMasked(motionEvent);


        switch (action) {

            case MotionEvent.ACTION_DOWN:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();

                if (!busCloseUp.getDoorsAreMoving())
                {
                    if (!sc.doorsBeenPushed && !sc.ninoOnTheBus) {
                        if ((xPos > busCloseUp.leftDoor.getXPos())
                                && (xPos < busCloseUp.rightDoor.getXPos() + busCloseUp.rightDoor.getWidth())
                                && (yPos > busCloseUp.leftDoor.getYPos())
                                && (yPos < busCloseUp.leftDoor.getYPos() + busCloseUp.leftDoor.getHeight())
                                ) {
                            ObjectAnimator leftDoorAnim = busCloseUp.leftDoor.openDoor("xPos", busCloseUp.leftDoor.getXPos(), busCloseUp.leftDoor.getXPos() - busCloseUp.leftDoor.getWidth(), 2000, 0);
                            ObjectAnimator rightDoorAnim = busCloseUp.rightDoor.openDoor("xPos", busCloseUp.rightDoor.getXPos(), busCloseUp.rightDoor.getXPos() + busCloseUp.rightDoor.getWidth(), 2000, 0);
                            AnimatorSet doorSet = new AnimatorSet();
                            doorSet.addListener(openDoorListener);
                            doorSet.play(leftDoorAnim)
                                    .with(rightDoorAnim);
                            sc.doorsBeenPushed = true;
                            doorSet.start();
                        }
                    } else if (sc.doorsBeenPushed) {
                        if (((xPos > busCloseUp.leftDoor.getXPos())
                                && (xPos < busCloseUp.leftDoor.getXPos() + busCloseUp.leftDoor.getWidth())
                                || (xPos > busCloseUp.rightDoor.getXPos())
                                && (xPos < busCloseUp.rightDoor.getXPos() + busCloseUp.leftDoor.getWidth()))
                                && (yPos > busCloseUp.leftDoor.getYPos())
                                && (yPos < busCloseUp.leftDoor.getYPos() + busCloseUp.leftDoor.getHeight())
                                ) {

                            if (sc.minimized) {
                                setUpNino();
                            }

                            ObjectAnimator leftDoorAnim = busCloseUp.leftDoor.openDoor("xPos", busCloseUp.leftDoor.getXPos(), busCloseUp.leftDoor.getXPos() + busCloseUp.leftDoor.getWidth(), 2000, 0);
                            ObjectAnimator rightDoorAnim = busCloseUp.rightDoor.openDoor("xPos", busCloseUp.rightDoor.getXPos(), busCloseUp.rightDoor.getXPos() - busCloseUp.rightDoor.getWidth(), 2000, 0);
                            AnimatorSet doorSet = new AnimatorSet();
                            doorSet.addListener(closeDoorListener);
                            doorSet.play(leftDoorAnim)
                                    .with(rightDoorAnim);
                            doorSet.start();
                        }
                    }
        }





            case MotionEvent.ACTION_MOVE:



                break;

            case MotionEvent.ACTION_UP: {



                break;
            }
        }
        return true;

    }

    void animateBusAway (float destXPos, long duration) {

        ObjectAnimator characterAnimator = ObjectAnimator.ofFloat(nino, "xPos", nino.getXPos(), destXPos);
        characterAnimator.setDuration(duration);
        characterAnimator.setInterpolator(new AccelerateInterpolator());
        characterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()

        {
            @Override
            public void onAnimationUpdate (ValueAnimator animation){
                nino.setXPos((Float) animation.getAnimatedValue());


            }
        });
        AnimatorSet animSet = new AnimatorSet();
        animSet.addListener(busListener);
        animSet.play(busCloseUp.animateBusXPos(displayWidth,6000))
                .with(characterAnimator);
        animSet.start();
    }

    void updateBackgroundPositions() {

        if(townCloseUp1.getXPos() > 0) {
            townCloseUp1.setXPos(townCloseUp1.getXPos() - 10);
            townCloseUp2.setXPos(townCloseUp1.getXPos() - townCloseUp1.getBackgroundWidth());
        }

        else if(townCloseUp1.getXPos() <0 && townCloseUp1.getXPos() > 0-displayWidth) {
            townCloseUp1.setXPos(townCloseUp1.getXPos() - 10);
            townCloseUp2.setXPos(townCloseUp1.getXPos() + townCloseUp1.getBackgroundWidth());
        }
        else {
            townCloseUp1.setXPos(displayWidth);
            townCloseUp2.setXPos(townCloseUp1.getXPos() - townCloseUp1.getBackgroundWidth());
        }

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

    ObjectAnimator.AnimatorListener busListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {


        }

        @Override
        public void onAnimationEnd(Animator animation) {
            exitAndContinue();
        }




        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };


    ObjectAnimator.AnimatorListener closeDoorListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            busCloseUp.setDoorsAreMoving(true);

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            sc.doorsBeenPushed = false;
            busCloseUp.setDoorsAreMoving(false);





            if (sc.ninoOnTheBus) {
                animateBusAway(nino.getXPos() + (displayWidth - busCloseUp.getXPos()), 6000);

                sc.timeForTakeOff = true;
            }


        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    ObjectAnimator.AnimatorListener openDoorListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            busCloseUp.setDoorsAreMoving(true);

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            busCloseUp.setDoorsAreMoving(false);


        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

}











