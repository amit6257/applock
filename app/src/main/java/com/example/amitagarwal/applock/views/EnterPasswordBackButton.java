package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.utils.SetPasswordListener;

/**
 * Created by amitagarwal on 1/17/15.
 */
public class EnterPasswordBackButton extends Button implements View.OnClickListener {

    private SetPasswordListener setPasswordListener;
    private int width;
    private int height;

    public EnterPasswordBackButton(Context context, SetPasswordListener setPasswordListener) {
        super(context);
        this.setPasswordListener = setPasswordListener;
    }

    public void setParamsAndUpdateView(int width, int height) {
        this.height = height;
        this.width = width;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        setLayoutParams(lp);
        setOnClickListener(this);

        updateView();
    }

    private void updateView() {
        setTextSize(25);
        setText(" < ");
    }

    @Override
    public void onClick(View v) {
        setPasswordListener.backClicked();
    }
}