package com.netmng.ctrl.adm;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.netmng.com.error.ErrorClass;
import com.netmng.ctrl.AbstractCtrl;
import com.netmng.dto.adm.stp.InterfaceInfoDTO;
import com.netmng.param.FileParam;
import com.netmng.param.adm.InterfaceInfoDTOList;
import com.netmng.param.adm.SrchErrorParam;
import com.netmng.param.adm.SrchEventParam;
import com.netmng.param.adm.SrchTopologyParam;
import com.netmng.svc.adm.AdmService;
import com.netmng.util.PropUtil;
import com.netmng.util.StrUtil;
import com.netmng.vo.ErrorLog;
import com.netmng.vo.FileInfo;
import com.netmng.vo.GnprInfo;
import com.netmng.vo.InterfaceInfo;
import com.netmng.vo.MsgInfo;
import com.netmng.vo.ProviderNsa;
import com.netmng.vo.Router;
import com.netmng.vo.RouterType;
import com.netmng.vo.StpNetwork;
import com.netmng.websvc.rest.client.param.gnpr.Gnpr;
import com.netmng.websvc.rest.client.param.gnpr.Info;
import com.netmng.websvc.rest.client.param.gnpr.IntermedianNode;
import com.netmng.websvc.rest.client.param.gnpr.Site;
import com.netmng.websvc.rest.client.param.ipdomain.Ipdomain;
import com.netmng.websvc.rest.client.param.link.Link;
import com.netmng.websvc.rest.server.vo.ResMsg;
import com.netmng.websvc.soap.parser.intra.owlparser.DuplicatedMap;
import com.netmng.websvc.soap.parser.intra.owlparser.DuplicatedMapInt;
import com.netmng.websvc.soap.parser.intra.owlparser.LinkSpec;
import com.netmng.websvc.soap.parser.intra.owlparser.NESpec;
import com.netmng.websvc.soap.parser.intra.owlparser.OWLUtility;
import com.netmng.websvc.soap.parser.intra.owlparser.SPSpec;
import com.netmng.websvc.rest.RESTClient;
import com.netmng.websvc.soap.svc.topology.inter.parser.InterDomainInformation;
import com.netmng.websvc.soap.svc.topology.inter.parser.TopologyParser;
import com.netmng.websvc.soap.svc.topology.inter.parser.TopologyReference;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.netmng.websvc.soap.parser.intra.owlparser.DuplicatedMapInt;

@Controller
public class AdmTopologyCtrllor extends AbstractCtrl {

	@Autowired(required=true) 
	private AdmService 	admService;
	@Value("#{netmngConfig['nsa.urn']}")
	private String		urn;
	@Value("#{netmngConfig['nsa.head.router.site_name']}")
	private String 		head_site_name;
	@Value("#{netmngConfig['nsa.head.router.ip']}")
	private String		head_ip;
	@Value("#{netmngConfig['nsa.head.router.urn']}")
	private String		head_urn;
	@Value("#{netmngConfig['nsa.head.router.type']}")
	private String 		head_router_type;
	@Value("#{netmngConfig['nsa.latency']}")
	private String 		latency;
	@Value("#{netmngConfig['dynamicKL.xmlns']}")
	private String		xmlns;
	@Value("#{netmngConfig['dynamicKL.xmlns_xsi']}")
	private String		xmlns_xsi;
	@Value("#{netmngConfig['dynamicKL.xmlns_schemaLocation']}")
	private String		xmlns_schemaLocation;
	
	// 토폴로지 관리(리스트)
	/*@RequestMapping(value="/adm/topologyL")
	public ModelAndView topologyL(
			@Valid SrchTopologyParam srchTopologyParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{
				mnv.addObject("routerDTOList", this.admService.getTopologyList());
				mnv.setViewName("/adm/topologyL");
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		
		return mnv;
	}*/
	@RequestMapping(value="/adm/topologyL")
	public ModelAndView topologyL(
			@Valid SrchTopologyParam srchTopologyParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		try{
			/*Inter Network 설정 정보*/
			StrUtil.sysPrint("providerNSA()");
			mnv.addObject("providerNSAList", this.admService.getProviderNsaList());
			
			StrUtil.sysPrint("StpNetwork()");
			mnv.addObject("stpNetworkList", this.admService.getStpNetworkList());
			
			StrUtil.sysPrint("StpLocal()");
			mnv.addObject("stpLocalList", this.admService.getStpLocalList());
			
			StrUtil.sysPrint("StpVlan()");
			mnv.addObject("stpVlanList", this.admService.getStpVlanList());
			
			/*Intra Network 설정 정보*/
			String sOwlSubPath	= "/upload/intraTopology/dynamicKL_Intra.owl";
			String sOwlFilePath = (new PropUtil()).getRootPath() + "WebContent" + sOwlSubPath;
			File owlFile = new File(sOwlFilePath);
			if(owlFile.exists()){
				OWLUtility owlu = new OWLUtility(sOwlFilePath);
				List<NESpec> listNESpec		= owlu.getNetworkEquipment();	/*NetworkEquipment*/
				List<SPSpec> listSPSpec		= owlu.getServicePoint();		/*SDP or STP*/
				List<LinkSpec> listLinkSpec	= owlu.getLinkSpec();			/*Path or FixedPath*/
				
				mnv.addObject("intra_listNESpec", listNESpec);
				mnv.addObject("intra_listSPSpec", listSPSpec);
				mnv.addObject("intra_listLinkSpec", listLinkSpec);
				
				String sOwlFileUrl = "http://";
				sOwlFileUrl += request.getLocalAddr()+":"+request.getLocalPort();
				sOwlFileUrl += request.getContextPath();
				sOwlFileUrl += sOwlSubPath;
				StrUtil.sysPrint("sFileUrl="+sOwlFileUrl);
				
				mnv.addObject("intra_fileUrl", sOwlFileUrl);
			}
			
			mnv.setViewName("/adm/topologyL");
		}catch(Exception e){
			result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		}		
		return mnv;
	}
	
	// 토폴로지 설정
	@RequestMapping(value="/adm/topologyConfigFile", method = RequestMethod.POST)
	public ModelAndView topologyConfigFile(
			FileParam file,
			BindingResult result,
			HttpServletRequest request) {
		StrUtil.sysPrint("topologyConfigFile start");

		ModelAndView mnv = new ModelAndView();
		MsgInfo msgInfo = new MsgInfo();
		
		if(!file.getFile()[0].isEmpty()) {
			String sProcType = request.getParameter("proc_type");
			StrUtil.sysPrint("sProcType="+sProcType);
			
			String dirStr	= file.getFileDir(request, 1);
			String sSubDri	= "";
			if(sProcType!=null){
				if(sProcType.equals("inter_file")){
					sSubDri = "upload/interTopology/";
				}else if(sProcType.equals("intra_file")){
					sSubDri = "upload/intraTopology/";
				}
				dirStr = dirStr.substring(0, dirStr.indexOf("WEB-INF"))+sSubDri;
				StrUtil.sysPrint("dirStr="+dirStr);
			}
			
			File dir = new File(dirStr);
			if(!dir.exists())
				dir.mkdirs();
			String fileName = "";
			if(sProcType!=null){
				if(sProcType.equals("inter_file")){
					fileName = "master.xml";
				}else if(sProcType.equals("intra_file")){
					fileName = "dynamicKL_Intra.owl";
				}
			}
			StrUtil.sysPrint("fileName="+fileName);
			File uploadFile = new File(dirStr + fileName);
			
			/*StrUtil.sysPrint("request.getContextPath()="+request.getContextPath());
			StrUtil.sysPrint("request.getLocalAddr()="+request.getLocalAddr());
			StrUtil.sysPrint("request.getRemoteAddr()="+request.getRemoteAddr());
			StrUtil.sysPrint("request.getRemoteHost()="+request.getRemoteHost());
			StrUtil.sysPrint("request.getRequestURI()="+request.getRequestURI());
			StrUtil.sysPrint("request.getServletPath()="+request.getServletPath());*/
			
			String sFileUrl = "http://";
			sFileUrl += request.getLocalAddr()+":"+request.getLocalPort();
			sFileUrl += request.getContextPath();
			sFileUrl += "/"+sSubDri;
			sFileUrl += fileName;
			StrUtil.sysPrint("sFileUrl="+sFileUrl);
			
			String sFilePath = dirStr+fileName;
			StrUtil.sysPrint("sFilePath="+sFilePath);
			
			try {
				if(sProcType!=null){
					if(sProcType.equals("inter_file") || sProcType.equals("intra_file")){
						file.getFile()[0].transferTo(uploadFile);
						FileInfo fileInfo = new FileInfo();
						fileInfo.setName(file.getFile()[0].getOriginalFilename());
						fileInfo.setPath(dirStr);
						fileInfo.setRealName(fileName);
						fileInfo.setSize(String.valueOf(file.getFile()[0].getSize()));
						fileInfo.setMime(file.getFile()[0].getContentType());
						/*StrUtil.sysPrint("fileInfo.getName()="		+fileInfo.getName());
						StrUtil.sysPrint("fileInfo.getPath()="		+fileInfo.getPath());
						StrUtil.sysPrint("fileInfo.getRealName()="	+fileInfo.getRealName());
						StrUtil.sysPrint("fileInfo.getSize()="		+fileInfo.getSize());
						StrUtil.sysPrint("fileInfo.getMime()="		+fileInfo.getMime());*/
						
						if(sProcType.equals("inter_file")){
							TopologyParser tp = new TopologyParser(sFileUrl);
							ArrayList<InterDomainInformation> listTopology = tp.createInterDomainInformationList();
							ArrayList<TopologyReference> listTopologyReference = tp.getTrlist();
							this.admService.interTopologyConfig(listTopology, listTopologyReference);
							/*
							for(int i=0; i<listTopology.size(); i++){
								InterDomainInformation interDomainInfo = listTopology.get(i);
								StrUtil.sysPrint("==========================================================================");					
								StrUtil.sysPrint("interDomainInfo.getNsa()="			+interDomainInfo.getNsa());
								StrUtil.sysPrint("interDomainInfo.getAdminContact()="	+interDomainInfo.getAdminContact());
								StrUtil.sysPrint("interDomainInfo.getNsa()="			+interDomainInfo.getEndPointAddress());
								StrUtil.sysPrint("interDomainInfo.getNetworkId()="		+interDomainInfo.getNetworkId());	
							}*/
							/*dynamicKL에 URL 전송*/
							com.netmng.websvc.rest.RESTClient c = new com.netmng.websvc.rest.RESTClient();
							c.interLink(sFileUrl);
						}else if(sProcType.equals("intra_file")){	
							/*dynamicKL에 URL 전송*/
							com.netmng.websvc.rest.RESTClient c = new com.netmng.websvc.rest.RESTClient();
							c.intraLink(sFileUrl);
						}
					}
				}
				mnv.addObject("mode", "CONFIG");
				mnv.setViewName("/adm/proc/topology");
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgInfo.setResult(false);
				if(sProcType.equals("inter_url")){
					msgInfo.setMsg("Inter Domain Topology 설정(XML 파일을 사용하여 설정:IllegalStateException) 오류입니다.");					
				}else if(sProcType.equals("intra_url")){
					msgInfo.setMsg("Intra Domain Topology 설정(OWL 파일을 사용하여 설정:IllegalStateException) 오류입니다.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgInfo.setResult(false);
				if(sProcType.equals("inter_url")){
					msgInfo.setMsg("Inter Domain Topology 설정(XML 파일을 사용하여 설정:IOException) 오류입니다.");					
				}else if(sProcType.equals("intra_url")){
					msgInfo.setMsg("Intra Domain Topology 설정(OWL 파일을 사용하여 설정:IOException) 오류입니다.");
				}			
			/*} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgInfo.setResult(false);
				msgInfo.setMsg("업로드 오류입니다.");*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgInfo.setResult(false);
				if(sProcType.equals("inter_url")){
					msgInfo.setMsg("Inter Domain Topology 설정(XML 파일을 사용하여 설정:Exception) 오류입니다.");					
				}else if(sProcType.equals("intra_url")){
					msgInfo.setMsg("Intra Domain Topology 설정(OWL 파일을 사용하여 설정:Exception) 오류입니다.");
				}
			}
		}else{
			msgInfo.setResult(false);
			msgInfo.setMsg("정상적인 파일이 아닙니다.");
		}
		return mnv;
	}
	
	// 토폴로지 설정
	@RequestMapping(value="/adm/topologyConfigUrl")
	public ModelAndView topologyConfigUrl(
			@Valid SrchTopologyParam srchTopologyParam,
			BindingResult result,
			HttpServletRequest request) {
		StrUtil.sysPrint("topologyConfigUrl start");
				
		ModelAndView mnv = new ModelAndView();
		MsgInfo msgInfo = new MsgInfo();
		
		String sProcType = request.getParameter("proc_type");
		String sUrlInter = request.getParameter("url_inter");
		String sUrlIntra = request.getParameter("url_intra");
		StrUtil.sysPrint("sProcType="+sProcType);
		StrUtil.sysPrint("sInterUrl="+sUrlInter);
		StrUtil.sysPrint("sIntraUrl="+sUrlIntra);
		
		if(sProcType!=null) {
			String sOwlSubPath	= "/upload/intraTopology/dynamicKL_Intra.owl";
			String sOwlFilePath = (new PropUtil()).getRootPath() + "WebContent" + sOwlSubPath;
			
			String sSubDri	= "";
			if(sProcType!=null){
				if(sProcType.equals("inter_url")){
					sSubDri = "/upload/interTopology/master.xml";
				}else if(sProcType.equals("intra_url")){
					sSubDri = "/upload/intraTopology/dynamicKL_Intra.owl";
				}
			}
			String sFilePath = (new PropUtil()).getRootPath() + "WebContent" + sSubDri;
			StrUtil.sysPrint("sFilePath="+sFilePath);
			try {
				if(sProcType.equals("inter_url")){
					TopologyParser tp = new TopologyParser(sUrlInter);
					ArrayList<InterDomainInformation> listTopology = tp.createInterDomainInformationList();
					//this.admService.interTopologyConfig(listTopology);
					ArrayList<TopologyReference> listTopologyReference = tp.getTrlist();
					this.admService.interTopologyConfig(listTopology, listTopologyReference);
					/*
					for(int i=0; i<listTopology.size(); i++){
						InterDomainInformation interDomainInfo = listTopology.get(i);
						StrUtil.sysPrint("==========================================================================");					
						StrUtil.sysPrint("interDomainInfo.getNsa()="			+interDomainInfo.getNsa());
						StrUtil.sysPrint("interDomainInfo.getAdminContact()="	+interDomainInfo.getAdminContact());
						StrUtil.sysPrint("interDomainInfo.getNsa()="			+interDomainInfo.getEndPointAddress());
						StrUtil.sysPrint("interDomainInfo.getNetworkId()="		+interDomainInfo.getNetworkId());	
						ArrayList<HashMap<String, ArrayList<String>>> listBidirectionalPort = interDomainInfo.getBidirectionalPort();
						for(int j=0; j<listBidirectionalPort.size(); j++){
							HashMap<String, ArrayList<String>> mBidirectionalPort = listBidirectionalPort.get(j);
							Iterator keyset = mBidirectionalPort.keySet().iterator();
							while(keyset.hasNext()){
								String key = keyset.next().toString();
			                    ArrayList<String> vlanLength = mBidirectionalPort.get(key);
			                    System.out.println("LocalID : " + key);
			                    for(String vlan : vlanLength){
			                        System.out.println("   Vlan : " + vlan);
			                    }
							}
						}
					}*/
					/*dynamicKL에 URL 전송*/
					com.netmng.websvc.rest.RESTClient c = new com.netmng.websvc.rest.RESTClient();
					c.interLink(sUrlInter);
				}else if(sProcType.equals("intra_url")){
					URL url = new URL(sUrlIntra); 
					URLConnection conn = url.openConnection(); 
					InputStreamReader isr = new InputStreamReader(conn.getInputStream(), "UTF-8");
					FileOutputStream fos = new FileOutputStream(sFilePath);
					int data;
				    while((data=isr.read()) != -1) {
				    	fos.write(data);
				    	fos.flush();
				    }
				    isr.close();
				    fos.close();
				    
				    /*dynamicKL에 URL 전송*/
					com.netmng.websvc.rest.RESTClient c = new com.netmng.websvc.rest.RESTClient();
					c.intraLink(sUrlIntra);
				}
				mnv.addObject("mode", "CONFIG");
				mnv.setViewName("/adm/proc/topology");
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgInfo.setResult(false);
				if(sProcType.equals("inter_url")){
					msgInfo.setMsg("Inter Domain Topology 설정(URL을 입력하여 설정:IllegalStateException) 오류입니다.");					
				}else if(sProcType.equals("intra_url")){
					msgInfo.setMsg("Intra Domain Topology 설정(URL을 입력하여 설정:IllegalStateException) 오류입니다.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgInfo.setResult(false);
				if(sProcType.equals("inter_url")){
					msgInfo.setMsg("Inter Domain Topology 설정(URL을 입력하여 설정:IOException) 오류입니다.");					
				}else if(sProcType.equals("intra_url")){
					msgInfo.setMsg("Intra Domain Topology 설정(URL을 입력하여 설정:IOException) 오류입니다.");
				}			
			/*} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgInfo.setResult(false);
				msgInfo.setMsg("업로드 오류입니다.");*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgInfo.setResult(false);
				if(sProcType.equals("inter_url")){
					msgInfo.setMsg("Inter Domain Topology 설정(URL을 입력하여 설정:Exception) 오류입니다.");					
				}else if(sProcType.equals("intra_url")){
					msgInfo.setMsg("Intra Domain Topology 설정(URL을 입력하여 설정:Exception) 오류입니다.");
				}
			}
		}else{
			msgInfo.setResult(false);
			msgInfo.setMsg("정상적인 파일이 아닙니다.");
		}
		return mnv;
	}
		
	// 토폴로지 alias 설정 update
	@RequestMapping(value="/adm/topologyAliasU")
	public ModelAndView topologyAliasU(
			@Valid SrchTopologyParam srchTopologyParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		try{
			String sNsaAddr = request.getParameter("NSA_ADDR");
			if(sNsaAddr!=null && !sNsaAddr.equals("")){
				ProviderNsa providerNsa = new ProviderNsa();
				providerNsa.setNsa_addr(sNsaAddr);
				mnv.addObject("providerNSA", this.admService.getProviderNsaSelect(providerNsa));
				
				List<StpNetwork> listStpNetwork = this.admService.getStpNetworkList();
				String sNetworkId = "";
				for(int i=0; i<listStpNetwork.size(); i++){
					StpNetwork stpNetwork = listStpNetwork.get(i);					
					if(stpNetwork.getNsa_addr().equals(sNsaAddr)){
						sNetworkId = stpNetwork.getNetwork_id();
						mnv.addObject("stpNetwork", stpNetwork);
					}
				}
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("network_id", sNetworkId);
				mnv.addObject("stpLocalList", this.admService.getStpLocalListSelect(map));
			}			
			mnv.setViewName("/adm/pop/topologyAlias");
		}catch(Exception e){
			result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		}		
		return mnv;
	}
	
	// 토폴로지 alias 설정 update 처리
	@RequestMapping(value="/adm/topologyAliasUP")
	public ModelAndView topologyAliasUP(
			@Valid SrchTopologyParam srchTopologyParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		try{
			String sNsaAddr		= request.getParameter("nsa_addr");
			String sNsaNm		= request.getParameter("nsa_nm");
			String sNetworkId	= request.getParameter("network_id");
			String sNetworkNm	= request.getParameter("network_nm");
			String[] arrLocalId	= request.getParameterValues("local_id");
			String[] arrLocalNm	= request.getParameterValues("local_nm");
			
			Map map = new HashMap();
			map.put("nsa_addr", sNsaAddr);
			map.put("nsa_nm", sNsaNm);
			map.put("network_id", sNetworkId);
			map.put("network_nm", sNetworkNm);
			map.put("local_id", arrLocalId);
			map.put("local_nm", arrLocalNm);
			
			this.admService.topologyAliasUpdate(map);
			
			/*if(sNsaAddr!=null && !sNsaAddr.equals("")){
				ProviderNsa providerNsa = new ProviderNsa();
				providerNsa.setNsa_addr(sNsaAddr);
				mnv.addObject("providerNSA", this.admService.getProviderNsaSelect(providerNsa));
				
				List<StpNetwork> listStpNetwork = this.admService.getStpNetworkList();
				String sNetworkId = "";
				for(int i=0; i<listStpNetwork.size(); i++){
					StpNetwork stpNetwork = listStpNetwork.get(i);					
					if(stpNetwork.getNsa_addr().equals(sNsaAddr)){
						sNetworkId = stpNetwork.getNetwork_id();
						mnv.addObject("stpNetwork", stpNetwork);
					}
				}
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("network_id", sNetworkId);
				mnv.addObject("stpLocalList", this.admService.getStpLocalListSelect(map));
			}*/	
			mnv.addObject("mode", "CONFIG_ALIAS");
			mnv.setViewName("/adm/proc/topology");
		}catch(Exception e){
			result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		}		
		return mnv;
	}
	
	
	
	// 토폴로지 수정(화면)
	@RequestMapping(value="/adm/topologyUF")
	public ModelAndView topologyUF(
			InterfaceInfoDTO param,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("kind", "B");
	
			mnv.addObject("interfaceInfoDTOList", this.admService.getInterfaceInfoList(map));
			mnv.addObject("routerTypeList", this.admService.getRouterTypeList());
			mnv.setViewName("/adm/topologyUF");
		} catch (Exception e) {
			result.rejectValue("seq", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		}
		
		
		return mnv;
	}
	
	// 토폴로지 수정(저장)
	@RequestMapping(value="/adm/proc/topologyUP")
	public ModelAndView topologyUP(
			@Valid InterfaceInfoDTOList param,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
			
				List<RouterType> routerTypeList = this.admService.getRouterTypeList();
				Map<String, String> routerKind = new HashMap<String, String>();
				
				for(RouterType routerType : routerTypeList) {
					routerKind.put(routerType.getSeq(), routerType.getName());
				}
				
				Router[] routerList = new Router[param.getSite_name().length + 1];
				InterfaceInfo[] interfaceHList = new InterfaceInfo[param.getSite_name().length];
				InterfaceInfo[] interfaceBList = new InterfaceInfo[param.getSite_name().length];
								
				routerList[0] = new Router();
				routerList[0].setSite_name(this.head_site_name);
				routerList[0].setKind("H");
				routerList[0].setUrn(this.head_urn);
				routerList[0].setIp(this.head_ip);
				routerList[0].setRouter_type_seq(this.head_router_type);
				
				Ipdomain ipdomain = new Ipdomain();
				List<com.netmng.websvc.rest.client.param.ipdomain.Info> stpList = new ArrayList<com.netmng.websvc.rest.client.param.ipdomain.Info>();
				
				{
					com.netmng.websvc.rest.client.param.ipdomain.Info stpInfo = new com.netmng.websvc.rest.client.param.ipdomain.Info();
					
					stpInfo.stp = this.head_urn;
					stpInfo.ip = this.head_ip;
					stpInfo.routerType = routerKind.get(this.head_router_type);
					
					stpList.add(stpInfo);	
				}
				
				for(int i=1; i<routerList.length; i++) {
					routerList[i] = new Router();
					routerList[i].setSite_name(param.getSite_name()[i-1]);
					routerList[i].setKind("B");
					routerList[i].setUrn(this.urn + param.getUrn()[i-1]);
					routerList[i].setIp(param.getIp()[i-1]);
					routerList[i].setRouter_type_seq(param.getRouter_type_seq()[i-1]);
					
					com.netmng.websvc.rest.client.param.ipdomain.Info stpInfo = new com.netmng.websvc.rest.client.param.ipdomain.Info();
					
					stpInfo.stp = this.urn + param.getUrn()[i-1];
					stpInfo.ip = param.getIp()[i-1];
					stpInfo.routerType = routerKind.get(param.getRouter_type_seq()[i-1]);
					
					stpList.add(stpInfo);
				}
				
				ipdomain.infoList = stpList;
				ipdomain.numberOfEntries = String.valueOf(ipdomain.infoList.size());
				
				for(int i=0; i<interfaceHList.length; i++) {
					interfaceHList[i] = new InterfaceInfo();
					interfaceBList[i] = new InterfaceInfo();
					
					interfaceHList[i].setPrimary_ip(param.getLink_primary_ip()[i]);
					interfaceHList[i].setSecondary_ip(param.getLink_secondary_ip()[i]);
					interfaceHList[i].setPrimary_bw(param.getPrimary_bw()[i]);
					interfaceHList[i].setSecondary_bw(param.getSecondary_bw()[i]);

					interfaceBList[i].setPrimary_ip(param.getPrimary_ip()[i]);
					interfaceBList[i].setSecondary_ip(param.getSecondary_ip()[i]);
					interfaceBList[i].setPrimary_bw(param.getPrimary_bw()[i]);
					interfaceBList[i].setSecondary_bw(param.getSecondary_bw()[i]);
				}
				
				this.admService.topologyUpdate(routerList, interfaceHList, interfaceBList);
				
				String group_seq = interfaceHList[0].getGroup_seq();
				// 토폴로지 수정(rest) 2.3, 2.5, 2.8호출
				Gnpr gnpr = new Gnpr();
				
				List<Info> bGnprInfoList = new ArrayList<Info>();
				List<Info> rGnprInfoList = new ArrayList<Info>();
				
				Link link = new Link();
				
				List<com.netmng.websvc.rest.client.param.link.Info> bLinkInfoList = new ArrayList<com.netmng.websvc.rest.client.param.link.Info>();
				List<com.netmng.websvc.rest.client.param.link.Info> rLinkInfoList = new ArrayList<com.netmng.websvc.rest.client.param.link.Info>();
								
				for(int i=0; i<routerList.length; i++) {
					for(int j=1+i; j<routerList.length; j++) {						
						
						Info bGnprInfo = new Info();
						Info rGnprInfo = new Info();
						Site bSiteA = new Site();
						Site rSiteA = new Site();
						Site bSiteB = new Site();
						Site rSiteB = new Site();
						
						com.netmng.websvc.rest.client.param.link.Info bLinkInfo = new com.netmng.websvc.rest.client.param.link.Info();
						com.netmng.websvc.rest.client.param.link.Info rLinkInfo = new com.netmng.websvc.rest.client.param.link.Info();
												
						bSiteA.router = routerList[i].getIp();
						rSiteA.router = routerList[i].getIp();
						bSiteA._interface = i==0? interfaceHList[j-1].getPrimary_ip() : interfaceBList[i-1].getPrimary_ip();
						rSiteA._interface = i==0? interfaceHList[j-1].getSecondary_ip() : interfaceBList[i-1].getSecondary_ip();
						
						bLinkInfo.sourceRouter = routerList[i].getIp();
						rLinkInfo.sourceRouter = routerList[i].getIp();
						bLinkInfo.sourceInterface = i==0? interfaceHList[j-1].getPrimary_ip() : interfaceBList[i-1].getPrimary_ip();
						rLinkInfo.sourceInterface = i==0? interfaceHList[j-1].getSecondary_ip() : interfaceBList[i-1].getSecondary_ip();
						
						bSiteB.router = routerList[j].getIp();
						rSiteB.router = routerList[j].getIp();
						bSiteB._interface = interfaceBList[j-1].getPrimary_ip();
						rSiteB._interface = interfaceBList[j-1].getSecondary_ip();
						
						bLinkInfo.destinationRouter = routerList[j].getIp();
						rLinkInfo.destinationRouter = routerList[j].getIp();
						bLinkInfo.destinationInterface = interfaceBList[j-1].getPrimary_ip();
						rLinkInfo.destinationInterface = interfaceBList[j-1].getSecondary_ip();
						
						bGnprInfo.atoBBandwidth.value = i==0? interfaceHList[j-1].getPrimary_bw() : interfaceBList[i-1].getPrimary_bw();
						bGnprInfo.btoABandwidth.value = interfaceBList[j-1].getPrimary_bw();
						rGnprInfo.atoBBandwidth.value = i==0? interfaceHList[j-1].getSecondary_bw() : interfaceBList[i-1].getSecondary_bw();
						rGnprInfo.btoABandwidth.value = interfaceBList[j-1].getSecondary_bw();
						
						long bBandWidth = Long.parseLong(bGnprInfo.atoBBandwidth.value) > Long.parseLong(bGnprInfo.btoABandwidth.value) ? 
								Long.parseLong(bGnprInfo.btoABandwidth.value) : Long.parseLong(bGnprInfo.atoBBandwidth.value);
						long rBandWidth = Long.parseLong(rGnprInfo.atoBBandwidth.value) > Long.parseLong(rGnprInfo.btoABandwidth.value) ? 
								Long.parseLong(rGnprInfo.btoABandwidth.value) : Long.parseLong(rGnprInfo.atoBBandwidth.value);
						
						bGnprInfo.atoBBandwidth.value = String.valueOf(bBandWidth);
						bGnprInfo.btoABandwidth.value = String.valueOf(bBandWidth);
						rGnprInfo.atoBBandwidth.value = String.valueOf(rBandWidth);
						rGnprInfo.btoABandwidth.value = String.valueOf(rBandWidth);
						
						bLinkInfo.bandwidth.value = String.valueOf(bBandWidth);
						rLinkInfo.bandwidth.value = String.valueOf(rBandWidth);
								
						bGnprInfo.latency.value = this.latency;
						rGnprInfo.latency.value = this.latency;
						
						if(routerList[i].getKind().equals("B")) {
							IntermedianNode bIntermedianNode = new IntermedianNode();
							IntermedianNode rIntermedianNode = new IntermedianNode();
							
							bIntermedianNode.router = routerList[0].getIp();
							rIntermedianNode.router = routerList[0].getIp();
							
							bIntermedianNode.linterface = interfaceHList[i-1].getPrimary_ip();
							rIntermedianNode.linterface = interfaceHList[i-1].getSecondary_ip();
							
							bIntermedianNode.rinterface = interfaceHList[j-1].getPrimary_ip();
							rIntermedianNode.rinterface = interfaceHList[j-1].getSecondary_ip();
							
							bGnprInfo.intermedianNode = bIntermedianNode;
							rGnprInfo.intermedianNode = rIntermedianNode;
						}
						
						bGnprInfo.siteA = bSiteA;
						bGnprInfo.siteB = bSiteB;
						rGnprInfo.siteA = rSiteA;
						rGnprInfo.siteB = rSiteB;
						
						bGnprInfoList.add(bGnprInfo);
						rGnprInfoList.add(rGnprInfo);
						
						bLinkInfoList.add(bLinkInfo);
						rLinkInfoList.add(rLinkInfo);
						
					}
					
				}

				boolean flag = false;
				String	errorMsg = null;
				
				{
					JAXBContext jaxbContext = JAXBContext.newInstance(Ipdomain.class);
					StringWriter st = new StringWriter();
					Marshaller marshaller = jaxbContext.createMarshaller();
					marshaller.marshal(ipdomain, st);
					//marshaller.marshal(ipdomain, new File("../../../ipdomain.xml"));
					
					ClientConfig config = new DefaultClientConfig();   
			        Client client = Client.create(config);   
			        WebResource service = client.resource("http://203.230.116.210:8080/nsi/management/stp/ipdomain");
			        ClientResponse response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).post(ClientResponse.class, st.toString());
			        
			        if(response.getStatus() <= 201) {
				        System.out.println("ipdomain post 성공" + response.getStatus());
				        response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).get(ClientResponse.class);
				        if(response.getStatus() <= 201) {
				        	flag = true;
				        } else {
				        	jaxbContext = JAXBContext.newInstance(ResMsg.class);
				        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				        	//String msg = response.getEntity(String.class).replaceAll(this.xmlns, "").replaceAll(this.xmlns_xsi, "").replaceAll(this.xmlns_schemaLocation, "");
					        //ResMsg resMsg = (ResMsg)unmarshaller.unmarshal(new StringReader(msg));
				        	//errorMsg = resMsg.getCause();
				        	
				        	ErrorLog errorLog = new ErrorLog();
				        	errorLog.setUrl("/adm/proc/topologyUP.do");
				        	errorLog.setName("dynamicKL::ipdomain::get");
				        	errorLog.setLog_message(response.toString());
				        	this.admService.errorLogInsert(errorLog);
				        }
			        } else {
			        	System.out.println("ipdomain 실패" + response.getStatus());
			        	jaxbContext = JAXBContext.newInstance(ResMsg.class);
			        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			        	//String msg = response.getEntity(String.class).replaceAll(this.xmlns, "").replaceAll(this.xmlns_xsi, "").replaceAll(this.xmlns_schemaLocation, "");
				        //ResMsg resMsg = (ResMsg)unmarshaller.unmarshal(new StringReader(msg));
			        	//errorMsg = resMsg.getCause();
			        	
			        	ErrorLog errorLog = new ErrorLog();
			        	errorLog.setUrl("/adm/proc/topologyUP.do");
			        	errorLog.setName("dynamicKL::ipdomain::post");
			        	errorLog.setLog_message(response.toString());
			        	this.admService.errorLogInsert(errorLog);
			        }
				}
				
				bGnprInfoList.addAll(rGnprInfoList);
				
				gnpr.infoList = bGnprInfoList;
				gnpr.numberOfEntries = String.valueOf(gnpr.infoList.size());
				
				if(flag)
				{
					flag = false;
					JAXBContext jaxbContext = JAXBContext.newInstance(Gnpr.class);
					StringWriter st = new StringWriter();
					Marshaller marshaller = jaxbContext.createMarshaller();
					marshaller.marshal(gnpr, st);
					//marshaller.marshal(gnpr, new File("../../../gnpr.xml"));
					
					ClientConfig config = new DefaultClientConfig();   
			        Client client = Client.create(config);   
			        WebResource service = client.resource("http://203.230.116.210:8080/nsi/management/gnpr");
			        ClientResponse response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).post(ClientResponse.class, st.toString());
			        
			        if(response.getStatus() <= 201) {
				        System.out.println("gnpr post 성공" + response.getStatus());
				        response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).get(ClientResponse.class);
				        if(response.getStatus() <= 201) {
				        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				        	Gnpr msg = (Gnpr) unmarshaller.unmarshal(new StringReader(response.getEntity(String.class).replaceAll(this.xmlns, "").replaceAll(this.xmlns_xsi, "").replaceAll(this.xmlns_schemaLocation, "")));
				        	
				        	List<GnprInfo> gnprInfoList = new ArrayList<GnprInfo>();
					        
					        for(Info info : msg.infoList) {
					        	GnprInfo gnprInfo = new GnprInfo();
					        	gnprInfo.setGroup_seq(group_seq);
					        	gnprInfo.setGnpr_id(info.gnprId);
					        	gnprInfo.setSite_a_router_ip(info.siteA.router);
					        	gnprInfo.setSite_a_interface_ip(info.siteA._interface);
					        	gnprInfo.setSite_b_router_ip(info.siteB.router);
					        	gnprInfo.setSite_b_interface_ip(info.siteB._interface);
					        	
					        	gnprInfoList.add(gnprInfo);
							}
					        this.admService.gnprInfoUpdate(gnprInfoList);
				        	
				        	flag = true;
				        } else {
				        	jaxbContext = JAXBContext.newInstance(ResMsg.class);
				        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				        	//String msg = response.getEntity(String.class).replaceAll(this.xmlns, "").replaceAll(this.xmlns_xsi, "").replaceAll(this.xmlns_schemaLocation, "");
					        //ResMsg resMsg = (ResMsg)unmarshaller.unmarshal(new StringReader(msg));
				        	//errorMsg = resMsg.getCause();
				        	
				        	ErrorLog errorLog = new ErrorLog();
				        	errorLog.setUrl("/adm/proc/topologyUP.do");
				        	errorLog.setName("dynamicKL::gnpr::get");
				        	errorLog.setLog_message(response.toString());
				        	this.admService.errorLogInsert(errorLog);
				        }
			        	
			        } else {
			        	System.out.println("gnpr 실패" + response.getStatus());
			        	jaxbContext = JAXBContext.newInstance(ResMsg.class);
			        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			        	//String msg = response.getEntity(String.class).replaceAll(this.xmlns, "").replaceAll(this.xmlns_xsi, "").replaceAll(this.xmlns_schemaLocation, "");
				        //ResMsg resMsg = (ResMsg)unmarshaller.unmarshal(new StringReader(msg));
			        	//errorMsg = resMsg.getCause();
			        	
			        	ErrorLog errorLog = new ErrorLog();
			        	errorLog.setUrl("/adm/proc/topologyUP.do");
			        	errorLog.setName("dynamicKL::gnpr::post");
			        	errorLog.setLog_message(response.toString());
			        	this.admService.errorLogInsert(errorLog);
			        }
				}
				

				bLinkInfoList.addAll(rLinkInfoList);
				link.infoList = bLinkInfoList;
				link.numberOfEntries = String.valueOf(link.infoList.size());
				
				if(flag)
				{
					flag = false;
					JAXBContext jaxbContext = JAXBContext.newInstance(Link.class);
					//StringWriter st = new StringWriter();
					Marshaller marshaller = jaxbContext.createMarshaller();
					//marshaller.marshal(link, st);
					marshaller.marshal(link, new File(request.getSession().getServletContext().getRealPath("/") + "WEB-INF/xml/link.xml"));
					flag = true;
					/*
					ClientConfig config = new DefaultClientConfig();   
			        Client client = Client.create(config);   
			        WebResource service = client.resource("http://203.230.116.210:8080/nsi/management/link");
			        ClientResponse response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).post(ClientResponse.class, st.toString());

			        if(response.getStatus() <= 201) {
				        System.out.println("Link 성공" + response.getStatus());
				        response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).get(ClientResponse.class);
				        if(response.getStatus() <= 201) {
				        	System.out.println(response.toString());
				        	flag = true;
				        }
			        } else {
				        System.out.println("Link 실패" + response.getStatus());
				        jaxbContext = JAXBContext.newInstance(ResMsg.class);
			        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			        	String msg = response.getEntity(String.class).replaceAll(this.xmlns, "").replaceAll(this.xmlns_xsi, "").replaceAll(this.xmlns_schemaLocation, "");
				        ResMsg resMsg = (ResMsg)unmarshaller.unmarshal(new StringReader(msg));
			        	errorMsg = resMsg.getCause();
			        }
			        */
				}
				//wadl2java.bat -o D:\netmng\dynamicKL -p com.netmng.websvc.rest.client http://203.230.116.210:8080/NSI_Requester/wadl/dynamicKL.wadl
				if(flag) {
					mnv.addObject("mode", "UPDATE");
					mnv.setViewName("/adm/proc/topology");
				} else {
					result.rejectValue("site_name", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}
			} catch (Exception e) {
	        	
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/adm/proc/topologyUP.do");
	        	errorLog.setName("dynamicKL::ipdomain or gnpr");
	        	errorLog.setLog_message(e.toString());
	        	this.admService.errorLogInsert(errorLog);
	        	
				result.rejectValue("site_name", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
			}
			
		}

		return mnv;
	}
	
	// 토폴로지 초기화
	@RequestMapping(value="/adm/proc/nsaInitP")
	public ModelAndView nsaInitP(
			InterfaceInfoDTOList param,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		File file = null;
		BufferedReader inFile = null;
		
		try {
			
			ClientConfig config = new DefaultClientConfig();   
			Client client = Client.create(config); 
			WebResource service = client.resource("http://203.230.116.210:8080/nsi/management/init");
	        ClientResponse response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).post(ClientResponse.class);
	        
	        if(response.getStatus() <= 201) {
		        System.out.println("init 성공" + response.getStatus());
		        file = new File(request.getSession().getServletContext().getRealPath("/") + "WEB-INF/xml/link.xml");
		        StringBuffer sb = new StringBuffer(); 
		        
		        
		        if (file.exists()) {
		        	inFile = new BufferedReader(new FileReader(file));
		        	String sLine = null;
		        
		        	while ((sLine = inFile.readLine()) != null) {
		        		sb.append(sLine);  
		        	}
		        	inFile.close();
		 
		        }
		        service = client.resource("http://203.230.116.210:8080/nsi/management/link");
		        response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).post(ClientResponse.class, sb.toString());

		        if(response.getStatus() <= 201) {
			        System.out.println("Link 성공" + response.getStatus());
			        response = service.accept("text/xml;charset=UTF-8").type(MediaType.APPLICATION_XML).get(ClientResponse.class);
			        if(response.getStatus() <= 201) {
			        	System.out.println(response.toString());
				        this.admService.nsaInitUpdate();
						mnv.addObject("mode", "NSAINIT");
						mnv.setViewName("/adm/proc/topology");
			        } else {
			        	JAXBContext jaxbContext = JAXBContext.newInstance(ResMsg.class);
			        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			        	//String msg = response.getEntity(String.class).replaceAll(this.xmlns, "").replaceAll(this.xmlns_xsi, "").replaceAll(this.xmlns_schemaLocation, "");
				        //ResMsg resMsg = (ResMsg)unmarshaller.unmarshal(new StringReader(msg));
			        			        	
			        	ErrorLog errorLog = new ErrorLog();
			        	errorLog.setUrl("/adm/proc/nsaInitP.do");
			        	errorLog.setName("dynamicKL::link::get");
			        	errorLog.setLog_message(response.toString());
			        	this.admService.errorLogInsert(errorLog);
			        	
			        	throw new Exception();
			        }
		        } else {
			        System.out.println("Link 실패" + response.getStatus());
			        JAXBContext jaxbContext = JAXBContext.newInstance(Link.class);
			        jaxbContext = JAXBContext.newInstance(ResMsg.class);
		        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		        	//String msg = response.getEntity(String.class).replaceAll(this.xmlns, "").replaceAll(this.xmlns_xsi, "").replaceAll(this.xmlns_schemaLocation, "");
			        //ResMsg resMsg = (ResMsg)unmarshaller.unmarshal(new StringReader(msg));
		        	
					ErrorLog errorLog = new ErrorLog();
					errorLog.setUrl("/adm/proc/nsaInitP.do");
					errorLog.setName("dynamicKL::link::post");
					errorLog.setLog_message(response.toString());
					this.admService.errorLogInsert(errorLog);

			        throw new Exception();
		        }
		        
	        } else {
	        	System.out.println("init 실패" + response.getStatus());
	        	
	        	JAXBContext jaxbContext = JAXBContext.newInstance(ResMsg.class);
	        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	        	//String msg = response.getEntity(String.class).replaceAll(this.xmlns, "").replaceAll(this.xmlns_xsi, "").replaceAll(this.xmlns_schemaLocation, "");
		        //ResMsg resMsg = (ResMsg)unmarshaller.unmarshal(new StringReader(msg));
	        			        	
	        	ErrorLog errorLog = new ErrorLog();
	        	errorLog.setUrl("/adm/proc/nsaInitP.do");
	        	errorLog.setName("dynamicKL::init");
	        	errorLog.setLog_message(response.toString());
	        	this.admService.errorLogInsert(errorLog);
	        	throw new Exception();
	        }
			
			
		} catch (Exception e) {
			e.printStackTrace();
			result.rejectValue("site_name", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
        	
			ErrorLog errorLog = new ErrorLog();
			errorLog.setUrl("/adm/proc/nsaInitP.do");
			errorLog.setName("dynamicKL::init or link");
			errorLog.setLog_message(e.toString());
			this.admService.errorLogInsert(errorLog);
		} finally {
			if(inFile != null) {
				try {
					inFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return mnv;
	}
	
	// 폴트(리스트)
	@RequestMapping(value="/adm/eventL")
	public ModelAndView eventL(
			@Valid SrchEventParam srchEventParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{
				srchEventParam.setTotRow(this.admService.getSrchEventCnt(srchEventParam));
				mnv.addObject("eventList", this.admService.getSrchEventList(srchEventParam));
				mnv.addObject("srchParam", srchEventParam);
				mnv.setViewName("/adm/eventL");
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		
		return mnv;
	}
	
	// 오류발생(리스트)
	@RequestMapping(value="/adm/errorL")
	public ModelAndView errorL(
			@Valid SrchErrorParam srchErrorParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try{
				srchErrorParam.setTotRow(this.admService.getSrchErrorCnt(srchErrorParam));
				mnv.addObject("errorList", this.admService.getSrchErrorList(srchErrorParam));
				mnv.addObject("srchParam", srchErrorParam);
				mnv.setViewName("/adm/errorL");
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		
		return mnv;
	}
	
}
