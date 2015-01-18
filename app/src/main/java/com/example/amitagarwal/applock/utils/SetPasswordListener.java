package com.example.amitagarwal.applock.utils;

import android.view.View;

import com.example.amitagarwal.applock.activity.EnterPasswordBaseActivity;
import com.example.amitagarwal.applock.activity.PasswordBaseActivity;
import com.example.amitagarwal.applock.broadcastreceiver.AppLockContextCache;
import com.example.amitagarwal.applock.views.Figure;

import java.util.ArrayList;

/**
 * Created by amitagarwal on 1/17/15.
 */
public class SetPasswordListener{

    private EnterPasswordBaseActivity enterPasswordBaseActivity;
    private ArrayList<EnterPasswordFigure> passwordEntered = new ArrayList<>();

    public SetPasswordListener(EnterPasswordBaseActivity enterPasswordBaseActivity) {
        this.enterPasswordBaseActivity = enterPasswordBaseActivity;
    }

    public void addFigure(EnterPasswordFigure figure) {
        passwordEntered.add(figure);
        enterPasswordBaseActivity.addStarsToPassword();
    }

    public void nextClicked() {
        AppLockContextCache.instatnce().putItem(PasswordManager.PASSWORD_CONFIGURED,passwordEntered);
        enterPasswordBaseActivity.startPasswordConfirmationActivity();
    }

    public void backClicked() {
        enterPasswordBaseActivity.removeStarsFromPassword();
        if(passwordEntered.size() > 0)
            passwordEntered.remove(passwordEntered.size()-1);
    }
}
