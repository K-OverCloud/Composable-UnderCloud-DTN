<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
    
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>  
<script type="text/javascript">
	$(document).ready(function() {
		$("#reservationDTO").find("#start_date").mask("9999.99.99");
		$("#reservationDTO").find("#end_date").mask("9999.99.99");
		$("#reservationDTO").find("#start_date").datepick({showOnFocus: false, showTrigger: '#startBt', dateFormat: 'yyyy.mm.dd'});
		$("#reservationDTO").find("#end_date").datepick({showOnFocus: false, showTrigger: '#endBt', dateFormat: 'yyyy.mm.dd'});
		
		/* chgProviderNsa();
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
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/querySummaryP.do").attr("target", "_self").submit();
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
		/* window.location.hash = "top"; */
		window.scrollTo(0,0);
		loadConfirm($("#provisionDv"));
	}
	function confirmRelease() {
		/* window.location.hash = "top"; */
		window.scrollTo(0,0);
		loadConfirm($("#releaseDv"));
	}
	function confirmTerminate() {
		/* window.location.hash = "top"; */
		window.scrollTo(0,0);
		loadConfirm($("#terminateDv"));
	}
	function reserveMod() {
		/* window.location.hash = "top"; */
		window.scrollTo(0,0);
		loadConfirm($("#reserveMod"));
	}
	function reserveAbort() {
		/* window.location.hash = "top"; */
		window.scrollTo(0,0);
		loadConfirm($("#reserveAbort"));
	}
	function reserveCommit() {
		/* window.location.hash = "top"; */
		window.scrollTo(0,0);
		loadConfirm($("#reserveCommit"));
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
		<span class="now"><s:message code='com.netmng.menu.network3' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/title_2_3.gif" alt=""></h2>
	<f:form name="reservationDTO" id="reservationDTO" modelAttribute="reservationDTO" method="POST">
	<!-- // request_1 -->
	<%-- <div id="request_1">
		<dl class="img_area">
			<dt><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/title_2_2_1.gif" alt=""></dt>
			<dd>
				<!-- 
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/img_2.jpg" width="323" height="354">
				-->
				<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=11,1,102,63" width="323px" height="450px" id="EI_flash">
					<param name="movie" value="${pageContext.request.contextPath}/swf/userbox_check.swf?loadURL=${pageContext.request.contextPath}/rest/api/topologyList&startNUM=${reservationDTO.source_stp_seq}&endNUM=${reservationDTO.dest_stp_seq}&CI=${reservationDTO.connection_id}&BW=${reservationDTO.desired}" />
					<param name="quality" value="high" />
					<param name='wmode' value='transparent' />
					<param name="allowScriptAccess" value="always" />
					<embed src="${pageContext.request.contextPath}/swf/userbox_check.swf?loadURL=${pageContext.request.contextPath}/rest/api/topologyList&startNUM=${reservationDTO.source_stp_seq}&endNUM=${reservationDTO.dest_stp_seq}&CI=${reservationDTO.connection_id}&BW=${reservationDTO.desired}" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="323" height="450" allowScriptAccess="always" showLiveConnect="true" name="EI_flash"></embed>
				</object>
			</dd>
		</dl>
	</div> --%>
	<!-- request_1 //-->

	<!-- // reserve_2 -->
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
				<c:if test="${reservationDTO.life_cycle!='Terminated' && reservationDTO.version_commit=='Y'}">
					<c:if test="${reservationDTO.reserve_state=='ReserveStart'}">
						<!-- <a onClick="reserveMod();"> -->
						<a onClick="goUpdate();">
							<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_modify.gif" alt="수정">
						</a>
					</c:if>
					<c:if test="${reservationDTO.reserve_state=='ReserveHeld' && reservationDTO.reserve_fin=='N'}">
						<a onClick="reserveCommit();">
							<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_commit_on.gif" alt="예약확정">
						</a>
					</c:if>
					<c:if test="${(reservationDTO.reserve_state=='ReserveFailed' || reservationDTO.reserve_state=='ReserveHeld' || reservationDTO.reserve_state=='ReserveTimeout') && reservationDTO.reserve_fin=='N'}">
						<a onClick="reserveAbort();">
							<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_abort_on.gif" alt="예약취소">
						</a>
					</c:if>
					<c:if test="${reservationDTO.reserve_state=='ReserveStart' && reservationDTO.reserve_fin=='Y' && reservationDTO.provision_state=='Released'}">
						<a onClick="provision();">
							<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_configure_on.gif" alt="구성">
						</a>
					</c:if>
					<c:if test="${reservationDTO.reserve_state=='ReserveStart' && reservationDTO.reserve_fin=='Y' && reservationDTO.provision_state=='Provisioned'}">
						<a onClick="release();">
							<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_clear_on.gif" alt="해제">
						</a>
					</c:if>
					<a onClick="terminate();">
						<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_end.gif" alt="종결">
					</a>					
				</c:if>

				<a onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_list.gif" alt="목록"></a>
			
			</div>
			<%-- <div class="btn_4"> 
				<a onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_list.gif" alt="목록"></a>
				<a onClick="querySummaryProc();">dynamicKL정보</a>
			</div> --%>
		</div>
		
	</div>
	<!-- // reserve_2 -->
	</f:form>


	<iframe id="_proc" name="_proc" frameborder="0" scrolling="no" width="0" height="0" ></iframe>
	<f:form name="listFrm" id="listFrm" action="${pageContext.request.contextPath}/nsa/reserveL.do" method="GET" modelAttribute="srchParam">
		<input type="hidden" name="seq" id="seq" />
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
<!--//content-->