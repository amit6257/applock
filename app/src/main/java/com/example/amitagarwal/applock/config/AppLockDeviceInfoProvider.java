package com.example.amitagarwal.applock.config;

import android.os.Build;

import com.example.amitagarwal.applock.broadcastreceiver.LApplication;

public class AppLockDeviceInfoProvider {
	public static String getManufacturer(){
		return Build.MANUFACTURER;
	}
	
	public static String getModel(){
		return Build.MODEL;
	}
	
	public static String getUserAgent(){
		StringBuilder uaBuilder = new StringBuilder();
		uaBuilder.append("app/applock");
    	uaBuilder.append("/").append(ApplockConfigProvider.getAppVersionNumber(LApplication.getAppContext()));
    	uaBuilder.append("/Android");
    	
    	uaBuilder.append(" (").append(AppLockDeviceInfoProvider.getManufacturer());
    	uaBuilder.append("/").append(AppLockDeviceInfoProvider.getModel());
    	uaBuilder.append(")");
    	
    	return uaBuilder.toString();
	}
}
