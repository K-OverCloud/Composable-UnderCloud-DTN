
package com.netmng.websvc.soap.assertion;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import com.netmng.websvc.soap.result.xmldsig.SignatureType;


/**
 * <p>AssertionType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="AssertionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Issuer"/>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Subject" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Conditions" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Advice" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Statement"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}AuthnStatement"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}AuthzDecisionStatement"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}AttributeStatement"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="Version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="IssueInstant" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssertionType", propOrder = {
    "issuer",
    "signature",
    "subject",
    "conditions",
    "advice",
    "statementOrAuthnStatementOrAuthzDecisionStatement"
})
public class AssertionType {

    @XmlElement(name = "Issuer", required = true)
    protected NameIDType issuer;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected SignatureType signature;
    @XmlElement(name = "Subject")
    protected SubjectType subject;
    @XmlElement(name = "Conditions")
    protected ConditionsType conditions;
    @XmlElement(name = "Advice")
    protected AdviceType advice;
    @XmlElements({
        @XmlElement(name = "Statement"),
        @XmlElement(name = "AuthnStatement", type = AuthnStatementType.class),
        @XmlElement(name = "AuthzDecisionStatement", type = AuthzDecisionStatementType.class),
        @XmlElement(name = "AttributeStatement", type = AttributeStatementType.class)
    })
    protected List<StatementAbstractType> statementOrAuthnStatementOrAuthzDecisionStatement;
    @XmlAttribute(name = "Version", required = true)
    protected String version;
    @XmlAttribute(name = "ID", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "IssueInstant", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar issueInstant;

    /**
     * issuer 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link NameIDType }
     *     
     */
    public NameIDType getIssuer() {
        return issuer;
    }

    /**
     * issuer 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link NameIDType }
     *     
     */
    public void setIssuer(NameIDType value) {
        this.issuer = value;
    }

    /**
     * signature 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getSignature() {
        return signature;
    }

    /**
     * signature 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setSignature(SignatureType value) {
        this.signature = value;
    }

    /**
     * subject 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link SubjectType }
     *     
     */
    public SubjectType getSubject() {
        return subject;
    }

    /**
     * subject 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link SubjectType }
     *     
     */
    public void setSubject(SubjectType value) {
        this.subject = value;
    }

    /**
     * conditions 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link ConditionsType }
     *     
     */
    public ConditionsType getConditions() {
        return conditions;
    }

    /**
     * conditions 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionsType }
     *     
     */
    public void setConditions(ConditionsType value) {
        this.conditions = value;
    }

    /**
     * advice 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link AdviceType }
     *     
     */
    public AdviceType getAdvice() {
        return advice;
    }

    /**
     * advice 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link AdviceType }
     *     
     */
    public void setAdvice(AdviceType value) {
        this.advice = value;
    }

    /**
     * Gets the value of the statementOrAuthnStatementOrAuthzDecisionStatement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the statementOrAuthnStatementOrAuthzDecisionStatement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStatementOrAuthnStatementOrAuthzDecisionStatement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StatementAbstractType }
     * {@link AuthnStatementType }
     * {@link AuthzDecisionStatementType }
     * {@link AttributeStatementType }
     * 
     * 
     */
    public List<StatementAbstractType> getStatementOrAuthnStatementOrAuthzDecisionStatement() {
        if (statementOrAuthnStatementOrAuthzDecisionStatement == null) {
            statementOrAuthnStatementOrAuthzDecisionStatement = new ArrayList<StatementAbstractType>();
        }
        return this.statementOrAuthnStatementOrAuthzDecisionStatement;
    }

    /**
     * version 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * version 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * id 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * id 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * issueInstant 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIssueInstant() {
        return issueInstant;
    }

    /**
     * issueInstant 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIssueInstant(XMLGregorianCalendar value) {
        this.issueInstant = value;
    }

}
