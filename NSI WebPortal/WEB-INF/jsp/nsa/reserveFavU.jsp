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
		/* $("#reservationDTO").find("#start_date").mask("9999.99.99");
		$("#reservationDTO").find("#end_date").mask("9999.99.99"); */
		$("#reservationDTO").find("#host_ip_str").mask("___.___.___.___");
		$("#reservationDTO").find("#dest_ip_str").mask("___.___.___.___");
		/* $("#reservationDTO").find("#start_date").datepick({showOnFocus: false, showTrigger: '#startBt', dateFormat: 'yyyy.mm.dd'});
		$("#reservationDTO").find("#end_date").datepick({showOnFocus: false, showTrigger: '#endBt', dateFormat: 'yyyy.mm.dd'}); */
		
		chgProviderNsa();
		chgNetworkId("source_network_id", "source_local_id", "sourceVLAN");
		chgNetworkId("dest_network_id", "dest_local_id", "destVLAN");
		
		$("#source_local_id > option[value='${reservationFav.sourceLocal.local_id}']").attr("selected", "true");
		$("#dest_local_id > option[value='${reservationFav.destLocal.local_id}']").attr("selected", "true"); 
		
		$("#reservationDTO").find("#host_ip_str").val(chgIpMaskInitVal("${reservationFav.host_ip}"));
		$("#reservationDTO").find("#dest_ip_str").val(chgIpMaskInitVal("${reservationFav.dest_ip}"));
	});
	
	$(function(){
		$(document).on("keyup", "input:text[numberOnly]", function() {$(this).val( $(this).val().replace(/[^0-9]/gi,"") );});
		$(document).on("keyup", "input:text[datetimeOnly]", function() {$(this).val( $(this).val().replace(/[^0-9:\-]/gi,"") );});
	});
	
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
		//chgLocalId(s1, s2, s3);
	}
	
	/* function chgLocalId(s1, s2, s3){
		var selNetworkId = $("#reservationDTO").find("#"+s1+" option:selected").val();
		var selLocalId = $("#reservationDTO").find("#"+s2+" option:selected").val();
		var obj = $("#reservationDTO").find("#"+s3);
		obj.find("option").remove();
		<c:forEach items="${stpVlanList}" varStatus="status" var="stpVlanDTO">
			if(selNetworkId=="${stpVlanDTO.network_id}" && selLocalId=="${stpVlanDTO.local_id}"){
				obj.append("<option value='${stpVlanDTO.vlan_seq}'>${stpVlanDTO.vlan_seq}</option>");
			}
		</c:forEach>
	} */

	function confirm() {
		/* window.location.hash = "top"; */
		window.scrollTo(0,0);
		loadConfirm($("#confirmDv"));
	}
	
	function fn_mod() { 
		if($("#state_nm").val()==""){
			alert("FavoriteName을 입력하세요.");
			return;
		}else if($("#host_ip_str").val()==""){
			alert("source IP를 입력하세요.");
			return;
		}else if($("#dest_ip_str").val()==""){
			alert("dest IP를 입력하세요.");
			return;
		}else{
			$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/reserveFavUP.do").attr("target", "_proc");
			$("#reservationDTO").find("#host_ip").val($("#reservationDTO").find("#host_ip_str").val().replace(/_/g, ''));
			$("#reservationDTO").find("#dest_ip").val($("#reservationDTO").find("#dest_ip_str").val().replace(/_/g, ''));
			//disableConfirm();
			$("#reservationDTO").submit();
		}
	}
	
	function delConfirm() {
		window.location.hash = "top";
		loadConfirm($("#delConfirmDv"));
	}
	
	function fn_del(){
		disableConfirm();
		$("#reservationDTO").attr("action", "${pageContext.request.contextPath}/nsa/proc/reserveFavDP.do").attr("target", "_proc");
		$("#reservationDTO").submit();
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
		location.href="${pageContext.request.contextPath}/nsa/reserveFavL.do";
	}
	
	function goListSeq(seq) {
		location.href="${pageContext.request.contextPath}/nsa/reserveFavL.do";
	}
	
	function goUpdateSeq(seq) {
		location.href="${pageContext.request.contextPath}/nsa/reserveFavU.do?seq=" + seq;
	}
	
	function eroAdd(){
		var row = $("#TEMPLAT_ERO").val();
		$(row).appendTo("#TBODY_ERO");
	}
	
	function eroDel(obj){
		$(obj).parent().parent().remove();
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
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_5_on.gif" width="167"> 
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
		<span class="now"><s:message code='com.netmng.menu.network5' /></span>
	</div>	
	<h2 id="request_h2">
		<%-- <img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/title_2_3.gif" alt=""> --%>
		<s:message code='com.netmng.menu.network5' />
	</h2>
	<!-- // request_1 -->
	
	<!-- request_1 //-->		
	<!-- // request_2 -->
	<!-- <div id="request_2"> -->
	<div id="notice">
	
		<f:form name="reservationDTO" id="reservationDTO" action="${pageContext.request.contextPath}/nsa/proc/reserveIP.do" commandName="reservationDTO" method="POST">
			
			<table cellspacing="0" class="tb_request">
			<colgroup>
				<col class="col">
				<col width="280px">
				<col class="col">
				<col width="280px">
			</colgroup>
			<tbody>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="${status.expression}">FavoriteName</label></th>
				<td align="left" valign="middle" colspan="3">				
					<f:input path="state_nm" value="${reservationFav.nm}" cssStyle="width:500px;" />					
	 			</td>
	 			<f:hidden path="seq" value="${reservationFav.seq}" />	
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="${status.expression}"><s:message code='com.netmng.text.network.prvNSA' /></label></th>
				<td align="left" valign="middle" colspan="3">				
					<select id="SEL_PROVIDER_NSA" style="width:500px;height:18px" onchange="chgProviderNsa();">
						<c:forEach items="${providerNSAList}" varStatus="status" var="providerNSA">
							<c:if test="${providerNSA.nsa_addr==reservationFav.providerNsa.nsa_addr}">
								<option value="${providerNSA.nsa_addr}|${providerNSA.endpoint_addr}" selected>
									${providerNSA.nsa_nm}(${providerNSA.nsa_addr})
								</option>
							</c:if>
							<c:if test="${providerNSA.nsa_addr!=reservationFav.providerNsa.nsa_addr}">
								<option value="${providerNSA.nsa_addr}|${providerNSA.endpoint_addr}">
									${providerNSA.nsa_nm}(${providerNSA.nsa_addr})
								</option>
							</c:if>
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
							<c:if test="${stpNetworkDTO.network_id==reservationFav.sourceNetwork.network_id}">
								<option value="${stpNetworkDTO.network_id}" selected>
									${stpNetworkDTO.network_nm}
								</option>
							</c:if>
							<c:if test="${stpNetworkDTO.network_id!=reservationFav.sourceNetwork.network_id}">
								<option value="${stpNetworkDTO.network_id}">
									${stpNetworkDTO.network_nm}
								</option>
							</c:if>
						</c:forEach>
					</f:select>
					<br />
					<f:select path="source_local_id" cssStyle="width:450px;height:18px" >
					</f:select>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="dest_ip_str"><s:message code='com.netmng.text.network.destSTP' /></label></th>
				<td align="left" valign="middle" colspan="3">
					<f:select path="dest_network_id" cssStyle="width:450px;height:18px" onchange="chgNetworkId('dest_network_id', 'dest_local_id', 'destVLAN')">
						<c:forEach items="${stpNetworkList}" varStatus="status" var="stpNetworkDTO">
							<c:if test="${stpNetworkDTO.network_id==reservationFav.destNetwork.network_id}">
								<option value="${stpNetworkDTO.network_id}" selected>
									${stpNetworkDTO.network_nm}
								</option>
							</c:if>
							<c:if test="${stpNetworkDTO.network_id!=reservationFav.destNetwork.network_id}">
								<option value="${stpNetworkDTO.network_id}">
									${stpNetworkDTO.network_nm}
								</option>
							</c:if>
						</c:forEach>
					</f:select>
					<br />
					<f:select path="dest_local_id" cssStyle="width:450px;height:18px" >
					</f:select>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="host_ip_str"><s:message code='com.netmng.text.network.hostIP' /></label></th>
				<td align="left" valign="middle">
					<s:bind path="host_ip">
						<f:input path="host_ip_str" value="${reservationFav.host_ip}" cssStyle="width:100px;" />
						<f:hidden path="${status.expression}" value="" />
					</s:bind>
				</td>
				<th align="left" valign="middle" scope="row"><label for="dest_ip_str"><s:message code='com.netmng.text.network.destinationIP' /></label></th>
				<td align="left" valign="middle">
					<s:bind path="dest_ip">
						<f:input path="dest_ip_str" value="${reservationFav.dest_ip}" cssStyle="width:100px;" />
						<f:hidden path="${status.expression}" value="" />
					</s:bind>
				</td>
			</tr>
			</tbody>
			</table>
			
			<div class="btn_area2">
				<div class="btn_5">
					<a href="#none" onclick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_list.gif" alt="목록"></a>
				</div>
				<div class="btn_6">
					<a href="#none" onclick="delConfirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_delete.gif" alt="삭제"></a>
				</div>
				<div class="btn_7">
					<a href="#none" onclick="fn_mod();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_modify.gif" alt="수정"></a>
				</div>
			</div>
			
		</f:form>
		

	</div>
	<!-- request_2 //-->

	<iframe id="_proc" name="_proc" frameborder="0" scrolling="no" width="0" height="0" ></iframe>
	
	<div id="delConfirmDv" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.delete' /></b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="fn_del();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
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
