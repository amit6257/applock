package com.example.amitagarwal.applock.services;

import android.app.ActivityManager.RunningTaskInfo;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.broadcastreceiver.LApplication;
import com.example.amitagarwal.applock.database.LockedAppDataForDao;
import com.example.amitagarwal.applock.database.LockedAppsDao;
import com.example.amitagarwal.applock.utils.ActivityUtils;
import com.example.amitagarwal.applock.utils.ParseUtils;

import java.util.ArrayList;
import java.util.List;

public class MyUpdateService extends IntentService
{
	private static int currentServiceLaunchCount = 0;
	RunningTaskInfo topActivity;
	String previousPackageName = "";
	String currentPackageName = "";

	//only use of this variable is to stop service launch count to go to infinity
	private final static int MAX = 100;

	public MyUpdateService(){
		super(MyUpdateService.class.getSimpleName());
		LApplication.serviceCount ++;
	}

	private void loadRegisteredAppsDataFromDb() {

		LockedAppsDao dao = new LockedAppsDao(LApplication.getAppContext());
		List<LockedAppDataForDao> allPackages = dao.getAll();
		final ArrayList<String> registeredApps = new ArrayList<String>();

		for(LockedAppDataForDao packageDataForDao : allPackages){
			registeredApps.add(packageDataForDao.getPid());
		}
		LApplication.registeredApps = registeredApps;
	}

	@Override 
	protected void onHandleIntent(Intent intent)
	{
		try{
			if(currentServiceLaunchCount == 0){
				//load data from DB
				loadRegisteredAppsDataFromDb();
			}
			currentServiceLaunchCount ++;
			if(currentServiceLaunchCount > MAX)
				currentServiceLaunchCount = 1;

			//getTopActivity
			topActivity = ActivityUtils.getActivityAtTop(MyUpdateService.this);
			if(topActivity != null){
				currentPackageName = topActivity.baseActivity.getPackageName();
				if(currentPackageName != null){
					previousPackageName = LApplication.getPreviousPackageName();
					if(isFreshPackage()){
						ActivityUtils.openLockScreen(MyUpdateService.this,currentPackageName);
					}
					if(! currentPackageName.equalsIgnoreCase(LApplication.getAppContext().getPackageName()))
						LApplication.setPreviousPackageName(currentPackageName);	
				}
				else{
					Log.d(Constants.TAG,"currentPackageName is null");
				}
			}
		}catch(Exception e){
			ParseUtils.sendParseException(e);
		}		 
	}

	private boolean isFreshPackage() {
		if(! currentPackageName.equalsIgnoreCase(previousPackageName))
			return true;

		return false;
	}

	@Override
	public void onDestroy(){

		super.onDestroy();		
	}
}