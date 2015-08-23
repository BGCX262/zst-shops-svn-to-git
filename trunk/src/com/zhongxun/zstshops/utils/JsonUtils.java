package com.zhongxun.zstshops.utils;

import com.google.gson.Gson;

public class JsonUtils
{
	private static Gson gson = new Gson();
	
	public static <T> T fromJson(String json,Class<T> classOfT){
		return gson.fromJson(json, classOfT);
	}

}
