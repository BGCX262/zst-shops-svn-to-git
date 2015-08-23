package com.zhongxun.zstshops.utils;
import android.util.Log;
public class MyLog {
	
	private static final boolean  isDebug  = true;
	
	public static void i(String tag,String msg){
		if(isDebug)
          Log.i(tag, msg);
	}
	
	
	public static void e(String tag,String msg){
		if(isDebug)
          Log.e(tag, msg);
	}
	
	public static void v(String tag,String msg){
		if(isDebug)
          Log.v(tag, msg);
	}

}
