package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.utils.ActivityUtils;
import com.example.amitagarwal.applock.utils.ScreenMathUtils;

/**
 * Created by amitagarwal on 1/10/15.
 */
public class LastRowLayout extends LinearLayout {


    private Figure f1;
    private int screenWidth;
    private int screenHeight;
    private int headerHeight ;

    private int buttonWidth;
    private int buttonHEight;
    public LastRowLayout(Context context,Figure f1,PasswordEvaluator passwordEvaluator) {
        super(context);

        this.f1 = f1;

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
        MyCancelButton cancelButton = new MyCancelButton(context,passwordEvaluator);
        cancelButton.setParamsAndUpdateView(buttonWidth,buttonHEight);

        MyCustomButton button1 = new MyCustomButton(context,passwordEvaluator);
        button1.setParamsAndUpdateView(buttonWidth,buttonHEight,f1);

        MyBackButton backButton = new MyBackButton(context,passwordEvaluator);
        backButton.setParamsAndUpdateView(buttonWidth,buttonHEight);

        addView(cancelButton);
        addView(button1);
        addView(backButton);
    }
}
