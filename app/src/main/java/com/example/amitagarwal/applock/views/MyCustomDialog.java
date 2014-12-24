package com.example.amitagarwal.applock.views;

import android.content.Context;

import com.example.amitagarwal.applock.broadcastreceiver.MyCallback;
import com.example.amitagarwal.applock.utils.ParseUtils;

public class MyCustomDialog {

	private static MyCustomAlertDialog customAlertDialog = null;

	private static void setDialog(Context context) {
		try{
			if (context != null) {
				if (customAlertDialog != null) {
					if (customAlertDialog.isShowing()) {
						customAlertDialog.dismiss();		
					}
					customAlertDialog = null;
				}
				customAlertDialog = new MyCustomAlertDialog(context);
			}	
		}catch(Exception e){
			ParseUtils.sendParseException(e);
		}		
	}

	public static void showErrorMessage(String errorMessage,Context context) {
		try{
			synchronized (MyCustomDialog.class) {
				setDialog(context);
				if (customAlertDialog != null) {
					customAlertDialog.showErrorMessage(errorMessage);
				}
			}	
		}catch(Exception e){
			ParseUtils.sendParseException(e);
		}		
	}

	public static void showErrorMessageWithCallback(String errorMessage,Context context,MyCallback callback) {
		synchronized (MyCustomDialog.class) {
			setDialog(context);
			if (customAlertDialog != null) {
				customAlertDialog.showErrorMessageWithCallback(errorMessage,callback);
			}
		}
	}
	
	public static void showActionDialog(String title,String errorMessage,Context context,MyCallback callback) {
		synchronized (MyCustomDialog.class) {
			setDialog(context);
			if (customAlertDialog != null) {
				customAlertDialog.showActionDialog(title,errorMessage,callback);
			}
		}
	}
}