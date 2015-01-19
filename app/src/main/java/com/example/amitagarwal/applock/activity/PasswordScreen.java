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

	public static final int GRIDVIEW_HORIZONTAL_SPACING = 1;
	public static final int GRIDVIEW_VERTICAL_SPACING = 1;
//	private int buttonPressCount = 0;
//	private int passwordLength;
//	private int[] pwdEntered;
//	private int savedPassword[];
//	private MyCustomPasswordView passwordView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_screen);

		LinearLayout passwordScreen = (LinearLayout)findViewById(R.id.password_screen);
        passwordStarText = (EditText)findViewById(R.id.password_text);

//		passwordView = new MyCustomPasswordView(this,this);
//		passwordScreen.addView(passwordView);

        passwordView = new PasswordView(this,passwordEvaluator);
        passwordScreen.addView(passwordView);
		//init vars
		MyPreferenceManager.instance().initialize(PasswordScreen.this);
//		MathUtils.fillWithZeros(pwdEntered, passwordLength);
//		setSavedPwdAndPwdLength();
	}

//	private void setSavedPwdAndPwdLength() {
//		String pwd = MyPreferenceManager.instance().getPassword();
//
//		passwordLength = pwd.length();
//
//		pwdEntered = new int[100];
//		savedPassword = new int[passwordLength];
//		int c;
//		for(int i=0;i<passwordLength;i++){
//			c = pwd.charAt(i) - 48;
//			savedPassword[i] = c;
//		}
//	}

//	private void resetValues() {
//		MathUtils.fillWithZeros(pwdEntered, passwordLength);
//		buttonPressCount = 0;
//	}

//	private boolean isPasswordValid() {
//
//		for(int i = 0;i<passwordLength;i++){
//
//			if(pwdEntered[i] != savedPassword[i]){
//				return false;
//			}
//		}
//		return true;
//	}

	@Override
	public void onBackPressed() {
		launchHomeScreen();
		super.onBackPressed();		
	}

	private void launchHomeScreen() {

		Log.d(Constants.TAG, "launchHomeScreen on backpress");

		Intent launchHomeScreen = new Intent(Intent.ACTION_MAIN);
		launchHomeScreen.addCategory(Intent.CATEGORY_HOME);
		launchHomeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(launchHomeScreen);
		finish();
	}

//	@Override
//	public void onClick(View v) {
//
//
////		if(buttonPressCount == passwordLength)
////			return;
//
//		String tag = (String)v.getTag();
//		if(tag.equalsIgnoreCase(CustomViewConstants.GO_HOME)){
//			onBackPressed();
//			return;
//		}
//		if(tag.equalsIgnoreCase(CustomViewConstants.DELETE)){
//			if(buttonPressCount == 0)
//				return;
////			passwordView.removeStarsFromPassword();
//			buttonPressCount --;
//			return;
//		}
////		passwordView.addStarsToPassword();
//		pwdEntered[buttonPressCount] = Integer.parseInt((String)v.getTag());
//
//		buttonPressCount ++;
//
//		if(buttonPressCount == passwordLength){
//			Log.d(Constants.TAG,"your pwd entered");
//
//			boolean isPasswordValid = isPasswordValid();
//			if(isPasswordValid){
//				//				Toast.makeText(this, "YAYYY!!! correct pwd :)", Toast.LENGTH_SHORT).show();
//				Log.d(Constants.TAG,"YAYYY!!! correct pwd :)");
//				AppLockContextCache.instatnce().putItem(Constants.SHOW_PWD, Constants.DONT_SHOW_PWD);
//				finish();
//			}
////			else{
////				Log.d(Constants.TAG,"LOLL.. incorrect pwd :(");
////				//				Toast.makeText(this, "LOLL.. incorrect pwd :(", Toast.LENGTH_SHORT).show();
////				// Get a string resource from your app's Resources
////				String errorEnterCorrectPwd = getResources().getString(R.string.error_enter_correct_pwd);
////				MyCustomDialog.showErrorMessage(errorEnterCorrectPwd, PasswordScreen.this);
////
////				resetValues();
////			}
//		}
//
//
//	}
}
