package com.netmng.websvc.soap.svc.requester;

import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.cxf.headers.Header;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.netmng.util.StrUtil;
import com.netmng.websvc.soap.svc.header.CommonHeaderType;
import com.netmng.websvc.soap.svc.provider.ConnectionProviderPort;
import com.netmng.websvc.soap.svc.provider.ConnectionServiceProvider;

public class ConnectionRequesterHeader2 implements LogicalHandler<LogicalMessageContext>{
	@Override
	public boolean handleMessage(LogicalMessageContext context) {
		LogicalMessage soapMsg = context.getMessage();
		
		Boolean isRequest = (Boolean) context.get(LogicalMessageContext.MESSAGE_OUTBOUND_PROPERTY);  
		
		if(isRequest.booleanValue()){
			try{
				System.out.println("=====>> ConnectionRequesterHeader2.handleMessage() : outbound message");
				System.out.println("=====>> soapMsg.toString()="+soapMsg.toString());

			}catch(Exception e){
				System.err.println(e);
			}
		}else{
			System.out.println("=====>> ConnectionRequesterHeader2.handleMessage() : inbound message");
			System.out.println("=====>> soapMsg.toString()="+soapMsg.toString());
		}
		//continue other handler chain
		return true;
	}
	
	@Override
	public boolean handleFault(LogicalMessageContext context) {
		System.out.println("Server : handleFault()......"); 
		return true;
	}
		
	@Override
	public void close(MessageContext context) {
		System.out.println("Server : close()......");
	}
	
	public Set<QName> getHeaders() {
		System.out.println("Server : getHeaders()......");
		return Collections.emptySet();
	}
	
}
