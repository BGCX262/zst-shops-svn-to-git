package com.zhongxun.zstshops.ui;

import com.zhongxun.zstshops.R;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	public TabHost tabhost;
	public static final String TAB_HOME = "0";
	public static final String TAB_PERFORMANCE = "1";
	public static final String TAB_PUBLISH = "2";
	public static final String TAB_SHOP = "3";
	public RadioGroup radioGroup;
    private RadioButton radioButton0,radioButton1,radioButton2,radioButton3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// CustomProgressDialog.showDialog(this, "AAAA");
		tabhost = this.getTabHost();
		setTab();
	}

	private void setTab() {
		TabSpec ts1 = tabhost.newTabSpec(TAB_HOME).setIndicator(TAB_HOME);
		ts1.setContent(new Intent(MainActivity.this, HomeActivity.class));
		tabhost.addTab(ts1);

		TabSpec ts2 = tabhost.newTabSpec(TAB_PERFORMANCE).setIndicator(
				TAB_PERFORMANCE);
		ts2.setContent(new Intent(MainActivity.this, PerformanceActivity.class));
		tabhost.addTab(ts2);

		TabSpec ts3 = tabhost.newTabSpec(TAB_PUBLISH).setIndicator(TAB_PUBLISH);
		ts3.setContent(new Intent(MainActivity.this, PublishActivity.class));
		tabhost.addTab(ts3);

		TabSpec ts4 = tabhost.newTabSpec(TAB_SHOP).setIndicator(TAB_SHOP);
		ts4.setContent(new Intent(MainActivity.this, ShopActivity.class));
		tabhost.addTab(ts4);

		this.radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioButton0 = (RadioButton) radioGroup.findViewById(R.id.radio_button0);
		radioButton1 = (RadioButton) radioGroup.findViewById(R.id.radio_button1);
		radioButton2 = (RadioButton) radioGroup.findViewById(R.id.radio_button2);
		radioButton3 = (RadioButton) radioGroup.findViewById(R.id.radio_button3);
		
		radioButton0.setChecked(true);
		tabhost.setCurrentTabByTag(TAB_HOME); // 默认首页
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.radio_button0:
					tabhost.setCurrentTabByTag(TAB_HOME);
					break;
				case R.id.radio_button1:
					tabhost.setCurrentTabByTag(TAB_PERFORMANCE);
					break;
				case R.id.radio_button2:
					tabhost.setCurrentTabByTag(TAB_PUBLISH);
					break;
				case R.id.radio_button3:
					tabhost.setCurrentTabByTag(TAB_SHOP);
					break;

				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

	private GestureDetector detector = new GestureDetector(
			new GestureDetector.SimpleOnGestureListener() {

				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2,
						float velocityX, float velocityY) {
					if ((e2.getRawX() - e1.getRawX()) > 150) {
						showPre();
						return true;
					}

					if ((e1.getRawX() - e2.getRawX()) > 150) {
						showNext();
						return true;
					}
					return super.onFling(e1, e2, velocityX, velocityY);
				}

			});

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	/**
	 * 当前页面索引
	 */
	int i = 0;

	/**
	 * 显示下一个页面
	 */
	protected void showNext() {
		// 三元表达式控制3个页面的循环.
		tabhost.setCurrentTab(i=(i == 3 ? i = 0 : ++i));
        setCheck(i);
	}

	/**
	 * 显示前一个页面
	 */
	protected void showPre() {
     
      tabhost.setCurrentTab(i=(i==0?i= 3 : --i));
      setCheck(i);
	}
	
	
	private void setCheck(int index){
		switch (index) {
		case 0:
			radioButton0.setChecked(true);
			break;
        
		case 1:
			radioButton1.setChecked(true);
			break; 		
		
		case 2:
			radioButton2.setChecked(true);
			break; 
		
		case 3:
			radioButton3.setChecked(true);
			break; 
			
		}
	}

}
