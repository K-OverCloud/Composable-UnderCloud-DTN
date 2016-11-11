package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

public class GnprInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8453532561593494083L;

	private	String	seq;
	private	String	group_seq;
	private	String	gnpr_id;
	private	String	site_a_seq;
	private	String	site_a_router_ip;
	private	String	site_a_interface_ip;
	private	String	site_b_seq;
	private	String	site_b_router_ip;
	private	String	site_b_interface_ip;
	private	String	interface_type;
	private	String	use_yn;
	private	Date	createtime;
	private	Date	deltime;
	
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
	public String getGnpr_id() {
		return this.gnpr_id;
	}
	public void setGnpr_id(String gnpr_id) {
		this.gnpr_id = gnpr_id;
	}
	public String getSite_a_seq() {
		return this.site_a_seq;
	}
	public void setSite_a_seq(String site_a_seq) {
		this.site_a_seq = site_a_seq;
	}
	public String getSite_a_router_ip() {
		return this.site_a_router_ip;
	}
	public void setSite_a_router_ip(String site_a_router_ip) {
		this.site_a_router_ip = site_a_router_ip;
	}
	public String getSite_a_interface_ip() {
		return this.site_a_interface_ip;
	}
	public void setSite_a_interface_ip(String site_a_interface_ip) {
		this.site_a_interface_ip = site_a_interface_ip;
	}
	public String getSite_b_seq() {
		return this.site_b_seq;
	}
	public void setSite_b_seq(String site_b_seq) {
		this.site_b_seq = site_b_seq;
	}
	public String getSite_b_router_ip() {
		return this.site_b_router_ip;
	}
	public void setSite_b_router_ip(String site_b_router_ip) {
		this.site_b_router_ip = site_b_router_ip;
	}
	public String getSite_b_interface_ip() {
		return this.site_b_interface_ip;
	}
	public void setSite_b_interface_ip(String site_b_interface_ip) {
		this.site_b_interface_ip = site_b_interface_ip;
	}
	public String getInterface_type() {
		return this.interface_type;
	}
	public void setInterface_type(String interface_type) {
		this.interface_type = interface_type;
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
