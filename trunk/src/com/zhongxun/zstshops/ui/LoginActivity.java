package com.zhongxun.zstshops.ui;

import java.util.HashMap;
import java.util.Map;
import com.zhongxun.zstshops.R;
import com.zhongxun.zstshops.http.ConnectCallBack;
import com.zhongxun.zstshops.http.HttpConnect;
import com.zhongxun.zstshops.http.Urls;
import com.zhongxun.zstshops.model.LoginResponse;
import com.zhongxun.zstshops.utils.Constant;
import com.zhongxun.zstshops.utils.JsonUtils;
import com.zhongxun.zstshops.utils.MyLog;
import com.zhongxun.zstshops.view.CustomToast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity implements ConnectCallBack{
	
    private boolean isLogin=false;//是否正在登录，防止重复点击登录
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Button btn_login =(Button)findViewById(R.id.login_btn_register);
		final EditText login_et_user = (EditText) findViewById(R.id.login_et_user);
		final EditText login_et_pwd = (EditText) findViewById(R.id.login_et_pwd);
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isLogin)
				goLogin(login_et_user,login_et_pwd);
			}
		});
	}
	
	/**
	 * 检查网络
	 * @param act
	 * @return
	 */
	public boolean detectNet(Activity act) {
		ConnectivityManager manager = (ConnectivityManager) act.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}

		return true;
	}
	
	
	/**
	 * 验证登陆条件
	 * @param name
	 * @param pwd
	 * @return
	 */
	private boolean checkIsOk(EditText name,EditText pwd){
		String nameStr = name.getText().toString().trim();
		if(TextUtils.isEmpty(nameStr)){
			CustomToast.TransBgToastWithImage(this, "请输入用户名", R.drawable.warning);
			return false;
		}
		String pwdStr = pwd.getText().toString().trim();
		if(TextUtils.isEmpty(pwdStr)){
			CustomToast.TransBgToastWithImage(this, "请输入密码", R.drawable.warning);
			return false;
		}
		if(!detectNet(this)){
			CustomToast.TransBgToastWithImage(this, "未联网状态，请检查网络设置", R.drawable.warning);
			return false;
		}
		return true;
	}
	
	
	/**
	 * 登录
	 * @param name
	 * @param pwd
	 */
	private void goLogin(EditText name,EditText pwd){
		if(checkIsOk(name,pwd)){
			String nameStr = name.getText().toString().trim();
			String pwdStr = pwd.getText().toString().trim();
			
			Map params = new HashMap();
			params.put("UserName", nameStr);
			params.put("PassWord", pwdStr);
			
			HttpConnect connect = new HttpConnect();
			connect.setCallBack(this);
			connect.post(params, Urls.URL_LOGIN);
			
		} 
		
	}
	
	private void  handleLoginData(String data)
	{   
		MyLog.e("loginActivity", data+"");
		LoginResponse res = JsonUtils.fromJson(data, LoginResponse.class);
		if(res==null||res.getStatus()!=1)
		{
			CustomToast.TransBgToast(getApplicationContext(), "登录失败");
		}else
		{
			CustomToast.TransBgToast(getApplicationContext(), res.getMsg());
			//保存用户登录信息
			Constant.LOGINDATA =res.getData();
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
			
	}

	@Override
	public void onSuccess(String data) 
	{
		isLogin = false;
		stopDialog();
		handleLoginData(data);
	}

	@Override
	public void onFialure(String failMsg) {
		isLogin = false;
		CustomToast.TransBgToast(getApplicationContext(), "登录失败-"+failMsg);
		stopDialog();
	}

	@Override
	public void onPrepare() {
		isLogin = true;
		showDialog("正在登录");
	}

}
