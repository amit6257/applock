package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by amitagarwal on 1/1/15.
 */
public class RowLayout extends LinearLayout{

    private int height;
    private int width;
    private Figure f1;
    private Figure f2;
    private Figure f3;

    public RowLayout(Context context,int height,int width,Figure f1,Figure f2,Figure f3,PasswordEntered passwordEntered) {
        super(context);

        this.height = height;
        this.width = width;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;

        LayoutParams lp = new LayoutParams(width,height);
        setLayoutParams(lp);
        setOrientation(LinearLayout.HORIZONTAL);

        //add buttons
        MyCustomButton button1 = new MyCustomButton(context);
        button1.setParamsAndUpdateView(width/3,height,f1);

        MyCustomButton button2 = new MyCustomButton(context);
        button2.setParamsAndUpdateView(width/3,height,f2);

        MyCustomButton button3 = new MyCustomButton(context);
        button3.setParamsAndUpdateView(width/3,height,f3);

        addView(button1);
        addView(button2);
        addView(button3);

    }
}
