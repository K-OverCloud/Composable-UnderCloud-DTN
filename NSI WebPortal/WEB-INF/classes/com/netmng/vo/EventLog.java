package com.netmng.vo;

public class EventLog {

	private	String	seq;
	private	String	event_seq;
	private	String	fault_router;
	private	String	fault_interface;
	private	String	link_router;
	private	String	link_interface;
	private	String	line;
	private	String	fault_urn;
	private	String	link_urn;
	private	String	description;
	private	String	cause;
	
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getEvent_seq() {
		return this.event_seq;
	}
	public void setEvent_seq(String event_seq) {
		this.event_seq = event_seq;
	}
	public String getFault_router() {
		return this.fault_router;
	}
	public void setFault_router(String fault_router) {
		this.fault_router = fault_router;
	}
	public String getFault_interface() {
		return this.fault_interface;
	}
	public void setFault_interface(String fault_interface) {
		this.fault_interface = fault_interface;
	}
	public String getLink_router() {
		return this.link_router;
	}
	public void setLink_router(String link_router) {
		this.link_router = link_router;
	}
	public String getLink_interface() {
		return this.link_interface;
	}
	public void setLink_interface(String link_interface) {
		this.link_interface = link_interface;
	}
	public String getLine() {
		return this.line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getFault_urn() {
		return this.fault_urn;
	}
	public void setFault_urn(String fault_urn) {
		this.fault_urn = fault_urn;
	}
	public String getLink_urn() {
		return this.link_urn;
	}
	public void setLink_urn(String link_urn) {
		this.link_urn = link_urn;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCause() {
		return this.cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
}
