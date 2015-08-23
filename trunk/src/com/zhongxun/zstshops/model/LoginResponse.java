package com.zhongxun.zstshops.model;

public class LoginResponse {

	
	
	private int Status;
	private String Msg;
	private LoginData Data;
	
	
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
    
	public String getMsg() {
		return Msg;
	}
	public void setMsg(String msg) {
		Msg = msg;
	}
	public LoginData getData() {
		return Data;
	}
	public void setData(LoginData data) {
		Data = data;
	}
	
	
	
}
