package com.netmng.websvc.soap.svc.param.provider;

import java.util.List;

public class QueryFailed {

	private String correlationId;
	private String requesterNSA;
	private String providerNSA;
	private String errorId;
	private String errorText;
	private List<Object> attributeOrEncryptedAttribute;

	public String getCorrelationId() {
		return this.correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
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
	public String getErrorId() {
		return this.errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public String getErrorText() {
		return this.errorText;
	}
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	public List<Object> getAttributeOrEncryptedAttribute() {
		return this.attributeOrEncryptedAttribute;
	}
	public void setAttributeOrEncryptedAttribute(
			List<Object> attributeOrEncryptedAttribute) {
		this.attributeOrEncryptedAttribute = attributeOrEncryptedAttribute;
	}
}
