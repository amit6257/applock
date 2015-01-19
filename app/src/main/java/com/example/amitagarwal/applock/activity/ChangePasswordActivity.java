package com.example.amitagarwal.applock.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.broadcastreceiver.AppLockContextCache;
import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.utils.MyPreferenceManager;
import com.example.amitagarwal.applock.views.PasswordView;
import com.example.amitagarwal.applocks.R;

public class ChangePasswordActivity extends PasswordBaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.change_pwd);
        passwordStarText = (EditText)findViewById(R.id.password_text);
		passwordView = new PasswordView(this,passwordEvaluator);
		LinearLayout passwordScreen = (LinearLayout)findViewById(R.id.change_password_screen);
		passwordScreen.addView(passwordView);
        MyPreferenceManager.instance().initialize(ChangePasswordActivity.this);
	}

	@Override
	public void onBackPressed() {
		//since we are in-app , pwd screen need not be shown
		AppLockContextCache.instatnce().putItem(Constants.SHOW_PWD, Constants.DONT_SHOW_PWD);
		super.onBackPressed();
	}
}
