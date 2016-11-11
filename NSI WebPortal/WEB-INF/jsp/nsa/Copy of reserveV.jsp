<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		
		chgProviderNsa();
		chgNetworkId("source_network_id", "source_local_id", "sourceVLAN");
		chgNetworkId("dest_network_id", "dest_local_id", "destVLAN");
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

<%System.out.println("=====>> 000"); %>

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

<%System.out.println("=====>> 222"); %>

	<!-- // reserve_2 -->
	<div id="reserve_2">
	<s:bind path="reservationDTO.seq">
	<f:hidden path="${status.expression}" /> 
	</s:bind>
	
		<table cellspacing="0" class="tb_request">
		<colgroup>
			<colgroup>
				<col class="col">
				<col width="280px">
				<col class="col">
				<col width="280px">
			</colgroup>
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
				<td align="left" valign="middle">
					<c:out value="${reservationDTO.sourceStpNetwork.network_nm}" />(<c:out value="${reservationDTO.sourceStpLocal.local_nm}" />)
				</td>
				<th align="left" valign="middle" scope="row"><label for="dest_ip_str"><s:message code='com.netmng.text.network.destSTP' /></label></th>
				<td align="left" valign="middle">
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
					<div class="inputbox_1">
						<s:bind path="start_date">
						<fmt:formatDate value="${reservationDTO.start_time}" var="start_dateFmt" pattern="yyyy.MM.dd"/>
						<f:input path="${status.expression}" cssClass="box_1" cssStyle="width:70px;height:14px" value="${start_dateFmt}"/>
						</s:bind>
					</div>
					<div class="btn_1" style="display: none;">
						<a  id="startBt"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_calendar.gif" alt="" class="btn_1"></a>
					</div>
					<div class="select_1">
						<fmt:formatDate value="${reservationDTO.start_time}" var="start_hourFmt" pattern="H"/>
						<f:select path="start_hour" cssStyle="width:50px;height:18px">
						<c:forEach begin="0" end="23" var="i" step="1">
							<f:option value='${i}' label='${i}시' />
						</c:forEach>
						</f:select>
					</div>
					<div class="select_2">
						<fmt:formatDate value="${reservationDTO.start_time}" var="start_minFmt" pattern="m"/>					
						<f:select path="start_min" cssStyle="width:50px;height:18px">
						<c:forEach begin="0" end="59" var="i" step="1">
							<f:option value='${i}' label='${i}분' />
						</c:forEach>
						</f:select>
					</div>
				</td>
				<th align="left" valign="middle" scope="row"><label for="end_date"><s:message code='com.netmng.text.network.endTime' /></label></th>
				<td align="left" valign="middle">
					<div class="inputbox_1">
						<s:bind path="end_date">
						<f:input path="${status.expression}" cssClass="box_1" cssStyle="width:70px;height:14px" />
						</s:bind>
					</div>
					<div class="btn_1" style="display: none;">
						<a  id="endBt"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_calendar.gif" alt="" class="btn_1"></a>
					</div>
					<div class="select_1">
						<f:select path="end_hour" cssStyle="width:50px;height:18px">
						<c:forEach begin="0" end="23" var="i" step="1">
							<f:option value='${i}' label='${i}시' />
						</c:forEach>
						</f:select>
					</div>
					<div class="select_2">
						<f:select path="end_min" cssStyle="width:50px;height:18px">
						<c:forEach begin="0" end="59" var="i" step="1">
							<f:option value='${i}' label='${i}분' />
						</c:forEach>
						</f:select>
					</div>
				</td>
			</tr>
			
			
			
			
			<tr>
				<th width="33%" align="left" valign="middle" class="top1" scope="row">Connection ID</th>
				<td width="*" align="left" valign="middle" class="top2">
				<s:bind path="reservationDTO.connection_id">
					<c:out value="${status.value}" />
				</s:bind>
				</td>
			</tr>

<%System.out.println("=====>> 333"); %>
			<tr>
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.network.reservationType' /></th>
				<td align="left" valign="middle">
					<f:label path="type">
					<c:if test="${reservationDTO.type == 'inner'}"><s:message code='com.netmng.text.network.insideDomain' /></c:if>
					<c:if test="${reservationDTO.type == 'multi'}"><s:message code='com.netmng.text.network.multiDomain' /></c:if>
					</f:label>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.network.startTime' /></th>
				<td align="left" valign="middle">
					<s:bind path="reservationDTO.start_time">
						<f:label path="${status.expression}"><s:eval expression='new java.text.SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(reservationDTO.start_time)' /></f:label>
					</s:bind>
				</td>
			</tr>
<%System.out.println("=====>> 444"); %>
			<tr>
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.network.endTime' /></th>
				<td align="left" valign="middle">
					<s:bind path="reservationDTO.end_time">
					<f:label path="${status.expression}"><s:eval expression='new java.text.SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(reservationDTO.end_time)' /></f:label>
					</s:bind>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.network.source' /></th>
				<td align="left" valign="middle">
					<s:bind path="reservationDTO.source_stp.site_name">
					<f:label path="${status.expression}"><c:out value="${status.value}" /></f:label>
					</s:bind>
				</td>
			</tr>
<%System.out.println("=====>> 555"); %>
			<tr>
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.network.destination' /></th>
				<td align="left" valign="middle">
					<s:bind path="reservationDTO.dest_stp.site_name">
					<f:label path="${status.expression}"><c:out value="${status.value}" /></f:label>
					</s:bind>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.network.bandwidth' /></th>
				<td align="left" valign="middle">
					<s:bind path="reservationDTO.desired">
					<f:label path="${status.expression}"><c:out value="${status.value}" />M</f:label>
					</s:bind>
				</td>
			</tr>
<%System.out.println("=====>> 666"); %>
			<tr>
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.network.status' /></th>
				<td align="left" valign="middle">
					<s:bind path="reservationDTO.status">
					<f:label path="${status.expression}">
						<%-- <s:eval expression='new com.netmng.util.ComUtil().getReserveStatusStr(reservationDTO.status)' /> --%>
						<c:if test="${reservationDTO.flag == 'R'}">
						&nbsp;<s:message code='com.netmng.text.network.processing' />
						</c:if>
					</f:label>
					</s:bind>
					<c:if test="${reservationDTO.trouble_yn == 'Y'}">
						&nbsp;(<s:message code='com.netmng.text.network.error' />)
					</c:if>
				</td>
			</tr>
		</tbody>
<%System.out.println("=====>> 777"); %>
		</table>
		<div class="btn_area4">
			<%-- <c:if test="${timeFlag == true}"> --%>
			<c:if test="${reservationDTO.status == 'C'}">	<!-- 상태=예약 -->
					<div class="btn_8">
						<a onClick="confirmProvision();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_configure_on.gif" alt="구성"></a>
					</div>
			</c:if>
			<c:if test="${reservationDTO.status == 'P'}">	<!-- 상태=구성 -->
				<c:if test="${reservationDTO.flag == 'F'}">		<!-- 요청상태=실패 -->
					<div class="btn_8">
						<a onClick="confirmProvision();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_configure_on.gif" alt="구성"></a>
					</div>
				</c:if>
				<c:if test="${reservationDTO.flag == 'C'}">		<!-- 요청상태=성공 -->
					<div class="btn_9">
						<a onClick="confirmRelease();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_clear_on.gif" alt="해제"></a>
					</div>
				</c:if>
			</c:if>
			<c:if test="${reservationDTO.status == 'R'}">	<!-- 상태=해제 -->
				<c:if test="${reservationDTO.flag == 'F'}">		<!-- 요청상태=실패 -->
					<div class="btn_9">
						<a onClick="confirmRelease();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_clear_on.gif" alt="해제"></a>
					</div>
				</c:if>
				<c:if test="${reservationDTO.flag == 'C'}">		<!-- 요청상태=성공 -->
					<div class="btn_8">
						<a onClick="confirmProvision();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_configure_on.gif" alt="구성"></a>
					</div>
				</c:if>
			</c:if>
			<%-- </c:if> --%>
			<div class="btn_10">
				<a onClick="confirmTerminate();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_end.gif" alt="종결"></a>
			</div>
		</div>
		<div class="btn_area5">
			<div class="btn_11">
				<a onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_list.gif" alt="목록"></a>
			</div>
			<div class="btn_11">
				<a onClick="troubleAsk();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_report_2.gif" alt="장애신고"></a>
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
</div>
<!--//content-->