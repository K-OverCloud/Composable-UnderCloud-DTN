
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ProvisionStateEnumType에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * <p>
 * <pre>
 * &lt;simpleType name="ProvisionStateEnumType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Released"/>
 *     &lt;enumeration value="Provisioning"/>
 *     &lt;enumeration value="Provisioned"/>
 *     &lt;enumeration value="Releasing"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ProvisionStateEnumType")
@XmlEnum
public enum ProvisionStateEnumType {

    @XmlEnumValue("Released")
    RELEASED("Released"),
    @XmlEnumValue("Provisioning")
    PROVISIONING("Provisioning"),
    @XmlEnumValue("Provisioned")
    PROVISIONED("Provisioned"),
    @XmlEnumValue("Releasing")
    RELEASING("Releasing");
    private final String value;

    ProvisionStateEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProvisionStateEnumType fromValue(String v) {
        for (ProvisionStateEnumType c: ProvisionStateEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
