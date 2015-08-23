package com.zhongxun.zstshops.ui;

import com.zhongxun.zstshops.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
/**
 * 会员列表
 * @author Administrator
 *
 */
public class MemberListView extends LinearLayout {

	public MemberListView(Context context) {
		super(context);
		this.mcontext =context;
         init(context);
         
	}
	


	public MemberListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mcontext =context;
		init(context);

	}
	
	private Context mcontext;
   
	
	private void init(Context context) 
	{
	  	View view  = View.inflate(context, R.layout.memberlist, this);
	}
}
