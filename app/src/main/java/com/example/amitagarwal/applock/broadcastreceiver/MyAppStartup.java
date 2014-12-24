package com.example.amitagarwal.applock.broadcastreceiver;

import android.content.Context;

public class MyAppStartup {

	private static MyAppStartup instance;

	public MyAppStartup(Context cxt){

	}

	public MyAppStartup(){

	}

	public static synchronized MyAppStartup instance() {
		if (instance == null) {
			instance = new MyAppStartup();
		}
		return instance;
	}

	

	
}
