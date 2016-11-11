package com.netmng.ctrl.adm;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.netmng.com.error.ErrorClass;
import com.netmng.ctrl.AbstractCtrl;
import com.netmng.ctrl.nsa.NsaCtrllor;
import com.netmng.dto.nsa.ReservationDTO;
import com.netmng.param.adm.SrchGlobalMonitorParam;
import com.netmng.param.adm.SrchInterMonitorParam;
import com.netmng.param.adm.SrchUserMonitorParam;
import com.netmng.param.nsa.SrchReserveParam;
import com.netmng.svc.adm.AdmService;
import com.netmng.svc.nsa.NsaService;
import com.netmng.util.StrUtil;
import com.netmng.vo.Ero;
import com.netmng.vo.ErrorLog;
import com.netmng.vo.ProviderNsa;
import com.netmng.vo.User;
import com.netmng.websvc.rest.client.vo.ResMsg;
import com.netmng.websvc.soap.param.types.QueryNotificationType;
import com.netmng.websvc.soap.param.types.ReservationRequestCriteriaType;
import com.netmng.websvc.soap.param.types.ScheduleType;
import com.netmng.websvc.soap.svc.Provider;
/*import com.netmng.websvc.soap.svc.param.provider.Provision;
import com.netmng.websvc.soap.svc.param.provider.Release;
import com.netmng.websvc.soap.svc.param.provider.Terminate;*/
import com.netmng.websvc.soap.svc.services.point2point.EthernetVlanType;
import com.netmng.websvc.soap.svc.services.types.DirectionalityType;
import com.netmng.websvc.soap.svc.services.types.OrderedStpType;
import com.netmng.websvc.soap.svc.services.types.StpListType;
import com.netmng.websvc.soap.svc.services.types.StpType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Controller
public class AdmMonitorCtrllor extends AbstractCtrl {

	@Autowired(required=true) 
	private AdmService admService;
	@Autowired(required=true) 
	private NsaService nsaService;
	
	// 이용자 모니터링(리스트)
	@RequestMapping(value="/adm/userMonitorL")
	public ModelAndView userMonitorL(
			@Valid SrchUserMonitorParam srchUserMonitorParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{
				this.admService.reservationTerminate();
				
				srchUserMonitorParam.setTotRow(this.admService.getSrchUserMonitorCnt(srchUserMonitorParam));				
				mnv.addObject("userMonitorList", this.admService.getSrchUserMonitorList(srchUserMonitorParam));
				mnv.addObject("srchParam", srchUserMonitorParam);
				mnv.setViewName("/adm/userMonitorL");
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}
	
	// 이용자 모니터링(뷰)
	@RequestMapping(value="/adm/userMonitorV")
	public ModelAndView userMonitorV(
			@Valid SrchUserMonitorParam srchUserMonitorParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{
				this.admService.reservationTerminate();
				
				srchUserMonitorParam.setTotRow(this.admService.getSrchUserMonitorGroupCnt(srchUserMonitorParam));
				List<ReservationDTO> list_reserve = this.admService.getSrchUserMonitorGroup(srchUserMonitorParam);
				mnv.addObject("reservationList", list_reserve);
				mnv.addObject("srchParam", srchUserMonitorParam);
				mnv.setViewName("/adm/userMonitorV");
				StrUtil.sysPrint("userMonitorV end !!");
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}
	
	// 이용자 모니터링(리스트)
	/*@RequestMapping(value="/adm/interMonitorL")
	public ModelAndView interMonitorL(
			@Valid SrchInterMonitorParam srchInterMonitorParam,
			String seq,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{

				System.out.println("=====>> interMonitorL() : seq="+seq);
				if(!StrUtil.nullToStr(seq).equals("")) {
					System.out.println("=====>> interMonitorL() : seq is not blank");
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
				
				this.admService.reservationTerminate();
				srchInterMonitorParam.setTotRow(this.admService.getSrchInterMonitorCnt(srchInterMonitorParam));
				mnv.addObject("interMonitorList", this.admService.getSrchInterMonitorList(srchInterMonitorParam));
				mnv.addObject("srchParam", srchInterMonitorParam);
				mnv.setViewName("/adm/interMonitorL");
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}*/
	@RequestMapping(value="/adm/interMonitorL")
	public ModelAndView interMonitorL(
			@Valid SrchInterMonitorParam srchInterMonitorParam,
			String seq,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{			
				this.nsaService.reservationTerminate();
							
				srchInterMonitorParam.setTotRow(this.admService.getSrchInterMonitorCnt(srchInterMonitorParam));
				mnv.addObject("reservationList", this.admService.getSrchInterMonitorList(srchInterMonitorParam));
				mnv.addObject("srchParam", srchInterMonitorParam);
				mnv.setViewName("/adm/interMonitorL");
				StrUtil.sysPrint("interMonitorL() end");
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}
	
	// 국내망 모니터링(뷰)
	@RequestMapping(value="/adm/interMonitorV")
	public ModelAndView interMonitorV(
			@Valid SrchInterMonitorParam srchInterMonitorParam,
			String seq,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		//if (this.isNotValid(param, result, User.AdmUserView.class)) {
		if (1==2) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
				StrUtil.sysPrint("seq="+seq);
				if(seq != null && !seq.equals("")){
					StrUtil.sysPrint("seq is not null !!!");
					
					this.nsaService.reservationTerminate();					//예약시간_종료<현재 -> 종료 성공으로 RESERVATION update
					
					ReservationDTO param = new ReservationDTO();
					param.setSeq(seq);
					param = this.nsaService.getReservationSelectOtr(param);	//예약정보 조회
					mnv.addObject("reservationDTO", param);

					List<com.netmng.vo.Ero> list_ero = this.nsaService.getEroSelect(param);
					mnv.addObject("list_ero", list_ero);
					
					mnv.addObject("srchParam", srchInterMonitorParam);
					
					Date currTime = new Date();
					mnv.addObject("timeFlag", param.getEnd_time().after(currTime) && !param.getStart_time().after(currTime));	
					
					mnv.setViewName("/adm/interMonitorV");
					StrUtil.sysPrint("interMonitorV end");
				} else {														
					StrUtil.sysPrint("seq is null !!!");
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
	
	// 국내망 모니터링 (수정화면)
	@RequestMapping(value="/adm/interMonitorU")
	public ModelAndView interMonitorU(
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
				param = this.nsaService.getReservationSelectOtr(param);	//예약정보 조회
				
				if(param != null){
					StrUtil.sysPrint("param is not null !!!");
					
					List<com.netmng.vo.Ero> list_ero = this.nsaService.getEroSelect(param);
					mnv.addObject("list_ero", list_ero);
					
					mnv.addObject("stpVlanList", this.admService.getStpVlanList());
					
					mnv.addObject("reservationDTO", param);
					
					mnv.addObject("srchParam", srchReserveParam);
					
					Date currTime = new Date();
					mnv.addObject("timeFlag", param.getEnd_time().after(currTime) && !param.getStart_time().after(currTime));	
					
					mnv.setViewName("/adm/interMonitorU");
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
	
	// 국내망 모니터링 (수정처리)
	@RequestMapping(value="/adm/proc/interMonitorUP")
	public ModelAndView interMonitorUP(
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
				
				String sdTime = "";
				sdTime += param.getStart_date()+" "+param.getStart_hour()+":"+param.getStart_min()+":00";
				sdTime += "|";
				sdTime += param.getEnd_date()+" "+param.getEnd_hour()+":"+param.getEnd_min()+":00";
				maximum = Integer.parseInt((new NsaCtrllor()).getBandwidth(	param.getSource_network_id(), 
																param.getSource_local_id(), 
																param.getDest_network_id(), 
																param.getDest_local_id(), 
																sdTime	));					
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
					    		revDTO = this.nsaService.getReservationSelectOtr(param);	//예약정보 조회
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
					    		mnv.setViewName("/adm/proc/interMonitor");
					    		StrUtil.sysPrint("view ==>> /adm/proc/interMonitorUP end!!");
					    	}
						} catch (Exception e) {
				    		StrUtil.sysPrint("AdmMonitorCtrllor.interMonitorUP() : AdmMonitorCtrllor.interMonitorUP Exception");
				    		e.printStackTrace();
				    		// data 삭제
				    		param.setSeq(sModSeq);
				    		this.nsaService.nsaDelete(param, revDTO.getSeq());
				    		result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.reserve.fail");
				    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
							mnv.setViewName(ErrorClass.ERRORDATA);
							
				        	ErrorLog errorLog = new ErrorLog();
				        	errorLog.setUrl("/adm/proc/interMonitorUP.do");
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
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("seq", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
				
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/adm/proc/interMonitorUP.do");
	        	errorLog.setName("NSI::reserve");
	        	errorLog.setLog_message(e.toString());
	        	System.out.println("=====>> interMonitorUP() : Exception");
	        	System.out.println("=====>> interMonitorUP() : errorLog.getLog_message = "+errorLog.getLog_message());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
	        	System.out.println("=====>> interMonitorUP() : errorLogInsert");
			}
		}
		return mnv;
	}
	
	// 구성,해제, 종결서비스 (예약확정)
	@RequestMapping(value="/adm/proc/reserveCommitP")
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
				
	    		ReservationDTO revDTO = this.nsaService.getReservationSelectOtr(param);	//예약정보 조회

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
	    		mnv.setViewName("/adm/proc/interMonitor");
			} catch (Exception e) {
				try{
					this.nsaService.updateReserveState(null, null, null, null, sOldStateNm, sConnectionId);
				}catch(Exception ex){
					e.printStackTrace();
					result.rejectValue("seq", "DB01", "com.netmng.exception.db");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
					
		        	ErrorLog errorLog = new ErrorLog();
		        	errorLog.setUrl("/adm/proc/reserveCommitP.do");
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
	        	errorLog.setUrl("/adm/proc/reserveCommitP.do");
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
		
	// 구성,해제, 종결서비스 (예약취소)
	@RequestMapping(value="/adm/proc/reserveAbortP")
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

	    		ReservationDTO revDTO = this.nsaService.getReservationSelectOtr(param);	//예약정보 조회

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
	    		mnv.setViewName("/adm/proc/interMonitor");
			} catch (Exception e) {
				try{
					this.nsaService.updateReserveState(null, null, null, null, sOldStateNm, sConnectionId);
				}catch(Exception ex){
					e.printStackTrace();
					result.rejectValue("seq", "DB01", "com.netmng.exception.db");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
					
		        	ErrorLog errorLog = new ErrorLog();
		        	errorLog.setUrl("/adm/proc/reserveAbortP.do");
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
	        	errorLog.setUrl("/adm/proc/reserveAbortP.do");
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
	
	// 구성 
	@RequestMapping(value="/adm/proc/provisionP")
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
				
	    		ReservationDTO revDTO = this.nsaService.getReservationSelectOtr(param);	//예약정보 조회

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
	    		mnv.setViewName("/adm/proc/interMonitor");
			} catch (Exception e) {
				try{
					this.nsaService.updateReserveState(null, null, null, null, sOldStateNm, sConnectionId);
				}catch(Exception ex){
					e.printStackTrace();
					result.rejectValue("seq", "DB01", "com.netmng.exception.db");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
					
		        	ErrorLog errorLog = new ErrorLog();
		        	errorLog.setUrl("/adm/proc/provisionP.do");
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
	        	errorLog.setUrl("/adm/proc/provisionP.do");
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
	@RequestMapping(value="/adm/proc/releaseP")
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
				
	    		ReservationDTO revDTO = this.nsaService.getReservationSelectOtr(param);	//예약정보 조회

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
	    		mnv.setViewName("/adm/proc/interMonitor");
			} catch (Exception e) {
				try{
					this.nsaService.updateReserveState(null, null, null, null, sOldStateNm, sConnectionId);
				}catch(Exception ex){
					e.printStackTrace();
					result.rejectValue("seq", "DB01", "com.netmng.exception.db");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
					
		        	ErrorLog errorLog = new ErrorLog();
		        	errorLog.setUrl("/adm/proc/releaseP.do");
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
	        	errorLog.setUrl("/adm/proc/releaseP.do");
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
	@RequestMapping(value="/adm/proc/terminateP")
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
				
	    		ReservationDTO revDTO = this.nsaService.getReservationSelectOtr(param);	//예약정보 조회

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
	    		mnv.setViewName("/adm/proc/interMonitor");
			} catch (Exception e) {
				try{
					this.nsaService.updateReserveState(null, null, null, null, sOldStateNm, sConnectionId);
				}catch(Exception ex){
					e.printStackTrace();
					result.rejectValue("seq", "DB01", "com.netmng.exception.db");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
					
		        	ErrorLog errorLog = new ErrorLog();
		        	errorLog.setUrl("/adm/proc/terminateP.do");
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
	        	errorLog.setUrl("/adm/proc/terminateP.do");
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
	
	// query
	@RequestMapping(value="/adm/query")
	public ModelAndView query(
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
				StrUtil.sysPrint("seq="+request.getParameter("seq"));
				param.setSeq(request.getParameter("seq"));
				mnv.addObject("seq", request.getParameter("seq"));
				
				String sProcType = request.getParameter("proc_type");
				if(sProcType==null || sProcType.equals(""))
					sProcType = "querySummary";
				mnv.addObject("proc_type", sProcType);
				
				//2-2-2)soap 통신 정보 setting
	    		ReservationDTO revDTO = this.nsaService.getReservationSelectOtr(param);	//예약정보 조회
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
	            	mnv.setViewName("/adm/pop/nsaV_qrySummary");
				}
				else if(sProcType.equals("querySummarySync"))
				{	
					com.netmng.websvc.soap.param.types.QueryType querySummary = new com.netmng.websvc.soap.param.types.QueryType();
	                querySummary.getConnectionId().add(revDTO.getConnection_id());
	                com.netmng.websvc.soap.param.types.QuerySummaryConfirmedType querySummaryConfirmedType = provider.querySummarySync(querySummary);	
	            	StrUtil.sysPrint("provider.nsaV : querySummarySync end!!");
	            	
	            	List<com.netmng.websvc.soap.param.types.QuerySummaryResultType> queryReuslt = querySummaryConfirmedType.getReservation();
	            	mnv.addObject("queryResult", queryReuslt);
	            	mnv.setViewName("/adm/pop/nsaV_qrySummary");
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
	            	mnv.setViewName("/adm/pop/nsaV_qryRecursive");
				}
				else if(sProcType.equals("queryNotification"))
				{
					provider.queryNotification(revDTO.getConnection_id(), 0, 100);	
	            	StrUtil.sysPrint("provider.nsaV : queryNotification end!!");
	            	
	            	Thread.sleep(200);

	            	com.netmng.websvc.soap.param.types.QueryNotificationConfirmedType queryReuslt = com.netmng.vo.QueryResult.queryResultNotification;

	            	mnv.addObject("queryResult", queryReuslt);
	            	mnv.setViewName("/adm/pop/nsaV_qryNotification");
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
	            	mnv.setViewName("/adm/pop/nsaV_qryNotification");
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
	        	System.out.println("=====>> query() : Exception");
	        	System.out.println("=====>> query() : errorLog.getLog_message = "+errorLog.getLog_message());
	        	e.printStackTrace();
	        	this.admService.errorLogInsert(errorLog);
	        	System.out.println("=====>> query() : errorLogInsert");
			}
		}
		return mnv;
	}
	
	
	
	
	
	// 국제망 모니터링
	@RequestMapping(value="/adm/globalMonitorL")
	public ModelAndView globalMonitorL(
			@Valid SrchGlobalMonitorParam srchGlobalMonitorParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{
				this.admService.reservationTerminate();
				
				mnv.addObject("srchParam", srchGlobalMonitorParam);
				mnv.setViewName("/adm/globalMonitorL");
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}
	
	// 모니터링 상세화면
	/*@RequestMapping(value="/adm/interMonitorV")
	public ModelAndView reserveV(
			ReservationDTO param,
			SrchInterMonitorParam srchInterMonitorParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
				this.admService.reservationTerminate();
				param = this.admService.getReservationSelect(param);
				if(param != null && param.getTrouble_yn().equals("N")) {
					mnv.addObject("reservationDTO", param);
					mnv.addObject("srchParam", srchInterMonitorParam);
					Date currTime = new Date();
					mnv.addObject("timeFlag", param.getEnd_time().after(currTime) && !param.getStart_time().after(currTime));					
					mnv.setViewName("/adm/interMonitorV");
				} else if(param != null && param.getTrouble_yn().equals("Y")) {
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
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}*/
	
	// 구성 
	/*@RequestMapping(value="/adm/proc/provisionP")
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
				data = this.admService.getReservationSelect(data);
				if(data != null && data.getTrouble_yn().equals("N")) {
					Date currTime = new Date();
					if(data.getStatus().equals("P")) {
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
								mnv.setViewName("/adm/proc/reserve");
							} else {
								data.setStatus(status);
								data.setFlag(flag);
								this.nsaService.nsaStatusUpdate(data);
					    		result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.provision.fail");
					    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
								mnv.setViewName(ErrorClass.ERRORDATA);
								
					        	ErrorLog errorLog = new ErrorLog();
					        	errorLog.setUrl("/adm/proc/provisionP.do");
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
				        	errorLog.setUrl("/adm/proc/provisionP.do");
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
	        	errorLog.setUrl("/adm/proc/provisionP.do");
	        	errorLog.setName("NSI::provision");
	        	errorLog.setLog_message(e.toString());
	        	this.admService.errorLogInsert(errorLog);
			}
		}
		
		return mnv;
	}*/
	
	// 해제
	/*@RequestMapping(value="/adm/proc/releaseP")
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
				data = this.admService.getReservationSelect(data);
				if(data != null && data.getTrouble_yn().equals("N")) {
					Date currTime = new Date();
					if(data.getStatus().equals("P")) {
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
					    		mnv.setViewName("/adm/proc/reserve");
					    	} else {
					    		data.setStatus(status);
								data.setFlag(flag);
								this.nsaService.nsaStatusUpdate(data);
					    		result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.release.fail");
					    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
								mnv.setViewName(ErrorClass.ERRORDATA);
								
					        	ErrorLog errorLog = new ErrorLog();
					        	errorLog.setUrl("/adm/proc/releaseP.do");
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
				        	errorLog.setUrl("/adm/proc/releaseP.do");
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
	        	errorLog.setUrl("/adm/proc/releaseP.do");
	        	errorLog.setName("NSI::release");
	        	errorLog.setLog_message(e.toString());
	        	this.admService.errorLogInsert(errorLog);
			}
		}
		
		return mnv;
	}*/
	
	// 종결
	/*@RequestMapping(value="/adm/proc/terminateP")
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
				data = this.admService.getReservationSelect(data);
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
				    		mnv.setViewName("/adm/proc/reserve");
				    	} else {
				    		data.setStatus(status);
							data.setFlag(flag);
							this.nsaService.nsaStatusUpdate(data);
				    		result.rejectValue("correlation_id", "DB01", "com.netmng.ctrl.nsa.terminate.fail");
				    		mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
							mnv.setViewName(ErrorClass.ERRORDATA);
							
				        	ErrorLog errorLog = new ErrorLog();
				        	errorLog.setUrl("/adm/proc/terminateP.do");
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
			        	errorLog.setUrl("/adm/proc/terminateP.do");
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
	        	errorLog.setUrl("/adm/proc/terminateP.do");
	        	errorLog.setName("NSI::terminate");
	        	errorLog.setLog_message(e.toString());
	        	this.admService.errorLogInsert(errorLog);
			}
		}
		
		return mnv;
	}*/
}
