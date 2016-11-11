package com.netmng.websvc.rest.client.param.gnpr;

import javax.xml.bind.annotation.XmlElement;

public class IntermedianNode {

	@XmlElement(name="router")
	public String router;
	@XmlElement(name="linterface")
	public String linterface;
	@XmlElement(name="rinterface")
	public String rinterface;
	
}
