package com.zhongxun.zstshops.model;

import java.util.List;

public class OrderListResponse {

  
	private int Status;
	private String Msg;
	private int Total;
	private List<OrderInfo> Rows;
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
	public int getTotal() {
		return Total;
	}
	public void setTotal(int total) {
		Total = total;
	}
	
	
	public List<OrderInfo> getRows() {
		return Rows;
	}
	public void setRows(List<OrderInfo> rows) {
		Rows = rows;
	}
    
	
	
	
	
}
