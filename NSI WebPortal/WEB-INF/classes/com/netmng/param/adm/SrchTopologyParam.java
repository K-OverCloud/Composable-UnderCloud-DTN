package com.netmng.param.adm;

import java.io.Serializable;

public class SrchTopologyParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8710761242174720317L;
	
	private String mode;

	public String getMode() {
		return this.mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
}
