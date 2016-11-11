package com.netmng.svc.nsa;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netmng.dto.adm.stp.RouterDTO;
import com.netmng.dto.nsa.ReservationDTO;
import com.netmng.param.adm.SrchInterMonitorParam;
import com.netmng.param.nsa.SrchReserveParam;
import com.netmng.svc.AbstractSvc;
import com.netmng.util.StrUtil;
import com.netmng.vo.Ero;
import com.netmng.vo.ErrorLog;
import com.netmng.vo.NotificationLog;
import com.netmng.vo.ReservationFav;
import com.netmng.vo.User;
import com.netmng.websvc.soap.param.types.ConnectionStatesType;
import com.netmng.websvc.soap.param.types.NotificationBaseType;
import com.netmng.websvc.soap.param.types.QueryNotificationConfirmedType;
import com.netmng.websvc.soap.param.types.QueryRecursiveResultCriteriaType;
import com.netmng.websvc.soap.param.types.QueryRecursiveResultType;
import com.netmng.websvc.soap.param.types.QuerySummaryResultCriteriaType;
import com.netmng.websvc.soap.param.types.QuerySummaryResultType;
import com.netmng.websvc.soap.param.types.QueryType;
import com.netmng.websvc.soap.param.types.ReservationRequestCriteriaType;
import com.netmng.websvc.soap.param.types.ReservationStateEnumType;
import com.netmng.websvc.soap.param.types.ProvisionStateEnumType;
import com.netmng.websvc.soap.param.types.LifecycleStateEnumType;
import com.netmng.websvc.soap.param.types.DataPlaneStatusType;
import com.netmng.websvc.soap.param.types.ScheduleType;
import com.netmng.websvc.soap.svc.services.point2point.EthernetVlanType;
import com.netmng.websvc.soap.svc.services.types.OrderedStpType;
import com.netmng.websvc.soap.svc.services.types.StpListType;
import com.netmng.websvc.soap.svc.services.types.StpType;
import com.netmng.websvc.soap.svc.services.types.DirectionalityType;

@SuppressWarnings("unchecked")
public class NsaService extends AbstractSvc {

	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Long nsaReserve(ReservationDTO data) throws Exception {
		Long lSeq = (Long)this.sqlMapClientTemplate.insert("nsa.reservationInsert", data);
		List<Ero> list_ero = data.getList_ero();
		if(list_ero!=null && list_ero.size()>0){
			for(int i=0; i<list_ero.size(); i++){
				Ero pEro = list_ero.get(i);
				pEro.setReservation_seq(String.valueOf(lSeq));
				this.sqlMapClientTemplate.insert("nsa.reservationEroInsert", pEro);
			}
		}
		return lSeq;
		/*return (Long)this.sqlMapClientTemplate.insert("nsa.reservationInsert", data);*/
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Long nsaModReserve(ReservationDTO data) throws Exception {
		Long lSeq = (Long)this.sqlMapClientTemplate.insert("nsa.reservationModInsert", data);
		//data.setSeq(String.valueOf(lSeq));
		
		Map<String,String> m = new HashMap<String,String>();
		m.put("mod_reservation_seq", String.valueOf(lSeq));
		m.put("old_reservation_seq", data.getSeq());
		this.sqlMapClientTemplate.insert("nsa.reservationModEroInsert", m);
		
		m.put("chg_version_commit_reserve_seq", data.getSeq());
		m.put("version_commit", "N");
		this.sqlMapClientTemplate.update("nsa.reservationVersionCommitUpdate", m);
		
		return lSeq;
		/*return (Long)this.sqlMapClientTemplate.insert("nsa.reservationInsert", data);*/
	}
		
	@Transactional(readOnly = true)
	public Long getSrchReservationAllCnt(SrchReserveParam param) throws Exception {
		param.setUser_seq(((User)this.getSessionData(User.SESSION_KEY)).getSeq());
		return (Long)this.sqlMapClientTemplate.queryForObject("nsa.srchReservationAllCnt", param);
	}
	
	@Transactional(readOnly = true)
	public List<ReservationDTO> getSrchReservationAllList(SrchReserveParam param) throws Exception {
		param.setUser_seq(((User)this.getSessionData(User.SESSION_KEY)).getSeq());
		return (List<ReservationDTO>)this.sqlMapClientTemplate.queryForList("nsa.srchReservationAllList", param);
	}
	
	@Transactional(readOnly = true)
	public Long getSrchReservationCnt(SrchReserveParam param) throws Exception {
		param.setUser_seq(((User)this.getSessionData(User.SESSION_KEY)).getSeq());
		return (Long)this.sqlMapClientTemplate.queryForObject("nsa.srchReservationCnt", param);
	}
	
	@Transactional(readOnly = true)
	public Long getSrchReservationCntAll(SrchReserveParam param) throws Exception {
		param.setUser_seq(((User)this.getSessionData(User.SESSION_KEY)).getSeq());
		return (Long)this.sqlMapClientTemplate.queryForObject("nsa.srchReservationCntAll", param);
	}
	
	@Transactional(readOnly = true)
	public List<ReservationDTO> getSrchReservationList(SrchReserveParam param) throws Exception {
		param.setUser_seq(((User)this.getSessionData(User.SESSION_KEY)).getSeq());
		return (List<ReservationDTO>)this.sqlMapClientTemplate.queryForList("nsa.srchReservationList", param);
	}
	
	@Transactional(readOnly = true)
	public List<ReservationDTO> getReserveListSelect() throws Exception {
		Map<String,String> param = new HashMap<String,String>();
		param.put("user_seq", ((User)this.getSessionData(User.SESSION_KEY)).getSeq());
		return (List<ReservationDTO>)this.sqlMapClientTemplate.queryForList("nsa.reservationList", param);
	}
	
	@Transactional(readOnly = true)
	public List<ReservationDTO> getReserveListSelectAll(SrchReserveParam param) throws Exception {
		/*Map<String,String> param = new HashMap<String,String>();
		param.put("user_seq", ((User)this.getSessionData(User.SESSION_KEY)).getSeq());*/
		param.setUser_seq(((User)this.getSessionData(User.SESSION_KEY)).getSeq());
		return (List<ReservationDTO>)this.sqlMapClientTemplate.queryForList("nsa.reservationListAll", param);
	}
	
	
	@Transactional(readOnly = true)
	public ReservationDTO getReservationSelect(ReservationDTO param) throws Exception {
		param.setUser_seq(((User)this.getSessionData(User.SESSION_KEY)).getSeq());
		return (ReservationDTO)this.sqlMapClientTemplate.queryForObject("nsa.reservationSelect", param);
	}
	/*
	@Transactional(readOnly = true)
	public ReservationDTO getReservationSelectAdm(ReservationDTO param) throws Exception {
		param.setUser_seq(((User)this.getSessionData(User.SESSION_KEY)).getSeq());
		return (ReservationDTO)this.sqlMapClientTemplate.queryForObject("nsa.reservationSelectAdm", param);
	}
	*/
	@Transactional(readOnly = true)
	public ReservationDTO getReservationSelectOtr(ReservationDTO param) throws Exception {
		return (ReservationDTO)this.sqlMapClientTemplate.queryForObject("nsa.reservationSelectOtr", param);
	}
	
	@Transactional(readOnly = true)
	public ReservationDTO getReservationSelectAll(ReservationDTO param) throws Exception {
		param.setUser_seq(((User)this.getSessionData(User.SESSION_KEY)).getSeq());
		return (ReservationDTO)this.sqlMapClientTemplate.queryForObject("nsa.reservationSelectAll", param);
	}
	
	@Transactional(readOnly = true)
	public List<Ero> getEroSelect(ReservationDTO param) throws Exception {
		return (List<Ero>)this.sqlMapClientTemplate.queryForList("nsa.EroSelect", param);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer updateReserveConnId(Map<String,String> data) throws Exception {
		return (Integer)this.sqlMapClientTemplate.update("nsa.reservationConnIdUpdate", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer updateReserveState(	String sReserveState,
										String sProvisionState,
										String sLifeCycle,
										String sDataPlane,
										String sStateNm,
										String sConnectionId	) throws Exception {
		Map<String,String> data = new HashMap<String,String>();
		data.put("reserveState", sReserveState);
		data.put("provisionState", sProvisionState);
		data.put("lifeCycle", sLifeCycle);
		data.put("dataPlane", sDataPlane);
		data.put("stateNm", sStateNm);
		data.put("connectionId", sConnectionId);
		//return (Integer)this.sqlMapClientTemplate.update("nsa.reservationStateUpdate", data);
		return (Integer)this.sqlMapClientTemplate.update("nsa.revStatUpdate", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer updateProvisionState(String sProvisionState,
										String sStateNm,
										String sConnectionId	) throws Exception {
		Map<String,String> data = new HashMap<String,String>();
		data.put("provisionState", sProvisionState);
		data.put("stateNm", sStateNm);
		data.put("connectionId", sConnectionId);
		//return (Integer)this.sqlMapClientTemplate.update("nsa.provisionStateUpdate", data);
		return (Integer)this.sqlMapClientTemplate.update("nsa.revStatUpdate", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer updateTerminateState(/*String sProvisionState,
										String sLifeCycle,*/
										String sConnectionId	) throws Exception {
		Map<String,String> data = new HashMap<String,String>();
		/*data.put("provisionState", sProvisionState);
		data.put("lifeCycle", sLifeCycle);*/
		data.put("connectionId", sConnectionId);
		return (Integer)this.sqlMapClientTemplate.update("nsa.terminateStateUpdate", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer updateReserveCommit(	String sReserveState,
										String sProvisionState,
										String sLifeCycle,
										String sDataPlane,
										String sConnectionId	) throws Exception {
		Map<String,String> data = new HashMap<String,String>();
		data.put("reserveState", sReserveState);
		data.put("provisionState", sProvisionState);
		data.put("lifeCycle", sLifeCycle);
		data.put("dataPlane", sDataPlane);
		data.put("stateNm", "ReserveCommitedConfirmed");
		data.put("reserveFin", "Y");
		data.put("connectionId", sConnectionId);
		//Integer iRtn = (Integer)this.sqlMapClientTemplate.update("nsa.reservationStateFinUpdate", data);
		Integer iRtn = (Integer)this.sqlMapClientTemplate.update("nsa.revStatUpdate", data);
		//data.put("lifeCycle", "Terminated");
		Integer iTerminate = (Integer)this.sqlMapClientTemplate.update("nsa.prvVersionTerminateUpdate", data);
		
		return iRtn;
		//return (Integer)this.sqlMapClientTemplate.update("nsa.reservationStateUpdate", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer dataPlaneStateChange(	String sConnectionId,
											int iNotificationId,
											javax.xml.datatype.XMLGregorianCalendar timeStamp,
											String noti_type,
											String noti_type_desc,	
											int iVersion,
											String sDataPlane	) throws Exception {
		Integer iRtn = 0;
		// reservation data_plane update
		Map<String,String> data = new HashMap<String,String>();
		data.put("dataPlane", sDataPlane);
		if(sDataPlane!=null && sDataPlane.equals("Active"))
			data.put("state_nm", "Activate");
		else if(sDataPlane!=null && sDataPlane.equals("Inactive"))
			data.put("state_nm", "Deactivate");
		data.put("version", String.valueOf(iVersion));
		data.put("connectionId", sConnectionId);
		iRtn = (Integer)this.sqlMapClientTemplate.update("nsa.dataPlaneUpdate", data);
		
		//notification log insert
		NotificationLog notificationLog = new NotificationLog(sConnectionId, iNotificationId, timeStamp, noti_type, noti_type_desc);
		this.sqlMapClientTemplate.insert("nsa.notificationLogInsert", notificationLog);
		
		return iRtn;
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer errorEvent(	String sConnectionId,
								int iNotificationId,
								javax.xml.datatype.XMLGregorianCalendar timeStamp,
								String noti_type,
								String noti_type_desc	) throws Exception {
		Integer iRtn = 0;
		if(	noti_type!=null && noti_type.equals("errorEvent") && noti_type_desc!=null ){
			//life-cycle update
			Map<String,String> data = new HashMap<String,String>();
			data.put("connectionId", sConnectionId);
			if(noti_type_desc.equals("forcedEnd")){				
				data.put("provisionState", "Released");
				data.put("lifeCycle", "Terminated");
				data.put("dataPlane", "Inactive");	
				data.put("state_nm", "forcedEnd");	
			}else if(noti_type_desc.equals("dataplaneError")){	
				data.put("provisionState", "Released");
				data.put("dataPlane", "Inactive");	
				data.put("state_nm", "Release");	
			}else if(noti_type_desc.equals("activateFailed")){	
				data.put("provisionState", "Released");
				data.put("dataPlane", "Inactive");
				data.put("state_nm", "Release");	
			}else if(noti_type_desc.equals("deactivateFailed")){	
				data.put("provisionState", "Provisioned");
				data.put("dataPlane", "Active");
				data.put("state_nm", "Provision");	
			}
			//iRtn = (Integer)this.sqlMapClientTemplate.update("nsa.lifeCycleUpdate", data);
			iRtn = (Integer)this.sqlMapClientTemplate.update("nsa.revStatUpdate", data);
		}
		//notification log insert
		NotificationLog notificationLog = new NotificationLog(sConnectionId, iNotificationId, timeStamp, noti_type, noti_type_desc);
		this.sqlMapClientTemplate.insert("nsa.notificationLogInsert", notificationLog);
		
		return iRtn;
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer reserveTimeout(	String sConnectionId,
									int iNotificationId,
									javax.xml.datatype.XMLGregorianCalendar timeStamp,
									String noti_type,
									String noti_type_desc	) throws Exception {
		Integer iRtn = 0;
		//reserve state change
		Map<String,String> data = new HashMap<String,String>();
		data.put("reserveState", "ReserveTimeout");
		data.put("connectionId", sConnectionId);
		data.put("state_nm", "ReserveTimeOut");
		//iRtn = (Integer)this.sqlMapClientTemplate.update("nsa.reserveStateUpdate", data);
		iRtn = (Integer)this.sqlMapClientTemplate.update("nsa.revStatUpdate", data);
		
		//notification log insert
		NotificationLog notificationLog = new NotificationLog(sConnectionId, iNotificationId, timeStamp, noti_type, noti_type_desc);
		this.sqlMapClientTemplate.insert("nsa.notificationLogInsert", notificationLog);
		
		return iRtn;
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer messageDeliveryTimeout(	String sConnectionId,
											int iNotificationId,
											javax.xml.datatype.XMLGregorianCalendar timeStamp,
											String noti_type,
											String noti_type_desc	) throws Exception {
		Integer iRtn = 0;
		
		//notification log insert
		NotificationLog notificationLog = new NotificationLog(sConnectionId, iNotificationId, timeStamp, noti_type, noti_type_desc);
		this.sqlMapClientTemplate.insert("nsa.notificationLogInsert", notificationLog);
		
		return iRtn;
	}
	
	@Transactional(readOnly = true)
	public List<QuerySummaryResultType> querySummaryConfirmed(QueryType param) throws Exception {
		List<QuerySummaryResultType> listRtn = new ArrayList<QuerySummaryResultType>();
		
		List<String> listConnectionId		= param.getConnectionId();
		List<String> listGlobalReservationId= param.getGlobalReservationId();
		
		String sKey = "";
		List<String> listVal = null;
		
		if(listConnectionId!=null && listConnectionId.size()>0){
			sKey = "connectionId";
			listVal = listConnectionId;
		}else if(listGlobalReservationId!=null && listGlobalReservationId.size()>0){
			sKey = "globalReservationId";
			listVal = listGlobalReservationId;
		}
		
		if(sKey!=null && !sKey.equals("")){
			for(int i=0; i<listVal.size(); i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put(sKey, listVal.get(i));
				List<ReservationDTO> listRevDto = (List<ReservationDTO>)this.sqlMapClientTemplate.queryForList("nsa.resvListByQry", map);
				for(int s=0; s<listRevDto.size(); s++){
					ReservationDTO revDto = listRevDto.get(s);
					
					QuerySummaryResultType qrySummaryResult = new QuerySummaryResultType();
					qrySummaryResult.setConnectionId(revDto.getConnection_id());
					qrySummaryResult.setGlobalReservationId(revDto.getGlobal_reservation_id());
					qrySummaryResult.setDescription(revDto.getDescription());
					qrySummaryResult.setRequesterNSA(revDto.getRequester_nsa());	
					qrySummaryResult.setConnectionStates(this.getConnectionStatesType(revDto));
					
					QuerySummaryResultCriteriaType criteria = new QuerySummaryResultCriteriaType();
					criteria.setVersion(revDto.getVersion());
					criteria.setSchedule(this.getSchedule(revDto));
                    criteria.setServiceType("http://services.ogf.org/nsi/2013/07/descriptions/EVTS.A-GOLE");
                    
                    com.netmng.websvc.soap.svc.services.point2point.ObjectFactory of = new com.netmng.websvc.soap.svc.services.point2point.ObjectFactory();
                    JAXBElement<EthernetVlanType> evts = of.createEvts(this.getEvt(revDto));
                    criteria.getAny().add(evts);
                    qrySummaryResult.getCriteria().add(criteria);
                    
                    listRtn.add(qrySummaryResult);
				}
			}
		}
		return listRtn;
	}
	
	@Transactional(readOnly = true)
	public List<QueryRecursiveResultType> queryRecursiveConfirmed(QueryType param) throws Exception {
		List<QueryRecursiveResultType> listRtn = new ArrayList<QueryRecursiveResultType>();
		
		List<String> listConnectionId		= param.getConnectionId();
		List<String> listGlobalReservationId= param.getGlobalReservationId();
		
		String sKey = "";
		List<String> listVal = null;
		
		if(listConnectionId!=null && listConnectionId.size()>0){
			sKey = "connectionId";
			listVal = listConnectionId;
		}else if(listGlobalReservationId!=null && listGlobalReservationId.size()>0){
			sKey = "globalReservationId";
			listVal = listGlobalReservationId;
		}
		
		if(sKey!=null && !sKey.equals("")){
			for(int i=0; i<listVal.size(); i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put(sKey, listVal.get(i));
				List<ReservationDTO> listRevDto = (List<ReservationDTO>)this.sqlMapClientTemplate.queryForList("nsa.resvListByQry", map);
				for(int s=0; s<listRevDto.size(); s++){
					ReservationDTO revDto = listRevDto.get(s);
					
					QueryRecursiveResultType qryRecursiveResult = new QueryRecursiveResultType();
					qryRecursiveResult.setConnectionId(revDto.getConnection_id());
					qryRecursiveResult.setGlobalReservationId(revDto.getGlobal_reservation_id());
					qryRecursiveResult.setDescription(revDto.getDescription());
					qryRecursiveResult.setRequesterNSA(revDto.getRequester_nsa());	
					qryRecursiveResult.setConnectionStates(this.getConnectionStatesType(revDto));
					
					QueryRecursiveResultCriteriaType criteria = new QueryRecursiveResultCriteriaType();
					criteria.setVersion(revDto.getVersion());
					criteria.setSchedule(this.getSchedule(revDto));
                    criteria.setServiceType("http://services.ogf.org/nsi/2013/07/descriptions/EVTS.A-GOLE");
                    
                    com.netmng.websvc.soap.svc.services.point2point.ObjectFactory of = new com.netmng.websvc.soap.svc.services.point2point.ObjectFactory();
                    JAXBElement<EthernetVlanType> evts = of.createEvts(this.getEvt(revDto));
                    criteria.getAny().add(evts);
                    qryRecursiveResult.getCriteria().add(criteria);
                    
                    listRtn.add(qryRecursiveResult);
				}
			}
		}
		return listRtn;
	}
	
	private ConnectionStatesType getConnectionStatesType(ReservationDTO revDto) throws Exception {
		ConnectionStatesType connectionStates = new ConnectionStatesType();
		connectionStates.setReservationState(ReservationStateEnumType.fromValue(revDto.getReserve_state()));
		connectionStates.setProvisionState(ProvisionStateEnumType.fromValue(revDto.getProvision_state()));
		connectionStates.setLifecycleState(LifecycleStateEnumType.fromValue(revDto.getLife_cycle()));
			DataPlaneStatusType dataPlaneStatus = new DataPlaneStatusType();
			if(revDto.getData_plane()!=null && revDto.getData_plane().equals("Active"))
				dataPlaneStatus.setActive(true);
			else
				dataPlaneStatus.setActive(false);
			dataPlaneStatus.setVersion(revDto.getVersion());
			dataPlaneStatus.setVersionConsistent(true);
		connectionStates.setDataPlaneStatus(dataPlaneStatus);
		return connectionStates;
	}
	
	private ScheduleType getSchedule(ReservationDTO revDto) throws Exception {
		ScheduleType schedule = new ScheduleType();
		try {
            Date startTime = revDto.getStart_time();
            GregorianCalendar startTimeGregorian = new GregorianCalendar();
            startTimeGregorian.setTime(startTime);
            XMLGregorianCalendar startTimeXMLGregorian = DatatypeFactory.newInstance().newXMLGregorianCalendar(startTimeGregorian);
            Duration sDuration = DatatypeFactory.newInstance().newDuration(true, 0, 0, 0, 0, 0, 0);
            startTimeXMLGregorian.add(sDuration);
            schedule.setStartTime(startTimeXMLGregorian);
            
            Date endTime = revDto.getEnd_time();
            GregorianCalendar endTimeGregorian = new GregorianCalendar();
            endTimeGregorian.setTime(endTime);
            XMLGregorianCalendar endTimeXMLGregorian = DatatypeFactory.newInstance().newXMLGregorianCalendar(endTimeGregorian);
            Duration dDuration = DatatypeFactory.newInstance().newDuration(true, 0, 0, 0, 1, 0, 0);
            endTimeXMLGregorian.add(dDuration);
            schedule.setEndTime(endTimeXMLGregorian);    					
        } catch (DatatypeConfigurationException ex) {
        	StrUtil.sysPrint("NsaService.getSchedule() : DatatypeConfigurationException");
        	ex.printStackTrace();
        }
		return schedule;
		
	}
	
	@Transactional(readOnly = true)
	public XMLGregorianCalendar getXMLGregorianCalendar(Date t) throws Exception {
		GregorianCalendar timeGregorian = new GregorianCalendar();
		timeGregorian.setTime(t);
        XMLGregorianCalendar timeXMLGregorian = DatatypeFactory.newInstance().newXMLGregorianCalendar(timeGregorian);
        Duration dDuration = DatatypeFactory.newInstance().newDuration(true, 0, 0, 0, 1, 0, 0);
        timeXMLGregorian.add(dDuration);
        return timeXMLGregorian;
	}

	private EthernetVlanType getEvt(ReservationDTO revDto) throws Exception {
		EthernetVlanType evt = new EthernetVlanType();
        evt.setCapacity(Integer.parseInt(revDto.getDesired()));
            StpType sourceSTP = new StpType();
            sourceSTP.setNetworkId(revDto.getSource_network_id());
            sourceSTP.setLocalId(revDto.getSource_local_id());
			sourceSTP.setLabels(null);
        evt.setSourceSTP(sourceSTP);
			StpType destSTP = new StpType();
			destSTP.setNetworkId(revDto.getDest_network_id());
			destSTP.setLocalId(revDto.getDest_local_id());
			destSTP.setLabels(null);
		evt.setDestSTP(destSTP);
		
		List<Ero> listEro = (List<Ero>)this.sqlMapClientTemplate.queryForList("nsa.EroSelect", revDto);

		StpListType stpList = null;
        if(listEro!=null && listEro.size()>0){
        	stpList = new StpListType();
        	for(int e=0; e<listEro.size(); e++){
        		Ero ero = listEro.get(e);
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
        
        evt.setMtu(revDto.getMtu());
		evt.setBurstsize(revDto.getBurstsize());
		evt.setDirectionality(DirectionalityType.fromValue(revDto.getDirectionality()));
		evt.setSourceVLAN(revDto.getSourceVLAN());
        evt.setDestVLAN(revDto.getDestVLAN());
        
        return evt;
	}
	
	@Transactional(readOnly = true)
	public QueryNotificationConfirmedType queryNotificationConfonfirmed(String sConnectionId, int startNotificationId,  int endNotificationId) throws Exception {
		QueryNotificationConfirmedType queryNotificationConfirmed = new QueryNotificationConfirmedType();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("connectionId", sConnectionId);
		map.put("start_notificationId", String.valueOf(startNotificationId));
		map.put("end_notificationId", String.valueOf(endNotificationId));
		
		List<NotificationLog> listNotificationLog = (List<NotificationLog>)this.sqlMapClientTemplate.queryForList("nsa.NotificationSelect", map);
		if(listNotificationLog!=null && listNotificationLog.size()>0){
			for(int i=0; i<listNotificationLog.size(); i++){
				NotificationLog notificationLog = listNotificationLog.get(i);
				
				NotificationBaseType notificationBase = new NotificationBaseType();
				notificationBase.setConnectionId(notificationLog.getConnection_id());
				notificationBase.setNotificationId(notificationLog.getNotification_id());
				notificationBase.setTimeStamp(this.getXMLGregorianCalendar(notificationLog.getOccur_time()));
				
				queryNotificationConfirmed.getErrorEventOrReserveTimeoutOrDataPlaneStateChange().add(notificationBase);
			}
		}
		return queryNotificationConfirmed;
	}
	
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer vlanUpdate(Map<String,String> data) throws Exception {
		return (Integer)this.sqlMapClientTemplate.update("nsa.vlanUpdate", data);
	}
	
	
	
	
	
	
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer nsaProvision(ReservationDTO data) throws Exception {
		data.setStatus("P");
		data.setFlag("R");
		return (Integer)this.sqlMapClientTemplate.update("nsa.reservationStatusUpdate", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer nsaRelease(ReservationDTO data) throws Exception {
		data.setStatus("R");
		data.setFlag("R");
		return (Integer)this.sqlMapClientTemplate.update("nsa.reservationStatusUpdate", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer nsaDelete(ReservationDTO data, String sOldRevSeq) throws Exception {
		Integer iDelEro = (Integer)this.sqlMapClientTemplate.delete("nsa.eroDelete", data);
		Integer iRtn = (Integer)this.sqlMapClientTemplate.delete("nsa.reservationDelete", data);
		if(sOldRevSeq!=null && !sOldRevSeq.equals("")){
			Map<String,String> m = new HashMap<String,String>();
			m.put("chg_version_commit_reserve_seq", data.getSeq());
			m.put("version_commit", "N");
			this.sqlMapClientTemplate.update("nsa.reservationVersionCommitUpdate", m);
		}
		return iRtn;
		//return (Integer)this.sqlMapClientTemplate.delete("nsa.reservationDelete", data);
	}
	
	@Transactional(readOnly = true)
	public RouterDTO getRouterDTOSelect(RouterDTO param) throws Exception {
		return (RouterDTO)this.sqlMapClientTemplate.queryForObject("adm.routerDTOSelect", param);
	}
	
	@Transactional(readOnly = true)
	public RouterDTO getUseRouterDTOSelect(RouterDTO param) throws Exception {
		return (RouterDTO)this.sqlMapClientTemplate.queryForObject("adm.useRouterDTOSelect", param);
	}
	
	public Integer reservationTerminate() throws Exception {
		return (Integer)this.sqlMapClientTemplate.update("nsa.reservationTerminate");
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer nsaTerminate(ReservationDTO data) throws Exception {
		data.setStatus("T");
		data.setFlag("R");
		return (Integer)this.sqlMapClientTemplate.update("nsa.reservationStatusUpdate", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer nsaStatusUpdate(ReservationDTO data) throws Exception {
		return (Integer)this.sqlMapClientTemplate.update("nsa.reservationStatusUpdate", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer nsaAckStatusUpdate(ReservationDTO data) throws Exception {
		return (Integer)this.sqlMapClientTemplate.update("nsa.reservationAckStatusUpdate", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void nsaFail() throws Exception {
		this.sqlMapClientTemplate.update("nsa.reservationFailUpdate");
		this.sqlMapClientTemplate.update("nsa.reservationFailDelete");
	}
	
	@Transactional(readOnly = true)
	public ReservationDTO getNsaStatusSelect(String seq) throws Exception {
		return (ReservationDTO)this.sqlMapClientTemplate.queryForObject("nsa.reservationStatusSelect", seq);
	}
	
	public void errorLogInsert(ErrorLog data) {
		try {
			this.sqlMapClientTemplate.insert("adm.errorLogInsert", data);
		} catch(Exception e) {
			
		}
	}
	
	@Transactional(readOnly = true)
	public Long getSrchReservationFavCnt(Map param) throws Exception {
		param.put("user_seq", ((User)this.getSessionData(User.SESSION_KEY)).getSeq());
		return (Long)this.sqlMapClientTemplate.queryForObject("nsa.srchReservationFavCnt", param);
	}
	
	@Transactional(readOnly = true)
	public List<ReservationFav> getReserveListFavSelect() throws Exception {
		Map<String,String> param = new HashMap<String,String>();
		param.put("user_seq", ((User)this.getSessionData(User.SESSION_KEY)).getSeq());
		return (List<ReservationFav>)this.sqlMapClientTemplate.queryForList("nsa.srchReservationFavList", param);
	}
	
	@Transactional(readOnly = true)
	public ReservationFav getReserveFavSelect(Map param) throws Exception {
		param.put("user_seq", ((User)this.getSessionData(User.SESSION_KEY)).getSeq());
		return (ReservationFav)this.sqlMapClientTemplate.queryForObject("nsa.reservationFavSelect", param);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Long reserveFavInsert(ReservationDTO data) throws Exception {
		return (Long)this.sqlMapClientTemplate.insert("nsa.reserveFavInsert", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer reserveFavUpdate(ReservationDTO data) throws Exception {
		return (Integer)this.sqlMapClientTemplate.update("nsa.reserveFavUpdate", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Integer reserveFavDelete(String data) throws Exception {
		return (Integer)this.sqlMapClientTemplate.delete("nsa.reserveFavDelete", data);
	}
}
