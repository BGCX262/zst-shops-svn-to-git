package com.zhongxun.zstshops.ui;


import com.zhongxun.zstshops.utils.AppManager;
import com.zhongxun.zstshops.view.CustomProgressDialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

public class BaseActivity extends Activity {
    public Dialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
	}
	
	
	public void showDialog(String msg){
		progressDialog = CustomProgressDialog.createDialog(this, msg);
		progressDialog.show();
	}
	
	
	
	public  void stopDialog(){
		if(progressDialog!=null&&progressDialog.isShowing())
		{
			progressDialog.dismiss();
		}
	}
	
	@Override
	protected void onDestroy() {
		stopDialog();
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	}

}
