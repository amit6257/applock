package com.example.amitagarwal.applock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.amitagarwal.applock.broadcastreceiver.AppLockContextCache;
import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.broadcastreceiver.DataBaseHandler;
import com.example.amitagarwal.applock.utils.MyPreferenceManager;
import com.example.amitagarwal.applock.utils.MyStringUtils;

public class MySplashActivity extends Activity{

	private DataBaseHandler dataBaseHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.splash_screen);

		if(! isAppFirstTimeOpened()){
			startMainActivity();
		}
		else{
            performBackgroundTasks();
            startWelcomeActivity();

		}
	}
	private void startMainActivity() {
		Intent listActivityIntent = new Intent(MySplashActivity.this,AppsListMainActivity.class);		        
		String isBackFromSetPassActivity = (String) AppLockContextCache.instatnce().getItem(Constants.SHOW_PWD);
		if(!MyStringUtils.isNullOrEmpty(isBackFromSetPassActivity) && 
				isBackFromSetPassActivity.equalsIgnoreCase(Constants.DONT_SHOW_PWD)){

		}else
			AppLockContextCache.instatnce().putItem(Constants.SHOW_PWD, Constants.SHOW_PWD);
		startActivity(listActivityIntent);			

		finish();
	}

	private void startWelcomeActivity() {

		Intent intent =  new Intent(MySplashActivity.this,WelcomeActivity.class);		            		
		startActivity(intent);
		finish();
	}

	private void performBackgroundTasks() {
		//init datahandler
		dataBaseHandler = new DataBaseHandler() {

			@Override
			public void resultReceived(Object obj) {
			}
		};
		//init data
		dataBaseHandler.performDbTasksOnFirstLaunch(this);
	}

	private boolean isAppFirstTimeOpened() {

		if(MyStringUtils.isNullOrEmpty(MyPreferenceManager.instance().getPassword())){
			return true;
		}
		return false;
	}
}
