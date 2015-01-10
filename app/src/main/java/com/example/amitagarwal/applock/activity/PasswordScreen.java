package com.example.amitagarwal.applock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.broadcastreceiver.AppLockContextCache;
import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.utils.ActivityUtils;
import com.example.amitagarwal.applock.utils.ScreenMathUtils;
import com.example.amitagarwal.applock.utils.MathUtils;
import com.example.amitagarwal.applock.utils.MyPreferenceManager;
import com.example.amitagarwal.applock.views.CustomViewConstants;
import com.example.amitagarwal.applock.views.MyCustomPasswordView;
import com.example.amitagarwal.applock.views.PasswordEvaluator;
import com.example.amitagarwal.applock.views.PasswordView;
import com.example.amitagarwal.applocks.R;

public class PasswordScreen extends Activity implements OnClickListener {

	public static final int GRIDVIEW_HORIZONTAL_SPACING = 1;
	public static final int GRIDVIEW_VERTICAL_SPACING = 1;

	private int buttonPressCount = 0;
    private int noOfStars = 0;
	private int passwordLength;
    private PasswordEvaluator passwordEvaluator;
	private int[] pwdEntered;

	private int savedPassword[];	
//	private MyCustomPasswordView passwordView;
    private PasswordView passwordView;
    EditText passwordText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_screen);
        passwordText = (EditText)findViewById(R.id.password_text);
        passwordEvaluator = new PasswordEvaluator(this);
		LinearLayout passwordScreen = (LinearLayout)findViewById(R.id.password_screen);


//		passwordView = new MyCustomPasswordView(this,this);
//		passwordScreen.addView(passwordView);

        passwordView = new PasswordView(this,passwordEvaluator);
        passwordScreen.addView(passwordView);
		//init vars
		MyPreferenceManager.instance().initialize(PasswordScreen.this);
		MathUtils.fillWithZeros(pwdEntered, passwordLength);
		setSavedPwdAndPwdLength();
	}

	private void setSavedPwdAndPwdLength() {
		String pwd = MyPreferenceManager.instance().getPasswod();

		passwordLength = pwd.length();

		pwdEntered = new int[100];
		savedPassword = new int[passwordLength];
		int c;
		for(int i=0;i<passwordLength;i++){
			c = pwd.charAt(i) - 48;
			savedPassword[i] = c;
		}		
	}

	private void resetValues() {
		MathUtils.fillWithZeros(pwdEntered, passwordLength);
		buttonPressCount = 0;
	}

	private boolean isPasswordValid() {

		for(int i = 0;i<passwordLength;i++){

			if(pwdEntered[i] != savedPassword[i]){
				return false;
			}
		}
		return true;
	}

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

	@Override
	public void onClick(View v) {


//		if(buttonPressCount == passwordLength)
//			return;
		
		String tag = (String)v.getTag();
		if(tag.equalsIgnoreCase(CustomViewConstants.GO_HOME)){
			onBackPressed();
			return;
		}
		if(tag.equalsIgnoreCase(CustomViewConstants.DELETE)){
			if(buttonPressCount == 0)
				return;
//			passwordView.removeStarsFromPassword();
			buttonPressCount --;
			return;
		}
//		passwordView.addStarsToPassword();
		pwdEntered[buttonPressCount] = Integer.parseInt((String)v.getTag());

		buttonPressCount ++;

		if(buttonPressCount == passwordLength){
			Log.d(Constants.TAG,"your pwd entered");

			boolean isPasswordValid = isPasswordValid();
			if(isPasswordValid){
				//				Toast.makeText(this, "YAYYY!!! correct pwd :)", Toast.LENGTH_SHORT).show();
				Log.d(Constants.TAG,"YAYYY!!! correct pwd :)");
				AppLockContextCache.instatnce().putItem(Constants.SHOW_PWD, Constants.DONT_SHOW_PWD);
				finish();
			}
//			else{
//				Log.d(Constants.TAG,"LOLL.. incorrect pwd :(");
//				//				Toast.makeText(this, "LOLL.. incorrect pwd :(", Toast.LENGTH_SHORT).show();
//				// Get a string resource from your app's Resources
//				String errorEnterCorrectPwd = getResources().getString(R.string.error_enter_correct_pwd);
//				MyCustomDialog.showErrorMessage(errorEnterCorrectPwd, PasswordScreen.this);
//
//				resetValues();
//			}
		}


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
