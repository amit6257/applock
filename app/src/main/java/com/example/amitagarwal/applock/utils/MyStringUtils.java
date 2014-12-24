package com.example.amitagarwal.applock.utils;

import android.util.Log;

import com.example.amitagarwal.applock.broadcastreceiver.Constants;

import java.util.ArrayList;

public class MyStringUtils {

	public static boolean isNullOrEmpty(String str){
		return str == null || str.length() == 0 || str.equalsIgnoreCase("null"); 
	}
	
	public static void print(String msg){
		Log.d(Constants.TAG,msg + "");
	}
	
	public static <T> boolean isNullOrEmpty(ArrayList<T> list){
		return list == null || list.size() == 0; 
	}
}
