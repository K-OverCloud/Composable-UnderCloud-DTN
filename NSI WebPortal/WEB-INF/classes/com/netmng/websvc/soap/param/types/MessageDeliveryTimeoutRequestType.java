
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 A notification message type definition for the Message Transport
 *                 Layer (MTL) delivery timeout of a request message. In the event
 *                 of an MTL timed out or Coordinator timeout, the Coordinator will
 *                 generate this message delivery failure notification and send it
 *                 up the workflow tree (towards the uRA).
 *                 
 *                 An MTL timeout can be generated as the result of a timeout
 *                 on receiving an ACK message for a corresponding send request.
 *                 A Coordinator timeout can occur when no confirm or fail reply
 *                 has been received to a previous request issued by the
 *                 Coordinator.  In both cases the local timers for these timeout
 *                 conditions are locally defined.
 *                 
 *                 Elements:
 *                 
 *                 correlationId - This value indicates the correlationId of
 *                 the original message that the transport layer failed to
 *                 send.
 *             
 * 
 * <p>MessageDeliveryTimeoutRequestType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="MessageDeliveryTimeoutRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.ogf.org/nsi/2013/07/connection/types}NotificationBaseType">
 *       &lt;sequence>
 *         &lt;element name="correlationId" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}UuidType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessageDeliveryTimeoutRequestType", propOrder = {
    "correlationId"
})
public class MessageDeliveryTimeoutRequestType
    extends NotificationBaseType
{

    @XmlElement(required = true)
    protected String correlationId;

    /**
     * correlationId 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * correlationId 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrelationId(String value) {
        this.correlationId = value;
    }

}
