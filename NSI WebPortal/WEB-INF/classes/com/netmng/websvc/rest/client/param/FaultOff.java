
package com.netmng.websvc.rest.client.param;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for faultOff complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="faultOff">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="interface" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="resumeResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="router" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "faultOff", propOrder = {
    "_interface",
    "resumeResult",
    "router"
})
public class FaultOff {

    @XmlElement(name = "interface")
    protected List<String> _interface;
    protected boolean resumeResult;
    protected String router;

    /**
     * Gets the value of the interface property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the interface property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInterface().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getInterface() {
        if (_interface == null) {
            _interface = new ArrayList<String>();
        }
        return this._interface;
    }

    /**
     * Gets the value of the resumeResult property.
     * 
     */
    public boolean isResumeResult() {
        return resumeResult;
    }

    /**
     * Sets the value of the resumeResult property.
     * 
     */
    public void setResumeResult(boolean value) {
        this.resumeResult = value;
    }

    /**
     * Gets the value of the router property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRouter() {
        return router;
    }

    /**
     * Sets the value of the router property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRouter(String value) {
        this.router = value;
    }

}
