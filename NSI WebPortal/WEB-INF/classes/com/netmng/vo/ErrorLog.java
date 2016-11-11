package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

public class ErrorLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6379532736229797860L;

	private	String	seq;
	private	String	url;
	private	String	name;
	private	String	log_message;
	private	Date	createtime;
	
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLog_message() {
		return this.log_message;
	}
	public void setLog_message(String log_message) {
		this.log_message = log_message;
	}
	public Date getCreatetime() {
		return this.createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
