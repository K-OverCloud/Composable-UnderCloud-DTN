
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ReservationStateEnumType에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * <p>
 * <pre>
 * &lt;simpleType name="ReservationStateEnumType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ReserveStart"/>
 *     &lt;enumeration value="ReserveChecking"/>
 *     &lt;enumeration value="ReserveFailed"/>
 *     &lt;enumeration value="ReserveAborting"/>
 *     &lt;enumeration value="ReserveHeld"/>
 *     &lt;enumeration value="ReserveCommitting"/>
 *     &lt;enumeration value="ReserveTimeout"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReservationStateEnumType")
@XmlEnum
public enum ReservationStateEnumType {

    @XmlEnumValue("ReserveStart")
    RESERVE_START("ReserveStart"),
    @XmlEnumValue("ReserveChecking")
    RESERVE_CHECKING("ReserveChecking"),
    @XmlEnumValue("ReserveFailed")
    RESERVE_FAILED("ReserveFailed"),
    @XmlEnumValue("ReserveAborting")
    RESERVE_ABORTING("ReserveAborting"),
    @XmlEnumValue("ReserveHeld")
    RESERVE_HELD("ReserveHeld"),
    @XmlEnumValue("ReserveCommitting")
    RESERVE_COMMITTING("ReserveCommitting"),
    @XmlEnumValue("ReserveTimeout")
    RESERVE_TIMEOUT("ReserveTimeout");
    private final String value;

    ReservationStateEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReservationStateEnumType fromValue(String v) {
        for (ReservationStateEnumType c: ReservationStateEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
