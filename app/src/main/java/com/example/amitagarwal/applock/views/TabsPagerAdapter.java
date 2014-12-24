package com.example.amitagarwal.applock.views;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.amitagarwal.applock.fragments.AppLockBaseFragment;
import com.example.amitagarwal.applock.fragments.LockedAppsListFragment;
import com.example.amitagarwal.applock.fragments.SystemAppsListFragment;
import com.example.amitagarwal.applock.fragments.ThirdPartyAppsListFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	private AppLockBaseFragment currentFragment;
	private ThirdPartyAppsListFragment thirdPartyAppsListFragment;
	private SystemAppsListFragment systemAppsListFragment;
	private LockedAppsListFragment lockedAppsListFragment;

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public AppLockBaseFragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			if(thirdPartyAppsListFragment == null)
				thirdPartyAppsListFragment =  new ThirdPartyAppsListFragment();
			currentFragment = thirdPartyAppsListFragment;
			return thirdPartyAppsListFragment;
		case 1:
			// Games fragment activity
			if(systemAppsListFragment == null)
				systemAppsListFragment =  new SystemAppsListFragment();
			currentFragment = systemAppsListFragment;
			return systemAppsListFragment;
		case 2:
			// Movies fragment activity
			if(lockedAppsListFragment == null)
				lockedAppsListFragment =  new LockedAppsListFragment();
			currentFragment = lockedAppsListFragment;
			return lockedAppsListFragment;
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

	public AppLockBaseFragment getCurrentFragment() {
		return currentFragment;
	}
	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		if (getCurrentFragment() != object) {
			currentFragment = ((AppLockBaseFragment) object);
		}
		super.setPrimaryItem(container, position, object);
	}
}