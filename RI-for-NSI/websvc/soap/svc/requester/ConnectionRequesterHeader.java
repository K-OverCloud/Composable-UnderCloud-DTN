package com.netmng.websvc.soap.svc.requester;

import javax.servlet.ServletConfig;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMResult;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.cxf.headers.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.netmng.svc.nsa.NsaService;
import com.netmng.util.PropUtil;
import com.netmng.util.StrUtil;
import com.netmng.websvc.soap.svc.Provider;
import com.netmng.websvc.soap.svc.header.CommonHeaderType;
import com.netmng.websvc.soap.svc.provider.ConnectionProviderPort;
import com.netmng.websvc.soap.svc.provider.ConnectionServiceProvider;

public class ConnectionRequesterHeader implements SOAPHandler<SOAPMessageContext>{
	
	/*@Autowired(required=true) 
	private NsaService nsaService;*/
	private static String statCorrelationId	= "";
	private static String statProviderNSA	= "";
	private static String statReplyTo		= "";
	
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		SOAPMessage soapMsg	= context.getMessage();

		Boolean isRequest = (Boolean)context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		
		if(isRequest.booleanValue()){
			try{
				StrUtil.sysPrint("ConnectionRequesterHeader.handleMessage() : outbound message");
				soapMsg.writeTo(System.out);
				System.out.println("");
				
			    SOAPEnvelope soapEnv	= soapMsg.getSOAPPart().getEnvelope();
		        SOAPHeader soapHeader	= soapEnv.getHeader();		   
				
				//if no header, add one
		        if (soapHeader == null){
		            StrUtil.sysPrint("ConnectionRequesterHeader.handleMessage(): No SOAP header -> new header create.");
		            StrUtil.sysPrint("statCorrelationId="+statCorrelationId);
		            StrUtil.sysPrint("statProviderNSA="+statProviderNSA);
		            
		        	soapHeader = soapEnv.addHeader();
		        	soapHeader.setPrefix("S");
		        	
		        	Provider provider = new Provider();
		        	JAXBElement<CommonHeaderType> chtElement = provider.getJAXBElementCommonHeaderType(statCorrelationId, statProviderNSA, "");
		        	//JAXBElement<CommonHeaderType> chtElement = provider.getJAXBElementCommonHeaderType(this.statCorrelationId, this.statProviderNSA, this.statReplyTo);
		        	
		        	DOMResult res = new DOMResult();
		        	Marshaller marshaller = JAXBContext.newInstance(CommonHeaderType.class).createMarshaller();
	                marshaller.marshal(chtElement, res);
	                Element domElement = ((Document) res.getNode()).getDocumentElement();
	                SOAPFactory factory = SOAPFactory.newInstance();
	                SOAPElement se = factory.createElement(domElement);
	                soapHeader.addChildElement(se);
			     }else{
			    	 StrUtil.sysPrint("ConnectionRequesterHeader.handleMessage(): Is SOAP header.");
			     }
			    soapMsg.saveChanges();
			    soapMsg.writeTo(System.out);
				System.out.println("");
			} catch (SOAPException ex) {
                Logger.getLogger(ConnectionRequesterHeader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JAXBException ex) {
                Logger.getLogger(ConnectionRequesterHeader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ConnectionRequesterHeader.class.getName()).log(Level.SEVERE, null, ex);
			}
		}else{
			StrUtil.sysPrint("ConnectionRequesterHeader.handleMessage() : inbound message");
			
			try {
            	soapMsg.writeTo(System.out);
				System.out.println("");
				
				SOAPEnvelope soapEnv	= soapMsg.getSOAPPart().getEnvelope();
		        SOAPHeader soapHeader	= soapEnv.getHeader();
		        SOAPBody soapBody		= soapEnv.getBody();
		        
		        String sProcNm = this.getProcNm(soapEnv);
		        StrUtil.sysPrint("sProcNm="+sProcNm);
		        if(sProcNm!=null && sProcNm.equals("reserveConfirmed")){
		        	String sCorrelationId		= this.getHeaderElementTxtCont(soapEnv, "correlationId");
		        	String sProviderNSA			= this.getHeaderElementTxtCont(soapEnv, "providerNSA");
		        	String sConnectionId		= this.getBodyElementTxtCont(soapEnv, "connectionId");
		        	String sGlobalReservationId	= this.getBodyElementTxtCont(soapEnv, "globalReservationId");
			        StrUtil.sysPrint("sCorrelationId="+sCorrelationId);
			        StrUtil.sysPrint("sConnectionId="+sConnectionId);
			        StrUtil.sysPrint("sGlobalReservationId="+sGlobalReservationId);
			        
			        Map<String,String> m = new HashMap<String,String>();
			        m.put("correlationId", sCorrelationId);
			        m.put("connectionId", sConnectionId);
			        m.put("globalReservationId", sGlobalReservationId);
			        
			        ApplicationContext appContext = new ClassPathXmlApplicationContext("config/spring/spring-app.xml"); 
			        com.netmng.svc.nsa.NsaService nsaService = (com.netmng.svc.nsa.NsaService) appContext.getBean("nsaService"); 
			        nsaService.updateReserveConnId(m); 
			        
			        this.statCorrelationId	= sCorrelationId;
			        this.statProviderNSA	= sProviderNSA;
		        }else if(	sProcNm!=null && 
		        			(sProcNm.equals("querySummary") || sProcNm.equals("querySummarySync"))	){
		        	String sCorrelationId		= this.getHeaderElementTxtCont(soapEnv, "correlationId");
		        	String sProviderNSA			= this.getHeaderElementTxtCont(soapEnv, "providerNSA");
		        	String sReplyTo				= this.getHeaderElementTxtCont(soapEnv, "replyTo");
			        StrUtil.sysPrint("sCorrelationId="+sCorrelationId);
			        
			        PropUtil.setGlobalVal("correlationId", sCorrelationId);
			        PropUtil.setGlobalVal("providerNSA", sProviderNSA);
			        PropUtil.setGlobalVal("replyTo", sReplyTo);
			        
			        /*this.statCorrelationId	= sCorrelationId;
			        this.statProviderNSA	= sProviderNSA;
			        this.statReplyTo		= sReplyTo;*/
		        }else{
		        	this.statCorrelationId	= "";
			        this.statProviderNSA	= "";
			        this.statReplyTo		= "";
		        }
            } catch (SOAPException ex) {
                Logger.getLogger(ConnectionRequesterHeader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ConnectionRequesterHeader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
            	ex.printStackTrace();
                Logger.getLogger(ConnectionRequesterHeader.class.getName()).log(Level.SEVERE, null, ex);
            }
		}
		//continue other handler chain
		return true;
	}
	
	public String getProcNm(SOAPEnvelope soapEnv){
		try{
			SOAPBody soapBody = soapEnv.getBody();        
	        if (soapBody != null){
	        	Iterator it = soapBody.getChildElements();
	        	while(it.hasNext()){
	        		SOAPBodyElement bd = (SOAPBodyElement)it.next();
	        		return bd.getNodeName().split(":")[1];
	        	}
	        }
	        return null;
		} catch (SOAPException ex) {
            Logger.getLogger(ConnectionRequesterHeader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
		}
	}
	
	public String getHeaderElementTxtCont(SOAPEnvelope soapEnv, String nodeNm){
		try{
			SOAPHeader soapHeader = soapEnv.getHeader();     
	        if (soapHeader != null){
	        	Iterator it = soapHeader.getChildElements();
	        	while(it.hasNext()){
	        		SOAPHeaderElement he	= (SOAPHeaderElement)it.next();
	        		NodeList nodeList		= he.getElementsByTagName(nodeNm);
	        		Node node				= nodeList.item(0);
	        		return node.getTextContent();
	        	}
	        }
	        return null;
		} catch (SOAPException ex) {
            Logger.getLogger(ConnectionRequesterHeader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
		}
	}
	
	public String getBodyElementTxtCont(SOAPEnvelope soapEnv, String nodeNm){
		try{
			SOAPBody soapBody = soapEnv.getBody();       
	        if (soapBody != null){
	        	Iterator it = soapBody.getChildElements();
	        	while(it.hasNext()){
	        		SOAPBodyElement bd	= (SOAPBodyElement)it.next();
	        		NodeList nodeList	= bd.getElementsByTagName(nodeNm);
	        		Node node			= nodeList.item(0);
	        		return node.getTextContent();
	        	}
	        }
	        return null;
		} catch (SOAPException ex) {
            Logger.getLogger(ConnectionRequesterHeader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
		}
	}
	
	@Override
	public boolean handleFault(SOAPMessageContext context) {
		System.out.println("Server : handleFault()......"); 
		return true;
	}
	
	@Override
	public void close(MessageContext context) {
		System.out.println("Server : close()......");
	}
	
	@Override
	public Set<QName> getHeaders() {
		System.out.println("Server : getHeaders()......");
		return null;
	}
}
