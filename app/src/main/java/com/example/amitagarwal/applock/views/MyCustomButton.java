package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

/**
 * Created by amitagarwal on 1/1/15.
 */
public class MyCustomButton extends Button {

    private int width;
    private int height;
    private Figure figure;


    public MyCustomButton(Context context) {

        super(context);


    }
    public void setParamsAndUpdateView(int width,int height,Figure figure){
        this.figure = figure;
        this.height = height;
        this.width = width;

        updateView();
    }

    private void updateView() {
        setText(figure.getNumber());
        if(figure.getColour().equalsIgnoreCase("RED"))
            setBackgroundColor(Color.RED);
        else if(figure.getColour().equalsIgnoreCase("BLUE"))
            setBackgroundColor(Color.BLUE);
        else if(figure.getColour().equalsIgnoreCase("GREEN"))
            setBackgroundColor(Color.GREEN);
        else if(figure.getColour().equalsIgnoreCase("BLACK"))
            setBackgroundColor(Color.BLACK);
    }
}
