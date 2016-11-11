package com.netmng.ctrl.nsa;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.netmng.com.error.ErrorClass;
import com.netmng.ctrl.AbstractCtrl;
import com.netmng.dto.adm.stp.InterfaceInfoDTO;
import com.netmng.dto.adm.stp.RouterDTO;
import com.netmng.dto.nsa.ReservationDTO;
import com.netmng.dto.user.UserDTO;
import com.netmng.param.nsa.SrchReserveParam;
import com.netmng.svc.adm.AdmService;
import com.netmng.svc.nsa.NsaService;
import com.netmng.svc.user.UserService;
import com.netmng.util.MailUtil;
import com.netmng.util.StrUtil;
import com.netmng.vo.Ero;
import com.netmng.vo.ErrorLog;
import com.netmng.vo.ProviderNsa;
import com.netmng.vo.Reservation;
import com.netmng.vo.ReservationFav;
import com.netmng.vo.User;
import com.netmng.websvc.rest.client.param.residualBw.ResidualBw;
import com.netmng.websvc.rest.client.param.residualBw.StpAndTime;
import com.netmng.websvc.rest.client.vo.ResMsg;
import com.netmng.websvc.soap.assertion.AttributeType;
//import com.netmng.websvc.soap.param.types.DirectionalityType;
import com.netmng.websvc.soap.param.types.QueryNotificationType;
import com.netmng.websvc.soap.param.types.QuerySummaryResultType;
import com.netmng.websvc.soap.param.types.QueryType;
import com.netmng.websvc.soap.param.types.ReservationRequestCriteriaType;
import com.netmng.websvc.soap.param.types.ScheduleType;
import com.netmng.websvc.soap.svc.header.CommonHeaderType;
import com.netmng.websvc.soap.svc.services.point2point.EthernetVlanType;
import com.netmng.websvc.soap.svc.services.types.DirectionalityType;
import com.netmng.websvc.soap.svc.services.types.OrderedStpType;
import com.netmng.websvc.soap.svc.services.types.StpListType;
import com.netmng.websvc.soap.svc.services.types.StpType;
import com.netmng.websvc.soap.svc.Provider;
/*import com.netmng.websvc.soap.svc.param.provider.Provision;
import com.netmng.websvc.soap.svc.param.provider.Release;
import com.netmng.websvc.soap.svc.param.provider.Reserve;
import com.netmng.websvc.soap.svc.param.provider.Terminate;*/
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@Controller
public class NsaCtrllor extends AbstractCtrl {
	
	@Value("#{netmngConfig['nsa.replyTo']}")
	private String reply_to;
	@Value("#{netmngConfig['nsa.requesterNSA']}")
	private String requesterNSA;
	@Value("#{netmngConfig['nsa.providerNSA']}")
	private String providerNSA;
	@Value("#{netmngConfig['nsa.directionality']}")
	private String directionality;
	@Value("#{netmngConfig['dynamicKL.xmlns']}")
	private String		xmlns;
	@Value("#{netmngConfig['dynamicKL.xmlns_xsi']}")
	private String		xmlns_xsi;
	@Value("#{netmngConfig['dynamicKL.xmlns_schemaLocation']}")
	private String		xmlns_schemaLocation;
	
	@Value("#{netmngConfig['adm.mail.server']}")
	private String mailServer;
	@Value("#{netmngConfig['adm.mail.adminAddress']}")
	private String adminAddress;

	@Autowired(required=true) 
	private NsaService nsaService;
	@Autowired(required=true) 
	private AdmService admService;
	@Autowired(required=true) 
	private UserService userService;
	
	// 요청서비스(화면)
	@RequestMapping(value="/nsa/index")
	public ModelAndView index() {
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("/nsa/index");
		return mnv;
	}
	
	// 요청서비스(화면)
	@RequestMapping(value="/nsa/reserveIF")
	public ModelAndView reserveIF(
			ReservationDTO param,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		System.out.println("=====>> reserveIF() : start");
		try {
			mnv.addObject("reservationDTO", param);
			
			StrUtil.sysPrint("providerNSA()");
			mnv.addObject("providerNSAList", this.admService.getProviderNsaList());
			
			StrUtil.sysPrint("StpNetwork()");
			mnv.addObject("stpNetworkList", this.admService.getStpNetworkList());
			
			StrUtil.sysPrint("StpLocal()");
			mnv.addObject("stpLocalList", this.admService.getStpLocalList());
			
			StrUtil.sysPrint("StpVlan()");
			mnv.addObject("stpVlanList", this.admService.getStpVlanList());
			
			StrUtil.sysPrint("ReserveFavorite()");
			List<ReservationFav> list_reserveFav = this.nsaService.getReserveListFavSelect();
			mnv.addObject("reservationFavList", list_reserveFav);
			/*
			StrUtil.sysPrint("imsi test start");
			// imsi test
			Map map = new HashMap();
			map.put("service_type", "querySummaryConfirmed");
			QueryType queryType = new QueryType();
			queryType.getConnectionId().add("urn:uuid:03df9a96-8638-4325-be4b-ef37b0179c58");
			map.put("queryType", queryType);
			com.netmng.websvc.soap.svc.NewThread nt = new com.netmng.websvc.soap.svc.NewThread(map);
			nt.start();
			// imsi test
			StrUtil.sysPrint("imsi test end");
			*/
			
			mnv.setViewName("/nsa/reserveIF");
		} catch(Exception e) {
			result.rejectValue("seq", "DB01", "com.netmng.exception.db");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		}
		return mnv;
	}
	
	// 요청서비스(예약)
	/*@RequestMapping(value="/nsa/proc/reserveIP")	
	public ModelAndView reserveIP(
			ReservationDTO param,
			BindingResult result,
			HttpServletRequest request,
			HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		int maximum = 0;
		if (this.isNotValid(param, result, Reservation.ReserveInsert.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else {
			try{
				System.out.println("=====>> 00)this.reply_to="+this.reply_to);
				if((maximum = Integer.parseInt(this.getBandwidth(
								param.getSource_stp_seq(), 
								param.getDest_stp_seq(), 
								new StringBuffer(param.getStart_date()).append(" ").append(param.getStart_hour()).append(":").append(param.getStart_min()).toString(), 
								new StringBuffer(param.getEnd_date()).append(" ").append(param.getEnd_hour()).append(":").append(param.getEnd_min()).toString())))
						< Integer.parseInt(param.getDesired())
				)
				//1)최대대역폭을 dynamicKL에서 얻음.
				maximum = Integer.parseInt(this.getBandwidth(	param.getSource_stp_seq(), 
																param.getDest_stp_seq(), 
																new StringBuffer(param.getStart_date()).append(" ").append(param.getStart_hour()).append(":").append(param.getStart_min()).toString(), 
																new StringBuffer(param.getEnd_date()).append(" ").append(param.getEnd_hour()).append(":").append(param.getEnd_min()).toString())	);
				maximum = 1000;	kisti rest 적용시 삭제
				System.out.println("=====>> maximum="+maximum);
				System.out.println("=====>> Integer.parseInt(param.getDesired()="+Integer.parseInt(param.getDesired()));
				//2)대역폭 vs 최대대역폭 비교
				if(maximum < Integer.parseInt(param.getDesired())){
					
					//2-1)입력한 대역폭이 최대대역폭보다 큰 경우 : error처리
					result.rejectValue("desired", "DB01", "com.netmng.vo.Reservation.desired.maximum");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
					mnv.setViewName(ErrorClass.ERRORDATA);
				} else {
					//2-2)입력한 대역폭이 최대대역폭보다 같거나 작은 경우
					//2-2-1)param(ReservationDTO) setting
					param.setCorrelation_id(UUID.randomUUID().toString());			//UUID type, 각 request에 대한 response 메시지 식별자 ID, transaction 식별자로 보면됨
					param.setConnection_id("urn:" + UUID.randomUUID().toString());	//각 연결에 대한 식별자, A->B->C 가 있을때 A->B 사이는 a, B->C 사이는 b 라는 connectionID를 가짐, 이때 A,B,C는 동일한 globalReservationID를 가짐
					param.setGlobal_reservation_id(UUID.randomUUID().toString());	//optional, 네트워크를 통한 각각 연관된 예약요청의 연계성을 나타내는 식별자, end-to-end 연결에 대해서 동일한 값을 가짐
					param.setStatus("C");											//상태 (예약 : C, 구성 : P, 해제 : R, 종료 : T)
					param.setFlag("R");												//요청상태(요청 : R, 성공 : C, 실패 : F)
					
					if(param.getType().equals("multi")) {
						
					}
					param.setReply_to(this.reply_to);				//각 response 메시지의 목적지
					System.out.println("=====>> this.reply_to="+this.reply_to);
					param.setRequester_nsa(this.requesterNSA);		//초기 source NSA 식별, confrim or fail을 최종적으로 받을 NSA
					System.out.println("=====>> this.requesterNSA="+this.requesterNSA);
					param.setProvider_nsa(this.providerNSA);		//최종 destination NSA 식별, confrim or fail 을 보내는 NSA
					System.out.println("=====>> this.providerNSA="+this.providerNSA);
					param.setDirectionality(this.directionality);	//
					System.out.println("=====>> this.directionality="+this.directionality);
					param.setUser_seq(((User)sess.getAttribute(User.SESSION_KEY)).getSeq());	//등록자일련번호
					param.setStart_time(StrUtil.transformDate(new StringBuffer(param.getStart_date()).append(" ").append(param.getStart_hour()).append(":").append(param.getStart_min()).append(":00").toString(), new String[]{"yyyy.MM.dd HH:mm:ss"}));	//예약시간 시작(yyyy-MM-dd HH:mm:ss)
					param.setEnd_time(StrUtil.transformDate(new StringBuffer(param.getEnd_date()).append(" ").append(param.getEnd_hour()).append(":").append(param.getEnd_min()).append(":00").toString(), new String[]{"yyyy.MM.dd HH:mm:ss"}));			//예약시간 종료(yyyy-MM-dd HH:mm:ss)
					param.setMinimum("0");							//대역폭최소값
					param.setMaximum(String.valueOf(maximum));		//대역폭최대값
					param.setSource_stp(new RouterDTO());			//출발지 STP TOPOLOGY_S : SEQ
					param.getSource_stp().setSeq(param.getSource_stp_seq());
					param.setDest_stp(new RouterDTO());				//도착지 STP TOPOLOGY_S : SEQ
					param.getDest_stp().setSeq(param.getDest_stp_seq());
					System.out.println("=====>> 11");
									
					//2-2-2)출발지/도착지 router의 seq를 통해 사용중이고, STP식별번호(URN)가 동일한 router정보 얻음
					//FROM ROUTER WHERE USE_YN = 'Y' AND URN = (SELECT URN FROM ROUTER WHERE SEQ = #seq#)
					RouterDTO sourceStp = this.nsaService.getUseRouterDTOSelect(param.getSource_stp());
					RouterDTO destStp = this.nsaService.getUseRouterDTOSelect(param.getDest_stp());
					System.out.println("=====>> 22");
					
					//2-2-3)router의 seq를 통해 interface정보 얻음(list 중 1st row 정보 사용) 
					@SuppressWarnings("unused")
					InterfaceInfoDTO sourceInterfaceInfo = null;
					@SuppressWarnings("unused")
					InterfaceInfoDTO destInterfaceInfo = null;
					System.out.println("=====>> 33");
					
					if(sourceStp.getKind().equals("B")) {	//라우터종류=말단부
						InterfaceInfoDTO data = new InterfaceInfoDTO();
						data.setRouter_seq(sourceStp.getSeq());
						//FROM INTERFACE_INFO WHERE ROUTER_SEQ = #router_seq#
						sourceInterfaceInfo = this.admService.getInterfaceInfo(data).get(0);
					}
					
					if(destStp.getKind().equals("B")) {		//라우터종류=말단부
						InterfaceInfoDTO data = new InterfaceInfoDTO();
						data.setRouter_seq(destStp.getSeq());
						destInterfaceInfo = this.admService.getInterfaceInfo(data).get(0);
					}
					System.out.println("=====>> 44");
					
					//2-2-4)
					//	출발지 router 장애 || 
					//	(출발지 interface!=null &&(출발지정회선 장애 || 출발지부회선 장애)) ||
					//	(도착지 interface!=null &&(도착지정회선 장애 || 도착지부회선 장애))
					if(sourceStp.getFault_yn() 
					|| (sourceInterfaceInfo != null && (sourceInterfaceInfo.getP_fault_yn() || sourceInterfaceInfo.getS_fault_yn()))
					|| (destInterfaceInfo != null && (destInterfaceInfo.getP_fault_yn() || destInterfaceInfo.getS_fault_yn()))
					) {
						System.out.println("=====>> 51");
						result.rejectValue("seq", "DB01", "com.netmng.exception.nsa.router_trouble");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
						mnv.setViewName(ErrorClass.ERRORDATA);
					}
					//	도착지 router 장애
					else if(destStp.getFault_yn()) {
						System.out.println("=====>> 52");
						result.rejectValue("seq", "DB01", "com.netmng.exception.nsa.router_trouble");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
						mnv.setViewName(ErrorClass.ERRORDATA);
					} 
					//	예약시간_시작 이전
					else if(!new Date().before(param.getStart_time())) {
						System.out.println("=====>> 53");
						result.rejectValue("start_time", "DB01", "com.netmng.vo.Reservation.start_time.compare");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
						mnv.setViewName(ErrorClass.ERRORDATA);
					} 
					//	예약시간_시작 > 예약시간_종료 : soap 처리에 필요한 정보 setting 및 예약정보 insert
					else if(param.getStart_time().before(param.getEnd_time())) {
						System.out.println("=====>> 54");
						Provider provider = new Provider();
						
						//2-2-4-1)예약(Reserve)bean에 정보 담기
						Reserve reserve = new Reserve();
						reserve.setCorrelationId(param.getCorrelation_id());
						reserve.setReplyTo(param.getReply_to());
						reserve.setRequesterNSA(param.getRequester_nsa());
						reserve.setProviderNSA(param.getProvider_nsa());
						reserve.setGlobalReservationId(param.getGlobal_reservation_id());
						reserve.setConnectionId(param.getConnection_id());
				    	GregorianCalendar start = new GregorianCalendar();
				    	start.setTime(param.getStart_time());
				    	reserve.setStartTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(start));
						GregorianCalendar end = new GregorianCalendar();
				    	end.setTime(param.getEnd_time());
				    	reserve.setEndTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(end));
				    	reserve.setDesired(Integer.parseInt(param.getDesired()));
				    	reserve.setMinimum(0);
				    	reserve.setMaximum(maximum);
				    	reserve.setDirectionality(DirectionalityType.fromValue(param.getDirectionality()));
				    	System.out.println("=====>> 66");
				    	
				    	//2-2-4-2)
				    	AttributeType recovery = new AttributeType();
				        recovery.setName("DynamicKL:recoveryFlag");
				        recovery.setNameFormat("boolean");
				        recovery.getAttributeValue().add(true);
				        
				        AttributeType recoveryMechanism = new AttributeType();
				        recoveryMechanism.setName("DynamicKL:recoveryMechanism");
				        recoveryMechanism.setNameFormat("String");
				        recoveryMechanism.getAttributeValue().add("1:1");
				    	
				        AttributeType srcHostIP = new AttributeType();
				        srcHostIP.setName("DynamicKL:srcHostIP");
				        srcHostIP.setNameFormat("String");
				        srcHostIP.getAttributeValue().add(param.getHost_ip());
				        	
				        AttributeType destHostIP = new AttributeType();
				        destHostIP.setName("DynamicKL:destHostIP");
				        destHostIP.setNameFormat("String");
				        destHostIP.getAttributeValue().add(param.getDest_ip());
				        
				        List<Object> serviceGuaranteed = new ArrayList<Object>();
				        serviceGuaranteed.add(recovery);
				        serviceGuaranteed.add(recoveryMechanism);
				        serviceGuaranteed.add(srcHostIP);
				        serviceGuaranteed.add(destHostIP);
				        
				    	reserve.setServiceGuaranteed(serviceGuaranteed);
				    	System.out.println("=====>> 77");
				    	
				    	// 지금 사용하고 있는 stp를 검색 후 urn value 변경
				    	//reserve.setSourceStpId(this.nsaService.getUseRouterDTOSelect(param.getSource_stp()).getUrn());
				    	//reserve.setDestStpId(this.nsaService.getUseRouterDTOSelect(param.getDest_stp()).getUrn());
				    	reserve.setSourceStpId(sourceStp.getUrn());	//출발지 router의 STP식별번호 setting
		    			reserve.setDestStpId(destStp.getUrn());		//도착지 router의 STP식별번호 setting
				    	
		    			//2-2-4-3)예약정보(reservation) insert
				    	param.setSeq(String.valueOf(this.nsaService.nsaReserve(param)));
				    	System.out.println("=====>> 88");
				    	
				    	try {
				    		//2-2-4-4)soap을 통해 예약(Reserve)bean을 전달하고 UUID type을 return 받아 param(ReservationDTO)의 UUID type과 비교
				    		//		  -> provider.reserve(reserve)
				    		System.out.println("=====>> reserve start");
				    		//2-2-4-4-1)동일
					    	if(param.getCorrelation_id().equals(provider.reserve(reserve))) {
					    		System.out.println("=====>> reserve success");
					    		mnv.addObject("mode", "RESERVE");
					    		mnv.addObject("seq", param.getSeq());
					    		mnv.setViewName("/nsa/proc/reserve");
					    	} 
				    		//2-2-4-4-2)다름 : insert한 예약정보(reservation) delete 후 error
					    	else {
					    		System.out.println("=====>> reserve fail 1)");
					    		// data 삭제
					    		this.nsaService.nsaDelete(param);
					    		result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.reserve.fail");
					    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
								mnv.setViewName(ErrorClass.ERRORDATA);
								
					        	ErrorLog errorLog = new ErrorLog();
					        	errorLog.setUrl("/nsa/proc/reserveIP.do");
					        	errorLog.setName("NSI::reserve");
					        	errorLog.setLog_message("");
					        	this.admService.errorLogInsert(errorLog);
					    	}
				    	} catch (Exception e) {
				    		System.out.println("=====>> reserve fail 2)");
				    		System.out.println("=====>> : "+e.toString());
				    		e.printStackTrace();
				    		// data 삭제
				    		this.nsaService.nsaDelete(param);
				    		result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.reserve.fail");
				    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
							mnv.setViewName(ErrorClass.ERRORDATA);
							
				        	ErrorLog errorLog = new ErrorLog();
				        	errorLog.setUrl("/nsa/proc/reserveIP.do");
				        	errorLog.setName("NSI::reserve");
				        	errorLog.setLog_message(e.toString());
				        	this.admService.errorLogInsert(errorLog);
				    	}
					} else {
						result.rejectValue("start_time", "DB01", "com.netmng.vo.Reservation.time.compare");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
						mnv.setViewName(ErrorClass.ERRORDATA);
					}
				}
		    	
			} catch(Exception e) {
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/reserveIP.do");
	        	errorLog.setName("NSI::reserve");
	        	errorLog.setLog_message(e.toString());
	        	System.out.println("=====>> reserveIP() : Exception");
	        	System.out.println("=====>> reserveIP() : errorLog.getLog_message = "+errorLog.getLog_message());
	        	this.admService.errorLogInsert(errorLog);
	        	System.out.println("=====>> reserveIP() : errorLogInsert");
			}
		}
		return mnv;
	}*/
	// 요청서비스(예약)
	@RequestMapping(value="/nsa/proc/reserveIP")	
	public ModelAndView reserveIP(
			ReservationDTO param,
			BindingResult result,
			HttpServletRequest request,
			HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		int maximum = 0;
		if (this.isNotValid(param, result, Reservation.ReserveInsert.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else {
			try{
				StrUtil.sysPrint("reserveIP start!!");
				/*if((maximum = Integer.parseInt(this.getBandwidth(
								param.getSource_stp_seq(), 
								param.getDest_stp_seq(), 
								new StringBuffer(param.getStart_date()).append(" ").append(param.getStart_hour()).append(":").append(param.getStart_min()).toString(), 
								new StringBuffer(param.getEnd_date()).append(" ").append(param.getEnd_hour()).append(":").append(param.getEnd_min()).toString())))
						< Integer.parseInt(param.getDesired())
				)*/
				//1)최대대역폭을 dynamicKL에서 얻음.
				/*maximum = Integer.parseInt(this.getBandwidth(	param.getSource_stp_seq(), 
																param.getDest_stp_seq(), 
																new StringBuffer(param.getStart_date()).append(" ").append(param.getStart_hour()).append(":").append(param.getStart_min()).toString(), 
																new StringBuffer(param.getEnd_date()).append(" ").append(param.getEnd_hour()).append(":").append(param.getEnd_min()).toString())	);*/
				String sdTime = "";
				sdTime += param.getStart_date()+" "+param.getStart_hour()+":"+param.getStart_min()+":00";
				sdTime += "|";
				sdTime += param.getEnd_date()+" "+param.getEnd_hour()+":"+param.getEnd_min()+":00";
				maximum = Integer.parseInt(this.getBandwidth(	param.getSource_network_id(), 
																param.getSource_local_id(), 
																param.getDest_network_id(), 
																param.getDest_local_id(), 
																sdTime	));				
				//maximum = 10;	/*kisti rest 적용시 삭제*/
				
				//2)대역폭 vs 최대대역폭 비교
				if(maximum < Integer.parseInt(param.getDesired())){
					//2-1)입력한 대역폭이 최대대역폭보다 큰 경우 : error처리
					result.rejectValue("desired", "DB01", "com.netmng.vo.Reservation.desired.maximum");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
					mnv.setViewName(ErrorClass.ERRORDATA);
				} else {
					//2-2)입력한 대역폭이 최대대역폭보다 같거나 작은 경우
					//2-2-1)예약정보(reservation) insert 정보 setting
					param.setCorrelation_id("urn:uuid:"+UUID.randomUUID().toString());			//UUID type, 각 request에 대한 response 메시지 식별자 ID, transaction 식별자로 보면됨
					param.setRequester_nsa(this.requesterNSA);
					param.setReply_to(this.reply_to);											//각 response 메시지의 목적지
					param.setGlobal_reservation_id("urn:uuid:" + UUID.randomUUID().toString());	//optional, 네트워크를 통한 각각 연관된 예약요청의 연계성을 나타내는 식별자, end-to-end 연결에 대해서 동일한 값을 가짐
					param.setStart_time(StrUtil.transformDate(new StringBuffer(param.getStart_date()).append(" ").append(param.getStart_hour()).append(":").append(param.getStart_min()).append(":00").toString(), new String[]{"yyyy.MM.dd HH:mm:ss"}));	//예약시간 시작(yyyy-MM-dd HH:mm:ss)
					param.setEnd_time(StrUtil.transformDate(new StringBuffer(param.getEnd_date()).append(" ").append(param.getEnd_hour()).append(":").append(param.getEnd_min()).append(":00").toString(), new String[]{"yyyy.MM.dd HH:mm:ss"}));			//예약시간 종료(yyyy-MM-dd HH:mm:ss)
					param.setMinimum("0");							//대역폭최소값
					param.setMaximum(String.valueOf(maximum));		//대역폭최대값
					
					String[] arrEroNetworkLocal = request.getParameterValues("ero_network_local");
					String[] arrEroOrder = request.getParameterValues("ero_order");
					if(arrEroNetworkLocal!=null && arrEroNetworkLocal.length>0){
						List<Ero> listEro = new ArrayList();
						for(int i=0; i<arrEroNetworkLocal.length; i++){
							Ero ero = new Ero();
							ero.setNetwork_id(StrUtil.split(arrEroNetworkLocal[i], "|")[0]);
							ero.setLocal_id(StrUtil.split(arrEroNetworkLocal[i], "|")[1]);
							ero.setEro_order(Integer.parseInt(arrEroOrder[i]));
							//StrUtil.sysPrint(i+"]"+arrEroNetworkLocal[i]+" // "+arrEroOrder[i]);
							listEro.add(ero);
						}
						param.setList_ero(listEro);
					}
					param.setVersion(0);
					param.setUser_seq(((User)sess.getAttribute(User.SESSION_KEY)).getSeq());	//등록자일련번호
					param.setReserve_fin("N");
					param.setReserve_state("ReserveStart");
					param.setProvision_state("");
					param.setLife_cycle("");
					param.setData_plane("");
					param.setState_nm("Reserve");
                	
					//2-2-2)soap 통신 정보 setting
					/*connectionId*/
                	Holder cid = null;
                	/*globalReservationId*/
                    String gid = param.getGlobal_reservation_id();
                	/*description*/
                    String description = param.getDescription();
                	/*critieria*/            
                    ReservationRequestCriteriaType criteria = new ReservationRequestCriteriaType();
                    criteria.setVersion(param.getVersion());
                    ScheduleType schedule = new ScheduleType();
                    try {
                        Date startTime = param.getStart_time();
                        GregorianCalendar startTimeGregorian = new GregorianCalendar();
                        startTimeGregorian.setTime(startTime);
                        XMLGregorianCalendar startTimeXMLGregorian = DatatypeFactory.newInstance().newXMLGregorianCalendar(startTimeGregorian);
                        Duration sDuration = DatatypeFactory.newInstance().newDuration(true, 0, 0, 0, 0, 0, 0);
                        startTimeXMLGregorian.add(sDuration);
                        schedule.setStartTime(startTimeXMLGregorian);
                        
                        Date endTime = param.getEnd_time();
                        GregorianCalendar endTimeGregorian = new GregorianCalendar();
                        endTimeGregorian.setTime(endTime);
                        XMLGregorianCalendar endTimeXMLGregorian = DatatypeFactory.newInstance().newXMLGregorianCalendar(endTimeGregorian);
                        Duration dDuration = DatatypeFactory.newInstance().newDuration(true, 0, 0, 0, 1, 0, 0);
                        endTimeXMLGregorian.add(dDuration);
                        schedule.setEndTime(endTimeXMLGregorian);    					
                    } catch (DatatypeConfigurationException ex) {
                    	StrUtil.sysPrint("ProviderClient3.main() : DatatypeConfigurationException");
                    	ex.printStackTrace();
                    }
                    criteria.setSchedule(schedule);
                    criteria.setServiceType("http://services.ogf.org/nsi/2013/07/descriptions/EVTS.A-GOLE");
                    /*criteria.setServiceType(param.getType());*/
                    
                    EthernetVlanType evt = new EthernetVlanType();
                    evt.setCapacity(Integer.parseInt(param.getDesired()));
        	            StpType sourceSTP = new StpType();
        	            sourceSTP.setNetworkId(param.getSource_network_id());
        	            StrUtil.sysPrint("source network id="+sourceSTP.getNetworkId());
        	            sourceSTP.setLocalId(param.getSource_local_id());
        	            StrUtil.sysPrint("source local id="+sourceSTP.getLocalId());
        				sourceSTP.setLabels(null);
                    evt.setSourceSTP(sourceSTP);
        				StpType destSTP = new StpType();
        				destSTP.setNetworkId(param.getDest_network_id());
        	            StrUtil.sysPrint("dest network id="+destSTP.getNetworkId());
        				destSTP.setLocalId(param.getDest_local_id());
        	            StrUtil.sysPrint("dest local id="+destSTP.getLocalId());
        				destSTP.setLabels(null);
        			evt.setDestSTP(destSTP);
        			
                    //evt.setEro(null);
        			StpListType stpList = null;
                    if(param.getList_ero()!=null && param.getList_ero().size()>0){
                    	stpList = new StpListType();
                    	List<Ero> list_ero = param.getList_ero();
                    	for(int i=0; i<list_ero.size(); i++){
                    		Ero ero = list_ero.get(i);
                    		StpType eroSTP = new StpType();
                    		eroSTP.setNetworkId(ero.getNetwork_id());
                    		eroSTP.setLocalId(ero.getLocal_id());
                    		OrderedStpType orderedStp = new OrderedStpType();
                    		orderedStp.setStp(eroSTP);
                    		orderedStp.setOrder(ero.getEro_order());
                    		stpList.getOrderedSTP().add(orderedStp);
                    	}
                    }
                    evt.setEro(stpList);        			
                    
                    evt.setMtu(param.getMtu());
        			evt.setBurstsize(param.getBurstsize());
        			StrUtil.sysPrint("directionality="+param.getDirectionality());
        			/*evt.setDirectionality(DirectionalityType.fromValue(param.getDirectionality()));*/
        			/*evt.setDirectionality(DirectionalityType.fromValue("Bidirectional"));*/
        			evt.setDirectionality(DirectionalityType.fromValue(param.getDirectionality()));
        			if(param.getSymmetricPath()==1)
        				evt.setSymmetricPath(true);
        			else
        				evt.setSymmetricPath(false);
        			evt.setSourceVLAN(param.getSourceVLAN());
                    evt.setDestVLAN(param.getDestVLAN());
                    com.netmng.websvc.soap.svc.services.point2point.ObjectFactory of = new com.netmng.websvc.soap.svc.services.point2point.ObjectFactory();
                    JAXBElement<EthernetVlanType> evts = of.createEvts(evt);
                    criteria.getAny().add(evts);
                    criteria.getOtherAttributes().put(new QName("","dynamicKL_srcHostIP"), param.getHost_ip());
                    criteria.getOtherAttributes().put(new QName("","dynamicKL_destHostIP"), param.getDest_ip());
                    /*StrUtil.sysPrint("simpleMode="+request.getParameter("simpleMode"));
                    criteria.getOtherAttributes().put(new QName("","dynamicKL_SimpleMode"), request.getParameter("simpleMode"));*/
                    StrUtil.sysPrint("simpleMode="+param.getSimple_mode());
                    criteria.getOtherAttributes().put(new QName("","dynamicKL_SimpleMode"), param.getSimple_mode());
                    
					if(!new Date().before(param.getStart_time())) {
						result.rejectValue("start_time", "DB01", "com.netmng.vo.Reservation.start_time.compare");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
						mnv.setViewName(ErrorClass.ERRORDATA);
					} 
					// 2-2-3)예약시간_시작 > 예약시간_종료 : 예약정보 insert 및 soap(reserve) 처리 
					else if(param.getStart_time().before(param.getEnd_time())) {
		    			//2-2-3-1)예약정보(reservation) insert
				    	param.setSeq(String.valueOf(this.nsaService.nsaReserve(param)));
				    	try {
					    	if(param.getSeq() != null && !param.getSeq().equals("")){
					    		//2-2-3-2)soap(reserve) 처리 
					    		String sEndPointAddr = request.getParameter("end_point_address");
					    		StrUtil.sysPrint("sEndPointAddr="+sEndPointAddr);
					    		Provider provider = new Provider(param.getCorrelation_id(), param.getProvider_nsa(), param.getReply_to(), sEndPointAddr);
			                	provider.reserve(null, gid, description, criteria);	//최초 reserve 시에는 connectId 없음
			                	StrUtil.sysPrint("provider.reserve end!!");
			                	
					    		mnv.addObject("mode", "RESERVE");
					    		mnv.addObject("seq", param.getSeq());
					    		mnv.setViewName("/nsa/proc/reserve");
					    		StrUtil.sysPrint("view ==>> /nsa/proc/reserve end!!");
					    	} 
				    		/*//2-2-4-4-2)다름 : insert한 예약정보(reservation) delete 후 error
					    	else {
					    		System.out.println("=====>> reserve fail 1)");
					    		// data 삭제
					    		this.nsaService.nsaDelete(param);
					    		result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.reserve.fail");
					    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
								mnv.setViewName(ErrorClass.ERRORDATA);
								
					        	ErrorLog errorLog = new ErrorLog();
					        	errorLog.setUrl("/nsa/proc/reserveIP.do");
					        	errorLog.setName("NSI::reserve");
					        	errorLog.setLog_message("");
					        	this.admService.errorLogInsert(errorLog);
					    	}*/
				    	} catch (Exception e) {
				    		StrUtil.sysPrint("NsaCtrllor.reserveIP() : Provider.reserve Exception");
				    		e.printStackTrace();
				    		// data 삭제
				    		this.nsaService.nsaDelete(param, null);
				    		result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.reserve.fail");
				    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
							mnv.setViewName(ErrorClass.ERRORDATA);
							
				        	ErrorLog errorLog = new ErrorLog();
				        	errorLog.setUrl("/nsa/proc/reserveIP.do");
				        	errorLog.setName("NSI::reserve");
				        	errorLog.setLog_message(e.toString());
				        	this.admService.errorLogInsert(errorLog);
				    	}
					} else {
						result.rejectValue("start_time", "DB01", "com.netmng.vo.Reservation.time.compare");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
						mnv.setViewName(ErrorClass.ERRORDATA);
					}
				}
		    	
			} catch(Exception e) {
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/reserveIP.do");
	        	errorLog.setName("NSI::reserve");
	        	errorLog.setLog_message(e.toString());
	        	System.out.println("=====>> reserveIP() : Exception");
	        	System.out.println("=====>> reserveIP() : errorLog.getLog_message = "+errorLog.getLog_message());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
	        	System.out.println("=====>> reserveIP() : errorLogInsert");
			}
		}
		return mnv;
	}
	
	// 구성,해제, 종결서비스 (리스트)
	/*@RequestMapping(value="/nsa/reserveL")
	public ModelAndView reserveL(
			@Valid SrchReserveParam srchReserveParam,
			String seq,
			BindingResult result, 
			HttpServletRequest request) {
		System.out.println("=====>> reserveL start");
		ModelAndView mnv = new ModelAndView();
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{
				System.out.println("=====>> seq="+seq);
				if(!StrUtil.nullToStr(seq).equals("")) {
					ReservationDTO reservationDTO = this.nsaService.getNsaStatusSelect(seq);
					
					if(reservationDTO.getStatus().equals("C")) {
						if(reservationDTO.getFlag().equals("R")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.reserve.request");
						} else if(reservationDTO.getFlag().equals("C")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.reserve.success");
						} else if(reservationDTO.getFlag().equals("F")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.reserve.fail");
						}
					} else if(reservationDTO.getStatus().equals("P")) {
						if(reservationDTO.getFlag().equals("R")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.provision.request");
						} else if(reservationDTO.getFlag().equals("C")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.provision.success");
						} else if(reservationDTO.getFlag().equals("F")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.provision.fail");
						}
					} else if(reservationDTO.getStatus().equals("R")) {
						if(reservationDTO.getFlag().equals("R")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.release.request");
						} else if(reservationDTO.getFlag().equals("C")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.release.success");
						} else if(reservationDTO.getFlag().equals("F")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.release.fail");
						}
					} else if(reservationDTO.getStatus().equals("T")) {
						if(reservationDTO.getFlag().equals("R")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.terminate.request");
						} else if(reservationDTO.getFlag().equals("C")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.terminate.success");
						} else if(reservationDTO.getFlag().equals("F")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.terminate.fail");
						}
					} 
				}
				this.nsaService.nsaFail();
				this.nsaService.reservationTerminate();
				System.out.println("=====>> getSrchReservationAllCnt");
				srchReserveParam.setTotRow(this.nsaService.getSrchReservationAllCnt(srchReserveParam));
				System.out.println("=====>> getSrchReservationAllList");
				List<ReservationDTO> list = this.nsaService.getSrchReservationAllList(srchReserveParam);
				mnv.addObject("reservationList", list);
				mnv.addObject("srchParam", srchReserveParam);
				mnv.setViewName("/nsa/reserveL");
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		System.out.println("=====>> reserveL end");
		
		return mnv;
	}*/
	@RequestMapping(value="/nsa/reserveL")
	public ModelAndView reserveL(
			@Valid SrchReserveParam srchReserveParam,
			String seq,
			BindingResult result, 
			HttpServletRequest request) {
		System.out.println("=====>> reserveL start");
		ModelAndView mnv = new ModelAndView();
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{
				System.out.println("=====>> nsaL() : seq="+seq);
				
				System.out.println("=====>> nsaL() : reservationTerminate() : 예약종료시간이 지난 예약정보 강제 TERMINATE");
				this.nsaService.reservationTerminate();
				
				System.out.println("=====>> nsaL() : getSrchReservationCnt() : 전체 리스트 카운트");
				srchReserveParam.setTotRow(this.nsaService.getSrchReservationCnt(srchReserveParam));
				
				List<ReservationDTO> list_reserve = this.nsaService.getReserveListSelect();
				mnv.addObject("reservationList", list_reserve);
				mnv.addObject("srchParam", srchReserveParam);
				mnv.setViewName("/nsa/reserveL");
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		System.out.println("=====>> reserveL end");
		
		return mnv;
	}
	
	// 구성,해제, 종결서비스 (상세보기)
	/*@RequestMapping(value="/nsa/reserveV")
	public ModelAndView reserveV(
			ReservationDTO param,
			SrchReserveParam srchReserveParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
				this.nsaService.reservationTerminate();					//예약시간_종료<현재 -> 종료 성공으로 RESERVATION update
				param = this.nsaService.getReservationSelect(param);	//예약정보 조회
				if(param != null && param.getTrouble_yn().equals("N")) {		//1)장애발생 없는 예약정보인 경우
					mnv.addObject("reservationDTO", param);
					mnv.addObject("srchParam", srchReserveParam);
					Date currTime = new Date();
					mnv.addObject("timeFlag", param.getEnd_time().after(currTime) && !param.getStart_time().after(currTime));					
					mnv.setViewName("/nsa/reserveV");
				} else if(param != null && param.getTrouble_yn().equals("Y")) {	//2)장애발생 있는 예약정보인 경우
					result.rejectValue("seq", "DB01", "com.netmng.exception.nsa.reservation_trouble");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				} else {														//3)이외
					result.rejectValue("seq", "DB01", "com.netmng.vo.Reservation.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}*/
	@RequestMapping(value="/nsa/reserveV")
	public ModelAndView reserveV(
			ReservationDTO param,
			SrchReserveParam srchReserveParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
				StrUtil.sysPrint("seq="+param.getSeq());
				this.nsaService.reservationTerminate();					//예약시간_종료<현재 -> 종료 성공으로 RESERVATION update
				param = this.nsaService.getReservationSelect(param);	//예약정보 조회
				List<com.netmng.vo.Ero> list_ero = this.nsaService.getEroSelect(param);
				
				if(param != null){
					StrUtil.sysPrint("param is not null !!!");
					mnv.addObject("reservationDTO", param);
					mnv.addObject("list_ero", list_ero);
					mnv.addObject("srchParam", srchReserveParam);
					Date currTime = new Date();
					mnv.addObject("timeFlag", param.getEnd_time().after(currTime) && !param.getStart_time().after(currTime));					
					mnv.setViewName("/nsa/reserveV");
				} else {														//3)이외
					StrUtil.sysPrint("param is null !!!");
					result.rejectValue("seq", "DB01", "com.netmng.vo.Reservation.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}
	
	// 구성,해제, 종결서비스 (수정화면)
	@RequestMapping(value="/nsa/reserveU")
	public ModelAndView reserveU(
			ReservationDTO param,
			SrchReserveParam srchReserveParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
				StrUtil.sysPrint("seq="+param.getSeq());
				this.nsaService.reservationTerminate();					//예약시간_종료<현재 -> 종료 성공으로 RESERVATION update
				param = this.nsaService.getReservationSelect(param);	//예약정보 조회
				
				if(param != null){
					StrUtil.sysPrint("param is not null !!!");
					
					List<com.netmng.vo.Ero> list_ero = this.nsaService.getEroSelect(param);
					mnv.addObject("list_ero", list_ero);
					
					mnv.addObject("stpVlanList", this.admService.getStpVlanList());
					
					mnv.addObject("reservationDTO", param);
					
					mnv.addObject("srchParam", srchReserveParam);
					
					Date currTime = new Date();
					mnv.addObject("timeFlag", param.getEnd_time().after(currTime) && !param.getStart_time().after(currTime));	
					
					mnv.setViewName("/nsa/reserveU");
				} else {														//3)이외
					StrUtil.sysPrint("param is null !!!");
					result.rejectValue("seq", "DB01", "com.netmng.vo.Reservation.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}
	
	// 구성,해제, 종결서비스 (수정처리)
	@RequestMapping(value="/nsa/proc/reserveUP")
	public ModelAndView reserveUP(
			ReservationDTO param,
			SrchReserveParam srchReserveParam,
			BindingResult result,
			HttpServletRequest request,
			HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		int maximum = 0;
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
				StrUtil.sysPrint("reserveUP start!!");
				StrUtil.sysPrint("seq="+param.getSeq());
				
				//1)최대대역폭을 dynamicKL에서 얻음.
				/*maximum = Integer.parseInt(this.getBandwidth(	param.getSource_stp_seq(), 
																param.getDest_stp_seq(), 
																new StringBuffer(param.getStart_date()).append(" ").append(param.getStart_hour()).append(":").append(param.getStart_min()).toString(), 
																new StringBuffer(param.getEnd_date()).append(" ").append(param.getEnd_hour()).append(":").append(param.getEnd_min()).toString())	);*/
				
				//ReservationDTO oldRevDto = this.nsaService.getReservationSelect(param);	//예약정보 조회
				String sdTime = "";
				sdTime += param.getStart_date()+" "+param.getStart_hour()+":"+param.getStart_min()+":00";
				sdTime += "|";
				sdTime += param.getEnd_date()+" "+param.getEnd_hour()+":"+param.getEnd_min()+":00";
				maximum = Integer.parseInt(this.getBandwidth(	param.getSource_network_id(), 
																param.getSource_local_id(), 
																param.getDest_network_id(), 
																param.getDest_local_id(), 
																sdTime	));	
				//maximum = 10;	/*kisti rest 적용시 삭제*/
				
				//2)대역폭 vs 최대대역폭 비교
				if(maximum < Integer.parseInt(param.getDesired())){
					//2-1)입력한 대역폭이 최대대역폭보다 큰 경우 : error처리
					result.rejectValue("desired", "DB01", "com.netmng.vo.Reservation.desired.maximum");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
					mnv.setViewName(ErrorClass.ERRORDATA);
				} else {
					//2-2)입력한 대역폭이 최대대역폭보다 같거나 작은 경우
					//2-2-1)예약정보(reservation) insert 정보 setting
					param.setCorrelation_id("urn:uuid:"+UUID.randomUUID().toString());
					param.setStart_time(StrUtil.transformDate(new StringBuffer(param.getStart_date()).append(" ").append(param.getStart_hour()).append(":").append(param.getStart_min()).append(":00").toString(), new String[]{"yyyy.MM.dd HH:mm:ss"}));	//예약시간 시작(yyyy-MM-dd HH:mm:ss)
					param.setEnd_time(StrUtil.transformDate(new StringBuffer(param.getEnd_date()).append(" ").append(param.getEnd_hour()).append(":").append(param.getEnd_min()).append(":00").toString(), new String[]{"yyyy.MM.dd HH:mm:ss"}));			//예약시간 종료(yyyy-MM-dd HH:mm:ss)
					param.setMinimum("0");							//대역폭최소값
					param.setMaximum(String.valueOf(maximum));		//대역폭최대값						
					param.setVersion(param.getVersion()+1);
					param.setReserve_fin("N");
					param.setReserve_state("ReserveStart");
					param.setProvision_state("");
					param.setLife_cycle("");
					param.setData_plane("");
					param.setState_nm("Reserve");
					
					StrUtil.sysPrint("getVersion="		+param.getVersion());
					StrUtil.sysPrint("getStart_date="	+param.getStart_date());
					StrUtil.sysPrint("getStart_hour="	+param.getStart_hour());
					StrUtil.sysPrint("getStart_min="	+param.getStart_min());
					StrUtil.sysPrint("getEnd_date="		+param.getEnd_date());
					StrUtil.sysPrint("getEnd_hour="		+param.getEnd_hour());
					StrUtil.sysPrint("getEnd_min="		+param.getEnd_min());					
					StrUtil.sysPrint("getSourceVLAN="	+param.getSourceVLAN());
					StrUtil.sysPrint("getDestVLAN="		+param.getDestVLAN());
					StrUtil.sysPrint("getDesired="		+param.getDesired());
					
					if(!new Date().before(param.getStart_time())) {
						result.rejectValue("start_time", "DB01", "com.netmng.vo.Reservation.start_time.compare");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
						mnv.setViewName(ErrorClass.ERRORDATA);
					} 
					// 2-2-3)예약시간_시작 > 예약시간_종료 : 예약정보 insert 및 soap(reserve) 처리 
					else if(param.getStart_time().before(param.getEnd_time())) {
		    			//2-2-3-1)예약정보(reservation) insert
				    	//param.setSeq(String.valueOf(this.nsaService.nsaModReserve(param)));
						String sModSeq = String.valueOf(this.nsaService.nsaModReserve(param));
						ReservationDTO revDTO = null;
						try {
					    	if(sModSeq != null && !sModSeq.equals("")){
					    		//2-2-2)soap 통신 정보 setting
					    		revDTO = this.nsaService.getReservationSelect(param);	//예약정보 조회
								List<com.netmng.vo.Ero> list_ero = this.nsaService.getEroSelect(param);
								
								/*connectionId*/
			                	Holder cid = new Holder(revDTO.getConnection_id()); 
			                	//param.setConnection_id(revDTO.getConnection_id());
			                	/*globalReservationId*/
			                    String gid = revDTO.getGlobal_reservation_id();	
			                    /*description*/
			                    String description = revDTO.getDescription();
			                    /*critieria*/            
			                    ReservationRequestCriteriaType criteria = new ReservationRequestCriteriaType();
			                    criteria.setVersion(param.getVersion());
			                    ScheduleType schedule = new ScheduleType();
			                    try {
			                        Date startTime = param.getStart_time();
			                        GregorianCalendar startTimeGregorian = new GregorianCalendar();
			                        startTimeGregorian.setTime(startTime);
			                        XMLGregorianCalendar startTimeXMLGregorian = DatatypeFactory.newInstance().newXMLGregorianCalendar(startTimeGregorian);
			                        Duration sDuration = DatatypeFactory.newInstance().newDuration(true, 0, 0, 0, 0, 0, 0);
			                        startTimeXMLGregorian.add(sDuration);
			                        schedule.setStartTime(startTimeXMLGregorian);
			                        
			                        Date endTime = param.getEnd_time();
			                        GregorianCalendar endTimeGregorian = new GregorianCalendar();
			                        endTimeGregorian.setTime(endTime);
			                        XMLGregorianCalendar endTimeXMLGregorian = DatatypeFactory.newInstance().newXMLGregorianCalendar(endTimeGregorian);
			                        Duration dDuration = DatatypeFactory.newInstance().newDuration(true, 0, 0, 0, 1, 0, 0);
			                        endTimeXMLGregorian.add(dDuration);
			                        schedule.setEndTime(endTimeXMLGregorian);    					
			                    } catch (DatatypeConfigurationException ex) {
			                    	ex.printStackTrace();
			                    }
			                    criteria.setSchedule(schedule);
			                    criteria.setServiceType("http://services.ogf.org/nsi/2013/07/descriptions/EVTS.A-GOLE");
			                    
			                    EthernetVlanType evt = new EthernetVlanType();
			                    evt.setCapacity(Integer.parseInt(param.getDesired()));
			        	            StpType sourceSTP = new StpType();
			        	            sourceSTP.setNetworkId(revDTO.getSource_network_id());
			        	            StrUtil.sysPrint("source network id="+sourceSTP.getNetworkId());
			        	            sourceSTP.setLocalId(revDTO.getSource_local_id());
			        	            StrUtil.sysPrint("source local id="+sourceSTP.getLocalId());
			        				sourceSTP.setLabels(null);
			                    evt.setSourceSTP(sourceSTP);
			        				StpType destSTP = new StpType();
			        				destSTP.setNetworkId(revDTO.getDest_network_id());
			        	            StrUtil.sysPrint("dest network id="+destSTP.getNetworkId());
			        				destSTP.setLocalId(revDTO.getDest_local_id());
			        	            StrUtil.sysPrint("dest local id="+destSTP.getLocalId());
			        				destSTP.setLabels(null);
			        			evt.setDestSTP(destSTP);
			        			
			        			StpListType stpList = null;
			                    if(list_ero!=null && list_ero.size()>0){
			                    	stpList = new StpListType();
			                    	for(int i=0; i<list_ero.size(); i++){
			                    		Ero ero = list_ero.get(i);
			                    		StpType eroSTP = new StpType();
			                    		eroSTP.setNetworkId(ero.getNetwork_id());
			                    		eroSTP.setLocalId(ero.getLocal_id());
			                    		OrderedStpType orderedStp = new OrderedStpType();
			                    		orderedStp.setStp(eroSTP);
			                    		orderedStp.setOrder(ero.getEro_order());
			                    		stpList.getOrderedSTP().add(orderedStp);
			                    	}
			                    }
			                    evt.setEro(stpList); 
			                    
			                    evt.setMtu(revDTO.getMtu());
			        			evt.setBurstsize(revDTO.getBurstsize());
			        			StrUtil.sysPrint("directionality="+revDTO.getDirectionality());
			        			evt.setDirectionality(DirectionalityType.fromValue(revDTO.getDirectionality()));
			        			/*evt.setDirectionality(DirectionalityType.fromValue("Bidirectional"));*/
			        			evt.setSourceVLAN(param.getSourceVLAN());
			                    evt.setDestVLAN(param.getDestVLAN());
			                    com.netmng.websvc.soap.svc.services.point2point.ObjectFactory of = new com.netmng.websvc.soap.svc.services.point2point.ObjectFactory();
			                    JAXBElement<EthernetVlanType> evts = of.createEvts(evt);
			                    criteria.getAny().add(evts);
			                    criteria.getOtherAttributes().put(new QName("","dynamicKL_srcHostIP"), revDTO.getHost_ip());
			                    criteria.getOtherAttributes().put(new QName("","dynamicKL_destHostIP"), revDTO.getDest_ip());
			                    StrUtil.sysPrint("simpleMode="+revDTO.getSimple_mode());
			                    criteria.getOtherAttributes().put(new QName("","dynamicKL_SimpleMode"), revDTO.getSimple_mode());
			                    
			                    ProviderNsa providerNsa = new ProviderNsa();
			                    providerNsa.setNsa_addr(revDTO.getProvider_nsa());
			                    String sEndPointAddr = this.admService.getProviderNsaSelect(providerNsa).getEndpoint_addr();
			                    Provider provider = new Provider(param.getCorrelation_id(), revDTO.getProvider_nsa(), revDTO.getReply_to(), sEndPointAddr);
			                    provider.reserve(cid, gid, description, criteria);	//최초 reserve 시에는 connectId 없음
			                	StrUtil.sysPrint("provider.reserve end!!");	                    
			                    
					    		param.setSeq(sModSeq);
					    		mnv.addObject("mode", "RESERVE");
					    		mnv.addObject("seq", param.getSeq());
					    		mnv.setViewName("/nsa/proc/reserve");
					    		StrUtil.sysPrint("view ==>> /nsa/proc/reserve end!!");
					    	}
						} catch (Exception e) {
				    		StrUtil.sysPrint("NsaCtrllor.reserveUP() : Provider.reserve Exception");
				    		e.printStackTrace();
				    		// data 삭제
				    		param.setSeq(sModSeq);
				    		this.nsaService.nsaDelete(param, revDTO.getSeq());
				    		result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.reserve.fail");
				    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
							mnv.setViewName(ErrorClass.ERRORDATA);
							
				        	ErrorLog errorLog = new ErrorLog();
				        	errorLog.setUrl("/nsa/proc/reserveIP.do");
				        	errorLog.setName("NSI::reserve");
				        	errorLog.setLog_message(e.toString());
				        	this.admService.errorLogInsert(errorLog);
				    	}	
					} else {
						result.rejectValue("start_time", "DB01", "com.netmng.vo.Reservation.time.compare");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
						mnv.setViewName(ErrorClass.ERRORDATA);
					}

					//param = this.nsaService.getReservationSelect(param);	//예약정보 조회
					
					//2-2)입력한 대역폭이 최대대역폭보다 같거나 작은 경우
					//2-2-1)예약정보(reservation) insert 정보 setting
					
					/*ReservationDTO modParam = new ReservationDTO();			//수정 예약정보
					modParam.setCorrelation_id("urn:uuid:"+UUID.randomUUID().toString());
					modParam.setRequester_nsa(param.getRequester_nsa());
					modParam.setReply_to(param.getReply_to());											
					modParam.setGlobal_reservation_id(param.getGlobal_reservation_id());	
					modParam.setStart_time(param.getStart_time());	
					modParam.setEnd_time(param.get);
					modParam.setMinimum("0");							//대역폭최소값
					modParam.setMaximum(String.valueOf(maximum));		//대역폭최대값*/

					//2-2-2)soap 통신 정보 setting

				}
				
				
				/*this.nsaService.reservationTerminate();					//예약시간_종료<현재 -> 종료 성공으로 RESERVATION update
				param = this.nsaService.getReservationSelect(param);	//예약정보 조회
				
				if(param != null){
					StrUtil.sysPrint("param is not null !!!");
					
					List<com.netmng.vo.Ero> list_ero = this.nsaService.getEroSelect(param);
					mnv.addObject("list_ero", list_ero);
					
					mnv.addObject("stpVlanList", this.admService.getStpVlanList());
					
					mnv.addObject("reservationDTO", param);
					
					mnv.addObject("srchParam", srchReserveParam);
					
					Date currTime = new Date();
					mnv.addObject("timeFlag", param.getEnd_time().after(currTime) && !param.getStart_time().after(currTime));	
					
					mnv.setViewName("/nsa/reserveU");
				} else {														//3)이외
					StrUtil.sysPrint("param is null !!!");
					result.rejectValue("seq", "DB01", "com.netmng.vo.Reservation.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}*/
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/reserveUP.do");
	        	errorLog.setName("NSI::reserve");
	        	errorLog.setLog_message(e.toString());
	        	System.out.println("=====>> reserveUP() : Exception");
	        	System.out.println("=====>> reserveUP() : errorLog.getLog_message = "+errorLog.getLog_message());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
	        	System.out.println("=====>> reserveUP() : errorLogInsert");
			}
		}
		return mnv;
	}
	
	// 구성,해제, 종결서비스 (예약취소)
	@RequestMapping(value="/nsa/proc/reserveAbortP")
	public ModelAndView reserveAbortP(
			ReservationDTO param,
			SrchReserveParam srchReserveParam,
			BindingResult result,
			HttpServletRequest request,
			HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			String sConnectionId= "";
			String sOldStateNm	= "";
			try {
				StrUtil.sysPrint("seq="+param.getSeq());

	    		ReservationDTO revDTO = this.nsaService.getReservationSelect(param);	//예약정보 조회

				//2-2-1)reserve 상태명(state_nm) update
	    		sConnectionId	= revDTO.getConnection_id();
	    		sOldStateNm		= revDTO.getState_nm();
				this.nsaService.updateReserveState(null, null, null, null, "ReserveAborted", sConnectionId);
				
				//2-2-2)soap 통신 정보 setting
	    		ProviderNsa providerNsa = new ProviderNsa();
                providerNsa.setNsa_addr(revDTO.getProvider_nsa());                
                String sEndPointAddr = this.admService.getProviderNsaSelect(providerNsa).getEndpoint_addr();
                
                Provider provider = new Provider(param.getCorrelation_id(), revDTO.getProvider_nsa(), revDTO.getReply_to(), sEndPointAddr);
                provider.reserveAbort(revDTO.getConnection_id());	
            	StrUtil.sysPrint("provider.reserveAbort end!!");	
            	
            	mnv.addObject("mode", "RESERVE");
	    		mnv.addObject("seq", param.getSeq());
	    		mnv.setViewName("/nsa/proc/reserve");
			} catch (Exception e) {
				try{
					this.nsaService.updateReserveState(null, null, null, null, sOldStateNm, sConnectionId);
				}catch(Exception ex){
					e.printStackTrace();
					result.rejectValue("seq", "DB01", "com.netmng.exception.db");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
					
		        	ErrorLog errorLog = new ErrorLog();
		        	errorLog.setUrl("/nsa/proc/reserveAbortP.do");
		        	errorLog.setName("NSI::reserve");
		        	errorLog.setLog_message(e.toString());
		        	e.printStackTrace();
		        	this.admService.errorLogInsert(errorLog);
				}
				e.printStackTrace();
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/reserveAbortP.do");
	        	errorLog.setName("NSI::reserve");
	        	errorLog.setLog_message(e.toString());
	        	System.out.println("=====>> reserveAbortP() : Exception");
	        	System.out.println("=====>> reserveAbortP() : errorLog.getLog_message = "+errorLog.getLog_message());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
	        	System.out.println("=====>> reserveAbortP() : errorLogInsert");
			}
		}
		return mnv;
	}
	
	// 구성,해제, 종결서비스 (예약확정)
	@RequestMapping(value="/nsa/proc/reserveCommitP")
	public ModelAndView reserveCommitP(
			ReservationDTO param,
			SrchReserveParam srchReserveParam,
			BindingResult result,
			HttpServletRequest request,
			HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			String sConnectionId= "";
			String sOldStateNm	= "";
			try {
				StrUtil.sysPrint("seq="+param.getSeq());
				
	    		ReservationDTO revDTO = this.nsaService.getReservationSelect(param);	//예약정보 조회

	    		//2-2-1)reserve 상태명(state_nm) update
	    		sConnectionId	= revDTO.getConnection_id();
	    		sOldStateNm		= revDTO.getState_nm();
				this.nsaService.updateReserveState(null, null, null, null, "ReserveCommited", sConnectionId);
				
				//2-2-2)soap 통신 정보 setting
	    		ProviderNsa providerNsa = new ProviderNsa();
                providerNsa.setNsa_addr(revDTO.getProvider_nsa());                
                String sEndPointAddr = this.admService.getProviderNsaSelect(providerNsa).getEndpoint_addr();
                
                Provider provider = new Provider(param.getCorrelation_id(), revDTO.getProvider_nsa(), revDTO.getReply_to(), sEndPointAddr);
                provider.reserveCommit(revDTO.getConnection_id());	
            	StrUtil.sysPrint("provider.reserveCommit end!!");	
            	
            	mnv.addObject("mode", "RESERVE");
	    		mnv.addObject("seq", param.getSeq());
	    		mnv.setViewName("/nsa/proc/reserve");
			} catch (Exception e) {
				try{
					this.nsaService.updateReserveState(null, null, null, null, sOldStateNm, sConnectionId);
				}catch(Exception ex){
					e.printStackTrace();
					result.rejectValue("seq", "DB01", "com.netmng.exception.db");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
					
		        	ErrorLog errorLog = new ErrorLog();
		        	errorLog.setUrl("/nsa/proc/reserveAbortP.do");
		        	errorLog.setName("NSI::reserve");
		        	errorLog.setLog_message(e.toString());
		        	e.printStackTrace();
		        	this.admService.errorLogInsert(errorLog);
				}
				e.printStackTrace();
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/reserveAbortP.do");
	        	errorLog.setName("NSI::reserve");
	        	errorLog.setLog_message(e.toString());
	        	System.out.println("=====>> reserveCommitP() : Exception");
	        	System.out.println("=====>> reserveCommitP() : errorLog.getLog_message = "+errorLog.getLog_message());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
	        	System.out.println("=====>> reserveCommitP() : errorLogInsert");
			}
		}
		return mnv;
	}
		
	// 구성 
	/*@RequestMapping(value="/nsa/proc/provisionP")
	public ModelAndView provisionP(
			ReservationDTO data,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(data, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else {
			try {
				data = this.nsaService.getReservationSelect(data);
				if(data != null && data.getTrouble_yn().equals("N")) {	장애가 없는 경우
					Date currTime = new Date();
					if(data.getStatus().equals("P")) {						상태=구성
						result.rejectValue("seq", "DB01", "com.netmng.ctrl.nsa.provision.duliicated");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
						mnv.setViewName(ErrorClass.ERRORDATA);
					} else if(!(data.getEnd_time().after(currTime) && !data.getStart_time().after(currTime))){
						result.rejectValue("seq", "DB01", "com.netmng.ctrl.nsa.provision.time.compare");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
						mnv.setViewName(ErrorClass.ERRORDATA);
					} else {
						Provider provider = new Provider();
						
						Provision provision = new Provision();
						provision.setCorrelationId(data.getCorrelation_id());
						provision.setReplyTo(data.getReply_to());
						provision.setRequesterNSA(data.getRequester_nsa());
						provision.setProviderNSA(data.getProvider_nsa());
						provision.setConnectionId(data.getConnection_id());
						
						String status = data.getStatus();
						String flag = data.getFlag();
						
						this.nsaService.nsaProvision(data);
						
						try{
							if(data.getCorrelation_id().equals(provider.provision(provision))) {
								mnv.addObject("mode", "PROVISION");
					    		mnv.addObject("seq", data.getSeq());
								mnv.setViewName("/nsa/proc/reserve");
							} else {
								data.setStatus(status);
								data.setFlag(flag);
								this.nsaService.nsaStatusUpdate(data);
					    		result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.provision.fail");
					    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
								mnv.setViewName(ErrorClass.ERRORDATA);
								
					        	ErrorLog errorLog = new ErrorLog();
					        	errorLog.setUrl("/nsa/proc/provisionP.do");
					        	errorLog.setName("NSI::provision");
					        	errorLog.setLog_message("");
					        	this.admService.errorLogInsert(errorLog);
							}
						} catch(Exception e) {
							data.setStatus(status);
							data.setFlag(flag);
							this.nsaService.nsaStatusUpdate(data);
							result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.provision.fail");
				    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
							mnv.setViewName(ErrorClass.ERRORDATA);
							
				        	ErrorLog errorLog = new ErrorLog();
				        	errorLog.setUrl("/nsa/proc/provisionP.do");
				        	errorLog.setName("NSI::provision");
				        	errorLog.setLog_message(e.toString());
				        	this.admService.errorLogInsert(errorLog);
						}
					}
				} else if(data != null && data.getTrouble_yn().equals("Y")) {
					result.rejectValue("seq", "DB01", "com.netmng.exception.nsa.reservation_trouble");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				} else {
					result.rejectValue("seq", "DB01", "com.netmng.vo.Reservation.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/provisionP.do");
	        	errorLog.setName("NSI::provision");
	        	errorLog.setLog_message(e.toString());
	        	this.admService.errorLogInsert(errorLog);
			}
		}
		
		return mnv;
	}*/
	@RequestMapping(value="/nsa/proc/provisionP")
	public ModelAndView provisionP(
			ReservationDTO param,
			SrchReserveParam srchReserveParam,
			BindingResult result,
			HttpServletRequest request,
			HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			String sConnectionId= "";
			String sOldStateNm	= "";
			try {
				StrUtil.sysPrint("seq="+param.getSeq());
				
	    		ReservationDTO revDTO = this.nsaService.getReservationSelect(param);	//예약정보 조회

	    		//2-2-1)reserve 상태명(state_nm) update
	    		sConnectionId	= revDTO.getConnection_id();
	    		sOldStateNm		= revDTO.getState_nm();
				this.nsaService.updateReserveState(null, null, null, null, "Provision", sConnectionId);
				
				//2-2-2)soap 통신 정보 setting
	    		ProviderNsa providerNsa = new ProviderNsa();
                providerNsa.setNsa_addr(revDTO.getProvider_nsa());                
                String sEndPointAddr = this.admService.getProviderNsaSelect(providerNsa).getEndpoint_addr();
                
                Provider provider = new Provider(param.getCorrelation_id(), revDTO.getProvider_nsa(), revDTO.getReply_to(), sEndPointAddr);
                provider.provision(revDTO.getConnection_id());	
            	StrUtil.sysPrint("provider.provision end!!");	
            	
            	mnv.addObject("mode", "RESERVE");
	    		mnv.addObject("seq", param.getSeq());
	    		mnv.setViewName("/nsa/proc/reserve");
			} catch (Exception e) {
				try{
					this.nsaService.updateReserveState(null, null, null, null, sOldStateNm, sConnectionId);
				}catch(Exception ex){
					e.printStackTrace();
					result.rejectValue("seq", "DB01", "com.netmng.exception.db");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
					
		        	ErrorLog errorLog = new ErrorLog();
		        	errorLog.setUrl("/nsa/proc/provisionP.do");
		        	errorLog.setName("NSI::reserve");
		        	errorLog.setLog_message(e.toString());
		        	e.printStackTrace();
		        	this.admService.errorLogInsert(errorLog);
				}
				e.printStackTrace();
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/provisionP.do");
	        	errorLog.setName("NSI::reserve");
	        	errorLog.setLog_message(e.toString());
	        	System.out.println("=====>> provisionP() : Exception");
	        	System.out.println("=====>> provisionP() : errorLog.getLog_message = "+errorLog.getLog_message());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
	        	System.out.println("=====>> provisionP() : errorLogInsert");
			}
		}
		return mnv;
	}
		
	// 해제
	/*@RequestMapping(value="/nsa/proc/releaseP")
	public ModelAndView releaseP(
			ReservationDTO data,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(data, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else {
			try {
				data = this.nsaService.getReservationSelect(data);
				if(data != null && data.getTrouble_yn().equals("N")) {	장애가 없는 경우
					Date currTime = new Date();
					if(data.getStatus().equals("P")) {						상태=구성
						Provider provider = new Provider();
						
						Release release = new Release();
						release.setCorrelationId(data.getCorrelation_id());
						release.setReplyTo(data.getReply_to());
						release.setRequesterNSA(data.getRequester_nsa());
						release.setProviderNSA(data.getProvider_nsa());
						release.setConnectionId(data.getConnection_id());
						
						String status = data.getStatus();
						String flag = data.getFlag();
						
						this.nsaService.nsaRelease(data);
						
						try{
							if(data.getCorrelation_id().equals(provider.release(release))) {	
								mnv.addObject("mode", "RELEASE");
					    		mnv.addObject("seq", data.getSeq());
					    		mnv.setViewName("/nsa/proc/reserve");
					    	} else {
					    		data.setStatus(status);
								data.setFlag(flag);
								this.nsaService.nsaStatusUpdate(data);
					    		result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.release.fail");
					    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
								mnv.setViewName(ErrorClass.ERRORDATA);
								
					        	ErrorLog errorLog = new ErrorLog();
					        	errorLog.setUrl("/nsa/proc/releaseP.do");
					        	errorLog.setName("NSI::release");
					        	errorLog.setLog_message("");
					        	this.admService.errorLogInsert(errorLog);
					    	}
						} catch(Exception e) {
							data.setStatus(status);
							data.setFlag(flag);
							this.nsaService.nsaStatusUpdate(data);
							result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.release.fail");
				    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
							mnv.setViewName(ErrorClass.ERRORDATA);
							
				        	ErrorLog errorLog = new ErrorLog();
				        	errorLog.setUrl("/nsa/proc/releaseP.do");
				        	errorLog.setName("NSI::release");
				        	errorLog.setLog_message(e.toString());
				        	this.admService.errorLogInsert(errorLog);
						}
						
					} else if(!(data.getEnd_time().after(currTime) && !data.getStart_time().after(currTime))){
						result.rejectValue("seq", "DB01", "com.netmng.ctrl.nsa.release.time.compare");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
						mnv.setViewName(ErrorClass.ERRORDATA);
					} else {
						result.rejectValue("seq", "DB01", "com.netmng.ctrl.nsa.release.duliicated");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
						mnv.setViewName(ErrorClass.ERRORDATA);
					}
				} else if(data != null && data.getTrouble_yn().equals("Y")) {
					result.rejectValue("seq", "DB01", "com.netmng.exception.nsa.reservation_trouble");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				} else {
					result.rejectValue("seq", "DB01", "com.netmng.vo.Reservation.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/releaseP.do");
	        	errorLog.setName("NSI::release");
	        	errorLog.setLog_message(e.toString());
	        	this.admService.errorLogInsert(errorLog);
			}
		}
		
		return mnv;
	}*/
	@RequestMapping(value="/nsa/proc/releaseP")
	public ModelAndView releaseP(
			ReservationDTO param,
			SrchReserveParam srchReserveParam,
			BindingResult result,
			HttpServletRequest request,
			HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			String sConnectionId= "";
			String sOldStateNm	= "";
			try {
				StrUtil.sysPrint("seq="+param.getSeq());
				
	    		ReservationDTO revDTO = this.nsaService.getReservationSelect(param);	//예약정보 조회

	    		//2-2-1)reserve 상태명(state_nm) update
	    		sConnectionId	= revDTO.getConnection_id();
	    		sOldStateNm		= revDTO.getState_nm();
				this.nsaService.updateReserveState(null, null, null, null, "Release", sConnectionId);
				
				//2-2-2)soap 통신 정보 setting
	    		ProviderNsa providerNsa = new ProviderNsa();
                providerNsa.setNsa_addr(revDTO.getProvider_nsa());                
                String sEndPointAddr = this.admService.getProviderNsaSelect(providerNsa).getEndpoint_addr();
                
                Provider provider = new Provider(param.getCorrelation_id(), revDTO.getProvider_nsa(), revDTO.getReply_to(), sEndPointAddr);
                provider.release(revDTO.getConnection_id());	
            	StrUtil.sysPrint("provider.release end!!");	
            	
            	mnv.addObject("mode", "RESERVE");
	    		mnv.addObject("seq", param.getSeq());
	    		mnv.setViewName("/nsa/proc/reserve");
			} catch (Exception e) {
				try{
					this.nsaService.updateReserveState(null, null, null, null, sOldStateNm, sConnectionId);
				}catch(Exception ex){
					e.printStackTrace();
					result.rejectValue("seq", "DB01", "com.netmng.exception.db");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
					
		        	ErrorLog errorLog = new ErrorLog();
		        	errorLog.setUrl("/nsa/proc/releaseP.do");
		        	errorLog.setName("NSI::reserve");
		        	errorLog.setLog_message(e.toString());
		        	e.printStackTrace();
		        	this.admService.errorLogInsert(errorLog);
				}
				e.printStackTrace();
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/releaseP.do");
	        	errorLog.setName("NSI::reserve");
	        	errorLog.setLog_message(e.toString());
	        	System.out.println("=====>> releaseP() : Exception");
	        	System.out.println("=====>> releaseP() : errorLog.getLog_message = "+errorLog.getLog_message());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
	        	System.out.println("=====>> releaseP() : errorLogInsert");
			}
		}
		return mnv;
	}
	// 종결
	/*@RequestMapping(value="/nsa/proc/terminateP")
	public ModelAndView terminateP(
			ReservationDTO data,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(data, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else {
			try {
				data = this.nsaService.getReservationSelect(data);
				if(data != null && data.getTrouble_yn().equals("N")) {
					Provider provider = new Provider();
					
					Terminate terminate = new Terminate();
					terminate.setCorrelationId(data.getCorrelation_id());
					terminate.setReplyTo(data.getReply_to());
					terminate.setRequesterNSA(data.getRequester_nsa());
					terminate.setProviderNSA(data.getProvider_nsa());
					terminate.setConnectionId(data.getConnection_id());

					String status = data.getStatus();
					String flag = data.getFlag();
					
					this.nsaService.nsaTerminate(data);
					
					try{
						if(data.getCorrelation_id().equals(provider.terminate(terminate))) {
							mnv.addObject("mode", "TERMINATE");
				    		mnv.addObject("seq", data.getSeq());
				    		mnv.setViewName("/nsa/proc/reserve");
				    	} else {
				    		data.setStatus(status);
							data.setFlag(flag);
							this.nsaService.nsaStatusUpdate(data);
				    		result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.terminate.fail");
				    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
							mnv.setViewName(ErrorClass.ERRORDATA);
							
				        	ErrorLog errorLog = new ErrorLog();
				        	errorLog.setUrl("/nsa/proc/terminateP.do");
				        	errorLog.setName("NSI::terminate");
				        	errorLog.setLog_message("");
				        	this.admService.errorLogInsert(errorLog);
				    	}
					} catch(Exception e) {
						data.setStatus(status);
						data.setFlag(flag);
						this.nsaService.nsaStatusUpdate(data);
						result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.terminate.fail");
			    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
						mnv.setViewName(ErrorClass.ERRORDATA);
						
			        	ErrorLog errorLog = new ErrorLog();
			        	errorLog.setUrl("/nsa/proc/terminateP.do");
			        	errorLog.setName("NSI::terminate");
			        	errorLog.setLog_message(e.toString());
			        	this.admService.errorLogInsert(errorLog);
					}
					
				} else if(data != null && data.getTrouble_yn().equals("Y")) {
					result.rejectValue("seq", "DB01", "com.netmng.exception.nsa.reservation_trouble");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				} else {
					result.rejectValue("seq", "DB01", "com.netmng.vo.Reservation.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/terminateP.do");
	        	errorLog.setName("NSI::terminate");
	        	errorLog.setLog_message(e.toString());
	        	this.admService.errorLogInsert(errorLog);
			}
		}
		
		return mnv;
	}*/
	@RequestMapping(value="/nsa/proc/terminateP")
	public ModelAndView terminateP(
			ReservationDTO param,
			SrchReserveParam srchReserveParam,
			BindingResult result,
			HttpServletRequest request,
			HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			String sConnectionId= "";
			String sOldStateNm	= "";
			try {
				StrUtil.sysPrint("seq="+param.getSeq());
				
	    		ReservationDTO revDTO = this.nsaService.getReservationSelect(param);	//예약정보 조회

	    		//2-2-1)reserve 상태명(state_nm) update
	    		sConnectionId	= revDTO.getConnection_id();
	    		sOldStateNm		= revDTO.getState_nm();
				this.nsaService.updateReserveState(null, null, null, null, "Terminate", sConnectionId);
				
				//2-2-2)soap 통신 정보 setting
	    		ProviderNsa providerNsa = new ProviderNsa();
                providerNsa.setNsa_addr(revDTO.getProvider_nsa());                
                String sEndPointAddr = this.admService.getProviderNsaSelect(providerNsa).getEndpoint_addr();
                
                Provider provider = new Provider(param.getCorrelation_id(), revDTO.getProvider_nsa(), revDTO.getReply_to(), sEndPointAddr);
                provider.terminate(revDTO.getConnection_id());	
            	StrUtil.sysPrint("provider.terminateP end!!");	
            	
            	mnv.addObject("mode", "RESERVE");
	    		mnv.addObject("seq", param.getSeq());
	    		mnv.setViewName("/nsa/proc/reserve");
			} catch (Exception e) {
				try{
					this.nsaService.updateReserveState(null, null, null, null, sOldStateNm, sConnectionId);
				}catch(Exception ex){
					e.printStackTrace();
					result.rejectValue("seq", "DB01", "com.netmng.exception.db");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
					
		        	ErrorLog errorLog = new ErrorLog();
		        	errorLog.setUrl("/nsa/proc/terminateP.do");
		        	errorLog.setName("NSI::reserve");
		        	errorLog.setLog_message(e.toString());
		        	e.printStackTrace();
		        	this.admService.errorLogInsert(errorLog);
				}
				e.printStackTrace();
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/terminateP.do");
	        	errorLog.setName("NSI::reserve");
	        	errorLog.setLog_message(e.toString());
	        	System.out.println("=====>> terminateP() : Exception");
	        	System.out.println("=====>> terminateP() : errorLog.getLog_message = "+errorLog.getLog_message());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
	        	System.out.println("=====>> terminateP() : errorLogInsert");
			}
		}
		return mnv;
	}
	
	// querySummary
	@RequestMapping(value="/nsa/proc/querySummaryP")
	public ModelAndView querySummaryP(
			ReservationDTO param,
			SrchReserveParam srchReserveParam,
			BindingResult result,
			HttpServletRequest request,
			HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
				StrUtil.sysPrint("seq="+param.getSeq());
				
				//2-2-2)soap 통신 정보 setting
	    		ReservationDTO revDTO = this.nsaService.getReservationSelect(param);	//예약정보 조회
	    		ProviderNsa providerNsa = new ProviderNsa();
                providerNsa.setNsa_addr(revDTO.getProvider_nsa());                
                String sEndPointAddr = this.admService.getProviderNsaSelect(providerNsa).getEndpoint_addr();
                
                Provider provider = new Provider(param.getCorrelation_id(), revDTO.getProvider_nsa(), revDTO.getReply_to(), sEndPointAddr);
                com.netmng.websvc.soap.param.types.QueryType querySummary = new com.netmng.websvc.soap.param.types.QueryType();
                querySummary.getConnectionId().add(revDTO.getConnection_id());
                provider.querySummary(querySummary);	
            	StrUtil.sysPrint("provider.querySummaryP end!!");
            	
            	Thread.sleep(200);

            	List<com.netmng.websvc.soap.param.types.QuerySummaryResultType> queryReuslt = com.netmng.vo.QueryResult.queryResultResv;
            	mnv.addObject("queryResult", queryReuslt);
            	
            	
            	
            	/*for(int i=0; i<queryReuslt.size(); i++){
            		com.netmng.websvc.soap.param.types.QuerySummaryResultType querySummaryResultType = queryReuslt.get(i);
            		
            		com.netmng.websvc.soap.param.types.ObjectFactory chtOF = new com.netmng.websvc.soap.param.types.ObjectFactory();
                    JAXBElement<com.netmng.websvc.soap.param.types.QuerySummaryResultType> chtElement = chtOF.createQuerySummary
            		JAXBElement<CommonHeaderType> chtElement = 
            		
	            	java.io.StringWriter sw = new java.io.StringWriter();
	        		//JAXBContext jaxbContext = JAXBContext.newInstance(CommonHeaderType.class);
	            	JAXBContext jaxbContext = JAXBContext.newInstance(com.netmng.websvc.soap.param.types.QuerySummaryResultType.class);
	        		
	        		Marshaller marshaller = jaxbContext.createMarshaller();
	        		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	        		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        		marshaller.marshal(querySummaryResultType, sw);
	        		//sw.close();
	        		StrUtil.sysPrint("sw.toString()="+sw.toString());
	        		//StrUtil.sysPrint("sw.toString()="+querySummaryResultType.toString());
            		
            	}*/
            	
            	
            	
            	
            	
            	
            	
	    		mnv.addObject("seq", param.getSeq());
	    		mnv.setViewName("/nsa/reserveQuery");
	    		StrUtil.sysPrint("provider.querySummaryP go query view!!");
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/querySummaryP.do");
	        	errorLog.setName("NSI::reserve");
	        	errorLog.setLog_message(e.toString());
	        	System.out.println("=====>> querySummaryP() : Exception");
	        	System.out.println("=====>> querySummaryP() : errorLog.getLog_message = "+errorLog.getLog_message());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
	        	System.out.println("=====>> querySummaryP() : errorLogInsert");
			}
		}
		return mnv;
	}
	
	// 조회 서비스
	/*@RequestMapping(value="/nsa/nsaL")
	public ModelAndView nsaL(
			@Valid SrchReserveParam srchReserveParam,
			String seq,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{
				System.out.println("=====>> nsaL() : seq="+seq);
				if(!StrUtil.nullToStr(seq).equals("")) {
					ReservationDTO reservationDTO = this.nsaService.getNsaStatusSelect(seq);
					
					if(reservationDTO.getStatus().equals("C")) {
						if(reservationDTO.getFlag().equals("R")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.reserve.request");
						} else if(reservationDTO.getFlag().equals("C")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.reserve.success");
						} else if(reservationDTO.getFlag().equals("F")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.reserve.fail");
						}
					} else if(reservationDTO.getStatus().equals("P")) {
						if(reservationDTO.getFlag().equals("R")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.provision.request");
						} else if(reservationDTO.getFlag().equals("C")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.provision.success");
						} else if(reservationDTO.getFlag().equals("F")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.provision.fail");
						}
					} else if(reservationDTO.getStatus().equals("R")) {
						if(reservationDTO.getFlag().equals("R")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.release.request");
						} else if(reservationDTO.getFlag().equals("C")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.release.success");
						} else if(reservationDTO.getFlag().equals("F")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.release.fail");
						}
					} else if(reservationDTO.getStatus().equals("T")) {
						if(reservationDTO.getFlag().equals("R")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.terminate.request");
						} else if(reservationDTO.getFlag().equals("C")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.terminate.success");
						} else if(reservationDTO.getFlag().equals("F")) {
							mnv.addObject("reservationMsg", "com.netmng.ctrl.nsa.terminate.fail");
						}
					} 
				}
				
				System.out.println("=====>> nsaL() : nsaFail()");
				this.nsaService.nsaFail();
				System.out.println("=====>> nsaL() : reservationTerminate()");
				this.nsaService.reservationTerminate();
				System.out.println("=====>> nsaL() : getSrchReservationCnt()");
				srchReserveParam.setTotRow(this.nsaService.getSrchReservationCnt(srchReserveParam));
				System.out.println("=====>> nsaL() : getSrchReservationList()");
				List<ReservationDTO> list = this.nsaService.getSrchReservationList(srchReserveParam);
				mnv.addObject("nsaList", list);
				mnv.addObject("srchParam", srchReserveParam);
				mnv.setViewName("/nsa/nsaL");
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}*/
	@RequestMapping(value="/nsa/nsaL")
	public ModelAndView nsaL(
			@Valid SrchReserveParam srchReserveParam,
			String seq,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{
				System.out.println("=====>> nsaL() : seq="+seq);
				
				/*System.out.println("=====>> nsaL() : nsaFail()");
				this.nsaService.nsaFail();*/
				
				System.out.println("=====>> nsaL() : reservationTerminate() : 예약종료시간이 지난 예약정보 강제 TERMINATE");
				this.nsaService.reservationTerminate();
				
				System.out.println("=====>> nsaL() : getSrchReservationCnt() : 전체 리스트 카운트");
				srchReserveParam.setTotRow(this.nsaService.getSrchReservationCnt(srchReserveParam));
				
				/*System.out.println("=====>> nsaL() : getSrchReservationList()");
				List<ReservationDTO> list = this.nsaService.getSrchReservationList(srchReserveParam);*/
				List<ReservationDTO> list_reserve = this.nsaService.getReserveListSelect();
				mnv.addObject("nsaList", list_reserve);
				mnv.addObject("srchParam", srchReserveParam);
				mnv.setViewName("/nsa/nsaL");
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}
	
	// querySummary
	@RequestMapping(value="/nsa/nsaV")
	public ModelAndView nsaV(
			ReservationDTO param,
			SrchReserveParam srchReserveParam,
			BindingResult result,
			HttpServletRequest request,
			HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
				StrUtil.sysPrint("seq="+param.getSeq());
				mnv.addObject("seq", param.getSeq());
				
				String sProcType = request.getParameter("proc_type");
				if(sProcType==null || sProcType.equals(""))
					sProcType = "querySummary";
				mnv.addObject("proc_type", sProcType);
				
				//2-2-2)soap 통신 정보 setting
	    		ReservationDTO revDTO = this.nsaService.getReservationSelect(param);	//예약정보 조회
	    		mnv.addObject("reservationDTO", revDTO);
	    		ProviderNsa providerNsa = new ProviderNsa();
                providerNsa.setNsa_addr(revDTO.getProvider_nsa());                
                String sEndPointAddr = this.admService.getProviderNsaSelect(providerNsa).getEndpoint_addr();
                
                Provider provider = new Provider(param.getCorrelation_id(), revDTO.getProvider_nsa(), revDTO.getReply_to(), sEndPointAddr);
                
				if(sProcType.equals("querySummary"))
				{
	                com.netmng.websvc.soap.param.types.QueryType querySummary = new com.netmng.websvc.soap.param.types.QueryType();
	                querySummary.getConnectionId().add(revDTO.getConnection_id());
	                provider.querySummary(querySummary);	
	            	StrUtil.sysPrint("provider.nsaV : querySummary end!!");
	            	
	            	Thread.sleep(200);

	            	List<com.netmng.websvc.soap.param.types.QuerySummaryResultType> queryReuslt = com.netmng.vo.QueryResult.queryResultResv;
	            	mnv.addObject("queryResult", queryReuslt);
	            	mnv.setViewName("/nsa/nsaV_qrySummary");
				}
				else if(sProcType.equals("querySummarySync"))
				{	
					com.netmng.websvc.soap.param.types.QueryType querySummary = new com.netmng.websvc.soap.param.types.QueryType();
	                querySummary.getConnectionId().add(revDTO.getConnection_id());
	                com.netmng.websvc.soap.param.types.QuerySummaryConfirmedType querySummaryConfirmedType = provider.querySummarySync(querySummary);	
	            	StrUtil.sysPrint("provider.nsaV : querySummarySync end!!");
	            	
	            	List<com.netmng.websvc.soap.param.types.QuerySummaryResultType> queryReuslt = querySummaryConfirmedType.getReservation();
	            	mnv.addObject("queryResult", queryReuslt);
	            	mnv.setViewName("/nsa/nsaV_qrySummary");
				}
				else if(sProcType.equals("queryRecursive"))
				{
					com.netmng.websvc.soap.param.types.QueryType querySummary = new com.netmng.websvc.soap.param.types.QueryType();
	                querySummary.getConnectionId().add(revDTO.getConnection_id());
	                provider.queryRecursive(querySummary);	
	            	StrUtil.sysPrint("provider.nsaV : queryRecursive end!!");
	            	
	            	Thread.sleep(200);

	            	List<com.netmng.websvc.soap.param.types.QueryRecursiveResultType> queryReuslt = com.netmng.vo.QueryResult.queryResultRecursive;
	            	mnv.addObject("queryResult", queryReuslt);
	            	mnv.setViewName("/nsa/nsaV_qryRecursive");
				}
				else if(sProcType.equals("queryNotification"))
				{
					provider.queryNotification(revDTO.getConnection_id(), 0, 100);	
	            	StrUtil.sysPrint("provider.nsaV : queryNotification end!!");
	            	
	            	Thread.sleep(200);

	            	com.netmng.websvc.soap.param.types.QueryNotificationConfirmedType queryReuslt = com.netmng.vo.QueryResult.queryResultNotification;

	            	mnv.addObject("queryResult", queryReuslt);
	            	mnv.setViewName("/nsa/nsaV_qryNotification");
				}
				else if(sProcType.equals("queryNotificationSync"))
				{				
					QueryNotificationType queryNotificationSync = new QueryNotificationType();
	                queryNotificationSync.setConnectionId(revDTO.getConnection_id());
	                queryNotificationSync.setStartNotificationId(0);
	                queryNotificationSync.setEndNotificationId(100);
	                com.netmng.websvc.soap.param.types.QueryNotificationConfirmedType queryReuslt = provider.queryNotificationSync(queryNotificationSync);	
	            	StrUtil.sysPrint("provider.nsaV : queryNotificationSync end!!");
	            	
	            	mnv.addObject("queryResult", queryReuslt);
	            	mnv.setViewName("/nsa/nsaV_qryNotification");
				}
				
	    		StrUtil.sysPrint("provider.nsaV go query view!!");
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/nsaV.do");
	        	errorLog.setName("NSI::reserve");
	        	errorLog.setLog_message(e.toString());
	        	System.out.println("=====>> nsaV() : Exception");
	        	System.out.println("=====>> nsaV() : errorLog.getLog_message = "+errorLog.getLog_message());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
	        	System.out.println("=====>> nsaV() : errorLogInsert");
			}
		}
		return mnv;
	}
	
	@RequestMapping(value="/nsa/reserveLogL")
	public ModelAndView reserveLogL(
			@Valid SrchReserveParam srchReserveParam,
			String seq,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{
				System.out.println("=====>> reserveLogL() : seq="+seq);
				
				System.out.println("=====>> reserveLogL() : reservationTerminate() : 예약종료시간이 지난 예약정보 강제 TERMINATE");
				this.nsaService.reservationTerminate();
				
				System.out.println("=====>> reserveLogL() : getSrchReservationCnt() : 전체 리스트 카운트");
				srchReserveParam.setTotRow(this.nsaService.getSrchReservationCntAll(srchReserveParam));
				
				System.out.println("=====>> reserveLogL() : getReserveListSelectAll() : 리스트");
				List<ReservationDTO> list_reserve = this.nsaService.getReserveListSelectAll(srchReserveParam);
				mnv.addObject("nsaList", list_reserve);
				mnv.addObject("srchParam", srchReserveParam);
				mnv.setViewName("/nsa/reserveLogL");
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}
	
	@RequestMapping(value="/nsa/reserveLogV")
	public ModelAndView reserveLogV(
			ReservationDTO param,
			SrchReserveParam srchReserveParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
				StrUtil.sysPrint("seq="+param.getSeq());
				this.nsaService.reservationTerminate();					//예약시간_종료<현재 -> 종료 성공으로 RESERVATION update
				param = this.nsaService.getReservationSelectAll(param);	//예약정보 조회
				List<com.netmng.vo.Ero> list_ero = this.nsaService.getEroSelect(param);
				
				if(param != null){
					StrUtil.sysPrint("param is not null !!!");
					mnv.addObject("reservationDTO", param);
					mnv.addObject("list_ero", list_ero);
					mnv.addObject("srchParam", srchReserveParam);
					Date currTime = new Date();
					mnv.addObject("timeFlag", param.getEnd_time().after(currTime) && !param.getStart_time().after(currTime));					
					mnv.setViewName("/nsa/reserveLogV");
				} else {														//3)이외
					StrUtil.sysPrint("param is null !!!");
					result.rejectValue("seq", "DB01", "com.netmng.vo.Reservation.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}
	
	/*
	@RequestMapping("/getBandwidth")
	@ResponseBody
	public String getBandwidth(
			String source_stp_seq,
			String dest_stp_seq,
			String startTime,
			String endTime) {
		try {
			if(	source_stp_seq != null	&& !source_stp_seq.equals("") &&
				dest_stp_seq != null	&& !dest_stp_seq.equals("") &&
				startTime != null		&& !startTime.equals("") &&
				endTime != null			&& !endTime.equals("")	)
			{
				StpAndTime stpAndTime = new StpAndTime();
				ReservationDTO param = new ReservationDTO();
				
				RouterDTO source_stp = new RouterDTO();
				RouterDTO dest_stp = new RouterDTO();
				source_stp.setSeq(source_stp_seq);
				dest_stp.setSeq(dest_stp_seq);
				param.setSource_stp(source_stp);
				param.setDest_stp(dest_stp);
				stpAndTime.sourceSTP = this.nsaService.getUseRouterDTOSelect(param.getSource_stp()).getUrn();
				System.out.println("=====>> getBandwidth() : stpAndTime.sourceSTP="+stpAndTime.sourceSTP);
				stpAndTime.destSTP = this.nsaService.getUseRouterDTOSelect(param.getDest_stp()).getUrn();
				System.out.println("=====>> getBandwidth() : stpAndTime.destSTP="+stpAndTime.destSTP);
				stpAndTime.startTime = StrUtil.transformString(StrUtil.transformDate(startTime + ":00", new String[]{"yyyy.MM.dd HH:mm:ss"}), "yyyy-MM-dd HH:mm:ss").replaceAll(" ", " T") + ".000+09:00";
				System.out.println("=====>> getBandwidth() : stpAndTime.startTime="+stpAndTime.startTime);
				stpAndTime.endTime = StrUtil.transformString(StrUtil.transformDate(endTime + ":00", new String[]{"yyyy.MM.dd HH:mm:ss"}), "yyyy-MM-dd HH:mm:ss").replaceAll(" ", " T") + ".000+09:00";
				System.out.println("=====>> getBandwidth() : stpAndTime.endTime="+stpAndTime.endTime);
				
				JAXBContext jaxbContext = JAXBContext.newInstance(StpAndTime.class);
				StringWriter st = new StringWriter();
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.marshal(stpAndTime, st);	//stpAndTime내용을 st에 정렬화
				ClientConfig config = new DefaultClientConfig();   
				Client client = Client.create(config); 
				WebResource service = client.resource("http://203.230.116.210:8080/nsi/management/stp/residual-bw");
				WebResource service = client.resource("http://dynamickl.kisti.re.kr/nsi/management/stp/residual-bw");
				System.out.println("=====>> st.toString()="+st.toString());
				System.out.println("=====>> soap call : band width");
		        ClientResponse res = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).post(ClientResponse.class, st.toString());
				System.out.println("=====>> //soap call : band width");
		    	
		        if(res.getStatus() <= 201) {
		        	jaxbContext = JAXBContext.newInstance(ResidualBw.class);
		        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		        	String msg = res.getEntity(String.class).replaceAll(this.xmlns, "").replaceAll(this.xmlns_xsi, "").replaceAll(this.xmlns_schemaLocation, "");
		        	ResidualBw residualBw = (ResidualBw) unmarshaller.unmarshal(new StringReader(msg));
		        	if(residualBw.unit.equals("Gb")) 
		        		residualBw.value = String.valueOf(Integer.parseInt(residualBw.value)*1024);
		        	return residualBw.value;
		        } else {
		        	ResMsg msg = res.getEntity(ResMsg.class);
		        	ErrorLog errorLog = new ErrorLog();
		        	errorLog.setUrl("/getBandwidth.json");
		        	errorLog.setName("dinamicKL::residual-bw");
		        	errorLog.setLog_message(res.getStatus() + "::" + msg.getCause());
		        	this.admService.errorLogInsert(errorLog);
					return null;
		        }
		        
			} else {
				return null;
			}
		} catch (Exception e) {
        	ErrorLog errorLog = new ErrorLog();
        	errorLog.setUrl("/getBandwidth.json");
        	errorLog.setName("dinamicKL::residual-bw");
        	errorLog.setLog_message(e.toString());
        	System.out.println("=====>> getBandwidth() : exception ");
        	System.out.println("=====>> getBandwidth() : errorLog = "+errorLog.getLog_message());
        	this.admService.errorLogInsert(errorLog);
        	System.out.println("=====>> getBandwidth() : errorLogInsert ");
			return null;
		}
	}*/
	@RequestMapping("/getBandwidth")
	@ResponseBody
	public String getBandwidth(	String srcNetworkId,
					            String srcLocalId,
					            String destNetworkId,
					            String destLocalId,
					            String sdTime	) {
		
		StrUtil.sysPrint("1)srcNetworkId="+srcNetworkId);
		StrUtil.sysPrint("1)srcLocalId="+srcLocalId);
		StrUtil.sysPrint("1)destNetworkId="+destNetworkId);
		StrUtil.sysPrint("1)destLocalId="+destLocalId);
		StrUtil.sysPrint("1)sdTime="+sdTime);
		
		/*String startTime= sdTime.split("|")[0].replaceAll(".", "-");
		String endTIme	= sdTime.split("|")[1].replaceAll(".", "-");*/
		String startTime= StrUtil.split(sdTime, "|")[0].replaceAll("\\.", "-");
		String endTIme	= StrUtil.split(sdTime, "|")[1].replaceAll("\\.", "-");
		StrUtil.sysPrint("1)startTime="+startTime);
		StrUtil.sysPrint("1)endTIme="+endTIme);
		
		try {
			if(	srcNetworkId != null	&& !srcNetworkId.equals("")	&&
				srcLocalId != null		&& !srcLocalId.equals("")	&&
				destNetworkId != null	&& !destNetworkId.equals("")&&
				destLocalId != null		&& !destLocalId.equals("")	&&
				startTime != null		&& !startTime.equals("")	&&
				endTIme != null			&& !endTIme.equals("")	)
			{
				Client client = Client.create();
			    WebResource webResource = client.resource("http://nsi2.kisti.re.kr/extend-api/management");
			    
			    String subPath = "/residual-bw";
		        /*
		        MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();
		        params.add("srcNetworkId", srcNetworkId);
		        params.add("srcLocalId", srcLocalId);
		        params.add("destNetworkId", destNetworkId);
		        params.add("destLocalId", destLocalId);
		        params.add("startTime", startTime);
		        params.add("endTIme", endTIme);
		        */
			    /*
		        webResource.queryParam("srcNetworkId", srcNetworkId);
		        webResource.queryParam("srcLocalId", srcLocalId);
		        webResource.queryParam("destNetworkId", destNetworkId);
		        webResource.queryParam("destLocalId", destLocalId);
		        webResource.queryParam("startTime", startTime);
		        webResource.queryParam("endTIme", endTIme);
		        */
			    startTime = startTime.replaceAll(" ", "&");
			    endTIme = endTIme.replaceAll(" ", "&");
			    System.out.println("=====>> 시작시간="+startTime);
			    System.out.println("=====>> 종료시간="+endTIme);
		        ClientResponse response = webResource.path(subPath)
		        		.queryParam("srcNetworkId", srcNetworkId)
		        		.queryParam("srcLocalId", srcLocalId)
		        		.queryParam("destNetworkId", destNetworkId)
		        		.queryParam("destLocalId", destLocalId)
		        		.queryParam("startTime", startTime)
		        		.queryParam("endTime", endTIme)
		        		.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		        StrUtil.sysPrint("Request URL : " + webResource.path(subPath).getURI());
		        
		        int status = response.getStatus();
		        String result = "";
		        if(status == 200){
		        	/*org.w3c.dom.Node node = response.getEntity(org.w3c.dom.Node.class);
		        	StrUtil.sysPrint("getNodeName="+node.getNodeName());
		        	StrUtil.sysPrint("getTextContent="+node.getTextContent());*/
		        	
		        	
		        	
		        	
		            result = response.getEntity(String.class);
		            StrUtil.sysPrint("Success! result raw data : ");
		            StrUtil.sysPrint(result);
		            
		            String [] splitResult1 = result.split("\">");
		            String [] splitResult2 = splitResult1[1].split("</");
		            String residualBw = splitResult2[0];
		            
		            splitResult1 = result.split("unit=\"");
		            splitResult2 = splitResult1[1].split("\"");
		            String unit = splitResult2[0];
		            
		            StrUtil.sysPrint("1)대역폭="+residualBw);
		            StrUtil.sysPrint("2)대역폭="+residualBw+unit);
			        //return splitResult2[0]+splitResultUnit2[0];
		            
		            String sRtn = residualBw + unit;
		            return residualBw;
		        }else{
		        	StrUtil.sysPrint("HTTP REST request FAIL! " + status);
		        	
		        	ResMsg msg = response.getEntity(ResMsg.class);
		        	ErrorLog errorLog = new ErrorLog();
		        	errorLog.setUrl("/getBandwidth.json");
		        	errorLog.setName("dinamicKL::residual-bw");
		        	errorLog.setLog_message(response.getStatus() + "::" + msg.getCause());
		        	this.admService.errorLogInsert(errorLog);
					return null;
		        }
			}else{
				return null;
			}
		} catch (Exception e) {
        	ErrorLog errorLog = new ErrorLog();
        	errorLog.setUrl("/getBandwidth.json");
        	errorLog.setName("dinamicKL::residual-bw");
        	errorLog.setLog_message(e.toString());
        	StrUtil.sysPrint("getBandwidth() : exception ");
        	StrUtil.sysPrint("getBandwidth() : errorLog = "+errorLog.getLog_message());
        	this.admService.errorLogInsert(errorLog);
        	StrUtil.sysPrint("getBandwidth() : errorLogInsert ");
			return null;
		}
	}
	
	@RequestMapping("/troubleAsk")
	@ResponseBody
	public boolean troubleAsk(
		String url,
		HttpSession sess
	) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", ((User)sess.getAttribute(User.SESSION_KEY)).getId());
			map.put("url", url);
			map.put("time", StrUtil.transformString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			String sContents = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngineFactory.createVelocityEngine(), "errorAlarm.vm", "utf-8", map);
			
			UserDTO data = new UserDTO();
			data.setMode("grade");
			data.setGrade_seq("1");
			
			List<User> admin = this.userService.getUserCkList(data);
			
			for(int i=0; i<admin.size(); i++) {
				MailUtil.sendMail(this.mailServer, this.adminAddress, admin.get(i).getEmail(), "장애발생 신고가 왔습니다.", sContents);
			}
			
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

	// 즐겨찾는 사이트 (리스트)
	@RequestMapping(value="/nsa/reserveFavL")
	public ModelAndView reserveFavL(
			@Valid SrchReserveParam srchReserveParam,
			String seq,
			BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{
				Map map = new HashMap();
				System.out.println("=====>> reserveFavL() : getSrchReservationCnt() : 전체 리스트 카운트");
				srchReserveParam.setTotRow(this.nsaService.getSrchReservationFavCnt(map));
				
				List<ReservationFav> list_reserveFav = this.nsaService.getReserveListFavSelect();
				mnv.addObject("reservationFavList", list_reserveFav);
				mnv.addObject("srchParam", srchReserveParam);
				mnv.setViewName("/nsa/reserveFavL");
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		System.out.println("=====>> reserveFavL end");
		
		return mnv;
	}
	
	// 즐겨찾는 사이트 (등록화면)
	@RequestMapping(value="/nsa/reserveFavI")
	public ModelAndView reserveFavI(
			ReservationDTO param,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		try {
			mnv.addObject("reservationDTO", param);
			
			StrUtil.sysPrint("providerNSA()");
			mnv.addObject("providerNSAList", this.admService.getProviderNsaList());
			
			StrUtil.sysPrint("StpNetwork()");
			mnv.addObject("stpNetworkList", this.admService.getStpNetworkList());
			
			StrUtil.sysPrint("StpLocal()");
			mnv.addObject("stpLocalList", this.admService.getStpLocalList());
					
			mnv.setViewName("/nsa/reserveFavI");
		} catch(Exception e) {
			result.rejectValue("seq", "DB01", "com.netmng.exception.db");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		}
		return mnv;
	}

	// 즐겨찾는 사이트 (등록처리)
	@RequestMapping(value="/nsa/proc/reserveFavIP")	
	public ModelAndView reserveFavIP(
			ReservationDTO param,
			BindingResult result,
			HttpServletRequest request,
			HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		int maximum = 0;
		if (this.isNotValid(param, result, Reservation.ReserveInsert.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else {
			try{
				//1)즐겨찾는 사이트(user_fav) insert 정보 setting				
				param.setUser_seq(((User)sess.getAttribute(User.SESSION_KEY)).getSeq());	//등록자일련번호
				//2)즐겨찾는 사이트(user_fav) insert
		    	param.setSeq(String.valueOf(this.nsaService.reserveFavInsert(param)));
		    	
		    	mnv.addObject("mode", "RESERVE_FAV_I");
	    		mnv.addObject("seq", param.getSeq());
	    		mnv.setViewName("/nsa/proc/reserve");
			} catch(Exception e) {
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/reserveFavIP.do");
	        	errorLog.setName("NSI::reserveFav");
	        	errorLog.setLog_message(e.toString());
	        	System.out.println("=====>> reserveFavIP() : Exception");
	        	System.out.println("=====>> reserveFavIP() : errorLog.getLog_message = "+errorLog.getLog_message());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
	        	System.out.println("=====>> reserveFavIP() : errorLogInsert");
			}
		}
		return mnv;
	}

	// 즐겨찾는 사이트 (수정화면)
	@RequestMapping(value="/nsa/reserveFavU")
	public ModelAndView reserveFavU(
			ReservationDTO param,
			@Valid SrchReserveParam srchReserveParam,
			BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{
				String sSeq = request.getParameter("seq");
				if(sSeq!=null && !sSeq.equals("")){
					mnv.addObject("reservationDTO", param);

					Map<String, String> map = new HashMap<String, String>();
					map.put("seq", sSeq);
					ReservationFav reservationFav = this.nsaService.getReserveFavSelect(map);
					mnv.addObject("reservationFav", reservationFav);
					
					StrUtil.sysPrint("providerNSA()");
					mnv.addObject("providerNSAList", this.admService.getProviderNsaList());
					
					StrUtil.sysPrint("StpNetwork()");
					mnv.addObject("stpNetworkList", this.admService.getStpNetworkList());
					
					StrUtil.sysPrint("StpLocal()");
					mnv.addObject("stpLocalList", this.admService.getStpLocalList());
					
					mnv.setViewName("/nsa/reserveFavU");
				}else{
					StrUtil.sysPrint("user_fav seq is null");
					result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERROR500);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		System.out.println("=====>> reserveFavU end");
		
		return mnv;
	}
	
	// 즐겨찾는 사이트 (수정처리)
	@RequestMapping(value="/nsa/proc/reserveFavUP")	
	public ModelAndView reserveFavUP(
			ReservationDTO param,
			BindingResult result,
			HttpServletRequest request,
			HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		int maximum = 0;
		if (this.isNotValid(param, result, Reservation.ReserveInsert.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else {
			try{
				String sSeq = request.getParameter("seq");
				if(sSeq!=null && !sSeq.equals("")){
					//1)즐겨찾는 사이트(user_fav) update 정보 setting	
					param.setSeq(sSeq);
					param.setUser_seq(((User)sess.getAttribute(User.SESSION_KEY)).getSeq());	//등록자일련번호
					//2)즐겨찾는 사이트(user_fav) update
					this.nsaService.reserveFavUpdate(param);
			    	
			    	mnv.addObject("mode", "RESERVE_FAV_U");
		    		mnv.addObject("seq", param.getSeq());
		    		mnv.setViewName("/nsa/proc/reserve");
				}else{
					StrUtil.sysPrint("user_fav seq is null");
					result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERROR500);
				}				
			} catch(Exception e) {
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/reserveFavUP.do");
	        	errorLog.setName("NSI::reserveFav");
	        	errorLog.setLog_message(e.toString());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
			}
		}
		return mnv;
	}
	
	// 즐겨찾는 사이트 (삭제처리)
	@RequestMapping(value="/nsa/proc/reserveFavDP")	
	public ModelAndView reserveFavDP(
			ReservationDTO param,
			BindingResult result,
			HttpServletRequest request,
			HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		int maximum = 0;
		//if (this.isNotValid(param, result, Reservation.ReserveInsert.class)) {
		if(1==2){
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else {
			try{
				String sSeq = request.getParameter("seq");
				if(sSeq!=null && !sSeq.equals("")){
					this.nsaService.reserveFavDelete(sSeq);
					
			    	mnv.addObject("mode", "RESERVE_FAV_D");
		    		mnv.addObject("seq", param.getSeq());
		    		mnv.setViewName("/nsa/proc/reserve");
				}else{
					StrUtil.sysPrint("user_fav seq is null");
					result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERROR500);
				}				
			} catch(Exception e) {
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/nsa/proc/reserveFavDP.do");
	        	errorLog.setName("NSI::reserveFav");
	        	errorLog.setLog_message(e.toString());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
			}
		}
		return mnv;
	}
}

 