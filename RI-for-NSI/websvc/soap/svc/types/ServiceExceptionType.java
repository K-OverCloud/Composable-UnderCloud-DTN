
package com.netmng.websvc.soap.svc.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Common service exception used for SOAP faults and Failed
 *                 message.
 *                 
 *                 Elements:
 *                 
 *                 nsaId - NSA that generated the service exception.
 *                 
 *                 connectionId - The connectionId associated with the reservation
 *                 impacted by this error.
 *                 
 *                 serviceType - The service type identifying the applicable
 *                 service description in the context of the NSA generating the
 *                 error.
 *                 
 *                 errorId - Error identifier uniquely identifying each known
 *                 fault within the protocol.  Acts as a parent functionality
 *                 classification for service specific errors.
 *                 
 *                 text - User friendly message text describing the error.
 *                 
 *                 variables - An  optional collection of type/value pairs providing
 *                 additional information relating to the error.
 *                 
 *                 childException - Hierarchical list of service exceptions
 *                 capturing failures within the request tree.
 *             
 * 
 * <p>ServiceExceptionType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="ServiceExceptionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nsaId" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}NsaIdType"/>
 *         &lt;element name="connectionId" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}ConnectionIdType" minOccurs="0"/>
 *         &lt;element name="serviceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="variables" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}VariablesType" minOccurs="0"/>
 *         &lt;element name="childException" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}ServiceExceptionType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceExceptionType", propOrder = {
    "nsaId",
    "connectionId",
    "serviceType",
    "errorId",
    "text",
    "variables",
    "childException"
})
public class ServiceExceptionType {

    @XmlElement(required = true)
    protected String nsaId;
    protected String connectionId;
    protected String serviceType;
    @XmlElement(required = true)
    protected String errorId;
    @XmlElement(required = true)
    protected String text;
    protected VariablesType variables;
    protected List<ServiceExceptionType> childException;

    /**
     * nsaId 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNsaId() {
        return nsaId;
    }

    /**
     * nsaId 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNsaId(String value) {
        this.nsaId = value;
    }

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
     * serviceType 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * serviceType 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceType(String value) {
        this.serviceType = value;
    }

    /**
     * errorId 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorId() {
        return errorId;
    }

    /**
     * errorId 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorId(String value) {
        this.errorId = value;
    }

    /**
     * text 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * text 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * variables 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link VariablesType }
     *     
     */
    public VariablesType getVariables() {
        return variables;
    }

    /**
     * variables 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link VariablesType }
     *     
     */
    public void setVariables(VariablesType value) {
        this.variables = value;
    }

    /**
     * Gets the value of the childException property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the childException property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChildException().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceExceptionType }
     * 
     * 
     */
    public List<ServiceExceptionType> getChildException() {
        if (childException == null) {
            childException = new ArrayList<ServiceExceptionType>();
        }
        return this.childException;
    }

}
