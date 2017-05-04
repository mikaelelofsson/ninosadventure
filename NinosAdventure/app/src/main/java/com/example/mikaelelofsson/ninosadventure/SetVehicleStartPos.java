package com.example.mikaelelofsson.ninosadventure;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Mikael Elofsson on 2017-03-01.
 */

public class SetVehicleStartPos {

    ValueAnimator vehicleAnimator;
    int duration;
    int delay;

    public SetVehicleStartPos(ValueAnimator vAnimator) {
        this.vehicleAnimator = vAnimator;
    }

    void animateVehicleToFinishState(final Vehicle vehicle, float destXPos) {

        vehicle.setTouchable(false);
        vehicleAnimator = ObjectAnimator.ofFloat(vehicle, "xPos", vehicle.getXPos(), destXPos);
        vehicleAnimator.setDuration(2000);
        vehicleAnimator.setStartDelay(1000);
        vehicleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        vehicleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                vehicle.setXPos((Float) vehicleAnimator.getAnimatedValue());

            }
        });

    }
    void startAnimation () {
        vehicleAnimator.start();
    }

}
