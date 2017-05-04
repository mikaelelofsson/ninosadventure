/*package com.example.mikaelelofsson.ninosadventure;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
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
public class ConstructionSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    SurfaceHolder holderOfTheSurface;
    GameThread gameThread;
    Paint rectColor = new Paint();

    Paint rectPaint;
    Context myContext;

    ArrayList<Vehicle> listOfVehicles = new ArrayList<Vehicle>();
    ArrayList<Vehicle> disabledVehicles = new ArrayList<Vehicle>();

    ValueAnimator holediggerAnimator;
    ValueAnimator minitractorAnimator;
    ValueAnimator gaffeltruckAnimator;
    ValueAnimator craneAnimator;
    ValueAnimator excavatorAnimator;
    ValueAnimator vehicleAnimator;

    Vehicle excavator;
    Vehicle minitractor;
    Vehicle holedigger;
    Vehicle gaffeltruck;
    Vehicle crane;

    Bitmap excavatorBmp;
    Bitmap holediggerBmp;
    Bitmap minitractorBmp;
    Bitmap gaffeltruckBmp;
    Bitmap craneBmp;

    float xPos, yPos;
    float startOfScreen, endOfScreen;
    float displayHeight, displayWidth;
    float lenghtToMoveY, lenghtToMoveX;



    Paint circleColor = new Paint();


    public ConstructionSurfaceView(Context context, AttributeSet attrs) {
        super(context,attrs);

        holderOfTheSurface = getHolder();
        holderOfTheSurface.addCallback(this);

        Bitmap origExcavatorBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.excavator);
        excavatorBmp = Bitmap.createScaledBitmap(origExcavatorBmp, 500, 300, true);

        Bitmap origHolediggerBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.holedigger);
        holediggerBmp = Bitmap.createScaledBitmap(origHolediggerBmp, 500, 300, true);

        Bitmap origMinitractorBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.minitractor);
        minitractorBmp = Bitmap.createScaledBitmap(origMinitractorBmp, 350, 250, true);

        Bitmap origGaffeltruckBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.gaffeltruck);
        gaffeltruckBmp = Bitmap.createScaledBitmap(origGaffeltruckBmp, 350, 250, true);

        Bitmap origCraneBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.crane);
        craneBmp = Bitmap.createScaledBitmap(origCraneBmp, 500, 300, true);



        this.myContext = context;
        rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;
        endOfScreen = displayWidth + 250;
        startOfScreen = -500;

       // this.setOnTouchListener(this);


        excavator = new Vehicle(0, displayHeight - 320);
        holedigger = new Vehicle(550, displayHeight - 320);
        minitractor = new Vehicle(1100, displayHeight - 280);
        gaffeltruck = new Vehicle(1500, displayHeight - 275);
        crane = new Vehicle(1900, displayHeight - 320);

        listOfVehicles.add(excavator);
        listOfVehicles.add(holedigger);
        listOfVehicles.add(minitractor);
        listOfVehicles.add(gaffeltruck);
        listOfVehicles.add(crane);




    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        android.view.ViewGroup.LayoutParams lp = this.getLayoutParams();
        lp.width = (int)displayWidth; // required width
        lp.height = (int)displayHeight; // required height
        this.setLayoutParams(lp);



        gameThread = new GameThread(holderOfTheSurface,this);
        gameThread.setRunning(true);
        gameThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setRunning(false);
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void draw (Canvas canvas) {

        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
        canvas.drawRect(0, displayHeight - 230, displayWidth, displayHeight, rectPaint);

        canvas.drawBitmap(holediggerBmp, holedigger.getXPos(), holedigger.getYPos(), null);
        canvas.drawBitmap(craneBmp, crane.getXPos(), crane.getYPos(), null);
        canvas.drawBitmap(excavatorBmp, excavator.getXPos(), excavator.getYPos(), null);
        canvas.drawBitmap(gaffeltruckBmp, gaffeltruck.getXPos(), gaffeltruck.getYPos(), null);
        canvas.drawBitmap(minitractorBmp, minitractor.getXPos(), minitractor.getYPos(), null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {



        int action = MotionEventCompat.getActionMasked(motionEvent);


        switch (action) {

            case MotionEvent.ACTION_DOWN:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();

                //CHECK IF USER HAS POINTED ON ANY OF THE VEHICLES
                setInitialCondition(excavator, yPos, xPos, 500, 200);
                setInitialCondition(holedigger, yPos, xPos, 500, 200);
                setInitialCondition(gaffeltruck, yPos, xPos, 500, 200);
                setInitialCondition(minitractor, yPos, xPos, 500, 200);
                setInitialCondition(crane, yPos, xPos, 500, 200);


            case MotionEvent.ACTION_MOVE:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();

                disabledVehicles.add(holedigger);
                disabledVehicles.add(gaffeltruck);
                disabledVehicles.add(minitractor);
                disabledVehicles.add(crane);
                setMovingCondition(excavator, disabledVehicles, yPos, xPos, 500, 200);

                disabledVehicles.clear();
                disabledVehicles.add(excavator);
                disabledVehicles.add(gaffeltruck);
                disabledVehicles.add(minitractor);
                disabledVehicles.add(crane);
                setMovingCondition(holedigger, disabledVehicles, yPos, xPos, 500, 200);

                disabledVehicles.clear();
                disabledVehicles.add(holedigger);
                disabledVehicles.add(excavator);
                disabledVehicles.add(minitractor);
                disabledVehicles.add(crane);
                setMovingCondition(gaffeltruck, disabledVehicles, yPos, xPos, 500, 200);

                disabledVehicles.clear();
                disabledVehicles.add(holedigger);
                disabledVehicles.add(gaffeltruck);
                disabledVehicles.add(excavator);
                disabledVehicles.add(crane);
                setMovingCondition(minitractor, disabledVehicles, yPos, xPos, 500, 200);

                disabledVehicles.clear();
                disabledVehicles.add(holedigger);
                disabledVehicles.add(gaffeltruck);
                disabledVehicles.add(excavator);
                disabledVehicles.add(minitractor);
                setMovingCondition(crane, disabledVehicles, yPos, xPos, 500, 200);

                disabledVehicles.clear();

                break;

            case MotionEvent.ACTION_UP: {

                checkIfMoving(excavator);
                checkIfMoving(holedigger);
                checkIfMoving(minitractor);
                checkIfMoving(gaffeltruck);
                checkIfMoving(crane);

                decideAnimation(excavator, 490, 490, 400, 400, 320, 0, 1);
                decideAnimation(holedigger, 580, 580, 400, 400, 320, 550, 550);
                decideAnimation(minitractor, 440, 470, 350, 350, 280, 1100, 1100);
                decideAnimation(gaffeltruck, 440, 460,350, 350, 275, 1500, 1500);
                decideAnimation(crane, 580, 570, 400, 400, 320, 1900, 1900);



                break;
            }
        }
        return true;

    }


    void vehicleAnimators(final Vehicle vehicle, int animatorNumber, float destXPos, float destYPos) {
        vehicle.setTouchable(false);

        if (vehicle == crane) {
            vehicleAnimator = craneAnimator;
        }else if (vehicle == minitractor) {
            vehicleAnimator = minitractorAnimator;
        } else if (vehicle == holedigger) {
            vehicleAnimator = holediggerAnimator;
        }else if (vehicle == excavator) {
            vehicleAnimator = excavatorAnimator;
        }else if (vehicle == gaffeltruck) {
            vehicleAnimator = gaffeltruckAnimator;
        }
        //DRAWBACK
        switch (animatorNumber) {

            case 1: //DRAW BACK TO STARTING POINT

                final PropertyValuesHolder hX = PropertyValuesHolder.ofFloat("xPos", destXPos);
                final PropertyValuesHolder hY = PropertyValuesHolder.ofFloat("yPos", displayHeight - destYPos);
                vehicleAnimator = ObjectAnimator.ofPropertyValuesHolder(vehicle, hX, hY);
                vehicleAnimator.setDuration(200);
                vehicleAnimator.setInterpolator(new DecelerateInterpolator());
                vehicleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (vehicle == crane) {
                            craneAnimator = vehicleAnimator;
                        }else if (vehicle == minitractor) {
                            minitractorAnimator = vehicleAnimator;
                        } else if (vehicle == holedigger) {
                            holediggerAnimator = vehicleAnimator;
                        }else if (vehicle == excavator) {
                            excavatorAnimator = vehicleAnimator;
                        }else if (vehicle == gaffeltruck) {
                            gaffeltruckAnimator = vehicleAnimator;
                        }

                        vehicle.setYPos((Float) vehicleAnimator.getAnimatedValue("yPos"));
                        vehicle.setXPos((Float) vehicleAnimator.getAnimatedValue("xPos"));

                    }
                });
                vehicleAnimator.addListener(animListen);
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
                        if (vehicle == crane) {
                            craneAnimator = vehicleAnimator;
                        }else if (vehicle == minitractor) {
                            minitractorAnimator = vehicleAnimator;
                        } else if (vehicle == holedigger) {
                            holediggerAnimator = vehicleAnimator;
                        }else if (vehicle == excavator) {
                            excavatorAnimator = vehicleAnimator;
                        }else if (vehicle == gaffeltruck) {
                            gaffeltruckAnimator = vehicleAnimator;
                        }
                        vehicle.setYPos((Float) animation.getAnimatedValue());

                    }
                });
                vehicleAnimator.addListener(animListen);
                vehicleAnimator.start();
                break;

            case 3: //LIFT ON TO THE ROAD

                vehicleAnimator = ObjectAnimator.ofFloat(vehicle, "yPos", vehicle.getYPos(), destYPos);
                vehicleAnimator.setDuration(100);
                vehicleAnimator.setInterpolator(new DecelerateInterpolator());
                vehicleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (vehicle == crane) {
                            craneAnimator = vehicleAnimator;
                        }else if (vehicle == minitractor) {
                            minitractorAnimator = vehicleAnimator;
                        } else if (vehicle == holedigger) {
                            holediggerAnimator = vehicleAnimator;
                        }else if (vehicle == excavator) {
                            excavatorAnimator = vehicleAnimator;
                        }else if (vehicle == gaffeltruck) {
                            gaffeltruckAnimator = vehicleAnimator;
                        }
                        vehicle.setYPos((Float) animation.getAnimatedValue());

                    }
                });
                vehicleAnimator.addListener(animListen);
                vehicleAnimator.start();
                break;

            default:
                break;

        }
    }

    void animateItems() {

        excavator.setTouchable(false);
        holedigger.setTouchable(false);
        minitractor.setTouchable(false);
        gaffeltruck.setTouchable(false);
        crane.setTouchable(false);

        holediggerAnimator = ObjectAnimator.ofFloat(holedigger, "xPos", holedigger.getXPos(), startOfScreen);
        holediggerAnimator.setDuration(3500);
        holediggerAnimator.setStartDelay(1000);
        holediggerAnimator.setInterpolator(new AccelerateInterpolator());
        holediggerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                holedigger.setXPos((Float) holediggerAnimator.getAnimatedValue());

            }
        });

        gaffeltruckAnimator = ObjectAnimator.ofFloat(gaffeltruck, "xPos", gaffeltruck.getXPos(), endOfScreen);
        gaffeltruckAnimator.setDuration(2500);
        gaffeltruckAnimator.setStartDelay(1000);
        gaffeltruckAnimator.setInterpolator(new AccelerateInterpolator());
        gaffeltruckAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                gaffeltruck.setXPos((Float) gaffeltruckAnimator.getAnimatedValue());

            }
        });
        minitractorAnimator = ObjectAnimator.ofFloat(minitractor, "xPos", minitractor.getXPos(), endOfScreen);
        minitractorAnimator.setDuration(2000);
        minitractorAnimator.setInterpolator(new AccelerateInterpolator());
        minitractorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                minitractor.setXPos((Float) minitractorAnimator.getAnimatedValue());

            }
        });

        excavatorAnimator = ObjectAnimator.ofFloat(excavator, "xPos", excavator.getXPos(), endOfScreen);
        excavatorAnimator.setDuration(5000);
        excavatorAnimator.setStartDelay(1000);
        excavatorAnimator.setInterpolator(new AccelerateInterpolator());
        excavatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                excavator.setXPos((Float) excavatorAnimator.getAnimatedValue());

            }
        });
        craneAnimator = ObjectAnimator.ofFloat(crane, "xPos", crane.getXPos(), startOfScreen);
        craneAnimator.setDuration(3000);
        craneAnimator.setStartDelay(200);
        craneAnimator.setInterpolator(new AccelerateInterpolator());
        craneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                crane.setXPos((Float) craneAnimator.getAnimatedValue());

            }
        });

        AnimatorSet vehicleAnimationSet = new AnimatorSet();
        vehicleAnimationSet.addListener(sceneAnimator);
        vehicleAnimationSet.play(holediggerAnimator)
                .with(gaffeltruckAnimator)
                .with(minitractorAnimator)
                .with(excavatorAnimator)
                .with(craneAnimator);
        vehicleAnimationSet.start();

    }

    ObjectAnimator.AnimatorListener animListen = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            if (animation == gaffeltruckAnimator) {
                if ((!gaffeltruck.getFlagDrawBack())
                        && excavator.getFlagInPlace()
                        && minitractor.getFlagInPlace()
                        && holedigger.getFlagInPlace()
                        && crane.getFlagInPlace()) {
                    minitractor.setTouchable(false);
                    excavator.setTouchable(false);
                    holedigger.setTouchable(false);
                    crane.setTouchable(false);
                }
            } else if (animation == minitractorAnimator) {
                if (!minitractor.getFlagDrawBack()
                        && excavator.getFlagInPlace()
                        && gaffeltruck.getFlagInPlace()
                        && holedigger.getFlagInPlace()
                        && crane.getFlagInPlace()) {
                    gaffeltruck.setTouchable(false);
                    excavator.setTouchable(false);
                    holedigger.setTouchable(false);
                    crane.setTouchable(false);

                }
            } else if (animation == holediggerAnimator) {
                if (!holedigger.getFlagDrawBack()
                        && excavator.getFlagInPlace()
                        && minitractor.getFlagInPlace()
                        && gaffeltruck.getFlagInPlace()
                        && crane.getFlagInPlace()) {
                    minitractor.setTouchable(false);
                    excavator.setTouchable(false);
                    gaffeltruck.setTouchable(false);
                    crane.setTouchable(false);
                }
            } else if (animation == excavatorAnimator) {
                if (!excavator.getFlagDrawBack()
                        && gaffeltruck.getFlagInPlace()
                        && minitractor.getFlagInPlace()
                        && holedigger.getFlagInPlace()
                        && crane.getFlagInPlace()) {
                    minitractor.setTouchable(false);
                    gaffeltruck.setTouchable(false);
                    holedigger.setTouchable(false);
                    crane.setTouchable(false);
                }
            }else if (animation == craneAnimator) {
                if (!crane.getFlagDrawBack()
                        && gaffeltruck.getFlagInPlace()
                        && minitractor.getFlagInPlace()
                        && holedigger.getFlagInPlace()
                        && excavator.getFlagInPlace()) {
                    minitractor.setTouchable(false);
                    gaffeltruck.setTouchable(false);
                    holedigger.setTouchable(false);
                    excavator.setTouchable(false);
                }
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {


            if (animation == gaffeltruckAnimator) {
                gaffeltruck.setTouchable(true);
                if (!gaffeltruck.getFlagDrawBack())
                    gaffeltruck.flagInPlace(true);
                else if (gaffeltruck.getFlagDrawBack())
                    gaffeltruck.flagDrawBack(false);
            } else if (animation == holediggerAnimator) {
                holedigger.setTouchable(true);
                if (!holedigger.getFlagDrawBack())
                    holedigger.flagInPlace(true);
                else if (holedigger.getFlagDrawBack())
                    holedigger.flagDrawBack(false);
            } else if (animation == minitractorAnimator) {
                minitractor.setTouchable(true);
                if (!minitractor.getFlagDrawBack())
                    minitractor.flagInPlace(true);
                else if (minitractor.getFlagDrawBack())
                    minitractor.flagDrawBack(false);
            } else if (animation == excavatorAnimator) {
                excavator.setTouchable(true);
                if (!excavator.getFlagDrawBack())
                    excavator.flagInPlace(true);
                else if (excavator.getFlagDrawBack())
                    excavator.flagDrawBack(false);
            } else if (animation == craneAnimator) {
                crane.setTouchable(true);
                if (!crane.getFlagDrawBack())
                    crane.flagInPlace(true);
                else if (crane.getFlagDrawBack())
                    crane.flagDrawBack(false);
            }
            if (gaffeltruck.getFlagInPlace()
                    && excavator.getFlagInPlace()
                    && minitractor.getFlagInPlace()
                    && holedigger.getFlagInPlace()
                    && crane.getFlagInPlace()) {
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
            gameThread.setRunning(false);
            gameThread.interrupt();


                    ConstructionActivity.startIntent();
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
                && (!disabledVehicles.get(1).getFlagIsMoving())
                && (!disabledVehicles.get(2).getFlagIsMoving())
                && (!disabledVehicles.get(3).getFlagIsMoving())) {
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

    void decideAnimation(Vehicle vehicle, int upperBorder, int drawVar, int lowerBorder,
                         int backUpperBorder, int toMoveY, int toMoveX, int animateX) {



        decideAnimation(excavator,      490, 490,   400, 400, 320, 0,       1);
        decideAnimation(holedigger,     580, 580,   400, 400, 320, 550,     550);
        decideAnimation(minitractor,    440, 470,   350, 350, 280, 1100,    1100);
        decideAnimation(gaffeltruck,    440, 460,   350, 350, 275, 1500,    1500);
        decideAnimation(crane,          580, 570,   400, 400, 320, 1900,    1900);

        excavator = new Vehicle(0, displayHeight - 320);
        holedigger = new Vehicle(550, displayHeight - 320);
        minitractor = new Vehicle(1100, displayHeight - 280);
        gaffeltruck = new Vehicle(1500, displayHeight - 275);
        crane = new Vehicle(1900, displayHeight - 320);


        //SHOULD VEHICLE BE DRAWN UP ON THE ROAD?
        if (vehicle.getYPos() <= displayHeight - upperBorder && vehicle.getFlagHasMoved()) {
            vehicle.setOffsetPos(true);

            if (vehicle == crane)
                vehicleAnimators(crane,2, 0, (float) displayHeight - upperBorder);
            else if (vehicle == minitractor)
                vehicleAnimators(minitractor,2, 0, (float) displayHeight - upperBorder);
            else if (vehicle == holedigger)
                vehicleAnimators(holedigger,2, 0, (float) displayHeight - upperBorder);
            else if (vehicle == excavator)
                vehicleAnimators(excavator,2, 0, (float) displayHeight - upperBorder);
            else if (vehicle == gaffeltruck)
                vehicleAnimators(gaffeltruck,2, 0, (float) displayHeight - upperBorder);

            //SHOULD VEHICLE BE DROPPED ON THE ROAD?
        } else if (vehicle.getYPos() > displayHeight - upperBorder
                && vehicle.getYPos() < displayHeight - lowerBorder
                && vehicle.getFlagHasMoved()) {
            vehicle.setOffsetPos(true);

            if (vehicle == crane)
                vehicleAnimators(crane,3, 0, (float) displayHeight - drawVar);
            else if (vehicle == minitractor)
                vehicleAnimators(minitractor,3, 0, (float) displayHeight - drawVar);
            else if (vehicle == holedigger)
                vehicleAnimators(holedigger,3, 0, (float) displayHeight - drawVar);
            else if (vehicle == excavator)
                vehicleAnimators(excavator,3, 0, (float) displayHeight - drawVar);
            else if (vehicle == gaffeltruck)
                vehicleAnimators(gaffeltruck,3, 0, (float) displayHeight - drawVar);

            //SHOULD VEHICLE BE SENT BACK TO INITIAL POSITION?
        } else if (vehicle.getYPos() > displayHeight - backUpperBorder && vehicle.getFlagHasMoved()) {

            vehicle.setOffsetPos(true);
            lenghtToMoveY = displayHeight - toMoveY - vehicle.getYPos();
            lenghtToMoveX = vehicle.getXPos() - toMoveX;
            if (vehicle == crane)
                vehicleAnimators(crane,1, (float) animateX, (float) toMoveY);
            else if (vehicle == minitractor)
                vehicleAnimators(minitractor,1, (float) animateX, (float) toMoveY);
            else if (vehicle == holedigger)
                vehicleAnimators(holedigger,1, (float) animateX, (float) toMoveY);
            else if (vehicle == excavator)
                vehicleAnimators(excavator,1, (float) animateX, (float) toMoveY);
            else if (vehicle == gaffeltruck)
                vehicleAnimators(gaffeltruck,1, (float) animateX, (float) toMoveY);


        }
    }


    void setDisabledVehicles(Vehicle vehicle) {

        if (vehicle == excavator) {
            minitractor.flagIsMoving(false);
            gaffeltruck.flagIsMoving(false);
            holedigger.flagIsMoving(false);
            crane.flagIsMoving(false);
            excavator.setAllOtherDisabled(true);
        }

        if (vehicle == minitractor) {
            excavator.flagIsMoving(false);
            gaffeltruck.flagIsMoving(false);
            holedigger.flagIsMoving(false);
            crane.flagIsMoving(false);
            minitractor.setAllOtherDisabled(true);
        }

        if (vehicle == gaffeltruck) {
            minitractor.flagIsMoving(false);
            excavator.flagIsMoving(false);
            holedigger.flagIsMoving(false);
            crane.flagIsMoving(false);
            gaffeltruck.setAllOtherDisabled(true);
        }

        if (vehicle == holedigger) {
            minitractor.flagIsMoving(false);
            gaffeltruck.flagIsMoving(false);
            excavator.flagIsMoving(false);
            crane.flagIsMoving(false);
            holedigger.setAllOtherDisabled(true);
        }

        if (vehicle == crane) {
            minitractor.flagIsMoving(false);
            gaffeltruck.flagIsMoving(false);
            excavator.flagIsMoving(false);
            holedigger.flagIsMoving(false);
            crane.setAllOtherDisabled(true);
        }


    }

}

*/