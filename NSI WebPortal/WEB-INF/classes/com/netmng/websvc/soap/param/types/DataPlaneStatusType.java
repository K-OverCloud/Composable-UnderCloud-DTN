
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Models the current connection activation state within the
 *                 data plane.
 * 
 *                 Elements:
 *                 
 *                 active - True if the dataplane is active.  For an aggregator,
 *                 this flag is true when data plane is activated in all
 *                 participating children.
 *                 
 *                 version - Version of the connection reservation this entry is
 *                 modeling.
 *                 
 *                 versionConsistent - Always true for uPA. For an aggregator,
 *                 if version numbers of all children are the same. This flag is
 *                 true. This field is valid when Active is true.
 *             
 * 
 * <p>DataPlaneStatusType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="DataPlaneStatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="versionConsistent" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataPlaneStatusType", propOrder = {
    "active",
    "version",
    "versionConsistent"
})
public class DataPlaneStatusType {

    protected boolean active;
    protected int version;
    protected boolean versionConsistent;

    /**
     * active 속성의 값을 가져옵니다.
     * 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * active 속성의 값을 설정합니다.
     * 
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    /**
     * version 속성의 값을 가져옵니다.
     * 
     */
    public int getVersion() {
        return version;
    }

    /**
     * version 속성의 값을 설정합니다.
     * 
     */
    public void setVersion(int value) {
        this.version = value;
    }

    /**
     * versionConsistent 속성의 값을 가져옵니다.
     * 
     */
    public boolean isVersionConsistent() {
        return versionConsistent;
    }

    /**
     * versionConsistent 속성의 값을 설정합니다.
     * 
     */
    public void setVersionConsistent(boolean value) {
        this.versionConsistent = value;
    }

}
