<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.*" %>
<%@ page import="org.w3c.dom.*" %> 
<%@ page import="com.netmng.websvc.soap.param.types.QuerySummaryResultType" %>
<%@ page import="com.netmng.websvc.soap.param.types.QuerySummaryResultCriteriaType" %>
<%@ page import="com.netmng.websvc.soap.param.types.ReservationRequestCriteriaType" %>
<%@ page import="com.netmng.websvc.soap.svc.services.point2point.EthernetVlanType" %>
<%@ page import="javax.xml.bind.JAXBElement" %>
<%@ page import="javax.xml.bind.JAXBContext" %>
<%@ page import="javax.xml.bind.Unmarshaller" %>
<%@ page import="javax.xml.bind.Binder" %>

<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript">
	$(document).ready(function() {
		/* $("#reservationDTO").find("#start_date").mask("9999.99.99");
		$("#reservationDTO").find("#end_date").mask("9999.99.99");
		$("#reservationDTO").find("#start_date").datepick({showOnFocus: false, showTrigger: '#startBt', dateFormat: 'yyyy.mm.dd'});
		$("#reservationDTO").find("#end_date").datepick({showOnFocus: false, showTrigger: '#endBt', dateFormat: 'yyyy.mm.dd'});
		
		chgProviderNsa();
		chgNetworkId("source_network_id", "source_local_id", "sourceVLAN");
		chgNetworkId("dest_network_id", "dest_local_id", "destVLAN"); */
	});
	function provision() {
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/provisionP.do").attr("target", "_proc").submit();
	}
	function release() {
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/releaseP.do").attr("target", "_proc").submit();
	}
	function terminate() {
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/terminateP.do").attr("target", "_proc").submit();
	}
	function reserveAportProc(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/reserveAbortP.do").attr("target", "_proc").submit();
	}
	function reserveCommitProc(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/reserveCommitP.do").attr("target", "_proc").submit();
	}
	function provisionProc(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/provisionP.do").attr("target", "_proc").submit();
	}
	function releaseProc(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/releaseP.do").attr("target", "_proc").submit();
	}
	function terminateProc(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/terminateP.do").attr("target", "_proc").submit();
	}
	function querySummaryProc(){
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/querySummaryP.do").attr("target", "_proc").submit();
	}
	function goUpdate(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/reserveU.do").attr("target", "_self").submit();
	}
	function goList() {
		$("#listFrm").attr("action", "${pageContext.request.contextPath}/nsa/reserveL.do").submit();
	}
	function goListSeq(seq) {
		$("#listFrm").find("#seq").val(seq);
		$("#listFrm").attr("action", "${pageContext.request.contextPath}/nsa/reserveL.do").submit();
	}
	function confirmProvision() {
		loadConfirm($("#provisionDv"));
	}
	function confirmRelease() {
		loadConfirm($("#releaseDv"));
	}
	function confirmTerminate() {
		loadConfirm($("#terminateDv"));
	}
	function reserveMod() {
		loadConfirm($("#reserveMod"));
	}
	function reserveAbort() {
		loadConfirm($("#reserveAbort"));
	}
	function reserveCommit() {
		loadConfirm($("#reserveCommit"));
	}
	function provision() {
		loadConfirm($("#provision"));
	}
	function release() {
		loadConfirm($("#release"));
	}
	function terminate() {
		loadConfirm($("#terminate"));
	}
	
	function troubleAsk() {
		$.ajax( 
			{
				type : "GET",
				url : "${pageContext.request.contextPath}/troubleAsk.json?cache=" + Math.round(Math.random()* (new Date().getTime())),
				contentType : "application/json; charset=utf-8",
				dataType: "json",
		        data : ({
		        		url : "http://www.netmng.re.kr/nsa/reserveV.do?seq=" + <c:out value="${reservationDTO.seq}" />
		        }),
		        success : 	function(data) {
		        				if(data == true) {
		        					alert("<s:message code='com.netmng.ctrl.nsa.troubleAsk.success' />");
		        				} else {
		        					alert("<s:message code='com.netmng.ctrl.nsa.troubleAsk.fail' />");
		        				}
		        			},
				error : 	function(data) {
									alert("<s:message code='com.netmng.ctrl.nsa.troubleAsk.fail' />");
							}
			}
		);
	}
	function cellEvent(sourceSeq, destSeq ,CI ,BW) {
		thisMovie("EI_flash").moveMC(sourceSeq, destSeq ,CI ,BW);
	}
	
	
</script>

<!-- left_column -->
<div id="left_column">
	<!-- left_area  -->
	<div id="left_area">
		<ul class="left_menu">
			<li class="left_tit">
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_title_2.gif" alt="">
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/nsa/reserveIF.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image23','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_1_on.gif',1)"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_1_off.gif" name="Image23" width="167" height="22" border="0"></a> 
			</li>	
			<li>
				<a href="${pageContext.request.contextPath}/nsa/nsaL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('left_menu_2_2','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_2_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_2_off.gif" name="left_menu_2_2" width="167" height="22" border="0">
				</a>
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_3_on.gif" width="167"> 
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
		<a href="#" class="loc"><s:message code='com.netmng.menu.network' /></a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<span class="now"><s:message code='com.netmng.menu.network3' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/title_2_3.gif" alt=""></h2>
	<f:form name="reservationDTO" id="reservationDTO" modelAttribute="reservationDTO" method="POST">
	<!-- // request_1 -->
	
	<!-- request_1 //-->

	<!-- // reserve_2 -->
	<div id="reserve_2">
	<s:bind path="reservationDTO.seq">
	<f:hidden path="${status.expression}" /> 
	</s:bind>
	
		<table cellspacing="0" class="tb_request" style="width:697px;">
			<colgroup>
				<col width="100%">
			</colgroup>
		<tbody>	
<%
	List<QuerySummaryResultType> list = (List<QuerySummaryResultType>)request.getAttribute("queryResult");
	for(int i=0; i<list.size(); i++){
		QuerySummaryResultType querySummaryResultType = list.get(i);
%>	
			<tr>
				<td align="left" valign="middle">
					* requesterNSA :		<%=querySummaryResultType.getRequesterNSA()%><br />
					<%-- * notificationId :		<%=querySummaryResultType.getNotificationId()%><br /> --%>	

					* globalReservationId :	<%=querySummaryResultType.getGlobalReservationId()%><br />
					* description :			<%=querySummaryResultType.getDescription()%><br />
					* connectionId :		<%=querySummaryResultType.getConnectionId()%><br />
					* criteria <br />
<%
		List<QuerySummaryResultCriteriaType> listCriteria = querySummaryResultType.getCriteria();
		for(int j=0; j<listCriteria.size(); j++){
			QuerySummaryResultCriteriaType criteria = listCriteria.get(j);
%>					
						&nbsp; - schedule <br />
							&nbsp;&nbsp;&nbsp; @ startTime :	<%=criteria.getSchedule().getStartTime()%><br />
							&nbsp;&nbsp;&nbsp; @ endTime :		<%=criteria.getSchedule().getEndTime()%><br />
						&nbsp; - serviceType :	<%=criteria.getServiceType()%><br />
						
						&nbsp; - any <br />						
<%
			List<Object> listAny = (List<Object>)criteria.getAny();
			for(int k=0; k<criteria.getAny().size(); k++){
				Object content = listAny.get(k);
				//JAXBContext context = JAXBContext.newInstance(EthernetVlanType.class);
				JAXBElement<EthernetVlanType> rawEvts = (JAXBElement<EthernetVlanType>)content;
                //EthernetVlanType evts = rawEvts.getValue();
				
			
				
				//Node node = (Node)content;
				Node node = (Node)rawEvts;
				NodeList nodeList = node.getChildNodes();
				for(int n=0; n<nodeList.getLength(); n++){
					Node node1 = (Node)nodeList.item(n);
					String nodeNm1		= node1.getNodeName();
					String nodeTxtCont1	= node1.getTextContent();
					if(	nodeNm1.equals("capacity")		|| 
						nodeNm1.equals("directionality")||
						nodeNm1.equals("mtu")			||
						nodeNm1.equals("burstsize")		||
						nodeNm1.equals("sourceVLAN")	||
						nodeNm1.equals("destVLAN")			){
						out.print("&nbsp;&nbsp;&nbsp; @ "+nodeNm1+" : "+nodeTxtCont1+"<br />");
					}
					else if(	nodeNm1.equals("sourceSTP")	|| 
								nodeNm1.equals("destSTP")	)
					{
						out.print("&nbsp;&nbsp;&nbsp; @ "+nodeNm1+"<br />");
						NodeList nodeList2 = node1.getChildNodes();
						for(int n2=0; n2<nodeList2.getLength(); n2++){
							Node node2 = (Node)nodeList2.item(n2);
							String nodeNm2		= node2.getNodeName();
							String nodeTxtCont2	= node2.getTextContent();
							if(	nodeNm2.equals("networkId")	|| 
								nodeNm2.equals("localId")		){
									out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ... "+nodeNm2+" : "+nodeTxtCont2+"<br />");
							}
						}
					}
				}
			}
%>
						&nbsp; - version :		<%=criteria.getVersion()%><br />
<%			
		}
%>

				</td>
			</tr>
<%	}%>			
		</tbody>
		</table>
		
		<div class="btn_area_request">
			<div class="btn_4"> 
				<a onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_list.gif" alt="목록"></a>
			</div>
		</div>
		
	</div>
	<!-- // reserve_2 -->
	</f:form>
	
	<f:form name="listFrm" id="listFrm" action="${pageContext.request.contextPath}/nsa/reserveL.do" method="GET" modelAttribute="srchParam">
		<input type="hidden" name="seq" id="seq" />
		<%@ include file="/WEB-INF/jsp/com/pageParamH.jsp"%>
	</f:form>

</div>
<!--//content-->