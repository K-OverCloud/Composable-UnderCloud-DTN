package com.netmng.websvc.rest.client.param;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="event")
@XmlAccessorType(XmlAccessType.FIELD)
public class Event {
	
	//@XmlAttribute(name="xmlns:xsi")
	//public String 			xmlns_xsi = "http://www.w3.org/2001/XMLSchema-instance";
	//@XmlAttribute(name="xsi:schemaLocation")
	//public String 			xsi_schemaLocation = "http://kisti.re.kr/dynamicKL/api dynamicKL.xsd";
	//@XmlAttribute(name="xmlns")
	//public String 			xmlns = "http://kisti.re.kr/dynamicKL/api";
		
	public String cause;
	public String description;
	public String router;
	@XmlElement(name="interface")
	public List<String> _interface;
	
}
