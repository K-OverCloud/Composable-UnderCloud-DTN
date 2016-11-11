package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;
import com.netmng.vo.StpNetwork;

public class StpLocal extends StpNetwork implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2920823847177486508L;

	private String 	local_id;
	private String 	local_nm;
	private String	local_fault_yn;
	
	public String getLocal_id() {
		return local_id;
	}
	public void setLocal_id(String local_id) {
		this.local_id = local_id;
	}
	public String getLocal_nm() {
		return local_nm;
	}
	public void setLocal_nm(String local_nm) {
		this.local_nm = local_nm;
	}
	public String getLocal_fault_yn() {
		return local_fault_yn;
	}
	public void setLocal_fault_yn(String local_fault_yn) {
		this.local_fault_yn = local_fault_yn;
	}
	
}
