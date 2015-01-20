package com.example.amitagarwal.applock.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.amitagarwal.applock.activity.PasswordScreen;
import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.broadcastreceiver.LApplication;

import java.util.ArrayList;
import java.util.List;

public class ActivityUtils {
	
	private static ActivityManager am;
	public static RunningTaskInfo getActivityAtTop(Context context){
		try{
			if(am == null){
				am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
			}
			List<RunningTaskInfo> l = am.getRunningTasks(1);
			
			return l.get(0);	
		}catch(Exception e){
			ParseUtils.sendParseException(e);
		}
		return null;
	}
	
	public static void openLockScreen(final Context context,String currentPackageName) {
		
		if(isRegisteredApp(context,currentPackageName)){
			
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					Log.d(Constants.TAG,":) opening lockscreen........................");		
//					Intent mIntent = new Intent(context,LScreenActivity.class);
					Intent mIntent = new Intent(context,PasswordScreen.class);
					mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					context.startActivity(mIntent);
				}
			};
			Thread thread = new Thread(runnable);
			thread.start();			
		}
	}
	private static ArrayList<String> getRegisteredApps(Context context){
//		ArrayList<String> registeredApps = new ArrayList<String>();
////		registeredApps.add("com.flipkart.android");
//		registeredApps.add("com.whatsapp");
//		registeredApps.add("com.digiplex.game");
//		registeredApps.add("com.myntra.android");
//		registeredApps.add("com.amazon.mShop.android");
//		registeredApps.add("com.android.calculator2");
		
		
		
		return LApplication.registeredApps;
	}

    public static boolean isRegisteredApp(Context context,String packageName){
		return getRegisteredApps(context).contains(packageName);
	}
}
