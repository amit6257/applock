package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.utils.ActivityUtils;
import com.example.amitagarwal.applock.utils.ScreenMathUtils;

/**
 * Created by amitagarwal on 1/1/15.
 */
public class RowLayout extends LinearLayout{

    private Figure f1;
    private Figure f2;
    private Figure f3;
    private int screenWidth;
    private int screenHeight;
    private int headerHeight ;

    private int buttonWidth;
    private int buttonHEight;
    public RowLayout(Context context,Figure f1,Figure f2,Figure f3,PasswordEvaluator passwordEvaluator) {
        super(context);

        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;

        headerHeight = ScreenMathUtils.dpToPx(20, context);
        screenWidth = ActivityUtils.getWidthInPx(context);
        screenHeight = ActivityUtils.getHeightInPx(context);

        buttonHEight = (int)(screenHeight/3.0);
        buttonWidth = (int)(screenWidth/3.0);

        LayoutParams lp = new LayoutParams(screenWidth,buttonHEight);
        setLayoutParams(lp);
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(Color.GRAY);

        //add buttons
        MyCustomButton button1 = new MyCustomButton(context,passwordEvaluator);
        button1.setParamsAndUpdateView(buttonWidth,buttonHEight,f1);

        MyCustomButton button2 = new MyCustomButton(context,passwordEvaluator);
        button2.setParamsAndUpdateView(buttonWidth,buttonHEight,f2);

        MyCustomButton button3 = new MyCustomButton(context,passwordEvaluator);
        button3.setParamsAndUpdateView(buttonWidth,buttonHEight,f3);

        addView(button1);
        addView(button2);
        addView(button3);

    }
}
