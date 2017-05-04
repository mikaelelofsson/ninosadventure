package com.example.mikaelelofsson.ninosadventure;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by Mikael Elofsson on 2017-03-11.
 */

public class ButtonsView extends View {
    public ButtonsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int displayHeight = metrics.heightPixels;
        int displayWidth = metrics.widthPixels;

        ImageView button = new ImageView(context);
        button.setImageResource(R.drawable.savebutton);

        button.getLayoutParams().height = 20 * displayHeight/100;
        button.getLayoutParams().width = 20 * displayHeight/100;

    }
}
