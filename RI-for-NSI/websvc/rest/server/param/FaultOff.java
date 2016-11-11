package com.netmng.websvc.rest.server.param;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="faultOff")
public class FaultOff {
	
	private boolean resumeResult;
	private String router;
	private List<String> interfaceIP;
	
	public boolean isResumeResult() {
		return this.resumeResult;
	}
	public void setResumeResult(boolean resumeResult) {
		this.resumeResult = resumeResult;
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
