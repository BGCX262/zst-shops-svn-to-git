package com.zhongxun.zstshops.view;

import com.zhongxun.zstshops.R;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 自定义Toast
 * @author Administrator
 *
 */
public class CustomToast {
	/**
	 * 透明背景的Toast
	 * @param context
	 */
	public static void TransBgToast(Context context,String msg){
		Toast toast = new Toast(context);
		View toastView = View.inflate(context, R.layout.a_toast, null);
		TextView message = (TextView) toastView.findViewById(R.id.message);
		message.setText(msg);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 100);
		toast.setView(toastView);
		toast.show();
		
	}
	/**
	 * 透明背景带图片的Toast
	 * @param context
	 */
	public static void TransBgToastWithImage(Context context,String msg,int resId){
		Toast toast = new Toast(context);
		View toastView = View.inflate(context, R.layout.b_toast, null);
		ImageView imageView = (ImageView) toastView.findViewById(R.id.image);
		imageView.setImageResource(resId);
		TextView message = (TextView) toastView.findViewById(R.id.message);
		message.setText(msg);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 100);
		toast.setView(toastView);
		toast.show();
		
	}
	
	


}
