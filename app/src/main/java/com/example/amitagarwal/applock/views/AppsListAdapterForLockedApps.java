package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amitagarwal.applock.database.LockedAppDataForDao;
import com.example.amitagarwal.applock.utils.MyStringUtils;
import com.example.amitagarwal.applock.utils.ParseUtils;
import com.example.amitagarwal.applocks.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class AppsListAdapterForLockedApps  extends BaseAdapter {
	ArrayList<LockedAppDataForDao> initialListViewData = new ArrayList<LockedAppDataForDao>();
	ArrayList<LockedAppDataForDao> partialData;
	LayoutInflater inflater;
	Context context;
	boolean isRefreshing = false;
	OnClickListener listener;
	Map<String, Drawable> appIconMap = new HashMap<String, Drawable>();

	public AppsListAdapterForLockedApps(Context context,ArrayList<LockedAppDataForDao> listViewData,
			OnClickListener listener) {
		this.context = context;
		this.initialListViewData = listViewData;
		this.listener = listener;
		initAppIconMap();

		sortApps();
		this.partialData = new ArrayList<LockedAppDataForDao>(initialListViewData);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public AppsListAdapterForLockedApps(Context context, boolean isRefreshing) {
		this.isRefreshing = isRefreshing;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private void sortApps() {
		Collections.sort(initialListViewData, new Comparator<LockedAppDataForDao>() {

			@Override
			public int compare(LockedAppDataForDao lhs, LockedAppDataForDao rhs) {
				return lhs.getAppname().compareToIgnoreCase(rhs.getAppname());				
			}			
		});
	}

	private Drawable getImageDrawable(String packageName) {
		try {
			return context.getPackageManager().getApplicationIcon(packageName);
		} catch (NameNotFoundException e) {
			ParseUtils.sendParseException(e);
		}
		return context.getResources().getDrawable(R.drawable.ic_launcher);
	}

	private void initAppIconMap() {
		for(LockedAppDataForDao packageData:initialListViewData){
			appIconMap.put(packageData.getPid(), getImageDrawable(packageData.getPid()));
		}
	}

	@Override
	public int getCount() {
		if(isRefreshing)
			return 1;
		return partialData.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(isRefreshing || getCount() == 0){
			return inflater.inflate(R.layout.refreshing_view, null);
		}

		ViewHolder viewHolder = null;

		if(convertView == null){
			convertView = inflater.inflate(R.layout.listview_content, null);
			viewHolder = new ViewHolder();
			viewHolder.appName = (TextView)convertView.findViewById(R.id.listview_text);			
			viewHolder.appIcon = (ImageView)convertView.findViewById(R.id.listview_appicon);
			viewHolder.systemApp = (TextView)convertView.findViewById(R.id.is_system_app);

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.appIcon.setImageDrawable(appIconMap.get(partialData.get(position).getPid()));

		viewHolder.appName.setText(partialData.get(position).getAppname());
		if(partialData.get(position).isSystemApp()){
			viewHolder.systemApp.setText("System App");
		}else{
			viewHolder.systemApp.setText("Third Party App");
		}

		String packagename = partialData.get(position).getPid();
		viewHolder.appName.setTag(packagename);

		convertView.setOnClickListener(listener);		

		return convertView;
	}

	public void alterAdapterData(CharSequence s) {
		String autoSuggestText = s + "";
		partialData.clear();
		if (MyStringUtils.isNullOrEmpty(autoSuggestText)) {
			partialData.addAll(initialListViewData);            
		}
		else {
			autoSuggestText = autoSuggestText.toLowerCase();
			for (int i = 0; i < initialListViewData.size(); i++) {
				if (initialListViewData.get(i).getAppname().toLowerCase().startsWith(autoSuggestText)) {
					partialData.add(initialListViewData.get(i));
				}                
			}
		}
		notifyDataSetChanged();
	}
	private static class ViewHolder{
		TextView appName;
		ImageView appIcon;
		TextView systemApp;
	}
	public void removePackageAndRefreshViews(String pid) {
		//refreshes list view without hitting DB ..it happenes to remove one item from this list which has been locked now
		for(LockedAppDataForDao appDataForDao:initialListViewData){			
			if(appDataForDao.getPid().equalsIgnoreCase(pid)){
				initialListViewData.remove(appDataForDao);
				break;
			}
		}
		for(LockedAppDataForDao appDataForDao:partialData){			
			if(appDataForDao.getPid().equalsIgnoreCase(pid)){
				partialData.remove(appDataForDao);
				break;
			}
		}
		notifyDataSetChanged();

	}

}
