/*
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
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.ArrayList;

import static com.example.mikaelelofsson.ninosadventure.AirportActivity.bgSurfaceView;



public class AirportSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder airportHolder;
    GameThread gameThread;
    Paint rectColor = new Paint();
    Context context;
*/
/*
    Paint rectPaint;
    Context myContext;

    ArrayList<Vehicle> listOfVehicles = new ArrayList<Vehicle>();
    ArrayList<Vehicle> disabledVehicles = new ArrayList<Vehicle>();

    ValueAnimator jumbojetAnimator;
    ValueAnimator helicopterAnimator;
    ValueAnimator ladderCarAnimator;
    ValueAnimator vehicleAnimator;

    Boolean timeForTakeOff = false;

    Vehicle jumbojet;
    Vehicle helicopter;
    Vehicle ladderCar;

    Bitmap jumbojetBmp;
    Bitmap helicopterBmp;
    Bitmap ladderCarBmp;
    Matrix matrix;
   float degree = 0;


    float xPos, yPos;
    float startOfScreen, endOfScreen;
    float displayHeight, displayWidth;
    float lenghtToMoveY, lenghtToMoveX;


    public AirportSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        airportHolder = getHolder();
        airportHolder.addCallback(this);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;
        endOfScreen = displayWidth + 900;
        startOfScreen = -500;

        jumbojet = new Vehicle();
        jumbojet.setWidth((int)(50*displayWidth/100));
        jumbojet.setHeight(((35*jumbojet.getWidth()/100)));
        jumbojet.setXPos( (2*displayWidth/100));
        jumbojet.setYPos( (displayHeight-(110*jumbojet.getHeight()/100)));
        jumbojet.setStartPosX(jumbojet.getXPos());
        jumbojet.setStartPosY(jumbojet.getYPos());

        helicopter = new Vehicle();
        helicopter.setWidth((int)(displayWidth/5));
        helicopter.setHeight((helicopter.getWidth()/3));
        helicopter.setXPos((55*displayWidth/100));
        helicopter.setYPos((85*displayHeight/100));
        helicopter.setStartPosX(helicopter.getXPos());
        helicopter.setStartPosY(helicopter.getYPos());


        ladderCar = new Vehicle();
        ladderCar.setWidth((int) (displayWidth/7));
        ladderCar.setHeight((int)(ladderCar.getWidth()/0.7));
        ladderCar.setXPos((80*displayWidth/100));
        ladderCar.setYPos(60*displayHeight/100);
        ladderCar.setStartPosX(ladderCar.getXPos());
        ladderCar.setStartPosY(ladderCar.getYPos());


        matrix = new Matrix();


        Bitmap origJumbojetBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.jumbojet_scaled);
        jumbojetBmp = Bitmap.createScaledBitmap(origJumbojetBmp, (int)jumbojet.getWidth(), (int)jumbojet.getHeight(), true);

        Bitmap origHelicopterBmp= BitmapFactory.decodeResource(this.getResources(), R.drawable.helicopter_scaled);
        helicopterBmp = Bitmap.createScaledBitmap(origHelicopterBmp, (int)helicopter.getWidth(),(int) helicopter.getHeight(), true);

        Bitmap origLadderBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.balloon);
        ladderCarBmp = Bitmap.createScaledBitmap(origLadderBmp, (int)ladderCar.getWidth(), (int)ladderCar.getHeight(), true);

        this.myContext = context;
        rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);

        listOfVehicles.add(jumbojet);
        listOfVehicles.add(helicopter);
        listOfVehicles.add(ladderCar);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        android.view.ViewGroup.LayoutParams lp = this.getLayoutParams();
        lp.width = (int) displayWidth;
        lp.height = (int) displayHeight;
        this.setLayoutParams(lp);

        gameThread = new GameThread(airportHolder, this);
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
        Intent intent = new Intent(myContext, OceanActivity.class);
        myContext.startActivity(intent);
    }




    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
        canvas.drawRect(0, 90*displayHeight/100, displayWidth, displayHeight, rectPaint);

        canvas.drawBitmap(ladderCarBmp, ladderCar.getXPos(), ladderCar.getYPos(), null);
        canvas.drawBitmap(helicopterBmp, helicopter.getXPos(), helicopter.getYPos(), null);

        if(timeForTakeOff){
            Matrix matrix = new Matrix();
            if(degree>-20) {
            degree += -0.1;
        }
            matrix.postRotate(degree);
            matrix.postTranslate(jumbojet.getXPos(), jumbojet.getYPos());
            canvas.drawBitmap(jumbojetBmp, matrix, null);
        }

            else{
            canvas.drawBitmap(jumbojetBmp, jumbojet.getXPos(), jumbojet.getYPos(), null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {


        int action = MotionEventCompat.getActionMasked(motionEvent);


        switch (action) {

            case MotionEvent.ACTION_DOWN:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();

                //CHECK IF USER HAS POINTED ON ANY OF THE VEHICLES
                setInitialCondition(jumbojet, yPos, xPos, jumbojet.getWidth() , jumbojet.getHeight());
                setInitialCondition(helicopter, yPos, xPos, helicopter.getWidth(), helicopter.getHeight());
                setInitialCondition(ladderCar, yPos, xPos, ladderCar.getWidth(), ladderCar.getHeight());


            case MotionEvent.ACTION_MOVE:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();


                disabledVehicles.clear();
                disabledVehicles.add(helicopter);
                disabledVehicles.add(ladderCar);
                setMovingCondition( jumbojet,
                                    disabledVehicles,
                                    yPos,
                                    xPos,
                                    jumbojet.getWidth(),
                                    jumbojet.getHeight());

                disabledVehicles.clear();
                disabledVehicles.add(jumbojet);
                disabledVehicles.add(ladderCar);
                setMovingCondition( helicopter,
                                    disabledVehicles,
                                    yPos,
                                    xPos,
                                    helicopter.getWidth(),
                                    helicopter.getHeight());

                disabledVehicles.clear();
                disabledVehicles.add(jumbojet);
                disabledVehicles.add(helicopter);
                setMovingCondition( ladderCar,
                                    disabledVehicles,
                                    yPos,
                                    xPos,
                                    ladderCar.getWidth(),
                                    ladderCar.getHeight());

                disabledVehicles.clear();

                break;

            case MotionEvent.ACTION_UP: {

                checkIfMoving(jumbojet);
                checkIfMoving(helicopter);
                checkIfMoving(ladderCar);


                decideAnimation(jumbojet,
                                50*displayHeight/100,     //limit if to drop it on the road1
                                50*displayHeight/100,     //point of where to place vehicle if pulled up
                                60*displayHeight/100,         //limit if to pull vehicle up on the road1
                                60*displayHeight/100,         //limit if to draw vehicle back ...same as lowerborder??
                                jumbojet.getStartPosY(),        // where to place car at Y
                                jumbojet.getStartPosX());       // where to place car at X


                decideAnimation(helicopter,
                                70*displayHeight/100,     //limit if to drop it on the road1
                                70*displayHeight/100,     //point of where to place vehicle if pulled up
                                60*displayHeight/100,         //limit if to pull vehicle up on the road1
                                60*displayHeight/100,         //limit if to draw vehicle back ...same as lowerborder??
                                helicopter.getStartPosY(),        // where to place car at Y
                                helicopter.getStartPosX());

                decideAnimation(ladderCar,
                                50*displayHeight/100,     //limit if to drop it on the road1
                                50*displayHeight/100,     //point of where to place vehicle if pulled up
                                60*displayHeight/100,         //limit if to pull vehicle up on the road1
                                60*displayHeight/100,         //limit if to draw vehicle back ...same as lowerborder??
                                ladderCar.getStartPosY(),        // where to place car at Y
                                ladderCar.getStartPosX());

                break;
            }
        }
        return true;

    }




    void vehicleAnimators(final Vehicle vehicle, int animatorNumber, float destXPos, float destYPos) {
        vehicle.setTouchable(false);

        if (vehicle == ladderCar) {
            vehicleAnimator = ladderCarAnimator;
        } else if (vehicle == helicopter) {
            vehicleAnimator = helicopterAnimator;
        } else if (vehicle == jumbojet) {
            vehicleAnimator = jumbojetAnimator;
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

                        if (vehicle == ladderCar) {
                            ladderCarAnimator = vehicleAnimator;
                        } else if (vehicle == helicopter) {
                            helicopterAnimator = vehicleAnimator;
                        } else if (vehicle == jumbojet) {
                            jumbojetAnimator = vehicleAnimator;
                        }

                        vehicle.setYPos((Float) vehicleAnimator.getAnimatedValue("yPos"));
                        vehicle.setXPos((Float) vehicleAnimator.getAnimatedValue("xPos"));

                    }
                });
                if (vehicle == ladderCar) {
                    vehicleAnimator.addListener(ladderListener);
                } else if (vehicle == helicopter) {
                    vehicleAnimator.addListener(helicopterListener);
                } else if (vehicle == jumbojet) {
                    vehicleAnimator.addListener(jumboJetListener);
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
                        if (vehicle == ladderCar) {
                            ladderCarAnimator = vehicleAnimator;
                        } else if (vehicle == helicopter) {
                            helicopterAnimator = vehicleAnimator;
                        } else if (vehicle == jumbojet) {
                            jumbojetAnimator = vehicleAnimator;
                        }

                        vehicle.setYPos((Float) animation.getAnimatedValue());

                    }
                });
                if (vehicle == ladderCar) {
                    vehicleAnimator.addListener(ladderListener);
                } else if (vehicle == helicopter) {
                    vehicleAnimator.addListener(helicopterListener);
                } else if (vehicle == jumbojet) {
                    vehicleAnimator.addListener(jumboJetListener);
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
                        if (vehicle == ladderCar) {
                            ladderCarAnimator = vehicleAnimator;
                        } else if (vehicle == helicopter) {
                            helicopterAnimator = vehicleAnimator;
                        } else if (vehicle == jumbojet) {
                            jumbojetAnimator = vehicleAnimator;
                        }
                        vehicle.setYPos((Float) animation.getAnimatedValue());

                    }
                });
                if (vehicle == ladderCar) {
                    vehicleAnimator.addListener(ladderListener);
                } else if (vehicle == helicopter) {
                    vehicleAnimator.addListener(helicopterListener);
                } else if (vehicle == jumbojet) {
                    vehicleAnimator.addListener(jumboJetListener);
                }
                vehicleAnimator.start();
                break;

            default:
                break;

        }
    }

    void animateItems(Boolean takeOff) {

        if (!takeOff) {
            jumbojet.setTouchable(false);
            helicopter.setTouchable(false);
            ladderCar.setTouchable(false);

            Keyframe k1 = Keyframe.ofFloat(0, jumbojet.getXPos());
            Keyframe k2 = Keyframe.ofFloat(1f, 10 * displayWidth / 100);


            final PropertyValuesHolder hX = PropertyValuesHolder.ofKeyframe("xPos", k1, k2);

            jumbojetAnimator = ObjectAnimator.ofPropertyValuesHolder(jumbojet, hX);

            jumbojetAnimator.setDuration(12000);
            jumbojetAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            jumbojetAnimator.addListener(planeStart);
            jumbojetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    jumbojet.setXPos((Float) jumbojetAnimator.getAnimatedValue());

                }
            });

            k1 = Keyframe.ofFloat(0, helicopter.getXPos());
            k2 = Keyframe.ofFloat(.5f, helicopter.getXPos());
            Keyframe k3 = Keyframe.ofFloat(1, 0 - helicopter.getWidth());
            k3.setFraction(1);
            k3.setInterpolator(new AccelerateInterpolator());

            final PropertyValuesHolder helX = PropertyValuesHolder.ofKeyframe("xPos", k1, k2, k3);

            k1 = Keyframe.ofFloat(0, helicopter.getYPos());
            k2 = Keyframe.ofFloat(.3f, (20 * displayHeight / 100));
            k2.setInterpolator(new AccelerateDecelerateInterpolator());
            k3 = Keyframe.ofFloat(.6f, (27 * displayHeight / 100));
            k2.setInterpolator(new AccelerateDecelerateInterpolator());
            Keyframe k4 = Keyframe.ofFloat(1f, (10 * displayHeight / 100));

            final PropertyValuesHolder helY = PropertyValuesHolder.ofKeyframe("yPos", k1, k2, k3, k4);

            helicopterAnimator = ObjectAnimator.ofPropertyValuesHolder(helicopter, helX, helY);
            helicopterAnimator.setDuration(11000);
            helicopterAnimator.setInterpolator(new AccelerateInterpolator());
            helicopterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    helicopter.setYPos((Float) helicopterAnimator.getAnimatedValue());

                }
            });


            ladderCarAnimator = ObjectAnimator.ofFloat(ladderCar, "yPos", ladderCar.getYPos(), (0 - ladderCar.getHeight()));
            ladderCarAnimator.setDuration(3000);
            ladderCarAnimator.setInterpolator(new AccelerateInterpolator());
            ladderCarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    ladderCar.setYPos((Float) ladderCarAnimator.getAnimatedValue());

                }
            });


            AnimatorSet vehicleAnimationSet = new AnimatorSet();
            AnimatorSet wholeSet = new AnimatorSet();
            wholeSet.addListener(sceneAnimator);
            vehicleAnimationSet.play(helicopterAnimator)
                    .with(ladderCarAnimator);
            wholeSet.play(jumbojetAnimator)
                    .after(vehicleAnimationSet);
            wholeSet.start();


            if (!helicopter.getFlagDrawBack()
                    && jumbojet.getFlagInPlace()
                    && ladderCar.getFlagInPlace())

            {
                jumbojet.setTouchable(false);
                ladderCar.setTouchable(false);
            }
        }

        else {

            Keyframe k1 = Keyframe.ofFloat(0, jumbojet.getXPos());
            Keyframe k3 = Keyframe.ofFloat(1, displayWidth*2);
            k3.setInterpolator(new AccelerateInterpolator());

            final PropertyValuesHolder hX = PropertyValuesHolder.ofKeyframe("xPos",k1,k3);

            k1 = Keyframe.ofFloat(0, jumbojet.getYPos());
            Keyframe k2 = Keyframe.ofFloat((long)1, 0-(displayHeight*2));
            k2.setInterpolator(new AccelerateInterpolator());


            final PropertyValuesHolder hY = PropertyValuesHolder.ofKeyframe("yPos",k1,k2);

            final ObjectAnimator jumbojetTakeOffAnimator = ObjectAnimator.ofPropertyValuesHolder(jumbojet,hX,hY);

           jumbojetTakeOffAnimator.setDuration(6000);
            jumbojetTakeOffAnimator.setInterpolator(new AccelerateInterpolator());
            jumbojetTakeOffAnimator.addListener(planeTakeOff);
            jumbojetTakeOffAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    jumbojet.setYPos((Float) jumbojetTakeOffAnimator.getAnimatedValue());
                    jumbojet.setXPos((Float) jumbojetTakeOffAnimator.getAnimatedValue());

                }
            });

            jumbojetTakeOffAnimator.start();
        }

    }

    ObjectAnimator.AnimatorListener jumboJetListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            if (!jumbojet.getFlagDrawBack()
                    && helicopter.getFlagInPlace()
                    && ladderCar.getFlagInPlace()) {
                helicopter.setTouchable(false);
                ladderCar.setTouchable(false);
            }

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            jumbojet.setTouchable(true);


            if (!jumbojet.getFlagDrawBack()) {
                jumbojet.flagInPlace(true);
                }

            else if (jumbojet.getFlagDrawBack())
                jumbojet.flagDrawBack(false);

            if (helicopter.getFlagInPlace()
                    && jumbojet.getFlagInPlace()
                    && ladderCar.getFlagInPlace()) {
                animateItems(false);
            }

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    ObjectAnimator.AnimatorListener planeTakeOff = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            timeForTakeOff=true;

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
    ObjectAnimator.AnimatorListener planeStart = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            Interpolator interpolator = new AccelerateInterpolator();

            bgSurfaceView.startBgLoops(10000,interpolator);
            bgSurfaceView.startSkyLoops(20000,interpolator);
            bgSurfaceView.animateItems();

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            }



        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    ObjectAnimator.AnimatorListener helicopterListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            if (!helicopter.getFlagDrawBack()
                    && jumbojet.getFlagInPlace()
                    && ladderCar.getFlagInPlace()) {
                jumbojet.setTouchable(false);
                ladderCar.setTouchable(false);
            }

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            helicopter.setTouchable(true);


            if (!helicopter.getFlagDrawBack()) {
                helicopter.flagInPlace(true);
            }
            else if (helicopter.getFlagDrawBack())
                helicopter.flagDrawBack(false);

            if (helicopter.getFlagInPlace()
                    && jumbojet.getFlagInPlace()
                    && ladderCar.getFlagInPlace()) {
                animateItems(false);
            }

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    ObjectAnimator.AnimatorListener ladderListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            if (!ladderCar.getFlagDrawBack()
                    && helicopter.getFlagInPlace()
                    && ladderCar.getFlagInPlace()) {
                jumbojet.setTouchable(false);
                helicopter.setTouchable(false);
            }

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            ladderCar.setTouchable(true);


            if (!ladderCar.getFlagDrawBack()) {
                ladderCar.flagInPlace(true);
            }
            else if (ladderCar.getFlagDrawBack())
                ladderCar.flagDrawBack(false);

            if (helicopter.getFlagInPlace()
                    && jumbojet.getFlagInPlace()
                    && ladderCar.getFlagInPlace()) {
                animateItems(false);
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
                         float toMoveX) {           // where to place car at X


        //SHOULD VEHICLE BE DROPPED
        if (vehicle.getYPos() <= upperBorder && vehicle.getFlagHasMoved()) {
            vehicle.setOffsetPos(true);

           if (vehicle == ladderCar)
                vehicleAnimators(ladderCar,2, 0, (float) upperBorder);
            else if (vehicle == helicopter)
                vehicleAnimators(helicopter,2, 0, (float) upperBorder);
            else if (vehicle == jumbojet)
               vehicleAnimators(jumbojet,2, 0, (float) upperBorder);

            //SHOULD VEHICLE BE LIFT UP
        } else if (vehicle.getYPos() > upperBorder
                && vehicle.getYPos() < lowerBorder
                && vehicle.getFlagHasMoved()) {
            vehicle.setOffsetPos(true);

            if (vehicle == ladderCar)
                vehicleAnimators(ladderCar,3, 0, (float) drawVar);
            else if (vehicle == helicopter)
                vehicleAnimators(helicopter,3, 0, (float) drawVar);
            else if (vehicle == jumbojet)
                vehicleAnimators(jumbojet,3, 0, (float) drawVar);

            //SHOULD VEHICLE BE SENT BACK TO INITIAL POSITION?
        } else if (vehicle.getYPos() > backUpperBorder && vehicle.getFlagHasMoved()) {

            vehicle.setOffsetPos(true);
            lenghtToMoveY = displayHeight - toMoveY - vehicle.getYPos();
            lenghtToMoveX = vehicle.getXPos() - toMoveX;
            if (vehicle == ladderCar)
                vehicleAnimators(ladderCar,1, (float) toMoveX, (float) toMoveY);
            else if (vehicle == helicopter)
                vehicleAnimators(helicopter,1, (float) toMoveX, (float) toMoveY);
            else if (vehicle == jumbojet)
                vehicleAnimators(jumbojet,1, (float) toMoveX, (float) toMoveY);

        }
    }


    void setDisabledVehicles(Vehicle vehicle) {



        if (vehicle == helicopter) {
            jumbojet.flagIsMoving(false);
            ladderCar.flagIsMoving(false);
            helicopter.setAllOtherDisabled(true);
        }


        if (vehicle == jumbojet) {
            helicopter.flagIsMoving(false);
            ladderCar.flagIsMoving(false);
            jumbojet.setAllOtherDisabled(true);
        }

        if (vehicle == ladderCar) {
            helicopter.flagIsMoving(false);
            jumbojet.flagIsMoving(false);
            ladderCar.setAllOtherDisabled(true);
        }


    }

}


*/


