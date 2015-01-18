package com.example.amitagarwal.applock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.broadcastreceiver.AppLockContextCache;
import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.utils.MyStringUtils;
import com.example.amitagarwal.applock.views.ConfigurePasswordView;
import com.example.amitagarwal.applocks.R;

import java.util.ArrayList;

public class SetPassFirstActivity extends EnterPasswordBaseActivity {

	ArrayList<Integer> passEntered = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_pwd_screen1);
        passwordStarText = (EditText)findViewById(R.id.password_text);
		LinearLayout passwordScreenView = (LinearLayout)findViewById(R.id.set_first_password_screen);
        passwordViewNew = new ConfigurePasswordView(this,setPasswordListener);
        passwordScreenView.addView(passwordViewNew);

	}

//	public void resetPassword(View v){
//		clearPasswordData();
//	}
	
	public void continueToConfirmationScreen(View v){
		AppLockContextCache.instatnce().putItem(Constants.TEMP_PWD, passEntered);
		Intent intent =  new Intent(this,SetPassConfirmationActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onResume() {

		//if password saved successfully finish it
		String passSavedSuccess = (String) AppLockContextCache.instatnce().getItem(Constants.NEW_PWD_SAVED);
		if(!MyStringUtils.isNullOrEmpty(passSavedSuccess) && passSavedSuccess.equalsIgnoreCase(Constants.NEW_PWD_SAVED)){
			AppLockContextCache.instatnce().clearValue(Constants.NEW_PWD_SAVED);
			AppLockContextCache.instatnce().putItem(Constants.SHOW_PWD, Constants.DONT_SHOW_PWD);
			//start splash activity
			Intent intent =  new Intent(this,MySplashActivity.class);		            		
			startActivity(intent);
			finish();
		}else{
			//if come back from confirm pass screen then clear pwd
			String clearPwd = (String) AppLockContextCache.instatnce().getItem(Constants.CLEAR_PWD);
			if(!MyStringUtils.isNullOrEmpty(clearPwd) && clearPwd.equalsIgnoreCase(Constants.CLEAR_PWD)){
				//clearPasswordData();
			}			
		}
		//else if return from background do nothing
		super.onResume();
	}

	@Override
	public void onBackPressed() {

		//since we are in-app , pwd screen need not be shown
		AppLockContextCache.instatnce().putItem(Constants.SHOW_PWD, Constants.DONT_SHOW_PWD);
		super.onBackPressed();
	}

    public void startPasswordConfirmationActivity() {
        Intent confirmationActivity = new Intent(this,SetPassConfirmationActivity.class);
        startActivity(confirmationActivity);
    }
}