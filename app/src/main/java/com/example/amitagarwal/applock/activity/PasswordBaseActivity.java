package com.example.amitagarwal.applock.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.amitagarwal.applock.views.PasswordEvaluator;
import com.example.amitagarwal.applock.views.PasswordView;
import com.example.amitagarwal.applocks.R;

/**
 * Created by amitagarwal on 1/17/15.
 */
public class PasswordBaseActivity extends Activity {

    protected PasswordView passwordView;
    protected EditText passwordText;
    protected int noOfStars = 0;
    protected PasswordEvaluator passwordEvaluator;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordEvaluator = new PasswordEvaluator(this);
    }


        public void addStarsToPassword() {
        noOfStars ++;
        StringBuilder stars = new StringBuilder();
        for(int i=0;i<noOfStars;i++){
            stars.append("*");
        }
        passwordText.setText(stars.toString());
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
        passwordText.setText(stars.toString());
    }
}
