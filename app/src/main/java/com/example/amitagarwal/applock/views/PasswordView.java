package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.utils.ActivityUtils;
import com.example.amitagarwal.applock.utils.PasswordManager;
import com.example.amitagarwal.applock.utils.ScreenMathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by amitagarwal on 1/3/15.
 */
public class PasswordView extends LinearLayout {

    private PasswordEvaluator passwordEvaluator;

    public PasswordView(Context context,PasswordEvaluator passwordEvaluator) {
        super(context);
        this.passwordEvaluator = passwordEvaluator;

        setOrientation(LinearLayout.VERTICAL);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(lp);

        ArrayList<Figure> figureList  = PasswordManager.getPassword();

        RowLayout row1 = new RowLayout(context,figureList.get(0),figureList.get(1),figureList.get(2),passwordEvaluator);
        RowLayout row2 = new RowLayout(context,figureList.get(3),figureList.get(4),figureList.get(5),passwordEvaluator);
        RowLayout row3 = new RowLayout(context,figureList.get(6),figureList.get(7),figureList.get(8),passwordEvaluator);

        LastRowLayout lastRow = new LastRowLayout(context,figureList.get(9),passwordEvaluator);

        addView(row1);
        addView(row2);
        addView(row3);
        addView(lastRow);

    }
}
