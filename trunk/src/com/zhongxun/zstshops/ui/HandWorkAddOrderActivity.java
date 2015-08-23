package com.zhongxun.zstshops.ui;

import java.util.HashMap;
import java.util.Map;

import com.zhongxun.zstshops.R;
import com.zhongxun.zstshops.http.ConnectCallBack;
import com.zhongxun.zstshops.http.HttpConnect;
import com.zhongxun.zstshops.http.Urls;
import com.zhongxun.zstshops.model.AddOrderRersponse;
import com.zhongxun.zstshops.model.OrderIdResponse;
import com.zhongxun.zstshops.utils.Constant;
import com.zhongxun.zstshops.utils.CyptoUtils;
import com.zhongxun.zstshops.utils.JsonUtils;
import com.zhongxun.zstshops.utils.MyLog;
import com.zhongxun.zstshops.view.CustomToast;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 手工添加订单
 * @author Administrator
 *
 */
public class HandWorkAddOrderActivity extends BaseActivity implements OnClickListener {
	private Button left_btn,right_btn;
	private TextView title_tv;
	
	private EditText order_id,order_price,order_number;
	
	private String orderId=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.handwork_addorder);
		title_tv = (TextView) findViewById(R.id.title_tv);
		title_tv.setText("订单");
		
		left_btn= (Button) findViewById(R.id.left_bt);
		left_btn.setGravity(Gravity.CENTER);
		right_btn= (Button) findViewById(R.id.right_bt);
		left_btn.setOnClickListener(this);
		right_btn.setOnClickListener(this);
		
		
		order_id = (EditText) findViewById(R.id.order_id);
		order_price = (EditText) findViewById(R.id.order_price);
		order_number = (EditText) findViewById(R.id.order_number);
		
		getOrderId();
	}
    
	
	
	@Override
	public void onClick(View arg0) 
	{
		switch (arg0.getId()) {
		case R.id.left_bt:
			finish();
			break;

		case R.id.right_bt:
			commitOrder();
			break;
		}
	}
	
	/**
	 * 获取订单ID
	 */
	private void getOrderId()
	{
		if(Constant.LOGINDATA!=null&&Constant.LOGINDATA.getShopID()!=0)
		{
			Map params = new HashMap();
			params.put("S", CyptoUtils.encode(Urls.IV, Constant.LOGINDATA.getShopID()+""));
			
			params.put("ShopID", Constant.LOGINDATA.getShopID()+"");
			
			HttpConnect connect = new HttpConnect();
			
			connect.setCallBack(new ConnectCallBack() {
				
				@Override
				public void onSuccess(String data) {
					MyLog.e("orderid", data+"@@@@@@@@@@@");
					stopDialog();
					
					if(!TextUtils.isEmpty(data)){
						OrderIdResponse res = JsonUtils.fromJson(data, OrderIdResponse.class);
						if(res.getStatus()!=1)
						{
							CustomToast.TransBgToast(getApplicationContext(), "获取订单编号失败，请重试");
						}else
						{
							order_id.setText(""+res.getOrderID());
							orderId=res.getOrderID();
						}
					}else
					{
						CustomToast.TransBgToast(getApplicationContext(), "获取订单编号失败，请重试");
					}
				}
				
				@Override
				public void onPrepare() {
					showDialog("请稍候");
				}
				
				@Override
				public void onFialure(String failMsg) {
					stopDialog();
					CustomToast.TransBgToast(getApplicationContext(), "获取订单编号失败，请重试");
					orderId=null;
					
				}
			});
			
			connect.post(params, Urls.URL_GET_ORDER_ID);
		}
		else
		{
			CustomToast.TransBgToast(getApplicationContext(), "获取订单编号失败，请重试");
		}
	}
	
	/**
	 * 提交订单
	 */
	
	private void commitOrder(){
      if(checkInput())
      {
			Map params = new HashMap();
			params.put("S", CyptoUtils.encode(Urls.IV, Constant.LOGINDATA.getShopID()+""));
			params.put("OrderID", orderId);
			params.put("ShopID", Constant.LOGINDATA.getShopID()+"");
			params.put("Price", order_price.getText().toString().trim()+"");
			params.put("UserID", order_number.getText().toString().trim()+"");
			
			HttpConnect connect = new HttpConnect();
			connect.setCallBack(new ConnectCallBack() {
				
				@Override
				public void onSuccess(String data) 
				{   
					MyLog.e("生成订单", data+"");
					stopDialog();
					if(!TextUtils.isEmpty(data)){
						AddOrderRersponse res=JsonUtils.fromJson(data, AddOrderRersponse.class);
						
						if(res.getStatus()==1)
						{
							CustomToast.TransBgToast(getApplicationContext(), "添加订单成功");
							finish();
						}
					}else
					{
						CustomToast.TransBgToast(getApplicationContext(), "添加订单失败");
					}
					
				}
				
				@Override
				public void onPrepare() 
				{
                   showDialog("正在添加订单");					
				}
				
				@Override
				public void onFialure(String failMsg) 
				{   
					stopDialog();
					CustomToast.TransBgToast(getApplicationContext(), "添加订单失败");
				}
			});
			
			connect.post(params, Urls.URL_ADD_ORDER);
    	  
    	  
    	  
      }

		
	}
	
	
	/**
	 * 验证输入
	 */
   private boolean  checkInput()
   {
	   if(TextUtils.isEmpty(order_id.getText().toString().trim())){
		   CustomToast.TransBgToast(getApplicationContext(), "订单编号没有生成");
		   return false;
	   }
	   
	   if(TextUtils.isEmpty(order_price.getText().toString().trim())){
		   CustomToast.TransBgToast(getApplicationContext(), "请输入订单价格");
		   return false;
	   }
	   
	   if(TextUtils.isEmpty(order_number.getText().toString().trim()))
	   {
		   CustomToast.TransBgToast(getApplicationContext(), "请输入会员号");
		   return false;
	   }
	   
	   return true;
   }
}
