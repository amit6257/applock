package com.example.amitagarwal.applock.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.example.amitagarwal.applock.broadcastreceiver.LApplication;

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

    public static int pixToDp(int pix,Context context){
        if (context == null) {
            context = LApplication.getAppContext();
        }
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pix, r.getDisplayMetrics());
        return (int)px;
    }

    public static int getScreenHeightInPixels(Context context){
        if (context == null) {
            context = LApplication.getAppContext();
        }
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        return height;

    }

    public static int getWidthInPx(Context context) {

        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        return width;
    }

    public static int getHeightInPx(Context context) {
        //square board
        return getWidthInPx(context);
    }
}
