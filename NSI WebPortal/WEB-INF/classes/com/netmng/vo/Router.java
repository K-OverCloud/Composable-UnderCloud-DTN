package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

public class Router implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2920823847177486508L;

	private String 	seq;
	private String 	group_seq;
	private String	site_name;
	private String 	kind;
	private String 	urn;
	private String 	ip;
	private String	router_type_seq;
	private String 	use_yn;
	private Date	createtime;
	private Date	deltime;
	private boolean	fault_yn;
	
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getGroup_seq() {
		return this.group_seq;
	}
	public void setGroup_seq(String group_seq) {
		this.group_seq = group_seq;
	}
	public String getSite_name() {
		return this.site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getKind() {
		return this.kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getUrn() {
		return this.urn;
	}
	public void setUrn(String urn) {
		this.urn = urn;
	}
	public String getIp() {
		return this.ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRouter_type_seq() {
		return this.router_type_seq;
	}
	public void setRouter_type_seq(String router_type_seq) {
		this.router_type_seq = router_type_seq;
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
	public boolean getFault_yn() {
		return this.fault_yn;
	}
	public void setFault_yn(boolean fault_yn) {
		this.fault_yn = fault_yn;
	}
}
