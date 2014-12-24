package com.example.amitagarwal.applock.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.example.amitagarwal.applock.broadcastreceiver.AppLockContextCache;
import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applocks.R;

public class UpgradeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.upgrade);
		
		Button cancel = (Button)findViewById(R.id.cancel);
		Button upgrade = (Button)findViewById(R.id.upgrade);

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AppLockContextCache.instatnce().putItem(Constants.SHOW_PWD, Constants.DONT_SHOW_PWD);
				finish();

			}
		});
		upgrade.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//open the play store link.. for the app
				final String packageName = "com.example.amitagarwal.applockspro";
				String uri = getResources().getString(R.string.share_extra_text);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri + packageName));
				startActivity(intent);
				finish();

			}
		});
	}

	@Override
	public void onBackPressed() {
		AppLockContextCache.instatnce().putItem(Constants.SHOW_PWD, Constants.DONT_SHOW_PWD);
		super.onBackPressed();
	}
}
