package com.netmng.websvc.rest.client.param.gnpr;

import javax.xml.bind.annotation.XmlElement;

public class Site {

	@XmlElement(name="router")
	public String router;
	@XmlElement(name="interface")
	public String _interface;
	
}
