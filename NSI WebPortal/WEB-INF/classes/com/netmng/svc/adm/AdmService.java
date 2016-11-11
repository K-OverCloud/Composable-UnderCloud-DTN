package com.netmng.svc.adm;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netmng.dto.adm.monitor.UserMonitorDTO;
import com.netmng.dto.adm.stp.InterfaceInfoDTO;
import com.netmng.dto.adm.stp.RouterDTO;
import com.netmng.dto.nsa.ReservationDTO;
import com.netmng.dto.user.UserDTO;
import com.netmng.param.adm.SrchErrorParam;
import com.netmng.param.adm.SrchEventParam;
import com.netmng.param.adm.SrchInterMonitorParam;
import com.netmng.param.adm.SrchUserMonitorParam;
import com.netmng.param.adm.SrchUserParam;
import com.netmng.svc.AbstractSvc;
import com.netmng.util.StrUtil;
import com.netmng.vo.CdDw;
import com.netmng.vo.ErrorLog;
import com.netmng.vo.Event;
import com.netmng.vo.EventLog;
import com.netmng.vo.GnprInfo;
import com.netmng.vo.InterfaceInfo;
import com.netmng.vo.MenuAuth;
import com.netmng.vo.ProviderNsa;
import com.netmng.vo.Router;
import com.netmng.vo.RouterType;
import com.netmng.vo.StpLocal;
import com.netmng.vo.StpNetwork;
import com.netmng.vo.StpVlan;
import com.netmng.vo.User;
import com.netmng.websvc.soap.parser.inter.base.LabelGroupType;
import com.netmng.websvc.soap.parser.inter.base.PortGroupRelationType;
import com.netmng.websvc.soap.parser.inter.base.PortGroupType;
import com.netmng.websvc.soap.parser.inter.base.TopologyRelationType;
import com.netmng.websvc.soap.parser.inter.base.TopologyType;
import com.netmng.websvc.soap.parser.inter.topology.NSAType;
import com.netmng.websvc.soap.svc.topology.inter.parser.InterDomainInformation;
import com.netmng.websvc.soap.svc.topology.inter.parser.TopologyReference;

@SuppressWarnings("unchecked")
public class AdmService extends AbstractSvc {
	
	@Transactional(readOnly = true)
	public Long getSrchUserCnt(SrchUserParam param) throws Exception {
		return (Long)this.sqlMapClientTemplate.queryForObject("adm.srchUserCnt", param);
	}
	
	@Transactional(readOnly = true)
	public List<User> getSrchUserList(SrchUserParam param) throws Exception {
		return (List<User>)this.sqlMapClientTemplate.queryForList("adm.srchUserList", param);
	}
	
	@Transactional(readOnly = true)
	public UserDTO getUserInfoSelect(User param) throws Exception {
		return (UserDTO)this.sqlMapClientTemplate.queryForObject("adm.userInfoSelect", param);
	}

	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public Integer userGradeUpdate(UserDTO data) throws Exception {
		return (Integer)this.sqlMapClientTemplate.update("adm.userGradeUpdate", data);
	}

	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public Integer userDropUpdate(UserDTO data) throws Exception {
		return (Integer)this.sqlMapClientTemplate.update("adm.userDropUpdate", data);
	}
	
	@Transactional(readOnly = true)
	public MenuAuth getMenuAuthSelect(Map<String, String> param) throws Exception {
		return (MenuAuth)this.sqlMapClientTemplate.queryForObject("user.menuAuthSelect", param);
	}
	
	@Transactional(readOnly = true)
	public List<RouterType> getRouterTypeList() throws Exception {
		return (List<RouterType>)this.sqlMapClientTemplate.queryForList("adm.routerTypeList");
	}
	
	@Transactional(readOnly = true)
	public Long getSrchUserMonitorCnt(SrchUserMonitorParam param) throws Exception {
		return (Long)this.sqlMapClientTemplate.queryForObject("adm.srchUserMonitorCnt", param);
	}
	
	@Transactional(readOnly = true)
	public List<UserMonitorDTO> getSrchUserMonitorList(SrchUserMonitorParam param) throws Exception {
		return (List<UserMonitorDTO>)this.sqlMapClientTemplate.queryForList("adm.srchUserMonitorList", param);
	}
	
	public Integer reservationTerminate() throws Exception {
		return (Integer)this.sqlMapClientTemplate.update("nsa.reservationTerminate");
	}
	
	@Transactional(readOnly = true)
	public Long getSrchUserMonitorGroupCnt(SrchUserMonitorParam param) throws Exception {
		return (Long)this.sqlMapClientTemplate.queryForObject("nsa.srchUserMonitorGroupCnt", param);
	}
	
	@Transactional(readOnly = true)
	public List<ReservationDTO> getSrchUserMonitorGroup(SrchUserMonitorParam param) throws Exception {
		return (List<ReservationDTO>)this.sqlMapClientTemplate.queryForList("nsa.srchUserMonitorGroup", param);
	}
	
	@Transactional(readOnly = true)
	public Long getSrchInterMonitorCnt(SrchInterMonitorParam param) throws Exception {
		return (Long)this.sqlMapClientTemplate.queryForObject("nsa.srchInterMonitorCnt", param);
	}
	
	@Transactional(readOnly = true)
	public List<ReservationDTO> getSrchInterMonitorList(SrchInterMonitorParam param) throws Exception {
		return (List<ReservationDTO>)this.sqlMapClientTemplate.queryForList("nsa.srchInterMonitorList", param);
	}
	
	@Transactional(readOnly = true)
	public List<InterfaceInfoDTO> getInterfaceInfoList(Map<String, String> param) throws Exception {
		return (List<InterfaceInfoDTO>)this.sqlMapClientTemplate.queryForList("adm.interfaceInfoList", param);
	}
	
	@Transactional(readOnly = true)
	public List<InterfaceInfoDTO> getInterfaceInfo(InterfaceInfoDTO data) throws Exception {
		return (List<InterfaceInfoDTO>)this.sqlMapClientTemplate.queryForList("adm.interfaceInfoSelect", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public void topologyUpdate(Router[] routerList, InterfaceInfo[] interfaceHList, InterfaceInfo[] interfaceBList) throws Exception {
		long group_seq = (Long)this.sqlMapClientTemplate.queryForObject("adm.routerGroupSeqSelect");
		for(int i=0; i<routerList.length; i++) {
			routerList[i].setGroup_seq(String.valueOf(group_seq));
			routerList[i].setSeq(String.valueOf((Long)this.sqlMapClientTemplate.insert("adm.routerInsert", routerList[i])));
		}
		for(int i=0; i<interfaceHList.length; i++) {
			interfaceHList[i].setGroup_seq(String.valueOf(group_seq));
			interfaceHList[i].setRouter_seq(routerList[0].getSeq());
			interfaceBList[i].setGroup_seq(String.valueOf(group_seq));
			interfaceBList[i].setRouter_seq(routerList[i+1].getSeq());
			interfaceHList[i].setSeq(String.valueOf((Long)this.sqlMapClientTemplate.insert("adm.interfaceInfoInsert", interfaceHList[i])));
			interfaceBList[i].setSeq(String.valueOf((Long)this.sqlMapClientTemplate.insert("adm.interfaceInfoInsert", interfaceBList[i])));
			interfaceHList[i].setLink_interface_seq(interfaceBList[i].getSeq());
			interfaceBList[i].setLink_interface_seq(interfaceHList[i].getSeq());
			this.sqlMapClientTemplate.update("adm.interfaceInfoLinkUpdate", interfaceHList[i]);
			this.sqlMapClientTemplate.update("adm.interfaceInfoLinkUpdate", interfaceBList[i]);
		}
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public void gnprInfoUpdate(List<GnprInfo> data) throws Exception {
		for(GnprInfo gnprInfo : data) {
			this.sqlMapClientTemplate.insert("adm.gnprInfoInsert", gnprInfo);
		}
	}
	
	@Transactional(readOnly = true)
	public List<RouterDTO> getTopologyList() throws Exception {
		return (List<RouterDTO>)this.sqlMapClientTemplate.queryForList("adm.routerDTOList");
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public void nsaInitUpdate() throws Exception {	
		this.sqlMapClientTemplate.update("adm.reservationAllTerminate");
		this.sqlMapClientTemplate.update("adm.nsaInitUpdate1");
		this.sqlMapClientTemplate.update("adm.nsaInitUpdate2");
		this.sqlMapClientTemplate.update("adm.nsaInitUpdate3");
		this.sqlMapClientTemplate.update("adm.nsaInitUpdate4");
		this.sqlMapClientTemplate.update("adm.nsaInitUpdate5");
		this.sqlMapClientTemplate.update("adm.nsaInitUpdate6");
	}
	
	/*@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public void eventInsert(Event data) throws Exception {
		
		int cause = Integer.parseInt(data.getCause());

		switch(cause) {
			// interface down
			case 1 : {
				String seq = String.valueOf((Long) this.sqlMapClientTemplate.insert("adm.eventInsert", data));
				for(int i=0; i<data.getEventLogList().size(); i++) {
					EventLog eventLog = data.getEventLogList().get(i);
					eventLog.setCause(data.getCause());
					eventLog.setEvent_seq(seq);
					this.sqlMapClientTemplate.insert("adm.eventLogInsert", eventLog);
					eventLog.setLine("P");
					this.sqlMapClientTemplate.update("adm.interfaceInfoFault", eventLog);
					eventLog.setLine("S");
					this.sqlMapClientTemplate.update("adm.interfaceInfoFault", eventLog);
					this.sqlMapClientTemplate.update("adm.reservationFault", eventLog);
				}
				break;
			}
			// interface up
			case 2 : {
				//this.sqlMapClientTemplate.update("adm.eventRestoreUpdate", data.getEventLogList().get(0));
				String seq = String.valueOf((Long) this.sqlMapClientTemplate.insert("adm.eventInsert", data));
				for(int i=0; i<data.getEventLogList().size(); i++) {
					EventLog eventLog = data.getEventLogList().get(i);
					eventLog.setCause(data.getCause());
					eventLog.setEvent_seq(seq);
					this.sqlMapClientTemplate.insert("adm.eventLogInsert", eventLog);
					eventLog.setLine("P");
					this.sqlMapClientTemplate.update("adm.interfaceInfoFault", eventLog);
					eventLog.setLine("S");
					this.sqlMapClientTemplate.update("adm.interfaceInfoFault", eventLog);
					this.sqlMapClientTemplate.update("adm.reservationFault", eventLog);
				}
				break;
			}
			// Node down
			case 3 : {
				String seq = String.valueOf((Long) this.sqlMapClientTemplate.insert("adm.eventInsert", data));
				for(int i=0; i<data.getEventLogList().size(); i++) {
					EventLog eventLog = data.getEventLogList().get(i);
					eventLog.setCause(data.getCause());
					eventLog.setEvent_seq(seq);
					this.sqlMapClientTemplate.insert("adm.eventLogInsert", eventLog);
					////this.sqlMapClientTemplate.update("adm.interfaceInfoFault", eventLog);
					this.sqlMapClientTemplate.update("adm.routerFault", eventLog);
					this.sqlMapClientTemplate.update("adm.reservationFault", eventLog);
				}
				break;
			}
			// Node up
			case 4 : {
				//this.sqlMapClientTemplate.update("adm.eventRestoreUpdate", data.getEventLogList().get(0));
				String seq = String.valueOf((Long) this.sqlMapClientTemplate.insert("adm.eventInsert", data));
				for(int i=0; i<data.getEventLogList().size(); i++) {
					EventLog eventLog = data.getEventLogList().get(i);
					eventLog.setCause(data.getCause());
					eventLog.setEvent_seq(seq);
					this.sqlMapClientTemplate.insert("adm.eventLogInsert", eventLog);
					////this.sqlMapClientTemplate.update("adm.interfaceInfoFault", eventLog);
					this.sqlMapClientTemplate.update("adm.routerFault", eventLog);
					this.sqlMapClientTemplate.update("adm.reservationFault", eventLog);
				}
				break;
			}
			// primary2secondarySuccess
			case 5 : {
				//this.sqlMapClientTemplate.update("adm.eventLineUpdate", data.getEventLogList().get(0));
				String seq = String.valueOf((Long) this.sqlMapClientTemplate.insert("adm.eventInsert", data));
				for(int i=0; i<data.getEventLogList().size(); i++) {
					EventLog eventLog = data.getEventLogList().get(i);
					eventLog.setCause(data.getCause());
					eventLog.setEvent_seq(seq);
					this.sqlMapClientTemplate.insert("adm.eventLogInsert", eventLog);
					this.sqlMapClientTemplate.update("adm.interfaceInfoFault", eventLog);
				}
				break;
			}
			// secondary2primarySuccess
			case 6 : {
				//this.sqlMapClientTemplate.update("adm.eventLineUpdate", data.getEventLogList().get(0));
				String seq = String.valueOf((Long) this.sqlMapClientTemplate.insert("adm.eventInsert", data));
				for(int i=0; i<data.getEventLogList().size(); i++) {
					EventLog eventLog = data.getEventLogList().get(i);
					eventLog.setCause(data.getCause());
					eventLog.setEvent_seq(seq);
					this.sqlMapClientTemplate.insert("adm.eventLogInsert", eventLog);
					this.sqlMapClientTemplate.update("adm.interfaceInfoFault", eventLog);
				}
				break;
			}
			// primary2secondaryFail
			case 7 : {
				//this.sqlMapClientTemplate.update("adm.eventLineUpdate", data.getEventLogList().get(0));
				String seq = String.valueOf((Long) this.sqlMapClientTemplate.insert("adm.eventInsert", data));
				for(int i=0; i<data.getEventLogList().size(); i++) {
					EventLog eventLog = data.getEventLogList().get(i);
					eventLog.setCause(data.getCause());
					eventLog.setEvent_seq(seq);
					this.sqlMapClientTemplate.insert("adm.eventLogInsert", eventLog);
					//this.sqlMapClientTemplate.update("adm.interfaceInfoFault", eventLog);
				}
				break;
			}
			// secondary2primaryFail
			case 8 : {
				//this.sqlMapClientTemplate.update("adm.eventLineUpdate", data.getEventLogList().get(0));
				String seq = String.valueOf((Long) this.sqlMapClientTemplate.insert("adm.eventInsert", data));
				for(int i=0; i<data.getEventLogList().size(); i++) {
					EventLog eventLog = data.getEventLogList().get(i);
					eventLog.setCause(data.getCause());
					eventLog.setEvent_seq(seq);
					this.sqlMapClientTemplate.insert("adm.eventLogInsert", eventLog);
					//this.sqlMapClientTemplate.update("adm.interfaceInfoFault", eventLog);
				}
				break;
			}
		}
	}*/
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public void eventInsert(Event data) throws Exception {
		String seq = String.valueOf((Long) this.sqlMapClientTemplate.insert("adm.eventInsert", data));
		for(int i=0; i<data.getListInterface().size(); i++){
			Map map = new HashMap();
			map.put("event_seq", seq);
			map.put("fault_interface", data.getListInterface().get(i));
			this.sqlMapClientTemplate.insert("adm.eventLogInsert", map);
		}
	}
	
	@Transactional(readOnly = true)
	public Long getSrchEventCnt(SrchEventParam param) throws Exception {
		return (Long)this.sqlMapClientTemplate.queryForObject("adm.srchEventCnt", param);
	}
	
	@Transactional(readOnly = true)
	public List<Event> getSrchEventList(SrchEventParam param) throws Exception {
		return (List<Event>)this.sqlMapClientTemplate.queryForList("adm.srchEventList", param);
	}
	
	@Transactional(readOnly = true)
	public Long getSrchErrorCnt(SrchErrorParam param) throws Exception {
		return (Long)this.sqlMapClientTemplate.queryForObject("adm.srchErrorCnt", param);
	}
	
	@Transactional(readOnly = true)
	public List<ErrorLog> getSrchErrorList(SrchErrorParam param) throws Exception {
		return (List<ErrorLog>)this.sqlMapClientTemplate.queryForList("adm.srchErrorList", param);
	}
	
	public void errorLogInsert(ErrorLog data) {
		try {
			this.sqlMapClientTemplate.insert("adm.errorLogInsert", data);
		} catch(Exception e) {
			
		}
	}
	
	@Transactional(readOnly = true)
	public ReservationDTO getReservationSelect(ReservationDTO param) throws Exception {
		return (ReservationDTO)this.sqlMapClientTemplate.queryForObject("nsa.reservationSelect", param);
	}
	
	
	@Transactional(readOnly = true)
	public List<CdDw> getComCdDwList(Map<String, String> param) throws Exception {
		return (List<CdDw>)this.sqlMapClientTemplate.queryForList("adm.cdDwList", param);
	}
	
	@Transactional(readOnly = true)
	public List<ProviderNsa> getProviderNsaList() throws Exception {
		return (List<ProviderNsa>)this.sqlMapClientTemplate.queryForList("adm.providerNsaList");
	}

	@Transactional(readOnly = true)
	public ProviderNsa getProviderNsaSelect(ProviderNsa param) throws Exception {
		return (ProviderNsa)this.sqlMapClientTemplate.queryForObject("adm.providerNsaSelect", param);
	}
	
	@Transactional(readOnly = true)
	public List<StpNetwork> getStpNetworkList() throws Exception {
		return (List<StpNetwork>)this.sqlMapClientTemplate.queryForList("adm.stpNetworkList");
	}
	
	@Transactional(readOnly = true)
	public List<StpLocal> getStpLocalList() throws Exception {
		return (List<StpLocal>)this.sqlMapClientTemplate.queryForList("adm.stpLocalList");
	}
	
	@Transactional(readOnly = true)
	public List<StpLocal> getStpLocalListSelect(Map<String, String> param) throws Exception {
		return (List<StpLocal>)this.sqlMapClientTemplate.queryForList("adm.stpLocalListSelect", param);
	}
	
	@Transactional(readOnly = true)
	public List<StpVlan> getStpVlanList() throws Exception {
		return (List<StpVlan>)this.sqlMapClientTemplate.queryForList("adm.stpVlanList");
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public void interTopologyConfig(ArrayList<InterDomainInformation> listTopology, ArrayList<TopologyReference> listTopologyReference) throws Exception {
		/*stp_vlan, stp_local, stp_network, provider_nsa delete*/	
		Integer iDelStpVlan		= (Integer)this.sqlMapClientTemplate.delete("adm.stpVlanDeleteAll");
		Integer iDelStpLocal	= (Integer)this.sqlMapClientTemplate.delete("adm.stpLocalDeleteAll");
		Integer iDelStpNetwork	= (Integer)this.sqlMapClientTemplate.delete("adm.stpNetworkDeleteAll");
		Integer iDelProviderNsa	= (Integer)this.sqlMapClientTemplate.delete("adm.providerNsaDeleteAll");
		
		for(int i=0; i<listTopology.size(); i++){
			InterDomainInformation interDomainInfo = listTopology.get(i);
			TopologyReference topologyReference = listTopologyReference.get(i);
			StrUtil.sysPrint("==========================================================================");					
			StrUtil.sysPrint("interDomainInfo.getNsa()="			+interDomainInfo.getNsa());
			StrUtil.sysPrint("interDomainInfo.getAdminContact()="	+interDomainInfo.getAdminContact());
			StrUtil.sysPrint("interDomainInfo.getNsa()="			+interDomainInfo.getEndPointAddress());
			StrUtil.sysPrint("interDomainInfo.getNetworkId()="		+interDomainInfo.getNetworkId());
			
			Map<String, String> map = new HashMap<String, String>();			
			map.put("nsa_addr",		interDomainInfo.getNsa());
			map.put("nsa_nm",		interDomainInfo.getNsa());
			map.put("admin_contact",interDomainInfo.getAdminContact());
			map.put("endpoint_addr",interDomainInfo.getEndPointAddress());
			map.put("ref_url",		topologyReference.getDeployAddress());
			this.sqlMapClientTemplate.insert("adm.providerNsaInsert", map);		/*provider_nsa insert*/
			
			map.put("network_id",	interDomainInfo.getNetworkId());
			map.put("network_nm",	interDomainInfo.getNetworkId());
			map.put("fault_yn",		"N");
			this.sqlMapClientTemplate.insert("adm.stpNetworkInsert", map);		/*stp_network insert*/
			
			//VLAN관련 HashMap
			HashMap<String, String> inboundPort = interDomainInfo.getInboundPort();

			ArrayList<HashMap<String, ArrayList<String>>> listBidirectionalPort = interDomainInfo.getBidirectionalPort();
			for(int j=0; j<listBidirectionalPort.size(); j++){
				
				HashMap<String, ArrayList<String>> mBidirectionalPort = listBidirectionalPort.get(j);
				Iterator keyset = mBidirectionalPort.keySet().iterator();
				while(keyset.hasNext()){
					String key = keyset.next().toString();
					
					StrUtil.sysPrint("LocalID : " + key);
					
					map.put("local_id",	key);
					map.put("local_nm",	key);
					this.sqlMapClientTemplate.insert("adm.stpLocalInsert", map);	/*stp_local insert*/
					
					ArrayList<String> bidirectionPortGroup = mBidirectionalPort.get(key);
					String sPortGropIn = bidirectionPortGroup.get(0);	//VLAN Label 추출을 위한 key
					
					StrUtil.sysPrint("bidirectionPortGroup.get(0) : " + bidirectionPortGroup.get(0));
					StrUtil.sysPrint("bidirectionPortGroup.get(1) : " + bidirectionPortGroup.get(1));
					
					String sVLAN = "";
					if(inboundPort.get(bidirectionPortGroup.get(0)) == null){
						sVLAN = inboundPort.get(bidirectionPortGroup.get(1));
					}else{
						sVLAN = inboundPort.get(bidirectionPortGroup.get(0));
					}
					
					StrUtil.sysPrint("VLAN 범위 : " + sVLAN);
					
					ArrayList<Integer> arrVLAN = StrUtil.chgStrToNumRange(sVLAN);
					for(int v=0; v<arrVLAN.size(); v++){
						
						StrUtil.sysPrint("Vlan : " + arrVLAN.get(v));
						
						map.put("vlan_seq",	String.valueOf(arrVLAN.get(v)));
                        this.sqlMapClientTemplate.insert("adm.stpVlanInsert", map);	/*stp_vlan insert*/
					}
				}
			}
		}
	}
		
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public Integer topologyAliasUpdate(Map data) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("nsa_addr",	(String)data.get("nsa_addr"));
		map.put("nsa_nm", 	(String)data.get("nsa_nm"));		
		int iProviderNsa = (Integer)this.sqlMapClientTemplate.update("adm.providerNsaNmUpdate", map);
		
		map.put("network_id", (String)data.get("network_id"));				
		map.put("network_nm", (String)data.get("network_nm"));				
		int iNetworkId = (Integer)this.sqlMapClientTemplate.update("adm.networkNmUpdate", map);
		
		String[] arrLocalId	= (String[])data.get("local_id");
		String[] arrLocalNm	= (String[])data.get("local_nm");
		for(int i=0; i<arrLocalId.length; i++){
			map.put("local_id", arrLocalId[i]);
			map.put("local_nm", arrLocalNm[i]);
			int iLocalId = (Integer)this.sqlMapClientTemplate.update("adm.localNmUpdate", map);
		}
		
		return (Integer)iProviderNsa;
	}
}
