package com.zhongxun.zstshops.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShopActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		
		textView.setText("商铺");
		setContentView(textView);
	}

}
