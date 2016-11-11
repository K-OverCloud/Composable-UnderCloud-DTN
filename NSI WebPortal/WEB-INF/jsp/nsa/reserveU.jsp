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
		
		setVLAN("sourceVLAN");
		setVLAN("destVLAN");
	});
	
	$(function(){
		$(document).on("keyup", "input:text[numberOnly]", function() {$(this).val( $(this).val().replace(/[^0-9]/gi,"") );});
		$(document).on("keyup", "input:text[datetimeOnly]", function() {$(this).val( $(this).val().replace(/[^0-9:\-]/gi,"") );});
	});
	
	function setVLAN(vlan){
		var networkId	= "";
		var localId		= "";
		var vlanSeq		= "";
		if(vlan=="sourceVLAN"){
			networkId	= "${reservationDTO.source_network_id}";
			localId		= "${reservationDTO.source_local_id}";
			vlanSeq		= "${reservationDTO.sourceVLAN}";
		}else if(vlan=="destVLAN"){
			networkId	= "${reservationDTO.dest_network_id}";
			localId		= "${reservationDTO.dest_local_id}";
			vlanSeq		= "${reservationDTO.destVLAN}";
		}
			
		var obj = $("#reservationDTO").find("#"+vlan);
		obj.find("option").remove();
		<c:forEach items="${stpVlanList}" varStatus="status" var="stpVlanDTO">
		if(networkId=="${stpVlanDTO.network_id}" && localId=="${stpVlanDTO.local_id}"){
			if(vlanSeq=="${stpVlanDTO.vlan_seq}")
				obj.append("<option value='${stpVlanDTO.vlan_seq}' selected='selected'>${stpVlanDTO.vlan_seq}</option>");
			else
				obj.append("<option value='${stpVlanDTO.vlan_seq}'>${stpVlanDTO.vlan_seq}</option>");
		}
		</c:forEach>
	}
	
	/* function provision() {
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
	} */
	function reserveUpdate(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/reserveUP.do").attr("target", "_proc").submit();
	}
	function goList() {
		$("#listFrm").attr("action", "${pageContext.request.contextPath}/nsa/reserveL.do").submit();
	}
	function goView() {
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/reserveV.do").submit();
	}
	
	function goListSeq(seq) {
		$("#listFrm").find("#seq").val(seq);
		$("#listFrm").attr("action", "${pageContext.request.contextPath}/nsa/reserveV.do").submit();
	}
	/* function confirmProvision() {
		loadConfirm($("#provisionDv"));
	}
	function confirmRelease() {
		loadConfirm($("#releaseDv"));
	}
	function confirmTerminate() {
		loadConfirm($("#terminateDv"));
	} */
	
	function reserveMod() {
		/* window.location.hash = "top"; */
		window.scrollTo(0,0);
		loadConfirm($("#reserveMod"));
	}
	
	/* function troubleAsk() {
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
	} */
	/* function cellEvent(sourceSeq, destSeq ,CI ,BW) {
		thisMovie("EI_flash").moveMC(sourceSeq, destSeq ,CI ,BW);
	} */
	
	function srchBandWidth() {
		if($("#reservationDTO").find("#source_network_id").val() == "") {
			alert("<s:message code='com.netmng.vo.Reservation.source_network_id' />");
			return false;
		} else if($("#reservationDTO").find("#source_local_id").val() == "") {
			alert("<s:message code='com.netmng.vo.Reservation.source_local_id' />");
			return false;
		} else if($("#reservationDTO").find("#dest_network_id").val() == "") {
			alert("<s:message code='com.netmng.vo.Reservation.dest_network_id' />");
			return false;
		} else if($("#reservationDTO").find("#dest_local_id").val() == "") {
			alert("<s:message code='com.netmng.vo.Reservation.dest_local_id' />");
			return false;
		} else if($("#reservationDTO").find("#start_date").val() == "") {
			alert("<s:message code='com.netmng.vo.Reservation.start_time' />");
			return false;
		} else if($("#reservationDTO").find("#end_date").val() == "") {
			alert("<s:message code='com.netmng.vo.Reservation.end_time' />");
			return false;
		} else {
			var vTime = "";
			vTime += $("#reservationDTO").find("#start_date").val()/*.replace(/\./gi, '-')*/ + " " + $("#reservationDTO").find("#start_hour").val() + ":" + $("#reservationDTO").find("#start_min").val()+":00";
			vTime += "|";
			vTime += $("#reservationDTO").find("#end_date").val()/*.replace(/\./gi, '-')*/ + " " + $("#reservationDTO").find("#end_hour").val() + ":" + $("#reservationDTO").find("#end_min").val()+":00";

			//alert( $("#reservationDTO").find("#end_date").val() + " " + $("#reservationDTO").find("#end_hour").val() + ":" + $("#reservationDTO").find("#end_min").val() + ":00" );
			$("#bandwidthDv").hide();
			$.ajax( 
				{
					type : "GET",
					url : "${pageContext.request.contextPath}/getBandwidth.json?cache=" + Math.round(Math.random()* (new Date().getTime())),
					contentType : "application/json; charset=utf-8",
					dataType: "json",
			        data : ({
			        	srcNetworkId : $("#reservationDTO").find("#source_network_id").val(),
			        	srcLocalId : $("#reservationDTO").find("#source_local_id").val(),
			        	destNetworkId : $("#reservationDTO").find("#dest_network_id").val(),
			        	destLocalId : $("#reservationDTO").find("#dest_local_id").val(),
			        	sdTime : vTime
			        }),
			        success : 	function(data) {
			        				if(data != null) {
			        					$("#bandwidthLb").text("총 최대" +  data + "Mb 사용 가능합니다");
			        					$("#bandwidthDv").show();
			        					bandWidth = data;
			        				} else {
			        					$("#bandwidthLb").text("<s:message code='com.netmng.exception.db' />");
			        					$("#bandwidthDv").show();
			        				}
			        			},
					error : 	function(data) {
				    					$("#bandwidthLb").text("<s:message code='com.netmng.exception.db' />");
				    					$("#bandwidthDv").show();
								}
				}
			);
		}
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
	

	<!-- // reserve_2 -->
	<div id="reserve_2">
	<s:bind path="reservationDTO.seq">
		<f:hidden path="${status.expression}" /> 
	</s:bind>
	
		<table cellspacing="0" class="tb_request" style="width:760px;">
		<colgroup>
			<col class="col" width="96px">
			<col width="280px">
			<col class="col" width="96px">
			<col width="280px">
		</colgroup>
		<tbody>
			<tr>
	 			<th align="left" valign="middle" scope="row"><label for="${status.expression}"><s:message code='com.netmng.text.network.version' /></label></th>
				<td align="left" valign="middle" colspan="3"><c:out value="${reservationDTO.version+1}" /></td>
				<s:bind path="reservationDTO.version">
					<f:hidden path="${status.expression}"/> 
				</s:bind>
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
					<f:hidden path="source_network_id" value="${reservationDTO.sourceStpNetwork.network_id}"/> 
					<f:hidden path="source_local_id" value="${reservationDTO.sourceStpLocal.local_id}"/> 
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="dest_ip_str"><s:message code='com.netmng.text.network.destSTP' /></label></th>
				<td align="left" valign="middle" colspan="3">
					<c:out value="${reservationDTO.destStpNetwork.network_nm}" />(<c:out value="${reservationDTO.destStpLocal.local_nm}" />)
					<f:hidden path="dest_network_id" value="${reservationDTO.destStpNetwork.network_id}"/> 
					<f:hidden path="dest_local_id" value="${reservationDTO.destStpLocal.local_id}"/>
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
								<c:if test="${i==start_hourFmt}">						
									<f:option value='${i}' label='${i}시' selected='selected'/>
								</c:if>
								<c:if test="${i!=start_hourFmt}">						
									<f:option value='${i}' label='${i}시'/>
								</c:if>
							</c:forEach>
						</f:select>
					</div>
					<div class="select_2">
						<fmt:formatDate value="${reservationDTO.start_time}" var="start_minFmt" pattern="m"/>					
						<f:select path="start_min" cssStyle="width:50px;height:18px">
							<c:forEach begin="0" end="59" var="i" step="1">
								<c:if test="${i==start_minFmt}">						
									<f:option value='${i}' label='${i}분' selected='selected'/>
								</c:if>
								<c:if test="${i!=start_minFmt}">						
									<f:option value='${i}' label='${i}분' />
								</c:if>
							</c:forEach>
						</f:select>
					</div>
				</td>
				<th align="left" valign="middle" scope="row"><label for="end_date"><s:message code='com.netmng.text.network.endTime' /></label></th>
				<td align="left" valign="middle">
					<div class="inputbox_1">
						<s:bind path="end_date">
						<fmt:formatDate value="${reservationDTO.end_time}" var="end_dateFmt" pattern="yyyy.MM.dd"/>
						<f:input path="${status.expression}" cssClass="box_1" cssStyle="width:70px;height:14px" value="${end_dateFmt}"/>
						</s:bind>
					</div>
					<div class="btn_1" style="display: none;">
						<a  id="endBt"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_calendar.gif" alt="" class="btn_1"></a>
					</div>
					<div class="select_1">
						<fmt:formatDate value="${reservationDTO.end_time}" var="end_hourFmt" pattern="H"/>
						<f:select path="end_hour" cssStyle="width:50px;height:18px">
							<c:forEach begin="0" end="23" var="i" step="1">
								<c:if test="${i==end_hourFmt}">						
									<f:option value='${i}' label='${i}시' selected='selected'/>
								</c:if>
								<c:if test="${i!=end_hourFmt}">						
									<f:option value='${i}' label='${i}시' />
								</c:if>
							</c:forEach>
						</f:select>
					</div>
					<div class="select_2">
						<fmt:formatDate value="${reservationDTO.end_time}" var="end_minFmt" pattern="m"/>
						<f:select path="end_min" cssStyle="width:50px;height:18px">
							<c:forEach begin="0" end="59" var="i" step="1">
								<c:if test="${i==end_minFmt}">						
									<f:option value='${i}' label='${i}분' selected='selected'/>
								</c:if>
								<c:if test="${i!=end_minFmt}">						
									<f:option value='${i}' label='${i}분' />
								</c:if>
							</c:forEach>
						</f:select>
					</div>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.network.bandwidth' /></th>
				<td align="left" valign="middle" class="profile_img2" colspan="3">
					<%-- <c:out value="${reservationDTO.desired}" /> --%>
					<div class="btn_2">
						<a  onClick="srchBandWidth();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_inquiry.gif" alt=""></a>
					</div>
					<div class="txt_1" id="bandwidthDv" style="display:none"><label id="bandwidthLb"></label></div>
					<div class="inputbox_2">
					<s:bind path="desired">
					<f:input path="${status.expression}" value="" cssStyle="width:260px;" numberonly="true" />
					</s:bind>
					<s:bind path="minimum">
					<f:hidden path="${status.expression}" value="" />
					</s:bind>
					<s:bind path="maximum">
					<f:hidden path="${status.expression}" value="" />
					</s:bind>
					</div>
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
					<f:select path="sourceVLAN" cssStyle="width:100px;height:18px" >
					</f:select>
				</td>
				<th align="left" valign="middle" scope="row"><label for="end_date"><s:message code='com.netmng.text.network.destVLAN' /></label></th>
				<td align="left" valign="middle">
					<f:select path="destVLAN" cssStyle="width:100px;height:18px" >
					</f:select>
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
				<a onClick="reserveMod();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_modify.gif" alt="수정"></a>
				<a onClick="goView();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_cancel.gif" alt="취소"></a>
				<a onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_list.gif" alt="목록"></a>
			</div>
			<%-- <div class="btn_4"> 
				<a onClick="goView();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_cancel.gif" alt="취소"></a>
				<a onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_list.gif" alt="목록"></a>
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
	
	<div id="reserveMod" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.network.mod' /></b></span></p>
				</div>
				<div class="btn_area_con">				
					<div class="btn_yes">
						<a onClick="reserveUpdate();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
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