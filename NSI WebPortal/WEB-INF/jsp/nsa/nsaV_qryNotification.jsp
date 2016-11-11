<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.w3c.dom.*" %> 
<%@ page import="com.netmng.websvc.soap.param.types.QueryNotificationConfirmedType" %>
<%@ page import="com.netmng.websvc.soap.param.types.NotificationBaseType" %>
<%@ page import="com.netmng.websvc.soap.param.types.ReservationRequestCriteriaType" %>
<%@ page import="com.netmng.websvc.soap.svc.services.point2point.EthernetVlanType" %>
<%@ page import="com.netmng.websvc.soap.svc.services.types.StpListType" %>
<%@ page import="com.netmng.websvc.soap.svc.services.types.OrderedStpType" %>
<%@ page import="com.netmng.util.StrUtil" %>
<%@ page import="javax.xml.bind.JAXBElement" %>
<%@ page import="javax.xml.bind.JAXBContext" %>
<%@ page import="javax.xml.bind.Unmarshaller" %>
<%@ page import="javax.xml.bind.Binder" %>
<%@ page import="javax.xml.datatype.XMLGregorianCalendar" %>

<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>  
<script type="text/javascript">
	$(document).ready(function() {
	});
	
	function chgProcType(){
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/nsaV.do").submit();
	}
	function goList() {
		$("#listFrm").attr("action", "${pageContext.request.contextPath}/nsa/nsaL.do").submit();
	}
</script>
<!-- 
<a name="top" />
 -->
<!-- left_column -->
<div id="left_column">
	<!-- left_area  -->
	<div id="left_area">
		<ul class="left_menu">
			<li class="left_tit">
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_title_2.gif" alt="">
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/nsa/reserveIF.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image23','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_1_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_1_off.gif" name="Image23" width="167" height="22" border="0">
				</a> 
			</li>		
			<li>
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_2_on.gif" name="left_menu_2_2" width="167" height="22" border="0">
			</li>	
			<li>
				<a href="${pageContext.request.contextPath}/nsa/reserveL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('left_menu_2_3','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_3_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_3_off.gif" name="left_menu_2_3" width="167" border="0">
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/nsa/reserveLogL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('left_menu_2_4','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_4_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_4_off.gif" name="left_menu_2_4" width="167" border="0">
				</a> 
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/nsa/reserveFavL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('left_menu_2_5','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_5_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_5_off.gif" name="left_menu_2_5" width="167" border="0">
				</a> 
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
		<span class="now"><s:message code='com.netmng.menu.network2' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/title_2_2.gif" alt=""></h2>
	
	<f:form name="reservationDTO" id="reservationDTO" modelAttribute="reservationDTO" method="POST">
	
	<!-- // reserve_2 -->
	<div id="reserve_2">
	<s:bind path="reservationDTO.seq">
	<f:hidden path="${status.expression}" /> 
	</s:bind>

<%	String sProcType = request.getParameter("proc_type"); %>	

	<div style="font-weight:bold; 
					color:#3f81b0; 
					width:760px;
					padding:10px;
					margin:0px;
					border-style:solid;
					border-color:#c3c3c3; 
					border-width:2px;">
		[<s:message code='com.netmng.text.network.request_type' />]  
		<select id="proc_type" name="proc_type" onchange="chgProcType();">
			<option value="querySummary"			<%if(sProcType.equals("querySummary")) out.print("selected");%>				>querySummary</option>
			<option value="querySummarySync"		<%if(sProcType.equals("querySummarySync")) out.print("selected");%>			>querySummarySync</option>
			<option value="queryRecursive"			<%if(sProcType.equals("queryRecursive")) out.print("selected");%>			>queryRecursive</option>
			<option value="queryNotification"		<%if(sProcType.equals("queryNotification")) out.print("selected");%>		>queryNotification</option>
			<option value="queryNotificationSync"	<%if(sProcType.equals("queryNotificationSync")) out.print("selected");%>	>queryNotificationSync</option>
		</select>
	</div>
<%
	QueryNotificationConfirmedType queryResult = (QueryNotificationConfirmedType)request.getAttribute("queryResult");
	if(queryResult!=null){
		List<NotificationBaseType> list = queryResult.getErrorEventOrReserveTimeoutOrDataPlaneStateChange();
%>		
		<table cellspacing="0" class="tb_l" style="width:760px;">
			<colgroup>
				<col width="400px">
				<col width="100px">
				<col width="*">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">ConnectionId</th>
					<th scope="col">NotificationId</th>
					<th scope="col">Time</th>
				</tr>
			</thead>
			<tbody>					
<%
		for(int i=0; i<list.size(); i++){
			NotificationBaseType notificationBaseType = list.get(i);		
%>									
			<tr>
				<td align="left" valign="middle" style="text-align:left;">				
					<%=notificationBaseType.getConnectionId()%>
	 			</td>
				<td align="left" valign="middle">
					<%=notificationBaseType.getNotificationId()%>
				</td>
				<td align="left" valign="middle">
					<%=StrUtil.transformString(notificationBaseType.getTimeStamp().toGregorianCalendar().getTime(), "yyyy.MM.dd hh:mm")%>
				</td>
			</tr>
<% 		}%>
		</table>
<%	}else{%>							
		<br /><br />
		<div style="width:760px; text-align:center;"><s:message code='com.netmng.text.no_data' /></div>
<%	
	}
%>			
		<div class="btn_area_request">
			<div class="btn_3" style="width:760px; text-align:right;"> 
				<a onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_list.gif" alt="목록"></a>
			</div>
		</div>
		
	</div>
	<!-- // reserve_2 -->
	</f:form>

	<iframe id="_proc" name="_proc" frameborder="0" scrolling="no" width="0" height="0" ></iframe>
	<f:form name="listFrm" id="listFrm" action="${pageContext.request.contextPath}/nsa/reserveL.do" method="GET" modelAttribute="srchParam">
		<input type="hidden" name="seq" id="seq" />
		<%@ include file="/WEB-INF/jsp/com/pageParamH.jsp"%>
	</f:form>
	
</div>
<!--//content-->