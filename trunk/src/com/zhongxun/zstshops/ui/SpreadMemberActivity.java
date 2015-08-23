package com.zhongxun.zstshops.ui;

import com.zhongxun.zstshops.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * 扫描推广会员
 * @author Administrator
 *
 */
public class SpreadMemberActivity extends BaseActivity implements OnClickListener {
	private Button left_btn,right_btn;
	private TextView title_tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spreadmember);
		setViews();
		
		
	}

	private void setViews() {
		title_tv = (TextView) findViewById(R.id.title_tv);
		title_tv.setText("添加会员");
		
		left_btn= (Button) findViewById(R.id.left_bt);
		left_btn.setText("取消");
		right_btn= (Button) findViewById(R.id.right_bt);
		right_btn.setText("加入");
		left_btn.setOnClickListener(this);
		right_btn.setOnClickListener(this);		
	}

	@Override
	public void onClick(View arg0) 
	{
		
	}

}
