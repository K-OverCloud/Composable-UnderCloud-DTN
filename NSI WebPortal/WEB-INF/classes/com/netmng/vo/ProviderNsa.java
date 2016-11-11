package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

public class ProviderNsa implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2920823847177486508L;

	private String 	nsa_addr;
	private String 	nsa_nm;
	private String	admin_contact;
	private String 	endpoint_addr;
	private String	ref_url;
	
	public String getNsa_addr() {
		return nsa_addr;
	}
	public void setNsa_addr(String nsa_addr) {
		this.nsa_addr = nsa_addr;
	}
	public String getNsa_nm() {
		return nsa_nm;
	}
	public void setNsa_nm(String nsa_nm) {
		this.nsa_nm = nsa_nm;
	}
	public String getAdmin_contact() {
		return admin_contact;
	}
	public void setAdmin_contact(String admin_contact) {
		this.admin_contact = admin_contact;
	}
	public String getEndpoint_addr() {
		return endpoint_addr;
	}
	public void setEndpoint_addr(String endpoint_addr) {
		this.endpoint_addr = endpoint_addr;
	}
	public String getRef_url() {
		return ref_url;
		//return ref_url.replaceAll("\n", "<br />");
	}
	public void setRef_url(String ref_url) {
		this.ref_url = ref_url;
	}
	
	
}
