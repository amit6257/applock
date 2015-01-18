package com.example.amitagarwal.applock.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

// This class will hold all the Application Preference that needs to be persisted. 
public class MyPreferenceManager {

	private static MyPreferenceManager instance = null;
	public static MyPreferenceManager instance(){
		if (instance == null)
		{
			synchronized(MyPreferenceManager.class) { 
				if (instance == null)
					instance = new MyPreferenceManager();
			}
		}
		return instance;
	}

	private MyPreferenceManager(){}

	public static final String  KEY_FIRST_TIME_LOAD = "key_first_time_load";

	public static final String  KEY_PASSWORD = "key_password";

	private SharedPreferences sharedPreferences;

	public SharedPreferences getSharedPreferences(){
		return sharedPreferences;
	}

	public void initialize(Context context) {
		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);		
	}

	public void saveFirstTimeHomepageLoad(boolean val){
		this.sharedPreferences.edit().putBoolean(KEY_FIRST_TIME_LOAD, val).commit();
	}

	public boolean isFirstTimeHomepageLoad(){
		return this.sharedPreferences.getBoolean(KEY_FIRST_TIME_LOAD, true);
	}

	public void savePassword(String pwd){
		System.out.println("saving pwd  " + pwd);
		this.sharedPreferences.edit().putString(KEY_PASSWORD, pwd).commit();
	}
	
	public String getPassword(){
		return this.sharedPreferences.getString(KEY_PASSWORD, "");
	}

}