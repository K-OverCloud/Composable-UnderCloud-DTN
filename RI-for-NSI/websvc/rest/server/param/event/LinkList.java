package com.netmng.websvc.rest.server.param.event;

import java.util.List;

public class LinkList {

	private List<Fault> fault;
	private List<Fault> recovery;
	private List<Fault> linked;
	
	public List<Fault> getFault() {
		return this.fault;
	}
	public void setFault(List<Fault> fault) {
		this.fault = fault;
	}
	public List<Fault> getRecovery() {
		return this.recovery;
	}
	public void setRecovery(List<Fault> recovery) {
		this.recovery = recovery;
	}
	public List<Fault> getLinked() {
		return this.linked;
	}
	public void setLinked(List<Fault> linked) {
		this.linked = linked;
	}
	
}
