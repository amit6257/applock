package com.example.amitagarwal.applock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.views.ConfigurePasswordView;
import com.example.amitagarwal.applocks.R;

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
