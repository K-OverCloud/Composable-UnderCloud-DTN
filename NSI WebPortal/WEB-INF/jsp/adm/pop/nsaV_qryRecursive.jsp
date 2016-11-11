<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.w3c.dom.*" %> 
<%@ page import="com.netmng.websvc.soap.param.types.QueryRecursiveResultType" %>
<%@ page import="com.netmng.websvc.soap.param.types.QueryRecursiveResultCriteriaType" %>
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

<!-- //left_column -->


<!-- content -->
<div id="content">
	<div id="location">
		<a href="#" class="loc">Home</a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<a href="#" class="loc"><s:message code='com.netmng.menu.admin' /></a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<a href="#" class="loc"><s:message code='com.netmng.menu.admin2' /></a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<span class="now"><s:message code='com.netmng.menu.admin22' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/title_3_2.gif" alt=""></h2>
	
	<f:form name="reservationDTO" id="reservationDTO" modelAttribute="reservationDTO" method="POST">
	
	<!-- // reserve_2 -->
	<div id="reserve_2">
	<s:bind path="reservationDTO.seq">
	<f:hidden path="${status.expression}" /> 
	</s:bind>

<%
	List<QueryRecursiveResultType> list = (List<QueryRecursiveResultType>)request.getAttribute("queryResult");
	if(list!=null && list.size()>0){
		
		for(int i=0; i<list.size(); i++){
			QueryRecursiveResultType queryResultType = list.get(i);
			
			List<QueryRecursiveResultCriteriaType> listCriteria = queryResultType.getCriteria();
			for(int j=0; j<listCriteria.size(); j++){
				QueryRecursiveResultCriteriaType criteria = listCriteria.get(j);
				
				List<Object> listAny = (List<Object>)criteria.getAny();
				for(int k=0; k<criteria.getAny().size(); k++){
					Object content = listAny.get(k);
					JAXBElement<EthernetVlanType> rawEvts = (JAXBElement<EthernetVlanType>)content;
	                EthernetVlanType evts = rawEvts.getValue();
%>		
		<table cellspacing="0" class="tb_request" style="width:760px;">
		<colgroup>
			<col class="col">
			<col width="280px">
			<col class="col">
			<col width="280px">
		</colgroup>
		<tbody>
			<tr>
	 			<th align="left" valign="middle" scope="row"><label for="${status.expression}"><s:message code='com.netmng.text.network.version' /></label></th>
				<td align="left" valign="middle" colspan="3"><%=criteria.getVersion()%></td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="${status.expression}"><s:message code='com.netmng.text.network.reqNSA' /></label></th>
				<td align="left" valign="middle" colspan="3"><%=queryResultType.getRequesterNSA()%></td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="${status.expression}"><s:message code='com.netmng.text.network.prvNSA' /></label></th>
				<td align="left" valign="middle" colspan="3"><c:out value="${reservationDTO.provider_nsa}" />
					<%-- <c:out value="${reservationDTO.providerNsa.nsa_nm}" />(<c:out value="${reservationDTO.provider_nsa}" />) --%>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="host_ip_str"><s:message code='com.netmng.text.network.sourceSTP' /></label></th>
				<td align="left" valign="middle" colspan="3"><%=evts.getSourceSTP().getNetworkId()%> (<%=evts.getSourceSTP().getLocalId()%>)
					<%-- <c:out value="${reservationDTO.sourceStpNetwork.network_nm}" />(<c:out value="${reservationDTO.sourceStpLocal.local_nm}" />) --%>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="dest_ip_str"><s:message code='com.netmng.text.network.destSTP' /></label></th>
				<td align="left" valign="middle" colspan="3"><%=evts.getDestSTP().getNetworkId()%> (<%=evts.getDestSTP().getLocalId()%>)
					<%-- <c:out value="${reservationDTO.destStpNetwork.network_nm}" />(<c:out value="${reservationDTO.destStpLocal.local_nm}" />) --%>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="host_ip_str"><s:message code='com.netmng.text.network.hostIP' /></label></th>
				<td align="left" valign="middle"><%=criteria.getOtherAttributes().get("dynamicKL_srcHostIP")%>
					<%-- <c:out value="${reservationDTO.host_ip}" /> --%>
				</td>
				<th align="left" valign="middle" scope="row"><label for="dest_ip_str"><s:message code='com.netmng.text.network.destinationIP' /></label></th>
				<td align="left" valign="middle"><%=criteria.getOtherAttributes().get("dynamicKL_destHostIP")%>
					<%-- <c:out value="${reservationDTO.dest_ip}" /> --%>
				</td>
			</tr>
			<tr>		
				<th align="left" valign="middle" scope="row"><label for="start_date"><s:message code='com.netmng.text.network.startTime' /></label></th>
				<%-- <td align="left" valign="middle"><%=criteria.getSchedule().getStartTime()%></td> --%>
				<td align="left" valign="middle"><%=StrUtil.transformString(criteria.getSchedule().getStartTime().toGregorianCalendar().getTime(), "yyyy.MM.dd hh:mm")%></td>
				<th align="left" valign="middle" scope="row"><label for="end_date"><s:message code='com.netmng.text.network.endTime' /></label></th>
				<%-- <td align="left" valign="middle"><%=criteria.getSchedule().getEndTime()%></td> --%>
				<td align="left" valign="middle"><%=StrUtil.transformString(criteria.getSchedule().getEndTime().toGregorianCalendar().getTime(), "yyyy.MM.dd hh:mm")%></td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.network.bandwidth' /></th>
				<td align="left" valign="middle" class="profile_img2" colspan="3"><%=evts.getCapacity()%>
					<%-- <c:out value="${reservationDTO.desired}" /> --%>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="start_date"><s:message code='com.netmng.text.network.description' /></label></th>
				<td align="left" valign="middle" colspan="3"><%=queryResultType.getDescription()%>
					<%-- <c:out value="${reservationDTO.description}" /> --%>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="start_date"><s:message code='com.netmng.text.network.symmetricPath' /></label></th>
				<td align="left" valign="middle"><%=evts.isSymmetricPath()%>
					<%-- <c:if test="${reservationDTO.symmetricPath == '1'}">true</c:if>
					<c:if test="${reservationDTO.symmetricPath == '0'}">false</c:if> --%>
				</td>
				<th align="left" valign="middle" scope="row"><label for="end_date"><s:message code='com.netmng.text.network.directionality' /></label></th>
				<td align="left" valign="middle"><%=evts.getDirectionality().value()%>
					<%-- <c:out value="${reservationDTO.directionality}" /> --%>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="start_date"><s:message code='com.netmng.text.network.mtu' /></label></th>
				<td align="left" valign="middle"><%=evts.getMtu()%>
					<%-- <c:out value="${reservationDTO.mtu}" /> --%>
				</td>
				<th align="left" valign="middle" scope="row"><label for="end_date"><s:message code='com.netmng.text.network.burstsize' /></label></th>
				<td align="left" valign="middle"><%=evts.getBurstsize()%>
					<%-- <c:out value="${reservationDTO.burstsize}" /> --%>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row">
					<label for="start_date">
						<s:message code='com.netmng.text.network.ero' />
					</label>
				</th>
				<td align="left" valign="middle" colspan="3">
					<table cellspacing="0" class="tb_l" style="width:650px;">
						<colgroup>
							<col width="600px">
							<col width="*">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">NetwrokId > LocalId</th>
								<th scope="col">Order</th>
							</tr>
						</thead>
						<tbody id="TBODY_ERO">
							<%-- <c:forEach items="${list_ero}" varStatus="status" var="ero"> --%>
<%
				StpListType ero = evts.getEro();
				if(ero!=null){
					List<OrderedStpType> listOrderedStpType = ero.getOrderedSTP();
					if(listOrderedStpType!=null && listOrderedStpType.size()>0){
						for(int e=0; e<listOrderedStpType.size(); e++){
							OrderedStpType orderedStpType = listOrderedStpType.get(e);
%>							
							<tr>
								<td align="left" valign="middle" style="text-align:left;">				
									<%-- <c:out value="${ero.stpNetwork.network_nm}" /> &gt; <c:out value="${ero.stpLocal.local_nm}" /> --%> 
									<%=orderedStpType.getStp().getNetworkId()%> &gt; <%=orderedStpType.getStp().getLocalId()%>
					 			</td>
								<td align="left" valign="middle">
									<%-- <c:out value="${ero.ero_order}" /> --%>
									<%=orderedStpType.getOrder()%>
								</td>
							</tr>
<% 				
						}
					}
				}
%>							
							<%-- </c:forEach> --%>
						</tbody>
					</table>
				</td>
			</tr>
			
			<tr>
				<th align="left" valign="middle" scope="row"><label for="start_date"><s:message code='com.netmng.text.network.sourceVLAN' /></label></th>
				<td align="left" valign="middle"><%=evts.getSourceVLAN()%>
					<%-- <c:out value="${reservationDTO.sourceVLAN}" /> --%>
				</td>
				<th align="left" valign="middle" scope="row"><label for="end_date"><s:message code='com.netmng.text.network.destVLAN' /></label></th>
				<td align="left" valign="middle"><%=evts.getDestVLAN()%>
					<%-- <c:out value="${reservationDTO.destVLAN}" /> --%>
				</td>
			</tr>
			 
			<tr>
				<th align="left" valign="middle" scope="row"><label for="host_ip_str">reservationState</label></th>
				<td align="left" valign="middle"><%=queryResultType.getConnectionStates().getReservationState().value()%></td>
				<th align="left" valign="middle" scope="row"><label for="dest_ip_str">provisionState</label></th>
				<td align="left" valign="middle"><%=queryResultType.getConnectionStates().getProvisionState().value()%></td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="host_ip_str">lifecycleState</label></th>
				<td align="left" valign="middle"><%=queryResultType.getConnectionStates().getLifecycleState().value()%></td>
				<th align="left" valign="middle" scope="row"><label for="dest_ip_str">dataPlaneStatus</label></th>
				<td align="left" valign="middle"><%if(queryResultType.getConnectionStates().getDataPlaneStatus().isActive()) out.print("Active"); else out.print("Inactive");%></td>
			</tr>
		</tbody>
		</table>
<%
				}
			}
		}
	}else{
%>
		<br /><br />
		<div style="width:760px; text-align:center;">조회내용이 없습니다.</div>
<%	
	}
%>			
	
		<div class="btn_area_request">
			<div class="btn_3" style="width:760px; text-align:right;"> 
				<a onClick="window.close();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_close_2.gif" alt="닫기"></a>
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