package com.example.amitagarwal.applock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.amitagarwal.applocks.R;

public class WelcomeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);

	}

	public void setPassword(View v){
		//setInitial pwd
		Intent setPwdScreenIntent = new Intent(this,SetPassFirstActivity.class);
		startActivity(setPwdScreenIntent);		
		finish();
	}
}
