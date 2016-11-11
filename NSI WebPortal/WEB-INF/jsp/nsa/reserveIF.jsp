<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="com.netmng.websvc.soap.svc.services.types.DirectionalityType" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>

<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>  
 
<script type="text/javascript">
	
	var bandWidth = null;

	jQuery(function($){
		$.mask.definitions['_']='[_0-9]';
	});
	$(document).ready(function() {
		$("#reservationDTO").find("#start_date").mask("9999.99.99");
		$("#reservationDTO").find("#end_date").mask("9999.99.99");
		$("#reservationDTO").find("#host_ip_str").mask("___.___.___.___");
		$("#reservationDTO").find("#dest_ip_str").mask("___.___.___.___");
		$("#reservationDTO").find("#start_date").datepick({showOnFocus: false, showTrigger: '#startBt', dateFormat: 'yyyy.mm.dd'});
		$("#reservationDTO").find("#end_date").datepick({showOnFocus: false, showTrigger: '#endBt', dateFormat: 'yyyy.mm.dd'});
		
		chgProviderNsa();
		chgNetworkId("source_network_id", "source_local_id", "sourceVLAN");
		chgNetworkId("dest_network_id", "dest_local_id", "destVLAN");
	});
	
	$(function(){
		$(document).on("keyup", "input:text[numberOnly]", function() {$(this).val( $(this).val().replace(/[^0-9]/gi,"") );});
		$(document).on("keyup", "input:text[datetimeOnly]", function() {$(this).val( $(this).val().replace(/[^0-9:\-]/gi,"") );});
	});
	
	function chgProviderNsa(){
		var selVal = $("#reservationDTO").find("#SEL_PROVIDER_NSA option:selected").val();
		$("#reservationDTO").find("#provider_nsa").val(selVal.split("|")[0]);
		$("#reservationDTO").find("#end_point_address").val(selVal.split("|")[1]);
	}
	
	function chgNetworkId(s1, s2, s3){
		var selNetworkId = $("#reservationDTO").find("#"+s1+" option:selected").val();
		var obj = $("#reservationDTO").find("#"+s2);
		obj.find("option").remove();
		<c:forEach items="${stpLocalList}" varStatus="status" var="stpLocalDTO">
			if(selNetworkId=="${stpLocalDTO.network_id}"){
				obj.append("<option value='${stpLocalDTO.local_id}'>${stpLocalDTO.local_nm}</option>");
			}
		</c:forEach>
		chgLocalId(s1, s2, s3);
	}
	
	function chgLocalId(s1, s2, s3){
		var selNetworkId = $("#reservationDTO").find("#"+s1+" option:selected").val();
		var selLocalId = $("#reservationDTO").find("#"+s2+" option:selected").val();
		var obj = $("#reservationDTO").find("#"+s3);
		obj.find("option").remove();
		<c:forEach items="${stpVlanList}" varStatus="status" var="stpVlanDTO">
			if(selNetworkId=="${stpVlanDTO.network_id}" && selLocalId=="${stpVlanDTO.local_id}"){
				obj.append("<option value='${stpVlanDTO.vlan_seq}'>${stpVlanDTO.vlan_seq}</option>");
			}
		</c:forEach>
	}

	function confirm() {
		/* window.location.hash = "top"; */
		window.scrollTo(0,0);
		loadConfirm($("#confirmDv"));
	}
	
	function save() { 
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/reserveIP.do").attr("target", "_proc");
		$("#reservationDTO").find("#host_ip").val($("#reservationDTO").find("#host_ip_str").val().replace(/_/g, ''));
		$("#reservationDTO").find("#dest_ip").val($("#reservationDTO").find("#dest_ip_str").val().replace(/_/g, ''));
		disableConfirm();
		$("#reservationDTO").submit();
	}
	
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
				        				//alert("success, data is not null : "+data);
			        					$("#bandwidthLb").text("총 최대" +  data + "Mb 사용 가능합니다");
			        					$("#bandwidthDv").show();
			        					bandWidth = data;
			        				} else {
				        				//alert("success, data is null : "+data);
			        					$("#bandwidthLb").text("<s:message code='com.netmng.exception.db' />");
			        					$("#bandwidthDv").show();
			        				}
			        			},
					error : 	function(data) {
        								//alert("error, data : "+data);
				    					$("#bandwidthLb").text("<s:message code='com.netmng.exception.db' />");
				    					$("#bandwidthDv").show();
								}
				}
			);
		}
	}
	
	function troubleAsk() {
		$.ajax( 
			{
				type : "GET",
				url : "${pageContext.request.contextPath}/troubleAsk.json?cache=" + Math.round(Math.random()* (new Date().getTime())),
				contentType : "application/json; charset=utf-8",
				dataType: "json",
		        data : ({
		        		url : "http://www.netmng.re.kr/nsa/reserveIF.do"
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
	
	function setSource(seq) {
		if(seq != 0)
			$("#reservationDTO").find("#source_stp_seq").val(seq);
		else 
			$("#reservationDTO").find("#source_stp_seq").val("");
	}
	
	function setDest(seq) {
		if(seq != 0)
			$("#reservationDTO").find("#dest_stp_seq").val(seq);
		else
			$("#reservationDTO").find("#dest_stp_seq").val("");
	}
	
	function goList() {
		location.href="${pageContext.request.contextPath}/nsa/nsaL.do";
	}
	
	function cancel(){
		location.href="${pageContext.request.contextPath}/nsa/reserveIF.do";
	}
	
	function goListSeq(seq) {
		//location.href="${pageContext.request.contextPath}/nsa/nsaL.do?seq=" + seq;
		location.href="${pageContext.request.contextPath}/nsa/reserveL.do?seq=" + seq;
	}
	
	function eroAdd(){
		var row = $("#TEMPLAT_ERO").val();
		$(row).appendTo("#TBODY_ERO");
	}
	
	function eroDel(obj){
		$(obj).parent().parent().remove();
	}
	
	function selFav(){
		var val = $("#RESERVE_FAV option:selected").val();
		if(val!=null && val!=""){
			var arrS = val.split("|");
			var len = arrS.length;
			
			var endpoint_addr = "";
		<c:forEach items="${providerNSAList}" varStatus="status" var="providerNSA">
			if(arrS[0]=="${providerNSA.nsa_addr}"){
				endpoint_addr = "${providerNSA.endpoint_addr}";
			}
		</c:forEach>
			$("#SEL_PROVIDER_NSA > option[value='"+arrS[0]+"|"+endpoint_addr+"']").attr("selected", "true");
			chgProviderNsa();
			
			$("#source_network_id > option[value='"+arrS[1]+"']").attr("selected", "true");
			chgNetworkId("source_network_id", "source_local_id", "sourceVLAN");
			$("#source_local_id > option[value='"+arrS[3]+"']").attr("selected", "true");
			chgLocalId("source_network_id", "source_local_id", "sourceVLAN");
			
			$("#dest_network_id > option[value='"+arrS[2]+"']").attr("selected", "true");
			chgNetworkId("dest_network_id", "dest_local_id", "destVLAN");	
			$("#dest_local_id > option[value='"+arrS[4]+"']").attr("selected", "true");
			chgLocalId("dest_network_id", "dest_local_id", "destVLAN");
			
			$("#reservationDTO").find("#host_ip_str").val(chgIpMaskInitVal(arrS[5]));
			$("#reservationDTO").find("#dest_ip_str").val(chgIpMaskInitVal(arrS[6]));
		}		
	}
	
	function chgIpMaskInitVal(s){
		var arrHostIpStr = s.split(".");
		var len = arrHostIpStr.length;
		var initVal = "";
		for(i=0; i<len; i++){
			var temp = arrHostIpStr[i];
			if(temp.length==1){		
				temp = "__"+temp;
			}else if(temp.length==2){	
				temp = "_"+temp;
			}
			if(i<(len-1)){
				temp += ".";
			}
			initVal += temp;
		}
		return initVal;
	} 
	
	function chgDisplay(){
		if($("#optData").css('display')=='none'){
			$("#optData").show();
			$("#optDisplay").text("Optional data (-)");
		}else{
			$("#optData").hide();
			$("#optDisplay").text("Optional data (+)");
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
				<a><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_1_on.gif" alt=""></a>
			</li>	
			<li>
				<a href="${pageContext.request.contextPath}/nsa/nsaL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('left_menu_2_2','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_2_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_2_off.gif" name="left_menu_2_2" width="167" height="22" border="0">
				</a>
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
		<a href="${pageContext.request.contextPath}/" class="loc">Home</a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<a  class="loc" href="#"><s:message code='com.netmng.menu.network' /></a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<span class="now"><s:message code='com.netmng.menu.network1' /></span>
	</div>	
	
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/title_2_1.gif" alt=""></h2>	
	
	<!-- // request_1 -->
	
	<!-- request_1 //-->		
	<!-- // request_2 -->
	<!-- <div id="request_2"> -->
	<div id="notice">
		<h3><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/title_2_3_2.gif" alt=""></h3>
	
		<br /><br />
		<div style="font-weight:bold; 
					color:#3f81b0; 
					padding:10px;
					margin:0px;
					border-style:solid;
					border-color:#c3c3c3; 
					border-width:2px;">
			[<s:message code='com.netmng.menu.network5' />]
			<span>
				<select id="RESERVE_FAV" name="RESERVE_FAV" style="width:500px;height:18px" onchange="selFav();">
						<option value="">-- <s:message code='com.netmng.text.fav.select' /> --</option>
					<c:forEach items="${reservationFavList}" varStatus="status" var="reserveFav">
						<option value="${reserveFav.providerNsa.nsa_addr}|${reserveFav.sourceNetwork.network_id}|${reserveFav.destNetwork.network_id}|${reserveFav.sourceLocal.local_id}|${reserveFav.destLocal.local_id}|${reserveFav.host_ip}|${reserveFav.dest_ip}">
							${reserveFav.nm} (${reserveFav.providerNsa.nsa_addr})
						</option>
					</c:forEach>
				</select>
			</span>
		</div>
		
		<br /><br />
		
		<f:form name="reservationDTO" id="reservationDTO" action="${pageContext.request.contextPath}/nsa/proc/reserveIP.do" commandName="reservationDTO" method="POST">
			
		<!-- 필수항목 -->
		<div style="font-weight:bold; font-size:15px; color:#3f81b0;">Mandatory data</div>
		<table cellspacing="0" class="tb_request">
			<colgroup>
				<col class="col">
				<col width="280px">
				<col class="col">
				<col width="280px">
			</colgroup>
			<tbody>
			<tr>
	 			<th align="left" valign="middle" scope="row"><label for="${status.expression}"><s:message code='com.netmng.text.network.version' /></label></th>
				<td align="left" valign="middle" colspan="3">0</td>
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
					<select id="SEL_PROVIDER_NSA" style="width:500px;height:18px" onchange="chgProviderNsa();">
						<c:forEach items="${providerNSAList}" varStatus="status" var="providerNSA">
							<option value="${providerNSA.nsa_addr}|${providerNSA.endpoint_addr}">
								${providerNSA.nsa_nm}(${providerNSA.nsa_addr})
							</option>
						</c:forEach>
					</select>
					<f:hidden path="provider_nsa" value=""/>
					<input type="hidden" name="end_point_address" id="end_point_address" value="">
	 			</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="host_ip_str"><s:message code='com.netmng.text.network.sourceSTP' /></label></th>
				<td align="left" valign="middle" colspan="3">
					<f:select path="source_network_id" cssStyle="width:450px;height:18px" onchange="chgNetworkId('source_network_id', 'source_local_id', 'sourceVLAN')">
						<c:forEach items="${stpNetworkList}" varStatus="status" var="stpNetworkDTO">
							<option value="${stpNetworkDTO.network_id}">
								${stpNetworkDTO.network_nm}
							</option>
						</c:forEach>
					</f:select>
					<br />
					<f:select path="source_local_id" cssStyle="width:450px;height:18px" onchange="chgLocalId('source_network_id', 'source_local_id', 'sourceVLAN')">
					</f:select>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="dest_ip_str"><s:message code='com.netmng.text.network.destSTP' /></label></th>
				<td align="left" valign="middle" colspan="3">
					<f:select path="dest_network_id" cssStyle="width:450px;height:18px" onchange="chgNetworkId('dest_network_id', 'dest_local_id', 'destVLAN')">
						<c:forEach items="${stpNetworkList}" varStatus="status" var="stpNetworkDTO">
							<option value="${stpNetworkDTO.network_id}">
								${stpNetworkDTO.network_nm}
							</option>
						</c:forEach>
					</f:select>
					<br />
					<f:select path="dest_local_id" cssStyle="width:450px;height:18px" onchange="chgLocalId('dest_network_id', 'dest_local_id', 'destVLAN')">
					</f:select>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="host_ip_str"><s:message code='com.netmng.text.network.hostIP' /></label></th>
				<td align="left" valign="middle">
					<s:bind path="host_ip">
						<f:input path="host_ip_str" value="" cssStyle="width:100px;" />
						<f:hidden path="${status.expression}" value="" />
					</s:bind>
				</td>
				<th align="left" valign="middle" scope="row"><label for="dest_ip_str"><s:message code='com.netmng.text.network.destinationIP' /></label></th>
				<td align="left" valign="middle">
					<s:bind path="dest_ip">
						<f:input path="dest_ip_str" value="" cssStyle="width:100px;" />
						<f:hidden path="${status.expression}" value="" />
					</s:bind>
				</td>
			</tr>
<%
	SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ("yyyy.MM.dd", Locale.KOREA );
	Date currentTime = new Date();
	String mDate = mSimpleDateFormat.format(currentTime);
	mSimpleDateFormat = new SimpleDateFormat ("H", Locale.KOREA );
	String mHour = mSimpleDateFormat.format(currentTime);
	mSimpleDateFormat = new SimpleDateFormat ("m", Locale.KOREA );
	String mMin = mSimpleDateFormat.format(currentTime);
%>			
			<c:set var="currHour" value="<%=mHour%>" />
			<c:set var="currMin" value="<%=mMin%>" />
			<tr>
				<th align="left" valign="middle" scope="row"><label for="start_date"><s:message code='com.netmng.text.network.startTime' /></label></th>
				<td align="left" valign="middle">
					<div class="inputbox_1">
						<s:bind path="start_date">
						<f:input path="${status.expression}" value="<%=mDate%>" cssClass="box_1" cssStyle="width:70px;height:14px" />
						</s:bind>
					</div>
					<div class="btn_1" style="display: none;">
						<a  id="startBt"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_calendar.gif" alt="" class="btn_1"></a>
					</div>
					<div class="select_1">
						<f:select path="start_hour" cssStyle="width:50px;height:18px">
						<c:forEach begin="0" end="23" var="i" step="1">
							<c:if test="${i==currHour}">		
								<f:option value='${i}' label='${i}시' selected='selected'/>
							</c:if>
							<c:if test="${i!=currHour}">		
								<f:option value='${i}' label='${i}시' />
							</c:if>
						</c:forEach>
						</f:select>
					</div>
					<div class="select_2">
						<f:select path="start_min" cssStyle="width:50px;height:18px">
						<c:forEach begin="0" end="59" var="i" step="1">
							<c:if test="${i==currMin}">		
								<f:option value='${i}' label='${i}분' selected='selected'/>
							</c:if>
							<c:if test="${i!=currMin}">		
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
						<f:input path="${status.expression}" value="<%=mDate%>" cssClass="box_1" cssStyle="width:70px;height:14px" />
						</s:bind>
					</div>
					<div class="btn_1" style="display: none;">
						<a  id="endBt"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_calendar.gif" alt="" class="btn_1"></a>
					</div>
					<div class="select_1">
						<f:select path="end_hour" cssStyle="width:50px;height:18px">
						<c:forEach begin="0" end="23" var="i" step="1">
							<c:if test="${i==currHour}">		
								<f:option value='${i}' label='${i}시' selected='selected'/>
							</c:if>
							<c:if test="${i!=currHour}">		
								<f:option value='${i}' label='${i}시' />
							</c:if>
						</c:forEach>
						</f:select>
					</div>
					<div class="select_2">
						<f:select path="end_min" cssStyle="width:50px;height:18px">
						<c:forEach begin="0" end="59" var="i" step="1">
							<c:if test="${i==currMin}">		
								<f:option value='${i}' label='${i}분' selected='selected'/>
							</c:if>
							<c:if test="${i!=currMin}">		
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
					<div class="btn_2">
						<a  onClick="srchBandWidth();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_inquiry.gif" alt=""></a>
					</div>
					<div class="txt_1" id="bandwidthDv" style="display:none"><label id="bandwidthLb"></label></div>
					<div class="inputbox_2">
					<s:bind path="desired">
					<f:input path="${status.expression}" value="" cssStyle="width:260px;" numberonly="true"/>
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
					<f:input path="description" value="" cssStyle="width:500px;" />
				</td>
			</tr>
		</table>
		
		<br /><br />
			
		<!-- 선택항목 -->
		<div id="optDisplay" style="font-weight:bold; font-size:15px; color:#3f81b0;" onclick="chgDisplay();">Optional data (+)</div>
		<table cellspacing="0" class="tb_request" id="optData" style="display:none;">
			<colgroup>
				<col class="col">
				<col width="280px">
				<col class="col">
				<col width="280px">
			</colgroup>			
			<tr>
				<th align="left" valign="middle" scope="row"><label for="start_date"><s:message code='com.netmng.text.network.symmetricPath' /></label></th>
				<td align="left" valign="middle">
					<f:select path="symmetricPath" cssStyle="width:50px;height:18px">
						<option value="1">true</option>
						<option value="0">false</option>
					</f:select>
				</td>
				<th align="left" valign="middle" scope="row"><label for="end_date"><s:message code='com.netmng.text.network.directionality' /></label></th>
				<td align="left" valign="middle">
					<f:select path="directionality" cssStyle="width:200px;height:18px">
						<option value="<%=DirectionalityType.BIDIRECTIONAL.value()%>"><%=DirectionalityType.BIDIRECTIONAL.value()%></option>
						<option value="<%=DirectionalityType.UNIDIRECTIONAL.value()%>"><%=DirectionalityType.UNIDIRECTIONAL.value()%></option>
					</f:select>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="start_date"><s:message code='com.netmng.text.network.mtu' /></label></th>
				<td align="left" valign="middle">
					<f:input path="mtu" value="" cssStyle="width:200px;" numberonly="true"/>
				</td>
				<th align="left" valign="middle" scope="row"><label for="end_date"><s:message code='com.netmng.text.network.burstsize' /></label></th>
				<td align="left" valign="middle">
					<f:input path="burstsize" value="" cssStyle="width:200px;" numberonly="true"/>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row">
					<label for="start_date">
						<s:message code='com.netmng.text.network.ero' />
					</label>
				</th>
				<td align="left" valign="middle" colspan="3">
					<a href="#none" onclick="eroAdd();">add ero</a><br />
					<table cellspacing="0" class="tb_l" style="width:650px;">
						<colgroup>
							<col width="550px">
							<col width="50px">
							<col class="col">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">NetwrokId > LocalId</th>
								<th scope="col">Order</th>
								<th scope="col">Delete</th>
							</tr>
						</thead>
						<textarea id="TEMPLAT_ERO" style="display:none;">
							<tr>
								<td align="left" valign="middle" >				
									<select name="ero_network_local" id="ero_network_local" style="width:450px;height:18px">
										<c:forEach items="${stpLocalList}" varStatus="status" var="stpLocalDTO">
											<option value="${stpLocalDTO.network_id}|${stpLocalDTO.local_id}">${stpLocalDTO.network_nm} > ${stpLocalDTO.local_nm}</option>
										</c:forEach>
									</select>
					 			</td>
								<td align="left" valign="middle">
									<input type="text" name="ero_order" id="ero_order" value="" style="width:25px;" numberonly="true" />
								</td>
								<td align="left" valign="middle">
									<a href="#none" onclick="eroDel(this);">delete</a>
								</td>
							</tr>
						</textarea>										
						<tbody id="TBODY_ERO">
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
					<input type="radio" name="simple_mode" value="used" checked>used &nbsp;
					<input type="radio" name="simple_mode" value="unused">unused
				</td>
			</tr>
			
			</div>
			
			</tbody>
			</table>
			<div class="btn_area_request">
				<div class="btn_3" style="width:760px; text-align:right;"> 
					<a onClick="confirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_reserve.gif" alt="예약"></a>
					<a onClick="cancel();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_cancel.gif" alt="취소"></a>
				</div>
				<%-- <div class="btn_4"> 
					<a  onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_cancel.gif" alt="취소"></a>
				</div> --%>
				<%-- <div class="btn_16"> 
					<a  onClick="troubleAsk();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/btn_report_1.gif" alt="장애신고"></a>
				</div> --%>
			</div>
			
		</f:form>
		

	</div>
	<!-- request_2 //-->

	<iframe id="_proc" name="_proc" frameborder="0" scrolling="no" width="0" height="0" ></iframe>
	<div id="confirmDv" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.network.reservation' /></b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="save();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
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
