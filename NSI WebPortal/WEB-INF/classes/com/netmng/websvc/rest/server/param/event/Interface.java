package com.netmng.websvc.rest.server.param.event;

import javax.xml.bind.annotation.XmlElement;

public class Interface {

	/*private String router;*/
	private String _interface;
	
	/*public String getRouter() {
		return this.router;
	}
	public void setRouter(String router) {
		this.router = router;
	}*/
	@XmlElement(name="interface")
	public String get_interface() {
		return this._interface;
	}
	public void set_interface(String _interface) {
		this._interface = _interface;
	}
	
}
