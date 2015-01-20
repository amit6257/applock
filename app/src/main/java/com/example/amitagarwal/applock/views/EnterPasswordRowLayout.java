package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.utils.EnterPasswordFigure;
import com.example.amitagarwal.applock.utils.ScreenMathUtils;
import com.example.amitagarwal.applock.utils.SetPasswordListener;

/**
 * Created by amitagarwal on 1/17/15.
 */

public class EnterPasswordRowLayout extends LinearLayout{

    private EnterPasswordFigure f1;
    private EnterPasswordFigure f2;
    private EnterPasswordFigure f3;
    private int screenWidth;
    private int screenHeight;
    private int headerHeight ;

    private int buttonWidth;
    private int buttonHEight;
    public EnterPasswordRowLayout(Context context,EnterPasswordFigure f1,EnterPasswordFigure f2,EnterPasswordFigure f3,SetPasswordListener setPasswordListener) {
        super(context);

        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        headerHeight = ScreenMathUtils.dpToPx(20, context);
        screenWidth = ScreenMathUtils.getWidthInPx(context);
        screenHeight = ScreenMathUtils.getHeightInPx(context);

        buttonHEight = (int)(screenHeight/3.0);
        buttonWidth = (int)(screenWidth/3.0);

        LayoutParams lp = new LayoutParams(screenWidth,buttonHEight);
        setLayoutParams(lp);
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(Color.GRAY);

        //add buttons
        EnterPasswordCustomButton button1 = new EnterPasswordCustomButton(context,setPasswordListener);
        button1.setParamsAndUpdateView(buttonWidth,buttonHEight,f1);

        EnterPasswordCustomButton button2 = new EnterPasswordCustomButton(context,setPasswordListener);
        button2.setParamsAndUpdateView(buttonWidth,buttonHEight,f2);

        EnterPasswordCustomButton button3 = new EnterPasswordCustomButton(context,setPasswordListener);
        button3.setParamsAndUpdateView(buttonWidth,buttonHEight,f3);

        addView(button1);
        addView(button2);
        addView(button3);

    }
}