<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 	
<%@	taglib prefix="page" 	uri="/WEB-INF/tld/PagingTag.tld"%>

<style type="text/css">
#errorTb tbody tr:hover
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
		<span class="now"><s:message code='com.netmng.menu.admin33' /></span>
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
			<a href="${pageContext.request.contextPath}/adm/eventL.do">
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_3_2_off.gif" >
			</a>
		</div>  
		<div class="tab_Monitor_3">
			<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_3_3_on.gif">
		</div>
	</div>
	<!-- //tab_area -->
	
	<div id="notice">
		<f:form name="srchParam" id="srchParam" action="${pageContext.request.contextPath}/adm/errorL.do" modelAttribute="srchParam" method="GET">
			<div>
				<c:if test="${srchParam.pageIdx != null}">
				<input type="hidden" name="pageIdx" id="pageIdx" value='<c:out value="${srchParam.pageIdx}" />' />
				</c:if>
				<input type="hidden" name="sortKey" id="sortKey" value='<c:out value="${srchParam.sortKey}" />' />
				<input type="hidden" name="sortType" id="sortType" value='<c:out value="${srchParam.sortType}" />' />
			</div>	
				<table id="errorTb" cellspacing="0" class="tb_l">
				<colgroup>
					<col width="5%">
					<!--<col width="20%">-->
					<col width="30%">
					<col width="50%">
					<col width="15%">
				</colgroup>
				
				<thead>
				<tr>
					<th style="cursor:pointer" scope="col" onClick="goSort('SEQ')">No</th>
					<!-- <th style="cursor:pointer" scope="col" onClick="goSort('URL')">url</th> -->
					<th style="cursor:pointer" scope="col" onClick="goSort('NAME')">name</th>
					<th style="cursor:pointer" scope="col" onClick="goSort('LOG_MESSAGE')">log_message</th>
					<th style="cursor:pointer" scope="col" onClick="goSort('CREATETIME')">date</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${errorList}" varStatus="status" var="errorLog">
					<tr>
						<td>
							<c:out value="${srchParam.totRow-(status.index+srchParam.sIdx)}" />
						</td>
						<!-- <td>
							${pageContext.request.contextPath}<c:out value="${errorLog.url}" />
						</td> -->
						<td>
							<c:out value="${errorLog.name}" />
						</td>
						<td>
							<c:out value="${errorLog.log_message}" />
						</td>
						<td><s:eval expression='new java.text.SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(errorLog.createtime)' /></td>
					</tr>
					</c:forEach>
				</tbody>
				</table>
			<div class="paging">
				<page:pagingTag totRow="${srchParam.totRow}" row="${srchParam.row}" pageIdx="${srchParam.pageIdx}" func="goPage" cxtPath="" />
			</div>
		</f:form>
	</div>
</div>
<!-- //content -->