package com.zhongxun.zstshops.ui;


import net.tsz.afinal.FinalBitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.Contents;
import com.google.zxing.client.android.Intents;
import com.zhongxun.zstshops.R;
import com.zhongxun.zstshops.http.Urls;
import com.zhongxun.zstshops.utils.Constant;
import com.zhongxun.zstshops.utils.MyLog;
import com.zhongxun.zstshops.view.CustomToast;
import com.google.zxing.client.android.encode.QRCodeEncoder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends BaseActivity implements OnClickListener {
	private ImageView shop_logo,qrcode;
	private TextView shop_name,shop_level;
	private static final String USE_VCARD_KEY = "USE_VCARD";
	private QRCodeEncoder qrCodeEncoder;
	private Button handwork_add,scan_add_order;
	private Button spread_member,scan_add_member;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		setView();
		loadShopData();
		generateQRCODE();
	}
	
	
	private void setView(){
		shop_logo = (ImageView) findViewById(R.id.shop_logo);
		qrcode = (ImageView) findViewById(R.id.qrcode);
		shop_name = (TextView) findViewById(R.id.shop_name);
		shop_level = (TextView) findViewById(R.id.shop_level);
		
		handwork_add = (Button) findViewById(R.id.handwork_add);
		scan_add_order = (Button) findViewById(R.id.scan_add_order);
		spread_member = (Button) findViewById(R.id.spread_member);
		scan_add_member = (Button) findViewById(R.id.scan_add_member);
		
		handwork_add.setOnClickListener(this);
		scan_add_order.setOnClickListener(this);
		spread_member.setOnClickListener(this);
		scan_add_member.setOnClickListener(this);
	}
	
	/**
	 * 加载商铺信息
	 */
	private void loadShopData(){
		if(Constant.LOGINDATA!=null)
		{
			shop_name.setText(Constant.LOGINDATA.getShopName());
			shop_level.setText(Constant.LOGINDATA.getLevelID()+"级");
			
			//载入商铺头像
			FinalBitmap fb = FinalBitmap.create(this);
			
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{
				fb.configDiskCachePath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/zst_shop");//设置硬盘缓存
				MyLog.e(this.getClass().getSimpleName(), "设置硬盘缓存");
			}

			fb.display(shop_logo, Urls.HTTP+Urls.HOST+Constant.LOGINDATA.getShopLogo());
			
			
		}

	}
	
	/**
	 * 生成二维码
	 */
	private void generateQRCODE(){
		showDialog("加载中...");
		if(Constant.LOGINDATA==null||Constant.LOGINDATA.getShopID()==0){
			return;
		}
		String text = Urls.HTTP+Urls.HOST+Urls.URL_SPLITTER+Constant.LOGINDATA.getShopID();
		MyLog.e(this.getClass().getSimpleName(), ""+text);
		
	    Intent intent = new Intent(Intents.Encode.ACTION);
	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	    intent.putExtra(Intents.Encode.TYPE, Contents.Type.TEXT);
	    intent.putExtra(Intents.Encode.DATA, text);
	    intent.putExtra(Intents.Encode.FORMAT, BarcodeFormat.QR_CODE.toString());
	    
	    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
	    Display display = manager.getDefaultDisplay();
	    int width = display.getWidth();
	    int height = display.getHeight();
	    int smallerDimension = width < height ? width : height;
	    smallerDimension = smallerDimension * 7 / 8;
	    
	    try {
	        boolean useVCard = intent.getBooleanExtra(USE_VCARD_KEY, false);
	        qrCodeEncoder = new QRCodeEncoder(this, intent, smallerDimension, useVCard);
	        Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
	        if (bitmap == null) {
	        	
	          MyLog.e(this.getClass().getSimpleName(), "Could not encode barcode");
	          CustomToast.TransBgToast(getApplicationContext(), "生成二维码失败");
	          qrCodeEncoder = null;
	          return;
	        }


	        qrcode.setImageBitmap(bitmap);

	       
	      } catch (WriterException e) 
	      {
	        Log.w(this.getClass().getSimpleName(), "Could not encode barcode", e);
	        CustomToast.TransBgToast(getApplicationContext(), "生成二维码失败");
	        qrCodeEncoder = null;
	      }
	    
	    stopDialog();
	}


	@Override
	public void onClick(View arg0) {
        Intent intent=null;
		switch (arg0.getId()) 
		{
		
		case R.id.handwork_add:
			intent = new Intent(this, HandWorkAddOrderActivity.class);
			startActivity(intent);
			break;
		
		case R.id.scan_add_order:
		    intent = new Intent(this,CaptureActivity.class);
		    intent.putExtra("is_add_order", true);
		    startActivity(intent);
			break;
		
		case R.id.scan_add_member:
		    intent = new Intent(this,CaptureActivity.class);
		    intent.putExtra("is_add_order", false);
		    startActivity(intent);
			break;
		
		case R.id.spread_member:
			//短信推广
			Uri smsToUri = Uri.parse("smsto:");
			Intent message = new Intent(Intent.ACTION_SENDTO, smsToUri);
			String content="介绍给您一款很不错的软件-【我慧掌上通】客户端:http://zst.zxhlfb.net.";
			message.putExtra("sms_body", content);
			startActivity(message);
			break;


		}
		
	}



}
