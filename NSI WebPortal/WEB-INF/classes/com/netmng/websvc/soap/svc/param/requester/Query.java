package com.netmng.websvc.soap.svc.param.requester;

import java.util.List;

/*import com.netmng.websvc.soap.param.types.QueryOperationType;*/

public class Query {
	
	private String correlationId;
	private String replyTo;
	private String requesterNSA;
	private String providerNSA;
	private List<Object> attributeOrEncryptedAttribute;	
	/*private QueryOperationType operation;*/
	private List<String> connectionId;
	private List<String> globalReservationId;
	
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
	/*public QueryOperationType getOperation() {
		return this.operation;
	}
	public void setOperation(QueryOperationType operation) {
		this.operation = operation;
	}*/
	public List<Object> getAttributeOrEncryptedAttribute() {
		return this.attributeOrEncryptedAttribute;
	}
	public void setAttributeOrEncryptedAttribute(
			List<Object> attributeOrEncryptedAttribute) {
		this.attributeOrEncryptedAttribute = attributeOrEncryptedAttribute;
	}
	public List<String> getConnectionId() {
		return this.connectionId;
	}
	public void setConnectionId(List<String> connectionId) {
		this.connectionId = connectionId;
	}
	public List<String> getGlobalReservationId() {
		return this.globalReservationId;
	}
	public void setGlobalReservationId(List<String> globalReservationId) {
		this.globalReservationId = globalReservationId;
	}
}
