
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>LifecycleStateEnumType에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * <p>
 * <pre>
 * &lt;simpleType name="LifecycleStateEnumType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Created"/>
 *     &lt;enumeration value="Failed"/>
 *     &lt;enumeration value="PassedEndTime"/>
 *     &lt;enumeration value="Terminating"/>
 *     &lt;enumeration value="Terminated"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LifecycleStateEnumType")
@XmlEnum
public enum LifecycleStateEnumType {

    @XmlEnumValue("Created")
    CREATED("Created"),
    @XmlEnumValue("Failed")
    FAILED("Failed"),
    @XmlEnumValue("PassedEndTime")
    PASSED_END_TIME("PassedEndTime"),
    @XmlEnumValue("Terminating")
    TERMINATING("Terminating"),
    @XmlEnumValue("Terminated")
    TERMINATED("Terminated");
    private final String value;

    LifecycleStateEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LifecycleStateEnumType fromValue(String v) {
        for (LifecycleStateEnumType c: LifecycleStateEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
