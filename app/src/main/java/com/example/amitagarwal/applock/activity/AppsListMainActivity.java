package com.example.amitagarwal.applock.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.amitagarwal.applocks.R;
import com.example.amitagarwal.applock.broadcastreceiver.AppLockContextCache;
import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.broadcastreceiver.LApplication;
import com.example.amitagarwal.applock.fragments.AppLockBaseFragment;
import com.example.amitagarwal.applock.services.MyBackupUpdateService;
import com.example.amitagarwal.applock.services.MyUpdateService;
import com.example.amitagarwal.applock.utils.MyStringUtils;
import com.example.amitagarwal.applock.utils.ParseUtils;
import com.example.amitagarwal.applock.views.TabsPagerAdapter;
import com.parse.ParseAnalytics;

public class AppsListMainActivity extends FragmentActivity implements TabListener{

	private boolean isResumed = false;
	
	private ViewPager viewPager;
	private TabsPagerAdapter tabbedPagerAdapter;
	private ActionBar actionBar;
	// Tab titles
	private static final String[] tabs = { "Third Party Apps", "System Apps", "Protected Apps "};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_fragment_activity);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		tabbedPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(tabbedPagerAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}
		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		ParseAnalytics.trackAppOpened(getIntent());		
		ParseUtils.sendUserAgentToParse();
		ParseUtils.trackAppOpened();

		startAlarmManager(LApplication.getAppContext());
		startBackupAlarmManager(LApplication.getAppContext());
	}

	private void askForPassword() {

		Intent mIntent = new Intent(this,PasswordScreen.class);
		startActivity(mIntent);
	}


	private void startBackupAlarmManager(Context context){
		Intent intent = new Intent(context, MyBackupUpdateService.class);		
		PendingIntent pendingIntent =
				PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		//5 minute alarm
		alarmManager.setRepeating(AlarmManager.RTC, 0,300000, pendingIntent);
	}

	public void startAlarmManager(Context context){
		Intent intent = new Intent(context, MyUpdateService.class);		
		PendingIntent pendingIntent =
				PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC, 0,700, pendingIntent);		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_change_pwd:
			changePassword();	    		
			return true;

		case R.id.sharemyapp:
			share();
			return true;
		case R.id.ratemyapp:
			rateMyApp();
			return true;
		case R.id.give_feedback:
			openFeedBackActivity();
			return true;
		case R.id.refresh:
			refreshFragment();
			return true;
		case R.id.upgrade:
			doUpgradeTask();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	private void refreshFragment() {
		
		Fragment currentFragment = tabbedPagerAdapter.getCurrentFragment();
				
		if(currentFragment != null && currentFragment instanceof AppLockBaseFragment){
			((AppLockBaseFragment)currentFragment).refreshFragment();
		}
		
	}

	private void doUpgradeTask() {
		startActivity(new Intent(this,UpgradeActivity.class));
	}

	private void changePassword() {
		Intent intent = new Intent(this,ChangePasswordActivity.class);
		startActivity(intent);
	}

	private void openFeedBackActivity() {
		Intent feedBack = new Intent(this,FeedBackActivity.class);
		startActivity(feedBack);
	}

	private void rateMyApp() {
		//open the play store link.. for the app
		final String packageName = getPackageName();
		String uri = getResources().getString(R.string.share_extra_text);
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri + packageName));
		startActivity(intent);
	}

	private void share() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		final String packageName = getPackageName();

		// Get a string resource from your app's Resources
		String shareExtraSubject = getResources().getString(R.string.share_extra_subject);
		String shareExtraText = getResources().getString(R.string.share_extra_text);
		String shareChoose = getResources().getString(R.string.share_choose);

		intent.putExtra(Intent.EXTRA_SUBJECT, shareExtraSubject);
		intent.putExtra(Intent.EXTRA_TEXT, shareExtraText + packageName);


		startActivity(Intent.createChooser(intent, shareChoose));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onResume() {


		String showPasswordScreen = (String) AppLockContextCache.instatnce().getItem(Constants.SHOW_PWD);
		if(!MyStringUtils.isNullOrEmpty(showPasswordScreen) && showPasswordScreen.equalsIgnoreCase(Constants.SHOW_PWD)){
			askForPassword();
			AppLockContextCache.instatnce().clearValue(Constants.SHOW_PWD);
		}
		if(!MyStringUtils.isNullOrEmpty(showPasswordScreen) && showPasswordScreen.equalsIgnoreCase(Constants.DONT_SHOW_PWD)){
			//do not ask for pwd
			AppLockContextCache.instatnce().clearValue(Constants.SHOW_PWD);
		}else{
			if(isResumed){
				askForPassword();	
			}

		}	
		isResumed = true;
		super.onResume();		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
}
