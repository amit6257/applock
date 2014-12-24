package com.example.amitagarwal.applock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.example.amitagarwal.applocks.R;
import com.example.amitagarwal.applock.broadcastreceiver.AppLockContextCache;
import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.utils.MyPreferenceManager;
import com.example.amitagarwal.applock.views.CustomViewConstants;
import com.example.amitagarwal.applock.views.EnterCurrentPasswordView;
import com.example.amitagarwal.applock.views.MyCustomDialog;

import java.util.ArrayList;

public class ChangePasswordActivity extends Activity implements OnClickListener {

	ArrayList<Integer> passEntered = new ArrayList<Integer>();
	private EnterCurrentPasswordView passwordView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_pwd);

		passwordView = new EnterCurrentPasswordView(this,this);
		LinearLayout passwordScreen = (LinearLayout)findViewById(R.id.change_password_screen);

		passwordScreen.addView(passwordView);
	}

	@Override
	public void onBackPressed() {
		//since we are in-app , pwd screen need not be shown
		AppLockContextCache.instatnce().putItem(Constants.SHOW_PWD, Constants.DONT_SHOW_PWD);
		super.onBackPressed();
	}

	public void resetPassword(View v){
		clearPasswordData();
	}

	public void validatePassword(View v){
		String savedPassword = MyPreferenceManager.instance().getPasswod();

		String passwordEntered = "";
		for(int i=0;i<passEntered.size();i++){
			passwordEntered += passEntered.get(i).toString();
		}
		if(passwordEntered.equalsIgnoreCase(savedPassword)){
			Intent intent = new Intent(this,SetPassFirstActivity.class);
			startActivity(intent);
			finish();
		}else{
			clearPasswordData();
			
			String errorEnterCorrectPwd = getResources().getString(R.string.error_enter_correct_pwd);
			MyCustomDialog.showErrorMessage(errorEnterCorrectPwd,this);
		}
	}
	private void clearPasswordData() {
		passEntered.clear();
		passwordView.removeAllStars();
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
			validatePassword(v);
			return;
		}
		passwordView.addStarsToPassword();
		passEntered.add(Integer.valueOf(tag));
	}
}
