
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.netmng.websvc.soap.svc.types.ServiceExceptionType;
import com.netmng.websvc.soap.svc.types.TypeValuePairListType;


/**
 * 
 *                 Type definition for an autonomous message issued from a
 *                 Provider NSA to a Requester NSA when an existing reservation
 *                 encounters an autonomous error condition such as being
 *                 administratively terminated before the reservation's scheduled
 *                 end-time.
 *                 
 *                 Elements:
 *                 
 *                 event - The type of event that generated this notification.
 *                 
 *                 additionalInfo - Type/value pairs that can provide additional
 *                 error context as needed.
 *                 
 *                 serviceException - Specific error condition - the reason for the
 *                 generation of the error event.
 *             
 * 
 * <p>ErrorEventType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="ErrorEventType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.ogf.org/nsi/2013/07/connection/types}NotificationBaseType">
 *       &lt;sequence>
 *         &lt;element name="event" type="{http://schemas.ogf.org/nsi/2013/07/connection/types}EventEnumType"/>
 *         &lt;element name="additionalInfo" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}TypeValuePairListType" minOccurs="0"/>
 *         &lt;element name="serviceException" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}ServiceExceptionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorEventType", propOrder = {
    "event",
    "additionalInfo",
    "serviceException"
})
public class ErrorEventType
    extends NotificationBaseType
{

    @XmlElement(required = true)
    protected EventEnumType event;
    protected TypeValuePairListType additionalInfo;
    protected ServiceExceptionType serviceException;

    /**
     * event 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link EventEnumType }
     *     
     */
    public EventEnumType getEvent() {
        return event;
    }

    /**
     * event 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link EventEnumType }
     *     
     */
    public void setEvent(EventEnumType value) {
        this.event = value;
    }

    /**
     * additionalInfo 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link TypeValuePairListType }
     *     
     */
    public TypeValuePairListType getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * additionalInfo 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeValuePairListType }
     *     
     */
    public void setAdditionalInfo(TypeValuePairListType value) {
        this.additionalInfo = value;
    }

    /**
     * serviceException 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link ServiceExceptionType }
     *     
     */
    public ServiceExceptionType getServiceException() {
        return serviceException;
    }

    /**
     * serviceException 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceExceptionType }
     *     
     */
    public void setServiceException(ServiceExceptionType value) {
        this.serviceException = value;
    }

}
