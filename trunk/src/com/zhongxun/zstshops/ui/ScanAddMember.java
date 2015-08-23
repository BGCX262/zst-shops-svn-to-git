package com.zhongxun.zstshops.ui;

import java.util.HashMap;
import java.util.Map;

import com.zhongxun.zstshops.R;
import com.zhongxun.zstshops.http.ConnectCallBack;
import com.zhongxun.zstshops.http.HttpConnect;
import com.zhongxun.zstshops.http.Urls;
import com.zhongxun.zstshops.model.ScanAddMemberResponse;
import com.zhongxun.zstshops.model.UserInfoResponse;
import com.zhongxun.zstshops.utils.Constant;
import com.zhongxun.zstshops.utils.CyptoUtils;
import com.zhongxun.zstshops.utils.JsonUtils;
import com.zhongxun.zstshops.utils.MyLog;
import com.zhongxun.zstshops.view.CustomToast;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 扫一扫添加会员
 * @author Administrator
 *
 */
public class ScanAddMember extends BaseActivity implements OnClickListener {
	private EditText member_name,member_id;
	private Button left_btn,right_btn;
	private TextView title_tv;
	
	
	private String memberId=null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanaddmember);
		
		memberId = getIntent().getStringExtra("member_id");
		setViews();
		getUserInfo();
	}
	
	
	private void setViews(){
		title_tv = (TextView) findViewById(R.id.title_tv);
		title_tv.setText("添加会员");
		
		left_btn= (Button) findViewById(R.id.left_bt);
		left_btn.setGravity(Gravity.CENTER);
		left_btn.setText("返回");
		right_btn= (Button) findViewById(R.id.right_bt);
		right_btn.setText("添加");
		
		left_btn.setOnClickListener(this);
		right_btn.setOnClickListener(this);
		
		member_name = (EditText) findViewById(R.id.member_name);
		member_id = (EditText) findViewById(R.id.member_id);
		
		if(member_id!=null)
		{
			member_id.setText(memberId);
			getUserInfo();
		}
		else
		{
			CustomToast.TransBgToast(getApplicationContext(), "获取会员号失败");
		}

	}


	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.left_bt:
			finish();
			break;

        
		case R.id.right_bt:
			addMember();
			break;		
			
		}
		
	}
	
	/**
	 * 根据用户id查询用户信息
	 */
	private void getUserInfo()
	{
		Map params = new HashMap();
		params.put("s", CyptoUtils.encode(Urls.IV, Constant.LOGINDATA.getShopID()+""));
		params.put("username", memberId);
		
		HttpConnect connect = new HttpConnect();
		connect.setCallBack(new ConnectCallBack() {
			
			@Override
			public void onSuccess(String data) {
                 MyLog.e("查询用户名--", data+"");
                 stopDialog();
                 if(!TextUtils.isEmpty(data)){
                	 
                	 UserInfoResponse res = JsonUtils.fromJson(data, UserInfoResponse.class);
                	 if(res.getStatus()==1)
                	 {
                		 member_name.setText(res.getUserName());
                	 }else
                	 {
                		 CustomToast.TransBgToast(getApplicationContext(), "获取用户名失败");
                	 }
                	 
                 }else
                 {
                	 CustomToast.TransBgToast(getApplicationContext(), "获取用户名失败");
                 }
				
			}
			
			@Override
			public void onPrepare() {
               //showDialog("请稍后");				
			}
			
			@Override
			public void onFialure(String failMsg) {
				stopDialog();
				
			}
		});
		connect.post(params, Urls.URL_GER_USER_INFO );
	}
	
	/**
	 * 添加会员
	 */
	private void addMember()
	{   
		String strMemberID = member_id.getText().toString().trim();
		String strMemberName = member_name.getText().toString().trim();
		if(TextUtils.isEmpty(strMemberID))
		{
			CustomToast.TransBgToast(getApplicationContext(), "请检查会员号");
			return;
		}
		if(TextUtils.isEmpty(strMemberName))
		{   
			CustomToast.TransBgToast(getApplicationContext(), "请检查会员名称");
			return;
		}
		
		Map params = new HashMap();
		params.put("s", CyptoUtils.encode(Urls.IV, Constant.LOGINDATA.getShopID()+""));
		params.put("UserID", memberId);
		
		HttpConnect connect = new HttpConnect();
		connect.setCallBack(new ConnectCallBack() {
			
			@Override
			public void onSuccess(String data) {
                 MyLog.e("添加会员--", data+"");
                 stopDialog();
                 if(!TextUtils.isEmpty(data)){
                	 
                	 ScanAddMemberResponse res = JsonUtils.fromJson(data, ScanAddMemberResponse.class);
                	 if(res.getStatus()==1)
                	 {
                		 CustomToast.TransBgToast(getApplicationContext(), res.getMsg());
                	 }else
                	 {
                		 CustomToast.TransBgToast(getApplicationContext(), res.getMsg());
                	 }
                	 
                 }else
                 {
                	 CustomToast.TransBgToast(getApplicationContext(), "添加失败");
                 }
				
			}
			
			@Override
			public void onPrepare() {
               showDialog("请稍后");				
			}
			
			@Override
			public void onFialure(String failMsg) {
				stopDialog();
				
			}
		});
		connect.post(params, Urls.URL_SCAN_ADD_MEMBER );
	}

}
