package com.netmng.vo.twt;

import java.util.Date;

public class Config {
	
	private String con_id;
	private String con_val;
	private String con_comment;
	private Date con_time;
	
	public String getCon_id() {
		return this.con_id;
	}
	public void setCon_id(String con_id) {
		this.con_id = con_id;
	}
	public String getCon_val() {
		return this.con_val;
	}
	public void setCon_val(String con_val) {
		this.con_val = con_val;
	}
	public String getCon_comment() {
		return this.con_comment;
	}
	public void setCon_comment(String con_comment) {
		this.con_comment = con_comment;
	}
	public Date getCon_time() {
		return this.con_time;
	}
	public void setCon_time(Date con_time) {
		this.con_time = con_time;
	}
	
}
