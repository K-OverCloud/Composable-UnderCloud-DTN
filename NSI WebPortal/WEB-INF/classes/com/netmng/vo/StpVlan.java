package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;
import com.netmng.vo.StpNetwork;

public class StpVlan extends StpLocal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2920823847177486508L;

	private int 	vlan_seq;

	public int getVlan_seq() {
		return vlan_seq;
	}
	public void setVlan_seq(int vlan_seq) {
		this.vlan_seq = vlan_seq;
	}
}
