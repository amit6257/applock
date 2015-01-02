package com.example.amitagarwal.applock.views;

import android.view.View;

import com.example.amitagarwal.applock.activity.PasswordScreen;

import java.util.ArrayList;

/**
 * Created by amitagarwal on 1/1/15.
 */
public class PasswordEntered{

    ArrayList<Figure> passwordEntered = new ArrayList<>();
    private PasswordScreen passwordScreen;

    public PasswordEntered(PasswordScreen passwordScreen) {
        this.passwordScreen = passwordScreen;
    }

    public void updateAndCheckPassword(Figure figure){
        passwordEntered.add(figure);
        if(passwordEntered.size() == Password.PASSWORD_LENGTH){

        }
    }

    public void onBackPressed(){
        if(passwordEntered.size() > 0){
            passwordEntered.remove(passwordEntered.size() - 1);
        }
    }
}
