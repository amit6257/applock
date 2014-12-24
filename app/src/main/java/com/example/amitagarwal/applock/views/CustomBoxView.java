package com.example.amitagarwal.applock.views;

import android.content.Context;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.TextView;

import com.example.amitagarwal.applock.utils.MathUtils;

public class CustomBoxView extends TextView{

	private Context context;
	private final static double textSizePercent = 0.3; 
	
	public CustomBoxView(Context context) {
		super(context);
		this.context = context; 
	}

	public CustomBoxView(Context context,int boxNumber) {
		super(context);
		this.context = context;
		setText(boxNumber + 1 + "");
		setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		setTextSize((int)(MathUtils.getBoxWidth(context)*textSizePercent));
		setLayoutParams(new AbsListView.LayoutParams((int)MathUtils.getBoxWidth(context),(int)MathUtils.getBoxHeight(context)));
	}
}
