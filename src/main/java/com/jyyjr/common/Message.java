package com.jyyjr.common;

import java.io.Serializable;

/**
 * @Description:需要返回的数据
 * @author:tuizhi-cai
 * @time:2018年1月30日 上午10:47:01
 */
public class Message<T> implements Serializable{
	
	private int isSuccess ; //0失败1成功
	
	public static final int SUCCESS = 1;
	
	public static final int FAIL = 0;
	
	private String message; //返回消息
	
	private T data; //返回的具体数据
	
	public int getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setData(T data) {
		this.data=data;
	}
	public T getData() {
		return data;
	}
	
	public   Message(int isSuccess,String message,T data) {
		this.isSuccess= isSuccess;
		this.message = message;
		this.data = data;
	}
	
	public Message(int isSuccess,String message){
		this.isSuccess=isSuccess;
		this.message=message;
	}
}
