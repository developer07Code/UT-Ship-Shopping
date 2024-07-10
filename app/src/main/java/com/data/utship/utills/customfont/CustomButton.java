package com.data.utship.utills.customfont;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by HP on 23-10-2018.
 */

@SuppressLint("AppCompatCustomView")
public class CustomButton extends Button {
    Context context;
    public CustomButton(Context context) {
        super(context);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Regular.ttf");
        this.setTypeface(typeface);
    }
    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf");
        this.setTypeface(face);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf");
        this.setTypeface(face);
    }
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}
