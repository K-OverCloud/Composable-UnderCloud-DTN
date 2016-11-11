package com.netmng.websvc.rest.server.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement(name="failureResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResMsg {
	
	private String	cause;
	
	//@XmlAttribute(name="xmlns:xsi")
	//public String xmlns_xsi = "http://www.w3.org/2001/XMLSchema-instance";
	//@XmlAttribute(name="xsi:schemaLocation")
	//public String xsi_schemaLocation = "http://kisti.re.kr/dynamicKL/api dynamicKL.xsd";
	//@XmlAttribute(name="xmlns")
	//public String xmlns = "http://kisti.re.kr/dynamicKL/api";
	
	public ResMsg() {
		
	}
	
	public ResMsg(String cause) {
		this.cause = cause;
	}
	
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	
}
