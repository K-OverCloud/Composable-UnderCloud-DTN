package com.netmng.param.adm;

import java.io.Serializable;

import com.netmng.com.validate.ArrayStrValid;

public class InterfaceInfoDTOList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 197262388455660237L;
	
	public static interface AdmTopologyInsert {}
	
	@ArrayStrValid(regexp=".{1,}", message="com.netmng.param.adm.InterfaceInfoDTOList.site_name")
	private String[] site_name;
	@ArrayStrValid(regexp=".{1,}", message="com.netmng.param.adm.InterfaceInfoDTOList.urn")
	private String[] urn;
	@ArrayStrValid(regexp="(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])", 
			message="com.netmng.param.adm.InterfaceInfoDTOList.ip")
	private String[] ip;
	@ArrayStrValid(regexp="[0-9]|^[1-9]\\d{1,}", message="com.netmng.param.adm.InterfaceInfoDTOList.router_type_seq")
	private String[] router_type_seq;
	@ArrayStrValid(regexp="(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])", 
			message="com.netmng.param.adm.InterfaceInfoDTOList.primary_ip")
	private String[] primary_ip;
	@ArrayStrValid(regexp="[0-9]|^[1-9]\\d{1,}", message="com.netmng.param.adm.InterfaceInfoDTOList.primary_bw")
	private String[] primary_bw;
	@ArrayStrValid(regexp="(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])", 
			message="com.netmng.param.adm.InterfaceInfoDTOList.secondary_ip")
	private String[] secondary_ip;
	@ArrayStrValid(regexp="[0-9]|^[1-9]\\d{1,}", message="com.netmng.param.adm.InterfaceInfoDTOList.secondary_bw")
	private String[] secondary_bw;
	@ArrayStrValid(regexp="(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])", 
			message="com.netmng.param.adm.InterfaceInfoDTOList.link_primary_ip")
	private String[] link_primary_ip;
	@ArrayStrValid(regexp="(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])", 
			message="com.netmng.param.adm.InterfaceInfoDTOList.link_secondary_ip")
	private String[] link_secondary_ip;
	
	public String[] getSite_name() {
		return this.site_name;
	}
	public void setSite_name(String[] site_name) {
		this.site_name = site_name;
	}
	public String[] getUrn() {
		return this.urn;
	}
	public void setUrn(String[] urn) {
		this.urn = urn;
	}
	public String[] getIp() {
		return this.ip;
	}
	public void setIp(String[] ip) {
		this.ip = ip;
	}
	public String[] getRouter_type_seq() {
		return this.router_type_seq;
	}
	public void setRouter_type_seq(String[] router_type_seq) {
		this.router_type_seq = router_type_seq;
	}
	public String[] getPrimary_ip() {
		return this.primary_ip;
	}
	public void setPrimary_ip(String[] primary_ip) {
		this.primary_ip = primary_ip;
	}
	public String[] getPrimary_bw() {
		return this.primary_bw;
	}
	public void setPrimary_bw(String[] primary_bw) {
		this.primary_bw = primary_bw;
	}
	public String[] getSecondary_ip() {
		return this.secondary_ip;
	}
	public void setSecondary_ip(String[] secondary_ip) {
		this.secondary_ip = secondary_ip;
	}
	public String[] getSecondary_bw() {
		return this.secondary_bw;
	}
	public void setSecondary_bw(String[] secondary_bw) {
		this.secondary_bw = secondary_bw;
	}
	public String[] getLink_primary_ip() {
		return this.link_primary_ip;
	}
	public void setLink_primary_ip(String[] link_primary_ip) {
		this.link_primary_ip = link_primary_ip;
	}
	public String[] getLink_secondary_ip() {
		return this.link_secondary_ip;
	}
	public void setLink_secondary_ip(String[] link_secondary_ip) {
		this.link_secondary_ip = link_secondary_ip;
	}
}
