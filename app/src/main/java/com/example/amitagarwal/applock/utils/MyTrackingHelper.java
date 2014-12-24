package com.example.amitagarwal.applock.utils;

import com.parse.ParseObject;

public class MyTrackingHelper {
	
	public static void trackAppLocked(String appName){
		ParseObject exception = new ParseObject("trackAppLocked");    	
		exception.put("appName", appName);
		exception.saveInBackground();
	}

}
