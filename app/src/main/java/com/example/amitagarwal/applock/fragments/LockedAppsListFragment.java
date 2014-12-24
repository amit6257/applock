package com.example.amitagarwal.applock.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

import com.example.amitagarwal.applock.broadcastreceiver.DataBaseHandler;
import com.example.amitagarwal.applock.broadcastreceiver.LApplication;
import com.example.amitagarwal.applock.broadcastreceiver.MyCallback;
import com.example.amitagarwal.applock.database.LockedAppDataForDao;
import com.example.amitagarwal.applock.database.LockedAppsDao;
import com.example.amitagarwal.applock.utils.AdsUtils;
import com.example.amitagarwal.applock.views.AppsListAdapterForLockedApps;
import com.example.amitagarwal.applock.views.CustomEditText;
import com.example.amitagarwal.applock.views.MyCustomDialog;
import com.example.amitagarwal.applocks.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class LockedAppsListFragment extends AppLockBaseFragment implements OnClickListener{

	private ArrayList<LockedAppDataForDao> listViewData = new ArrayList<LockedAppDataForDao>();	
	private CustomEditText searchApp;
	private AppsListAdapterForLockedApps listAdapter ;
	private ListView appsList;
	View rootView ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.apps_list_main_activity, container, false);
		initAdsView();

		appsList = (ListView)rootView.findViewById(R.id.listview_main_activity);

		listAdapter = new AppsListAdapterForLockedApps(activity,listViewData,this); 
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
				listViewData.addAll((ArrayList<LockedAppDataForDao>)obj);
				listAdapter = new AppsListAdapterForLockedApps(activity, listViewData, LockedAppsListFragment.this);
				appsList.setAdapter(listAdapter);
			}
		};
	}
	private void initDataAndRefreshView() {
		dataBaseHandler.loadLockedAppsFromDb(activity);
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
	public void onClick(final View v) {
		
		TextView appNameTV = (TextView)v.findViewById(R.id.listview_text);
		String packageName =  (String)appNameTV.getTag();
		
		LockedAppDataForDao packageData = null;
		for(LockedAppDataForDao p : listViewData){
			if(p.getPid().equalsIgnoreCase(packageName)){
				packageData = p;
				break;
			}
		}
		final LockedAppDataForDao lockedApp = packageData;
		
		MyCustomDialog.showActionDialog("Unlock "+ lockedApp.getAppname(), "Are you really sure you want to unlock this app??",
				activity, new MyCallback() {
					
					@Override
					public void callBack(Object obj) {
						String result = (String)obj;
						if(result.equalsIgnoreCase("Ok")){							
							UnProtectAppDB protectAppDB = new UnProtectAppDB(lockedApp);
							protectAppDB.execute();
						}else if(result.equalsIgnoreCase("Cancel")){
							return;
						}else{
							System.out.println("impossibleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
						}
					}
				});		
	}
	
	private class UnProtectAppDB extends AsyncTask<Void, Void, Boolean>{	
		
		LockedAppsDao lockedAppsDao;
		LockedAppDataForDao lockedAppDataForDao;

		public UnProtectAppDB(LockedAppDataForDao packageData){
			lockedAppsDao = new LockedAppsDao(activity);
			this.lockedAppDataForDao = packageData;			
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			if (lockedAppDataForDao != null) {
				lockedAppsDao.delete(lockedAppDataForDao);
			}
			//update registered apps list in Application file
			removeRegisteredAppsDataFromDb(lockedAppDataForDao.getPid());
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			Toast.makeText(activity, lockedAppDataForDao.getAppname() +" un-locked", Toast.LENGTH_SHORT).show();
			removePackageAndRefreshListView(lockedAppDataForDao.getPid());
		}
	}
	
	@Override
	public void refreshFragment() {
		super.refreshFragment();
		initDataForNewApps();
	}
	public void removePackageAndRefreshListView(String pid) {
		
		listAdapter.removePackageAndRefreshViews(pid);
	}
	public void removeRegisteredAppsDataFromDb(String pid) {
		LApplication.registeredApps.remove(pid);
	}
	private void initDataForNewApps() {
		listAdapter = new AppsListAdapterForLockedApps(activity,true);
		appsList.setAdapter(listAdapter);
		initDataAndRefreshView();
	}
}
