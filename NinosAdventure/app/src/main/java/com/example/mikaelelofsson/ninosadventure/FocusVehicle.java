package com.example.mikaelelofsson.ninosadventure;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by Mikael Elofsson on 2017-02-01.
 */

public class FocusVehicle extends Positions {

    Wheel leftWheel;
    Wheel rightWheel;
    Door leftDoor;
    Door rightDoor;
    Boolean up = true;
    Character character2;
    private Boolean doorsAreMoving = false;

    GameBitmap chassiBmp;
    StaticVariables sv;

    public FocusVehicle() {
        leftWheel = new Wheel();
        rightWheel = new Wheel();
        leftDoor = new Door();
        rightDoor = new Door();

        sv = new StaticVariables();
    }

    public void setBitmap(SurfaceView sw, float width, float height, int drawable) {

        chassiBmp = new GameBitmap(sw, width, height, drawable);

    }

    public Bitmap getBitmap() {
        return chassiBmp.scaledBmp;
    }

    public Boolean getDoorsAreMoving() {
        return doorsAreMoving;
    }

    public void setDoorsAreMoving(Boolean doorsAreMoving) {
        this.doorsAreMoving = doorsAreMoving;
    }

    public void setNewPositions(float xPos, float yPos) {
        setXPos(xPos);
        setYPos(yPos);
    }

    public void setWheelPos(Wheel wheel, float xFactor, float yFactor) {
        float xPos = getXPos() + (xFactor * getWidth() / 100);
        float yPos = getYPos() + (yFactor * getHeight() / 100);
        wheel.setXPos(xPos);
        wheel.setYPos(yPos);
    }


    public void setWheelSize(Wheel wheel, float widthFactor) {

        float width = widthFactor * getWidth() / 100;
        float height = width;

        wheel.setWidth(width);
        wheel.setHeight(height);
    }

    public void setDoorPos(Door door, float xFactor, float yFactor) {
        float xPos = getXPos() + (xFactor * getWidth() / 100);
        float yPos = getYPos() + (yFactor * getHeight() / 100);
        door.setXPos(xPos);
        door.setYPos(yPos);
    }

    public void setDoorSize(Door door, float widthFactor, float heightFactor) {

        float width = widthFactor * getWidth() / 100;
        float height = heightFactor * width / 100;

        door.setWidth(width);
        door.setHeight(height);
    }

    void animatePosition(float yPosUpperLimit, float yPosLowerLimit) {
        if (up) {
            if (getYPos() > yPosUpperLimit)
                setYPos((float) (getYPos() - 0.2));
            else
                up = false;
        }

        if (!up) {
            if (getYPos() < yPosLowerLimit)
                setYPos((float) (getYPos() + 0.2));
            else
                up = true;
        }

        setWheelPos(leftWheel, (float) 20.5, 69);
        setWheelPos(rightWheel, (float) 71.5, 69);
        setDoorPos(leftDoor, (float) 84, 18);
        setDoorPos(rightDoor, (float) 89.3, (float) 18);

    }

    ObjectAnimator animateBusXPos(float destXPos, long duration) {

        ObjectAnimator vehicleAnimator = ObjectAnimator.ofFloat(this, "xPos", getXPos(), destXPos);
        vehicleAnimator.setDuration(duration);
        vehicleAnimator.setInterpolator(new AccelerateInterpolator());
        vehicleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()

        {
            @Override
            public void onAnimationUpdate (ValueAnimator animation){
                setXPos((Float) animation.getAnimatedValue());


            }
        });

return vehicleAnimator;
}



    Matrix animateWheel (Wheel wheel) {

        Matrix matrix = new Matrix();
        wheel.setChangingValue(4);
        wheel.setDegrees(wheel.getChangingVakue());

        matrix.postRotate((float)wheel.getDegrees(),wheel.getWidth()/2,wheel.getHeight()/2);
        matrix.postTranslate(wheel.getXPos(), wheel.getYPos());

        return matrix;
    }

}

class Positions {

    private float xPos;
    private float yPos;
    private float height;
    private float width;
    private float offsetXPos;
    private float offsetYPos;
    private float startPosX;
    private float startPosY;

    private boolean inPlace = false; //is true if vehicle is placed on the road
    private boolean hasMoved = false;
    private boolean isMoving = false; //is true if vehicle is being moved
    private boolean drawBack = false; //is true if vehicle is being drawn back to origin spot
    private boolean setOffsetPos = true;
    private boolean touchable = true; //is true if vehicle should be reacting to touches
    private boolean allOtherDisabled = false;

    public Positions () {

    }

    public void setNewPositions(float xPos, float yPos) {
        setXPos(xPos);
        setYPos(yPos);
    }

    public float getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }


    public float getYPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }


    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }


    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }


    public float getOffsetXPos() {
        return offsetXPos;
    }

    public void setOffsetXPos(float offsetXPos) {
        this.offsetXPos = offsetXPos;
    }


    public float getOffsetYPos() {
        return offsetYPos;
    }

    public void setOffsetYPos(float offsetYPos) {
        this.offsetYPos = offsetYPos;
    }


    public float getStartPosX() {
        return startPosX;
    }

    public void setStartPosX(float startPosX) {
        this.startPosX = startPosX;
    }


    public float getStartPosY() {
        return startPosY;
    }

    public void setStartPosY(float startPosY) {
        this.startPosY = startPosY;
    }


    void flagInPlace (Boolean inPlace) {
        this.inPlace = inPlace;
    }
    void flagHasMoved (Boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    void flagIsMoving (Boolean isMoving) {
        this.isMoving = isMoving;
    }
    void flagDrawBack (Boolean drawBack) {
        this.drawBack = drawBack;
    }
    void setOffsetPos (Boolean bool) {
        this.setOffsetPos = bool;
    }
    void setTouchable (Boolean bool) {
        this.touchable = bool;
    }
    void setAllOtherDisabled (Boolean bool) {
        this.allOtherDisabled = bool;
    }


    boolean getFlagInPlace () {
        if (inPlace) return true;
        else return false;
    }
    boolean getFlagHasMoved () {
        if (hasMoved) return true;
        else return false;
    }
    boolean getFlagIsMoving () {
        if (isMoving) return true;
        else return false;
    }
    boolean getFlagDrawBack () {
        if (drawBack) return true;
        else return false;
    }
    boolean isSetOffsetPos () {
        if (setOffsetPos) return true;
        else return false;
    }
    boolean isTouchable () {
        if (touchable) return true;
        else return false;
    }
    boolean isAllOtherDisabled () {
        if (allOtherDisabled) return true;
        else return false;
    }
}



class Wheel extends Positions {

    private double changingValue;
    private double degrees;
    GameBitmap wheelBmp;



    public Wheel () {

    }

    public void setBitmap (SurfaceView sw, float width, float height, int drawable) {

        wheelBmp = new GameBitmap(sw, width, height, drawable);

    }

    public Bitmap getBitmap () {
        return wheelBmp.scaledBmp;
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


class Door extends Positions {
    private ColorMatrix colorMatrix1;
    private ColorMatrix colorMatrix2;
    private ColorMatrixColorFilter filter;
    private Paint doorPaint1;
    private Paint doorPaint2;

    GameBitmap doorBmp;

    public Door() {
        doorPaint1 = new Paint();
        doorPaint2 = new Paint();
        colorMatrix1 = new ColorMatrix();
        colorMatrix2 = new ColorMatrix();
        updatePaintSat();
    }

    public void setBitmap (SurfaceView sw, float width, float height, int drawable) {

        doorBmp = new GameBitmap(sw, width, height, drawable);

    }


    public Bitmap getBitmap () {
        return doorBmp.scaledBmp;
    }

    public Paint getDoorPaint1() {
        return doorPaint1;
    }
    public Paint getDoorPaint2() {
        return doorPaint2;
    }


    public void updatePaintSat() {

            colorMatrix1.set(new float[] {
                    1, 0, 0, 0, 2,
                    0, 1, 0, 0, 2,
                    0, 0, 1, 0, 2,
                    0, 0, 0, 1, 0 });

            colorMatrix2.set(new float[] {
                    1, 0, 0, 0,12,
                    0, 1, 0, 0, 12,
                    0, 0, 1, 0, 12,
                    0, 0, 0, 1, 0 });

        this.doorPaint1.setColorFilter(new ColorMatrixColorFilter(colorMatrix1));
        this.doorPaint2.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
    }



    public ObjectAnimator openDoor(String property, float startingValue, float destValue, long duration, long delay) {
        final ObjectAnimator doorAnimator = ObjectAnimator.ofFloat(this, property, startingValue, destValue);
        doorAnimator.setDuration(duration);
        doorAnimator.setStartDelay(delay);
        doorAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        doorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setXPos((Float) doorAnimator.getAnimatedValue());
            }
        });

        return doorAnimator;

    }

}
