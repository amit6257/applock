package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.utils.EnterPasswordFigure;
import com.example.amitagarwal.applock.utils.PasswordManager;
import com.example.amitagarwal.applock.utils.SetPasswordListener;

import java.util.ArrayList;

/**
 * Created by amitagarwal on 1/17/15.
 */
public class ConfigurePasswordView extends LinearLayout{

    private final Context context;
    SetPasswordListener setPasswordListener;
    public ConfigurePasswordView(Context context,SetPasswordListener setPasswordListener) {
        super(context);

        this.context = context;
        this.setPasswordListener = setPasswordListener;

        setOrientation(LinearLayout.VERTICAL);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(lp);

        ArrayList<EnterPasswordFigure> figureList  = PasswordManager.getFigureListForConfigurePassword();

        EnterPasswordRowLayout row1 = new EnterPasswordRowLayout(context,figureList.get(0),figureList.get(1),figureList.get(2),setPasswordListener);
        EnterPasswordRowLayout row2 = new EnterPasswordRowLayout(context,figureList.get(3),figureList.get(4),figureList.get(5),setPasswordListener);
        EnterPasswordRowLayout row3 = new EnterPasswordRowLayout(context,figureList.get(6),figureList.get(7),figureList.get(8),setPasswordListener);

        EnterPasswordLastRowLayout lastRow = new EnterPasswordLastRowLayout(context,figureList.get(9),setPasswordListener);
        addView(row1);
        addView(row2);
        addView(row3);
        addView(lastRow);
    }
}
