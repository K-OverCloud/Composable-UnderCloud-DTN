package com.netmng.websvc.soap.svc.param.provider;

import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

/*import com.netmng.websvc.soap.param.types.DirectionalityType;
import com.netmng.websvc.soap.param.types.OrderedServiceTerminationPointType;*/

public class Reserve {
	
	private String correlationId;
	private String replyTo;
	private String requesterNSA;
	private String providerNSA;
	private List<Object> attributeOrEncryptedAttribute;
	private String globalReservationId;
	private String description;
	private String connectionId;
	private XMLGregorianCalendar startTime;
	private XMLGregorianCalendar endTime;
	private Duration duration;
	private int desired;
	private Integer minimum;
	private Integer maximum;
	
	private List<Object> serviceGuaranteed;
	private List<Object> servicePreferred;
	
	/*private DirectionalityType directionality;*/
	
	private String sourceStpId;
	private List<Object> sourceGuaranteed;
	private List<Object> sourcePreferred;
	private String destStpId;
	private List<Object> destGuaranteed;
	private List<Object> destPreferred;
	/*private List<OrderedServiceTerminationPointType> stp;*/
	
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
	public List<Object> getAttributeOrEncryptedAttribute() {
		return this.attributeOrEncryptedAttribute;
	}
	public void setAttributeOrEncryptedAttribute(
			List<Object> attributeOrEncryptedAttribute) {
		this.attributeOrEncryptedAttribute = attributeOrEncryptedAttribute;
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
	public List<Object> getServiceGuaranteed() {
		return this.serviceGuaranteed;
	}
	public void setServiceGuaranteed(List<Object> serviceGuaranteed) {
		this.serviceGuaranteed = serviceGuaranteed;
	}
	public List<Object> getServicePreferred() {
		return this.servicePreferred;
	}
	public void setServicePreferred(List<Object> servicePreferred) {
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
	public List<Object> getSourceGuaranteed() {
		return this.sourceGuaranteed;
	}
	public void setSourceGuaranteed(List<Object> sourceGuaranteed) {
		this.sourceGuaranteed = sourceGuaranteed;
	}
	public List<Object> getSourcePreferred() {
		return this.sourcePreferred;
	}
	public void setSourcePreferred(List<Object> sourcePreferred) {
		this.sourcePreferred = sourcePreferred;
	}
	public String getDestStpId() {
		return this.destStpId;
	}
	public void setDestStpId(String destStpId) {
		this.destStpId = destStpId;
	}
	public List<Object> getDestGuaranteed() {
		return this.destGuaranteed;
	}
	public void setDestGuaranteed(List<Object> destGuaranteed) {
		this.destGuaranteed = destGuaranteed;
	}
	public List<Object> getDestPreferred() {
		return this.destPreferred;
	}
	public void setDestPreferred(List<Object> destPreferred) {
		this.destPreferred = destPreferred;
	}
	/*public List<OrderedServiceTerminationPointType> getStp() {
		return this.stp;
	}
	public void setStp(List<OrderedServiceTerminationPointType> stp) {
		this.stp = stp;
	}*/
}
