package com.example.mikaelelofsson.ninosadventure;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.SurfaceView;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Mikael Elofsson on 2017-02-01.
 */

public class Character extends Positions {


    final float LEFTARM_XFACTOR = -11;
    final float LEFTARM_YFACTOR = 15;
    final float RIGHTARM_XFACTOR = 31;
    final float RIGHTARM_YFACTOR = 15;
    final float LEFTEYE_XFACTOR = 16;
    final float LEFTEYE_YFACTOR = 35;
    final float RIGHTEYE_XFACTOR = 53;
    final float RIGHTEYE_YFACTOR = 35;

    int frameCount = 1;
    Arm leftArm;
    Arm rightArm;
    Eye leftEye;
    Eye rightEye;


    private GameBitmap nino_01;
    private GameBitmap nino_02;
    private GameBitmap nino_03;
    private GameBitmap nino_04;
    private GameBitmap nino_05;
    private GameBitmap nino_06;
    private GameBitmap nino_07;
    private GameBitmap nino_08;

    private Bitmap currentFrameBmp;


    boolean characterIsWalking = true;

    StaticVariables sc = new StaticVariables();

    GameBitmap characterBmp;


    public Character() {
        leftArm = new Arm();
        rightArm = new Arm();
        leftEye = new Eye();
        rightEye = new Eye();


    }

    public void updateArmAndFeetPos () {
        setArmPos(leftArm,LEFTARM_XFACTOR,LEFTARM_YFACTOR);
        setArmSize(leftArm,70,70);
        setArmPos(rightArm,RIGHTARM_XFACTOR,RIGHTARM_YFACTOR);
        setArmSize(rightArm,70,70);

        setEyePos(leftEye,LEFTEYE_XFACTOR,LEFTEYE_YFACTOR);
        setEyeSize(leftEye,(float)100,20);
        setEyePos(rightEye,RIGHTEYE_XFACTOR, RIGHTEYE_YFACTOR);
        setEyeSize(rightEye,(float)100,20);
    }

    public void setWalkingBitmap(SurfaceView sw,
                                 float width,
                                 float height,
                                 int drawable1,
                                 int drawable2,
                                 int drawable3,
                                 int drawable4,
                                 int drawable5,
                                 int drawable6,
                                 int drawable7,
                                 int drawable8) {
        nino_01 = new GameBitmap(sw, width, height, drawable1);
        nino_02 = new GameBitmap(sw, width, height, drawable2);
        nino_03 = new GameBitmap(sw, width, height, drawable3);
        nino_04 = new GameBitmap(sw, width, height, drawable4);
        nino_05 = new GameBitmap(sw, width, height, drawable5);
        nino_06 = new GameBitmap(sw, width, height, drawable6);
        nino_07 = new GameBitmap(sw, width, height, drawable7);
        nino_08 = new GameBitmap(sw, width, height, drawable8);
    }


    public Bitmap getWalkingBitmap () {



        switch (frameCount) {

            case 1:
                currentFrameBmp =  nino_01.scaledBmp;
                break;

            case 2:
                currentFrameBmp = nino_02.scaledBmp;
                break;

            case 3:
                currentFrameBmp = nino_03.scaledBmp;
                break;

            case 4:
                currentFrameBmp = nino_04.scaledBmp;
                break;

            case 5:
                currentFrameBmp = nino_05.scaledBmp;
                break;

            case 6:
                currentFrameBmp = nino_06.scaledBmp;
                break;

            case 7:
                currentFrameBmp = nino_07.scaledBmp;
                break;

            case 8:
                currentFrameBmp = nino_08.scaledBmp;
                break;

            default: currentFrameBmp = null;
        }
        if(frameCount == 8)
            frameCount = 1;
        else
        frameCount++;

        return currentFrameBmp;


    }



    public void setBitmap(SurfaceView sw, float width, float height, int drawable) {

        characterBmp = new GameBitmap(sw, width, height, drawable);

    }

    public Bitmap getBitmap() {
        return characterBmp.scaledBmp;
    }


    public void setArmPos(Arm arm, float xFactor, float yFactor) {
        float xPos = getXPos() + (xFactor * getWidth() / 100);
        float yPos = getYPos() + (yFactor * getHeight() / 100);
        arm.setXPos(xPos);
        arm.setYPos(yPos);
    }


    public void setArmSize(Arm arm, float widthFactor, float heightFactor) {

        float height = heightFactor * getHeight() / 100;
        float width = widthFactor * height / 100;


        arm.setWidth(width);
        arm.setHeight(height);
    }

    public void setEyePos(Eye eye, float xFactor, float yFactor) {
        eye.setXPos(getXPos() + (xFactor * getWidth() / 100));
        eye.setYPos(getYPos() + (yFactor * getHeight() / 100));
    }


    public void setEyeSize(Eye eye, float widthFactor, float heightFactor) {

        float height = heightFactor * getHeight() / 100;
        float width = widthFactor * height / 100;


        eye.setWidth(width);
        eye.setHeight(height);
    }

    Matrix animateArm(Arm arm) {

        Matrix matrix = new Matrix();
        arm.setChangingValue(4);
        arm.setDegrees(arm.getChangingVakue());

        matrix.postRotate((float) arm.getDegrees(), arm.getWidth() / 2, arm.getHeight() / 2);
        matrix.postTranslate(arm.getXPos(), arm.getYPos());

        return matrix;
    }




    Boolean setInitialCondition(float yPos, float xPos) {

        Boolean passOn = true;

        if ((xPos > getXPos())
                && (xPos < getXPos() + getWidth())
                && (yPos > getYPos())
                && (yPos < getYPos() + getHeight())
                && (isTouchable())) {
            setOffsetXPos(xPos - getXPos());
            setOffsetYPos(yPos - getYPos());
            setOffsetPos(false);

        } else {
            passOn = false;
        }

        return passOn;

    }

    void setMovingCondition(float yPos,
                            float xPos,
                            float displayHeight,
                            float topOfDoor,
                            float bottomOfDoor,
                            float rightDoorXPos,
                            float leftDoorXPos,
                            SurfaceView sw) {

        if ((isTouchable())) {
            if (isSetOffsetPos()) {
                setOffsetXPos(xPos - getXPos());
                setOffsetYPos(yPos - getYPos());
                setOffsetPos(false);
            }
            setXPos(xPos - getOffsetXPos());
            setYPos(yPos - getOffsetYPos());
            setArmPos(leftArm, LEFTARM_XFACTOR, LEFTARM_YFACTOR);
            setArmPos(rightArm, RIGHTARM_XFACTOR, RIGHTARM_YFACTOR);
            setEyePos(leftEye, LEFTEYE_XFACTOR, LEFTEYE_YFACTOR);
            setEyePos(rightEye, RIGHTEYE_XFACTOR, RIGHTEYE_YFACTOR);
            flagIsMoving(true);

            if (sc.doorsBeenPushed && getXPos() >= leftDoorXPos && getXPos() <= (rightDoorXPos - getWidth()) && getYPos() >= (topOfDoor - 10*displayHeight/100) && getYPos() <= bottomOfDoor - getHeight()) {


                if (!sc.minimized) {
                    setOffsetPos(false);

                    setHeight((int) (33 * displayHeight / 100));
                    setWidth(((60 * getHeight() / 100)));

                    setArmSize(leftArm, 70, 70);
                    setArmSize(rightArm, 70, 70);

                    setEyeSize(leftEye, (float) 100, 20);
                    setEyeSize(rightEye, (float) 100, 20);

                    setBitmap(sw, getWidth(), getHeight(), R.drawable.nino_body_scaled);
                    leftArm.setBitmap(sw, leftArm.getWidth(), leftArm.getHeight(), R.drawable.nino_leftarm_scaled);
                    rightArm.setBitmap(sw, rightArm.getWidth(), rightArm.getHeight(), R.drawable.nino_rightarm_scaled);
                    leftEye.setBitmap(sw, leftEye.getWidth(), leftEye.getHeight(), R.drawable.eye_scaled);
                    rightEye.setBitmap(sw, rightEye.getWidth(), rightEye.getHeight(), R.drawable.eye_scaled);
                    sc.minimized = true;


                }

            } else if (!(getXPos() >= leftDoorXPos && getXPos() <= (rightDoorXPos - getWidth()) && getYPos() >= topOfDoor && getYPos() <= bottomOfDoor - getHeight())) {

                if (sc.minimized) {
                    setOffsetPos(false);

                    setHeight((int) (40 * displayHeight / 100));
                    setWidth(((60 * getHeight() / 100)));

                    setArmSize(leftArm, 70, 70);
                    setArmSize(rightArm, 70, 70);

                    setEyeSize(leftEye, (float) 100, 20);
                    setEyeSize(rightEye, (float) 100, 20);

                    setBitmap(sw, getWidth(), getHeight(), R.drawable.nino_body_scaled);
                    leftArm.setBitmap(sw, leftArm.getWidth(), leftArm.getHeight(), R.drawable.nino_leftarm_scaled);
                    rightArm.setBitmap(sw, rightArm.getWidth(), rightArm.getHeight(), R.drawable.nino_rightarm_scaled);
                    leftEye.setBitmap(sw, leftEye.getWidth(), leftEye.getHeight(), R.drawable.eye_scaled);
                    rightEye.setBitmap(sw, rightEye.getWidth(), rightEye.getHeight(), R.drawable.eye_scaled);

                    sc.minimized = false;
                }

            }
        }
    }
    void checkIfMoving() {

        if (!getFlagIsMoving()) {
            flagHasMoved(false);
        } else {
            flagHasMoved(true);
            flagIsMoving(false);
        }
    }

    void decideAnimation(double upperBorder,    //limit if to drop it on the road1
                         float destYPos,
                         float liftDestYPos,
                         double lowerBorder,    //limit if to pull vehicle up on the road1
                         double backUpperBorder, //limit if to draw vehicle back ...same as lowerborder??
                         long duration,
                         float leftDoorXPos,
                         float rightDoorXPos,
                         float topOfDoor,
                         float bottomOfDoor,
                         float insideVehicleYPos) {           // where to place car at X


        //SHOULD VEHICLE BE DROPPED ON BUS

        if (sc.doorsBeenPushed && getXPos() >= leftDoorXPos && getXPos() <= (rightDoorXPos - getWidth()) && getYPos() >= (topOfDoor - 10*lowerBorder/100) && getYPos() <= bottomOfDoor - getHeight()) {

            // if (getYPos() >= topOfDoor && getYPos() <= insideVehicleYPos) {

            setOffsetPos(true);

            ObjectAnimator animator = ObjectAnimator.ofFloat(this, "yPos", getYPos(), insideVehicleYPos);
            animator.setDuration(duration);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.addListener(putOnBus);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setYPos((Float) animation.getAnimatedValue());
                    setArmPos(leftArm, LEFTARM_XFACTOR, LEFTARM_YFACTOR);
                    setArmPos(rightArm, RIGHTARM_XFACTOR, RIGHTARM_YFACTOR);
                    setEyePos(leftEye, LEFTEYE_XFACTOR, LEFTEYE_YFACTOR);
                    setEyePos(rightEye, RIGHTEYE_XFACTOR, RIGHTEYE_YFACTOR);

                }
            });

            animator.start();


            //}
        } else if (getYPos() <= upperBorder && getFlagHasMoved()) {
            setOffsetPos(true);

            ObjectAnimator animator = ObjectAnimator.ofFloat(this, "yPos", getYPos(), destYPos);
            animator.setDuration(duration);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setYPos((Float) animation.getAnimatedValue());
                    setArmPos(leftArm, LEFTARM_XFACTOR, LEFTARM_YFACTOR);
                    setArmPos(rightArm, RIGHTARM_XFACTOR, RIGHTARM_YFACTOR);
                    setEyePos(leftEye, LEFTEYE_XFACTOR, LEFTEYE_YFACTOR);
                    setEyePos(rightEye, RIGHTEYE_XFACTOR, RIGHTEYE_YFACTOR);

                }
            });

            animator.start();

            //SHOULD VEHICLE BE LIFT UP
        } else if (getYPos() > backUpperBorder
                && getYPos() < lowerBorder
                && getFlagHasMoved()) {
            setOffsetPos(true);

            ObjectAnimator animator = ObjectAnimator.ofFloat(this, "yPos", getYPos(), liftDestYPos);
            animator.setDuration(duration);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setYPos((Float) animation.getAnimatedValue());
                    setArmPos(leftArm, LEFTARM_XFACTOR, LEFTARM_YFACTOR);
                    setArmPos(rightArm, RIGHTARM_XFACTOR, RIGHTARM_YFACTOR);
                    setEyePos(leftEye, LEFTEYE_XFACTOR, LEFTEYE_YFACTOR);
                    setEyePos(rightEye, RIGHTEYE_XFACTOR, RIGHTEYE_YFACTOR);

                }
            });
            animator.start();

            //SHOULD NINO BE PLACED ON THE BUS
        }

//ANIMATION METHODS

    }


    ObjectAnimator.AnimatorListener putOnBus = new ObjectAnimator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {


        }

        @Override
        public void onAnimationEnd(Animator animation) {
            sc.ninoInsideBusXPos = getXPos();
            sc.ninoInsideBusYPos = getYPos();

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };



}

    class Arm extends Positions {

        private double changingValue;
        private double degrees;
        GameBitmap armBmp;


        public Arm() {

        }

        public void setBitmap(SurfaceView sw, float width, float height, int drawable) {

            armBmp = new GameBitmap(sw, width, height, drawable);

        }

        public Bitmap getBitmap() {
            return armBmp.scaledBmp;
        }


        public double getChangingVakue() {
            return changingValue;
        }

        public void setChangingValue(double speed) {
            this.changingValue = speed;
        }

        public double getDegrees() {
            return degrees;
        }

        public void setDegrees(double degrees) {
            this.degrees += degrees;
        }

    }

    class Eye extends Positions {

        private double changingValue;
        private double degrees;
        GameBitmap eyeBmp;


        public Eye() {

        }

        public void setBitmap(SurfaceView sw, float width, float height, int drawable) {

            eyeBmp = new GameBitmap(sw, width, height, drawable);

        }

        public Bitmap getBitmap() {
            return eyeBmp.scaledBmp;
        }


        public double getChangingVakue() {
            return changingValue;
        }

        public void setChangingValue(double speed) {
            this.changingValue = speed;
        }

        public double getDegrees() {
            return degrees;
        }

        public void setDegrees(double degrees) {
            this.degrees += degrees;
        }

    }

