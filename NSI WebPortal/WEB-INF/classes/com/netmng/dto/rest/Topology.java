package com.netmng.dto.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Topology {
	
	private	String	seq;
	private	String	site_name;
	private	String	urn;
	private	String	kind;
	private	boolean	router_fault;
	private	boolean	p_interface_fault;
	private	boolean	s_interface_fault;
	private	String	use_line;
	
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSite_name() {
		return this.site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getUrn() {
		return this.urn;
	}
	public void setUrn(String urn) {
		this.urn = urn;
	}
	public String getKind() {
		return this.kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public boolean getRouter_fault() {
		return this.router_fault;
	}
	public void setRouter_fault(boolean router_fault) {
		this.router_fault = router_fault;
	}
	public boolean getP_interface_fault() {
		return this.p_interface_fault;
	}
	public void setP_interface_fault(boolean p_interface_fault) {
		this.p_interface_fault = p_interface_fault;
	}
	public boolean getS_interface_fault() {
		return this.s_interface_fault;
	}
	public void setS_interface_fault(boolean s_interface_fault) {
		this.s_interface_fault = s_interface_fault;
	}
	public String getUse_line() {
		return this.use_line;
	}
	public void setUse_line(String use_line) {
		this.use_line = use_line;
	}	
}
