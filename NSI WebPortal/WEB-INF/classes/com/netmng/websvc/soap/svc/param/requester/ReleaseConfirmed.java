package com.netmng.websvc.soap.svc.param.requester;

public class ReleaseConfirmed {

	private String correlationId;
	private String requesterNSA;
	private String providerNSA;
	private String globalReservationId;
	private String connectionId;

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
	public String getGlobalReservationId() {
		return this.globalReservationId;
	}
	public void setGlobalReservationId(String globalReservationId) {
		this.globalReservationId = globalReservationId;
	}
	public String getConnectionId() {
		return this.connectionId;
	}
	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}
}
