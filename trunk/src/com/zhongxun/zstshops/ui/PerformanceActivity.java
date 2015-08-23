package com.zhongxun.zstshops.ui;

import com.zhongxun.zstshops.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;
/**
 * 业绩
 * @author Administrator
 *
 */
public class PerformanceActivity extends BaseActivity implements OnClickListener {
	private Button order_list,member_list;
	private ViewFlipper viewFlipper;
	private static ProgressBar bar;
	private MemberListView memberListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.performance);
		setViews();
	}
	
	
	private void setViews(){
		order_list = (Button) findViewById(R.id.order_list);
		order_list.setSelected(true);
		
		member_list = (Button) findViewById(R.id.member_list);
		order_list.setOnClickListener(this);
		member_list.setOnClickListener(this);
		viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
		viewFlipper.addView(new OrderListView(this));
		bar = (ProgressBar) findViewById(R.id.loading_bar);
	}


	@Override
	public void onClick(View arg0) {
        switch (arg0.getId()) {
		case R.id.order_list:
			order_list.setSelected(true);
			member_list.setSelected(false);
			viewFlipper.setDisplayedChild(0);
			
			break;

		case R.id.member_list:
			order_list.setSelected(false);
			member_list.setSelected(true);
			if(memberListView==null)
			{    
				memberListView = new MemberListView(this);
				 viewFlipper.addView(memberListView);
			}
			viewFlipper.setDisplayedChild(1);
			
			break;
		}		
	}
	
	
	public static void showloading(){
		if(bar!=null){
			bar.setVisibility(View.VISIBLE);
		}
	}
	
	
	public static void hideloading(){
		if(bar!=null){
			bar.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	protected void onDestroy() {
		bar=null;
		super.onDestroy();
	}

}
