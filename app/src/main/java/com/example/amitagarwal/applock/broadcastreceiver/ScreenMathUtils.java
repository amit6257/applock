package com.example.amitagarwal.applock.broadcastreceiver;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class ScreenMathUtils {

	public static int dpToPx(int dp, Context context){
		if (context == null) {
			context = LApplication.getAppContext();
		}
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
		if(px < 1.0f){
			px = 1;
		}
		return (int)px;
	}
}
