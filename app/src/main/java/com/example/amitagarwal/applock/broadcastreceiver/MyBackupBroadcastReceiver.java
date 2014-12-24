package com.example.amitagarwal.applock.broadcastreceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.amitagarwal.applock.services.MyBackupUpdateService;

public class MyBackupBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(Constants.TAG, "MyBackupBroadcastReceiver started by intent " + intent.getAction());
		startBackupAlarmManager(context);
	}
	private void startBackupAlarmManager(Context context){
		Intent intent = new Intent(context, MyBackupUpdateService.class);		
		PendingIntent pendingIntent =
				PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		//5 minute alarm
		alarmManager.setRepeating(AlarmManager.RTC, 0,300000, pendingIntent);
	}
}