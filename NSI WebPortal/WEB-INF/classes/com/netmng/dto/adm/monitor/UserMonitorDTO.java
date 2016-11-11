package com.netmng.dto.adm.monitor;

import java.io.Serializable;

public class UserMonitorDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 884897978984025110L;
	
	private String 	seq;
	private String 	id;
	private String 	name;
	private String	cnt;
	
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCnt() {
		return this.cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
}
