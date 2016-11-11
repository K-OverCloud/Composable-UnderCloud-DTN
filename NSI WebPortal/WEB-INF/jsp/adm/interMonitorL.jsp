<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 	
<%@	taglib prefix="page" 	uri="/WEB-INF/tld/PagingTag.tld"%>

<style type="text/css">
#interMonitorTb tbody tr:hover
{
	background-color: LemonChiffon;
}
</style>  
<script type="text/javascript">
	$(document).ready(function() {
		$(document).attr("title", "국내망 모니터링");
		//$('#tableone').columnHover(); 
		$("#srchParam").find("#createBegin").mask("9999.99.99");
		$("#srchParam").find("#createEnd").mask("9999.99.99");
		$("#srchParam").find("#createBegin").datepick({showOnFocus: false, showTrigger: '#startBt', dateFormat: 'yyyy.mm.dd'});
		$("#srchParam").find("#createEnd").datepick({showOnFocus: false, showTrigger: '#endBt', dateFormat: 'yyyy.mm.dd'});
		//$(".datepick-trigger").removeClass("datepick-trigger").addClass("btn_user");
	});
	function goSearch() {
		$("#srchParam").find("#pageIdx").val("1");
		$("#srchParam").find("#sortKey").val("");
		$("#srchParam").find("#sortType").val("");
		$("#srchParam").submit();
	}
	function goPage(pageIdx){
		$("#srchParam").find("#pageIdx").val(pageIdx);
		$("#srchParam").submit();
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
	function view(seq) {
		$("#srchParam").find("#seq").val(seq);
		$("#srchParam").attr("action", "${pageContext.request.contextPath}/adm/interMonitorV.do").submit();
	}
	function cellEvent(sourceSeq, destSeq ,CI ,BW) {
		thisMovie("EI_flash").moveMC(sourceSeq, destSeq ,CI ,BW);
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
		<span class="now"><s:message code='com.netmng.menu.admin22' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/title_3_2.gif" alt=""></h2>

	<f:form name="srchParam" id="srchParam" action="${pageContext.request.contextPath}/adm/interMonitorL.do" modelAttribute="srchParam" method="GET">
	
	<!--  tab_area -->
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
			<td style="cursor:pointer;" onclick="chgTab('${pageContext.request.contextPath}/adm/userMonitorL.do');">
				<s:message code='com.netmng.menu.admin21' />
			</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td style="background-color:white;">
				<s:message code='com.netmng.menu.admin22' />
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<br /><br />	
	<%-- <div id="tab_area">
		<div class="tab_Monitor_1">
			<a href="${pageContext.request.contextPath}/adm/userMonitorL.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_2_1_off.gif"></a> 
		</div>
		<div class="tab_Monitor_2">
			<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_2_2_on.gif">
		</div>
		<div class="tab_Monitor_3">
			<a href="${pageContext.request.contextPath}/adm/globalMonitorL.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_2_3_off.gif" ></a>
			<a href="#none;" onclick="alert('This function is not service');"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_2_3_off.gif" ></a>
		</div>
	</div> --%>
	<!-- //tab_area -->
	
	<!--  search -->
	<div id="search_user">
		<input type="hidden" id="seq" name="seq" />
		<c:if test="${srchParam.pageIdx != null}">
		<input type="hidden" name="pageIdx" id="pageIdx" value='<c:out value="${srchParam.pageIdx}" />' />
		</c:if>
		<input type="hidden" name="sortKey" id="sortKey" value='<c:out value="${srchParam.sortKey}" />' />
		<input type="hidden" name="sortType" id="sortType" value='<c:out value="${srchParam.sortType}" />' />
		<div class="search_user_tit">
			<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/search_tit.gif" > 
		</div>
		<s:bind path="srchParam.createBegin">
		<div class="search_txt_1">
			<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/search_txt_1.gif" >
		</div>
		<div class="inputbox_user_3">
			<f:input path="${status.expression}" cssStyle="width:70px;height:17px" />
		</div>
		<div class="btn_user" style="display: none;">
			<a id="startBt"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_calendar.gif" /></a>
		</div>
		</s:bind>
		<div class="user_3">
			~
		</div>
		<s:bind path="srchParam.createEnd">
		<div class="search_txt_2">
			<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/search_txt_2.gif" >
		</div>
		<div class="inputbox_user_3">
			<f:input path="${status.expression}" cssStyle="width:70px;height:17px" />&nbsp;
		</div>
		<div class="btn_user" style="display: none;">
			<a id="endBt"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_calendar.gif" /></a>
		</div>
		</s:bind>
		<div class="btn_search_user">
			<a onClick="goSearch();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_search.gif" ></a> 
		</div>
	</div>	
	<!--  //search --> 
	
	<div id="notice">
		<f:form name="srchParam" id="srchParam" action="${pageContext.request.contextPath}/nsa/reserveL.do" modelAttribute="srchParam" method="GET">
			<input type="hidden" id="seq" name="seq" />
			<div class="notice_count">
				<c:if test="${pageContext.response.locale.language == null}">
				[ 총 <c:out value="${srchParam.totRow}" /> 건 ]
				</c:if>
				<c:if test="${pageContext.response.locale.language == ''}">
				[ 총 <c:out value="${srchParam.totRow}" /> 건 ]
				</c:if>
				<c:if test="${pageContext.response.locale.language == 'ko'}">
				[ 총 <c:out value="${srchParam.totRow}" /> 건 ]
				</c:if>
				<c:if test="${pageContext.response.locale.language == 'en'}">
				[ total <c:out value="${srchParam.totRow}" />]
				</c:if>
				<c:if test="${srchParam.pageIdx != null}">
				<input type="hidden" name="pageIdx" id="pageIdx" value='<c:out value="${srchParam.pageIdx}" />' />
				</c:if>
				<input type="hidden" name="sortKey" id="sortKey" value='<c:out value="${srchParam.sortKey}" />' />
				<input type="hidden" name="sortType" id="sortType" value='<c:out value="${srchParam.sortType}" />' />
			</div>
			
			<table id="reserveListTb" cellspacing="0" class="tb_r">
			<colgroup>
				<col width="5%">
				<col width="*">
				<col width="6%">
				<col width="6%">
				<col width="12%">
				<col width="12%">
				<col width="15%">
				<col width="15%">
				<col width="11%">
			</colgroup>
			<thead>
			<tr>
				<%-- <th style="cursor:pointer" scope="col" onClick="goSort('SEQ')"><s:message code='com.netmng.text.network.number' /></th>
				<th style="cursor:pointer" scope="col" onClick="goSort('CONNECTION_ID')">Connection<br/>ID</th>
				<th style="cursor:pointer" scope="col" onClick="goSort('SOURCE_STP_SEQ')"><s:message code='com.netmng.text.network.source' /></th>
				<th style="cursor:pointer" scope="col" onClick="goSort('DEST_STP_SEQ')"><s:message code='com.netmng.text.network.destination' /></th>
				<th style="cursor:pointer" scope="col" onClick="goSort('START_TIME')"><s:message code='com.netmng.text.network.startTime1' /></th>
				<th style="cursor:pointer" scope="col" onClick="goSort('END_TIME')"><s:message code='com.netmng.text.network.endTime1' /></th>
				<th style="cursor:pointer" scope="col" onClick="goSort('STATUS')"><s:message code='com.netmng.text.network.status' /></th> --%>
				<th scope="col"><s:message code='com.netmng.text.network.number' /></th>
				<th scope="col">Connection ID</th>
				<th scope="col">대역폭</th>
				<th scope="col">사용자</th>
				<th scope="col"><s:message code='com.netmng.text.network.source' /></th>
				<th scope="col"><s:message code='com.netmng.text.network.destination' /></th>
				<th scope="col"><s:message code='com.netmng.text.network.startTime1' /></th>
				<th scope="col"><s:message code='com.netmng.text.network.endTime1' /></th>
				<th scope="col"><s:message code='com.netmng.text.network.status' /></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${reservationList}" varStatus="status" var="reservation">
			<tr 
				<%// || (reservation.status == 'C' && reservation.flag == 'R') %>
				<%-- <c:if test="${(reservation.trouble_yn == 'N' && !(reservation.status == 'C' && reservation.flag == 'F'))}">
					onClick="view(<c:out value="${reservation.seq}" />);" 
					style="cursor:pointer"
				</c:if> --%>	
				onclick="view(<c:out value="${reservation.seq}" />);" 
				style="cursor:pointer"
			>
				<td>
					<c:out value="${srchParam.totRow-(status.index+srchParam.sIdx)}" />
				</td>
				<td class="tb_r_head"><c:out value="${reservation.connection_id}" /></td>
				<td><c:out value="${reservation.desired}" /></td>
				<td style="word-break:break-all"><c:out value="${reservation.user.id}" /></td>
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
			</tbody>
			</table>
			<!-- 페이징 -->
			<div class="paging">
				<page:pagingTag totRow="${srchParam.totRow}" row="${srchParam.row}" pageIdx="${srchParam.pageIdx}" func="goPage" cxtPath="" />
			</div>
			<!-- //페이징 -->
			
		</f:form>
	</div>
	</f:form>
</div>
<c:if test="${reservationMsg != null}">
<script type="text/javascript">
	alert("<s:message code='${reservationMsg}' />");
</script>
</c:if>
<!-- //content -->