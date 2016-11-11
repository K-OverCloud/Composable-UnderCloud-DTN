package com.netmng.vo;

import java.io.Serializable;

public class CdDw extends CdUp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6379532736229797860L;

	private	String	cd_dw_seq;
	private	String	cd_dw_nm;
	private String	cd_dw_val;
	
	public String getCd_dw_seq() {
		return cd_dw_seq;
	}
	public void setCd_dw_seq(String cd_dw_seq) {
		this.cd_dw_seq = cd_dw_seq;
	}
	public String getCd_dw_nm() {
		return cd_dw_nm;
	}
	public void setCd_dw_nm(String cd_dw_nm) {
		this.cd_dw_nm = cd_dw_nm;
	}
	public String getCd_dw_val() {
		return cd_dw_val;
	}
	public void setCd_dw_val(String cd_dw_val) {
		this.cd_dw_val = cd_dw_val;
	}	
	
}
