package com.example.amitagarwal.applock.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.example.amitagarwal.applock.broadcastreceiver.DataBaseHandler;

public class AppLockBaseFragment extends Fragment{
	
	protected Activity activity = null;
	protected DataBaseHandler dataBaseHandler;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			this.activity = activity;
		} catch (Exception e) {			
		}
	}

	public void refreshFragment() {
	}
}
