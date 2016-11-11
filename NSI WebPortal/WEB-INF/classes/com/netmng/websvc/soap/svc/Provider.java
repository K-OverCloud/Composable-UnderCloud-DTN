package com.netmng.websvc.soap.svc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.apache.cxf.headers.Header;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.netmng.util.PropUtil;
import com.netmng.util.StrUtil;
import com.netmng.websvc.soap.assertion.AttributeStatementType;
import com.netmng.websvc.soap.param.types.GenericAcknowledgmentType;
import com.netmng.websvc.soap.param.types.QueryNotificationConfirmedType;
import com.netmng.websvc.soap.param.types.QueryNotificationType;
import com.netmng.websvc.soap.param.types.QueryRecursiveResultType;
import com.netmng.websvc.soap.param.types.QuerySummaryConfirmedType;
import com.netmng.websvc.soap.param.types.QuerySummaryResultType;
import com.netmng.websvc.soap.param.types.QueryType;
import com.netmng.websvc.soap.param.types.ReservationRequestCriteriaType;
import com.netmng.websvc.soap.param.types.ScheduleType; 
import com.netmng.websvc.soap.svc.header.CommonHeaderType;
import com.netmng.websvc.soap.svc.param.provider.Provision;
import com.netmng.websvc.soap.svc.param.provider.Query;
import com.netmng.websvc.soap.svc.param.provider.QueryConfirmed;
import com.netmng.websvc.soap.svc.param.provider.QueryFailed;
import com.netmng.websvc.soap.svc.param.provider.Release;
import com.netmng.websvc.soap.svc.param.provider.Reserve;
import com.netmng.websvc.soap.svc.param.provider.Terminate;
import com.netmng.websvc.soap.svc.requester.ConnectionRequesterPort;
import com.netmng.websvc.soap.svc.requester.ConnectionServiceRequester;
import com.netmng.websvc.soap.svc.services.point2point.EthernetVlanType;
import com.netmng.websvc.soap.svc.services.types.StpType;
import com.netmng.websvc.soap.svc.types.ServiceExceptionType;
import com.netmng.websvc.soap.svc.provider.ConnectionProviderPort;
import com.netmng.websvc.soap.svc.provider.ConnectionServiceProvider;
import com.netmng.websvc.soap.param._interface.QueryNotificationSyncFailed;
import com.netmng.websvc.soap.param._interface.QuerySummarySyncFailed;
import com.netmng.websvc.soap.param._interface.ServiceException;

/*@HandlerChain(file="handler_chain_provider.xml")*/
public class Provider {

	private static final QName SERVICE_NAME = new QName("http://schemas.ogf.org/nsi/2013/07/connection/provider", "ConnectionServiceProvider");
	private static final QName SERVICE_NAME_R = new QName("http://schemas.ogf.org/nsi/2013/07/connection/requester", "ConnectionServiceRequester");
	private ConnectionProviderPort port = null;
	private ConnectionRequesterPort portR = null;
			
	public Provider(){
		try{
			this.init();
		}catch(JAXBException e){
			StrUtil.sysPrint("Provider.constructor : JAXBException");
			e.printStackTrace();
		}catch(IOException e){
			StrUtil.sysPrint("Provider.constructor : IOException");
			e.printStackTrace();
		}catch(ParserConfigurationException e){
			StrUtil.sysPrint("Provider.constructor : ParserConfigurationException");
			e.printStackTrace();
		}catch(SAXException e){
			StrUtil.sysPrint("Provider.constructor : SAXException");			
			e.printStackTrace();
		}
	}
	
	public Provider(String sCorrelationId, String sProviderNSA, String sReplyTo, String sEndPointAddr){
		try{
			this.init(sCorrelationId, sProviderNSA, sReplyTo, sEndPointAddr);
		}catch(JAXBException e){
			StrUtil.sysPrint("Provider.constructor : JAXBException");
			e.printStackTrace();
		}catch(IOException e){
			StrUtil.sysPrint("Provider.constructor : IOException");
			e.printStackTrace();
		}catch(ParserConfigurationException e){
			StrUtil.sysPrint("Provider.constructor : ParserConfigurationException");
			e.printStackTrace();
		}catch(SAXException e){
			StrUtil.sysPrint("Provider.constructor : SAXException");			
			e.printStackTrace();
		}
	}
	
	public Provider(String sCorrelationId, String sProviderNSA, String sReplyTo, String sEndPointAddr, String sType){
		try{
			this.init(sCorrelationId, sProviderNSA, sReplyTo, sEndPointAddr, sType);
		}catch(JAXBException e){
			StrUtil.sysPrint("Provider.constructor : JAXBException");
			e.printStackTrace();
		}catch(IOException e){
			StrUtil.sysPrint("Provider.constructor : IOException");
			e.printStackTrace();
		}catch(ParserConfigurationException e){
			StrUtil.sysPrint("Provider.constructor : ParserConfigurationException");
			e.printStackTrace();
		}catch(SAXException e){
			StrUtil.sysPrint("Provider.constructor : SAXException");			
			e.printStackTrace();
		}
	}
	
	public ConnectionProviderPort getConnectionProviderPort(){
		URL wsdlURL = ConnectionServiceProvider.WSDL_LOCATION;
        ConnectionServiceProvider ss = new ConnectionServiceProvider(wsdlURL, SERVICE_NAME);
        ConnectionProviderPort port = ss.getConnectionServiceProviderPort();
        ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://nsi2.kisti.re.kr/ConnectionServiceProvider");
        return port;
	}
	
	public ConnectionProviderPort getConnectionProviderPort(String sEndPointAddr){
		URL wsdlURL = ConnectionServiceProvider.WSDL_LOCATION;
        ConnectionServiceProvider ss = new ConnectionServiceProvider(wsdlURL, SERVICE_NAME);
        ConnectionProviderPort port = ss.getConnectionServiceProviderPort();
        ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, sEndPointAddr);
        return port;
	}
	
	public ConnectionRequesterPort getConnectionRequesterPort(String sEndPointAddr){
		URL wsdlURL = ConnectionServiceRequester.WSDL_LOCATION;
        ConnectionServiceRequester ss = new ConnectionServiceRequester(wsdlURL, SERVICE_NAME_R);
        ConnectionRequesterPort port = ss.getConnectionServiceRequesterPort();
        ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, sEndPointAddr);
        return port;
	}
	
	public void setHeader() throws	JAXBException, 
									IOException, 
									ParserConfigurationException, 
									SAXException {		
		this.setHeader("", "", "", "provider");
	}
	
	public void setHeader(String sCorrelationId, String sProviderNSA, String sReplyTo, String sType) throws	JAXBException, 
																								IOException, 
																								ParserConfigurationException, 
																								SAXException {
		JAXBElement<CommonHeaderType> chtElement = this.getJAXBElementCommonHeaderType(sCorrelationId, sProviderNSA, sReplyTo);
		
		java.io.StringWriter sw = new java.io.StringWriter();
		JAXBContext jaxbContext = JAXBContext.newInstance(CommonHeaderType.class);
		
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(chtElement, sw);
		sw.close();
		InputStream is = new ByteArrayInputStream(sw.toString().getBytes("UTF-8"));
		DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
		Document doc = docBuild.parse(is);
		
		Header header = new Header(new QName("http://schemas.ogf.org/nsi/2013/07/framework/headers"), doc.getDocumentElement());
		List<Header> headers = new ArrayList<Header>();
		headers.add(header);
		BindingProvider bp = null;
		if(sType!=null && sType.equals("provider")){
			bp = (BindingProvider)this.port;
		}else if(sType!=null && sType.equals("requester")){
			bp = (BindingProvider)this.portR;
		}else{
			bp = (BindingProvider)this.port;
		}
		bp.getRequestContext().put(Header.HEADER_LIST, headers);
	}
	
	public JAXBElement<CommonHeaderType> getJAXBElementCommonHeaderType(){
		return this.getJAXBElementCommonHeaderType("", "", "");
	}
	
	public JAXBElement<CommonHeaderType> getJAXBElementCommonHeaderType(String sCorrelationId, String sProviderNSA, String sReplyTo){
		CommonHeaderType cht = new CommonHeaderType();
		cht.setProtocolVersion("http://schemas.ogf.org/nsi/2013/07/connection/provider");
        cht.setRequesterNSA("urn:ogf:network:nsa:krlight_req.test:2012:nsa");
        
		if(sCorrelationId!=null && !sCorrelationId.equals(""))	cht.setCorrelationId(sCorrelationId);
		else													cht.setCorrelationId("urn:uuid:" + UUID.randomUUID().toString());
		StrUtil.sysPrint("Header CorrelationId="+cht.getCorrelationId());
		
		if(sProviderNSA!=null && !sProviderNSA.equals(""))	cht.setProviderNSA(sProviderNSA);
		else												cht.setProviderNSA("urn:ogf:network:krlight.net:2013:nsa");
		StrUtil.sysPrint("Header ProviderNSA="+cht.getProviderNSA());

		if(sReplyTo!=null && !sReplyTo.equals(""))	cht.setReplyTo(sReplyTo);
		else{										//cht.setReplyTo("http://112.216.233.235:8787/SC_Requester/ConnectionServiceRequester");
													cht.setReplyTo((new PropUtil()).getProp("nsa.replyTo"));
		}
		StrUtil.sysPrint("Header ReplyTo="+cht.getReplyTo());

        com.netmng.websvc.soap.svc.header.ObjectFactory chtOF = new com.netmng.websvc.soap.svc.header.ObjectFactory();
        JAXBElement<CommonHeaderType> chtElement = chtOF.createNsiHeader(cht);
        return chtElement;
	}
	
	public void init() throws	JAXBException, 
								IOException, 
								ParserConfigurationException, 
								SAXException {
		this.init("", "", "", "");
	}
	
	public void init(String sCorrelationId, String sProviderNSA, String sReplyTo, String sEndPointAddr) throws	JAXBException, 
																												IOException, 
																												ParserConfigurationException, 
																												SAXException {
		this.port = this.getConnectionProviderPort(sEndPointAddr);
		this.setHeader(sCorrelationId, sProviderNSA, sReplyTo, "provider");
	}
	
	public void init(String sCorrelationId, String sProviderNSA, String sReplyTo, String sEndPointAddr, String sType) throws	JAXBException, 
																																IOException, 
																																ParserConfigurationException, 
																																SAXException {
		if(sType!=null && sType.equals("provider")){	
			this.port = this.getConnectionProviderPort(sEndPointAddr);
		}else if(sType!=null && sType.equals("requester")){	
			this.portR = this.getConnectionRequesterPort(sEndPointAddr);			
		}else{
			this.port = this.getConnectionProviderPort(sEndPointAddr);			
		}
		//this.port = this.getConnectionProviderPort(sEndPointAddr);
		this.setHeader(sCorrelationId, sProviderNSA, sReplyTo, sType);
	}
	
	
	/*******************************/
	/*reserve function for provider*/
	/*******************************/
	
	/*reserve*/
	public void reserve(	javax.xml.ws.Holder<String> connectionId,
							String globalReservationId,
						    String description,
						    ReservationRequestCriteriaType criteria
						) throws ServiceException {
		StrUtil.sysPrint("Provider.reserve():connectionId="+connectionId);		
		StrUtil.sysPrint("Provider.reserve():globalReservationId="+globalReservationId);		
		StrUtil.sysPrint("Provider.reserve():description="+description);		
		this.port.reserve(connectionId, globalReservationId, description, criteria);
	}
	
	public void reserveCommit(String connectionId) throws ServiceException {
		StrUtil.sysPrint("Provider.reserveCommit():connectionId="+connectionId);		
		this.port.reserveCommit(connectionId);
	}
	
	public void reserveAbort(String connectionId) throws ServiceException {
		StrUtil.sysPrint("Provider.reserveAbort():connectionId="+connectionId);		
		this.port.reserveAbort(connectionId);
	}
	
	/*provision*/
	public void provision(java.lang.String connectionId) throws ServiceException {
		StrUtil.sysPrint("Provider.provision():connectionId="+connectionId);		
		this.port.provision(connectionId);
	}
	
	public void release(String connectionId) throws ServiceException {
		StrUtil.sysPrint("Provider.release():connectionId="+connectionId);		
		this.port.release(connectionId);
	}
	
	/*terminate*/
	public void terminate(String connectionId) throws ServiceException {
		StrUtil.sysPrint("Provider.terminate():connectionId="+connectionId);		
		this.port.terminate(connectionId);
	}
	
	
	/*****************************/
	/*query function for provider*/
	/*****************************/
	
	/*querySummary*/
	public GenericAcknowledgmentType querySummary(QueryType querySummary) throws ServiceException {
		StrUtil.sysPrint("connectionId size = " + querySummary.getConnectionId().size());
		StrUtil.sysPrint("globalReservationId size = " + querySummary.getGlobalReservationId().size());
		return this.port.querySummary(querySummary);
	}
	
	public QuerySummaryConfirmedType querySummarySync(QueryType querySummarySync) throws QuerySummarySyncFailed {
		System.out.println("=====>> Porvider.querySummarySync()");
		return this.port.querySummarySync(querySummarySync);
	}
	
	/*queryRecursive*/
	public GenericAcknowledgmentType queryRecursive(QueryType queryRecursive) throws ServiceException {
		System.out.println("=====>> Porvider.queryRecursive()");
		return this.port.queryRecursive(queryRecursive);
	}
	
	/*queryNotification*/	
	public void queryNotification(	String connectionId,
									Integer startNotificationId,
									Integer endNotificationId
								) throws ServiceException {
		StrUtil.sysPrint("Provider.queryNotification():connectionId="+connectionId);		
		StrUtil.sysPrint("Provider.queryNotification():startNotificationId="+startNotificationId);		
		StrUtil.sysPrint("Provider.queryNotification():endNotificationId="+endNotificationId);		
		this.port.queryNotification(connectionId, startNotificationId, endNotificationId);
	}
	
	public QueryNotificationConfirmedType queryNotificationSync(QueryNotificationType queryNotificationSync) throws QueryNotificationSyncFailed {
		return this.port.queryNotificationSync(queryNotificationSync);
	}
	
	
	/******************************/
	/*query function for requester*/
	/******************************/
	
	/*querySummary*/
	public void querySummaryConfirmed(List<QuerySummaryResultType> param) throws ServiceException {
		StrUtil.sysPrint("Provider.querySummaryConfirmed()");	
		this.portR.querySummaryConfirmed(param);
	}
	
	public void querySummaryFailed(ServiceExceptionType serviceException) throws ServiceException{
		StrUtil.sysPrint("Provider.querySummaryFailed()");	
		this.portR.querySummaryFailed(serviceException);
	}
	
	/*queryRecursive*/
	public void queryRecursiveConfirmed(List<QueryRecursiveResultType> param) throws ServiceException {
		StrUtil.sysPrint("Provider.queryRecursiveConfirmed()");	
		this.portR.queryRecursiveConfirmed(param);
	}
	
	public void queryRecursiveFailed(ServiceExceptionType serviceException) throws ServiceException{
		StrUtil.sysPrint("Provider.queryRecursiveFailed()");	
		this.portR.queryRecursiveFailed(serviceException);
	}
	
	/*queryNotification*/
	public GenericAcknowledgmentType queryNotificationConfirmed(QueryNotificationConfirmedType param) throws ServiceException {
		StrUtil.sysPrint("Provider.queryNotificationConfirmed()");	
		return this.portR.queryNotificationConfirmed(param);
	}
	
	public void queryNotificationFailed(ServiceExceptionType serviceException) throws ServiceException{
		StrUtil.sysPrint("Provider.queryNotificationFailed()");	
		this.portR.queryNotificationFailed(serviceException);
	}
	
	public ServiceExceptionType getServExcept(Exception e, String sNsaId, String sConnectionId, String sServiceType){
		StrUtil.sysPrint("Provider.getServExcept()");	
		StrUtil.sysPrint("Provider.getServExcept() : setText() = "+e.getMessage());
		
		ServiceExceptionType serviceException = new ServiceExceptionType();
		serviceException.setNsaId(sNsaId);
		serviceException.setConnectionId(sConnectionId);
		serviceException.setServiceType(sServiceType);
		serviceException.setErrorId("");
		serviceException.setText(e.getMessage());
		
		return serviceException;
	}
	
	
	
	
	
	
	
	
	//private QName service_name = new QName("http://schemas.ogf.org/nsi/2011/10/connection/provider", "ConnectionServiceProvider");
	private QName service_name = new QName("http://schemas.ogf.org/nsi/2013/07/connection/provider", "ConnectionServiceProvider");
	private URL wsdlURL = ConnectionServiceProvider.WSDL_LOCATION;
	//private URL wsdlURL = ConnectionServiceProvider.CONNECTIONSERVICEPROVIDER_WSDL_LOCATION;		

	public String reserve(Reserve param) throws ServiceException {
		System.out.println("=====>>1");
		String resultCorrelationId = null;
		System.out.println("=====>>a");
		
		ConnectionServiceProvider ss = new ConnectionServiceProvider(this.wsdlURL, this.service_name);
		System.out.println("=====>>b");
        ConnectionProviderPort port = ss.getConnectionServiceProviderPort(); 
		System.out.println("=====>>2");
        
        /*v 2.0*/
        CommonHeaderType header = new CommonHeaderType();									/* 1.header*/
        header.setProtocolVersion("http://schemas.ogf.org/nsi/2013/07/connection/provider");	/* 1.1.protocolVersion*/
        header.setCorrelationId(param.getCorrelationId());										/* 1.2.correlationId*/
        header.setRequesterNSA(param.getRequesterNSA());										/* 1.3.requesterNSA*/
        header.setProviderNSA(param.getProviderNSA());											/* 1.4.providerNSA*/
        header.setReplyTo(param.getReplyTo()); 													/* 1.5.replyTo*/  
        AttributeStatementType sessionSecurityAttr = new AttributeStatementType();				/* 1.6.sessionSecurityAttr*/
        List<Object> attributeOrEncryptedAttribute = param.getAttributeOrEncryptedAttribute();
		if(attributeOrEncryptedAttribute != null) {
			for(int i=0; i<attributeOrEncryptedAttribute.size(); i++)
				sessionSecurityAttr.getAttributeOrEncryptedAttribute().add(attributeOrEncryptedAttribute.get(i));
		}
        header.setSessionSecurityAttr(sessionSecurityAttr);
        header.getAny().add(null);																/* 1.7.any(List type)*/
        //header.getOtherAttributes().put(service_name, "");									/* 1.8.otherAttributes(Map<QName, String> type)*/
		System.out.println("=====>>3");
        
        String globalReservationId = param.getGlobalReservationId();						/* 2.globalReservationId*/
        String description = param.getDescription();										/* 3.description*/
        Holder connectionId = new Holder(param.getConnectionId());							/* 4.connectionId*/
        ReservationRequestCriteriaType criteria = new ReservationRequestCriteriaType();		/* 5.criteria*/
	        ScheduleType schedule = new ScheduleType();											/* 5.1.schedule*/
	        schedule.setStartTime(param.getStartTime());
			schedule.setEndTime(param.getEndTime());
		criteria.setSchedule(schedule);
		criteria.setServiceType("evts");														/* 5.2.serviceType*/
			EthernetVlanType evts = new EthernetVlanType();										/* 5.3.any(List type)*/
			evts.setCapacity(param.getDesired());
			/*evts.setDirectionality(param.getDirectionality());*/
			evts.setSymmetricPath(true);
				StpType sourceSTP = new StpType();
				sourceSTP.setNetworkId("111");
				sourceSTP.setLocalId("222");
				sourceSTP.setLabels(null);
			evts.setSourceSTP(sourceSTP);
			evts.setEro(null);
				StpType destSTP = new StpType();
				destSTP.setNetworkId("333");
				destSTP.setLocalId("444");
				destSTP.setLabels(null);
			evts.setDestSTP(destSTP);
			evts.setMtu(null);
			evts.setBurstsize(null);
			evts.setSourceVLAN(1782);
			evts.setDestVLAN(1782);		
		criteria.getAny().add(evts);																
		criteria.setVersion(null);																/* 5.4.version*/
		System.out.println("=====>>4");
		//criteria.getOtherAttributes().put(service_name, "");									/* 5.5.otherAttributes*/
        /*//v 2.0*/
		
        	/*ReserveRequestType reserveRequestType = new ReserveRequestType();
        	{
        		reserveRequestType.setCorrelationId(param.getCorrelationId());
        		reserveRequestType.setReplyTo(param.getReplyTo());

        		ReserveType reserve = new ReserveType();
        		{
        			reserve.setRequesterNSA(param.getRequesterNSA());
        			reserve.setProviderNSA(param.getProviderNSA());
        			
        			AttributeStatementType sessionSecurityAttr = new AttributeStatementType();
        			{
        				List<Object> attributeOrEncryptedAttribute = param.getAttributeOrEncryptedAttribute();
    					
    					if(attributeOrEncryptedAttribute != null) {
    						for(int i=0; i<attributeOrEncryptedAttribute.size(); i++)
    							sessionSecurityAttr.getAttributeOrEncryptedAttribute().add(attributeOrEncryptedAttribute.get(i));
    					}
        			}
        			
        			reserve.setSessionSecurityAttr(sessionSecurityAttr);
        			
        			ReservationInfoType reservation = new ReservationInfoType();
        			{
        				reservation.setGlobalReservationId(param.getGlobalReservationId());
        				reservation.setDescription(param.getDescription());
        				reservation.setConnectionId(param.getConnectionId());
        				
        				ServiceParametersType serviceParameters = new ServiceParametersType();
        				{
        					
        					ScheduleType schedule = new ScheduleType();
        					{
        						schedule.setStartTime(param.getStartTime());
        						schedule.setEndTime(param.getEndTime());
        						schedule.setDuration(param.getDuration());
        					}
        					serviceParameters.setSchedule(schedule);
        					BandwidthType bandwidth = new BandwidthType();
        					{
        						bandwidth.setDesired(param.getDesired());
        						bandwidth.setMinimum(param.getMinimum());
        						bandwidth.setMaximum(param.getMaximum());
        					}
        					serviceParameters.setBandwidth(bandwidth);
        					TechnologySpecificAttributesType serviceAttributes = new TechnologySpecificAttributesType();
        					{
        						AttributeStatementType guaranteed = new AttributeStatementType();
        						{
        							List<Object> serviceGuaranteed = param.getServiceGuaranteed();
        							
        							if(serviceGuaranteed != null) {
        								for(int i=0; i<serviceGuaranteed.size(); i++) {
                							guaranteed.getAttributeOrEncryptedAttribute().add(serviceGuaranteed.get(i));
        								}
        							}
        						}
        						serviceAttributes.setGuaranteed(guaranteed);
        						AttributeStatementType preferred = new AttributeStatementType();
        						{
        							List<Object> servicePreferred = param.getServicePreferred();
        							
        							if(servicePreferred != null) {
        								for(int i=0; i<servicePreferred.size(); i++) {
        									preferred.getAttributeOrEncryptedAttribute().add(servicePreferred.get(i));
        								}
        							}
        						}
        						serviceAttributes.setPreferred(preferred);
        					}
        					serviceParameters.setServiceAttributes(serviceAttributes);
        				}
        				reservation.setServiceParameters(serviceParameters);
        				PathType path = new PathType();
        				{
        					path.setDirectionality(param.getDirectionality());
        					ServiceTerminationPointType sourceSTP = new ServiceTerminationPointType();
        					{
        						sourceSTP.setStpId(param.getSourceStpId());
        						TechnologySpecificAttributesType stpSpecAttrs = new TechnologySpecificAttributesType();
        						{
        							AttributeStatementType guaranteed = new AttributeStatementType();
        							{
	        							List<Object> sourceGuaranteed = param.getSourceGuaranteed();
	        							
	        							if(sourceGuaranteed != null) {
	        								for(int i=0; i<sourceGuaranteed.size(); i++) {
	                							guaranteed.getAttributeOrEncryptedAttribute().add(sourceGuaranteed.get(i));
	        								}
	        							}
        							}
        							stpSpecAttrs.setGuaranteed(guaranteed);
        							
        							AttributeStatementType preferred = new AttributeStatementType();
        							{
	        							List<Object> sourcepreferred = param.getSourcePreferred();
	        							
	        							if(sourcepreferred != null) {
	        								for(int i=0; i<sourcepreferred.size(); i++) {
	        									preferred.getAttributeOrEncryptedAttribute().add(sourcepreferred.get(i));
	        								}
	        							}
        							}
        							stpSpecAttrs.setPreferred(preferred);
        							
        						}
        						sourceSTP.setStpSpecAttrs(stpSpecAttrs);
        					}
        					path.setSourceSTP(sourceSTP);
        					ServiceTerminationPointType destSTP = new ServiceTerminationPointType();
        					{
        						destSTP.setStpId(param.getDestStpId());
        						TechnologySpecificAttributesType stpSpecAttrs = new TechnologySpecificAttributesType();
        						{
        							AttributeStatementType guaranteed = new AttributeStatementType();
        							{
	        							List<Object> destGuaranteed = param.getDestGuaranteed();
	        							
	        							if(destGuaranteed != null) {
	        								for(int i=0; i<destGuaranteed.size(); i++) {
	                							guaranteed.getAttributeOrEncryptedAttribute().add(destGuaranteed.get(i));
	        								}
	        							}
        							}
        							stpSpecAttrs.setGuaranteed(guaranteed);
        							
        							AttributeStatementType preferred = new AttributeStatementType();
        							{
	        							List<Object> destPreferred = param.getDestPreferred();
	        							
	        							if(destPreferred != null) {
	        								for(int i=0; i<destPreferred.size(); i++) {
	        									preferred.getAttributeOrEncryptedAttribute().add(destPreferred.get(i));
	        								}
	        							}
        							}
        							stpSpecAttrs.setPreferred(preferred);
        							
        						}
        						destSTP.setStpSpecAttrs(stpSpecAttrs);
        					}
        					path.setDestSTP(destSTP);
        					StpListType stpList = new StpListType();
        					{
        						List<OrderedServiceTerminationPointType> stp = param.getStp();
        						
        						if(stp != null) {
        							for(int i=0; i<stp.size(); i++) {
        								stpList.getStp().add(stp.get(i));
        							}
        						}
        					}
        					path.setStpList(stpList);
        				}
        				reservation.setPath(path);
        			}
        			
        			reserve.setReservation(reservation);
        		}
        		
        		reserveRequestType.setReserve(reserve);
        	}
    	GenericAcknowledgmentType result = port.reserve(reserveRequestType);*/
		try{
			StrUtil.sysPrint("port.reserve() start");
			//port.reserve(header, connectionId, globalReservationId, description, criteria);
			port.reserve(connectionId, globalReservationId, description, criteria);
			StrUtil.sysPrint("port.reserve() end");
		}catch(Exception e){
			StrUtil.sysPrint("Provider.reserve() exception : ");
			StrUtil.sysPrint(""+e.toString());			
		}		
    	/*resultCorrelationId = result.getCorrelationId();*/
        
        return resultCorrelationId;
	}
	
	public String provision(Provision param) throws ServiceException {
    	
		String resultCorrelationId = null;
    	
    	/*ConnectionServiceProvider ss = new ConnectionServiceProvider(this.wsdlURL, this.service_name);
        ConnectionProviderPort port = ss.getConnectionServiceProviderPort();  
    	
    	ProvisionRequestType provisionRequestType = new ProvisionRequestType();
	    	GenericRequestType provision = new GenericRequestType();
    	
	    		provision.setRequesterNSA(param.getRequesterNSA());
	    		provision.setProviderNSA(param.getProviderNSA());
	    		provision.setSessionSecurityAttr(param.getSessionSecurityAttr());
	    		provision.setConnectionId(param.getConnectionId());
    	
		    provisionRequestType.setCorrelationId(param.getCorrelationId());
		    provisionRequestType.setReplyTo(param.getReplyTo());
		    provisionRequestType.setProvision(provision);
	    
    	GenericAcknowledgmentType result = port.provision(provisionRequestType);
    	resultCorrelationId = result.getCorrelationId();*/
	    
	    return resultCorrelationId;
    }
	
	public String release(Release param) throws ServiceException {
		
		String resultCorrelationId = null;
		
		/*ConnectionServiceProvider ss = new ConnectionServiceProvider(this.wsdlURL, this.service_name);
        ConnectionProviderPort port = ss.getConnectionServiceProviderPort();  
        
        ReleaseRequestType releaseRequestType = new ReleaseRequestType();
        	releaseRequestType.setCorrelationId(param.getCorrelationId());
        	releaseRequestType.setReplyTo(param.getReplyTo());
        	
        		GenericRequestType release = new GenericRequestType();
        			release.setRequesterNSA(param.getRequesterNSA());
        			release.setProviderNSA(param.getProviderNSA());
        			release.setSessionSecurityAttr(param.getSessionSecurityAttr());
        			release.setConnectionId(param.getConnectionId());
        		
        	releaseRequestType.setRelease(release);
        
        GenericAcknowledgmentType result = port.release(releaseRequestType);
	    resultCorrelationId = result.getCorrelationId();*/
        
        return resultCorrelationId;
	}
	
	public String terminate(Terminate param) throws ServiceException {
		
		String resultCorrelationId = null;
		
		/*ConnectionServiceProvider ss = new ConnectionServiceProvider(this.wsdlURL, this.service_name);
        ConnectionProviderPort port = ss.getConnectionServiceProviderPort();  
        
        	TerminateRequestType terminateRequestType = new TerminateRequestType();
        		terminateRequestType.setCorrelationId(param.getCorrelationId());
        		terminateRequestType.setReplyTo(param.getReplyTo());
	        		GenericRequestType terminate = new GenericRequestType();
	        			terminate.setRequesterNSA(param.getRequesterNSA());
	        			terminate.setProviderNSA(param.getProviderNSA());
	        			terminate.setSessionSecurityAttr(param.getSessionSecurityAttr());
	        			terminate.setConnectionId(param.getConnectionId());
	        	terminateRequestType.setTerminate(terminate);
        
     	GenericAcknowledgmentType result = port.terminate(terminateRequestType);
    	resultCorrelationId = result.getCorrelationId();*/
		
        return resultCorrelationId;
	}
	
	public String query(Query param) throws ServiceException {
		
		String resultCorrelationId = null;
		
		/*ConnectionServiceProvider ss = new ConnectionServiceProvider(this.wsdlURL, this.service_name);
        ConnectionProviderPort port = ss.getConnectionServiceProviderPort();  
        
        	QueryRequestType queryRequestType = new QueryRequestType();
	    		queryRequestType.setCorrelationId(param.getCorrelationId());
	    		queryRequestType.setReplyTo(param.getReplyTo());
	    		QueryType query = new QueryType();
	    			query.setRequesterNSA(param.getRequesterNSA());
	    			query.setProviderNSA(param.getProviderNSA());
	    			
	    			AttributeStatementType sessionSecurityAttr = new AttributeStatementType();
					
					List<Object> attributeOrEncryptedAttribute = param.getAttributeOrEncryptedAttribute();
					
					if(attributeOrEncryptedAttribute != null) {
						for(int i=0; i<attributeOrEncryptedAttribute.size(); i++)
							sessionSecurityAttr.getAttributeOrEncryptedAttribute().add(attributeOrEncryptedAttribute.get(i));
					}
	    			
	    			query.setSessionSecurityAttr(sessionSecurityAttr);
	    			query.setOperation(param.getOperation());
	    				QueryFilterType queryFilter = new QueryFilterType();
	    					List<String> connectionId = param.getConnectionId();
	    					List<String> globalReservationId = param.getGlobalReservationId();
	    					if(connectionId != null) {
	        					for(int i=0; i<connectionId.size(); i++) {
	        						queryFilter.getConnectionId().add(connectionId.get(i));
	        					}
	        				}
	    					if(globalReservationId != null) {
	        					for(int i=0; i<globalReservationId.size(); i++)
	        						queryFilter.getGlobalReservationId().add(globalReservationId.get(i));
	        				}
	    					
	    			query.setQueryFilter(queryFilter);
	    		queryRequestType.setQuery(query);
    	GenericAcknowledgmentType result = port.query(queryRequestType);
    	resultCorrelationId = result.getCorrelationId();*/
        
        return resultCorrelationId;
	}
	
	public String queryConfirmed(QueryConfirmed param) throws ServiceException {
		
		String resultCorrelationId = null;
		
		/*ConnectionServiceProvider ss = new ConnectionServiceProvider(this.wsdlURL, this.service_name);
        ConnectionProviderPort port = ss.getConnectionServiceProviderPort();  
        
	        QueryConfirmedRequestType queryConfirmedRequestType = new QueryConfirmedRequestType();
				queryConfirmedRequestType.setCorrelationId(param.getCorrelationId());
				
				QueryConfirmedType queryConfirmed = new QueryConfirmedType();
					queryConfirmed.setRequesterNSA(param.getRequesterNSA());
					queryConfirmed.setProviderNSA(param.getProviderNSA());
					
					List<QuerySummaryResultType> reservationSummary = param.getReservationSummary();
						if(reservationSummary != null) {
							for(int i=0; i<reservationSummary.size(); i++) {
								queryConfirmed.getReservationSummary().add(reservationSummary.get(i));
							}
						}
					List<QueryDetailsResultType> reservationDetails = param.getReservationDetails();
						if(reservationDetails != null) {
							for(int i=0; i<reservationDetails.size(); i++) {
								queryConfirmed.getReservationDetails().add(reservationDetails.get(i));
							}
						}
				queryConfirmedRequestType.setQueryConfirmed(queryConfirmed);
    	GenericAcknowledgmentType result = port.queryConfirmed(queryConfirmedRequestType);
    	resultCorrelationId = result.getCorrelationId();*/
		
        return resultCorrelationId;
	}
	
	public String queryFailed(QueryFailed param) throws ServiceException {
		
		String resultCorrelationId = null;
		
		/*ConnectionServiceProvider ss = new ConnectionServiceProvider(this.wsdlURL, this.service_name);
        ConnectionProviderPort port = ss.getConnectionServiceProviderPort();  
        
	        QueryFailedRequestType queryFailedRequestType = new QueryFailedRequestType();
				queryFailedRequestType.setCorrelationId(param.getCorrelationId());
				
					QueryFailedType queryFailed = new QueryFailedType();
						queryFailed.setRequesterNSA(param.getRequesterNSA());
						queryFailed.setProviderNSA(param.getProviderNSA());
					
		    				ServiceExceptionType serviceException = new ServiceExceptionType();
			    			serviceException.setErrorId(param.getErrorId());
			    			serviceException.setText(param.getErrorText());
			    			AttributeStatementType variables = new AttributeStatementType();
			    				
			    				List<Object> attributeOrEncryptedAttribute = param.getAttributeOrEncryptedAttribute();
			    				
			    				if(attributeOrEncryptedAttribute != null) {
			    					for(int i=0; i<attributeOrEncryptedAttribute.size(); i++)
			    						variables.getAttributeOrEncryptedAttribute().add(attributeOrEncryptedAttribute.get(i));
			    				}
			    				
			    			serviceException.setVariables(variables);
						
						queryFailed.setServiceException(serviceException);
				
		queryFailedRequestType.setQueryFailed(queryFailed);
    	GenericAcknowledgmentType result = port.queryFailed(queryFailedRequestType);
    	resultCorrelationId = result.getCorrelationId();*/
        
        return resultCorrelationId;
	}
}
