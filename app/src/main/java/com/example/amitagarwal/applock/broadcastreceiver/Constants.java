package com.example.amitagarwal.applock.broadcastreceiver;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {
	
	public static final String TAG = "ActivityTag123";
	
	public static final String DEFAULT_PWD_AS_STRING = "2468";
	
	public static final String PASSWORD_PRIMARY_KEY = "password";

	public static final int MIN_PASSWORD_LENGTH = 4;
	
	public static final String SYSTEM_APP = "SYSTEM_APP";
	
	public static final String USER_APP = "USER_APP";
	
	public static final String TRACK_APP_OPENED = "TRACK_APP_OPENED";

	public static final String SHOW_PWD = "SHOW_PWD";

	public static final String DONT_SHOW_PWD = "DONT_SHOW_PWD";

	public static final String CLEAR_PWD = "CLEAR_PWD";

	public static final String TEMP_PWD = "TEMP_PWD";

	public static final String NEW_PWD_SAVED = "NEW_PWD_SAVED";
	
	public static final ArrayList<String> SYSTEM_APPS = new ArrayList<String>(Arrays.asList
			(
					"com.google.android.calender",
					"com.google.android.GoogleCamera",
					"com.android.chrome",
					"com.google.android.deskclock",
					"com.google.android.apps.docs",
					"com.google.android.email",
					"com.google.android.gallery3d",
					"com.google.android.gm",
					"com.google.android.music",
					"com.android.vending",
					"com.google.android.apps.plus",
					"com.google.android.talk",
					"com.google.android.keep",
					"com.google.android.apps.maps",
					"com.quickoffice.android",
					"com.android.settings",
					"com.google.android.youtube"
			)
			);
}
