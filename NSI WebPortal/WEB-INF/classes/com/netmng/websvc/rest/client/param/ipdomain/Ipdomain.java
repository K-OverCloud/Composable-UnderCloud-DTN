package com.netmng.websvc.rest.client.param.ipdomain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="stpIpDomain")
public class Ipdomain {
	
	public String numberOfEntries;
	@XmlElement(name="info")
	public List<Info> infoList;
	
	//@XmlAttribute(name="xmlns:xsi")
	//public String xmlns_xsi = "http://www.w3.org/2001/XMLSchema-instance";
	//@XmlAttribute(name="xsi:schemaLocation")
	//public String xsi_schemaLocation = "http://kisti.re.kr/dynamicKL/api dynamicKL.xsd";
	//@XmlAttribute(name="xmlns")
	//public String xmlns = "http://kisti.re.kr/dynamicKL/api";		
}
