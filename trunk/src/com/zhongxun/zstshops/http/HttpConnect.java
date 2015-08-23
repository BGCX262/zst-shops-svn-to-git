package com.zhongxun.zstshops.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import com.zhongxun.zstshops.utils.StringUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 访问网络的封装
 * @author Administrator
 *
 */
public class HttpConnect {
    
    private ConnectCallBack callBack;
    
    
    public ConnectCallBack getCallBack() {
		return callBack;
	}


	public void setCallBack(ConnectCallBack callBack) {
		this.callBack = callBack;
	}


	public void post(Map params,String url){
		
		AjaxParams ajaxParams = new AjaxParams();
        setParams(ajaxParams, params);
        FinalHttp fh = new FinalHttp();
        fh.post(url, ajaxParams, new AjaxCallBack<String>() 
        {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				if(callBack!=null)
					callBack.onFialure(strMsg);
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onStart() {
				if(callBack!=null)
					callBack.onPrepare();
				super.onStart();
			}

			@Override
			public void onSuccess(String t) {
				if(callBack!=null)
					callBack.onSuccess(t);
				super.onSuccess(t);
			}
        	
        	
		});
    	
    }
	
	
	private void setParams(AjaxParams ajaxParams,Map params){
		Iterator iter = params.entrySet().iterator();
        while(iter.hasNext()){
        	Map.Entry entry = (Map.Entry) iter.next();
        	
        	if(entry.getValue() instanceof String)
        	{   
        		String value = (String)entry.getValue();
        		if(StringUtils.isChinese(value))
        		{
        			ajaxParams.put((String)entry.getKey(), URLEncoder.encode(value));
        		}
        		else
        		{
            		ajaxParams.put((String)entry.getKey(), value);
        		}

        	}
        	else if(entry.getValue() instanceof File)
        	{
        		try 
        		{
					ajaxParams.put((String)entry.getKey(), (File)entry.getValue());
				} 
        		catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
        	}
        	else if(entry.getValue() instanceof InputStream)
        	{
        		ajaxParams.put((String)entry.getKey(), (InputStream)entry.getValue());
        	}
        }
	
	}
}
