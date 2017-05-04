package com.example.mikaelelofsson.ninosadventure;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Mikael Elofsson on 2017-02-28.
 */

public class GameThread  extends Thread {

    Boolean run;
    Canvas myCanvas;
    SurfaceHolder surfaceHolder;
    SurfaceView surfaceView;


    public GameThread (SurfaceHolder surfaceHolder, SurfaceView surfaceView) {

        this.surfaceHolder = surfaceHolder;
        this.surfaceView = surfaceView;
    }

    void setRunning (Boolean setRun) {
        run = setRun;
    }

    @Override
    public void run() {
        super.run();


        while (run) {


            if (surfaceHolder.getSurface().isValid()) {
                myCanvas = surfaceHolder.lockCanvas();

                if (myCanvas != null) {

                    surfaceView.draw(myCanvas);

                    surfaceHolder.unlockCanvasAndPost(myCanvas);
                }
            }
        }
    }







}
