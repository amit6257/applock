package com.example.amitagarwal.applock.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.amitagarwal.applock.broadcastreceiver.AppLockContextCache;
import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.utils.MyPreferenceManager;
import com.example.amitagarwal.applock.utils.MyStringUtils;
import com.example.amitagarwal.applock.views.CustomViewConstants;
import com.example.amitagarwal.applock.views.EnterCurrentPasswordView;
import com.example.amitagarwal.applock.views.MyCustomDialog;
import com.example.amitagarwal.applocks.R;

import java.util.ArrayList;

public class SetPassConfirmationActivity extends PasswordBaseActivity implements OnClickListener {

	ArrayList<Integer> passEntered = new ArrayList<Integer>();
	private EnterCurrentPasswordView passwordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_pwd_confirmation_screen);

		passwordView = new EnterCurrentPasswordView(this,this);
		LinearLayout passwordScreen = (LinearLayout)findViewById(R.id.password_confirmation_screen);

		passwordScreen.addView(passwordView);	
	}

	private void clearPasswordData() {
		passEntered.clear();
		passwordView.removeAllStars();
	}

	@Override
	public void onBackPressed() {
		AppLockContextCache.instatnce().putItem(Constants.CLEAR_PWD, Constants.CLEAR_PWD);
		super.onBackPressed();
	}
	public void savePassword(View v){
		ArrayList<Integer> passFromLastScreen = (ArrayList<Integer>) AppLockContextCache.instatnce().getItem(Constants.TEMP_PWD);
		if(!MyStringUtils.isNullOrEmpty(passFromLastScreen)){
			if(doPasswordsMatch(passFromLastScreen)){
				String passToSave = "";
				for(int i=0;i<passFromLastScreen.size();i++){
					passToSave += passFromLastScreen.get(i).toString();
				}
				MyPreferenceManager.instance().savePassword(passToSave);
				Toast.makeText(this,"Password Saved :)", Toast.LENGTH_SHORT).show();
				AppLockContextCache.instatnce().putItem(Constants.NEW_PWD_SAVED, Constants.NEW_PWD_SAVED);
				finish();
			}else{
				clearPasswordData();
				String errorPasswordMismatch = getResources().getString(R.string.wrong_password);
				MyCustomDialog.showErrorMessage(errorPasswordMismatch, this);
			}
		}
	}

	private boolean doPasswordsMatch(ArrayList<Integer> passFromLastScreen) {
		if(passFromLastScreen.size() == passEntered.size()){
			for(int i=0;i<passFromLastScreen.size();i++){
				if(passFromLastScreen.get(i).intValue() != passEntered.get(i).intValue())
					return false;
			}
			return true;
		}
		return false;
	}
	public void resetPassword(View v){
		clearPasswordData();
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
			savePassword(v);
			return;
		}
		passwordView.addStarsToPassword();
		passEntered.add(Integer.valueOf(tag));
	}
}
