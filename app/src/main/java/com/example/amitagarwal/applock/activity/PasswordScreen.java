package com.example.amitagarwal.applock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.utils.MyPreferenceManager;
import com.example.amitagarwal.applock.views.PasswordView;
import com.example.amitagarwal.applocks.R;

public class PasswordScreen extends PasswordBaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_screen);

		LinearLayout passwordScreen = (LinearLayout)findViewById(R.id.password_screen);
        passwordStarText = (EditText)findViewById(R.id.password_text);
        passwordView = new PasswordView(this,passwordEvaluator);
        passwordScreen.addView(passwordView);
		//init vars
		MyPreferenceManager.instance().initialize(PasswordScreen.this);
	}

	@Override
	public void onBackPressed() {
		launchHomeScreen();
		super.onBackPressed();		
	}

	private void launchHomeScreen() {
		Intent launchHomeScreen = new Intent(Intent.ACTION_MAIN);
		launchHomeScreen.addCategory(Intent.CATEGORY_HOME);
		launchHomeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(launchHomeScreen);
		finish();
	}
}
