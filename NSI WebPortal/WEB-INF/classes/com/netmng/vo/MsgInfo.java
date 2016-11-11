package com.netmng.vo;

import java.io.Serializable;

public class MsgInfo implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7897288971010792446L;
	
	private boolean result;
	private String 	msg;
	private String 	code;
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
