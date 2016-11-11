package com.netmng.websvc.rest.client.param.residualBw;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="stpAndTime")
public class StpAndTime {
/*
	@XmlAttribute(name="xmlns:xsi")
	public String xmlns_xsi = "http://www.w3.org/2001/XMLSchema-instance";
	@XmlAttribute(name="xsi:schemaLocation")
	public String xsi_schemaLocation = "http://kisti.re.kr/dynamicKL/api dynamicKL.xsd";
	@XmlAttribute(name="xmlns")
	public String xmlns = "http://kisti.re.kr/dynamicKL/api";
*/	
	public String	sourceSTP; 
	public String	destSTP;
	public String	startTime;
	public String	endTime;
	
}
