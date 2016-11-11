<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
       
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
		/* $("#srchParam").find("#createBegin").mask("9999.99.99");
		$("#srchParam").find("#createEnd").mask("9999.99.99");
		$("#srchParam").find("#createBegin").datepick({showOnFocus: false, showTrigger: '#startBt', dateFormat: 'yyyy.mm.dd'});
		$("#srchParam").find("#createEnd").datepick({showOnFocus: false, showTrigger: '#endBt', dateFormat: 'yyyy.mm.dd'}); */
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
	
	
	function goListSeq(seq) {
		$("#listFrm").find("#seq").val(seq);
		$("#listFrm").attr("action", "${pageContext.request.contextPath}/adm/interMonitorL.do").submit();
	}
	
	function goList() {
		$("#listFrm").attr("action", "${pageContext.request.contextPath}/adm/interMonitorL.do").submit();
	}
	function goUpdate(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/adm/interMonitorU.do").attr("target", "_self").submit();
	}
	
	function reserveCommit() {
		/* window.location.hash = "top"; */
		window.scrollTo(0,0);
		loadConfirm($("#reserveCommit"));
	}
	function reserveAbort() {
		/* window.location.hash = "top"; */
		window.scrollTo(0,0);
		loadConfirm($("#reserveAbort"));
	}	
	function provision() {
        var now = new Date();
        var startTime = new Date($("#start_dateFmt").val());
        if(now<startTime){
       	 var autoProvisionMsg = "";
       	 autoProvisionMsg += "<s:message code='com.netmng.text.network.AutoProvision1' />";
       	 autoProvisionMsg += "("+$("#start_dateFmt").val()+")";
       	 autoProvisionMsg += "<s:message code='com.netmng.text.network.AutoProvision2' />";
       	 $("#provisionMsg").text(autoProvisionMsg);
        }else{
       	 $("#provisionMsg").text("<s:message code='com.netmng.text.network.ManualProvision' />"); 
        }
		loadConfirm($("#provision"));
	}
	function release() {
		/* window.location.hash = "top"; */
		window.scrollTo(0,0);
		loadConfirm($("#release"));
	}
	function terminate() {
		/* window.location.hash = "top"; */
		window.scrollTo(0,0);
		loadConfirm($("#terminate"));
	}
	
	function reserveCommitProc(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/adm/proc/reserveCommitP.do").attr("target", "_proc").submit();
	}
	function reserveAportProc(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/adm/proc/reserveAbortP.do").attr("target", "_proc").submit();
	}
	function provisionProc(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/adm/proc/provisionP.do").attr("target", "_proc").submit();
	}
	function releaseProc(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/adm/proc/releaseP.do").attr("target", "_proc").submit();
	}
	function terminateProc(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/adm/proc/terminateP.do").attr("target", "_proc").submit();
	}
	
	function pop_query(proc_type, seq){
		var url = "${pageContext.request.contextPath}/adm/query.do?proc_type="+proc_type+"&seq="+seq;
		window.open(url, "_blank", "width=800, height=600, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=no");
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

	<f:form name="reservationDTO" id="reservationDTO" modelAttribute="reservationDTO" method="POST">
	
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
	
	<!--  //search --> 
	
	<div id="reserve_2">
	<s:bind path="reservationDTO.seq">
	<f:hidden path="${status.expression}" /> 
	</s:bind>
	
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
				<td align="left" valign="middle" colspan="3"><c:out value="${reservationDTO.version}" /></td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="${status.expression}"><s:message code='com.netmng.text.network.reqNSA' /></label></th>
				<td align="left" valign="middle" colspan="3">				
					urn:ogf:network:nsa:krlight_req.test:2012:nsa
	 			</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="${status.expression}"><s:message code='com.netmng.text.network.prvNSA' /></label></th>
				<td align="left" valign="middle" colspan="3">
					<c:out value="${reservationDTO.providerNsa.nsa_nm}" />(<c:out value="${reservationDTO.provider_nsa}" />)
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="host_ip_str"><s:message code='com.netmng.text.network.sourceSTP' /></label></th>
				<td align="left" valign="middle" colspan="3">
					<c:out value="${reservationDTO.sourceStpNetwork.network_nm}" />(<c:out value="${reservationDTO.sourceStpLocal.local_nm}" />)
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="dest_ip_str"><s:message code='com.netmng.text.network.destSTP' /></label></th>
				<td align="left" valign="middle" colspan="3">
					<c:out value="${reservationDTO.destStpNetwork.network_nm}" />(<c:out value="${reservationDTO.destStpLocal.local_nm}" />)
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="host_ip_str"><s:message code='com.netmng.text.network.hostIP' /></label></th>
				<td align="left" valign="middle">
					<c:out value="${reservationDTO.host_ip}" />
				</td>
				<th align="left" valign="middle" scope="row"><label for="dest_ip_str"><s:message code='com.netmng.text.network.destinationIP' /></label></th>
				<td align="left" valign="middle">
					<c:out value="${reservationDTO.dest_ip}" />
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="start_date"><s:message code='com.netmng.text.network.startTime' /></label></th>
				<td align="left" valign="middle">
					<fmt:formatDate value="${reservationDTO.start_time}" var="start_dateFmt" pattern="yyyy.MM.dd H:m"/>
					<s:bind path="reservationDTO.start_time">
						<f:label path="${status.expression}"><s:eval expression='new java.text.SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(reservationDTO.start_time)' /></f:label>
					</s:bind>
					<%-- <br />
					<c:out value="${reservationDTO.start_time}" /> --%>
					<input type="hidden" id="start_dateFmt" name="start_dateFmt" value="${start_dateFmt}">
				</td>
				<th align="left" valign="middle" scope="row"><label for="end_date"><s:message code='com.netmng.text.network.endTime' /></label></th>
				<td align="left" valign="middle">
					<s:bind path="reservationDTO.end_time">
					<f:label path="${status.expression}"><s:eval expression='new java.text.SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(reservationDTO.end_time)' /></f:label>
					</s:bind>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.network.bandwidth' /></th>
				<td align="left" valign="middle" class="profile_img2" colspan="3">
					<c:out value="${reservationDTO.desired}" />
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="start_date"><s:message code='com.netmng.text.network.description' /></label></th>
				<td align="left" valign="middle" colspan="3">
					<c:out value="${reservationDTO.description}" />
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="start_date"><s:message code='com.netmng.text.network.symmetricPath' /></label></th>
				<td align="left" valign="middle">
					<c:if test="${reservationDTO.symmetricPath == '1'}">true</c:if>
					<c:if test="${reservationDTO.symmetricPath == '0'}">false</c:if>
				</td>
				<th align="left" valign="middle" scope="row"><label for="end_date"><s:message code='com.netmng.text.network.directionality' /></label></th>
				<td align="left" valign="middle">
					<c:out value="${reservationDTO.directionality}" />
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="start_date"><s:message code='com.netmng.text.network.mtu' /></label></th>
				<td align="left" valign="middle">
					<c:out value="${reservationDTO.mtu}" />
				</td>
				<th align="left" valign="middle" scope="row"><label for="end_date"><s:message code='com.netmng.text.network.burstsize' /></label></th>
				<td align="left" valign="middle">
					<c:out value="${reservationDTO.burstsize}" />
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
							<c:forEach items="${list_ero}" varStatus="status" var="ero">
							<tr>
								<td align="left" valign="middle" style="text-align:left;">				
									<c:out value="${ero.stpNetwork.network_nm}" /> &gt; <c:out value="${ero.stpLocal.local_nm}" /> 
					 			</td>
								<td align="left" valign="middle">
									<c:out value="${ero.ero_order}" />
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
			
			<tr>
				<th align="left" valign="middle" scope="row"><label for="start_date"><s:message code='com.netmng.text.network.sourceVLAN' /></label></th>
				<td align="left" valign="middle">
					<c:out value="${reservationDTO.sourceVLAN}" />
				</td>
				<th align="left" valign="middle" scope="row"><label for="end_date"><s:message code='com.netmng.text.network.destVLAN' /></label></th>
				<td align="left" valign="middle">
					<c:out value="${reservationDTO.destVLAN}" />
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="start_date">SimpleMode</label></th>
				<td align="left" valign="middle" colspan="3">
					<c:out value="${reservationDTO.simple_mode}" />
				</td>
			</tr>
			 
		</tbody>
		</table>
		
		<div class="btn_area_request">
			<div class="btn_3" style="width:760px; text-align:right;"> 
				<a onClick="goUpdate();">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_modify.gif" alt="수정">
				</a>
				<a onClick="reserveCommit();">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_commit_on.gif" alt="예약확정">
				</a>
				<a onClick="reserveAbort();">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_abort_on.gif" alt="예약취소">
				</a>
				<a onClick="provision();">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_configure_on.gif" alt="구성">
				</a>
				<a onClick="release();">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_clear_on.gif" alt="해제">
				</a>
				<a onClick="terminate();">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_end.gif" alt="종결">
				</a>
			</div>	
			<div class="btn_3" style="width:760px; text-align:right;"> 
				<a href="#none;" onclick="pop_query('querySummary', ${reservationDTO.seq})">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_qrySummary.gif" alt="qrySummary">
					<!-- qrySummary -->
				</a>
				<a href="#none;" onclick="pop_query('querySummarySync', ${reservationDTO.seq})">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_qrySummarySync.gif" alt="qrySummarySync">
					<!-- qrySummarySync -->
				</a>
				<a href="#none;" onclick="pop_query('queryRecursive', ${reservationDTO.seq})">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_qryRecursive.gif" alt="qryRecursive">
					<!-- qryRecursive -->
				</a>
				<a href="#none;" onclick="pop_query('queryNotification', ${reservationDTO.seq})">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_qryNotification.gif" alt="qryNotification">
					<!-- qryNotification -->
				</a>
				<a href="#none;" onclick="pop_query('queryNotificationSync', ${reservationDTO.seq})">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_qryNotificationSync.gif" alt="qryNotificationSync">
					<!-- qryNotificationSync -->
				</a>				
				<a onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_list.gif" alt="목록"></a>
			</div>
			<%-- <div class="btn_4"> 
				<a onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_list.gif" alt="목록"></a>
				<a onClick="querySummaryProc();">dynamicKL정보</a>
			</div> --%>
		</div>
		
	</div>
	
	<input type="hidden" name="createBegin"	id="createBegin"	value="${srchParam.createBegin}"/>
	<input type="hidden" name="createEnd"	id="createEnd"		value="${srchParam.createEnd}"/>
		
	</f:form>
	
	<iframe id="_proc" name="_proc" frameborder="0" scrolling="no" width="0" height="0" ></iframe>
	<f:form name="listFrm" id="listFrm" action="${pageContext.request.contextPath}/adm/interMonitorL.do" method="GET" modelAttribute="srchParam">
		<input type="hidden" name="seq"			id="seq" />
		<input type="hidden" name="createBegin"	id="createBegin"	value="${srchParam.createBegin}"/>
		<input type="hidden" name="createEnd"	id="createEnd"		value="${srchParam.createEnd}"/>
		<%@ include file="/WEB-INF/jsp/com/pageParamH.jsp"%>
	</f:form>
	<div id="provisionDv" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.network.provision' /></b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="provision();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
					</div>
					<div class="btn_no">
						<a onClick="disableConfirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_no.gif" alt="아니오"></a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="releaseDv" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.network.release' /></b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="release();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
					</div>
					<div class="btn_no">
						<a onClick="disableConfirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_no.gif" alt="아니오"></a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="terminateDv" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.network.terminate' /></b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="terminate();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
					</div>
					<div class="btn_no">
						<a onClick="disableConfirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_no.gif" alt="아니오"></a>
					</div>
				</div>
			</form>
		</div>
	</div>

<%
	SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ("yyyy.MM.dd", Locale.KOREA );
	Date currentTime = new Date();
	String mDate = mSimpleDateFormat.format(currentTime);
	mSimpleDateFormat = new SimpleDateFormat ("H", Locale.KOREA );
	String mHour = mSimpleDateFormat.format(currentTime);
	mSimpleDateFormat = new SimpleDateFormat ("m", Locale.KOREA );
	String mMin = mSimpleDateFormat.format(currentTime);
%>	
	
	<div id="reserveMod" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.network.mod' /></b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="goUpdate();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
					</div>
					<div class="btn_no">
						<a onClick="disableConfirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_no.gif" alt="아니오"></a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="reserveAbort" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.network.reserveAbort' /></b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="reserveAportProc();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
					</div>
					<div class="btn_no">
						<a onClick="disableConfirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_no.gif" alt="아니오"></a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="reserveCommit" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.network.reserveCommit' /></b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="reserveCommitProc();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
					</div>
					<div class="btn_no">
						<a onClick="disableConfirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_no.gif" alt="아니오"></a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="provision" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b id="provisionMsg"><s:message code='com.netmng.text.network.provision' /></b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="provisionProc();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
					</div>
					<div class="btn_no">
						<a onClick="disableConfirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_no.gif" alt="아니오"></a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="release" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.network.provision' /></b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="releaseProc();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
					</div>
					<div class="btn_no">
						<a onClick="disableConfirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_no.gif" alt="아니오"></a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="terminate" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.network.provision' /></b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="terminateProc();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
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