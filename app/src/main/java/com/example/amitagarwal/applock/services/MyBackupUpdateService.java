package com.example.amitagarwal.applock.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.broadcastreceiver.LApplication;

public class MyBackupUpdateService  extends IntentService
{
	public MyBackupUpdateService(){
		super(MyBackupUpdateService.class.getSimpleName());		
	}

	@Override 
	protected void onHandleIntent(Intent intent){
		Log.d(Constants.TAG, "onHandleIntent MyBackupUpdateService");
		startAlarmManager(LApplication.getAppContext());
	}
	
	private void startAlarmManager(Context context){
		Intent intent = new Intent(context, MyUpdateService.class);		
		PendingIntent pendingIntent =
				PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC, 0,700, pendingIntent);		
	}

	@Override
	public void onDestroy(){
		
		Log.d(Constants.TAG, "onDestroy MyBackupUpdateService");
		super.onDestroy();		
	}
}