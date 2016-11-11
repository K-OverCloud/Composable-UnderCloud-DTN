
package com.netmng.websvc.soap.assertion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>BaseIDAbstractType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="BaseIDAbstractType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attGroup ref="{urn:oasis:names:tc:SAML:2.0:assertion}IDNameQualifiers"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseIDAbstractType")
public abstract class BaseIDAbstractType {

    @XmlAttribute(name = "NameQualifier")
    protected String nameQualifier;
    @XmlAttribute(name = "SPNameQualifier")
    protected String spNameQualifier;

    /**
     * nameQualifier 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameQualifier() {
        return nameQualifier;
    }

    /**
     * nameQualifier 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameQualifier(String value) {
        this.nameQualifier = value;
    }

    /**
     * spNameQualifier 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPNameQualifier() {
        return spNameQualifier;
    }

    /**
     * spNameQualifier 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPNameQualifier(String value) {
        this.spNameQualifier = value;
    }

}
