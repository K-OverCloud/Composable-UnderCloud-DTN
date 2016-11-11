package com.netmng.websvc.rest.server.param.event;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Primary {
	
	private List<String> router;
	private List<String> _interface;
	
	public List<String> getRouter() {
		return this.router;
	}
	public void setRouter(List<String> router) {
		this.router = router;
	}
	@XmlElement(name="interface")
	public List<String> get_interface() {
		return this._interface;
	}
	public void set_interface(List<String> _interface) {
		this._interface = _interface;
	}
	
}
