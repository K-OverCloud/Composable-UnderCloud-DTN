package com.netmng.svc.rest;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.netmng.dto.adm.stp.RouterDTO;
import com.netmng.svc.AbstractSvc;
import com.netmng.svc.adm.AdmService;
import com.netmng.svc.nsa.NsaService;
import com.netmng.util.StrUtil;
import com.netmng.vo.ErrorLog;
import com.netmng.vo.EventLog;
import com.netmng.websvc.rest.server.param.event.Event;
import com.netmng.websvc.rest.server.param.FaultOff;
import com.netmng.websvc.rest.server.param.FaultOn;
import com.netmng.websvc.rest.client.ObjectFactory;
import com.netmng.websvc.rest.client.param.gnpr.Gnpr;
import com.netmng.websvc.rest.client.param.link.Link;
import com.netmng.websvc.rest.client.param.residualBw.ResidualBw;
import com.netmng.websvc.rest.client.param.residualBw.StpAndTime;
import com.netmng.websvc.rest.server.vo.ResMsg;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.UriBuilder;

@SuppressWarnings("unused")
@Path("/api")
public class RestService extends AbstractSvc {
	
	@Autowired(required=true) 
	private AdmService admService;
	
	@Autowired(required=true) 
	private NsaService nsaService;
	/*
	@RequestMapping(method=RequestMethod.GET, value="/faultOn")
				//headers="Accept=application/xml")
	public @ResponseBody FaultOn getFaultOn() {
		FaultOn faultOn = new FaultOn();
		faultOn.setCause("interface dowm");
		faultOn.setDescription("장비의 interface가 다운되었습니다.");
		faultOn.setRecoveryResult(true);
		faultOn.setRouter("192.168.0.1");
		return faultOn;
	}
	*/
	private static String SUCCESS 	= "HTTP 201 Created."; 
	
	@Path("/topologyList")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("text/xml;charset=UTF-8")
	public com.netmng.dto.rest.TopologyList topologyList() {
		
		com.netmng.dto.rest.TopologyList topologyList = new com.netmng.dto.rest.TopologyList();;
		
		try {
			List<RouterDTO> routerDTOList = this.admService.getTopologyList();
			List<com.netmng.dto.rest.Topology> data = new ArrayList<com.netmng.dto.rest.Topology>();
			
			for(int i=0; i<routerDTOList.size(); i++) {
				RouterDTO routerDTO = routerDTOList.get(i);
				com.netmng.dto.rest.Topology topology = new com.netmng.dto.rest.Topology();
				topology.setSeq(routerDTO.getSeq());
				topology.setUrn(routerDTO.getUrn());
				topology.setSite_name(routerDTO.getSite_name());
				topology.setKind(routerDTO.getKind());
				topology.setRouter_fault(routerDTO.getFault_yn());
				
				if(routerDTO.getKind().equals("B")) {
					topology.setP_interface_fault(routerDTO.getInterfaceInfoDTOList().get(0).getP_fault_yn());
					topology.setS_interface_fault(routerDTO.getInterfaceInfoDTOList().get(0).getS_fault_yn());
					topology.setUse_line(routerDTO.getInterfaceInfoDTOList().get(0).getUse_line());
				}
				
				data.add(topology);
			}
			topologyList.setTopology(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		return topologyList;
	}
	
	/*@POST
	@Path("/event")
	@Consumes({MediaType.TEXT_XML, MediaType.APPLICATION_XML})
	@Produces("text/xml;charset=UTF-8")
	//public void event(Event param) {
	public Response event() {
		boolean result 	= false;
		String 	msg		= null;
		
		System.out.println("=====>> rest event start");
		
		return Response.status(201).build();
		//throw new WebApplicationException(Response.status(201).entity(msg).build());
	}*/
	
	
	@Path("/event")
	@POST
	@Consumes({MediaType.APPLICATION_XML})
	//@Produces("text/xml;charset=UTF-8")
	//public void event(Event param) {
	public void event(String sParam) {
		System.out.println("Event@post");
		boolean result 	= false;
		String 	msg		= null;
		
		StrUtil.sysPrint("sParam="+sParam);
		
		try {
			com.netmng.vo.Event event = new com.netmng.vo.Event();

			DocumentBuilderFactory factory	= DocumentBuilderFactory.newInstance();
			DocumentBuilder builder			= factory.newDocumentBuilder();
			Document document				= builder.parse(new InputSource(new StringReader(sParam)));
			
			NodeList nlCause		= document.getElementsByTagName("cause");
			NodeList nlDescription	= document.getElementsByTagName("description");
			NodeList nlRouter		= document.getElementsByTagName("router");
			NodeList nlInterface	= document.getElementsByTagName("interface");
			
			event.setCause(nlCause.item(0).getTextContent());
			event.setDescription(nlDescription.item(0).getTextContent());
			event.setRouter(nlRouter.item(0).getTextContent());
			for(int i=0; i<nlInterface.getLength(); i++){
				event.getListInterface().add(nlInterface.item(i).getTextContent());
			}
			
			StrUtil.sysPrint("rest event start");
			StrUtil.sysPrint("Cause="+event.getCause());
			StrUtil.sysPrint("Description="+event.getDescription());
			StrUtil.sysPrint("Router="+event.getRouter());
			for(int i=0; i<event.getListInterface().size(); i++){
				StrUtil.sysPrint("Interface="+event.getListInterface().get(i));
			}
			
			this.admService.eventInsert(event);
			result = true;
			msg = RestService.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
        	ErrorLog errorLog = new ErrorLog();
        	errorLog.setUrl("/rest/api/event");
        	errorLog.setName("webserver::event");
        	errorLog.setLog_message(e.toString());
        	this.admService.errorLogInsert(errorLog);
			msg	= e.getMessage();
		}
		
		if(result) {
			throw new WebApplicationException(Response.status(201).entity(msg).build());
			//return Response.status(201).entity(RestCtrllor.SUCCESS).build();
		} else {
			throw new WebApplicationException(Response.status(400).entity(new ResMsg(msg)).build());
		}
	}
	
	@GET
	@Path("/event")
	//@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	//public void event(Event param) {
	public void event2() {
		
		throw new WebApplicationException(Response.status(201).entity("test ok").build());
	}
	
	@Path("/notification")
	@POST
	@Consumes({MediaType.APPLICATION_XML})
	//@Produces("text/xml;charset=UTF-8")
	//public void event(Event param) {
	public void notification(String sParam) {
		boolean result 	= false;
		String 	msg		= null;
		
		StrUtil.sysPrint("sParam="+sParam);
		
		try {
			com.netmng.vo.Event event = new com.netmng.vo.Event();

			DocumentBuilderFactory factory	= DocumentBuilderFactory.newInstance();
			DocumentBuilder builder			= factory.newDocumentBuilder();
			Document document				= builder.parse(new InputSource(new StringReader(sParam)));
			
			NodeList nlConnectionId		= document.getElementsByTagName("connectionId");
			NodeList nlSourceVLAN		= document.getElementsByTagName("sourceVLAN");
			NodeList nlDestinationVLAN	= document.getElementsByTagName("destinationVLAN");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("connectionId",		nlConnectionId.item(0).getTextContent());
			map.put("sourceVLAN",		nlSourceVLAN.item(0).getTextContent());
			map.put("destinationVLAN",	nlDestinationVLAN.item(0).getTextContent());
			
			StrUtil.sysPrint("rest vlanChgNoti start");
			StrUtil.sysPrint("connectionId="	+nlConnectionId.item(0).getTextContent());
			StrUtil.sysPrint("sourceVLAN="		+nlSourceVLAN.item(0).getTextContent());
			StrUtil.sysPrint("destinationVLAN="	+nlDestinationVLAN.item(0).getTextContent());
						
			this.nsaService.vlanUpdate(map);
			result = true;
			msg = RestService.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
        	ErrorLog errorLog = new ErrorLog();
        	errorLog.setUrl("/rest/api/vlanChgNoti");
        	errorLog.setName("webserver::vlanChgNoti");
        	errorLog.setLog_message(e.toString());
        	this.admService.errorLogInsert(errorLog);
			msg	= e.getMessage();
		}
		
		if(result) {
			throw new WebApplicationException(Response.status(201).entity(msg).build());
			//return Response.status(201).entity(RestCtrllor.SUCCESS).build();
		} else {
			throw new WebApplicationException(Response.status(400).entity(new ResMsg(msg)).build());
		}
	}
	
	public static void main(String[] args) {   
		  
		//String s = service.path("rest").path("faultOn").accept(MediaType.TEXT_PLAIN).get(String.class);   
		/*
		ClientConfig config = new DefaultClientConfig();   
        Client client = Client.create(config);
		WebResource service = client.resource("http://www.netmng.re.kr/rest/api/faultOn");
        String response = service.path("rest").path("faultOn").type(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).entity(   
                new File("M:/project/netmng/src/netmng/src/com/netmng/ctrl/rest/test.xml")).post(String.class);  
        System.out.println("response ===== " + response);
        */
		/*
        String xml 	= "";
        xml += "<faultOn><cause>interface dowm</cause><description>다운되었습니다.</description><recoveryResult>true</recoveryResult><router>192.169.0.0</router><interface>192.168.0.3</interface><interface>192.169.0.11</interface></faultOn>";
        
        WebResource resource = Client.create().resource("http://www.netmng.re.kr/rest/faultOn"); 
        ClientResponse response = resource.type("application/xml").post(ClientResponse.class, xml);  
        
		System.out.println(response.toString());
		*/
		/*
		String xml 	= "";
        xml += "<faultOn><cause>interface dowm</cause><description>다운되었습니다.</description><recoveryResult>true</recoveryResult><router>192.169.0.0</router><interface>192.168.0.3</interface><interface>192.169.0.11</interface></faultOn>";
        
        ClientConfig config = new DefaultClientConfig();   
        Client client = Client.create(config);   
        WebResource service = client.resource("http://www.netmng.re.kr/rest/api/faultOn");
        ClientResponse response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).post(ClientResponse.class, xml);
		
		
		
		ClientConfig config = new DefaultClientConfig();   
        Client client = Client.create(config);   
        WebResource service = client.resource("http://www.netmng.re.kr/rest/api/faultOn");
        
        Fault param = new Fault();
        
        param.setCause("interface dowm");
        param.setDescription("시스템다운!!");
        param.setRecoveryResult(false);
        param.setRouter("192.168.0.1");
        param.setInterfaceIP("192.168.0.5");
        
        ClientResponse response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).post(ClientResponse.class, param);
        
        if(response.getStatus() != 201) {
	        ResMsg msg = response.getEntity(ResMsg.class);
	        System.out.println(msg.getCause());
        } else {
        	System.out.println(response.getEntity(String.class));
        }
        
        
		*/
			
		/*        
		com.netmng.websvc.rest.client.HttpWwwNetmngReKrRest.Api.FaultOn faultOn = new com.netmng.websvc.rest.client.HttpWwwNetmngReKrRest.Api.FaultOn();
		
		com.netmng.websvc.rest.client.param.FaultOn param = new com.netmng.websvc.rest.client.param.FaultOn();
		
		param.setCause("interface dowm");
        param.setDescription("시스템 다운");
        param.setRecoveryResult(true);
        param.setRouter("192.168.0.1");
        param.getInterface().add("192.168.1.22");
        param.getInterface().add("192.168.2.2");
		
        ObjectFactory xmlparam = new ObjectFactory();
        
        
        ClientResponse response = faultOn.postApplicationXml(xmlparam.createFaultOn(param), ClientResponse.class);
        
        if(response.getStatus() != 201) {
	        ResMsg msg = response.getEntity(ResMsg.class);
	        System.out.println(msg.getCause());
        } else {
        	System.out.println(response.getEntity(String.class));
        }
        
        
        
        
        */
        
        
        
        
		/*
        com.netmng.websvc.rest.client.HttpWwwNetmngReKrRest.Api.FaultOff faultOff = new com.netmng.websvc.rest.client.HttpWwwNetmngReKrRest.Api.FaultOff();
        
        com.netmng.websvc.rest.client.param.FaultOff param = new com.netmng.websvc.rest.client.param.FaultOff();
        
        param.setResumeResult(false);
        param.setRouter("192.168.0.1");
        param.getInterface().add("192.168.0.5");
        param.getInterface().add("192.168.0.6");
        
        ObjectFactory xmlparam = new ObjectFactory();
        
        
        ClientResponse response = faultOff.postApplicationXml(xmlparam.createFaultOff(param), ClientResponse.class);
        
        if(response.getStatus() != 201) {
	        ResMsg msg = response.getEntity(ResMsg.class);
	        System.out.println(msg.getCause());
        } else {
        	System.out.println(response.getEntity(String.class));
        }
         */
        
        
        
        /*
        
		String xml 	= "";
        xml += "<faultOn><cause>interface dowm</cause><description>다운되었습니다.</description><recoveryResult>true</recoveryResult><router>192.169.0.0</router><interface>192.168.0.3</interface><interface>192.169.0.11</interface></faultOn>";
        
        ClientConfig config = new DefaultClientConfig();   
        Client client = Client.create(config);   
        WebResource service = client.resource("http://www.netmng.re.kr/rest/api/faultOn");
        ClientResponse response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).post(ClientResponse.class, xml);
        
        if(response.getStatus() != 201) {
	        ResMsg msg = response.getEntity(ResMsg.class);
	        System.out.println(msg.getCause());
        } else {
        	System.out.println(response.getEntity(String.class));
        }
        
        */
		
		/*
		 ClientConfig config = new DefaultClientConfig();   
		 Client client = Client.create(config);   
		 WebResource service = client.resource("http://203.230.116.210:8080/nsi/management/api/init");
		 ClientResponse response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).post(ClientResponse.class, null);
        
		 if(response.getStatus() != 201) {
			 //ResMsg msg = response.getEntity(ResMsg.class);
			 //System.out.println(msg.getCause());
			 System.out.println(response.getEntity(String.class));
		 } else {
			 //System.out.println(response.getEntity(String.class));
			 System.out.println(response.getEntity(String.class));
		 }
        */
		
		
		
		
		/////
		/*
		{
			ClientConfig config = new DefaultClientConfig();   
			Client client = Client.create(config); 
			WebResource service = client.resource("http://203.230.116.210:8080/nsi/management/debug/get/initGNPR_original");
			//WebResource service = client.resource("http://203.230.116.210:8080/nsi/management/debug/get/link_bw");
			ClientResponse response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).get(ClientResponse.class);
	        
	        if(response.getStatus() <= 201) {
		        System.out.println("link_bw 성공" + response.getStatus());
	        } else {
	        	//ResMsg msg = response.getEntity(ResMsg.class);
	        	System.out.println("link_bw 실패" + response.getStatus());
	        }
		}
		*/
		{
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(com.netmng.websvc.rest.client.param.Event.class);
				StringWriter st = new StringWriter();
				st.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
				st.append("<event>");
					st.append("<cause>");
						st.append("3");
					st.append("</cause>");
					st.append("<description>");
						st.append("SNMP Trap Raw Data");
					st.append("</description>");
					st.append("<linkList>");
						st.append("<fault>");
							st.append("<router>");
								st.append("192.168.2.0");
							st.append("</router>");
							st.append("<interface>");
								st.append("192.168.2.1");
							st.append("</interface>");
						st.append("</fault>");
						st.append("<linked>");
							st.append("<router>");
								st.append("192.168.1.0");
							st.append("</router>");
							st.append("<interface>");
								st.append("192.168.1.1");
							st.append("</interface>");
						st.append("</linked>");
						st.append("<fault>");
							st.append("<router>");
								st.append("192.168.2.0");
							st.append("</router>");
							st.append("<interface>");
								st.append("192.168.2.3");
							st.append("</interface>");
						st.append("</fault>");
						st.append("<linked>");
							st.append("<router>");
								st.append("192.168.3.0");
							st.append("</router>");
							st.append("<interface>");
								st.append("192.168.3.1");
							st.append("</interface>");
						st.append("</linked>");
					st.append("</linkList>");
				st.append("</event>");	
				//Marshaller marshaller = jaxbContext.createMarshaller();
				//marshaller.marshal(event, st);
				//marshaller.marshal(event, new File("../../../event.xml"));
				ClientConfig config = new DefaultClientConfig();   
				Client client = Client.create(config); 
				/*WebResource service = client.resource("http://www.netmng.re.kr/rest/api/event");*/
				WebResource service = client.resource("http://www.netmng.re.kr/netmng/rest/api/event");
		        ClientResponse response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).post(ClientResponse.class, st.toString());
		        
		        if(response.getStatus() <= 201) {
			        System.out.println("event 성공" + response.getStatus());
		        } else {
		        	System.out.println("event 실패" + response.getStatus());
		        }
		        
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		///////////
		/*
		{
			ClientConfig config = new DefaultClientConfig();   
			Client client = Client.create(config); 
			WebResource service = client.resource("http://www.netmng.re.kr/rest/api/event");
			StringBuffer st = new StringBuffer();
	        st.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
	        st.append("<event>");
	        //st.append("<event xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""); 
	        //st.append(" xsi:schemaLocation=\"http://kisti.re.kr/dynamicKL/api dynamicKL.xsd\""); 
	        //st.append(" xmlns=\"http://kisti.re.kr/dynamicKL/api\">");
	        st.append("<cause>222</cause>");
	        st.append("<description>111</description>");
	        st.append("<router>333</router>");
	        st.append("<interface>444</interface>");
	        st.append("<interface>555</interface>");
	        st.append("</event>");
	        List list = new ArrayList();
	        list.add("dsdadada");
	        list.add("sssssss");
	        
	        Event param = new Event();
	        //param.setXmlns("wwww");
	        //param.setXmlns_xsi("dddd");
	        //param.setXsi_schemaLocation("sdsdad");
	        param.setCause("cause");
	        param.setDescription("안녕");
	        param.setRouter("222");
	        param.set_interface(list);
	        
			ClientResponse response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).post(ClientResponse.class, st.toString());
	        
	        if(response.getStatus() <= 201) {
		        System.out.println("event 성공" + response.getStatus());
	        } else {
	        	//ResMsg msg = response.getEntity(ResMsg.class);
	        	System.out.println("event 실패" + response.getStatus());
	        }
		}
		*/
    }   
  
	private static URI getBaseURI() {   
        return UriBuilder.fromUri("http://www.netmng.re.kr/").build();   
    }   

}
