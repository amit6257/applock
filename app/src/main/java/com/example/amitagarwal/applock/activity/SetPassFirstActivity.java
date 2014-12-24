package com.example.amitagarwal.applock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.broadcastreceiver.AppLockContextCache;
import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.utils.MyStringUtils;
import com.example.amitagarwal.applock.views.CustomViewConstants;
import com.example.amitagarwal.applock.views.EnterCurrentPasswordView;
import com.example.amitagarwal.applocks.R;

import java.util.ArrayList;

public class SetPassFirstActivity extends Activity implements OnClickListener {

	ArrayList<Integer> passEntered = new ArrayList<Integer>();
	private EnterCurrentPasswordView passwordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_pwd_screen1);
		LinearLayout passwordScreen = (LinearLayout)findViewById(R.id.set_first_password_screen);

		passwordView = new EnterCurrentPasswordView(this,this);
		passwordScreen.addView(passwordView);
	}

	public void resetPassword(View v){
		clearPasswordData();
		
	}
	
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
				clearPasswordData();
			}			
		}
		//else if return from background do nothing
		super.onResume();
	}

	private void clearPasswordData() {
		passEntered.clear();
		passwordView.removeAllStars();
	}

	@Override
	public void onBackPressed() {

		//since we are in-app , pwd screen need not be shown
		AppLockContextCache.instatnce().putItem(Constants.SHOW_PWD, Constants.DONT_SHOW_PWD);
		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		String tag = (String)v.getTag();
		if(tag.equalsIgnoreCase(CustomViewConstants.DELETE)){
			if(passEntered.size() == 0)
				return;
			passwordView.removeStarsFromPassword();
			passEntered.remove(passEntered.size() - 1);
			return;
		}else if(tag.equalsIgnoreCase(CustomViewConstants.NEXT)){
			continueToConfirmationScreen(v);
			return;
		}
		passwordView.addStarsToPassword();
		passEntered.add(Integer.valueOf(tag));		
	}
}
