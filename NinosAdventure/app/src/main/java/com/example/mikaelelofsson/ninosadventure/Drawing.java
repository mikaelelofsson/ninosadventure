package com.example.mikaelelofsson.ninosadventure;





import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
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
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;


public class Drawing extends View implements View.OnTouchListener {

    Paint rectPaint;
    Context myContext;

    ArrayList<Vehicle> listOfVehicles = new ArrayList<Vehicle>();
    ArrayList<Vehicle> disabledVehicles = new ArrayList<Vehicle>();

    ValueAnimator truckAnimator;
    ValueAnimator taxiAnimator;
    ValueAnimator carAnimator;
    ValueAnimator firetruckAnimator;
    ValueAnimator busAnimator;

    Vehicle bus;
    Vehicle taxi;
    Vehicle truck;
    Vehicle car;
    Vehicle firetruck;

    float xPos, yPos;
    float startOfScreen, endOfScreen;
    float displayHeight, displayWidth;
    float lenghtToMoveY, lenghtToMoveX;

    Bitmap origBusBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.bussyellow);
    Bitmap busBmp = Bitmap.createScaledBitmap(origBusBmp, 500, 200, true);

    Bitmap origTruckBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.truck);
    Bitmap truckBmp = Bitmap.createScaledBitmap(origTruckBmp, 500, 200, true);

    Bitmap origTaxiBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.police);
    Bitmap taxiBmp = Bitmap.createScaledBitmap(origTaxiBmp, 350, 150, true);

    Bitmap origCarBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.car);
    Bitmap carBmp = Bitmap.createScaledBitmap(origCarBmp, 350, 150, true);

    Bitmap origFireBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.firetruck);
    Bitmap firetruckBmp = Bitmap.createScaledBitmap(origFireBmp, 500, 200, true);


    Paint circleColor = new Paint();

    public Drawing(final Context context, AttributeSet attrs) {
        super(context, attrs);

        this.myContext = context;
        rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        displayHeight = metrics.heightPixels;
        displayWidth = metrics.widthPixels;
        endOfScreen = displayWidth + 250;
        startOfScreen = -500;

        this.setOnTouchListener(this);


        bus = new Vehicle(0, displayHeight - 220);
        truck = new Vehicle(550, displayHeight - 220);
        taxi = new Vehicle(1100, displayHeight - 180);
        car = new Vehicle(1500, displayHeight - 175);
        firetruck = new Vehicle(1900, displayHeight - 220);
        listOfVehicles.add(bus);
        listOfVehicles.add(truck);
        listOfVehicles.add(taxi);
        listOfVehicles.add(car);
        listOfVehicles.add(firetruck);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       canvas.drawRect(0, displayHeight - 230, displayWidth, displayHeight, rectPaint);

        canvas.drawBitmap(truckBmp, truck.getXPos(), truck.getYPos(), null);
        canvas.drawBitmap(firetruckBmp, firetruck.getXPos(), firetruck.getYPos(), null);
        canvas.drawBitmap(busBmp, bus.getXPos(), bus.getYPos(), null);
        canvas.drawBitmap(carBmp, car.getXPos(), car.getYPos(), null);
        canvas.drawBitmap(taxiBmp, taxi.getXPos(), taxi.getYPos(), null);


    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        int action = MotionEventCompat.getActionMasked(motionEvent);


        switch (action) {

            case MotionEvent.ACTION_DOWN:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();

                //CHECK IF USER HAS POINTED ON ANY OF THE VEHICLES
                setInitialCondition(bus, yPos, xPos, 500, 200);
                setInitialCondition(truck, yPos, xPos, 500, 200);
                setInitialCondition(car, yPos, xPos, 500, 200);
                setInitialCondition(taxi, yPos, xPos, 500, 200);
                setInitialCondition(firetruck, yPos, xPos, 500, 200);


            case MotionEvent.ACTION_MOVE:

                xPos = motionEvent.getX();
                yPos = motionEvent.getY();

                disabledVehicles.add(truck);
                disabledVehicles.add(car);
                disabledVehicles.add(taxi);
                disabledVehicles.add(firetruck);
                setMovingCondition(bus, disabledVehicles, yPos, xPos, 500, 200);

                disabledVehicles.clear();
                disabledVehicles.add(bus);
                disabledVehicles.add(car);
                disabledVehicles.add(taxi);
                disabledVehicles.add(firetruck);
                setMovingCondition(truck, disabledVehicles, yPos, xPos, 500, 200);

                disabledVehicles.clear();
                disabledVehicles.add(truck);
                disabledVehicles.add(bus);
                disabledVehicles.add(taxi);
                disabledVehicles.add(firetruck);
                setMovingCondition(car, disabledVehicles, yPos, xPos, 500, 200);

                disabledVehicles.clear();
                disabledVehicles.add(truck);
                disabledVehicles.add(car);
                disabledVehicles.add(bus);
                disabledVehicles.add(firetruck);
                setMovingCondition(taxi, disabledVehicles, yPos, xPos, 500, 200);

                disabledVehicles.clear();
                disabledVehicles.add(truck);
                disabledVehicles.add(car);
                disabledVehicles.add(bus);
                disabledVehicles.add(taxi);
                setMovingCondition(firetruck, disabledVehicles, yPos, xPos, 500, 200);

                disabledVehicles.clear();
                invalidate();

                break;

            case MotionEvent.ACTION_UP: {

                checkIfMoving(bus);
                checkIfMoving(truck);
                checkIfMoving(taxi);
                checkIfMoving(car);
                checkIfMoving(firetruck);

                decideAnimation(bus, 490, 490, 400, 400, 220, 0, 1);

                decideAnimation(truck, 580, 580, 400, 400, 220, 550, 550);
                decideAnimation(taxi, 440, 470, 350, 350, 180, 1100, 1100);
                decideAnimation(car, 440, 460,350, 350, 175, 1500, 1500);
                decideAnimation(firetruck, 580, 570, 400, 400, 220, 1900, 1900);

                break;
            }
        }
        return true;

    }

    void carAnimators(int animatorNumber, float destXPos, float destYPos) {
        car.setTouchable(false);

        //DRAWBACK
        switch (animatorNumber) {

            case 1: //DRAW BACK TO STARTING POINT

                final PropertyValuesHolder hX = PropertyValuesHolder.ofFloat("xPos", destXPos);
                final PropertyValuesHolder hY = PropertyValuesHolder.ofFloat("yPos", displayHeight - destYPos);
                carAnimator = ObjectAnimator.ofPropertyValuesHolder(car, hX, hY);
                carAnimator.setDuration(200);
                carAnimator.setInterpolator(new DecelerateInterpolator());
                carAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        car.setYPos((Float) carAnimator.getAnimatedValue("yPos"));
                        car.setXPos((Float) carAnimator.getAnimatedValue("xPos"));
                        invalidate();
                    }
                });
                carAnimator.addListener(animListen);
                car.flagInPlace(false);
                car.flagDrawBack(true);
                carAnimator.start();

                break;

            case 2: // DROP ON THE ROAD

                carAnimator = ObjectAnimator.ofFloat(car, "yPos", car.getYPos(), destYPos);
                carAnimator.setDuration(700);
                carAnimator.setInterpolator(new BounceInterpolator());
                carAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        car.setYPos((Float) carAnimator.getAnimatedValue());
                        invalidate();
                    }
                });
                carAnimator.addListener(animListen);
                carAnimator.start();
                break;

            case 3: //LIFT ON TO THE ROAD

                carAnimator = ObjectAnimator.ofFloat(car, "yPos", car.getYPos(), destYPos);
                carAnimator.setDuration(100);
                carAnimator.setInterpolator(new DecelerateInterpolator());
                carAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        car.setYPos((Float) carAnimator.getAnimatedValue());
                        invalidate();
                    }
                });
                carAnimator.addListener(animListen);
                carAnimator.start();
                break;

            default:
                break;

        }
    }

    void taxiAnimators(int animatorNumber, float destXPos, float destYPos) {
        taxi.setTouchable(false);
        //DRAWBACK
        switch (animatorNumber) {

            case 1: //DRAW BACK TO STARTING POINT

                final PropertyValuesHolder hX = PropertyValuesHolder.ofFloat("xPos", destXPos);
                final PropertyValuesHolder hY = PropertyValuesHolder.ofFloat("yPos", displayHeight - destYPos);
                taxiAnimator = ObjectAnimator.ofPropertyValuesHolder(taxi, hX, hY);
                taxiAnimator.setDuration(200);
                taxiAnimator.setInterpolator(new DecelerateInterpolator());
                taxiAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        taxi.setYPos((Float) taxiAnimator.getAnimatedValue("yPos"));
                        taxi.setXPos((Float) taxiAnimator.getAnimatedValue("xPos"));
                        invalidate();
                    }
                });
                taxiAnimator.addListener(animListen);
                taxi.flagInPlace(false);
                taxi.flagDrawBack(true);
                taxiAnimator.start();
                break;

            case 2: // DROP ON THE ROAD

                taxiAnimator = ObjectAnimator.ofFloat(taxi, "yPos", taxi.getYPos(), destYPos);
                taxiAnimator.setDuration(700);
                taxiAnimator.setInterpolator(new BounceInterpolator());
                taxiAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        taxi.setYPos((Float) taxiAnimator.getAnimatedValue());
                        invalidate();
                    }
                });
                taxiAnimator.addListener(animListen);
                taxiAnimator.start();
                break;

            case 3: //LIFT ON TO THE ROAD

                taxiAnimator = ObjectAnimator.ofFloat(taxi, "yPos", taxi.getYPos(), destYPos);
                taxiAnimator.setDuration(100);
                taxiAnimator.setInterpolator(new DecelerateInterpolator());
                taxiAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        taxi.setYPos((Float) taxiAnimator.getAnimatedValue());
                        invalidate();
                    }
                });
                taxiAnimator.addListener(animListen);
                taxiAnimator.start();
                break;

            default:
                break;

        }
    }

    void busAnimators(int animatorNumber, float destXPos, float destYPos) {

        bus.setTouchable(false);
        //DRAWBACK
        switch (animatorNumber) {

            case 1: //DRAW BACK TO STARTING POINT

                final PropertyValuesHolder hX = PropertyValuesHolder.ofFloat("xPos", destXPos);
                final PropertyValuesHolder hY = PropertyValuesHolder.ofFloat("yPos", displayHeight - destYPos);
                busAnimator = ObjectAnimator.ofPropertyValuesHolder(bus, hX, hY);
                busAnimator.setDuration(200);
                busAnimator.setInterpolator(new DecelerateInterpolator());
                busAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        bus.setYPos((Float) busAnimator.getAnimatedValue("yPos"));
                        bus.setXPos((Float) busAnimator.getAnimatedValue("xPos"));
                        invalidate();
                    }
                });
                busAnimator.addListener(animListen);
                bus.flagInPlace(false);
                bus.flagDrawBack(true);
                busAnimator.start();

                break;

            case 2: // DROP ON THE ROAD

                busAnimator = ObjectAnimator.ofFloat(bus, "yPos", bus.getYPos(), destYPos);
                busAnimator.setDuration(700);
                busAnimator.setInterpolator(new BounceInterpolator());
                busAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        bus.setYPos((Float) busAnimator.getAnimatedValue());
                        invalidate();
                    }
                });
                busAnimator.addListener(animListen);
                busAnimator.start();
                break;

            case 3: //LIFT ON TO THE ROAD
                busAnimator = ObjectAnimator.ofFloat(bus, "yPos", bus.getYPos(), destYPos);
                busAnimator.setDuration(100);
                busAnimator.setInterpolator(new DecelerateInterpolator());
                busAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        bus.setYPos((Float) busAnimator.getAnimatedValue());
                        invalidate();
                    }
                });
                busAnimator.addListener(animListen);
                busAnimator.start();
                break;

            default:
                break;
        }


    }

    void truckAnimators(int animatorNumber, float destXPos, float destYPos) {
        truck.setTouchable(false);
        //DRAWBACK
        switch (animatorNumber) {

            case 1: //DRAW BACK TO STARTING POINT

                final PropertyValuesHolder hX = PropertyValuesHolder.ofFloat("xPos", destXPos);
                final PropertyValuesHolder hY = PropertyValuesHolder.ofFloat("yPos", displayHeight - destYPos);
                truckAnimator = ObjectAnimator.ofPropertyValuesHolder(truck, hX, hY);
                truckAnimator.setDuration(200);
                truckAnimator.setInterpolator(new DecelerateInterpolator());
                truckAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        truck.setYPos((Float) truckAnimator.getAnimatedValue("yPos"));
                        truck.setXPos((Float) truckAnimator.getAnimatedValue("xPos"));
                        invalidate();
                    }
                });
                truckAnimator.addListener(animListen);
                truck.flagInPlace(false);
                truck.flagDrawBack(true);
                truckAnimator.start();

                break;

            case 2: // DROP ON THE ROAD

                truckAnimator = ObjectAnimator.ofFloat(truck, "yPos", truck.getYPos(), destYPos);
                truckAnimator.setDuration(700);
                truckAnimator.setInterpolator(new BounceInterpolator());
                truckAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        truck.setYPos((Float) truckAnimator.getAnimatedValue());
                        invalidate();
                    }
                });
                truckAnimator.addListener(animListen);
                truckAnimator.start();
                break;

            case 3: //LIFT ON TO THE ROAD

                truckAnimator = ObjectAnimator.ofFloat(truck, "yPos", truck.getYPos(), destYPos);
                truckAnimator.setDuration(100);
                truckAnimator.setInterpolator(new DecelerateInterpolator());
                truckAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        truck.setYPos((Float) truckAnimator.getAnimatedValue());
                        invalidate();
                    }
                });
                truckAnimator.addListener(animListen);
                truckAnimator.start();
                break;

            default:
                break;

        }

    }

    void firetruckAnimators(int animatorNumber, float destXPos, float destYPos) {
        firetruck.setTouchable(false);
        //DRAWBACK
        switch (animatorNumber) {

            case 1: //DRAW BACK TO STARTING POINT

                final PropertyValuesHolder hX = PropertyValuesHolder.ofFloat("xPos", destXPos);
                final PropertyValuesHolder hY = PropertyValuesHolder.ofFloat("yPos", displayHeight - destYPos);
                firetruckAnimator = ObjectAnimator.ofPropertyValuesHolder(firetruck, hX, hY);
                firetruckAnimator.setDuration(200);
                firetruckAnimator.setInterpolator(new DecelerateInterpolator());
                firetruckAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        firetruck.setYPos((Float) firetruckAnimator.getAnimatedValue("yPos"));
                        firetruck.setXPos((Float) firetruckAnimator.getAnimatedValue("xPos"));
                        invalidate();
                    }
                });
                firetruckAnimator.addListener(animListen);
                firetruck.flagInPlace(false);
                firetruck.flagDrawBack(true);
                firetruckAnimator.start();

                break;

            case 2: // DROP ON THE ROAD

                firetruckAnimator = ObjectAnimator.ofFloat(firetruck, "yPos", firetruck.getYPos(), destYPos);
                firetruckAnimator.setDuration(700);
                firetruckAnimator.setInterpolator(new BounceInterpolator());
                firetruckAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        firetruck.setYPos((Float) firetruckAnimator.getAnimatedValue());
                        invalidate();
                    }
                });
                firetruckAnimator.addListener(animListen);
                firetruckAnimator.start();
                break;

            case 3: //LIFT ON TO THE ROAD

                firetruckAnimator = ObjectAnimator.ofFloat(firetruck, "yPos", firetruck.getYPos(), destYPos);
                firetruckAnimator.setDuration(100);
                firetruckAnimator.setInterpolator(new DecelerateInterpolator());
                firetruckAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        firetruck.setYPos((Float) firetruckAnimator.getAnimatedValue());
                        invalidate();
                    }
                });
                firetruckAnimator.addListener(animListen);
                firetruckAnimator.start();
                break;

            default:
                break;

        }

    }

    void animateItems() {

        bus.setTouchable(false);
        truck.setTouchable(false);
        taxi.setTouchable(false);
        car.setTouchable(false);
        firetruck.setTouchable(false);

        truckAnimator = ObjectAnimator.ofFloat(truck, "xPos", truck.getXPos(), startOfScreen);
        truckAnimator.setDuration(3500);
        truckAnimator.setStartDelay(1000);
        truckAnimator.setInterpolator(new AccelerateInterpolator());
        truckAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                truck.setXPos((Float) truckAnimator.getAnimatedValue());
                invalidate();
            }
        });

        carAnimator = ObjectAnimator.ofFloat(car, "xPos", car.getXPos(), endOfScreen);
        carAnimator.setDuration(2500);
        carAnimator.setStartDelay(1000);
        carAnimator.setInterpolator(new AccelerateInterpolator());
        carAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                car.setXPos((Float) carAnimator.getAnimatedValue());
                invalidate();
            }
        });
        taxiAnimator = ObjectAnimator.ofFloat(taxi, "xPos", taxi.getXPos(), endOfScreen);
        taxiAnimator.setDuration(2000);
        taxiAnimator.setInterpolator(new AccelerateInterpolator());
        taxiAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                taxi.setXPos((Float) taxiAnimator.getAnimatedValue());
                invalidate();
            }
        });

        busAnimator = ObjectAnimator.ofFloat(bus, "xPos", bus.getXPos(), endOfScreen);
        busAnimator.setDuration(5000);
        busAnimator.setStartDelay(1000);
        busAnimator.setInterpolator(new AccelerateInterpolator());
        busAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bus.setXPos((Float) busAnimator.getAnimatedValue());
                invalidate();
            }
        });
        firetruckAnimator = ObjectAnimator.ofFloat(firetruck, "xPos", firetruck.getXPos(), startOfScreen);
        firetruckAnimator.setDuration(3000);
        firetruckAnimator.setStartDelay(200);
        firetruckAnimator.setInterpolator(new AccelerateInterpolator());
        firetruckAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                firetruck.setXPos((Float) firetruckAnimator.getAnimatedValue());
                invalidate();
            }
        });

        AnimatorSet vehicleAnimationSet = new AnimatorSet();
        vehicleAnimationSet.addListener(sceneAnimator);
        vehicleAnimationSet.play(truckAnimator)
                .with(carAnimator)
                .with(taxiAnimator)
                .with(busAnimator)
                .with(firetruckAnimator);
        vehicleAnimationSet.start();

    }

    ObjectAnimator.AnimatorListener animListen = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            if (animation == carAnimator) {
                if ((!car.getFlagDrawBack())
                        && bus.getFlagInPlace()
                        && taxi.getFlagInPlace()
                        && truck.getFlagInPlace()
                        && firetruck.getFlagInPlace()) {
                    taxi.setTouchable(false);
                    bus.setTouchable(false);
                    truck.setTouchable(false);
                    firetruck.setTouchable(false);
                }
            } else if (animation == taxiAnimator) {
                if (!taxi.getFlagDrawBack()
                        && bus.getFlagInPlace()
                        && car.getFlagInPlace()
                        && truck.getFlagInPlace()
                        && firetruck.getFlagInPlace()) {
                    car.setTouchable(false);
                    bus.setTouchable(false);
                    truck.setTouchable(false);
                    firetruck.setTouchable(false);

                }
            } else if (animation == truckAnimator) {
                if (!truck.getFlagDrawBack()
                        && bus.getFlagInPlace()
                        && taxi.getFlagInPlace()
                        && car.getFlagInPlace()
                        && firetruck.getFlagInPlace()) {
                    taxi.setTouchable(false);
                    bus.setTouchable(false);
                    car.setTouchable(false);
                    firetruck.setTouchable(false);
                }
            } else if (animation == busAnimator) {
                if (!bus.getFlagDrawBack()
                        && car.getFlagInPlace()
                        && taxi.getFlagInPlace()
                        && truck.getFlagInPlace()
                        && firetruck.getFlagInPlace()) {
                    taxi.setTouchable(false);
                    car.setTouchable(false);
                    truck.setTouchable(false);
                    firetruck.setTouchable(false);
                }
            }else if (animation == firetruckAnimator) {
                if (!firetruck.getFlagDrawBack()
                        && car.getFlagInPlace()
                        && taxi.getFlagInPlace()
                        && truck.getFlagInPlace()
                        && bus.getFlagInPlace()) {
                    taxi.setTouchable(false);
                    car.setTouchable(false);
                    truck.setTouchable(false);
                    bus.setTouchable(false);
                }
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {


            if (animation == carAnimator) {
                car.setTouchable(true);
                if (!car.getFlagDrawBack())
                    car.flagInPlace(true);
                else if (car.getFlagDrawBack())
                    car.flagDrawBack(false);
            } else if (animation == truckAnimator) {
                truck.setTouchable(true);
                if (!truck.getFlagDrawBack())
                    truck.flagInPlace(true);
                else if (truck.getFlagDrawBack())
                    truck.flagDrawBack(false);
            } else if (animation == taxiAnimator) {
                taxi.setTouchable(true);
                if (!taxi.getFlagDrawBack())
                    taxi.flagInPlace(true);
                else if (taxi.getFlagDrawBack())
                    taxi.flagDrawBack(false);
            } else if (animation == busAnimator) {
                bus.setTouchable(true);
                if (!bus.getFlagDrawBack())
                    bus.flagInPlace(true);
                else if (bus.getFlagDrawBack())
                    bus.flagDrawBack(false);
            } else if (animation == firetruckAnimator) {
                firetruck.setTouchable(true);
                if (!firetruck.getFlagDrawBack())
                    firetruck.flagInPlace(true);
                else if (firetruck.getFlagDrawBack())
                    firetruck.flagDrawBack(false);
            }
            if (car.getFlagInPlace()
                    && bus.getFlagInPlace()
                    && taxi.getFlagInPlace()
                    && truck.getFlagInPlace()
                    && firetruck.getFlagInPlace()) {
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
            Intent intent = new Intent(myContext, ConstructionActivity.class);
            myContext.startActivity(intent);

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




        //SHOULD VEHICLE BE DRAWN UP ON THE ROAD?
        if (vehicle.getYPos() <= displayHeight - upperBorder && vehicle.getFlagHasMoved()) {
            vehicle.setOffsetPos(true);

            if (vehicle == bus)
                busAnimators(2, 0, (float) displayHeight - upperBorder);
            else if (vehicle == car)
                carAnimators(2, 0, (float) displayHeight - upperBorder);
            else if (vehicle == taxi)
                taxiAnimators(2, 0, (float) displayHeight - upperBorder);
            else if (vehicle == truck)
                truckAnimators(2, 0, (float) displayHeight - upperBorder);
            else if (vehicle == firetruck)
                firetruckAnimators(2, 0, (float) displayHeight - upperBorder);

            //SHOULD VEHICLE BE DROPPED ON THE ROAD?
        } else if (vehicle.getYPos() > displayHeight - upperBorder
                && vehicle.getYPos() < displayHeight - lowerBorder
                && vehicle.getFlagHasMoved()) {
            vehicle.setOffsetPos(true);

            if (vehicle == bus)
                busAnimators(3, 0, (float) displayHeight - drawVar);
            else if (vehicle == car)
                carAnimators(3, 0, (float) displayHeight - drawVar);
            else if (vehicle == taxi)
                taxiAnimators(3, 0, (float) displayHeight - drawVar);
            else if (vehicle == truck)
                truckAnimators(3, 0, (float) displayHeight - drawVar);
            else if (vehicle == firetruck)
                firetruckAnimators(3, 0, (float) displayHeight - drawVar);

            //SHOULD VEHICLE BE SENT BACK TO INITIAL POSITION?
        } else if (vehicle.getYPos() > displayHeight - backUpperBorder && vehicle.getFlagHasMoved()) {

            vehicle.setOffsetPos(true);
            lenghtToMoveY = displayHeight - toMoveY - vehicle.getYPos();
            lenghtToMoveX = vehicle.getXPos() - toMoveX;
            if (vehicle == bus)
                busAnimators(1, (float) animateX, (float) toMoveY);
            else if (vehicle == car)
                carAnimators(1, (float) animateX, (float) toMoveY);
            else if (vehicle == taxi)
                taxiAnimators(1, (float) animateX, (float) toMoveY);
            else if (vehicle == truck)
                truckAnimators(1, (float) animateX, (float) toMoveY);
            else if (vehicle == firetruck)
                firetruckAnimators(1, (float) animateX, (float) toMoveY);


        }
    }

    void setDisabledVehicles(Vehicle vehicle) {

        if (vehicle == bus) {
            taxi.flagIsMoving(false);
            car.flagIsMoving(false);
            truck.flagIsMoving(false);
            firetruck.flagIsMoving(false);
            bus.setAllOtherDisabled(true);
        }

        if (vehicle == taxi) {
            bus.flagIsMoving(false);
            car.flagIsMoving(false);
            truck.flagIsMoving(false);
            firetruck.flagIsMoving(false);
            taxi.setAllOtherDisabled(true);
        }

        if (vehicle == car) {
            taxi.flagIsMoving(false);
            bus.flagIsMoving(false);
            truck.flagIsMoving(false);
            firetruck.flagIsMoving(false);
            car.setAllOtherDisabled(true);
        }

        if (vehicle == truck) {
            taxi.flagIsMoving(false);
            car.flagIsMoving(false);
            bus.flagIsMoving(false);
            firetruck.flagIsMoving(false);
            truck.setAllOtherDisabled(true);
        }

        if (vehicle == firetruck) {
            taxi.flagIsMoving(false);
            car.flagIsMoving(false);
            bus.flagIsMoving(false);
            truck.flagIsMoving(false);
            firetruck.setAllOtherDisabled(true);
        }


    }

}
// }





