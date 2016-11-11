package com.netmng.websvc.rest.server.param;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="faultOn")
public class FaultOn {

	private String cause;
	private String description;
	private boolean recoveryResult;
	private String router;
	private List<String> interfaceIP;
	
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
	public boolean isRecoveryResult() {
		return this.recoveryResult;
	}
	public void setRecoveryResult(boolean recoveryResult) {
		this.recoveryResult = recoveryResult;
	}
	public String getRouter() {
		return this.router;
	}
	public void setRouter(String router) {
		this.router = router;
	}
	@XmlElement(name="interface")
	public List<String> getInterfaceIP() {
		return this.interfaceIP;
	}
	public void setInterfaceIP(List<String> interfaceIP) {
		this.interfaceIP = interfaceIP;
	}
}
