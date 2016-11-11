
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.netmng.websvc.soap.svc.types.ServiceExceptionType;


/**
 * 
 *                 A generic "Failed" message type sent as request in response to a
 *                 failure to process a previous protocol "Request" message.  This is
 *                 used in response to all request types that can return an error.
 * 
 *                 Elements:
 * 
 *                 connectionId - The Provider NSA assigned connectionId for this
 *                 reservation. This value will be unique within the context
 *                 of the Provider NSA.
 * 
 *                 connectionStates - Overall connection state for the reservation.
 * 
 *                 serviceException - Specific error condition - the reason for the
 *                 failure.
 *             
 * 
 * <p>GenericFailedType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="GenericFailedType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="connectionId" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}ConnectionIdType"/>
 *         &lt;element name="connectionStates" type="{http://schemas.ogf.org/nsi/2013/07/connection/types}ConnectionStatesType"/>
 *         &lt;element name="serviceException" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}ServiceExceptionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenericFailedType", propOrder = {
    "connectionId",
    "connectionStates",
    "serviceException"
})
public class GenericFailedType {

    @XmlElement(required = true)
    protected String connectionId;
    @XmlElement(required = true)
    protected ConnectionStatesType connectionStates;
    @XmlElement(required = true)
    protected ServiceExceptionType serviceException;

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
     * connectionStates 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link ConnectionStatesType }
     *     
     */
    public ConnectionStatesType getConnectionStates() {
        return connectionStates;
    }

    /**
     * connectionStates 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link ConnectionStatesType }
     *     
     */
    public void setConnectionStates(ConnectionStatesType value) {
        this.connectionStates = value;
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
