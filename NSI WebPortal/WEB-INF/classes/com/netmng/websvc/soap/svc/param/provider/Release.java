package com.netmng.websvc.soap.svc.param.provider;

import com.netmng.websvc.soap.assertion.AttributeStatementType;

public class Release {
	
	private String correlationId;
	private String replyTo;
	private String requesterNSA;
	private String providerNSA;
	private String connectionId;
	private AttributeStatementType sessionSecurityAttr;
	
	public String getCorrelationId() {
		return this.correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getReplyTo() {
		return this.replyTo;
	}
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	public String getRequesterNSA() {
		return this.requesterNSA;
	}
	public void setRequesterNSA(String requesterNSA) {
		this.requesterNSA = requesterNSA;
	}
	public String getProviderNSA() {
		return this.providerNSA;
	}
	public void setProviderNSA(String providerNSA) {
		this.providerNSA = providerNSA;
	}
	public String getConnectionId() {
		return this.connectionId;
	}
	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}
	public AttributeStatementType getSessionSecurityAttr() {
		return this.sessionSecurityAttr;
	}
	public void setSessionSecurityAttr(AttributeStatementType sessionSecurityAttr) {
		this.sessionSecurityAttr = sessionSecurityAttr;
	}
}
