package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.amitagarwal.applock.broadcastreceiver.Constants;

public class MyGridAdapter extends BaseAdapter {

	private Context context;
	
	public MyGridAdapter(Context context) {
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Log.d(Constants.TAG,"in get view " + position);
		
		return new CustomBoxView(context,position);
	}

	@Override
	public int getCount() {	
		return 9;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
