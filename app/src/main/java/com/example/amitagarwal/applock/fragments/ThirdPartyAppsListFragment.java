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
import com.example.amitagarwal.applock.database.ThirdPartyAppDao;
import com.example.amitagarwal.applock.database.ThirdPartyAppDataForDao;
import com.example.amitagarwal.applock.utils.AdsUtils;
import com.example.amitagarwal.applock.utils.ListUtils;
import com.example.amitagarwal.applock.utils.MyTrackingHelper;
import com.example.amitagarwal.applock.views.AppsListAdapter;
import com.example.amitagarwal.applock.views.CustomEditText;
import com.example.amitagarwal.applocks.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class ThirdPartyAppsListFragment extends AppLockBaseFragment implements OnClickListener{

	private ArrayList<ThirdPartyAppDataForDao> listViewData = new ArrayList<ThirdPartyAppDataForDao>();	
	private CustomEditText searchApp;
	private AppsListAdapter listAdapter ;
	private ListView appsList;
	View rootView ;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.apps_list_main_activity, container, false);
		initAdsView();

		appsList = (ListView)rootView.findViewById(R.id.listview_main_activity);

		listAdapter = new AppsListAdapter(activity,listViewData,this); 
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
				if(ListUtils.isNullOrEmpty((ArrayList<ThirdPartyAppDataForDao>)obj)){
					obj = new ArrayList<ThirdPartyAppDataForDao>();
				}
				listViewData.addAll((ArrayList<ThirdPartyAppDataForDao>)obj);
				listAdapter = new AppsListAdapter(activity, listViewData, ThirdPartyAppsListFragment.this);
				appsList.setAdapter(listAdapter);
			}
		};
	}
	private void initDataAndRefreshView() {
		dataBaseHandler.loadThirdPartyAppsFromDb(activity);
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
		TextView appName = (TextView)v.findViewById(R.id.listview_text);
		String packageName =  (String)appName.getTag();
		MyTrackingHelper.trackAppLocked(AppLockDeviceInfoProvider.getUserAgent() + "/" + packageName);

		ThirdPartyAppDataForDao packageData = null;
		for(ThirdPartyAppDataForDao p : listViewData){
			if(p.getPid().equalsIgnoreCase(packageName)){
				packageData = p;
				break;
			}
		}

		ProtectAppDB protectAppDB = new ProtectAppDB(packageData);
		protectAppDB.execute();
	}
	
	private class ProtectAppDB extends AsyncTask<Void, Void, Boolean>{

		ThirdPartyAppDao packageDao;
		ThirdPartyAppDataForDao packageDataForDao;		
		
		LockedAppsDao lockedAppsDao;
		LockedAppDataForDao lockedAppDataForDao;

		public ProtectAppDB(ThirdPartyAppDataForDao thirdPartyAppDataForDao){
			packageDao = new ThirdPartyAppDao(activity);
			this.packageDataForDao = thirdPartyAppDataForDao;
			
			lockedAppsDao = new LockedAppsDao(activity);
			lockedAppDataForDao = new LockedAppDataForDao(packageDataForDao.getPid(), packageDataForDao.getAppname(), false);
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
		listAdapter = new AppsListAdapter(activity,true);
		appsList.setAdapter(listAdapter);
		dataBaseHandler.performDbTaskRefreshNewApps(activity, true);
	}
	@Override
	public void refreshFragment() {
		super.refreshFragment();
		initDataForNewApps();
	}


}