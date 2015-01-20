package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by amitagarwal on 1/1/15.
 */
public class MyCustomButton extends Button implements View.OnClickListener{

    private PasswordEvaluator passwordEvaluator;
    private int width;
    private int height;
    private Figure figure;

    public MyCustomButton(Context context, PasswordEvaluator passwordEvaluator) {
        super(context);
        this.passwordEvaluator = passwordEvaluator;
    }

    public void setParamsAndUpdateView(int width,int height,Figure figure){
        this.figure = figure;
        this.height = height;
        this.width = width;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,height);
        setLayoutParams(lp);
        setOnClickListener(this);

        updateView();
    }

    private void updateView() {
        setText(figure.getNumber() + "");
        setTextSize(25);
        if(figure.getColour().equalsIgnoreCase("RED"))
            setBackgroundColor(Color.RED);
        else if(figure.getColour().equalsIgnoreCase("BLUE"))
            setBackgroundColor(Color.BLUE);
        else if(figure.getColour().equalsIgnoreCase("GREEN"))
            setBackgroundColor(Color.GREEN);
        else if(figure.getColour().equalsIgnoreCase("YELLOW"))
            setBackgroundColor(Color.YELLOW);
    }

    @Override
    public void onClick(View v) {
        passwordEvaluator.updateAndCheckPassword(figure);
    }
}