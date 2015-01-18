package com.example.amitagarwal.applock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.utils.SetPasswordListener;
import com.example.amitagarwal.applock.views.ConfigurePasswordView;

/**
 * Created by amitagarwal on 1/17/15.
 */
public class EnterPasswordBaseActivity extends Activity  {

    protected ConfigurePasswordView passwordViewNew;
    protected ConfigurePasswordView passwordView;
    protected EditText passwordStarText;
    protected int noOfStars = 0;
    protected LinearLayout passwordScreenView;
    protected SetPasswordListener setPasswordListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPasswordListener = new SetPasswordListener(this);
    }


    public void clearPassword(){
        noOfStars = 0;
        passwordStarText.setText("");
    }

    public void addStarsToPassword() {
        noOfStars ++;
        StringBuilder stars = new StringBuilder();
        for(int i=0;i<noOfStars;i++){
            stars.append("*");
        }
        passwordStarText.setText(stars.toString());
    }

    public void removeStarsFromPassword() {
        if(noOfStars <= 0){
            return;
        }
        noOfStars --;
        StringBuilder stars = new StringBuilder();
        for(int i=0;i<noOfStars;i++){
            stars.append("*");
        }
        passwordStarText.setText(stars.toString());
    }
}
