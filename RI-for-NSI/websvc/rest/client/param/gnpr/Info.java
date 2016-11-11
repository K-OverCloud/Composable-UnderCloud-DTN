package com.netmng.websvc.rest.client.param.gnpr;

import javax.xml.bind.annotation.XmlAccessType; 
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="info") 
@XmlAccessorType(XmlAccessType.FIELD) 
public class Info {

	@XmlElement(name="gnprId")
	public 	String gnprId;
	@XmlElement(name="siteA")
	public	Site 					siteA;
	@XmlElement(name="siteB")
	public 	Site 					siteB;
	@XmlElement(name="intermedianNode")
	public 	IntermedianNode 		intermedianNode;
	@XmlElement(name="atoBBandwidth")
	public Bandwidth 				atoBBandwidth = new Bandwidth();
	@XmlElement(name="btoABandwidth")
	public Bandwidth 				btoABandwidth = new Bandwidth();
	@XmlElement(name="latency")
	public Latency 					latency = new Latency();

}
