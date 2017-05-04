package com.example.mikaelelofsson.ninosadventure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * Created by Mikael Elofsson on 2017-04-10.
 */

public class myClasses
{
}

class GameBitmap{

    Bitmap origBmp;
    Bitmap scaledBmp;


    public GameBitmap (SurfaceView sw,float width, float height, int drawable) {
        origBmp = BitmapFactory.decodeResource(sw.getResources(), drawable);
        scaledBmp = Bitmap.createScaledBitmap(origBmp, (int)width, (int)height, true);

    }


    public void createBitmap (SurfaceView sw,float width, float height, int drawable) {

        }

    public Bitmap getBitmap () {
        return scaledBmp;
    }


}

class ScreenSize {

    private float displayWidth;
    private float displayHeight;


    public ScreenSize(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
            displayWidth = size.x;
            displayHeight = size.y;
        } else {
            display.getSize(size);
            displayWidth = size.x;
            displayHeight = size.y;// correct for devices with hardware navigation buttons
        }

    }

    public float getDisplayWidth () {
        return displayWidth;
    }

    public float getDisplayHeight () {
        return displayHeight;
    }
}

class StaticVariables {

    static float leftBusDoorXPs;
    static float rightBusDoorXPs;
    static float topOfDoor;
    static float bottomOfDoor;

    static float ninoInsideBusXPos;
    static float ninoInsideBusYPos;

    static Boolean ninoOnTheBus = false;
    static Boolean doorsBeenPushed = false;
    static Boolean minimized = false;
    static Boolean removeCharacter = false;
    static Boolean timeForTakeOff = false;

    public StaticVariables() {

    }

    public static float getNinoInsideBusXPos() {
        return ninoInsideBusXPos;
    }

    public static void setNinoInsideBusXPos(float ninoInsideBusXPos) {
        StaticVariables.ninoInsideBusXPos = ninoInsideBusXPos;
    }

    public static float getTopOfDoor() {
        return topOfDoor;
    }

    public static void setTopOfDoor(float topOfDoor) {
        StaticVariables.topOfDoor = topOfDoor;
    }

    public static float getBottomOfDoor() {
        return bottomOfDoor;
    }

    public static void setBottomOfDoor(float bottomOfDoor) {
        StaticVariables.bottomOfDoor = bottomOfDoor;
    }

    public static float getLeftBusDoorXPs() {
        return leftBusDoorXPs;
    }

    public static void setLeftBusDoorXPs(float leftBusDoorXPs) {
        StaticVariables.leftBusDoorXPs = leftBusDoorXPs;
    }

    public static float getRightBusDoorXPs() {
        return rightBusDoorXPs;
    }

    public static void setRightBusDoorXPs(float rightBusDoorXPs) {
        StaticVariables.rightBusDoorXPs = rightBusDoorXPs;
    }
}