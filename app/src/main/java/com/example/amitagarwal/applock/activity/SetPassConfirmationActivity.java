package com.example.amitagarwal.applock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.amitagarwal.applock.broadcastreceiver.AppLockContextCache;
import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.utils.MyPreferenceManager;
import com.example.amitagarwal.applock.utils.MyStringUtils;
import com.example.amitagarwal.applock.views.ConfigurePasswordView;
import com.example.amitagarwal.applock.views.CustomViewConstants;
import com.example.amitagarwal.applock.views.EnterCurrentPasswordView;
import com.example.amitagarwal.applock.views.MyCustomDialog;
import com.example.amitagarwal.applocks.R;

import java.util.ArrayList;

public class SetPassConfirmationActivity extends EnterPasswordBaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.set_pwd_confirmation_screen);
        passwordStarText = (EditText)findViewById(R.id.password_text);
        passwordScreenView = (LinearLayout)findViewById(R.id.password_confirmation_screen);
        passwordViewNew = new ConfigurePasswordView(this,setPasswordListener);
        passwordScreenView.addView(passwordViewNew);
	}


//	private void clearPasswordData() {
//		passEntered.clear();
//		passwordView.removeAllStars();
//	}

	@Override
	public void onBackPressed() {
        //start SetPassFirstActivity again
        Intent setPAssFirstActivity = new Intent(this,SetPassFirstActivity.class);
        startActivity(setPAssFirstActivity);
        finish();
		super.onBackPressed();
	}

//	public void resetPassword(View v){
//		clearPasswordData();
//	}
}
