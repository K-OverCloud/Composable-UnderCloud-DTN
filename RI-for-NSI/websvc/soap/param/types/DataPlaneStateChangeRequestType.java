
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Type definition for the data plane state change notification
 *                 message.
 *                 
 *                 This notification message sent up from a PA when a data plane
 *                 status has changed. Possible data plane status changes are:
 *                 activation, deactivation and activation version change.
 *                 
 *                 Elements:
 *                 
 *                 dataPlaneStatus - Current data plane activation state for the
 *                 reservation identified by connectionId.
 *             
 * 
 * <p>DataPlaneStateChangeRequestType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="DataPlaneStateChangeRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.ogf.org/nsi/2013/07/connection/types}NotificationBaseType">
 *       &lt;sequence>
 *         &lt;element name="dataPlaneStatus" type="{http://schemas.ogf.org/nsi/2013/07/connection/types}DataPlaneStatusType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataPlaneStateChangeRequestType", propOrder = {
    "dataPlaneStatus"
})
public class DataPlaneStateChangeRequestType
    extends NotificationBaseType
{

    @XmlElement(required = true)
    protected DataPlaneStatusType dataPlaneStatus;

    /**
     * dataPlaneStatus 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link DataPlaneStatusType }
     *     
     */
    public DataPlaneStatusType getDataPlaneStatus() {
        return dataPlaneStatus;
    }

    /**
     * dataPlaneStatus 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link DataPlaneStatusType }
     *     
     */
    public void setDataPlaneStatus(DataPlaneStatusType value) {
        this.dataPlaneStatus = value;
    }

}
