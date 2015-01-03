package com.example.amitagarwal.applock.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.views.PasswordEvaluator;
import com.example.amitagarwal.applock.views.PasswordView;
import com.example.amitagarwal.applocks.R;

public class TestActivity extends Activity{

    private PasswordEvaluator passwordEvaluator;
    private int[] pwdEntered;

    private int savedPassword[];
    //	private MyCustomPasswordView passwordView;
    private PasswordView passwordView;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_screen);

        passwordEvaluator = new PasswordEvaluator(null);
        LinearLayout passwordScreen = (LinearLayout)findViewById(R.id.password_screen);

        passwordView = new PasswordView(this,passwordEvaluator);
        passwordScreen.addView(passwordView);

    }
}
