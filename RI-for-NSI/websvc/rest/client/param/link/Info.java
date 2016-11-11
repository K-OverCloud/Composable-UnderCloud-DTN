package com.netmng.websvc.rest.client.param.link;

import javax.xml.bind.annotation.XmlElement;

public class Info {
	
	@XmlElement(name="bandwidth")
	public Bandwidth 	bandwidth 				= new Bandwidth();
	public String		sourceRouter;
	public String		sourceInterface;
	public String		destinationRouter;
	public String		destinationInterface;
}
