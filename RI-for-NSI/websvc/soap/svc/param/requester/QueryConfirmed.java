package com.netmng.websvc.soap.svc.param.requester;

import java.util.List;

/*import com.netmng.websvc.soap.param.types.QueryDetailsResultType;*/
import com.netmng.websvc.soap.param.types.QuerySummaryResultType;

public class QueryConfirmed {
	
	private String correlationId;
	private String requesterNSA;
	private String providerNSA;
	private List<QuerySummaryResultType> reservationSummary;
	/*private List<QueryDetailsResultType> reservationDetails;*/

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
	public List<QuerySummaryResultType> getReservationSummary() {
		return this.reservationSummary;
	}
	public void setReservationSummary(
			List<QuerySummaryResultType> reservationSummary) {
		this.reservationSummary = reservationSummary;
	}
	/*public List<QueryDetailsResultType> getReservationDetails() {
		return this.reservationDetails;
	}
	public void setReservationDetails(
			List<QueryDetailsResultType> reservationDetails) {
		this.reservationDetails = reservationDetails;
	}*/
}
