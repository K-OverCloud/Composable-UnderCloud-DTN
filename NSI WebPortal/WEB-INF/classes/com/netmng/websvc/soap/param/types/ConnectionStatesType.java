
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 A holder element containing the state machines associated with
 *                 a connection reservation.
 *                 
 *                 Elements:
 *                 
 *                 reservationState - Models the current connection reservation
 *                 state.
 *                 
 *                 provisionState - Models the current connection provisioning
 *                 state.  The provisionState is created for a reservation once
 *                 the reservation is committed.
 *                 
 *                 lifecycleState - Models the current connection lifecycle state.
 *                 
 *                 dataPlaneStatus - Models the current connection data plane
 *                 activation state.
 *             
 * 
 * <p>ConnectionStatesType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="ConnectionStatesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reservationState" type="{http://schemas.ogf.org/nsi/2013/07/connection/types}ReservationStateEnumType"/>
 *         &lt;element name="provisionState" type="{http://schemas.ogf.org/nsi/2013/07/connection/types}ProvisionStateEnumType" minOccurs="0"/>
 *         &lt;element name="lifecycleState" type="{http://schemas.ogf.org/nsi/2013/07/connection/types}LifecycleStateEnumType"/>
 *         &lt;element name="dataPlaneStatus" type="{http://schemas.ogf.org/nsi/2013/07/connection/types}DataPlaneStatusType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConnectionStatesType", propOrder = {
    "reservationState",
    "provisionState",
    "lifecycleState",
    "dataPlaneStatus"
})
public class ConnectionStatesType {

    @XmlElement(required = true)
    protected ReservationStateEnumType reservationState;
    protected ProvisionStateEnumType provisionState;
    @XmlElement(required = true)
    protected LifecycleStateEnumType lifecycleState;
    @XmlElement(required = true)
    protected DataPlaneStatusType dataPlaneStatus;

    /**
     * reservationState 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link ReservationStateEnumType }
     *     
     */
    public ReservationStateEnumType getReservationState() {
        return reservationState;
    }

    /**
     * reservationState 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservationStateEnumType }
     *     
     */
    public void setReservationState(ReservationStateEnumType value) {
        this.reservationState = value;
    }

    /**
     * provisionState 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link ProvisionStateEnumType }
     *     
     */
    public ProvisionStateEnumType getProvisionState() {
        return provisionState;
    }

    /**
     * provisionState 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvisionStateEnumType }
     *     
     */
    public void setProvisionState(ProvisionStateEnumType value) {
        this.provisionState = value;
    }

    /**
     * lifecycleState 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link LifecycleStateEnumType }
     *     
     */
    public LifecycleStateEnumType getLifecycleState() {
        return lifecycleState;
    }

    /**
     * lifecycleState 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link LifecycleStateEnumType }
     *     
     */
    public void setLifecycleState(LifecycleStateEnumType value) {
        this.lifecycleState = value;
    }

    /**
     * dataPlaneStatus 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link DataPlaneStatusType }
     *     
     */
    public DataPlaneStatusType getDataPlaneStatus() {
        return dataPlaneStatus;
    }

    /**
     * dataPlaneStatus 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link DataPlaneStatusType }
     *     
     */
    public void setDataPlaneStatus(DataPlaneStatusType value) {
        this.dataPlaneStatus = value;
    }

}
