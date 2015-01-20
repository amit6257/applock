package com.example.amitagarwal.applock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amitagarwal.applock.utils.MyPreferenceManager;
import com.example.amitagarwal.applock.utils.ScreenMathUtils;
import com.example.amitagarwal.applock.utils.ViewConstants;
import com.example.amitagarwal.applock.views.PasswordView;
import com.example.amitagarwal.applocks.R;

public class PasswordScreen extends PasswordBaseActivity{

    public int passwordLLHeight;

    @Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_screen);

        TextView title = (TextView) findViewById(R.id.title);
        int titleHeightPix = ScreenMathUtils.dpToPx(ViewConstants.TITLE_HEADING_HEIGHT,this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, titleHeightPix);
        title.setLayoutParams(lp);

        int passStarHeightPix = ScreenMathUtils.dpToPx(ViewConstants.STARRED_PASSWORD_HEIGHT,this);
        passwordStarText = (EditText)findViewById(R.id.password_text);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, passStarHeightPix);
        int starPassMarginPix = ScreenMathUtils.dpToPx(ViewConstants.STARRED_PASSWORD_MARGIN_TOP,this);
        lp2.setMargins(0, starPassMarginPix,0,0);
        passwordStarText.setLayoutParams(lp2);

		LinearLayout passwordScreen = (LinearLayout)findViewById(R.id.password_screen);
        //TODO: subtract any other heights when required
        //remaining height in pixels
        passwordLLHeight = ScreenMathUtils.getScreenHeightInPixels(this) -
                titleHeightPix -
                passStarHeightPix -
                starPassMarginPix - 250;
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, passwordLLHeight);
        passwordScreen.setLayoutParams(lp3);

        passwordView = new PasswordView(this,passwordEvaluator);
        passwordScreen.addView(passwordView);
		//init vars
		MyPreferenceManager.instance().initialize(PasswordScreen.this);
        System.out.println("screen ht = " + ScreenMathUtils.getScreenHeightInPixels(this));
        System.out.println("title ht = " + titleHeightPix);
        System.out.println("start ht , margin " + passStarHeightPix + ", "+ starPassMarginPix);
        System.out.println("ht pass viewLL " + passwordLLHeight);
    }

	@Override
	public void onBackPressed() {
		launchHomeScreen();
		super.onBackPressed();		
	}

	private void launchHomeScreen() {
		Intent launchHomeScreen = new Intent(Intent.ACTION_MAIN);
		launchHomeScreen.addCategory(Intent.CATEGORY_HOME);
		launchHomeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(launchHomeScreen);
		finish();
	}
}
