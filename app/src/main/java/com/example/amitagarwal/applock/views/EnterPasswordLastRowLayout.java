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
public class EnterPasswordLastRowLayout extends LinearLayout {


    private EnterPasswordFigure f1;
    private int screenWidth;
    private int screenHeight;
    private int headerHeight ;

    private int buttonWidth;
    private int buttonHEight;
    public EnterPasswordLastRowLayout(Context context,EnterPasswordFigure f1,SetPasswordListener setPasswordListener) {
        super(context);

        this.f1 = f1;

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
        EnterPasswordNextButton nextButton = new EnterPasswordNextButton(context,setPasswordListener);
        nextButton.setParamsAndUpdateView(buttonWidth, buttonHEight);

        EnterPasswordCustomButton button1 = new EnterPasswordCustomButton(context,setPasswordListener);
        button1.setParamsAndUpdateView(buttonWidth,buttonHEight,f1);

        EnterPasswordBackButton backButton = new EnterPasswordBackButton(context,setPasswordListener);
        backButton.setParamsAndUpdateView(buttonWidth,buttonHEight);

        addView(nextButton);
        addView(button1);
        addView(backButton);
    }
}
