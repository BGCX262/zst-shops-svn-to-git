package com.zhongxun.zstshops.http;

import java.io.File;
//zst.zxhlfb.net
public class Urls {
	public static final String IV = "ZXHLShop";
	public static final String HTTP = "http://";
	public static final String HOST = "wanxun.vicp.cc:8088";//wanxun.vicp.cc:8088
	public static final String URL_SPLITTER = File.separator;
	public static final String URL_API_HOST = HTTP + HOST + URL_SPLITTER;
	public static final String URL_API_IP = HOST + URL_SPLITTER;
	public static final String PAGE_SIZE = "10";
	//登录
	public static final String URL_LOGIN = URL_API_HOST + "api/Account/ShopLogin";
	//商铺基本信息
	public static final String URL_SHOP_BASE_INFO = URL_API_HOST + "api/shop/GetShopsUserBasicBySID";
	
	//获取订单号
	public static final String URL_GET_ORDER_ID = URL_API_HOST + "api/shop/GetOrderIDByShopID";
	
	//添加订单
	public static final String URL_ADD_ORDER = URL_API_HOST + "api/shop/AddOrder";
	
	//根据ID查找会员信息
	public static final String URL_GER_USER_INFO = URL_API_HOST + "api/shop/GetUserByUserName";
	
	//扫描添加会员
	public static final String URL_SCAN_ADD_MEMBER = URL_API_HOST + "api/shop/GetUserByShop";
	
	//订单列表
	public static final String URL_QUERY_ORDER_LIST = URL_API_HOST + "api/shop/GetOrderListByPara";
	
    //订单列表价格
	public static final String URL_QUERY_ORDER_LIST_PRICE = URL_API_HOST + "api/shop/GetOrderListPrcieNumByPara";

    
}
