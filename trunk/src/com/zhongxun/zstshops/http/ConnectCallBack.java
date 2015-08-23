package com.zhongxun.zstshops.http;

public interface ConnectCallBack {
	/**
	 * 三个回调
	 */
	void onPrepare();//http连接开始前
	void onSuccess(String data);//http获取数据成功
	void onFialure(String failMsg);//http连接获取数据失败

}
