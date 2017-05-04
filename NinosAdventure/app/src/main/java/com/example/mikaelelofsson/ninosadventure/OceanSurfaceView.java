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
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;

/**
 * Created by Mikael Elofsson on 2017-02-16.
 */
/*
public class OceanSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder oceanHolder;
    GameThread gameThread;
    Paint rectColor = new Paint();

    Paint rectPaint;
    Context myContext;

    ArrayList<Vehicle> listOfVehicles = new ArrayList<Vehicle>();
    ArrayList<Vehicle> disabledVehicles = new ArrayList<Vehicle>();

    ValueAnimator sailingboatAnimator;
    ValueAnimator oakAnimator;
    ValueAnimator shipAnimator;
    ValueAnimator vehicleAnimator;

    Vehicle sailingBoat;
    Vehicle oak;
    Vehicle ship;

    Bitmap sailingBoatBmp;
    Bitmap oakBmp;
    Bitmap shipBmp;

    float xPos, yPos;
    float startOfScreen, endOfScreen;
    float displayHeight, displayWidth;
    float lenghtToMoveY, lenghtToMoveX;

    public OceanSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        oceanHolder = getHolder();
        oceanHolder.addCallback(this);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;
        endOfScreen = displayWidth + 900;
        startOfScreen = -500;

        sailingBoat = new Vehicle();
        sailingBoat.setVehicleWidth((int)(55*displayWidth/100));
        sailingBoat.setVehicleHeight(((55*sailingBoat.getVehicleWidth()/100)));
        sailingBoat.setXPos( (2*displayWidth/100));
        sailingBoat.setYPos( (displayHeight-(105*sailingBoat.getVehicleHeight()/100)));
        sailingBoat.setStartPosX(sailingBoat.getXPos());
        sailingBoat.setStartPosY(sailingBoat.getYPos());

        oak = new Vehicle();
        oak.setVehicleWidth((int)(18*displayWidth/100));
        oak.setVehicleHeight(((65*oak.getVehicleWidth()/100)));
        oak.setXPos( (40*displayWidth/100));
        oak.setYPos( (displayHeight-(105*oak.getVehicleHeight()/100)));
        oak.setStartPosX(oak.getXPos());
        oak.setStartPosY(oak.getYPos());


        ship = new Vehicle();
        ship.setVehicleWidth((int)(50*displayWidth/100));
        ship.setVehicleHeight(((65*ship.getVehicleWidth()/100)));
        ship.setXPos( (displayWidth - 52*displayWidth/100));
        ship.setYPos( (displayHeight-(105*ship.getVehicleHeight()/100)));
        ship.setStartPosX(ship.getXPos());
        ship.setStartPosY(ship.getYPos());



        Bitmap origSailingBoatBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.sailingboat_scaled);
        sailingBoatBmp = Bitmap.createScaledBitmap(origSailingBoatBmp, sailingBoat.getVehicleWidth(), sailingBoat.getVehicleHeight(), true);

        Bitmap origOakBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.oak_scaled);
        oakBmp = Bitmap.createScaledBitmap(origOakBmp, oak.getVehicleWidth(), oak.getVehicleHeight(), true);


        Bitmap origShipBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.ship);
        shipBmp = Bitmap.createScaledBitmap(origShipBmp, ship.getVehicleWidth(), ship.getVehicleHeight(), true);

        this.myContext = context;
        rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);



        // this.setOnTouchListener(this);



        listOfVehicles.add(sailingBoat);
        listOfVehicles.add(oak);
        listOfVehicles.add(ship);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        ViewGroup.LayoutParams lp = this.getLayoutParams();
        lp.width = (int) displayWidth; // required width
        lp.height = (int) displayHeight; // required height
        this.setLayoutParams(lp);


        gameThread = new GameThread(oceanHolder, this);
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
        Intent intent = new Intent(myContext, MainActivity.class);
        myContext.startActivity(intent);
    }



    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
       // canvas.drawRect(0, displayHeight - 130, displayWidth, displayHeight, rectPaint);


        canvas.drawBitmap(shipBmp, ship.getXPos(), ship.getYPos(), null);
        canvas.drawBitmap(oakBmp, oak.getXPos(), oak.getYPos(), null);
        canvas.drawBitmap(sailingBoatBmp, sailingBoat.getXPos(), sailingBoat.getYPos(), null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {


        int action = MotionEventCompat.getActionMasked(motionEvent);


        switch (action) {

            case MotionEvent.ACTION_DOWN:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();

                //CHECK IF USER HAS POINTED ON ANY OF THE VEHICLES
                setInitialCondition(sailingBoat,
                        yPos,
                        xPos,
                        sailingBoat.getVehicleWidth(),
                        sailingBoat.getVehicleHeight());

                setInitialCondition(oak,
                        yPos,
                        xPos,
                        oak.getVehicleWidth(),
                        oak.getVehicleHeight());

                setInitialCondition(ship,
                        yPos,
                        xPos,
                        ship.getVehicleWidth(),
                        ship.getVehicleHeight());


            case MotionEvent.ACTION_MOVE:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();


                disabledVehicles.clear();
                disabledVehicles.add(oak);
                disabledVehicles.add(ship);
                setMovingCondition(sailingBoat,
                        disabledVehicles,
                        yPos,
                        xPos,
                        sailingBoat.getVehicleWidth(),
                        sailingBoat.getVehicleHeight());

                disabledVehicles.clear();
                disabledVehicles.add(sailingBoat);
                disabledVehicles.add(ship);
                setMovingCondition(oak,
                        disabledVehicles,
                        yPos,
                        xPos,
                        oak.getVehicleWidth(),
                        oak.getVehicleHeight());

                disabledVehicles.clear();
                disabledVehicles.add(sailingBoat);
                disabledVehicles.add(oak);
                setMovingCondition(ship,
                        disabledVehicles,
                        yPos,
                        xPos,
                        ship.getVehicleWidth(),
                        ship.getVehicleHeight());

                disabledVehicles.clear();

                break;

            case MotionEvent.ACTION_UP: {

                checkIfMoving(sailingBoat);
                checkIfMoving(oak);
                checkIfMoving(ship);


                decideAnimation(sailingBoat,
                        65 * displayHeight / 100 - sailingBoat.getVehicleHeight(),     //limit if to drop it on the road1
                        65 * displayHeight / 100 - sailingBoat.getVehicleHeight(),     //point of where to place vehicle if pulled up
                        85 * displayHeight / 100 - sailingBoat.getVehicleHeight(),         //limit if to pull vehicle up on the road1
                        85 * displayHeight / 100 - sailingBoat.getVehicleHeight(),      //limit if to draw vehicle back ...same as lowerborder??
                        sailingBoat.getStartPosY(),        // where to place car at Y
                        sailingBoat.getStartPosX());       // where to place car at X


                decideAnimation(oak,
                        80 * displayHeight / 100 - oak.getVehicleHeight(),     //limit if to drop it on the road1
                        80 * displayHeight / 100 - oak.getVehicleHeight(),     //point of where to place vehicle if pulled up
                        90 * displayHeight / 100 - oak.getVehicleHeight(),         //limit if to pull vehicle up on the road1
                        90 * displayHeight / 100 - oak.getVehicleHeight(),
                        oak.getStartPosY(),
                        oak.getStartPosX());

                decideAnimation(ship,
                        65 * displayHeight / 100 - ship.getVehicleHeight(),     //limit if to drop it on the road1
                        65 * displayHeight / 100 - ship.getVehicleHeight(),     //point of where to place vehicle if pulled up
                        85 * displayHeight / 100 - ship.getVehicleHeight(),         //limit if to pull vehicle up on the road1
                        85 * displayHeight / 100 - ship.getVehicleHeight(),
                        ship.getStartPosY(),
                        ship.getStartPosX());




                break;
            }
        }
        return true;

    }




    void vehicleAnimators(final Vehicle vehicle, int animatorNumber, float destXPos, float destYPos) {
        vehicle.setTouchable(false);

        if (vehicle == ship) {
            vehicleAnimator = shipAnimator;
        } else if (vehicle == oak) {
            vehicleAnimator = oakAnimator;
        } else if (vehicle == sailingBoat) {
            vehicleAnimator = sailingboatAnimator;
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

                        if (vehicle == ship) {
                            shipAnimator = vehicleAnimator;
                        } else if (vehicle == oak) {
                            oakAnimator = vehicleAnimator;
                        } else if (vehicle == sailingBoat) {
                            sailingboatAnimator = vehicleAnimator;
                        }

                        vehicle.setYPos((Float) vehicleAnimator.getAnimatedValue("yPos"));
                        vehicle.setXPos((Float) vehicleAnimator.getAnimatedValue("xPos"));

                    }
                });
                if (vehicle == ship) {
                    vehicleAnimator.addListener(shipListener);
                } else if (vehicle == oak) {
                    vehicleAnimator.addListener(oakListener);
                } else if (vehicle == sailingBoat) {
                    vehicleAnimator.addListener(sailingBoatListener);
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
                        if (vehicle == ship) {
                            shipAnimator = vehicleAnimator;
                        } else if (vehicle == oak) {
                            oakAnimator = vehicleAnimator;
                        } else if (vehicle == sailingBoat) {
                            sailingboatAnimator = vehicleAnimator;
                        }

                        vehicle.setYPos((Float) animation.getAnimatedValue());

                    }
                });
                if (vehicle == ship) {
                    vehicleAnimator.addListener(shipListener);
                } else if (vehicle == oak) {
                    vehicleAnimator.addListener(oakListener);
                } else if (vehicle == sailingBoat) {
                    vehicleAnimator.addListener(sailingBoatListener);
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
                        if (vehicle == ship) {
                            shipAnimator = vehicleAnimator;
                        } else if (vehicle == oak) {
                            oakAnimator = vehicleAnimator;
                        } else if (vehicle == sailingBoat) {
                            sailingboatAnimator = vehicleAnimator;
                        }
                        vehicle.setYPos((Float) animation.getAnimatedValue());

                    }
                });
                if (vehicle == ship) {
                    vehicleAnimator.addListener(shipListener);
                } else if (vehicle == oak) {
                    vehicleAnimator.addListener(oakListener);
                } else if (vehicle == sailingBoat) {
                    vehicleAnimator.addListener(sailingBoatListener);
                }
                vehicleAnimator.start();
                break;

            default:
                break;

        }
    }

    void animateItems() {

        sailingBoat.setTouchable(false);
        oak.setTouchable(false);
        ship.setTouchable(false);

        sailingboatAnimator = ObjectAnimator.ofFloat(sailingBoat, "xPos", sailingBoat.getXPos(), (((displayWidth-(displayWidth/2))-400) - 850) + displayWidth);
        sailingboatAnimator.setDuration(6000);
        sailingboatAnimator.setInterpolator(new AccelerateInterpolator());
        sailingboatAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sailingBoat.setXPos((Float) sailingboatAnimator.getAnimatedValue());

            }
        });


        shipAnimator = ObjectAnimator.ofFloat(ship, "xPos", ship.getXPos(), ((displayWidth-(displayWidth/2))+400)+displayWidth);
        shipAnimator.setDuration(6000);
        shipAnimator.setInterpolator(new AccelerateInterpolator());
        shipAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ship.setXPos((Float) shipAnimator.getAnimatedValue());

            }
        });


        AnimatorSet vehicleAnimationSet = new AnimatorSet();
        vehicleAnimationSet.addListener(sceneAnimator);
        vehicleAnimationSet.play(sailingboatAnimator)
                .with(shipAnimator);
        vehicleAnimationSet.start();


        if (!oak.getFlagDrawBack()
                && sailingBoat.getFlagInPlace()
                && ship.getFlagInPlace())

        {
            sailingBoat.setTouchable(false);
            ship.setTouchable(false);
        }
    }


    ObjectAnimator.AnimatorListener sailingBoatListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            if (!sailingBoat.getFlagDrawBack()
                    && oak.getFlagInPlace()
                    && ship.getFlagInPlace()) {
                oak.setTouchable(false);
                ship.setTouchable(false);
            }

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            sailingBoat.setTouchable(true);


            if (!sailingBoat.getFlagDrawBack()) {
                sailingBoat.flagInPlace(true);
            } else if (sailingBoat.getFlagDrawBack())
                sailingBoat.flagDrawBack(false);

            if (sailingBoat.getFlagInPlace()
                    && oak.getFlagInPlace()
                    && ship.getFlagInPlace()) {
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
    ObjectAnimator.AnimatorListener oakListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            if (!oak.getFlagDrawBack()
                    && sailingBoat.getFlagInPlace()
                    && ship.getFlagInPlace()) {
                sailingBoat.setTouchable(false);
                ship.setTouchable(false);
            }

        }

        @Override
        public void onAnimationEnd(Animator animation) {


            oak.setTouchable(true);


            if (!oak.getFlagDrawBack()) {
                oak.flagInPlace(true);
            } else if (oak.getFlagDrawBack())
                oak.flagDrawBack(false);

            if (sailingBoat.getFlagInPlace()
                    && oak.getFlagInPlace()
                    && ship.getFlagInPlace()) {
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
    ObjectAnimator.AnimatorListener shipListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {


            if (!ship.getFlagDrawBack()
                    && oak.getFlagInPlace()
                    && sailingBoat.getFlagInPlace()) {
                oak.setTouchable(false);
                sailingBoat.setTouchable(false);

            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {

            ship.setTouchable(true);


            if (!ship.getFlagDrawBack()) {
                ship.flagInPlace(true);
            } else if (ship.getFlagDrawBack())
                ship.flagDrawBack(false);

            if (sailingBoat.getFlagInPlace()
                    && oak.getFlagInPlace()
                    && ship.getFlagInPlace()) {
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

            if (vehicle == ship)
                vehicleAnimators(ship,2, 0, (float) upperBorder);
            else if (vehicle == oak)
                vehicleAnimators(oak,2, 0, (float) upperBorder);
            else if (vehicle == sailingBoat)
                vehicleAnimators(sailingBoat,2, 0, (float) upperBorder);

            //SHOULD VEHICLE BE LIFT UP
        } else if (vehicle.getYPos() > upperBorder
                && vehicle.getYPos() < lowerBorder
                && vehicle.getFlagHasMoved()) {
            vehicle.setOffsetPos(true);

            if (vehicle == ship)
                vehicleAnimators(ship,3, 0, (float) drawVar);
            else if (vehicle == oak)
                vehicleAnimators(oak,3, 0, (float) drawVar);
            else if (vehicle == sailingBoat)
                vehicleAnimators(sailingBoat,3, 0, (float) drawVar);

            //SHOULD VEHICLE BE SENT BACK TO INITIAL POSITION?
        } else if (vehicle.getYPos() > backUpperBorder && vehicle.getFlagHasMoved()) {

            vehicle.setOffsetPos(true);
            lenghtToMoveY = toMoveY - vehicle.getYPos();
            lenghtToMoveX = vehicle.getXPos() - toMoveX;
            if (vehicle == ship)
                vehicleAnimators(ship,1, (float) toMoveX, (float) toMoveY);
            else if (vehicle == oak)
                vehicleAnimators(oak,1, (float) toMoveX, (float) toMoveY);
            else if (vehicle == sailingBoat)
                vehicleAnimators(sailingBoat,1, (float) toMoveX, (float) toMoveY);

        }
    }


    void setDisabledVehicles(Vehicle vehicle) {



        if (vehicle == oak) {
            sailingBoat.flagIsMoving(false);
            ship.flagIsMoving(false);
            oak.setAllOtherDisabled(true);
        }


        if (vehicle == sailingBoat) {
            oak.flagIsMoving(false);
            ship.flagIsMoving(false);
            sailingBoat.setAllOtherDisabled(true);
        }

        if (vehicle == ship) {
            oak.flagIsMoving(false);
            sailingBoat.flagIsMoving(false);
            ship.setAllOtherDisabled(true);
        }


    }

}

*/



