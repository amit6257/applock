package com.example.amitagarwal.applock.config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.example.amitagarwal.applock.config.ApplockPropertiesReader.AppEnvironment;
import com.example.amitagarwal.applock.utils.ParseUtils;

public class ApplockConfigProvider {
	
	
	public static String getCrittercismKey(Context context) {
		AppEnvironment appVersionType = new ApplockPropertiesReader().getAppEnvironment(context);
		switch (appVersionType) {
		case DEVELOPMENT:
			return "5397123483fb79207c000001";
		case RELEASE:
			return "5394ca5a1787841053000001";
		}
		return "";
	}
	public static int getAppVersionNumber(Context tContext){
		PackageInfo pinfo;
		int aVNumber = 0;
		try {
			pinfo = tContext.getPackageManager().getPackageInfo(tContext.getPackageName(), 0);
			aVNumber = pinfo.versionCode;
		} catch (NameNotFoundException e) {			
			ParseUtils.sendParseException(e);
		}
		return aVNumber;
	}
}
