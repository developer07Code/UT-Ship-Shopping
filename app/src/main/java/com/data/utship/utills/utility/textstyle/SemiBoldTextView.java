package com.data.utship.utills.utility.textstyle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by HP on 23-10-2018.
 */

@SuppressLint("AppCompatCustomView")
public class SemiBoldTextView extends TextView {
    Context context;
    public SemiBoldTextView(Context context) {
        super(context);
        Typeface typeface= Typeface.createFromAsset(context.getAssets(),"fonts/Poppins-SemiBold.ttf");
        this.setTypeface(typeface);
    }
    public SemiBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-SemiBold.ttf");
        this.setTypeface(face);
    }
    public SemiBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-SemiBold.ttf");
        this.setTypeface(face);
    }
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }
}