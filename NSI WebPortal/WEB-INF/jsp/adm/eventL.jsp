<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 	
<%@	taglib prefix="page" 	uri="/WEB-INF/tld/PagingTag.tld"%>

<style type="text/css">
#eventTb tbody tr:hover
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
		<span class="now"><s:message code='com.netmng.menu.admin32' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/title3_3.gif" alt=""></h2>

	<!-- tab_area -->
	<div id="tab_area">
		<div class="tab_Monitor_1">
			<a href="${pageContext.request.contextPath}/adm/topologyL.do">
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_3_1_off.gif" > 
			</a>
		</div>
		<div class="tab_Monitor_2">
			<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_3_2_on.gif" >
		</div>
		<div class="tab_Monitor_3">
			<a href="${pageContext.request.contextPath}/adm/errorL.do">
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_3_3_off.gif">
			</a>
		</div>
	</div>
	<!-- //tab_area -->

	<%-- <div id="obstacle_1">
		<dl class="img_area">
			<dd>
				<!-- 
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/img_2_obstacle.jpg" width="323" height="354">
				-->
				<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=11,1,102,63" width="323px" height="450px" id="EI_flash">
					<param name="movie" value="${pageContext.request.contextPath}/swf/box_basic.swf?loadURL=${pageContext.request.contextPath}/rest/api/topologyList" />
					<param name="quality" value="high" />
					<param name='wmode' value='transparent' />
					<param name="allowScriptAccess" value="always" />
					<embed src="${pageContext.request.contextPath}/swf/box_basic.swf?loadURL=${pageContext.request.contextPath}/rest/api/topologyList" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="323px" height="450px" allowScriptAccess="always" showLiveConnect="true" name="EI_flash"></embed>
				</object>
			</dd>
		</dl>
	</div> --%>
	
	<div id="obstacle_2">
		<f:form name="srchParam" id="srchParam" action="${pageContext.request.contextPath}/adm/eventL.do" modelAttribute="srchParam" method="GET">
			<div>
				<c:if test="${srchParam.pageIdx != null}">
				<input type="hidden" name="pageIdx" id="pageIdx" value='<c:out value="${srchParam.pageIdx}" />' />
				</c:if>
				<input type="hidden" name="sortKey" id="sortKey" value='<c:out value="${srchParam.sortKey}" />' />
				<input type="hidden" name="sortType" id="sortType" value='<c:out value="${srchParam.sortType}" />' />
			</div>	
				<table id="eventTb" cellspacing="0" class="tb_obstacle" style="width:700px;" border="0">
				<colgroup>
					<col width="6%">
					<col width="18%">
					<col width="11%">
					<col width="11%">
					<col width="32%">
					<col width="22%">
				</colgroup>
				<thead>
				<tr>
					<th scope="col" onClick="goSort('SEQ')">No</th>
					<th scope="col">cause</th>
					<th scope="col">router</th>
					<th scope="col">interface</th>
					<th scope="col" onClick="goSort('DESCRIPTION')">description</th>
					<th scope="col" onClick="goSort('CREATETIME')">date</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${eventList}" varStatus="status" var="event">
					<tr> 
						<td><c:out value="${srchParam.totRow-(status.index+srchParam.sIdx)}" /></td>						
						<td class="tb_nsaL_head"><c:out value="${event.cause}" /></td>
						<td style="text-align:left;"><c:out value="${event.router}" /></td>
						<td style="text-align:left;">
							<c:forEach items="${event.eventLogList}" varStatus="eventLogStatus" var="eventLog">
								<c:if test="${eventLogStatus.index != 0}"><br /></c:if>
								<c:out value="${eventLog.fault_interface}" />
							</c:forEach>
						</td>
						<td><c:out value="${event.description}" /></td>
						<td><s:eval expression='new java.text.SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(event.createtime)' /></td>
					</tr>
					</c:forEach>
				</tbody>
				</table>
			<div class="paging_m" style="width:700px;">
				<page:pagingTag totRow="${srchParam.totRow}" row="${srchParam.row}" pageIdx="${srchParam.pageIdx}" func="goPage" cxtPath="" />
			</div>
		</f:form>
	</div>
</div>
<!-- //content -->