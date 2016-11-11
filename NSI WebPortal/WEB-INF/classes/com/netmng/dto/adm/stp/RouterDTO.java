package com.netmng.dto.adm.stp;

import java.util.List;

import com.netmng.vo.Router;
import com.netmng.vo.RouterType;

public class RouterDTO extends Router {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8590330295867674144L;

	private RouterType	routerType;

	private List<InterfaceInfoDTO> interfaceInfoDTOList;
	
	public RouterType getRouterType() {
		return this.routerType;
	}
	public void setRouterType(RouterType routerType) {
		this.routerType = routerType;
	}
	public List<InterfaceInfoDTO> getInterfaceInfoDTOList() {
		return this.interfaceInfoDTOList;
	}
	public void setInterfaceInfoDTOList(List<InterfaceInfoDTO> interfaceInfoDTOList) {
		this.interfaceInfoDTOList = interfaceInfoDTOList;
	}
}
