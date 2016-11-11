package com.netmng.vo;

import java.io.Serializable;

public class CdUp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6379532736229797860L;

	private	String	cd_up_seq;
	private	String	cd_up_nm;
	
	public String getCd_up_seq() {
		return cd_up_seq;
	}
	public void setCd_up_seq(String cd_up_seq) {
		this.cd_up_seq = cd_up_seq;
	}
	public String getCd_up_nm() {
		return cd_up_nm;
	}
	public void setCd_up_nm(String cd_up_nm) {
		this.cd_up_nm = cd_up_nm;
	}
}
