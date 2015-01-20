package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.activity.PasswordScreen;
import com.example.amitagarwal.applock.utils.ScreenMathUtils;

/**
 * Created by amitagarwal on 1/1/15.
 */
public class RowLayout extends LinearLayout{

    private Figure f1;
    private Figure f2;
    private Figure f3;

    private int screenWidth;
    private int passwordViewHeight;

    private int buttonColumnWidth;
    private int buttonRowHeight;
    public RowLayout(Context context,Figure f1,Figure f2,Figure f3,PasswordEvaluator passwordEvaluator) {
        super(context);

        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;

        screenWidth = ScreenMathUtils.getWidthInPx(context) - ScreenMathUtils.dpToPx(10,context);
        passwordViewHeight = ((PasswordScreen)passwordEvaluator.getPasswordBaseActivity()).passwordLLHeight;

        buttonRowHeight = (int)(passwordViewHeight /4.0);
        System.out.println(buttonRowHeight + " -----------");
        buttonColumnWidth = (int)(screenWidth/3.0);

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, buttonRowHeight);
        setLayoutParams(lp);
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(Color.GRAY);

        //add buttons
        MyCustomButton button1 = new MyCustomButton(context,passwordEvaluator);
        button1.setParamsAndUpdateView(buttonColumnWidth, buttonRowHeight,f1);

        MyCustomButton button2 = new MyCustomButton(context,passwordEvaluator);
        button2.setParamsAndUpdateView(buttonColumnWidth, buttonRowHeight,f2);

        MyCustomButton button3 = new MyCustomButton(context,passwordEvaluator);
        button3.setParamsAndUpdateView(buttonColumnWidth, buttonRowHeight,f3);

        addView(button1);
        addView(button2);
        addView(button3);

    }
}
