package com.example.mikaelelofsson.ninosadventure;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;

/**
 * Created by Mikael Elofsson on 2017-02-16.
 */
/*
public class FarmSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder farmHolder;
    GameThread gameThread;
    Paint rectColor = new Paint();

    Paint rectPaint;
    Context myContext;

    ArrayList<Vehicle> listOfVehicles = new ArrayList<Vehicle>();
    ArrayList<Vehicle> disabledVehicles = new ArrayList<Vehicle>();

    ValueAnimator ploughAnimator;
    ValueAnimator timberTruckAnimator;
    ValueAnimator farmAnimator;
    ValueAnimator vehicleAnimator;

    Vehicle plough;
    Vehicle timberTruck;
    Vehicle farmTractor;

    Bitmap ploughBmp;
    Bitmap timberTruckBmp;
    Bitmap farmTractorBmp;

    float xPos, yPos;
    float startOfScreen, endOfScreen;
    float displayHeight, displayWidth;
    float lenghtToMoveY, lenghtToMoveX;

    public FarmSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        farmHolder = getHolder();
        farmHolder.addCallback(this);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;
        endOfScreen = displayWidth*2;
        startOfScreen = -displayWidth;

        plough = new Vehicle();
        plough.setWidth((int) (25 * displayWidth / 100));
        plough.setHeight((int)((45 * plough.getWidth() / 100)));
        plough.setXPos((float)(1 * displayWidth / 100));
        plough.setYPos((displayHeight - (110 * plough.getHeight() / 100)));
        plough.setStartPosX(plough.getXPos());
        plough.setStartPosY(plough.getYPos());

        timberTruck = new Vehicle();
        timberTruck.setWidth((int) (42.5 * displayWidth / 100));
        timberTruck.setHeight((int)(26 * timberTruck.getWidth() / 100));
        timberTruck.setXPos((displayWidth/2) - (timberTruck.getWidth()/2));
        timberTruck.setYPos((displayHeight - (110 * timberTruck.getHeight() / 100)));
        timberTruck.setStartPosX(timberTruck.getXPos());
        timberTruck.setStartPosY(timberTruck.getYPos());

        farmTractor = new Vehicle();
        farmTractor.setWidth((int) (25 * displayWidth / 100));
        farmTractor.setHeight((int) (40 * farmTractor.getWidth() / 100));
        farmTractor.setXPos((displayWidth - ((float)(26 * displayWidth / 100))));
        farmTractor.setYPos((displayHeight - (110 * farmTractor.getHeight() / 100)));
        farmTractor.setStartPosX(farmTractor.getXPos());
        farmTractor.setStartPosY(farmTractor.getYPos());



        Bitmap origPloughBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.harrow_scaled);
        ploughBmp = Bitmap.createScaledBitmap(origPloughBmp, (int)plough.getWidth(), (int)plough.getHeight(), true);

        Bitmap origTimberTruckBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.timbertruck_scaled);
        timberTruckBmp = Bitmap.createScaledBitmap(origTimberTruckBmp, (int)timberTruck.getWidth(), (int)timberTruck.getHeight(), true);

        Bitmap origFarmTractorBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.tractor_scaled);
        farmTractorBmp = Bitmap.createScaledBitmap(origFarmTractorBmp, (int)farmTractor.getWidth(), (int)farmTractor.getHeight(), true);

        this.myContext = context;
        rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);

        listOfVehicles.add(plough);
        listOfVehicles.add(timberTruck);
        listOfVehicles.add(farmTractor);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        android.view.ViewGroup.LayoutParams lp = this.getLayoutParams();
        lp.width = (int) displayWidth;
        lp.height = (int) displayHeight;
        this.setLayoutParams(lp);


        gameThread = new GameThread(farmHolder, this);
        gameThread.setRunning(true);
        gameThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setRunning(false);
        boolean retry = true;

        while(retry)
        {
            try
            {
                gameThread.join();
                retry = false;
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void exitAndContinue () {
        gameThread.setRunning(false);
        boolean retry = true;

        while(retry)
        {
            try
            {
                gameThread.join();
                retry = false;
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(myContext, AirportActivity.class);
        myContext.startActivity(intent);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
        canvas.drawRect(0, 90 * displayHeight/100, displayWidth, displayHeight, rectPaint);


        canvas.drawBitmap(timberTruckBmp, timberTruck.getXPos(), timberTruck.getYPos(), null);
        canvas.drawBitmap(farmTractorBmp, farmTractor.getXPos(), farmTractor.getYPos(), null);
        canvas.drawBitmap(ploughBmp, plough.getXPos(), plough.getYPos(), null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {


        int action = MotionEventCompat.getActionMasked(motionEvent);


        switch (action) {

            case MotionEvent.ACTION_DOWN:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();

                //CHECK IF USER HAS POINTED ON ANY OF THE VEHICLES
                setInitialCondition(plough,
                        yPos,
                        xPos,
                        plough.getWidth(),
                        plough.getHeight());

                setInitialCondition(
                        timberTruck,
                        yPos,
                        xPos,
                        timberTruck.getWidth(),
                        timberTruck.getHeight());

                setInitialCondition(farmTractor,
                        yPos,
                        xPos,
                        farmTractor.getWidth(),
                        farmTractor.getHeight());


            case MotionEvent.ACTION_MOVE:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();


                disabledVehicles.clear();
                disabledVehicles.add(timberTruck);
                disabledVehicles.add(farmTractor);
                setMovingCondition(plough,
                        disabledVehicles,
                        yPos,
                        xPos,
                        plough.getWidth(),
                        plough.getHeight());

                disabledVehicles.clear();
                disabledVehicles.add(plough);
                disabledVehicles.add(farmTractor);
                setMovingCondition(timberTruck,
                        disabledVehicles,
                        yPos,
                        xPos,
                        timberTruck.getWidth(),
                        timberTruck.getHeight());

                disabledVehicles.clear();
                disabledVehicles.add(plough);
                disabledVehicles.add(timberTruck);
                setMovingCondition(farmTractor,
                        disabledVehicles,
                        yPos,
                        xPos,
                        farmTractor.getWidth(),
                        farmTractor.getHeight());

                disabledVehicles.clear();

                break;

            case MotionEvent.ACTION_UP: {

                checkIfMoving(plough);
                checkIfMoving(timberTruck);
                checkIfMoving(farmTractor);


                decideAnimation(plough,
                        87.5 * displayHeight / 100 - plough.getHeight(),     //limit if to drop it on the road1
                        87.5 * displayHeight / 100 - plough.getHeight(),     //point of where to place vehicle if pulled up
                        97.5 * displayHeight / 100 - plough.getHeight(),         //limit if to pull vehicle up on the road1
                        97.5 * displayHeight / 100 - plough.getHeight(),         //limit if to draw vehicle back ...same as lowerborder??
                        plough.getStartPosY(),        // where to place car at Y
                        plough.getStartPosX());

                decideAnimation(timberTruck,
                        68 * displayHeight / 100 - timberTruck.getHeight(),     //limit if to drop it on the road1
                        68 * displayHeight / 100 - timberTruck.getHeight(),     //point of where to place vehicle if pulled up
                        75 * displayHeight / 100 - timberTruck.getHeight(),         //limit if to pull vehicle up on the road1
                        75 * displayHeight / 100 - timberTruck.getHeight(),         //limit if to draw vehicle back ...same as lowerborder??
                        timberTruck.getStartPosY(),        // where to place car at Y
                        timberTruck.getStartPosX());

                decideAnimation(farmTractor,
                        72.5 * displayHeight / 100 - farmTractor.getHeight(),     //limit if to drop it on the road1
                        72.5 * displayHeight / 100 - farmTractor.getHeight(),     //point of where to place vehicle if pulled up
                        82.5 * displayHeight / 100 - farmTractor.getHeight(),         //limit if to pull vehicle up on the road1
                        82.5 * displayHeight / 100 - farmTractor.getHeight(),         //limit if to draw vehicle back ...same as lowerborder??
                        farmTractor.getStartPosY(),        // where to place car at Y
                        farmTractor.getStartPosX());

                break;
            }
        }
        return true;

    }


    void vehicleAnimators(final Vehicle vehicle, int animatorNumber, float destXPos, float destYPos) {
        vehicle.setTouchable(false);

        if (vehicle == farmTractor) {
            vehicleAnimator = farmAnimator;
        } else if (vehicle == timberTruck) {
            vehicleAnimator = timberTruckAnimator;
        } else if (vehicle == plough) {
            vehicleAnimator = ploughAnimator;
        }
        //DRAWBACK
        switch (animatorNumber) {

            case 1: //DRAW BACK TO STARTING POINT

                final PropertyValuesHolder hX = PropertyValuesHolder.ofFloat("xPos", destXPos);
                final PropertyValuesHolder hY = PropertyValuesHolder.ofFloat("yPos", destYPos);
                vehicleAnimator = ObjectAnimator.ofPropertyValuesHolder(vehicle, hX, hY);
                vehicleAnimator.setDuration(200);
                vehicleAnimator.setInterpolator(new DecelerateInterpolator());
                vehicleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (vehicle == farmTractor) {
                            farmAnimator = vehicleAnimator;
                        } else if (vehicle == timberTruck) {
                            timberTruckAnimator = vehicleAnimator;
                        } else if (vehicle == plough) {
                            ploughAnimator = vehicleAnimator;
                        }

                        vehicle.setYPos((Float) vehicleAnimator.getAnimatedValue("yPos"));
                        vehicle.setXPos((Float) vehicleAnimator.getAnimatedValue("xPos"));

                    }
                });

                if (vehicle == plough) {
                    vehicleAnimator.addListener(ploughListener);
                } else if (vehicle == farmTractor) {
                    vehicleAnimator.addListener(farmTractorListener);
                } else if (vehicle == timberTruck) {
                    vehicleAnimator.addListener(timberTruckListener);
                }

                vehicle.flagInPlace(false);
                vehicle.flagDrawBack(true);
                vehicleAnimator.start();

                break;

            case 2: // DROP ON THE ROAD

                vehicleAnimator = ObjectAnimator.ofFloat(vehicle, "yPos", vehicle.getYPos(), destYPos);
                vehicleAnimator.setDuration(700);
                vehicleAnimator.setInterpolator(new BounceInterpolator());
                vehicleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (vehicle == farmTractor) {
                            farmAnimator = vehicleAnimator;
                        } else if (vehicle == timberTruck) {
                            timberTruckAnimator = vehicleAnimator;
                        } else if (vehicle == plough) {
                            ploughAnimator = vehicleAnimator;
                        }
                        vehicle.setYPos((Float) animation.getAnimatedValue());

                    }
                });
                if (vehicle == plough) {
                    vehicleAnimator.addListener(ploughListener);
                } else if (vehicle == farmTractor) {
                    vehicleAnimator.addListener(farmTractorListener);
                } else if (vehicle == timberTruck) {
                    vehicleAnimator.addListener(timberTruckListener);
                }
                vehicleAnimator.start();
                break;

            case 3: //LIFT ON TO THE ROAD

                vehicleAnimator = ObjectAnimator.ofFloat(vehicle, "yPos", vehicle.getYPos(), destYPos);
                vehicleAnimator.setDuration(100);
                vehicleAnimator.setInterpolator(new DecelerateInterpolator());
                vehicleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (vehicle == farmTractor) {
                            farmAnimator = vehicleAnimator;
                        } else if (vehicle == timberTruck) {
                            timberTruckAnimator = vehicleAnimator;
                        } else if (vehicle == plough) {
                            ploughAnimator = vehicleAnimator;
                        }
                        vehicle.setYPos((Float) animation.getAnimatedValue());

                    }
                });
                if (vehicle == plough) {
                    vehicleAnimator.addListener(ploughListener);
                } else if (vehicle == farmTractor) {
                    vehicleAnimator.addListener(farmTractorListener);
                } else if (vehicle == timberTruck) {
                    vehicleAnimator.addListener(timberTruckListener);
                }
                vehicleAnimator.start();
                break;

            default:
                break;

        }
    }

    void animateItems() {

        plough.setTouchable(false);
        timberTruck.setTouchable(false);
        farmTractor.setTouchable(false);

        ploughAnimator = ObjectAnimator.ofFloat(plough, "xPos", plough.getXPos(), displayWidth + plough.getWidth());
        ploughAnimator.setDuration(11000);
        ploughAnimator.setStartDelay(1000);
        ploughAnimator.setInterpolator(new AccelerateInterpolator());
        ploughAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                plough.setXPos((Float) ploughAnimator.getAnimatedValue());

            }
        });

        timberTruckAnimator = ObjectAnimator.ofFloat(timberTruck, "xPos", timberTruck.getXPos(), startOfScreen);
        timberTruckAnimator.setDuration(7000);
        timberTruckAnimator.setInterpolator(new AccelerateInterpolator());
        timberTruckAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                timberTruck.setXPos((Float) timberTruckAnimator.getAnimatedValue());

            }
        });

        farmAnimator = ObjectAnimator.ofFloat(farmTractor, "xPos", farmTractor.getXPos(), endOfScreen);
        farmAnimator.setDuration(5000);
        farmAnimator.setStartDelay(200);
        farmAnimator.setInterpolator(new AccelerateInterpolator());
        farmAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                farmTractor.setXPos((Float) farmAnimator.getAnimatedValue());

            }
        });

        AnimatorSet vehicleAnimationSet = new AnimatorSet();
        vehicleAnimationSet.addListener(sceneAnimator);
        vehicleAnimationSet.play(ploughAnimator)
                .with(timberTruckAnimator)
                .with(farmAnimator);
        vehicleAnimationSet.start();

    }


    ObjectAnimator.AnimatorListener timberTruckListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            if (!timberTruck.getFlagDrawBack()
                    && plough.getFlagInPlace()
                    && farmTractor.getFlagInPlace()) {
                plough.setTouchable(false);
                farmTractor.setTouchable(false);
            }

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            timberTruck.setTouchable(true);


            if (!timberTruck.getFlagDrawBack()) {
                timberTruck.flagInPlace(true);
            } else if (timberTruck.getFlagDrawBack())
                timberTruck.flagDrawBack(false);

            if (timberTruck.getFlagInPlace()
                    && plough.getFlagInPlace()
                    && farmTractor.getFlagInPlace()) {
                animateItems();
            }

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    ObjectAnimator.AnimatorListener ploughListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            if (!plough.getFlagDrawBack()
                    && timberTruck.getFlagInPlace()
                    && farmTractor.getFlagInPlace()) {
                timberTruck.setTouchable(false);
                farmTractor.setTouchable(false);
            }

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            plough.setTouchable(true);


            if (!plough.getFlagDrawBack()) {
                plough.flagInPlace(true);
            } else if (plough.getFlagDrawBack())
                plough.flagDrawBack(false);

            if (timberTruck.getFlagInPlace()
                    && plough.getFlagInPlace()
                    && farmTractor.getFlagInPlace()) {
                animateItems();
            }

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    ObjectAnimator.AnimatorListener farmTractorListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            if (!farmTractor.getFlagDrawBack()
                    && plough.getFlagInPlace()
                    && timberTruck.getFlagInPlace()) {
                plough.setTouchable(false);
                timberTruck.setTouchable(false);
            }

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            farmTractor.setTouchable(true);


            if (!farmTractor.getFlagDrawBack()) {
                farmTractor.flagInPlace(true);
            } else if (farmTractor.getFlagDrawBack())
                farmTractor.flagDrawBack(false);

            if (timberTruck.getFlagInPlace()
                    && plough.getFlagInPlace()
                    && farmTractor.getFlagInPlace()) {
                animateItems();
            }

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    ObjectAnimator.AnimatorListener sceneAnimator = new ObjectAnimator.AnimatorListener() {
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

    void setMovingCondition(Vehicle vehicle, ArrayList<Vehicle> disabledVehicles, float yPos, float xPos, float widthVar, float heightVar) {
        if ((xPos > vehicle.getXPos())
                && (xPos < vehicle.getXPos() + widthVar)
                && (yPos > vehicle.getYPos())
                && (yPos < vehicle.getYPos() + heightVar)
                && (vehicle.isTouchable())
                && (!disabledVehicles.get(0).getFlagIsMoving())
                && (!disabledVehicles.get(1).getFlagIsMoving())) {
            if (vehicle.isSetOffsetPos()) {
                vehicle.setOffsetXPos(xPos - vehicle.getXPos());
                vehicle.setOffsetYPos(yPos - vehicle.getYPos());
                vehicle.setOffsetPos(false);
            }
            vehicle.setXPos(xPos - vehicle.getOffsetXPos());
            vehicle.setYPos(yPos - vehicle.getOffsetYPos());
            vehicle.flagIsMoving(true);
            setDisabledVehicles(vehicle);
        }
    }

    void setInitialCondition(Vehicle vehicle, float yPos, float xPos, float widthVar, float heightVar) {

        if ((xPos > vehicle.getXPos())
                && (xPos < vehicle.getXPos() + widthVar)
                && (yPos > vehicle.getYPos())
                && (yPos < vehicle.getYPos() + heightVar)
                && (vehicle.isTouchable())) {
            vehicle.setOffsetXPos(xPos - vehicle.getXPos());
            vehicle.setOffsetYPos(yPos - vehicle.getYPos());
            vehicle.setOffsetPos(false);
        }
    }

    void checkIfMoving(Vehicle vehicle) {

        if (!vehicle.getFlagIsMoving()) {
            vehicle.flagHasMoved(false);
        } else {
            vehicle.flagHasMoved(true);
            vehicle.flagIsMoving(false);
        }
    }

    void decideAnimation(Vehicle vehicle,
                         double upperBorder,    //limit if to drop it on the road1
                         double drawVar,        //point of where to place vehicle if pulled up
                         double lowerBorder,    //limit if to pull vehicle up on the road1
                         double backUpperBorder, //limit if to draw vehicle back ...same as lowerborder??
                         float toMoveY,             // where to place car at Y
                         float toMoveX) {

        //SHOULD VEHICLE BE DROPPED
        if (vehicle.getYPos() <= upperBorder && vehicle.getFlagHasMoved()) {
            vehicle.setOffsetPos(true);

            if (vehicle == plough)
                vehicleAnimators(plough, 2, 0, (float) upperBorder);
            else if (vehicle == timberTruck)
                vehicleAnimators(timberTruck, 2, 0, (float) upperBorder);
            else if (vehicle == farmTractor)
                vehicleAnimators(farmTractor, 2, 0, (float) upperBorder);

            //SHOULD VEHICLE BE LIFT UP
        } else if (vehicle.getYPos() > upperBorder
                && vehicle.getYPos() < lowerBorder
                && vehicle.getFlagHasMoved()) {
            vehicle.setOffsetPos(true);

            if (vehicle == plough)
                vehicleAnimators(plough, 3, 0, (float) drawVar);
            else if (vehicle == timberTruck)
                vehicleAnimators(timberTruck, 3, 0, (float) drawVar);
            else if (vehicle == farmTractor)
                vehicleAnimators(farmTractor, 3, 0, (float) drawVar);

            //SHOULD VEHICLE BE SENT BACK TO INITIAL POSITION?
        } else if (vehicle.getYPos() > backUpperBorder && vehicle.getFlagHasMoved()) {

            vehicle.setOffsetPos(true);
            lenghtToMoveY = toMoveY - vehicle.getYPos();
            lenghtToMoveX = vehicle.getXPos() - toMoveX;

            if (vehicle == plough)
                vehicleAnimators(plough, 1, toMoveX, toMoveY);
            else if (vehicle == timberTruck)
                vehicleAnimators(timberTruck,1, toMoveX,toMoveY);
            else if (vehicle == farmTractor)
                vehicleAnimators(farmTractor,   1, toMoveX,toMoveY);

        }
    }


    void setDisabledVehicles(Vehicle vehicle) {


        if (vehicle == timberTruck) {
            plough.flagIsMoving(false);
            farmTractor.flagIsMoving(false);
            timberTruck.setAllOtherDisabled(true);
        }


        if (vehicle == plough) {
            timberTruck.flagIsMoving(false);
            farmTractor.flagIsMoving(false);
            plough.setAllOtherDisabled(true);
        }

        if (vehicle == farmTractor) {
            timberTruck.flagIsMoving(false);
            plough.flagIsMoving(false);
            farmTractor.setAllOtherDisabled(true);
        }


    }

}



*/