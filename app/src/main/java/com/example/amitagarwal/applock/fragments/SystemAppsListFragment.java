package com.example.amitagarwal.applock.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.broadcastreceiver.DataBaseHandler;
import com.example.amitagarwal.applock.broadcastreceiver.LApplication;
import com.example.amitagarwal.applock.config.AppLockDeviceInfoProvider;
import com.example.amitagarwal.applock.database.LockedAppDataForDao;
import com.example.amitagarwal.applock.database.LockedAppsDao;
import com.example.amitagarwal.applock.database.SystemAppDao;
import com.example.amitagarwal.applock.database.SystemAppDataForDao;
import com.example.amitagarwal.applock.utils.AdsUtils;
import com.example.amitagarwal.applock.utils.ListUtils;
import com.example.amitagarwal.applock.utils.MyTrackingHelper;
import com.example.amitagarwal.applock.views.AppsListAdapterForSystemApps;
import com.example.amitagarwal.applock.views.CustomEditText;
import com.example.amitagarwal.applocks.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class SystemAppsListFragment extends AppLockBaseFragment implements OnClickListener{

	private ArrayList<SystemAppDataForDao> listViewData = new ArrayList<SystemAppDataForDao>();	
	private CustomEditText searchApp;
	private AppsListAdapterForSystemApps listAdapter ;
	private ListView appsList;
	View rootView ;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.apps_list_main_activity, container, false);
		initAdsView();

		appsList = (ListView)rootView.findViewById(R.id.listview_main_activity);

		listAdapter = new AppsListAdapterForSystemApps(activity,listViewData,this); 
		appsList.setAdapter(listAdapter);

		searchApp= (CustomEditText)rootView.findViewById(R.id.autocomplete_textview);
		searchApp.setParams("Search your app !!",InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS,EditorInfo.IME_ACTION_NONE);
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		searchApp.getEditText().addTextChangedListener(new TextWatcher() {

			public void onTextChanged( CharSequence s, int start, int before, int count)
			{ 
				listAdapter.alterAdapterData(s);
			}

			public void beforeTextChanged( CharSequence s, int start, int count, int after)
			{}

			public void afterTextChanged( final Editable s)
			{

			}
		});
		
		initDataHandler();
		initDataAndRefreshView();
		return rootView;
	}
	private void initDataHandler() {
		dataBaseHandler = new DataBaseHandler() {
			
			@Override
			public void resultReceived(Object obj) {
				listViewData.clear();				
				if(ListUtils.isNullOrEmpty((ArrayList<SystemAppDataForDao>)obj)){
					obj = new ArrayList<SystemAppDataForDao>();
				}				
				listViewData.addAll((ArrayList<SystemAppDataForDao>)obj);
				listAdapter = new AppsListAdapterForSystemApps(activity, listViewData, SystemAppsListFragment.this);
				appsList.setAdapter(listAdapter);
				
			}
		};
	}
	private void initDataAndRefreshView() {
		dataBaseHandler.loadSystemAppsFromDb(activity);
	}
	private void initAdsView() {

		//google ads
		LinearLayout adsLayout = (LinearLayout)rootView.findViewById(R.id.adsview);
		if(!AdsUtils.isAdsEnabled()){
			adsLayout.setVisibility(View.GONE);
			return;
		}
		AdView adView = new AdView(activity);		
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId("ca-app-pub-5164120210703194/8350760950");
		AdRequest adRequest = new AdRequest.Builder().build();

		adsLayout.addView(adView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		adView.loadAd(adRequest);
	}
	@Override
	public void onClick(View v) {
		Log.d(Constants.TAG, "onclick");
		TextView appNameTV = (TextView)v.findViewById(R.id.listview_text);
		String packageName =  (String)appNameTV.getTag();
		MyTrackingHelper.trackAppLocked(AppLockDeviceInfoProvider.getUserAgent() + "/" + packageName);

		SystemAppDataForDao packageData = null;
		for(SystemAppDataForDao p : listViewData){
			if(p.getPid().equalsIgnoreCase(packageName)){
				packageData = p;
				break;
			}
		}
		ProtectAppDB protectAppDB = new ProtectAppDB(packageData);
		protectAppDB.execute();
	}

	private class ProtectAppDB extends AsyncTask<Void, Void, Boolean>{

		SystemAppDao packageDao;
		SystemAppDataForDao packageDataForDao;		
		
		LockedAppsDao lockedAppsDao;
		LockedAppDataForDao lockedAppDataForDao;

		public ProtectAppDB(SystemAppDataForDao packageData){
			packageDao = new SystemAppDao(activity);
			this.packageDataForDao = packageData;
			
			lockedAppsDao = new LockedAppsDao(activity);
			lockedAppDataForDao = new LockedAppDataForDao(packageDataForDao.getPid(), packageDataForDao.getAppname(), true);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			if (packageDataForDao != null) {
				packageDao.delete(packageDataForDao);
			}
			if(lockedAppDataForDao != null){
				lockedAppsDao.create(lockedAppDataForDao, true);
				System.out.println("added locked app " + lockedAppDataForDao.getAppname());				
			}
			//update registered apps list in Application file
			loadRegisteredAppsDataFromDb(packageDataForDao.getPid());
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			Toast.makeText(activity, packageDataForDao.getAppname() +" locked", Toast.LENGTH_SHORT).show();
			removePackageAndRefreshListView(packageDataForDao.getPid());
		}
	}
	private void loadRegisteredAppsDataFromDb(String pid) {

		LApplication.registeredApps.add(pid);
	}
	public void removePackageAndRefreshListView(String pid) {

		listAdapter.removePackageAndRefreshViews(pid);
		
	}
	private void initDataForNewApps() {
		listAdapter = new AppsListAdapterForSystemApps(activity,true);
		appsList.setAdapter(listAdapter);
		dataBaseHandler.performDbTaskRefreshNewApps(activity, false);
	}
	@Override
	public void refreshFragment() {
		super.refreshFragment();
		initDataForNewApps();
	}
}
