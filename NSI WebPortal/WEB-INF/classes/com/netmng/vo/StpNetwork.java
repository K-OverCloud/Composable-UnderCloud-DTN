package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

public class StpNetwork implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2920823847177486508L;

	private String 	network_id;
	private String 	network_nm;
	private String	network_fault_yn;
	private String	nsa_addr;
	
	
	public String getNetwork_id() {
		return network_id;
	}
	public void setNetwork_id(String network_id) {
		this.network_id = network_id;
	}
	public String getNetwork_nm() {
		return network_nm;
	}
	public void setNetwork_nm(String network_nm) {
		this.network_nm = network_nm;
	}
	public String getNetwork_fault_yn() {
		return network_fault_yn;
	}
	public void setNetwork_fault_yn(String network_fault_yn) {
		this.network_fault_yn = network_fault_yn;
	}
	public String getNsa_addr() {
		return nsa_addr;
	}
	public void setNsa_addr(String nsa_addr) {
		this.nsa_addr = nsa_addr;
	}
	
}
