package com.example.amitagarwal.applock.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.amitagarwal.applock.broadcastreceiver.MyCallback;
import com.example.amitagarwal.applock.utils.MyStringUtils;

public class MyCustomAlertDialog extends AlertDialog{

	public MyCustomAlertDialog(Context context) {
		super(context);
		setCanceledOnTouchOutside(false);
	}

	public void showErrorMessage(String errorMessage) {
		setTitle("Error");
		setButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		setMessage(errorMessage);
		show();
	}

	public void showErrorMessageWithCallback(String errorMessage,
			final MyCallback callback) {
		setTitle("Error");
		setButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				callback.callBack(null);
			}
		});
		setMessage(errorMessage);
		show();
		
	}
	
	public void showActionDialog(String title,String errorMessage,
			final MyCallback callback) {
		if(MyStringUtils.isNullOrEmpty(title))
			setTitle("App Lock");
		else setTitle(title);
		
		setButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				callback.callBack("Ok");
			}
		});
		
		setButton2("Cancel", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				callback.callBack("Cancel");
			}
		});
		
		setMessage(errorMessage);
		show();
		
	}
	
}

