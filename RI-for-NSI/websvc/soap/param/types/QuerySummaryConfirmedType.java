
package com.netmng.websvc.soap.param.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 This is the type definition for the querySummaryConfirmed message
 *                 (both synchronous and asynchronous versions). An NSA sends this
 *                 positive querySummaryRequest response to the NSA that issued the
 *                 original request message.  There can be zero or more results
 *                 retuned in this confirmed message depending on the number of
 *                 matching reservation results.
 * 
 *                 Elements:
 * 
 *                 reservation - Resulting summary set of connection reservations
 *                 matching the query criteria.
 * 
 *                 If there were no matches to the query then no reservation
 *                 elements will be present.
 *             
 * 
 * <p>QuerySummaryConfirmedType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="QuerySummaryConfirmedType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reservation" type="{http://schemas.ogf.org/nsi/2013/07/connection/types}QuerySummaryResultType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuerySummaryConfirmedType", propOrder = {
    "reservation"
})
public class QuerySummaryConfirmedType {

    protected List<QuerySummaryResultType> reservation;

    /**
     * Gets the value of the reservation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reservation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReservation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QuerySummaryResultType }
     * 
     * 
     */
    public List<QuerySummaryResultType> getReservation() {
        if (reservation == null) {
            reservation = new ArrayList<QuerySummaryResultType>();
        }
        return this.reservation;
    }

}
