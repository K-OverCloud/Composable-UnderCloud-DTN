
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * 
 *                 A base type definition for an autonomous message issued from a
 *                 Provider NSA to a Requester NSA.
 *                 
 *                 Elements:
 *                 
 *                 connectionId - The Provider NSA assigned connectionId that this
 *                 notification is against.
 * 
 *                 notificationId - A notification identifier that is unique in the
 *                 context of a connectionId.  This is a linearly increasing
 *                 identifier that can be used for ordering notifications in the
 *                 context of the connectionId.
 *                 
 *                 timeStamp - Time the event was generated on the originating NSA.
 *             
 * 
 * <p>NotificationBaseType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="NotificationBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="connectionId" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}ConnectionIdType"/>
 *         &lt;element name="notificationId" type="{http://schemas.ogf.org/nsi/2013/07/connection/types}NotificationIdType"/>
 *         &lt;element name="timeStamp" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}DateTimeType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificationBaseType", propOrder = {
    "connectionId",
    "notificationId",
    "timeStamp"
})
@XmlSeeAlso({
    MessageDeliveryTimeoutRequestType.class,
    DataPlaneStateChangeRequestType.class,
    ErrorEventType.class,
    ReserveTimeoutRequestType.class
})
public class NotificationBaseType {

    @XmlElement(required = true)
    protected String connectionId;
    protected int notificationId;
    @XmlElement(required = true)
    protected XMLGregorianCalendar timeStamp;

    /**
     * connectionId 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectionId() {
        return connectionId;
    }

    /**
     * connectionId 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnectionId(String value) {
        this.connectionId = value;
    }

    /**
     * notificationId 속성의 값을 가져옵니다.
     * 
     */
    public int getNotificationId() {
        return notificationId;
    }

    /**
     * notificationId 속성의 값을 설정합니다.
     * 
     */
    public void setNotificationId(int value) {
        this.notificationId = value;
    }

    /**
     * timeStamp 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * timeStamp 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

}
