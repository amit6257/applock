package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.utils.EnterPasswordFigure;
import com.example.amitagarwal.applock.utils.SetPasswordListener;

/**
 * Created by amitagarwal on 1/17/15.
 */
public class EnterPasswordCustomButton extends Button implements View.OnClickListener{

    private SetPasswordListener setPasswordListener;
    private int width;
    private int height;
    private EnterPasswordFigure figure;

    public EnterPasswordCustomButton(Context context, SetPasswordListener setPasswordListener) {
        super(context);
        this.setPasswordListener = setPasswordListener;
    }

    public void setParamsAndUpdateView(int width,int height,EnterPasswordFigure figure){
        this.figure = figure;
        this.height = height;
        this.width = width;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,height);
        setLayoutParams(lp);
        setOnClickListener(this);

        updateView();
    }

    private void updateView() {

        if(figure.isColor()){
            if(figure.getColor().equalsIgnoreCase("RED"))
                setBackgroundColor(Color.RED);
            else if(figure.getColor().equalsIgnoreCase("BLUE"))
                setBackgroundColor(Color.BLUE);
            else if(figure.getColor().equalsIgnoreCase("GREEN"))
                setBackgroundColor(Color.GREEN);
            else if(figure.getColor().equalsIgnoreCase("YELLOW"))
                setBackgroundColor(Color.YELLOW);
        }else if(figure.isNumber()){
            setText(figure.getNumber() + "");
        }
    }

    @Override
    public void onClick(View v) {
        setPasswordListener.addFigure(figure);
    }
}