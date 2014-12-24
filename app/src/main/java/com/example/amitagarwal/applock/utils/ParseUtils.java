package com.example.amitagarwal.applock.utils;

import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.broadcastreceiver.LApplication;
import com.example.amitagarwal.applock.config.AppLockDeviceInfoProvider;
import com.example.amitagarwal.applock.config.ApplockPropertiesReader;
import com.example.amitagarwal.applock.config.ApplockPropertiesReader.AppEnvironment;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

public class ParseUtils {

	public static void sendParseException(Exception e){

		try{
			AppEnvironment appVersionType = new ApplockPropertiesReader().getAppEnvironment(LApplication.getAppContext());
			switch (appVersionType) {
			case DEVELOPMENT:
				//do not log when development mode..but print it
				System.out.println(e.toString());
				return;
			case RELEASE:
				ParseObject exception = new ParseObject("exceptionHandled");    	
				exception.put("exception", e.toString());
				exception.saveInBackground();
			}		
		}catch(Exception ex){			
		}			
	}

	public static void sendParseException(Exception e,String extraMessage){

		try{
			AppEnvironment appVersionType = new ApplockPropertiesReader().getAppEnvironment(LApplication.getAppContext());
			switch (appVersionType) {
			case DEVELOPMENT:
				//do not log when development mode..but print it
				System.out.println(e.toString() + " , extraMessage " + extraMessage);
				return;
			case RELEASE:
				ParseObject exception = new ParseObject("exceptionHandled");    	
				exception.put("exception", e.toString() + " , extraMessage " + extraMessage);
				exception.saveInBackground();
			}	
		}catch(Exception ex){			
		}			
	}

	public static void sendUserAgentToParse(){

		ParseObject userAgent = new ParseObject("useragent");    	
		userAgent.put("useragent", AppLockDeviceInfoProvider.getUserAgent());
		userAgent.saveInBackground();    	
	}

	public static void trackAppOpened(){
		ParseAnalytics.trackEvent(Constants.TRACK_APP_OPENED);
	}
}