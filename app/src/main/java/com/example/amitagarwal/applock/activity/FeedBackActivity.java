package com.example.amitagarwal.applock.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amitagarwal.applock.broadcastreceiver.LApplication;
import com.example.amitagarwal.applock.config.AppLockDeviceInfoProvider;
import com.example.amitagarwal.applocks.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class FeedBackActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
	}
	
	public void sendFeedback(View v){
		
		((Button)v).setClickable(false);
		
		EditText feedbackText = (EditText)findViewById(R.id.feedback_text);
		String text = feedbackText.getText().toString();
		
		ParseObject feedbackParseObject = new ParseObject("feedback");
		feedbackParseObject.put("feedbackText", text);
		feedbackParseObject.put("useragent", AppLockDeviceInfoProvider.getUserAgent());
		feedbackParseObject.saveInBackground(new SaveCallback() {		
			@Override
			public void done(ParseException e) {
				Toast.makeText(LApplication.getAppContext(), "Thankyou for your feedback :)", Toast.LENGTH_SHORT).show();				
			}
		});
		finish();
	}
}