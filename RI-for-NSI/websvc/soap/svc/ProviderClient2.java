/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.svc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import com.netmng.util.StrUtil;
import com.netmng.websvc.soap.param._interface.ServiceException;
import com.netmng.websvc.soap.svc.header.CommonHeaderType;
import com.netmng.websvc.soap.svc.provider.ConnectionProviderPort;
import com.netmng.websvc.soap.svc.provider.ConnectionServiceProvider;
import com.netmng.websvc.soap.svc.services.point2point.EthernetVlanType;
import com.netmng.websvc.soap.svc.services.types.StpType;
import com.netmng.websvc.soap.param.types.ReservationRequestCriteriaType;
import com.netmng.websvc.soap.param.types.ScheduleType;
/*import com.sun.xml.ws.developer.WSBindingProvider;
import com.sun.xml.ws.api.message.Headers;*/

import org.apache.cxf.headers.Header;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 *
 * @author TSOC
 */
public class ProviderClient2 {

    private static final QName SERVICE_NAME = new QName("http://schemas.ogf.org/nsi/2013/07/connection/provider", "ConnectionServiceProvider");

    /**
     * @param args the command line arguments
     * @throws JAXBException 
     * @throws IOException 
     * @throws ParserConfigurationException 
     * @throws SAXException 
     */
    public static void main(String[] args) throws	JAXBException, 
    												IOException, 
    												ParserConfigurationException, 
    												SAXException {

        URL wsdlURL = ConnectionServiceProvider.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        ConnectionServiceProvider ss = new ConnectionServiceProvider(wsdlURL, SERVICE_NAME);
        ConnectionProviderPort port = ss.getConnectionServiceProviderPort();
        //((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://203.253.235.19:8084/NSI_v2.9.1/ConnectionServiceProvider");
        ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://nsi2.kisti.re.kr/gnos/ConnectionServiceProvider");
        try {
        	/*header*/
        	/*CommonHeaderType cht = new CommonHeaderType();
            String coid = "urn:uuid:" + UUID.randomUUID().toString();
            cht.setCorrelationId(coid);
            cht.setProtocolVersion("http://schemas.ogf.org/nsi/2013/07/connection/provider");
            cht.setProviderNSA("urn:ogf:network:nsa:krlight_prov.test:2012:nsa");
            cht.setRequesterNSA("urn:ogf:network:nsa:krlight_req.test:2012:nsa");
            cht.setReplyTo("http://127.0.0.1:8084/NSI_v2.9.1_Requester/ConnectionServiceRequester");
            cht.setReplyTo("http://112.216.233.234:8787/SC_Requester/ConnectionServiceRequester");
            com.netmng.websvc.soap.svc.header.ObjectFactory chtOF = new com.netmng.websvc.soap.svc.header.ObjectFactory();
            JAXBElement<CommonHeaderType> chtElement = chtOF.createNsiHeader(cht);
            java.io.StringWriter sw = new java.io.StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(CommonHeaderType.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(chtElement, sw);
            System.out.println("=====>> sw.toString()="+sw.toString());
            sw.close();
            InputStream is = new ByteArrayInputStream(sw.toString().getBytes("UTF-8"));
            DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
            Document doc = docBuild.parse(is);
            if(port == null)	System.out.println("=====>> port is null");
            else				System.out.println("=====>> port is not null");
            WSBindingProvider bp = null;
            try{
            	//WSBindingProvider bp = (WSBindingProvider) port;
            	bp = (WSBindingProvider) port;
            }catch(java.lang.ClassCastException cce){
            	cce.printStackTrace();
            }
            //bp.setOutboundHeaders(cht);
            bp.setOutboundHeaders(
                    // simple string value as a header, like <simpleHeader>stringValue</simpleHeader>
                    Headers.create(doc.getDocumentElement())
            );*/  
        	CommonHeaderType cht = new CommonHeaderType();
            String coid = "urn:uuid:" + UUID.randomUUID().toString();
            cht.setCorrelationId(coid);
            cht.setProtocolVersion("http://schemas.ogf.org/nsi/2013/07/connection/provider");
            cht.setProviderNSA("urn:ogf:network:nsa:krlight_prov.test:2012:nsa");
            cht.setRequesterNSA("urn:ogf:network:nsa:krlight_req.test:2012:nsa");
            //cht.setReplyTo("http://127.0.0.1:8084/NSI_v2.9.1_Requester/ConnectionServiceRequester");
            cht.setReplyTo("http://112.216.233.235:8787/SC_Requester/ConnectionServiceRequester");
            //cht.setReplyTo("http://www.netmng.re.kr:8787/SC_Requester/ConnectionServiceRequester");
            com.netmng.websvc.soap.svc.header.ObjectFactory chtOF = new com.netmng.websvc.soap.svc.header.ObjectFactory();
            JAXBElement<CommonHeaderType> chtElement = chtOF.createNsiHeader(cht);
            java.io.StringWriter sw = new java.io.StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(CommonHeaderType.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(chtElement, sw);
            //System.out.println("=====>> sw.toString()="+sw.toString());
            sw.close();
            InputStream is = new ByteArrayInputStream(sw.toString().getBytes("UTF-8"));
            DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
            Document doc = docBuild.parse(is);
            /*if(port == null)	System.out.println("=====>> port is null");
            else				System.out.println("=====>> port is not null");*/
            
            Header header = new Header(new QName("http://schemas.ogf.org/nsi/2013/07/framework/headers"), doc.getDocumentElement());
            List<Header> headers = new ArrayList<Header>();
            headers.add(header);
            BindingProvider bp = (BindingProvider) port;
            //BindingProvider.class.cast(service).getRequestContext().put(Header.HEADER_LIST, headers);
            bp.getRequestContext().put(Header.HEADER_LIST, headers);
            
            /*//WSBindingProvider bp = null;
            BindingProvider bp = null;
            try{
            	//WSBindingProvider bp = (WSBindingProvider) port;
            	//bp = (WSBindingProvider) port;
            	bp = (BindingProvider) port;
            }catch(java.lang.ClassCastException cce){
            	cce.printStackTrace();
            }
            //bp.setOutboundHeaders(cht);
            //bp.getRequestContext().put("header", doc.getDocumentElement());
            bp.getRequestContext().put("header", cht);*/
            
            /*bp.setOutboundHeaders(
                    // simple string value as a header, like <simpleHeader>stringValue</simpleHeader>
                    Headers.create(doc.getDocumentElement())            		
            );*/
        	
        	
        	
        	/*connectionId*/
        	Holder cid = new Holder("urn:uuid:" + UUID.randomUUID().toString());
        	
        	/*globalReservationId*/
            String gid = "urn:uuid:" + UUID.randomUUID().toString();
            
        	/*description*/
            String description = "TEST REQUESTER!";
            
        	/*critieria*/            
            ReservationRequestCriteriaType criteria = new ReservationRequestCriteriaType();
            criteria.setVersion(0);
            ScheduleType schedule = new ScheduleType();
            try {
                Date startTime = new Date();
                GregorianCalendar startTimeGregorian = new GregorianCalendar();
                startTimeGregorian.setTime(startTime);
                XMLGregorianCalendar startTimeXMLGregorian = DatatypeFactory.newInstance().newXMLGregorianCalendar(startTimeGregorian);
                Duration sDuration = DatatypeFactory.newInstance().newDuration(true, 0, 0, 0, 0, 0, 0);
                startTimeXMLGregorian.add(sDuration);
                schedule.setStartTime(startTimeXMLGregorian);
                
                Date endTime = new Date();
                GregorianCalendar endTimeGregorian = new GregorianCalendar();
                endTimeGregorian.setTime(endTime);
                XMLGregorianCalendar endTimeXMLGregorian = DatatypeFactory.newInstance().newXMLGregorianCalendar(endTimeGregorian);
                Duration dDuration = DatatypeFactory.newInstance().newDuration(true, 0, 0, 0, 1, 0, 0);
                endTimeXMLGregorian.add(dDuration);
                schedule.setEndTime(endTimeXMLGregorian);
            } catch (DatatypeConfigurationException ex) {
            	System.out.println("=====>> DatatypeConfigurationException : ");
            	ex.printStackTrace();
                /*Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);*/
            }
            criteria.setSchedule(schedule);
            criteria.setServiceType("http://services.ogf.org/nsi/2013/07/descriptions/EVTS.A-GOLE");
            criteria.setVersion(0);
            
            
            
            EthernetVlanType evt = new EthernetVlanType();
            evt.setCapacity(200);
	            StpType sourceSTP = new StpType();
	            sourceSTP.setNetworkId("urn:ogf:network:dj.test:2013:topologyvv");
	            sourceSTP.setLocalId("urn:ogf:network:dj.test:2013:p0");
				sourceSTP.setLabels(null);
            evt.setSourceSTP(sourceSTP);
            evt.setEro(null);
				StpType destSTP = new StpType();
				destSTP.setNetworkId("urn:ogf:network:dj.test:2013:topology");
				destSTP.setLocalId("urn:ogf:network:dj.test:2013:p1");
				destSTP.setLabels(null);
			evt.setDestSTP(destSTP);
			evt.setMtu(null);
			evt.setBurstsize(null);
			evt.setSourceVLAN(1780);
            evt.setDestVLAN(1780);
            com.netmng.websvc.soap.svc.services.point2point.ObjectFactory of = new com.netmng.websvc.soap.svc.services.point2point.ObjectFactory();
            JAXBElement<EthernetVlanType> evts = of.createEvts(evt);
            
            /*criteria.getAny().add(evt);*/																
            criteria.getAny().add(evts);
    		//criteria.setVersion(null);
            
        	System.out.println("=====>> port.reserve() start");
            port.reserve(null, gid, description, criteria);
        	System.out.println("=====>> port.reserve() end");
        	
        	/*System.out.println("=====>> port.reserveCommit() start");
        	port.reserveCommit("urn:uuid:86dcc3ad-702d-4c31-8a88-186922a3492c");
        	System.out.println("=====>> port.reserveCommit() end");*/

        } catch (ServiceException ex) {
        //} catch (Exception ex) {
        	ex.printStackTrace();
            Logger.getLogger(ProviderClient2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
