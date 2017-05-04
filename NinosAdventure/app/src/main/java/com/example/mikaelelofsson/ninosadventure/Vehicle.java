package com.example.mikaelelofsson.ninosadventure;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.view.SurfaceView;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Mikael Elofsson on 2017-02-01.
 */

public class Vehicle extends Positions {

    private GameBitmap vehicleBmp;


    public Vehicle() {
    }

    public Vehicle(float XPos, float YPos) {
    }


    public Bitmap getBitmap() {
        return vehicleBmp.scaledBmp;
    }

    public void setBitmap(SurfaceView sw, float width, float height, int drawable) {
        vehicleBmp = new GameBitmap(sw, width, height, drawable);
    }

    public Animator rollInVehicle(long duration) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "xPos", getXPos(), getStartPosX());
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(rollInVehicle);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setXPos((Float) animation.getAnimatedValue());


            }
        });

        return animator;
    }

    void setInitialCondition(float yPos, float xPos) {

        if ((xPos > getXPos())
                && (xPos < getXPos() + getWidth())
                && (yPos > getYPos())
                && (yPos < getYPos() + getHeight())
                && (isTouchable())) {
            setOffsetXPos(xPos - getXPos());
            setOffsetYPos(yPos - getYPos());
            setOffsetPos(false);
        }
    }

    boolean decideAnimation(double upperBorder,   //limit if to drop it on the road
                            double lowerBorder,    //limit if to pull vehicle up on the road1
                            double backUpperBorder,
                            Boolean menuIsOpen//limit if to draw vehicle back ...same as lowerborder??
                         ) {           // where to place car at X

        Boolean returnValue = false;

        //SHOULD VEHICLE BE DROPPED
        if (getYPos() <= upperBorder && getFlagHasMoved()) {
            setOffsetPos(true);

            final ValueAnimator vehicleAnimator = ObjectAnimator.ofFloat(this, "yPos", getYPos(), getStartPosY());
            vehicleAnimator.setDuration(700);
            vehicleAnimator.setInterpolator(new BounceInterpolator());
            vehicleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    setYPos((Float) animation.getAnimatedValue());

                }
            });

            vehicleAnimator.addListener(vehicleListener);
            vehicleAnimator.start();

            //SHOULD VEHICLE BE LIFT UP
        } else if (getYPos() > upperBorder
                && getYPos() < lowerBorder
                && getFlagHasMoved()) {
            setOffsetPos(true);

            final ValueAnimator vehicleAnimator = ObjectAnimator.ofFloat(this, "yPos", getYPos(), getStartPosY());
            vehicleAnimator.setDuration(100);
            vehicleAnimator.setInterpolator(new DecelerateInterpolator());
            vehicleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    setYPos((Float) animation.getAnimatedValue());

                }
            });

            vehicleAnimator.addListener(vehicleListener);
            vehicleAnimator.start();

            //SHOULD VEHICLE BE SENT BACK TO INITIAL POSITION?
        } else if (getYPos() > backUpperBorder && getFlagHasMoved()) {


            if  (!menuIsOpen) {
                returnValue = true;
            }

            setOffsetPos(true);

            final PropertyValuesHolder hX = PropertyValuesHolder.ofFloat("xPos", getStartPosX());
            final PropertyValuesHolder hY = PropertyValuesHolder.ofFloat("yPos", getStartPosY());
            final ValueAnimator vehicleAnimator = ObjectAnimator.ofPropertyValuesHolder(this, hX, hY);
            vehicleAnimator.setDuration(200);
            vehicleAnimator.setInterpolator(new DecelerateInterpolator());
            vehicleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                   setYPos((Float) vehicleAnimator.getAnimatedValue("yPos"));
                    setXPos((Float) vehicleAnimator.getAnimatedValue("xPos"));

                }
            });

                vehicleAnimator.addListener(vehicleListener);

           flagInPlace(false);
            flagDrawBack(true);
            vehicleAnimator.start();

        }

        return returnValue;
    }


    ObjectAnimator.AnimatorListener rollInVehicle = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

            setTouchable(false);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            setTouchable(true);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    ObjectAnimator.AnimatorListener vehicleListener = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            setTouchable(true);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

//ANIMATION METHODS


}
