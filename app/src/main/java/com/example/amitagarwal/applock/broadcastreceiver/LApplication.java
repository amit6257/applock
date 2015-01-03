package com.example.amitagarwal.applock.broadcastreceiver;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.crittercism.app.Crittercism;
import com.example.amitagarwal.applock.config.ApplockConfigProvider;
import com.example.amitagarwal.applock.utils.MyPreferenceManager;
import com.parse.Parse;

import java.util.ArrayList;

public class LApplication extends Application{

	public static int serviceCount = 0;
	private static String previousPackageName = "";
	private static Context appContext;
	public static ArrayList<String> registeredApps = new ArrayList<String>();

	@Override
	public void onCreate(){		
		super.onCreate();
		appContext = getApplicationContext();		
	
		//register with parse....not working now
		//exception is 06-20 03:46:04.101: E/ParseCommandCache(4600): com.parse.ParseException: at least one 
		//ID field (installationId,deviceToken) must be specified in this operation
		//disabling push notification for now
		Parse.initialize(this, "ZX4q0t8Ff7NQ77swjygmGNoPTSKkk7WqRX2w2AKY", "tn2c1UGMKGElKNfxBLtlimnfeIhkb9g8wrImXb1m");
		
		//register with critterism
//		Crittercism.init(getApplicationContext(), ApplockConfigProvider.getCrittercismKey(getApplicationContext()));
		
		MyPreferenceManager.instance().initialize(appContext);

		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		BroadcastReceiver mReceiver = new MyBroadcastReceiver();
		registerReceiver(mReceiver, filter);
	}

	public static String getPreviousPackageName() {
		return previousPackageName;
	}

	public static void setPreviousPackageName(String previousPackageName) {
		LApplication.previousPackageName = previousPackageName;
	}

	public static Context getAppContext() {
		return appContext;
	}
}
