package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.amitagarwal.applock.utils.MyStringUtils;
import com.example.amitagarwal.applock.utils.ScreenMathUtils;
import com.example.amitagarwal.applocks.R;

public class CustomEditText extends LinearLayout {

	private EditText editText = null;
	private ImageView imageView = null;
	private LinearLayout currentView = null;
	
	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		buildView(context);
	}

	public CustomEditText(Context context) {
		super(context);
		buildView(context);
	}

	private void buildView(Context context) {
	
		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
		setBackgroundResource(R.drawable.product_page_image_gallery_empty_border);
		LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT,12);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );	
		editText = (EditText)inflater.inflate(R.layout.generic_edit_text, null);
		LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT,12);
		int margin = ScreenMathUtils.dpToPx(10, context);
		params.setMargins(margin,0,margin,0);
		editText.setLayoutParams(params);
		addView(editText);

		imageView = new ImageView(context);
		params = new LayoutParams(0, LayoutParams.WRAP_CONTENT,1);
		params.gravity = Gravity.CENTER_VERTICAL;
		params.setMargins(ScreenMathUtils.dpToPx(5, context),0,margin,0);
		imageView.setLayoutParams(params);
		imageView.setImageResource(R.drawable.cancel);
		imageView.setVisibility(View.GONE);
		addView(imageView);

		currentView = this;


		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (editText != null) {
					editText.setText("");
				}
			}
		});
		
		editText.addTextChangedListener(new TextWatcher() {

			public void onTextChanged( CharSequence s, int start, int before, int count)
			{ 
				if(s.length() > 0) {
					if (imageView != null) {
						imageView.setVisibility(View.VISIBLE);
					}
				} else {
					if (imageView != null) {
						imageView.setVisibility(View.GONE);
					}
				}
			}

			public void beforeTextChanged( CharSequence s, int start, int count, int after)
			{}

			public void afterTextChanged( final Editable s)
			{

			}
		});
		
		editText.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					if (currentView != null) {
						currentView.setBackgroundResource(R.drawable.product_page_image_gallery_border);
						if (editText != null && !MyStringUtils.isNullOrEmpty(editText.getText().toString())) {
							imageView.setVisibility(View.VISIBLE);
						}
					}
				} else {
					if (currentView != null) {
						currentView.setBackgroundResource(R.drawable.product_page_image_gallery_empty_border);
						imageView.setVisibility(View.GONE);
					}
				}
			}
		});

	}
	
	public String getText() {
		if (editText != null) {
			return editText.getText().toString();
		}
		return "";
	}
	
	public void setText(String text) {
		if (editText != null) {
			editText.setText(text);
		}
	}

	public void setParams(String hint,int inputType,int imeOptions) {
		editText.setHint(hint);
		editText.setInputType(inputType);
		editText.setImeOptions(imeOptions);
	}
	
	public EditText getEditText() {
		return editText;
	}


}

