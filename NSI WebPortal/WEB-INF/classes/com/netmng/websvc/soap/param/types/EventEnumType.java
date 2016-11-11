
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>EventEnumType에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * <p>
 * <pre>
 * &lt;simpleType name="EventEnumType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="activateFailed"/>
 *     &lt;enumeration value="deactivateFailed"/>
 *     &lt;enumeration value="dataplaneError"/>
 *     &lt;enumeration value="forcedEnd"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EventEnumType")
@XmlEnum
public enum EventEnumType {

    @XmlEnumValue("activateFailed")
    ACTIVATE_FAILED("activateFailed"),
    @XmlEnumValue("deactivateFailed")
    DEACTIVATE_FAILED("deactivateFailed"),
    @XmlEnumValue("dataplaneError")
    DATAPLANE_ERROR("dataplaneError"),
    @XmlEnumValue("forcedEnd")
    FORCED_END("forcedEnd");
    private final String value;

    EventEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EventEnumType fromValue(String v) {
        for (EventEnumType c: EventEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
