package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

public class RouterType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7619662696995602138L;
	
	private String 	seq;
	private String	name;
	private String 	use_yn;
	private Date 	createtime;
	private Date	deltime;
	
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUse_yn() {
		return this.use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public Date getCreatetime() {
		return this.createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getDeltime() {
		return this.deltime;
	}
	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}
	
}
