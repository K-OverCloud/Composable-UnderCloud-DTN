package com.netmng.websvc.soap.svc.param.requester;

import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.netmng.websvc.soap.assertion.AttributeStatementType;
/*import com.netmng.websvc.soap.param.types.DirectionalityType;
import com.netmng.websvc.soap.param.types.OrderedServiceTerminationPointType;*/

public class ReserveConfirmed {
	
	private String correlationId;
	private String requesterNSA;
	private String providerNSA;
	private String globalReservationId;
	private String description;
    private String connectionId;
    private XMLGregorianCalendar startTime;
    private XMLGregorianCalendar endTime;
    private Duration duration;
    private int desired;
    private Integer minimum;
    private Integer maximum;
    private AttributeStatementType serviceGuaranteed;
    private AttributeStatementType servicePreferred;
    /*private DirectionalityType directionality;*/
    private String sourceStpId;
    private AttributeStatementType sourceGuaranteed;
    private AttributeStatementType sourcePreferred;
    private String destStpId;
    private AttributeStatementType destGuaranteed;
    private AttributeStatementType destPreferred;
    /*private List<OrderedServiceTerminationPointType> stp;*/
    
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
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getConnectionId() {
		return this.connectionId;
	}
	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}
	public XMLGregorianCalendar getStartTime() {
		return this.startTime;
	}
	public void setStartTime(XMLGregorianCalendar startTime) {
		this.startTime = startTime;
	}
	public XMLGregorianCalendar getEndTime() {
		return this.endTime;
	}
	public void setEndTime(XMLGregorianCalendar endTime) {
		this.endTime = endTime;
	}
	public Duration getDuration() {
		return this.duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	public int getDesired() {
		return this.desired;
	}
	public void setDesired(int desired) {
		this.desired = desired;
	}
	public Integer getMinimum() {
		return this.minimum;
	}
	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}
	public Integer getMaximum() {
		return this.maximum;
	}
	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}
	public AttributeStatementType getServiceGuaranteed() {
		return this.serviceGuaranteed;
	}
	public void setServiceGuaranteed(AttributeStatementType serviceGuaranteed) {
		this.serviceGuaranteed = serviceGuaranteed;
	}
	public AttributeStatementType getServicePreferred() {
		return this.servicePreferred;
	}
	public void setServicePreferred(AttributeStatementType servicePreferred) {
		this.servicePreferred = servicePreferred;
	}
	/*public DirectionalityType getDirectionality() {
		return this.directionality;
	}
	public void setDirectionality(DirectionalityType directionality) {
		this.directionality = directionality;
	}*/
	public String getSourceStpId() {
		return this.sourceStpId;
	}
	public void setSourceStpId(String sourceStpId) {
		this.sourceStpId = sourceStpId;
	}
	public AttributeStatementType getSourceGuaranteed() {
		return this.sourceGuaranteed;
	}
	public void setSourceGuaranteed(AttributeStatementType sourceGuaranteed) {
		this.sourceGuaranteed = sourceGuaranteed;
	}
	public AttributeStatementType getSourcePreferred() {
		return this.sourcePreferred;
	}
	public void setSourcePreferred(AttributeStatementType sourcePreferred) {
		this.sourcePreferred = sourcePreferred;
	}
	public String getDestStpId() {
		return this.destStpId;
	}
	public void setDestStpId(String destStpId) {
		this.destStpId = destStpId;
	}
	public AttributeStatementType getDestGuaranteed() {
		return this.destGuaranteed;
	}
	public void setDestGuaranteed(AttributeStatementType destGuaranteed) {
		this.destGuaranteed = destGuaranteed;
	}
	public AttributeStatementType getDestPreferred() {
		return this.destPreferred;
	}
	public void setDestPreferred(AttributeStatementType destPreferred) {
		this.destPreferred = destPreferred;
	}
	/*public List<OrderedServiceTerminationPointType> getStp() {
		return this.stp;
	}
	public void setStp(List<OrderedServiceTerminationPointType> stp) {
		this.stp = stp;
	}*/
	
}
