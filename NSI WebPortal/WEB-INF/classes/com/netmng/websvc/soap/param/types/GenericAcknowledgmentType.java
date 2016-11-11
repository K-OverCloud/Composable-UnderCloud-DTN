
package com.netmng.websvc.soap.param.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Common acknowledgment message type.
 *                 
 *                 Elements:
 *                 We have moved the correlationId to the header so this is
 *                 now an empty response.
 *                 
 *                 Notes on acknowledgment:
 *                 Depending on NSA implementation and thread timing an
 *                 acknowledgment to a request operation may be returned
 *                 after the confirm/fail for the request has been returned
 *                 to the Requesting NSA.
 *                 
 *                 For protocol robustness, Requesting NSA should be
 *                 able to accept confirm/fail before acknowledgment.
 *             
 * 
 * <p>GenericAcknowledgmentType complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="GenericAcknowledgmentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenericAcknowledgmentType")
public class GenericAcknowledgmentType {


}
