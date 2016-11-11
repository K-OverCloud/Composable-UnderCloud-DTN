<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List"%> 
<%@ page import="com.netmng.websvc.soap.parser.intra.owlparser.NESpec"%> 
<%@ page import="com.netmng.websvc.soap.parser.intra.owlparser.DuplicatedMapInt"%> 
<%@ page import="com.netmng.websvc.soap.parser.intra.owlparser.SPSpec"%> 
<%@ page import="com.netmng.websvc.soap.parser.intra.owlparser.DuplicatedMap"%> 
<%@ page import="com.netmng.websvc.soap.parser.intra.owlparser.LinkSpec"%> 


<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>    
<%@	taglib prefix="page" 	uri="/WEB-INF/tld/PagingTag.tld"%>

<style type="text/css">
#topologyListTb tbody tr:hover
{
	background-color: LemonChiffon;
}
#siteListTb tbody tr:hover
{
	background-color: LemonChiffon;
}
</style>  
<script type="text/javascript">
	function nsaInit() {
		disableConfirm();
		$("#nsaInitFrm").attr("target", "_proc").submit();
	}
	function confirmNsaInit() {
		loadConfirm($("#nsaInitDv"));
	}
	function viewRowData(ref_url){
		window.open(ref_url, "_blank", "width=800, height=700, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=no");
	}
	function setAlias(nsa_addr){
		var url = "${pageContext.request.contextPath}/adm/topologyAliasU.do?NSA_ADDR="+nsa_addr;
		window.open(url, "_blank", "width=750, height=400, toolbar=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=no");
		//alert("this function is working!!");
	}
	function goList() {
		$("#topologyFrm").attr("action", "${pageContext.request.contextPath}/adm/topologyL.do").submit();
	}
	function topologyConfig(proc_type){
		if(proc_type=="inter_file" || proc_type=="inter_url"){
			if(proc_type=="inter_file"){
				if($("#interFrm").find('#file').val()==""){
					alert("설정할 파일을 선택하세요.");
					return;
				}else{
					$("#interFrm").attr("enctype", "multipart/form-data");
					$("#interFrm").attr("action", "${pageContext.request.contextPath}/adm/topologyConfigFile.do");
				}
			}else if(proc_type=="inter_url"){
				if($("#interFrm").find('#url_inter').val()==""){
					alert("설정할 URL을 입력하세요.");
					return;
				}else{
					$("#interFrm").attr("enctype", "application/x-www-form-urlencoded");
					$("#interFrm").attr("action", "${pageContext.request.contextPath}/adm/topologyConfigUrl.do");
				}
			}			
			$("#interFrm").find('#proc_type').val(proc_type);
			$("#interFrm").attr("target", "_proc").submit();
		}else if(proc_type=="intra_file" || proc_type=="intra_url"){
			if(proc_type=="intra_file"){
				if($("#intraFrm").find('#file').val()==""){
					alert("설정할 파일을 선택하세요.");
					return;
				}else{
					$("#intraFrm").attr("enctype", "multipart/form-data");
					$("#intraFrm").attr("action", "${pageContext.request.contextPath}/adm/topologyConfigFile.do");
				}
			}else if(proc_type=="intra_url"){
				if($("#interFrm").find('#url_intra').val()==""){
					alert("설정할 URL을 입력하세요.");
					return;
				}else{
					$("#intraFrm").attr("enctype", "application/x-www-form-urlencoded");
					$("#intraFrm").attr("action", "${pageContext.request.contextPath}/adm/topologyConfigUrl.do");
				}
			}
			$("#intraFrm").find('#proc_type').val(proc_type);
			$("#intraFrm").attr("target", "_proc").submit();
		}
	}
</script>
<!-- left_column -->
<div id="left_column">
	<!-- left_area  -->
	<div id="left_area">
		<ul class="left_menu">
			<li class="left_tit">
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3.gif" alt="">
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/adm/userL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image24','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_1_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_1_off.gif" width="167" height="22" id="Image24">
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/adm/userMonitorL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image22','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_2_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_2_off.gif" name="Image22" width="167" height="22" border="0">
				</a>
			</li>	
			<li>
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_3_on.gif" name="Image23" width="167" height="22" border="0">
			</li>	
		</ul>
	</div>
	<!-- //left_area  -->
</div>
<!-- //left_column -->	

<!-- content -->
<div id="content">
	<div id="location">
		<a href="#" class="loc">Home</a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<a href="#" class="loc"><s:message code='com.netmng.menu.admin' /></a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<a href="#" class="loc"><s:message code='com.netmng.menu.admin3' /></a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<span class="now"><s:message code='com.netmng.menu.admin31' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/title3_3.gif" alt=""></h2>
	<!-- tab_area -->
	<div id="tab_area">
		<div class="tab_Monitor_1"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_3_1_on.gif" > </div>
		<div class="tab_Monitor_2">
			<a href="${pageContext.request.contextPath}/adm/eventL.do">
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_3_2_off.gif" >
			</a>
		</div>
		<div class="tab_Monitor_3">
			<a href="${pageContext.request.contextPath}/adm/errorL.do">
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_3_3_off.gif">
			</a>
		</div>
	</div>
	<!-- //tab_area -->



	<%-- <f:form name="routerDTOList" id="routerDTOList" action="${pageContext.request.contextPath}/adm/topologyUF.do" method="GET"> --%>
	
		<!-- 
		<c:forEach items="${routerDTOList}" varStatus="status" var="routerDTO">
		<c:if test="${routerDTO.kind == 'H'}">	
		토폴로지 정보(<c:out value="${routerDTO.site_name}" />)
		<table border="1">
		<thead>
		<tr>
			<th>urn</th>
			<th>Router IP</th>
			<th>대응 Site name</th>
			<th>Interface 1</th>
			<th>Interface 2</th>
		</tr>
		</thead>
		<tr>
			<td rowspan="<s:eval expression="routerDTO.interfaceInfoDTOList.size()"/>"><c:out value="${routerDTO.urn}" /></td>
			<td rowspan="<s:eval expression="routerDTO.interfaceInfoDTOList.size()"/>"><c:out value="${routerDTO.ip}" /></td>
			<td><c:out value="${routerDTO.interfaceInfoDTOList[0].link_interface.routerDTO.site_name}" /></td>
			<td><c:out value="${routerDTO.interfaceInfoDTOList[0].primary_ip}" /></td>
			<td><c:out value="${routerDTO.interfaceInfoDTOList[0].secondary_ip}" /></td>
		</tr>
		<c:forEach items="${routerDTO.interfaceInfoDTOList}" begin="1" varStatus="status" var="interfaceInfoDTO" >
		<tr>
			<td><c:out value="${interfaceInfoDTO.link_interface.routerDTO.site_name}" /></td>
			<td><c:out value="${interfaceInfoDTO.primary_ip}" /></td>
			<td><c:out value="${interfaceInfoDTO.secondary_ip}" /></td>
		</tr>
		</c:forEach>
		</table>
		</c:if>
		</c:forEach>
		 -->
		 
		<!-- Inter Domain Topology 설정 -->
		
	<form name="interFrm" id="interFrm" action="${pageContext.request.contextPath}/adm/topologyConfig.do" method="post" enctype="multipart/form-data">
		<input type="hidden" id="proc_type" name="proc_type" value="">
	
		<div id="topologyL_1" style="width:700px;">
			<%-- <h3><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/title_3_3_1.gif" alt=""></h3> --%>
			<h3>▣ <s:message code='com.netmng.text.network.nsa.title.InterDomainConfig' /></h3>
			
			<br />
			<div>
				<table border="0" class="tb_topologyL" style="width:700px;">
					<colgroup>
						<col width="200px">
						<col width="*">
						<col width="70px">
					</colgroup>
					<tbody>
						<tr>
							<th class="top1" scope="row" style="text-align:left;">* <s:message code='com.netmng.text.network.nsa.title.InterDomainXMLConfig' /></td>
							<td class="top2"><input type="file" id="file" name="file" /></td>
							<td class="top2"><a href="#none" onclick="topologyConfig('inter_file');"><s:message code='com.netmng.text.network.nsa.btn.config' /></a></td>
						</tr>						
						<tr>
							<th style="text-align:left;">* <s:message code='com.netmng.text.network.nsa.title.InterDomainURLConfig' /></td>
							<td><input type="text" id="url_inter" name="url_inter" value="<s:message code='com.netmng.text.network.nsa.default.InterDomainURL' />" style="width:400px;"/></td>
							<td><a href="#none" onclick="topologyConfig('inter_url');"><s:message code='com.netmng.text.network.nsa.btn.config' /></a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<br />
			
			<div>[<s:message code='com.netmng.text.network.nsa.title.InterNetworkConfigInfo' />]</div>
			<br />
			
<c:if test="${providerNSAList==null}">	
			<div style="text-align:center;">
				설정정보가 없습니다.
			</div>
</c:if>
<c:if test="${providerNSAList!=null}">			
			<div style="overflow:auto; 
						width:720px; 
						height:300px; 
						padding:0px; 
						border-style:solid;
						border-color:#c3c3c3; 
						border-width:0px;">
				<c:forEach items="${providerNSAList}" varStatus="status" var="providerNSA">
				
				<br />
				<%-- <div style="text-align:right;">
					<a href="#none" onclick="setAlias('${providerNSA.nsa_addr}');">Alias 설정</a>&nbsp;|&nbsp;
					<a href="#none" onclick="viewRowData('${providerNSA.ref_url}');"><s:message code='com.netmng.text.network.nsa.btn.rowDataView' /></a>&nbsp;
				</div> --%>
				
				<table border="0" class="tb_topologyL" style="width:700px; border-style:solid; border-color:#c3c3c3; border-width:2px;">
					<colgroup>
						<col width="160px">
						<col width="*">
					</colgroup>
					<tbody>
						<%-- <tr>
							<td colspan="2" style="text-align:right">
								<a href="#none" onclick="setAlias('${providerNSA.nsa_addr}');">Alias 설정</a>&nbsp;|&nbsp;
								<a href="#none" onclick="viewRowData('${providerNSA.ref_url}');"><s:message code='com.netmng.text.network.nsa.btn.rowDataView' /></a>&nbsp;
							</td>
						</tr> --%>
						<tr>
							<th class="top1" scope="row" style="text-align:left;">&nbsp;NSA name</td>
							<td class="top2" style="word-break:break-all"><c:out value="${providerNSA.nsa_nm}" />(<c:out value="${providerNSA.nsa_addr}" />)</td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">&nbsp;Admin Contact</td>
							<%-- <td style="word-break:break-all"><c:out value="${fn:replace(providerNSA.admin_contact,'\\\r\\\n','<br />')}" /></td> --%>
							<td style="word-break:break-all"><c:out value="${providerNSA.admin_contact}" /></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">&nbsp;EndPoint Address</td>
							<td style="word-break:break-all"><c:out value="${providerNSA.endpoint_addr}" /></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">&nbsp;Network Id</td>
							<td style="word-break:break-all">
								<c:set var="selNetworkId" value="" />
								<c:forEach items="${stpNetworkList}" varStatus="status" var="stpNetwork">
									<c:if test="${providerNSA.nsa_addr==stpNetwork.nsa_addr}">
										<c:out value="${stpNetwork.network_nm}" />(<c:out value="${stpNetwork.network_id}" />)
										<c:set var="selNetworkId" value="${stpNetwork.network_id}" />
									</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">&nbsp;Local Id</td>
							<td style="word-break:break-all">
								<c:forEach items="${stpLocalList}" varStatus="status" var="stpLocal">
									<c:if test="${selNetworkId==stpLocal.network_id}">
										<c:out value="${stpLocal.local_nm}" />(<c:out value="${stpLocal.local_id}" />)<br />
									</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td colspan="2" style="text-align:right">
								<a href="#none" onclick="setAlias('${providerNSA.nsa_addr}');" style="font-weight:bold; color:#3f81b0;">Alias 설정</a>&nbsp;|&nbsp;
								<a href="#none" onclick="viewRowData('${providerNSA.ref_url}');" style="font-weight:bold; color:#3f81b0;"><s:message code='com.netmng.text.network.nsa.btn.rowDataView' /></a>&nbsp;
							</td>
						</tr>											
					</tbody>
				</table>
				<br />
				</c:forEach>
			</div>
</c:if>			
	</form>
			
			<br /><br /><br /><br />
			
			<!-- Intra Domain Topology 설정 -->
			
	<form name="intraFrm" id="intraFrm" action="${pageContext.request.contextPath}/adm/topologyConfig.do" method="post" enctype="multipart/form-data">
		<input type="hidden" id="proc_type" name="proc_type" value="">

			<h3>▣ <s:message code='com.netmng.text.network.nsa.title.IntraDomainConfig' /></h3>
			
			<br />
			<div>
				<table border="0" class="tb_topologyL" style="width:700px;">
					<colgroup>
						<col width="200px">
						<col width="*">
						<col width="70px">
					</colgroup>
					<tbody>
						<tr>
							<th class="top1" scope="row" style="text-align:left;">* <s:message code='com.netmng.text.network.nsa.title.IntraDomainOWLConfig' /></td>
							<td class="top2"><input type="file" id="file" name="file" /></td>
							<td class="top2"><a href="#none" onclick="topologyConfig('intra_file');"><s:message code='com.netmng.text.network.nsa.btn.config' /></a></td>
						</tr>						
						<tr>
							<th style="text-align:left;">* <s:message code='com.netmng.text.network.nsa.title.IntraDomainURLConfig' /></td>
							<td><input type="text" id="url_intra" name="url_intra" value="<s:message code='com.netmng.text.network.nsa.default.IntraDomainURL' />" style="width:400px;"/></td>
							<td><a href="#none" onclick="topologyConfig('intra_url');"><s:message code='com.netmng.text.network.nsa.btn.config' /></a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<br />
			<div>[<s:message code='com.netmng.text.network.nsa.title.IntraNetworkConfigInfo' />]</div>
			<br />
<c:if test="${intra_fileUrl==null}">	
			<div style="text-align:center;">
				설정정보가 없습니다.
			</div>
</c:if>
<c:if test="${intra_fileUrl!=null}">
			<div style="text-align:right;">
				<a href="#none" onclick="viewRowData('${intra_fileUrl}');" style="font-family:Tahoma; font-weight:bold; color:#3f81b0;">
					<s:message code='com.netmng.text.network.nsa.btn.rowDataView' />
				</a>&nbsp;
			</div>
			<div style="overflow:auto; width:720px; height:300px; padding:0px;">
				<br />
				<div>=============== NetworkEquipment =============== </div>
<%
	List<NESpec> listNESpec = (List<NESpec>)request.getAttribute("intra_listNESpec");
	if(listNESpec!=null){
		for(int i=0; i<listNESpec.size(); i++){
			NESpec nESpec = listNESpec.get(i);
%>				
				<table border="0" class="tb_topologyL" style="width:700px; border-style:solid; border-color:#c3c3c3; border-width:2px;">
					<colgroup>
						<col width="160px">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th class="top1" scope="row" style="text-align:left;">&nbsp;Associated STP</td>
							<td class="top2" style="word-break:break-all"><%=nESpec.getNEName()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">&nbsp;Equipment</td>
							<td style="word-break:break-all"><%=nESpec.getDeviceName()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">&nbsp;Vendor</td>
							<td style="word-break:break-all"><%=nESpec.getDeviceVendor()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">&nbsp;Connection Information</td>
							<td style="word-break:break-all">
								<%=nESpec.getProtocol()%> / 
								<%=nESpec.getPort()%> / 
								<%=nESpec.getIP()%> / 
								<%=nESpec.getID()%> /
								<%=nESpec.getPW()%>
							</td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">&nbsp;Property</td>
							<td style="word-break:break-all">
<%
			List<DuplicatedMapInt> listDuplicatedMapInt = nESpec.getConnectedTo();
			for(int j=0; j<listDuplicatedMapInt.size(); j++){
				DuplicatedMapInt dmi = listDuplicatedMapInt.get(j);								
				out.print(NESpec.ObjectProperties.connectedTo.name()+" : "+dmi.key+" / "+NESpec.AnnotationProperties.Weight.name()+" : "+dmi.value+"<br />");
			}
%>								
							</td>
						</tr>										
					</tbody>
				</table>
<%		
		}
	}
%>								
				<br />
				<div>=============== SDP or STP =============== </div>
<%
	List<SPSpec> listSPSpec = (List<SPSpec>)request.getAttribute("intra_listSPSpec");
	if(listSPSpec!=null){
		for(int i=0; i<listSPSpec.size(); i++){
			SPSpec sPSpec = listSPSpec.get(i);
%>				
				<table border="0" class="tb_topologyL" style="width:700px;">
					<colgroup>
						<col width="160px">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th class="top1" scope="row" style="text-align:left;">Equipment Name</td>
							<td class="top2" style="word-break:break-all"><%=sPSpec.getNEName()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">Associated Id</td>
							<td style="word-break:break-all"><%=sPSpec.getNSAName()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">STPLocalID</td>
							<td style="word-break:break-all"><%=sPSpec.getSTPLocalID()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">Type</td>
							<td style="word-break:break-all"><%=sPSpec.getType().name()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">Description</td>
							<td style="word-break:break-all"><%=sPSpec.getDescription()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">Property</td>
							<td style="word-break:break-all">
<%
			List<String> listConnTo = sPSpec.getConnectedTo();
			for(int j=0; j<listConnTo.size(); j++){
				out.print(SPSpec.ObjectProperties.connectedTo.name()+" : "+listConnTo.get(j)+"<br />");
			}
			List<DuplicatedMap> listConnToExt = sPSpec.getConnectedTo_External();
			for(int j=0; j<listConnToExt.size(); j++){
				DuplicatedMap duplicatedMap = listConnToExt.get(j);
				out.print(SPSpec.ObjectProperties.connectedTo.name()+" : ["+duplicatedMap.key+"] -> ["+duplicatedMap.value+"]"+"<br />");
			}
%>								
							</td>
						</tr>									
					</tbody>
				</table>
<%
		}
	}
%>								
				
				<br />
				<div>=============== Path or FixedPath =============== </div>
<%
	List<LinkSpec> listLinkSpec = (List<LinkSpec>)request.getAttribute("intra_listLinkSpec");
	if(listLinkSpec!=null){
		for(int i=0; i<listLinkSpec.size(); i++){
			LinkSpec linkSpec = listLinkSpec.get(i);
%>					
				<table border="0" class="tb_topologyL" style="width:700px;">
					<colgroup>
						<col width="160px">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th class="top1" scope="row" style="text-align:left;">Link Name</td>
							<td class="top2" style="word-break:break-all"><%=linkSpec.getLinkName()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">Type</td>
							<td style="word-break:break-all"><%=linkSpec.getType().name()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">AtoBbandwidth</td>
							<td style="word-break:break-all"><%=linkSpec.getAtoBBW()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">BtoAbandwidth</td>
							<td style="word-break:break-all"><%=linkSpec.getBtoABW()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">directly</td>
							<td style="word-break:break-all"><%=linkSpec.getBidirectional()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">Latency</td>
							<td style="word-break:break-all"><%=linkSpec.getLatency()%></td>
						</tr>
						<tr>
							<th scope="row" style="text-align:left;">Property</td>
							<td style="word-break:break-all">
<%
			List<DuplicatedMapInt> listDuplicatedMapInt = linkSpec.getHasNetworkEquipment();
			for(int j=0; j<listDuplicatedMapInt.size(); j++){
				DuplicatedMapInt dmi = listDuplicatedMapInt.get(j);
				out.print(LinkSpec.ObjectProperties.hasNetworkEquipment.name()+" : "+dmi.key+" / "+LinkSpec.AnnotationProperties.Order.name()+" : "+dmi.value+"<br />");
			}
%>								
							</td>
						</tr>									
					</tbody>
				</table>
<%
		}
	} 
%>								

			</div>
</c:if>
			
		</div>
	</form>
		
		<%-- <br /><br />
		
		<div id="btn_area_topologyL">
			<ul>
				<li class="left"><a onClick="confirmNsaInit();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/btn_nsa.gif" alt="NSA 초기화"></a></li>
				<li class="right"><a href="${pageContext.request.contextPath}/adm/topologyUF.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/btn_topology.gif" alt="토폴로지 관리"></a></li>
			</ul>
		</div> --%>
	
	<%-- </f:form> --%>
	<f:form name="topologyFrm" id="topologyFrm" action="${pageContext.request.contextPath}/adm/proc/nsaInitP.do" method="GET" />
	<iframe id="_proc" name="_proc" frameborder="0" scrolling="no" width="0" height="0" ></iframe>
	<div id="nsaInitDv" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.network.nsa.init' /></b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="nsaInit();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
					</div>
					<div class="btn_no">
						<a onClick="disableConfirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_no.gif" alt="아니오"></a>
					</div>
				</div>
			</form>
		</div>
	</div>

</div>
<!-- //content -->