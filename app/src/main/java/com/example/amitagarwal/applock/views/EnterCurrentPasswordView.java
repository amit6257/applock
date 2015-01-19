package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.amitagarwal.applock.utils.ActivityUtils;
import com.example.amitagarwal.applock.utils.ScreenMathUtils;
import com.example.amitagarwal.applocks.R;

public class EnterCurrentPasswordView extends RelativeLayout{

	private int screenWidth;
	private LinearLayout appNameLayout;
	private PasswordLayout passwordLayout;
	private OnClickListener listener; 
	private int noOfStars = 0;
	private EditText passwordText;

	private void addDivider(LinearLayout passwordLayout, Context context) {
		View dividerView = new View(context);
		dividerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,ScreenMathUtils.dpToPx(1, context)));
		passwordLayout.addView(dividerView);
	}

	//not using for now
	private void addAppNameView(Context context) {
		appNameLayout = new LinearLayout(context);
		appNameLayout.setOrientation(LinearLayout.HORIZONTAL);
		appNameLayout.setId(11);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, ScreenMathUtils.dpToPx(20, context));
		appNameLayout.setLayoutParams(lp);
		ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(new LayoutParams(40,40));
		imageView.setBackgroundResource(R.drawable.lock);

		TextView appName = new TextView(context);
		appName.setLayoutParams(new LayoutParams(CustomViewConstants.APPNAME_WIDTH ,LayoutParams.WRAP_CONTENT));
		appName.setText("App Lock");

		appNameLayout.addView(imageView);
		appNameLayout.addView(appName);	
		appNameLayout.setGravity(Gravity.CENTER_HORIZONTAL);

		addView(appNameLayout);
	}

	private void addPassswordTextView(Context context) {

		passwordText = new EditText(context);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,CustomViewConstants.EDITTEXT_HEIGHT);
		lp.addRule(RelativeLayout.ABOVE,passwordLayout.getId());
		lp.setMargins(ScreenMathUtils.dpToPx(5, context), 0, ScreenMathUtils.dpToPx(5, context), ScreenMathUtils.dpToPx(20, context));
		passwordText.setLayoutParams(lp);
		passwordText.setClickable(false);
		passwordText.setKeyListener(null);
		passwordText.setEnabled(false);
		passwordText.setHint("Password");
		passwordText.setPadding(ScreenMathUtils.dpToPx(5, context),0,ScreenMathUtils.dpToPx(5, context), 0);

		addView(passwordText);		
	}

	public EnterCurrentPasswordView(Context context) {
		super(context,null);
	}

	public EnterCurrentPasswordView(Context context,OnClickListener listener) {
		super(context,null);
		this.listener = listener;
		screenWidth = ActivityUtils.getWidthInPx(context);
		CustomViewConstants.BUTTON_WIDTH = screenWidth/3.0 - 2;		

		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		//		addAppNameView(context);
		passwordLayout = new PasswordLayout(context);
		addView(passwordLayout);

		addPassswordTextView(context);

	}

	private class PasswordLayout extends LinearLayout{

		public PasswordLayout(Context context) {
			super(context);
			setId(22);
			setOrientation(LinearLayout.VERTICAL);		
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			setLayoutParams(lp);

			RowLayout rowLayout1 = new RowLayout(context, "1", "2", "3");
			RowLayout rowLayout2 = new RowLayout(context, "4", "5", "6");
			RowLayout rowLayout3 = new RowLayout(context, "7", "8", "9");

			addView(rowLayout1);
			addDivider(this,context);

			addView(rowLayout2);
			addDivider(this,context);

			addView(rowLayout3);
			addDivider(this,context);
			
			//last row is for zero and back button
			LastRow lastRow = new LastRow(context);
			addView(lastRow);
		}
	}
	
	private class LastRow extends LinearLayout{

		public LastRow(Context context) {
			super(context);
			setOrientation(LinearLayout.HORIZONTAL);
			setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,CustomViewConstants.ROW_HEIGHT));
			
			//adding zero
			TextView continueView = new TextView(context);
			continueView.setText(CustomViewConstants.NEXT);
			continueView.setTag(CustomViewConstants.NEXT);
			continueView.setLayoutParams(new LayoutParams((int) CustomViewConstants.BUTTON_WIDTH,CustomViewConstants.BUTTON_HEIGHT));
			continueView.setBackgroundResource(R.drawable.button_selector);
			continueView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
			continueView.setTypeface(null, Typeface.BOLD);
			continueView.setTextColor(getResources().getColor(R.color.black));
			continueView.setGravity(Gravity.CENTER);
			continueView.setOnClickListener(listener);
			addView(continueView);
			
			View verticalView = new View(context);
			verticalView.setLayoutParams(new LayoutParams(ScreenMathUtils.dpToPx(1, context),CustomViewConstants.ROW_HEIGHT));
			addView(verticalView);
			
			//adding zero
			TextView zeroView = new TextView(context);
			zeroView.setText("0");
			zeroView.setTag("0");
			zeroView.setLayoutParams(new LayoutParams((int) CustomViewConstants.BUTTON_WIDTH ,CustomViewConstants.BUTTON_HEIGHT));
			zeroView.setBackgroundResource(R.drawable.button_selector);
			zeroView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
			zeroView.setTypeface(null, Typeface.BOLD);
			zeroView.setTextColor(getResources().getColor(R.color.black));
			zeroView.setGravity(Gravity.CENTER);
			zeroView.setOnClickListener(listener);
			addView(zeroView);
			
			View verticalView2 = new View(context);
			verticalView2.setLayoutParams(new LayoutParams(ScreenMathUtils.dpToPx(1, context),CustomViewConstants.ROW_HEIGHT));
			addView(verticalView2);
			
			TextView cancelView = new TextView(context);
			cancelView.setText(CustomViewConstants.DELETE);
			cancelView.setTag(CustomViewConstants.DELETE);
			cancelView.setLayoutParams(new LayoutParams((int) CustomViewConstants.BUTTON_WIDTH,CustomViewConstants.BUTTON_HEIGHT));
			cancelView.setBackgroundResource(R.drawable.button_selector);
			cancelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
			cancelView.setTypeface(null, Typeface.BOLD);
			cancelView.setTextColor(getResources().getColor(R.color.black));
			cancelView.setGravity(Gravity.CENTER);
			cancelView.setOnClickListener(listener);
			addView(cancelView);
		}		
	}

	private class RowLayout extends LinearLayout{

		public RowLayout(Context context, AttributeSet attrs, String num1, String num2, String num3) {
			super(context, attrs);
			setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,CustomViewConstants.ROW_HEIGHT));
			setOrientation(LinearLayout.HORIZONTAL);

			TextView textView1 = new TextView(context);
			textView1.setText(num1);
			textView1.setTag(num1);
			setButtonParams(textView1);
			addView(textView1);		

			View verticalView = new View(context);
			verticalView.setLayoutParams(new LayoutParams(ScreenMathUtils.dpToPx(1, context),CustomViewConstants.ROW_HEIGHT));
			addView(verticalView);

			TextView textView2 = new TextView(context);
			textView2.setText(num2);
			textView2.setTag(num2);
			setButtonParams(textView2);
			addView(textView2);

			View verticalView2 = new View(context);
			verticalView2.setLayoutParams(new LayoutParams(ScreenMathUtils.dpToPx(1, context),CustomViewConstants.ROW_HEIGHT));
			addView(verticalView2);

			TextView textView3 = new TextView(context);
			textView3.setText(num3);
			textView3.setTag(num3);
			setButtonParams(textView3);
			addView(textView3);						
		}

		public RowLayout(Context context,String num1,String num2,String num3) {
			this(context,null,num1,num2,num3);
		}
	}

	public void setButtonParams(TextView textView) {
		textView.setLayoutParams(new LayoutParams((int) CustomViewConstants.BUTTON_WIDTH,CustomViewConstants.BUTTON_HEIGHT));
		textView.setBackgroundResource(R.drawable.button_selector);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
		textView.setTypeface(null, Typeface.BOLD);
		textView.setTextColor(getResources().getColor(R.color.black));
		textView.setGravity(Gravity.CENTER);
		textView.setOnClickListener(listener);
	}

	public void addStarsToPassword() {
		noOfStars ++;
		StringBuilder stars = new StringBuilder();
		for(int i=0;i<noOfStars;i++){
			stars.append("*");
		}
		passwordText.setText(stars.toString());
	}

	public void removeStarsFromPassword() {
		if(noOfStars <= 0){
			return;
		}
		noOfStars --;
		StringBuilder stars = new StringBuilder();
		for(int i=0;i<noOfStars;i++){
			stars.append("*");
		}
		passwordText.setText(stars.toString());
		
	}
	public void removeAllStars(){
		noOfStars = 0;
		passwordText.setText("");
	}
}