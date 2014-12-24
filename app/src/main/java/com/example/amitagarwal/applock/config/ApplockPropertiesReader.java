package com.example.amitagarwal.applock.config;

import android.content.Context;

import com.example.amitagarwal.applock.utils.ParseUtils;
import com.example.amitagarwal.applocks.R;

import java.io.InputStream;
import java.util.Properties;


public class ApplockPropertiesReader {
	
	
	private String ENVIRONMENT = "Environment";
	
	public AppEnvironment getAppEnvironment(Context context){
		AppEnvironment appVersionType = AppEnvironment.DEVELOPMENT;
		try {
			Properties properties = new Properties();
			InputStream resourceAsStream = context.getResources().openRawResource(R.raw.applock);
			properties.load(resourceAsStream);
			String appTypeValue = properties.getProperty(ENVIRONMENT);
			appVersionType = AppEnvironment.valueOf(appTypeValue);
		} catch (Exception e) {
			ParseUtils.sendParseException(e);
		}
		return appVersionType;
	}

	public enum AppEnvironment {
		DEVELOPMENT, RELEASE
	};
}
