package com.netmng.websvc.rest.server.param.event;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="event")
public class Event {
	
	private	String			cause;
	private	String			description;
	
	private String			router;
	private List<Interface>	listInterface;
	
	/*private Fault			fault;
	private Fault			recovery;
	private Fault			linked;
	private LinkList		linkList;
	private	Primary			primary;
	private	Primary			secondary;*/
	
	
	public String getCause() {
		return this.cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getRouter() {
		return router;
	}
	public void setRouter(String router) {
		this.router = router;
	}
	public List<Interface> getListInterface() {
		return listInterface;
	}
	public void setListInterface(List<Interface> listInterface) {
		this.listInterface = listInterface;
	}
	
	/*public Fault getFault() {
		return this.fault;
	}
	public void setFault(Fault fault) {
		this.fault = fault;
	}
	public Fault getRecovery() {
		return this.recovery;
	}
	public void setRecovery(Fault recovery) {
		this.recovery = recovery;
	}
	public Fault getLinked() {
		return this.linked;
	}
	public void setLinked(Fault linked) {
		this.linked = linked;
	}
	public LinkList getLinkList() {
		return this.linkList;
	}
	public void setLinkList(LinkList linkList) {
		this.linkList = linkList;
	}
	public Primary getPrimary() {
		return this.primary;
	}
	public void setPrimary(Primary primary) {
		this.primary = primary;
	}
	public Primary getSecondary() {
		return this.secondary;
	}
	public void setSecondary(Primary secondary) {
		this.secondary = secondary;
	}*/
}
