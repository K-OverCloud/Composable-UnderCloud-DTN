
package com.netmng.websvc.soap.assertion;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.netmng.websvc.soap.result.xmlenc.EncryptedDataType;
import com.netmng.websvc.soap.result.xmlenc.EncryptedKeyType;


/**
 * <p>EncryptedElementType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="EncryptedElementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.w3.org/2001/04/xmlenc#}EncryptedData"/>
 *         &lt;element ref="{http://www.w3.org/2001/04/xmlenc#}EncryptedKey" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EncryptedElementType", propOrder = {
    "encryptedData",
    "encryptedKey"
})
public class EncryptedElementType {

    @XmlElement(name = "EncryptedData", namespace = "http://www.w3.org/2001/04/xmlenc#", required = true)
    protected EncryptedDataType encryptedData;
    @XmlElement(name = "EncryptedKey", namespace = "http://www.w3.org/2001/04/xmlenc#")
    protected List<EncryptedKeyType> encryptedKey;

    /**
     * encryptedData 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link EncryptedDataType }
     *     
     */
    public EncryptedDataType getEncryptedData() {
        return encryptedData;
    }

    /**
     * encryptedData 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link EncryptedDataType }
     *     
     */
    public void setEncryptedData(EncryptedDataType value) {
        this.encryptedData = value;
    }

    /**
     * Gets the value of the encryptedKey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the encryptedKey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEncryptedKey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EncryptedKeyType }
     * 
     * 
     */
    public List<EncryptedKeyType> getEncryptedKey() {
        if (encryptedKey == null) {
            encryptedKey = new ArrayList<EncryptedKeyType>();
        }
        return this.encryptedKey;
    }

}
