package com.netmng.websvc.soap.svc.param.requester;

import java.util.List;

/*import com.netmng.websvc.soap.param.types.ConnectionStateType;*/

public class ProvisionFailed {

	private String correlationId;
	private String requesterNSA;
	private String providerNSA;
	private String globalReservationId;
	private String connectionId;
	/*private ConnectionStateType connectionState;*/
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
	/*public ConnectionStateType getConnectionState() {
		return this.connectionState;
	}
	public void setConnectionState(ConnectionStateType connectionState) {
		this.connectionState = connectionState;
	}*/
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
