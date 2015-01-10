package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by amitagarwal on 1/10/15.
 */
public class MyBackButton extends Button implements View.OnClickListener {

    private PasswordEvaluator passwordEvaluator;
    private int width;
    private int height;

    public MyBackButton(Context context, PasswordEvaluator passwordEvaluator) {
        super(context);
        this.passwordEvaluator = passwordEvaluator;
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
        passwordEvaluator.backClicked();
    }
}