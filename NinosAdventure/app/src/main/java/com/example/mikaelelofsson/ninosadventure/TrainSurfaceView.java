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
public class TrainSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder trainHolder;
    GameThread gameThread;
    Paint rectColor = new Paint();

    Paint rectPaint;
    Context myContext;

    ArrayList<Vehicle> listOfVehicles = new ArrayList<Vehicle>();
    ArrayList<Vehicle> disabledVehicles = new ArrayList<Vehicle>();

    ValueAnimator train1Animator;
    ValueAnimator train2Animator;
    ValueAnimator train3Animator;
    ValueAnimator vehicleAnimator;


    Vehicle train2;
    Vehicle train1;
    Vehicle train3;


    Bitmap train1Bmp;
    Bitmap train2Bmp;
    Bitmap train3Bmp;

    float xPos, yPos;
    float startOfScreen, endOfScreen;
    float displayHeight, displayWidth;
    float lenghtToMoveY, lenghtToMoveX;

    public TrainSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        trainHolder = getHolder();
        trainHolder.addCallback(this);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;
        endOfScreen = displayWidth + 900;
        startOfScreen = -500;

        train1 = new Vehicle();
        train1.setVehicleWidth((int) (32.5 * displayWidth / 100));
        train1.setVehicleHeight(((23 * train1.getVehicleWidth() / 100)));
        train1.setXPos((float)(1 * displayWidth / 100));
        train1.setYPos((displayHeight - (110 * train1.getVehicleHeight() / 100)));
        train1.setStartPosX(train1.getXPos());
        train1.setStartPosY(train1.getYPos());

        train2 = new Vehicle();
        train2.setVehicleWidth((int) (32.5 * displayWidth / 100));
        train2.setVehicleHeight((int)(23 * train2.getVehicleWidth() / 100));
        train2.setXPos((displayWidth/2) - (train2.getVehicleWidth()/2));
        train2.setYPos((displayHeight - (110 * train2.getVehicleHeight() / 100)));
        train2.setStartPosX(train2.getXPos());
        train2.setStartPosY(train2.getYPos());

        train3 = new Vehicle();
        train3.setVehicleWidth((int) (32.5 * displayWidth / 100));
        train3.setVehicleHeight((int) (23 * train3.getVehicleWidth() / 100));
        train3.setXPos((displayWidth - ((float)(33.5 * displayWidth / 100))));
        train3.setYPos((displayHeight - (110 * train3.getVehicleHeight() / 100)));
        train3.setStartPosX(train3.getXPos());
        train3.setStartPosY(train3.getYPos());





        Bitmap origTrain1Bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.train1);
        train1Bmp = Bitmap.createScaledBitmap(origTrain1Bmp, train1.getVehicleWidth(), train1.getVehicleHeight(), true);

        Bitmap origTrain2Bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.train2);
        train2Bmp = Bitmap.createScaledBitmap(origTrain2Bmp, train2.getVehicleWidth(), train2.getVehicleHeight(), true);


        Bitmap origTrain3Bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.train3);
        train3Bmp = Bitmap.createScaledBitmap(origTrain3Bmp,train3.getVehicleWidth(), train3.getVehicleHeight(), true);

        this.myContext = context;
        rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);

        listOfVehicles.add(train1);
        listOfVehicles.add(train2);
        listOfVehicles.add(train3);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        android.view.ViewGroup.LayoutParams lp = this.getLayoutParams();
        lp.width = (int) displayWidth; // required width
        lp.height = (int) displayHeight; // required height
        this.setLayoutParams(lp);


        gameThread = new GameThread(holder, this);
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
        Intent intent = new Intent(myContext, CountrySideActivity.class);
        myContext.startActivity(intent);
    }
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
        //canvas.drawRect(0, displayHeight - 230, displayWidth, displayHeight, rectPaint);

        canvas.drawBitmap(train1Bmp, train1.getXPos(), train1.getYPos(), null);
        canvas.drawBitmap(train3Bmp, train3.getXPos(), train3.getYPos(), null);
        canvas.drawBitmap(train2Bmp, train2.getXPos(), train2.getYPos(), null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {


        int action = MotionEventCompat.getActionMasked(motionEvent);


        switch (action) {

            case MotionEvent.ACTION_DOWN:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();

                //CHECK IF USER HAS POINTED ON ANY OF THE VEHICLES
                setInitialCondition(train1,
                        yPos,
                        xPos,
                        train1.getVehicleWidth(),
                        train1.getVehicleHeight());

                setInitialCondition(train2,
                        yPos,
                        xPos,
                        train2.getVehicleWidth(),
                        train2.getVehicleHeight());

                setInitialCondition(train3,
                        yPos,
                        xPos,
                        train3.getVehicleWidth(),
                        train3.getVehicleHeight());


            case MotionEvent.ACTION_MOVE:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();


                disabledVehicles.clear();
                disabledVehicles.add(train2);
                disabledVehicles.add(train3);
                setMovingCondition(train1,
                        disabledVehicles,
                        yPos,
                        xPos,
                        train1.getVehicleWidth(),
                        train1.getVehicleHeight());

                disabledVehicles.clear();
                disabledVehicles.add(train1);
                disabledVehicles.add(train3);
                setMovingCondition(train2,
                        disabledVehicles,
                        yPos,
                        xPos,
                        train2.getVehicleWidth(),
                        train2.getVehicleHeight());

                disabledVehicles.clear();
                disabledVehicles.add(train1);
                disabledVehicles.add(train2);
                setMovingCondition(train3,
                        disabledVehicles,
                        yPos,
                        xPos,
                        train3.getVehicleWidth(),
                        train3.getVehicleHeight());

                disabledVehicles.clear();

                break;

            case MotionEvent.ACTION_UP: {

                checkIfMoving(train1);
                checkIfMoving(train2);
                checkIfMoving(train3);

                decideAnimation(train1,
                        76.5 * displayHeight / 100 - train1.getVehicleHeight(),     //limit if to drop it on the road1
                        76.5 * displayHeight / 100 - train1.getVehicleHeight(),     //point of where to place vehicle if pulled up
                        86.5 * displayHeight / 100 - train1.getVehicleHeight(),         //limit if to pull vehicle up on the road1
                        86.5 * displayHeight / 100 - train1.getVehicleHeight(),         //limit if to draw vehicle back ...same as lowerborder??
                        train1.getStartPosY(),        // where to place car at Y
                        train1.getStartPosX());

                decideAnimation(train2,
                        76.5 * displayHeight / 100 - train2.getVehicleHeight(),     //limit if to drop it on the road1
                        76.5 * displayHeight / 100 - train2.getVehicleHeight(),     //point of where to place vehicle if pulled up
                        86.5 * displayHeight / 100 - train2.getVehicleHeight(),         //limit if to pull vehicle up on the road1
                        86.5 * displayHeight / 100 - train2.getVehicleHeight(),         //limit if to draw vehicle back ...same as lowerborder??
                        train2.getStartPosY(),        // where to place car at Y
                        train2.getStartPosX());

                decideAnimation(train3,
                        76.5 * displayHeight / 100 - train3.getVehicleHeight(),     //limit if to drop it on the road1
                        76.5 * displayHeight / 100 - train3.getVehicleHeight(),     //point of where to place vehicle if pulled up
                        86.5 * displayHeight / 100 - train3.getVehicleHeight(),         //limit if to pull vehicle up on the road1
                        86.5 * displayHeight / 100 - train3.getVehicleHeight(),         //limit if to draw vehicle back ...same as lowerborder??
                        train3.getStartPosY(),        // where to place car at Y
                        train3.getStartPosX());



                break;
            }
        }
        return true;

    }




    void vehicleAnimators(final Vehicle vehicle, int animatorNumber, float destXPos, float destYPos) {
        vehicle.setTouchable(false);

        if (vehicle == train3) {
            vehicleAnimator = train3Animator;
        } else if (vehicle == train2) {
            vehicleAnimator = train2Animator;
        } else if (vehicle == train1) {
            vehicleAnimator = train1Animator;
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
                        if (vehicle == train3) {
                            train3Animator = vehicleAnimator;
                        } else if (vehicle == train2) {
                            train2Animator = vehicleAnimator;
                        } else if (vehicle == train1) {
                            train1Animator = vehicleAnimator;
                        }

                        vehicle.setYPos((Float) vehicleAnimator.getAnimatedValue("yPos"));
                        vehicle.setXPos((Float) vehicleAnimator.getAnimatedValue("xPos"));

                    }
                });
                if (vehicle == train3) {
                    vehicleAnimator.addListener(train3anim);
                } else if (vehicle == train2) {
                    vehicleAnimator.addListener(train2anim);
                } else if (vehicle == train1) {
                    vehicleAnimator.addListener(train1anim);
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
                        if (vehicle == train3) {
                            train3Animator = vehicleAnimator;
                        } else if (vehicle == train2) {
                            train2Animator = vehicleAnimator;
                        } else if (vehicle == train1) {
                            train1Animator = vehicleAnimator;
                        }
                        vehicle.setYPos((Float) animation.getAnimatedValue());

                    }
                });
                if (vehicle == train3) {
                    vehicleAnimator.addListener(train3anim);
                } else if (vehicle == train2) {
                    vehicleAnimator.addListener(train2anim);
                } else if (vehicle == train1) {
                    vehicleAnimator.addListener(train1anim);
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
                        if (vehicle == train3) {
                            train3Animator = vehicleAnimator;
                        } else if (vehicle == train2) {
                            train2Animator = vehicleAnimator;
                        } else if (vehicle == train1) {
                            train1Animator = vehicleAnimator;
                        }
                        vehicle.setYPos((Float) animation.getAnimatedValue());

                    }
                });
                if (vehicle == train3) {
                    vehicleAnimator.addListener(train3anim);
                } else if (vehicle == train2) {
                    vehicleAnimator.addListener(train2anim);
                } else if (vehicle == train1) {
                    vehicleAnimator.addListener(train1anim);
                }
                vehicleAnimator.start();
                break;

            default:
                break;

        }
    }

    void animateItems() {

        train1.setTouchable(false);
        train2.setTouchable(false);
        train3.setTouchable(false);

        train1Animator = ObjectAnimator.ofFloat(train1, "xPos", train1.getXPos(), (((displayWidth-(displayWidth/2))-400) - 850) + displayWidth);
        train1Animator.setDuration(3500);
        train1Animator.setInterpolator(new AccelerateInterpolator());
        train1Animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                train1.setXPos((Float) train1Animator.getAnimatedValue());

            }
        });

        train2Animator = ObjectAnimator.ofFloat(train2, "xPos", train2.getXPos(), ((displayWidth-(displayWidth/2))-400)+displayWidth);
        train2Animator.setDuration(3500);
        train2Animator.setInterpolator(new AccelerateInterpolator());
        train2Animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                train2.setXPos((Float) train2Animator.getAnimatedValue());

            }
        });

        train3Animator = ObjectAnimator.ofFloat(train3, "xPos", train3.getXPos(), ((displayWidth-(displayWidth/2))+400)+displayWidth);
        train3Animator.setDuration(3500);
        train3Animator.setInterpolator(new AccelerateInterpolator());
        train3Animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                train3.setXPos((Float) train3Animator.getAnimatedValue());

            }
        });

        AnimatorSet vehicleAnimationSet = new AnimatorSet();
        vehicleAnimationSet.addListener(sceneAnimator);
        vehicleAnimationSet.play(train1Animator)
                .with(train2Animator)
                .with(train3Animator);
        vehicleAnimationSet.start();


        if (!train2.getFlagDrawBack()
                && train1.getFlagInPlace()
                && train3.getFlagInPlace())

        {
            train1.setTouchable(false);
            train3.setTouchable(false);
        }
    }


    ObjectAnimator.AnimatorListener train1prepAnim = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {


            if (!train1.getFlagDrawBack()
                    && train2.getFlagInPlace()
                    && train3.getFlagInPlace()) {
                train2.setTouchable(false);
                train3.setTouchable(false);
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {

            train1.flagInPlace(true);

            if (train2.getFlagInPlace()
                    && train1.getFlagInPlace()
                    && train3.getFlagInPlace()) {
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
    ObjectAnimator.AnimatorListener train1anim = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

                if (!train1.getFlagDrawBack()
                        && train2.getFlagInPlace()
                        && train3.getFlagInPlace()) {
                    train2.setTouchable(false);
                    train3.setTouchable(false);
                }

        }

        @Override
        public void onAnimationEnd(Animator animation) {

                train1.setTouchable(true);

                if (!train1.getFlagDrawBack()) {

                    SetVehicleStartPos train1PrepAnim = new SetVehicleStartPos(train1Animator);
                    train1PrepAnim.animateVehicleToFinishState(train1, (displayWidth/2-((train1.getVehicleWidth()/2)*3)));
                    train1PrepAnim.vehicleAnimator.addListener(train1prepAnim);
                    train1PrepAnim.startAnimation();
                }
                else if (train1.getFlagDrawBack())
                    train1.flagDrawBack(false);

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    ObjectAnimator.AnimatorListener train2prepAnim = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {


            if (!train2.getFlagDrawBack()
                    && train1.getFlagInPlace()
                    && train3.getFlagInPlace()) {
                train1.setTouchable(false);
                train3.setTouchable(false);
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {

            train2.flagInPlace(true);

            if (train2.getFlagInPlace()
                    && train1.getFlagInPlace()
                    && train3.getFlagInPlace()) {
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
    ObjectAnimator.AnimatorListener train2anim = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

                if (!train2.getFlagDrawBack()
                        && train1.getFlagInPlace()
                        && train3.getFlagInPlace()) {
                    train1.setTouchable(false);
                    train3.setTouchable(false);
                }

            }

        @Override
        public void onAnimationEnd(Animator animation) {


            train2.setTouchable(true);

            if (!train2.getFlagDrawBack()) {
                SetVehicleStartPos train2PrepAnim = new SetVehicleStartPos(train2Animator);
                train2PrepAnim.animateVehicleToFinishState(train2,(displayWidth/2-train2.getVehicleWidth()/2));
                train2PrepAnim.vehicleAnimator.addListener(train2prepAnim);
                train2PrepAnim.startAnimation();

            }
            else if (train2.getFlagDrawBack())
                train2.flagDrawBack(false);

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    ObjectAnimator.AnimatorListener train3prepAnim = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {


                if (!train3.getFlagDrawBack()
                        && train2.getFlagInPlace()
                        && train1.getFlagInPlace()) {
                    train2.setTouchable(false);
                    train1.setTouchable(false);

                }
            }

        @Override
        public void onAnimationEnd(Animator animation) {

            train3.flagInPlace(true);

            if (train2.getFlagInPlace()
                    && train1.getFlagInPlace()
                    && train3.getFlagInPlace()) {
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
    ObjectAnimator.AnimatorListener train3anim = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {


            if (!train3.getFlagDrawBack()
                    && train2.getFlagInPlace()
                    && train1.getFlagInPlace()) {
                train2.setTouchable(false);
                train1.setTouchable(false);

            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {

            train3.setTouchable(true);


            if (!train3.getFlagDrawBack()) {
                SetVehicleStartPos train3PrepAnim = new SetVehicleStartPos(train3Animator);
                train3PrepAnim.animateVehicleToFinishState(train3, (displayWidth/2+(train1.getVehicleWidth()/2)));
                train3PrepAnim.vehicleAnimator.addListener(train3prepAnim);
                train3PrepAnim.startAnimation();



            }

            else if (train3.getFlagDrawBack())
                train3.flagDrawBack(false);

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

    void setMovingCondition(Vehicle vehicle, ArrayList<Vehicle> disabledVehicles, float yPos, float xPos, int widthVar, int heightVar) {
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

    void setInitialCondition(Vehicle vehicle, float yPos, float xPos, int widthVar, int heightVar) {

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
                         float toMoveX) {           // where to place car at X


        //SHOULD VEHICLE BE DROPPED
        if (vehicle.getYPos() <= upperBorder && vehicle.getFlagHasMoved()) {
            vehicle.setOffsetPos(true);

            if (vehicle == train1)
                vehicleAnimators(train1, 2, 0, (float) upperBorder);
            else if (vehicle == train2)
                vehicleAnimators(train2, 2, 0, (float) upperBorder);
            else if (vehicle == train3)
                vehicleAnimators(train3, 2, 0, (float) upperBorder);


            //SHOULD VEHICLE BE LIFT UP
        } else if (vehicle.getYPos() > upperBorder
                && vehicle.getYPos() < lowerBorder
                && vehicle.getFlagHasMoved()) {
            vehicle.setOffsetPos(true);

            if (vehicle == train1)
                vehicleAnimators(train1, 3, 0, (float) drawVar);
            else if (vehicle == train2)
                vehicleAnimators(train2, 3, 0, (float) drawVar);
            else if (vehicle == train3)
                vehicleAnimators(train3, 3, 0, (float) drawVar);


            //SHOULD VEHICLE BE SENT BACK TO INITIAL POSITION?
        } else if (vehicle.getYPos() > backUpperBorder && vehicle.getFlagHasMoved()) {

            vehicle.setOffsetPos(true);
            lenghtToMoveY = displayHeight - toMoveY - vehicle.getYPos();
            lenghtToMoveX = vehicle.getXPos() - toMoveX;
            if (vehicle == train1)
                vehicleAnimators(train1, 1, toMoveX, toMoveY);
            else if (vehicle == train2)
                vehicleAnimators(train2,1, toMoveX,toMoveY);
            else if (vehicle == train3)
                vehicleAnimators(train3,   1, toMoveX,toMoveY);


        }
    }
    void setDisabledVehicles(Vehicle vehicle) {



        if (vehicle == train2) {
            train1.flagIsMoving(false);
            train3.flagIsMoving(false);
            train2.setAllOtherDisabled(true);
        }


        if (vehicle == train1) {
            train2.flagIsMoving(false);
            train3.flagIsMoving(false);
            train1.setAllOtherDisabled(true);
        }

        if (vehicle == train3) {
            train2.flagIsMoving(false);
            train1.flagIsMoving(false);
            train3.setAllOtherDisabled(true);
        }


    }

}





*/