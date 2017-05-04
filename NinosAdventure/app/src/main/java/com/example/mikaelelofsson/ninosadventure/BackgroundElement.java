package com.example.mikaelelofsson.ninosadventure;

import android.graphics.Bitmap;
import android.view.SurfaceView;

/**
 * Created by Mikael Elofsson on 2017-02-01.
 */

public class BackgroundElement extends Positions{

    private GameBitmap backgroundBmp;
    private float backgroundWidth;
    private float backgroundHeight;

    public BackgroundElement() {
    }
    public Bitmap getBitmap() {
        return backgroundBmp.scaledBmp;
    }

    public void setBitmap(SurfaceView sw, float width, float height, int drawable) {
        backgroundBmp = new GameBitmap(sw, width, height, drawable);
    }

//SET METHODS

    public float getBackgroundWidth() {
        return backgroundWidth;
    }

    public void setBackgroundWidth(float backgroundWidth) {
        this.backgroundWidth = backgroundWidth;
    }

    public float getBackgroundHeight() {
        return backgroundHeight;
    }

    public void setBackgroundHeight(float backgroundHeight) {
        this.backgroundHeight = backgroundHeight;
    }


//ANIMATION METHODS




}
