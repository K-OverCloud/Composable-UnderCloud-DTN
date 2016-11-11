
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Type definition for the QueryNotification message providing a
 *                 mechanism for a Requester NSA to query a Provider NSA for a
 *                 set of notifications against a specific connectionId.
 *                 
 *                 Elements compose a filter for specifying the notifications to
 *                 return in response to the query operation.  The filter query
 *                 provides an inclusive range of notification identifiers based
 *                 on connectionId.
 *                 
 *                 Elements:
 *                 
 *                 connectionId - Notifications for this connectionId.
 *                 
 *                 startNotificationId - The start of the range of notificationIds
 *                 to return.  If not present then the query should start from
 *                 oldest notificationId available.
 *                 
 *                 endNotificationId - The end of the range of notificationIds
 *                 to return.  If not present then the query should end with
 *                 the newest notificationId available.
 *             
 * 
 * <p>QueryNotificationType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="QueryNotificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="connectionId" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}ConnectionIdType"/>
 *         &lt;element name="startNotificationId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="endNotificationId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryNotificationType", propOrder = {
    "connectionId",
    "startNotificationId",
    "endNotificationId"
})
public class QueryNotificationType {

    @XmlElement(required = true)
    protected String connectionId;
    protected Integer startNotificationId;
    protected Integer endNotificationId;

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
     * startNotificationId 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStartNotificationId() {
        return startNotificationId;
    }

    /**
     * startNotificationId 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStartNotificationId(Integer value) {
        this.startNotificationId = value;
    }

    /**
     * endNotificationId 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEndNotificationId() {
        return endNotificationId;
    }

    /**
     * endNotificationId 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEndNotificationId(Integer value) {
        this.endNotificationId = value;
    }

}
