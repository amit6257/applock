package com.example.amitagarwal.applock.utils;

import android.content.Context;
import android.util.Log;

import com.example.amitagarwal.applock.activity.PasswordScreen;
import com.example.amitagarwal.applock.broadcastreceiver.Constants;

public class MathUtils {

	public static float getBoxHeight(Context context) {
		return (((float)(ActivityUtils.getWidthInPx(context) - 2* PasswordScreen.GRIDVIEW_VERTICAL_SPACING))/(float)3);
	}

	public static float getBoxWidth(Context context) {
		return (((float)(ActivityUtils.getWidthInPx(context) - 2* PasswordScreen.GRIDVIEW_VERTICAL_SPACING))/(float)3);		
	}

	private static void fillArrayWithZeros(int[][] c) {
		int i,j;
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
				c[i][j] = 0;
	}

	public static void fillWithZeros(int []array,int passLength){
		for(int i = 0;i<passLength;i++){			
			array[i] = 0;
		}
	}

	public static void printOneDArray(int[] oneDArray, int passLength) {
		for(int i=0;i<passLength;i++){
			Log.d(Constants.TAG,oneDArray[i] + "  ");
		}
	}
}
