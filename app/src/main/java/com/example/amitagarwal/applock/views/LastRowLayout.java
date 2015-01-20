package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.activity.PasswordScreen;
import com.example.amitagarwal.applock.utils.ScreenMathUtils;

/**
 * Created by amitagarwal on 1/10/15.
 */
public class LastRowLayout extends LinearLayout {


    private int screenWidth;
    private int passwordViewHeight;

    private int buttonColumnWidth;
    private int buttonRowHeight;
    public LastRowLayout(Context context,Figure f1,PasswordEvaluator passwordEvaluator) {
        super(context);

        screenWidth = ScreenMathUtils.getWidthInPx(context) - ScreenMathUtils.dpToPx(10,context);
        passwordViewHeight = ((PasswordScreen)passwordEvaluator.getPasswordBaseActivity()).passwordLLHeight;

        buttonRowHeight = (int)(passwordViewHeight /4.0);
        buttonColumnWidth = (int)(screenWidth/3.0);

        System.out.println(buttonRowHeight + " -----------");

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, buttonRowHeight);
        setLayoutParams(lp);
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(Color.GRAY);

        //add buttons
        MyCancelButton cancelButton = new MyCancelButton(context,passwordEvaluator);
        cancelButton.setParamsAndUpdateView(buttonColumnWidth, buttonRowHeight);

        MyCustomButton button1 = new MyCustomButton(context,passwordEvaluator);
        button1.setParamsAndUpdateView(buttonColumnWidth, buttonRowHeight,f1);

        MyBackButton backButton = new MyBackButton(context,passwordEvaluator);
        backButton.setParamsAndUpdateView(buttonColumnWidth, buttonRowHeight);

        addView(cancelButton);
        addView(button1);
        addView(backButton);
    }
}
