package com.netmng.dto.adm.stp;

import com.netmng.vo.InterfaceInfo;

public class InterfaceInfoDTO extends InterfaceInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1759006687158508218L;

	private InterfaceInfo 	link_interface;

	public InterfaceInfo getLink_interface() {
		return this.link_interface;
	}
	public void setLink_interface(InterfaceInfo link_interface) {
		this.link_interface = link_interface;
	}
}
