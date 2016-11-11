<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="com.netmng.util.PropUtil" %>
    
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 	
<%@	taglib prefix="page" 	uri="/WEB-INF/tld/PagingTag.tld"%>

<style type="text/css">
#nsaListTb tbody tr:hover
{
	background-color: LemonChiffon;
}
</style>  
<script type="text/javascript">
	$(document).ready(function() {
		//$('#tableone').columnHover(); 
	});
	function goPage(pageIdx){
		$("#srchParam").find("#pageIdx").val(pageIdx);
		$("#srchParam").submit();
	}
	function goSort(sortKey) {
		if($("#srchParam").find("#sortKey").val() == sortKey) {
			if($("#srchParam").find("#sortType").val() == "DESC") {
				if($("#srchParam").find("#sortKey").val() == "SEQ") {
					
				} else {
					$("#srchParam").find("#sortType").val("ASC");
				}
			} else {
				$("#srchParam").find("#sortType").val("DESC");
			}
		} else {
			$("#srchParam").find("#sortKey").val(sortKey);
			$("#srchParam").find("#sortType").val("DESC");
		}
		$("#srchParam").find("#pageIdx").val("1");
		goPage("1");
	}
	function cellEvent(sourceSeq, destSeq ,CI ,BW) {
		/* alert("sourceSeq="+sourceSeq);
		alert("destSeq="+destSeq);
		alert("CI="+CI);
		alert("BW="+BW); */
		/* thisMovie("EI_flash").moveMC(sourceSeq, destSeq ,CI ,BW); */
	}
	function view(seq) {
		$("#srchParam").find("#seq").val(seq);
		$("#srchParam").attr("action", "${pageContext.request.contextPath}/nsa/nsaV.do").submit();
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
	
	<!-- // request_1 -->
	<%-- <div id="request_1">
		<dl class="img_area">
			<dd>
				<!-- 
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/img_2.jpg" width="323" height="354">
				-->
				<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=11,1,102,63" width="323px" height="450px" id="EI_flash">
					<param name="movie" value="${pageContext.request.contextPath}/swf/userbox_check.swf?loadURL=${pageContext.request.contextPath}/rest/api/topologyList" />
					<param name="quality" value="high" />
					<param name='wmode' value='transparent' />
					<param name="allowScriptAccess" value="always" />
					<embed src="${pageContext.request.contextPath}/swf/userbox_check.swf?loadURL=${pageContext.request.contextPath}/rest/api/topologyList" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="323px" height="450px" allowScriptAccess="always" showLiveConnect="true" name="EI_flash"></embed>
				</object>
			</dd>
		</dl>
	</div> --%>
	<!-- request_1 //-->
	<!-- // request_2 -->
	<div id="reserve_2">
		<f:form name="srchParam" id="srchParam" action="${pageContext.request.contextPath}/nsa/nsaL.do" modelAttribute="srchParam" method="GET">
			<input type="hidden" id="seq"		name="seq" />
			<input type="hidden" id="proc_type"	name="proc_type"	value="querySummary" />
		<div>
			<c:if test="${srchParam.pageIdx != null}">
			<input type="hidden" name="pageIdx" id="pageIdx" value='<c:out value="${srchParam.pageIdx}" />' />
			</c:if>
			<input type="hidden" name="sortKey" id="sortKey" value='<c:out value="${srchParam.sortKey}" />' />
			<input type="hidden" name="sortType" id="sortType" value='<c:out value="${srchParam.sortType}" />' />
		</div>	
		<!-- <table id="nsaListTb" cellspacing="0" class="tb_nsaL"> -->
		<table id="nsaListTb" cellspacing="0" class="tb_l">
		<colgroup>
			<col width="5%">
			<col width="*">
			<col width="6%">
			<col width="12%">
			<col width="12%">
			<col width="15%">
			<col width="15%">
			<col width="11%">
		</colgroup>
		<thead>
		<tr>
			<th scope="col"><s:message code='com.netmng.text.network.number' /></th>
			<th scope="col">Connection ID</th>
			<th scope="col"><s:message code='com.netmng.text.network.bandwidth' /></th>
			<th scope="col"><s:message code='com.netmng.text.network.source' /></th>
			<th scope="col"><s:message code='com.netmng.text.network.destination' /></th>
			<th scope="col"><s:message code='com.netmng.text.network.startTime2' /></th>
			<th scope="col"><s:message code='com.netmng.text.network.endTime2' /></th>
			<th scope="col"><s:message code='com.netmng.text.network.status' /></th>
		</tr>
		</thead>
	
		<c:forEach items="${nsaList}" varStatus="status" var="reservation">
		<%-- <tr onmouseover="cellEvent('${reservation.source_stp_seq}','${reservation.dest_stp_seq}','${reservation.connection_id}','${reservation.desired}');"> --%>
		<tr	onclick="view(<c:out value="${reservation.seq}" />);" 
			style="cursor:pointer"	>
			<td>
				<c:out value="${srchParam.totRow-(status.index+srchParam.sIdx)}" />
				<!-- <c:out value="${reservation.source_stp.urn}" /><c:out value="${reservation.dest_stp.urn}" /><c:out value="${reservation.desired}" /> -->
			</td>
			<td class="tb_nsaL_head"><c:out value="${reservation.connection_id}" /></td>
			<td><c:out value="${reservation.desired}" /></td>
			<td style="word-break:break-all"><c:out value="${reservation.sourceStpNetwork.network_nm}" /></td>
			<td style="word-break:break-all"><c:out value="${reservation.destStpNetwork.network_nm}" /></td>
			<td><s:eval expression='new java.text.SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(reservation.start_time)' /></td>
			<td><s:eval expression='new java.text.SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(reservation.end_time)' /></td>
			<td>
				<c:set var="lang" value="<%=response.getLocale().getLanguage()%>" />
				<s:eval expression='new com.netmng.util.PropUtil(lang).getProp("com.netmng.text.network.state_nm."+reservation.state_nm)' />				
			</td>
		</tr>
		</c:forEach>
		
		</table>
		</f:form>
	</div>
	<!-- // request_2 -->
</div>
<c:if test="${reservationMsg != null}">
<script type="text/javascript">
	alert("<s:message code='${reservationMsg}' />");
</script>
</c:if>
<!--//content-->
