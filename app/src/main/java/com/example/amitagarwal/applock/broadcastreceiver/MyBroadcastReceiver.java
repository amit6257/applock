package com.example.amitagarwal.applock.broadcastreceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.amitagarwal.applock.services.MyUpdateService;

public class MyBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(Constants.TAG,intent.getAction().toString() + "broadcast recvdddddddddddddddddddddddddddddd intent= " + intent.getAction());		
		if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
			cancelAlarmManager(context, intent);

		}else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
			startAlarmManager(context, intent);
		}
		else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
			startAlarmManager(context, intent);
		}
	}
	private void startAlarmManager(Context context , Intent broadcastIntent){
		Intent intent = new Intent(context, MyUpdateService.class);		
		PendingIntent pendingIntent =
				PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC, 0,700, pendingIntent);
		Log.d(Constants.TAG,"alarm started by intent " + broadcastIntent.getAction());
	}

	private void cancelAlarmManager(Context context,Intent intent){
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		Intent updateServiceIntent = new Intent(context, MyUpdateService.class);
		PendingIntent pendingUpdateIntent = PendingIntent.getService(context, 0, updateServiceIntent, 0);

		// Cancel alarms
		try {
			alarmManager.cancel(pendingUpdateIntent);
			Log.d(Constants.TAG,"Alarm manager successfully cancell on intent " + intent.getAction());
		} catch (Exception e) {
			Log.d(Constants.TAG,"AlarmManager update was not canceled. " + e.toString());
		}
	}

}