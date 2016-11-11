package com.netmng.dto.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TopologyList {
	
	List<Topology> topology;

	public List<Topology> getTopology() {
		return this.topology;
	}
	public void setTopology(List<Topology> topology) {
		this.topology = topology;
	}
}
