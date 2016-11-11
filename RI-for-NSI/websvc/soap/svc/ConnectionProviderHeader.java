package com.netmng.websvc.soap.svc;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
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
import java.util.List;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.netmng.util.StrUtil;
import com.netmng.websvc.soap.svc.Provider;
import com.netmng.websvc.soap.svc.header.CommonHeaderType;
import com.netmng.websvc.soap.svc.provider.ConnectionProviderPort;
import com.netmng.websvc.soap.svc.provider.ConnectionServiceProvider;

public class ConnectionProviderHeader implements SOAPHandler<SOAPMessageContext>{
	
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		StrUtil.sysPrint("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxe");
		SOAPMessage soapMsg	= context.getMessage();

		Boolean isRequest = (Boolean)context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		
		if(isRequest.booleanValue()){
			try{
				StrUtil.sysPrint("ConnectionProviderHeader.handleMessage() : outbound message");				
				soapMsg.writeTo(System.out);
				System.out.println("");
				
			} catch (SOAPException ex) {
                Logger.getLogger(ConnectionProviderHeader.class.getName()).log(Level.SEVERE, null, ex);
            /*} catch (JAXBException ex) {
                Logger.getLogger(ConnectionProviderHeader.class.getName()).log(Level.SEVERE, null, ex);*/
            } catch (IOException ex) {
                Logger.getLogger(ConnectionProviderHeader.class.getName()).log(Level.SEVERE, null, ex);
			}
		}else{
			StrUtil.sysPrint("ConnectionProviderHeader.handleMessage() : inbound message");
			try {
            	soapMsg.writeTo(System.out);
				System.out.println("");
            } catch (SOAPException ex) {
                Logger.getLogger(ConnectionProviderHeader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ConnectionProviderHeader.class.getName()).log(Level.SEVERE, null, ex);
            }
		}
		//continue other handler chain
		return true;
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
