
package com.netmng.websvc.rest.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.netmng.websvc.rest.client.param.FaultOff;
import com.netmng.websvc.rest.client.param.FaultOn;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.netmng.websvc.rest.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FaultOff_QNAME = new QName("", "faultOff");
    private final static QName _FaultOn_QNAME = new QName("", "faultOn");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.netmng.websvc.rest.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FaultOff }
     * 
     */
    public FaultOff createFaultOff() {
        return new FaultOff();
    }

    /**
     * Create an instance of {@link FaultOn }
     * 
     */
    public FaultOn createFaultOn() {
        return new FaultOn();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultOff }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "faultOff")
    public JAXBElement<FaultOff> createFaultOff(FaultOff value) {
        return new JAXBElement<FaultOff>(_FaultOff_QNAME, FaultOff.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultOn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "faultOn")
    public JAXBElement<FaultOn> createFaultOn(FaultOn value) {
        return new JAXBElement<FaultOn>(_FaultOn_QNAME, FaultOn.class, null, value);
    }

}
