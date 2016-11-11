
package com.netmng.websvc.soap.param.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;


/**
 * 
 *                 This type is used to model a connection reservation's summary
 *                 path information.  The structure provides the next level of
 *                 connection information but not state.
 * 
 *                 Elements:
 * 
 *                 connectionId - The connection identifier associated with the
 *                 reservation and path segment.
 *                 
 *                 providerNSA - The provider NSA holding the connection
 *                 information associated with this instance of data.
 *                 
 *                 serviceType - The specific service type of this reservation. 
 *                 This service type string maps into the list of supported service
 *                 descriptions defined by the network providers, and in turn, to
 *                 the specific service elements carried in the serviceAttributes
 *                 element required to specify the requested service.  
 * 
 *                 any - Provides a flexible mechanism allowing additional elements\u2028
 *                 to be provided such as the service specific attributes specified\u2028
 *                 by serviceType.  Additional use of this element field is beyond\u2028
 *                 the current scope of this NSI specification, but may be used in\u2028
 *                 the future to extend the existing protocol without requiring a\u2028
 *                 schema change.\u2028
 *                 
 *                 Attributes:
 *                 
 *                 order - Specification of ordered path elements.
 *                 
 *                 anyAttribute - Provides a flexible mechanism allowing additional\u2028
 *                 attributes to be provided as needed.  Use of this attribute field\u2028
 *                 is beyond the current scope of this NSI specification, but may be
 *                 used in the future to extend the existing protocol without\u2028
 *                 requiring a schema change.
 *             
 * 
 * <p>ChildSummaryType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="ChildSummaryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="connectionId" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}ConnectionIdType"/>
 *         &lt;element name="providerNSA" type="{http://schemas.ogf.org/nsi/2013/07/framework/types}NsaIdType"/>
 *         &lt;element name="serviceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="order" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChildSummaryType", propOrder = {
    "connectionId",
    "providerNSA",
    "serviceType",
    "any"
})
public class ChildSummaryType {

    @XmlElement(required = true)
    protected String connectionId;
    @XmlElement(required = true)
    protected String providerNSA;
    protected String serviceType;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute(name = "order", required = true)
    protected int order;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

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
     * providerNSA 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderNSA() {
        return providerNSA;
    }

    /**
     * providerNSA 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderNSA(String value) {
        this.providerNSA = value;
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
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * {@link Element }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

    /**
     * order 속성의 값을 가져옵니다.
     * 
     */
    public int getOrder() {
        return order;
    }

    /**
     * order 속성의 값을 설정합니다.
     * 
     */
    public void setOrder(int value) {
        this.order = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
