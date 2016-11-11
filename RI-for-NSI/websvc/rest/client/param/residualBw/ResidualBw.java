package com.netmng.websvc.rest.client.param.residualBw;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name="residualBW")
public class ResidualBw {

	@XmlValue
	public String		value;
	@XmlAttribute(name="unit")
	public String	unit;
	
}
