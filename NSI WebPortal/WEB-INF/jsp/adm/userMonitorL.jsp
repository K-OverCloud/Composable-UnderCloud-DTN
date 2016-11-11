<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 	
<%@	taglib prefix="page" 	uri="/WEB-INF/tld/PagingTag.tld"%>

<style type="text/css">
#userMonitorTb tbody tr:hover
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
	function view(seq) {
		$("#srchParam").find("#user_seq").val(seq);
		$("#srchParam").attr("action", "${pageContext.request.contextPath}/adm/userMonitorV.do").submit();
	}
	function goSort(sortKey) {
		if($("#srchParam").find("#sortKey").val() == sortKey) {
			if($("#srchParam").find("#sortType").val() == "DESC") {
				if($("#srchParam").find("#sortKey").val() == "D.USER_SEQ") {
					
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
	function chgTab(url){
		document.location.href = url;
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
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_2_on.gif" width="167" height="22" border="0">
			</li>	
			<li>
				<a href="${pageContext.request.contextPath}/adm/topologyL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image23','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_3_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_3_off.gif" name="Image23" width="167" height="22" border="0">
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
		<a href="#" class="loc"><s:message code='com.netmng.menu.admin' /></a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<a href="#" class="loc"><s:message code='com.netmng.menu.admin2' /></a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<span class="now"><s:message code='com.netmng.menu.admin21' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/title_3_2.gif" alt=""></h2>
	<f:form name="srchParam" id="srchParam" action="${pageContext.request.contextPath}/adm/userMonitorL.do" modelAttribute="srchParam" method="GET">
	
	<!-- tab_area -->
	<table border="0" style="	width:700px; 
								height:35px; 
								font-family:돋움; 
								font-size:13px; 
								text-align:center; 
								font-weight:bold; 
								color:#7c7c7c; 
								background-color:#D8D4D4;">
		<colgroup>
			<col width="5%">
			<col width="40%">
			<col width="5%">
			<col width="5%">
			<col width="40%">
			<col width="5%">
		</colgroup>
		<tr><td style="height:5px;" colspan="6"></td></tr>
		<tr>
			<td>&nbsp;</td>
			<td style="background-color:white;">
				<s:message code='com.netmng.menu.admin21' />
			</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td style="cursor:pointer;" onclick="chgTab('${pageContext.request.contextPath}/adm/interMonitorL.do');">
				<s:message code='com.netmng.menu.admin22' />
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<br /><br />	
	<%-- <div id="tab_area">
		<div class="tab_Monitor_1">
			<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_2_1_on.gif" > 
		</div>
		<div class="tab_Monitor_2">
			<a href="${pageContext.request.contextPath}/adm/interMonitorL.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_2_2_off.gif" ></a>
		</div>
		<div class="tab_Monitor_3">
			<a href="${pageContext.request.contextPath}/adm/globalMonitorL.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_2_3_off.gif" ></a>
			<a href="#none;" onclick="alert('This function is not service');"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_2_3_off.gif" ></a>
		</div>
	</div> --%>
	<!-- //tab_area -->
	
	
	<!-- MonitorL -->
	<div id="MonitorL">
		<div>
			<input type="hidden" id="user_seq" name="user_seq" />
			<c:if test="${srchParam.pageIdx != null}">
			<input type="hidden" name="pageIdx" id="pageIdx" value='<c:out value="${srchParam.pageIdx}" />' />
			</c:if>
			<input type="hidden" name="sortKey" id="sortKey" value='<c:out value="${srchParam.sortKey}" />' />
			<input type="hidden" name="sortType" id="sortType" value='<c:out value="${srchParam.sortType}" />' />
		</div>	
		<table id="userMonitorTb" cellspacing="0" class="tb_MonitorL">
		<colgroup>
			<col width="10%">
			<col width="30%">
			<col width="30%">
			<col width="30%">
		</colgroup>
		<thead>
		<tr style="cursor:pointer">
			<th scope="col" onClick="goSort('D.USER_SEQ')"><s:message code='com.netmng.text.network.number' /></th>
			<th scope="col" onClick="goSort('E.ID')"><s:message code='com.netmng.text.network.id' /></th>
			<th scope="col" onClick="goSort('E.NAME')"><s:message code='com.netmng.text.network.name' /></th>
			<th scope="col" onClick="goSort('D.SEQ')"><s:message code='com.netmng.text.network.cnt' /></th>
		</tr>
		</thead>
		<c:forEach items="${userMonitorList}" varStatus="status" var="userMonitor">
		<tr onClick="view(<c:out value="${userMonitor.seq}" />);" style="cursor:pointer">
			<td>
				<c:out value="${srchParam.totRow-(status.index+srchParam.sIdx)}" />
			</td>
			<td><c:out value="${userMonitor.id}" /></td>
			<td><c:out value="${userMonitor.name}" /></td>
			<td class="MonitorL_head"><c:out value="${userMonitor.cnt}" /></td>
		</tr>
		</c:forEach>
		</table>
		<div class="paging">
			<page:pagingTag totRow="${srchParam.totRow}" row="${srchParam.row}" pageIdx="${srchParam.pageIdx}" func="goPage" cxtPath="" />
		</div>
	</div>
	<!-- //MonitorL -->
	</f:form>
</div>
<!-- //content -->