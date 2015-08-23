package com.zhongxun.zstshops.model;

public class UserInfoResponse {
   //{"Status":1,"Msg":"","UserName":"test004","TrueName":"test004","UserID":100006,"IsMember":true}
	
	private int Status;
	private String Msg;
	private String UserName;
	private String TrueName;
	private int UserID;
	private boolean IsMember;
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
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getTrueName() {
		return TrueName;
	}
	public void setTrueName(String trueName) {
		TrueName = trueName;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public boolean isIsMember() {
		return IsMember;
	}
	public void setIsMember(boolean isMember) {
		IsMember = isMember;
	}
	
	
}
