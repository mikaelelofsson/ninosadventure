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
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;


/**
 * Created by Mikael Elofsson on 2017-02-16.
 */

public class TownSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder townHolder;
    GameThread gameThread;

    Context myContext;

    ArrayList<Vehicle> listOfVehicles = new ArrayList<Vehicle>();
    ArrayList<Vehicle> disabledVehicles = new ArrayList<Vehicle>();

    ValueAnimator carAnimator;
    ValueAnimator policeAnimator;
    ValueAnimator truckAnimator;
    ValueAnimator firetruckAnimator;
    ValueAnimator busAnimator;
    ValueAnimator vehicleAnimator;
    ValueAnimator menuAnimator;
    ValueAnimator walkingAnimator;
    AnimatorSet allVehicles;

    Vehicle bus;
    Vehicle police;
    Vehicle truck;
    Vehicle car;
    Vehicle firetruck;
    Vehicle vehicleToMove;

    BackgroundElement busStop;
    BackgroundElement busStopCloser;
    BackgroundElement vehiclemenu;

    Character nino;
    Character characterToMove;

    static Boolean closeUpView = false;
    Boolean keepTouchEvent = true;
    Boolean menuIsOpen = false;
    Boolean showVehicles = false;
    Boolean vehiclesRolledIn = false;
    Boolean cutToCloseUp = false;


    Bitmap busStopBmp;
    Bitmap busStopCloserBmp;
    Bitmap menuBmp;
    float distance;
    float menuYPos;

    StaticVariables sc;

    float xPos, yPos;
    float displayHeight, displayWidth;
    float lenghtToMoveY, lenghtToMoveX;

    public TownSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.myContext = context;
        townHolder = getHolder();
        townHolder.addCallback(this);

        sc = new StaticVariables();

        ScreenSize screenSize = new ScreenSize(context);
        displayWidth = screenSize.getDisplayWidth();
        displayHeight = screenSize.getDisplayHeight();


        //CREATE ALL VEHICLE OBJECTS

        bus = new Vehicle();
        bus.setWidth((int) (30 * displayWidth / 100));
        bus.setHeight(((26 * bus.getWidth() / 100)));

        police = new Vehicle();
        police.setWidth((int) (10 * displayWidth / 100));
        police.setHeight((45 * police.getWidth() / 100));

        car = new Vehicle();
        car.setWidth((int) (10 * displayWidth / 100));
        car.setHeight((int) (45 * car.getWidth() / 100));

        truck = new Vehicle();
        truck.setWidth((int) (20 * displayWidth / 100));
        truck.setHeight((int) (39 * truck.getWidth() / 100));

        firetruck = new Vehicle();
        firetruck.setWidth((int) (18 * displayWidth / 100));
        firetruck.setHeight((int) (43 * firetruck.getWidth() / 100));

        nino = new Character();
        nino.setHeight((int) (8 * displayHeight / 100));
        nino.setWidth(((60 * nino.getHeight() / 100)));


        distance = (displayWidth -
                        (bus.getWidth()
                        + police.getWidth()
                        + car.getWidth()
                        + truck.getWidth()
                        + firetruck.getWidth()))
                / 6;

        bus.setNewPositions(-bus.getWidth(),(displayHeight - (110 * bus.getHeight() / 100)));
        bus.setStartPosX(distance);
        bus.setStartPosY(bus.getYPos());

        police.setNewPositions(-police.getWidth(),(displayHeight - (110 * police.getHeight() / 100)));
        police.setStartPosX(bus.getWidth() + distance * 2);
        police.setStartPosY((displayHeight - (110 * car.getHeight() / 100)));

        car.setNewPositions(-car.getWidth(),(displayHeight - (110 * car.getHeight() / 100)));
        car.setStartPosX(bus.getWidth() + police.getWidth() + distance * 3);
        car.setStartPosY(car.getYPos());

        truck.setNewPositions(displayWidth,(displayHeight - (105 * truck.getHeight() / 100)));
        truck.setStartPosX(bus.getWidth() + police.getWidth() + car.getWidth() + distance * 4);
        truck.setStartPosY(truck.getYPos());

        firetruck.setNewPositions(displayWidth,(displayHeight - (110 * firetruck.getHeight() / 100)));
        firetruck.setStartPosX(displayWidth - firetruck.getWidth() - distance);
        firetruck.setStartPosY(firetruck.getYPos());

        listOfVehicles.add(bus);
        listOfVehicles.add(police);
        listOfVehicles.add(truck);
        listOfVehicles.add(firetruck);
        listOfVehicles.add(car);


        nino.setNewPositions(displayWidth, 82 * displayHeight / 100 - nino.getHeight());
        nino.setStartPosX(nino.getXPos());
        nino.setStartPosY(nino.getYPos());

        nino.setArmPos(nino.leftArm, nino.LEFTARM_XFACTOR, nino.LEFTARM_YFACTOR);
        nino.setArmSize(nino.leftArm, 70, 70);
        nino.setArmPos(nino.rightArm, nino.RIGHTARM_XFACTOR, nino.RIGHTARM_YFACTOR);
        nino.setArmSize(nino.rightArm, 70, 70);

        nino.setEyePos(nino.leftEye, nino.LEFTEYE_XFACTOR, nino.LEFTEYE_YFACTOR);
        nino.setEyeSize(nino.leftEye, (float) 100, 20);
        nino.setEyePos(nino.rightEye, nino.RIGHTEYE_XFACTOR, nino.RIGHTEYE_YFACTOR);
        nino.setEyeSize(nino.rightEye, (float) 100, 20);

        nino.setWalkingBitmap(this, nino.getWidth(), nino.getHeight(),
                R.drawable.nino_scaled_01_flip,
                R.drawable.nino_scaled_02_flip,
                R.drawable.nino_scaled_03_flip,
                R.drawable.nino_scaled_04_flip,
                R.drawable.nino_scaled_05_flip,
                R.drawable.nino_scaled_06_flip,
                R.drawable.nino_scaled_07_flip,
                R.drawable.nino_scaled_08_flip);

        busStopCloser = new BackgroundElement();
        busStopCloser.setWidth(70 * displayWidth / 100);
        busStopCloser.setHeight((55 * busStopCloser.getWidth() / 100));
        busStopCloser.setXPos(-30 * displayWidth / 100);
        busStopCloser.setYPos(((float) 35 * displayHeight / 100));
        busStopCloser.setStartPosX(busStopCloser.getXPos());
        busStopCloser.setStartPosY(busStopCloser.getYPos());

        busStop = new BackgroundElement();
        busStop.setWidth(13 * displayWidth / 100);
        busStop.setHeight((50 * busStop.getWidth() / 100));
        busStop.setXPos(60 * displayWidth / 100);
        busStop.setYPos(((float) 82.5 * displayHeight / 100 - busStop.getHeight()));
        busStop.setStartPosX(busStop.getXPos());
        busStop.setStartPosY(busStop.getYPos());


        vehiclemenu = new BackgroundElement();
        vehiclemenu.setWidth(displayWidth);
        vehiclemenu.setHeight(getMenuHeight(listOfVehicles, distance));
        vehiclemenu.setXPos(0);
        vehiclemenu.setYPos(displayHeight);
        vehiclemenu.setStartPosX(vehiclemenu.getXPos());
        vehiclemenu.setStartPosY(vehiclemenu.getYPos());

        //rectHeight = getRectHeight(listOfVehicles, distance);

    bus.setBitmap(this, bus.getWidth(), bus.getHeight(), R.drawable.nybuss_mini_scaled);
    police.setBitmap(this, police.getWidth(), police.getHeight(), R.drawable.police);
    truck.setBitmap(this, truck.getWidth(), truck.getHeight(), R.drawable.truck);
    car.setBitmap(this, car.getWidth(), car.getHeight(), R.drawable.car);
    firetruck.setBitmap(this, firetruck.getWidth(), firetruck.getHeight(), R.drawable.firetruck);


        busStopBmp = createBitmap(busStop.getWidth(), busStop.getHeight(), R.drawable.busstop);
        busStopCloserBmp = createBitmap(busStopCloser.getWidth(), busStopCloser.getHeight(), R.drawable.busstop);

        nino.setBitmap(this, nino.getWidth(), nino.getHeight(), R.drawable.nino_body_scaled);
        menuBmp = createBitmap(vehiclemenu.getWidth(), vehiclemenu.getHeight(), R.drawable.vehiclemenu_scaled);


        nino.leftArm.setBitmap(this, nino.leftArm.getWidth(), nino.leftArm.getHeight(), R.drawable.nino_leftarm_scaled);
        nino.rightArm.setBitmap(this, nino.rightArm.getWidth(), nino.rightArm.getHeight(), R.drawable.nino_rightarm_scaled);
        nino.leftEye.setBitmap(this, nino.leftEye.getWidth(), nino.leftEye.getHeight(), R.drawable.eye_scaled);
        nino.rightEye.setBitmap(this, nino.rightEye.getWidth(), nino.rightEye.getHeight(), R.drawable.eye_scaled);


        allVehicles = new AnimatorSet();
        allVehicles.addListener(rollInVehicles);
        allVehicles.play(bus.rollInVehicle(1400)).with(police.rollInVehicle(1400))
                .with(car.rollInVehicle(1400))
                .with(truck.rollInVehicle(1400))
                .with(firetruck.rollInVehicle(1400));

        //List of Bitmaps that is iterated over when recycling in onSurfaceDestroyed


        animateWalking(busStop.getXPos() + busStop.getWidth(), 6000);


    }



    public void animateInVehicles() {
        allVehicles.start();
    }

    public void animateMenu (float yPos, long duration) {

        menuAnimator = ObjectAnimator.ofFloat(vehiclemenu, "yPos", vehiclemenu.getYPos(), yPos);
        menuAnimator.setDuration(duration);
        menuAnimator.setInterpolator(new DecelerateInterpolator());
        menuAnimator.addListener(menuListener);
        menuAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                vehiclemenu.setYPos((Float) animation.getAnimatedValue());

            }
        });

        menuAnimator.start();


    }
    public void animateWalking (float xPos, long duration) {

        walkingAnimator = ObjectAnimator.ofFloat(nino, "xPos", nino.getXPos(), xPos);
        walkingAnimator.setDuration(duration);
        walkingAnimator.setInterpolator(new LinearInterpolator());
        walkingAnimator.addListener(characterWalking);
        walkingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                nino.setXPos((Float) animation.getAnimatedValue());


            }
        });



        walkingAnimator.start();
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
        closeUpView = false;

    }

    public float getMenuHeight(ArrayList<Vehicle> listOfVehicles, float distance) {

        int maxNumber = 0;
        int x;

        for (int i = 0; i < listOfVehicles.size();i++) {
            x = Math.max(maxNumber, (int)listOfVehicles.get(i).getHeight());
            maxNumber = x;
        }
            return maxNumber + distance;
        }

    public float getMenuYPos(ArrayList<Vehicle> listOfVehicles, float distance) {

        int maxNumber = 0;
        int x;


        for (int i = 0; i < listOfVehicles.size();i++) {
            x = Math.max(maxNumber, (int)listOfVehicles.get(i).getHeight());
            maxNumber = x;
        }

        menuYPos = displayHeight - maxNumber - distance;
        return menuYPos;

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
        /*
        Intent intent = new Intent(myContext, TrainStationActivity.class);
        myContext.startActivity(intent);
        */
    }
    public void exitAndReturnToMain () {

        gameThread.setRunning(false);

        Intent intent = new Intent(myContext, MainActivity.class);

        if (walkingAnimator.isRunning()) {
            walkingAnimator.end();
        }

        if (menuAnimator.isRunning()) {
            menuAnimator.end();
        }

        if (allVehicles.isRunning()) {
            allVehicles.end();
        }

        nino = null;
        bus = null;
        truck = null;
        firetruck = null;
        police = null;
        car = truck;
        busStop = null;


        myContext.startActivity(intent);


    }



    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);

        canvas.drawBitmap(menuBmp, vehiclemenu.getXPos(), vehiclemenu.getYPos(), null);


        if (!closeUpView ) {
            if(!nino.characterIsWalking && showVehicles) {

                canvas.drawBitmap(truck.getBitmap(), truck.getXPos(), truck.getYPos(), null);
                canvas.drawBitmap(firetruck.getBitmap(), firetruck.getXPos(), firetruck.getYPos(), null);
                canvas.drawBitmap(bus.getBitmap(), bus.getXPos(), bus.getYPos(), null);
                canvas.drawBitmap(car.getBitmap(), car.getXPos(), car.getYPos(), null);
                canvas.drawBitmap(police.getBitmap(), police.getXPos(), police.getYPos(), null);
            }
            canvas.drawBitmap(busStopBmp, busStop.getXPos(), busStop.getYPos(), null);

        } else {
            if(!cutToCloseUp) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cutToCloseUp = true;
            }
            canvas.drawBitmap(busStopCloserBmp, busStopCloser.getXPos(), busStopCloser.getYPos(), null);
        }

        if (nino.characterIsWalking) {
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            canvas.drawBitmap(nino.getWalkingBitmap(), nino.getXPos(), nino.getYPos(), null);
        }

        else {
            if (!sc.removeCharacter) {
                canvas.drawBitmap(nino.leftArm.getBitmap(), nino.leftArm.getXPos(), nino.leftArm.getYPos(), null);
                canvas.drawBitmap(nino.rightArm.getBitmap(), nino.rightArm.getXPos(), nino.rightArm.getYPos(), null);
                canvas.drawBitmap(nino.getBitmap(), nino.getXPos(), nino.getYPos(), null);
                canvas.drawBitmap(nino.leftEye.getBitmap(), nino.leftEye.getXPos(), nino.leftEye.getYPos(), null);
                canvas.drawBitmap(nino.rightEye.getBitmap(), nino.rightEye.getXPos(), nino.rightEye.getYPos(), null);
            }
        }
    }

    void setNewCharacterSizes () {

        nino.setHeight((int) (40 * displayHeight / 100));
        nino.setWidth(((60 * nino.getHeight() / 100)));

        nino.setNewPositions(busStopCloser.getXPos() + busStopCloser.getWidth(), 97 * displayHeight / 100 - nino.getHeight());
        nino.setStartPosX(nino.getXPos());
        nino.setStartPosY(nino.getYPos());

        nino.updateArmAndFeetPos();

        nino.setBitmap(this, nino.getWidth(), nino.getHeight(), R.drawable.nino_body_scaled);
        nino.leftArm.setBitmap(this, nino.leftArm.getWidth(), nino.leftArm.getHeight(), R.drawable.nino_leftarm_scaled);
        nino.rightArm.setBitmap(this, nino.rightArm.getWidth(), nino.rightArm.getHeight(), R.drawable.nino_rightarm_scaled);
        nino.leftEye.setBitmap(this, nino.leftEye.getWidth(), nino.leftEye.getHeight(), R.drawable.eye_scaled);
        nino.rightEye.setBitmap(this, nino.rightEye.getWidth(), nino.rightEye.getHeight(), R.drawable.eye_scaled);

    }

    void setUpVehicleList (Vehicle vehicle) {
        disabledVehicles.clear();

        if (vehicle == bus) {
            disabledVehicles.add(truck);
            disabledVehicles.add(car);
            disabledVehicles.add(police);
            disabledVehicles.add(firetruck);
        }

        else if (vehicle == truck) {
            disabledVehicles.add(bus);
            disabledVehicles.add(car);
            disabledVehicles.add(police);
            disabledVehicles.add(firetruck);
        }

        else if (vehicle == car) {
            disabledVehicles.add(bus);
            disabledVehicles.add(truck);
            disabledVehicles.add(police);
            disabledVehicles.add(firetruck);
        }
        else if (vehicle == police) {
            disabledVehicles.add(bus);
            disabledVehicles.add(truck);
            disabledVehicles.add(car);
            disabledVehicles.add(firetruck);
        }
        else if (vehicle == firetruck) {
            disabledVehicles.add(bus);
            disabledVehicles.add(truck);
            disabledVehicles.add(car);
            disabledVehicles.add(police);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {


        int action = MotionEventCompat.getActionMasked(motionEvent);


        switch (action) {

            case MotionEvent.ACTION_DOWN:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();

                if (closeUpView) {
                    keepTouchEvent = nino.setInitialCondition(yPos, xPos);
                    if(keepTouchEvent){
                        characterToMove = nino;
                    }
                }

                //CHECK IF USER HAS POINTED ON ANY OF THE VEHICLES
                if (bus.setInitialCondition(yPos,xPos))
                    vehicleToMove = bus;
                if (police.setInitialCondition(yPos,xPos))
                    vehicleToMove = police;
                if (car.setInitialCondition(yPos,xPos))
                    vehicleToMove = car;
                if (truck.setInitialCondition(yPos,xPos))
                    vehicleToMove = truck;
                if (firetruck.setInitialCondition(yPos,xPos))
                    vehicleToMove = firetruck;


            case MotionEvent.ACTION_MOVE:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();

                if (characterToMove !=null) {
                    characterToMove.setMovingCondition(yPos,
                            xPos,
                            displayHeight,
                            sc.getTopOfDoor(),
                            sc.getBottomOfDoor(),
                            sc.getRightBusDoorXPs(),
                            sc.getLeftBusDoorXPs(),
                            this
                    );
                }

                if (vehicleToMove !=null) {
                    setUpVehicleList(vehicleToMove);

                    setMovingCondition(vehicleToMove,
                            disabledVehicles,
                            yPos,
                            xPos,
                            (int) bus.getWidth(),
                            (int) bus.getHeight());
                }



                disabledVehicles.clear();

                break;

            case MotionEvent.ACTION_UP: {

                vehicleToMove = null;

                checkIfMoving(bus);
                checkIfMoving(truck);
                checkIfMoving(police);
                checkIfMoving(car);
                checkIfMoving(firetruck);

                nino.checkIfMoving();

                nino.decideAnimation(
                        96 * displayHeight / 100 - nino.getHeight(),
                        96 * displayHeight / 100 - nino.getHeight(),
                        98 * displayHeight / 100 - nino.getHeight(),
                        displayHeight,
                        displayHeight - nino.getHeight(),
                        300,
                        sc.getLeftBusDoorXPs(),
                        sc.getRightBusDoorXPs(),
                        sc.getTopOfDoor(),
                        sc.getBottomOfDoor(),
                        sc.getTopOfDoor() + (15 * (sc.getBottomOfDoor() - sc.getTopOfDoor()) / 100) );



                decideAnimation(bus,
                        79.5 * displayHeight / 100 - bus.getHeight(),     //limit if to drop it on the road1
                        79.5 * displayHeight / 100 - bus.getHeight(),     //point of where to place vehicle if pulled up
                        91.5 * displayHeight / 100 - bus.getHeight(),         //limit if to pull vehicle up on the road1
                        91.5 * displayHeight / 100 - bus.getHeight(),         //limit if to draw vehicle back ...same as lowerborder??
                        bus.getStartPosY(),        // where to place car at Y
                        bus.getStartPosX());       // where to place car at X


                decideAnimation(police,
                        80.5 * displayHeight / 100 - police.getHeight(),     //limit if to drop it on the road1
                        80.5 * displayHeight / 100 - police.getHeight(),     //point of where to place vehicle if pulled up
                        90.5 * displayHeight / 100 - police.getHeight(),         //limit if to pull vehicle up on the road1
                        90.5 * displayHeight / 100 - police.getHeight(),         //limit if to draw vehicle back ...same as lowerborder??
                        police.getStartPosY(),        // where to place car at Y
                        police.getStartPosX());

                decideAnimation(truck,
                        73.5 * displayHeight / 100 - truck.getHeight(),     //limit if to drop it on the road1
                        73.5 * displayHeight / 100 - truck.getHeight(),     //point of where to place vehicle if pulled up
                        83.5 * displayHeight / 100 - truck.getHeight(),         //limit if to pull vehicle up on the road1
                        83.5 * displayHeight / 100 - truck.getHeight(),         //limit if to draw vehicle back ...same as lowerborder??
                        truck.getStartPosY(),        // where to place car at Y
                        truck.getStartPosX());

                decideAnimation(car,
                        80.5 * displayHeight / 100 - car.getHeight(),     //limit if to drop it on the road1
                        80.5 * displayHeight / 100 - car.getHeight(),     //point of where to place vehicle if pulled up
                        90.5 * displayHeight / 100 - car.getHeight(),         //limit if to pull vehicle up on the road1
                        90.5 * displayHeight / 100 - car.getHeight(),         //limit if to draw vehicle back ...same as lowerborder??
                        car.getStartPosY(),        // where to place car at Y
                        car.getStartPosX());

                decideAnimation(firetruck,
                        73.5 * displayHeight / 100 - firetruck.getHeight(),     //limit if to drop it on the road1
                        73.5 * displayHeight / 100 - firetruck.getHeight(),     //point of where to place vehicle if pulled up
                        83.5 * displayHeight / 100 - firetruck.getHeight(),         //limit if to pull vehicle up on the road1
                        83.5 * displayHeight / 100 - firetruck.getHeight(),          //limit if to draw vehicle back ...same as lowerborder??
                        firetruck.getStartPosY(),        // where to place car at Y
                        firetruck.getStartPosX());

                keepTouchEvent = true;

                break;
            }
        }
        return keepTouchEvent;

    }


    void vehicleAnimators(final Vehicle vehicle, int animatorNumber, float destXPos, float destYPos) {
        vehicle.setTouchable(false);

        if (vehicle == truck) {
            vehicleAnimator = truckAnimator;
        } else if (vehicle == police) {
            vehicleAnimator = policeAnimator;
        } else if (vehicle == bus) {
            vehicleAnimator = busAnimator;
        } else if (vehicle == car) {
            vehicleAnimator = carAnimator;
        } else if (vehicle == firetruck) {
            vehicleAnimator = firetruckAnimator;
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

                        if (vehicle == truck) {
                            truckAnimator = vehicleAnimator;
                        } else if (vehicle == police) {
                            policeAnimator = vehicleAnimator;
                        } else if (vehicle == bus) {
                            busAnimator = vehicleAnimator;
                        } else if (vehicle == car) {
                            carAnimator = vehicleAnimator;
                        } else if (vehicle == firetruck) {
                            firetruckAnimator = vehicleAnimator;
                        }

                        vehicle.setYPos((Float) vehicleAnimator.getAnimatedValue("yPos"));
                        vehicle.setXPos((Float) vehicleAnimator.getAnimatedValue("xPos"));

                    }
                });
                if (vehicle == truck) {
                    vehicleAnimator.addListener(truckListener);
                } else if (vehicle == police) {
                    vehicleAnimator.addListener(policeListener);
                } else if (vehicle == bus) {
                    vehicleAnimator.addListener(busListener);
                } else if (vehicle == car) {
                    vehicleAnimator.addListener(carListener);
                } else if (vehicle == firetruck) {
                    vehicleAnimator.addListener(firetruckListener);
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
                        if (vehicle == truck) {
                            truckAnimator = vehicleAnimator;
                        } else if (vehicle == police) {
                            policeAnimator = vehicleAnimator;
                        } else if (vehicle == bus) {
                            busAnimator = vehicleAnimator;
                        } else if (vehicle == car) {
                            carAnimator = vehicleAnimator;
                        } else if (vehicle == firetruck) {
                            firetruckAnimator = vehicleAnimator;
                        }

                        vehicle.setYPos((Float) animation.getAnimatedValue());

                    }
                });
                if (vehicle == truck) {
                    vehicleAnimator.addListener(truckListener);
                } else if (vehicle == police) {
                    vehicleAnimator.addListener(policeListener);
                } else if (vehicle == bus) {
                    vehicleAnimator.addListener(busListener);
                } else if (vehicle == car) {
                    vehicleAnimator.addListener(carListener);
                } else if (vehicle == firetruck) {
                    vehicleAnimator.addListener(firetruckListener);
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
                        if (vehicle == truck) {
                            truckAnimator = vehicleAnimator;
                        } else if (vehicle == police) {
                            policeAnimator = vehicleAnimator;
                        } else if (vehicle == bus) {
                            busAnimator = vehicleAnimator;
                        } else if (vehicle == car) {
                            carAnimator = vehicleAnimator;
                        } else if (vehicle == firetruck) {
                            firetruckAnimator = vehicleAnimator;
                        }
                        vehicle.setYPos((Float) animation.getAnimatedValue());

                    }
                });
                if (vehicle == truck) {
                    vehicleAnimator.addListener(truckListener);
                } else if (vehicle == police) {
                    vehicleAnimator.addListener(policeListener);
                } else if (vehicle == bus) {
                    vehicleAnimator.addListener(busListener);
                } else if (vehicle == car) {
                    vehicleAnimator.addListener(carListener);
                } else if (vehicle == firetruck) {
                    vehicleAnimator.addListener(firetruckListener);
                }
                vehicleAnimator.start();
                break;

            default:
                break;

        }
    }


    void animateItems(Boolean takeOff) {
        truck.setTouchable(false);
        bus.setTouchable(false);
        firetruck.setTouchable(false);
        police.setTouchable(false);
        car.setTouchable(false);




        busAnimator = ObjectAnimator.ofFloat(bus, "xPos", bus.getXPos(), (52 * displayWidth /100));
        busAnimator.setDuration(7000);
        busAnimator.setStartDelay(4000);
        busAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        busAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bus.setXPos((Float) busAnimator.getAnimatedValue());
            }
        });



        truckAnimator = ObjectAnimator.ofFloat(truck, "xPos", truck.getXPos(), 0 - truck.getWidth());
        truckAnimator.setDuration(7000);
        truckAnimator.setStartDelay(3000);
        truckAnimator.setInterpolator(new AccelerateInterpolator());
        truckAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                truck.setXPos((Float) truckAnimator.getAnimatedValue());
            }
        });

        carAnimator = ObjectAnimator.ofFloat(car, "xPos", car.getXPos(), displayWidth + car.getWidth());
        carAnimator.setDuration(5000);
        carAnimator.setStartDelay(2000);
        carAnimator.setInterpolator(new AccelerateInterpolator());
        carAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                car.setXPos((Float) carAnimator.getAnimatedValue());
            }
        });
        policeAnimator = ObjectAnimator.ofFloat(police, "xPos", police.getXPos(), displayWidth + police.getWidth());
        policeAnimator.setDuration(5000);
        policeAnimator.setInterpolator(new AccelerateInterpolator());
        policeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                police.setXPos((Float) policeAnimator.getAnimatedValue());
            }
        });

        firetruckAnimator = ObjectAnimator.ofFloat(firetruck, "xPos", firetruck.getXPos(), 0-firetruck.getWidth());
        firetruckAnimator.setDuration(5000);
        firetruckAnimator.setInterpolator(new AccelerateInterpolator());
        firetruckAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                firetruck.setXPos((Float) firetruckAnimator.getAnimatedValue());
            }
        });


        AnimatorSet vehicleAnimationSet = new AnimatorSet();
        vehicleAnimationSet.addListener(sceneAnimator);
        vehicleAnimationSet.play(firetruckAnimator)
                .with(carAnimator)
                .with(policeAnimator)
                .with(truckAnimator)
                .with(busAnimator);

        vehicleAnimationSet.start();


    }

    ObjectAnimator.AnimatorListener characterWalking = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {


        }

        @Override
        public void onAnimationEnd(Animator animation) {

            nino.characterIsWalking = false;
            nino.updateArmAndFeetPos();
            animateMenu(getMenuYPos(listOfVehicles, distance), 800);

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    ObjectAnimator.AnimatorListener rollInVehicles = new ObjectAnimator.AnimatorListener() {
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
    ObjectAnimator.AnimatorListener busListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            if (!bus.getFlagDrawBack()
                    && police.getFlagInPlace()
                    && truck.getFlagInPlace()
                    && firetruck.getFlagInPlace()
                    && car.getFlagInPlace()) {
                police.setTouchable(false);
                truck.setTouchable(false);
                firetruck.setTouchable(false);
                car.setTouchable(false);
            }

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            bus.setTouchable(true);


            if (!bus.getFlagDrawBack()) {
                bus.flagInPlace(true);
            } else if (bus.getFlagDrawBack())
                bus.flagDrawBack(false);

            if (police.getFlagInPlace()
                    && bus.getFlagInPlace()
                    && truck.getFlagInPlace()
                    && firetruck.getFlagInPlace()
                    && car.getFlagInPlace()) {
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
    ObjectAnimator.AnimatorListener policeListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            if (!police.getFlagDrawBack()
                    && bus.getFlagInPlace()
                    && truck.getFlagInPlace()
                    && firetruck.getFlagInPlace()
                    && car.getFlagInPlace()) {
                bus.setTouchable(false);
                truck.setTouchable(false);
                firetruck.setTouchable(false);
                car.setTouchable(false);
            }

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            police.setTouchable(true);


            if (!police.getFlagDrawBack()) {
                police.flagInPlace(true);
            } else if (police.getFlagDrawBack())
                police.flagDrawBack(false);

            if (police.getFlagInPlace()
                    && bus.getFlagInPlace()
                    && truck.getFlagInPlace()
                    && firetruck.getFlagInPlace()
                    && car.getFlagInPlace()) {
                animateItems(false);            }

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    ObjectAnimator.AnimatorListener truckListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            if (!truck.getFlagDrawBack()
                    && bus.getFlagInPlace()
                    && firetruck.getFlagInPlace()
                    && police.getFlagInPlace()
                    && car.getFlagInPlace()) {
                bus.setTouchable(false);
                firetruck.setTouchable(false);
                police.setTouchable(false);
                car.setTouchable(false);
            }


        }

        @Override
        public void onAnimationEnd(Animator animation) {

            truck.setTouchable(true);


            if (!truck.getFlagDrawBack()) {
                truck.flagInPlace(true);
            } else if (truck.getFlagDrawBack())
                truck.flagDrawBack(false);

            if (police.getFlagInPlace()
                    && bus.getFlagInPlace()
                    && truck.getFlagInPlace()
                    && firetruck.getFlagInPlace()
                    && car.getFlagInPlace()) {
                animateItems(false);            }

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    ObjectAnimator.AnimatorListener carListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            if (!car.getFlagDrawBack()
                    && bus.getFlagInPlace()
                    && truck.getFlagInPlace()
                    && police.getFlagInPlace()
                    && firetruck.getFlagInPlace()) {
                bus.setTouchable(false);
                truck.setTouchable(false);
                police.setTouchable(false);
                firetruck.setTouchable(false);
            }

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            car.setTouchable(true);


            if (!car.getFlagDrawBack()) {
                car.flagInPlace(true);
            } else if (car.getFlagDrawBack())
                car.flagDrawBack(false);

            if (police.getFlagInPlace()
                    && bus.getFlagInPlace()
                    && truck.getFlagInPlace()
                    && firetruck.getFlagInPlace()
                    && car.getFlagInPlace()) {
                animateItems(false);            }

        }
        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    ObjectAnimator.AnimatorListener firetruckListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            if (!firetruck.getFlagDrawBack()
                    && bus.getFlagInPlace()
                    && truck.getFlagInPlace()
                    && police.getFlagInPlace()
                    && car.getFlagInPlace()) {
                bus.setTouchable(false);
                truck.setTouchable(false);
                police.setTouchable(false);
                car.setTouchable(false);
            }

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            firetruck.setTouchable(true);


            if (!firetruck.getFlagDrawBack()) {
                firetruck.flagInPlace(true);
            } else if (firetruck.getFlagDrawBack())
                firetruck.flagDrawBack(false);

            if (police.getFlagInPlace()
                    && bus.getFlagInPlace()
                    && truck.getFlagInPlace()
                    && firetruck.getFlagInPlace()
                    && car.getFlagInPlace()) {
                animateItems(false);            }

        }
        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    ObjectAnimator.AnimatorListener menuListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {


        }

        @Override
        public void onAnimationEnd(Animator animation) {

            menuIsOpen = true;
            showVehicles = true;
            if(!vehiclesRolledIn) {
                animateInVehicles();
                vehiclesRolledIn = true;
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
           if(menuIsOpen) {
               animateMenu(displayHeight, 800);
               menuIsOpen = false;
           }

        }

        @Override
        public void onAnimationEnd(Animator animation) {
                closeUpView = true;
                keepTouchEvent = false;
                setNewCharacterSizes();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    void setMovingCondition(Vehicle vehicle, ArrayList<Vehicle> disabledVehicles, float yPos, float xPos, int widthVar, int heightVar) {

        if  (disabledVehicles.get(0).getFlagInPlace()
                && disabledVehicles.get(1).getFlagInPlace()
                && disabledVehicles.get(2).getFlagInPlace()
                && disabledVehicles.get(3).getFlagInPlace()
                && vehicle.getYPos() < menuYPos
                && menuIsOpen) {
            animateMenu(displayHeight, 800);
            menuIsOpen = false;
        }

        if  (disabledVehicles.get(0).getFlagInPlace()
                && disabledVehicles.get(1).getFlagInPlace()
                && disabledVehicles.get(2).getFlagInPlace()
                && disabledVehicles.get(3).getFlagInPlace()
                && vehicle.getYPos() > menuYPos
                && !menuIsOpen) {
            animateMenu(menuYPos, 800);
            menuIsOpen = true;
        }


        if ((vehicle.isTouchable()
                && (!disabledVehicles.get(0).getFlagIsMoving())
                && (!disabledVehicles.get(1).getFlagIsMoving())
                && (!disabledVehicles.get(2).getFlagIsMoving())
                && (!disabledVehicles.get(3).getFlagIsMoving()))){
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



    void checkIfMoving(Vehicle vehicle) {

        if (!vehicle.getFlagIsMoving()) {
            vehicle.flagHasMoved(false);
        } else {
            vehicle.flagHasMoved(true);
            vehicle.flagIsMoving(false);
        }
    }

    void startAnimation(Vehicle vehicle, double upperBorder, double lowerBorder, double backUpperBorder) {
        if (vehicle.decideAnimation(upperBorder, lowerBorder, backUpperBorder, menuIsOpen))
            animateMenu(menuYPos, 1000);
            menuIsOpen = true;

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

            if (vehicle == truck)
                vehicleAnimators(truck, 2, 0, (float) upperBorder);
            else if (vehicle == police)
                vehicleAnimators(police, 2, 0, (float) upperBorder);
            else if (vehicle == bus)
                vehicleAnimators(bus, 2, 0, (float) upperBorder);
            else if (vehicle == car)
                vehicleAnimators(car, 2, 0, (float) upperBorder);
            else if (vehicle == firetruck)
                vehicleAnimators(firetruck, 2, 0, (float) upperBorder);

            //SHOULD VEHICLE BE LIFT UP
        } else if (vehicle.getYPos() > upperBorder
                && vehicle.getYPos() < lowerBorder
                && vehicle.getFlagHasMoved()) {
            vehicle.setOffsetPos(true);

            if (vehicle == truck)
                vehicleAnimators(truck, 3, 0, (float) drawVar);
            else if (vehicle == police)
                vehicleAnimators(police, 3, 0, (float) drawVar);
            else if (vehicle == bus)
                vehicleAnimators(bus, 3, 0, (float) drawVar);
            else if (vehicle == car)
                vehicleAnimators(car, 3, 0, (float) drawVar);
            else if (vehicle == firetruck)
                vehicleAnimators(firetruck, 3, 0, (float) drawVar);

            //SHOULD VEHICLE BE SENT BACK TO INITIAL POSITION?
        } else if (vehicle.getYPos() > backUpperBorder && vehicle.getFlagHasMoved()) {

            if  (!menuIsOpen) {
                animateMenu(menuYPos, 1000);
                menuIsOpen = true;
            }

            vehicle.setOffsetPos(true);
            lenghtToMoveY = displayHeight - toMoveY - vehicle.getYPos();
            lenghtToMoveX = vehicle.getXPos() - toMoveX;
            if (vehicle == truck)
                vehicleAnimators(truck, 1, toMoveX, toMoveY);
            else if (vehicle == police)
                vehicleAnimators(police,1, toMoveX,toMoveY);
            else if (vehicle == bus)
                vehicleAnimators(bus,   1, toMoveX,toMoveY);
            else if (vehicle == car)
                vehicleAnimators(car,   1, toMoveX,toMoveY);
            else if (vehicle == firetruck)
                vehicleAnimators(firetruck,1,toMoveX, toMoveY);

        }
    }


    void setDisabledVehicles(Vehicle vehicle) {


        if (vehicle == police) {
            car.flagIsMoving(false);
            truck.flagIsMoving(false);
            firetruck.flagIsMoving(false);
            bus.flagIsMoving(false);

            police.setAllOtherDisabled(true);
        }


        if (vehicle == bus) {
            police.flagIsMoving(false);
            truck.flagIsMoving(false);
            firetruck.flagIsMoving(false);
            car.flagIsMoving(false);

            bus.setAllOtherDisabled(true);
        }

        if (vehicle == truck) {
            police.flagIsMoving(false);
            car.flagIsMoving(false);
            firetruck.flagIsMoving(false);
            bus.flagIsMoving(false);

            truck.setAllOtherDisabled(true);
        }

        if (vehicle == car) {
            police.flagIsMoving(false);
            truck.flagIsMoving(false);
            firetruck.flagIsMoving(false);
            bus.flagIsMoving(false);

            car.setAllOtherDisabled(true);
        }

        if (vehicle == firetruck) {
            police.flagIsMoving(false);
            truck.flagIsMoving(false);
            car.flagIsMoving(false);
            bus.flagIsMoving(false);

            firetruck.setAllOtherDisabled(true);
        }


    }


}






