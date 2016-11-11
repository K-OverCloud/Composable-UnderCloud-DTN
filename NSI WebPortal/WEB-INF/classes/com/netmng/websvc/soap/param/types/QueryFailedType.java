
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.netmng.websvc.soap.svc.types.ServiceExceptionType;


/**
 * 
 *                 A query "Failed" message type sent as request in response to a
 *                 failure to process a queryRequest message.  This is message is
 *                 returned as a result of a processing error and not for the case
 *                 where a query returns an empty result set.
 *                 
 *                 Elements:
 *                 
 *                 ServiceException - Specific error condition - the reason for the
 *                 failure.
 *             
 * 
 * <p>QueryFailedType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="QueryFailedType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
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
@XmlType(name = "QueryFailedType", propOrder = {
    "serviceException"
})
public class QueryFailedType {

    @XmlElement(required = true)
    protected ServiceExceptionType serviceException;

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
