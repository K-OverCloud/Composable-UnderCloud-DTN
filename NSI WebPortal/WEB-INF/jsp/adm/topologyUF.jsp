<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>  
<script type="text/javascript">
	jQuery(function($){
		$.mask.definitions['_']='[_0-9]';
	});
	var $addFormVar 	= null;
	$(document).ready(function() {
		$("#interfaceInfoDTOList").find("#ip_str").mask("___.___.___.___");
		$("#interfaceInfoDTOList").find("#primary_ip_str").mask("___.___.___.___");
		$("#interfaceInfoDTOList").find("#secondary_ip_str").mask("___.___.___.___");
		$("#interfaceInfoDTOList").find("#link_primary_ip_str").mask("___.___.___.___");
		$("#interfaceInfoDTOList").find("#link_secondary_ip_str").mask("___.___.___.___");
		$addFormVar 	= $("#clone_dv").clone(true);
	});
	
	function del(obj) {
		$(obj).parent().parent().parent().parent().parent().remove();
	}
	
	function add() {
		$("#interfaceInfoDTOList").find("#interfaceList_dv").append($addFormVar.html());
		$("#interfaceInfoDTOList").find("#ip_str").mask("___.___.___.___");
		$("#interfaceInfoDTOList").find("#primary_ip_str").mask("___.___.___.___");
		$("#interfaceInfoDTOList").find("#secondary_ip_str").mask("___.___.___.___");
		$("#interfaceInfoDTOList").find("#link_primary_ip_str").mask("___.___.___.___");
		$("#interfaceInfoDTOList").find("#link_secondary_ip_str").mask("___.___.___.___");
	}

	function confirm() {
		validateList = new Array();
		i = 0;
		$("#interfaceInfoDTOList").find("#interfaceList_dv table").each(
			function() {
				$(this).find("#urn").val($(this).find("#urn_str").val());
				$(this).find("#ip").val($(this).find("#ip_str").val().replace(/_/g, ''));
				$(this).find("#primary_ip").val($(this).find("#primary_ip_str").val().replace(/_/g, ''));
				$(this).find("#secondary_ip").val($(this).find("#secondary_ip_str").val().replace(/_/g, ''));
				$(this).find("#link_primary_ip").val($(this).find("#link_primary_ip_str").val().replace(/_/g, ''));
				$(this).find("#link_secondary_ip").val($(this).find("#link_secondary_ip_str").val().replace(/_/g, ''));
				validateList[i] = new Array();
				validateList[i]["site_name"] = $(this).find("#site_name").val();
				validateList[i]["urn"] = $(this).find("#urn").val();
				validateList[i]["ip"] = $(this).find("#ip").val();
				validateList[i]["router_type_seq"] = $(this).find("#router_type_seq").val();
				validateList[i]["primary_ip"] = $(this).find("#primary_ip").val();
				validateList[i]["primary_bw"] = $(this).find("#primary_bw").val();
				validateList[i]["secondary_ip"] = $(this).find("#secondary_ip").val();
				validateList[i]["secondary_bw"] = $(this).find("#secondary_bw").val();
				validateList[i]["link_primary_ip"] = $(this).find("#link_primary_ip").val();
				validateList[i]["link_secondary_ip"] = $(this).find("#link_secondary_ip").val();
				i++;
			}
		);
		flag = true;
		
		// 유효성검사(형식)
		for(i=0; i<validateList.length; i++) {
			if(cf_trim(validateList[i]["site_name"]).length <= 0
			|| validateList[i]["site_name"].length > 20) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.site_name' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#site_name").eq(i).focus();
				return false;
			} else if(cf_trim(validateList[i]["urn"]).length <= 0) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.urn' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#urn_str").eq(i).focus();
				return false;
			} else if(!getRegexStr("ip").test(validateList[i]["ip"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.ip' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#ip_str").eq(i).focus();
				return false;
			} else if(!getRegexStr("ip").test(validateList[i]["primary_ip"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.primary_ip' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#primary_ip_str").eq(i).focus();
				return false;
			} else if(validateList[i]["primary_bw"].length == 0 || getRegexStr("amount").test(validateList[i]["primary_bw"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.primary_bw' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#primary_bw").eq(i).focus();
				return false;
			} else if(!getRegexStr("ip").test(validateList[i]["secondary_ip"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.secondary_ip' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#secondary_ip_str").eq(i).focus();
				return false;
			} else if(validateList[i]["secondary_bw"].length == 0 || getRegexStr("amount").test(validateList[i]["secondary_bw"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.secondary_bw' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#secondary_bw").eq(i).focus();
				return false;
			} else if(!getRegexStr("ip").test(validateList[i]["link_primary_ip"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.link_primary_ip' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#link_primary_ip_str").eq(i).focus();
				return false;
			} else if(!getRegexStr("ip").test(validateList[i]["link_secondary_ip"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.link_secondary_ip' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#link_secondary_ip_str").eq(i).focus();
				return false;
			};
		};
		// 유효성검사(중복)
		for(i=0; i<validateList.length; i++) {

			if("<s:eval expression="@netmngConfig['nsa.head.router.ip']" />" == cf_trim(validateList[i]["ip"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.ip.duplicated' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#ip_str").eq(i).focus();
				return false;
			} else if("<s:eval expression="@netmngConfig['nsa.head.router.ip']" />" == cf_trim(validateList[i]["primary_ip"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.ip.duplicated' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#primary_ip_str").eq(i).focus();
				return false;
			} else if("<s:eval expression="@netmngConfig['nsa.head.router.ip']" />" == cf_trim(validateList[i]["secondary_ip"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.ip.duplicated' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#secondary_ip_str").eq(i).focus();
				return false;
			} else if("<s:eval expression="@netmngConfig['nsa.head.router.ip']" />" == cf_trim(validateList[i]["link_primary_ip"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.ip.duplicated' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#link_primary_ip_str").eq(i).focus();
				return false;
			} else if("<s:eval expression="@netmngConfig['nsa.head.router.ip']" />" == cf_trim(validateList[i]["link_secondary_ip"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.ip.duplicated' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#link_secondary_ip_str").eq(i).focus();
				return false;
			} else if("<s:eval expression="@netmngConfig['nsa.head.router.site_name']" />" == cf_trim(validateList[i]["site_name"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.site_name.duplicated' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#site_name").eq(i).focus();
				return false;
			} else if("<s:eval expression="@netmngConfig['nsa.head.router.urn']" />" == "<s:eval expression="@netmngConfig['nsa.urn']" />" + cf_trim(validateList[i]["urn"])) {
				alert("<s:message code='com.netmng.param.adm.InterfaceInfoDTOList.urn.duplicated' />");
				flag = false;
				$("#interfaceInfoDTOList").find("#urn_str").eq(i).focus();
				return false;
			};
			if(i==0) {
				if(cf_trim(validateList[i]["ip"]) == cf_trim(validateList[i]["primary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#primary_ip_str").eq(i).focus();
					return false;
				} else if(cf_trim(validateList[i]["ip"]) == cf_trim(validateList[i]["secondary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#secondary_ip_str").eq(i).focus();
					return false;
				} else if(cf_trim(validateList[i]["ip"]) == cf_trim(validateList[i]["link_primary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#link_primary_ip_str").eq(i).focus();
					return false;
				} else if(cf_trim(validateList[i]["ip"]) == cf_trim(validateList[i]["link_secondary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#link_secondary_ip_str").eq(i).focus();
					return false;
				};
			};
			
			for(j=i+1; j<validateList.length; j++) {
				if(cf_trim(validateList[i]["site_name"]) == cf_trim(validateList[j]["site_name"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#site_name").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["urn"]) == cf_trim(validateList[j]["urn"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#urn_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["ip"]) == cf_trim(validateList[j]["ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["ip"]) == cf_trim(validateList[j]["primary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#primary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["ip"]) == cf_trim(validateList[j]["secondary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#secondary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["ip"]) == cf_trim(validateList[j]["link_primary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#link_primary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["ip"]) == cf_trim(validateList[j]["link_secondary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#link_secondary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["primary_ip"]) == cf_trim(validateList[j]["ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["primary_ip"]) == cf_trim(validateList[j]["primary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#primary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["primary_ip"]) == cf_trim(validateList[j]["secondary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#secondary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["primary_ip"]) == cf_trim(validateList[j]["link_primary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#link_primary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["primary_ip"]) == cf_trim(validateList[j]["link_secondary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#link_secondary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["secondary_ip"]) == cf_trim(validateList[j]["ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["secondary_ip"]) == cf_trim(validateList[j]["primary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#primary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["secondary_ip"]) == cf_trim(validateList[j]["secondary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#secondary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["secondary_ip"]) == cf_trim(validateList[j]["link_primary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#link_primary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["secondary_ip"]) == cf_trim(validateList[j]["link_secondary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#link_secondary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["link_primary_ip"]) == cf_trim(validateList[j]["ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["link_primary_ip"]) == cf_trim(validateList[j]["primary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#primary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["link_primary_ip"]) == cf_trim(validateList[j]["secondary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#secondary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["link_primary_ip"]) == cf_trim(validateList[j]["link_primary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#link_primary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["link_primary_ip"]) == cf_trim(validateList[j]["link_secondary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#link_secondary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["link_secondary_ip"]) == cf_trim(validateList[j]["ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["link_secondary_ip"]) == cf_trim(validateList[j]["primary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#primary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["link_secondary_ip"]) == cf_trim(validateList[j]["secondary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#secondary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["link_secondary_ip"]) == cf_trim(validateList[j]["link_primary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#link_primary_ip_str").eq(j).focus();
					return false;
				} else if(cf_trim(validateList[i]["link_secondary_ip"]) == cf_trim(validateList[j]["link_secondary_ip"])) {
					alert("<s:message code='com.netmng.exception.duplicated' />");
					flag = false;
					$("#interfaceInfoDTOList").find("#link_secondary_ip_str").eq(j).focus();
					return false;
				};
			};
		}
		if(flag)
			loadConfirm($("#confirmDv"));
		
	}
	
	function save() { 
		$("#interfaceInfoDTOList").attr("action", "${pageContext.request.contextPath}/adm/proc/topologyUP.do").attr("target", "_proc");
		disableConfirm();
		$("#interfaceInfoDTOList").submit();
	}
	
	function goList() {
		location.href="${pageContext.request.contextPath}/adm/topologyL.do";
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
		<span class="now"><s:message code='com.netmng.menu.admin31' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/title3_3.gif" alt=""></h2>
	<!-- tab_area -->
	<div id="tab_area">
		<div class="tab_Monitor_1"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_3_1_on.gif" > </div>
		<div class="tab_Monitor_2">
			<a href="${pageContext.request.contextPath}/adm/eventL.do">
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_3_2_off.gif" >
			</a>
		</div>
		<div class="tab_Monitor_3">
			<a href="${pageContext.request.contextPath}/adm/errorL.do">
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_3_3_off.gif">
			</a>
		</div>
	</div>
	<!-- //tab_area -->
	
	<f:form name="interfaceInfoDTOList" id="interfaceInfoDTOList" modelAttribute="interfaceInfoDTOList" method="POST">
		<div id="interfaceList_dv">
			<h3><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/title_3_3_2.gif" alt=""></h3>
			<div class="btn_topology_add">
				<a onClick="add();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/btn_topology_set.gif" alt="토폴로지 추가"></a>
			</div>
			<br />
			<c:forEach items="${interfaceInfoDTOList}" varStatus="status" var="interfaceInfoDTO">
			<div>
				<table cellspacing="0" class="tb_set1">
				<colgroup>
					<col class="col">
				</colgroup>
				<tbody>
				<tr>
					<th width="28%" align="left" valign="middle" class="top1" scope="row"><label for="site_name">Site Name</label></th>
					<td width="49%" align="left" valign="middle" class="top2">
						<input type="text" id="site_name" name="site_name" value="<c:out value="${interfaceInfoDTO.routerDTO.site_name}" />" />
					</td>
					<td width="23%" class="top2_btn">&nbsp;</td>
				</tr>
				<tr>
					<th align="left" valign="middle" scope="row"><label for="urn_str">urn</label></th>
					<td align="left" valign="middle">
						<div class="userV_counts">
							<div class="urn_1">
								<s:eval expression="@netmngConfig['nsa.urn']" /><label><s:eval expression="interfaceInfoDTO.routerDTO.urn.replaceAll(@netmngConfig['nsa.urn'], '')"/></label>
							</div>
							<input type="hidden" id="urn_str" name="urn_str" value="<s:eval expression="interfaceInfoDTO.routerDTO.urn.replaceAll(@netmngConfig['nsa.urn'], '')"/>" readOnly="readOnly" />
							<input type="hidden" id="urn" name="urn" value="<s:eval expression="interfaceInfoDTO.routerDTO.urn.replaceAll(@netmngConfig['nsa.urn'], '')"/>" readOnly="readOnly" />
						</div>
					</td>
					<td width="23%" class="top2_btn_area">&nbsp;</td>
				</tr>
				<tr>
					<th align="left" valign="middle" scope="row"><label for="ip_str">Router IP</label></th>
					<td align="left" valign="middle">
						<span class="top2">
						<input type="text" id="ip_str" name="ip_str" 
							value="<s:eval expression='new String().format("%3s.%3s.%3s.%3s", new String(interfaceInfoDTO.routerDTO.ip).split("\\.")[0], new String(interfaceInfoDTO.routerDTO.ip).split("\\.")[1], new String(interfaceInfoDTO.routerDTO.ip).split("\\.")[2], new String(interfaceInfoDTO.routerDTO.ip).split("\\.")[3]).replaceAll(" ", "_")' />" 
							class="box_1" style="width:100px;height:19px" />
						<input type="hidden" id="ip" name="ip" value="<c:out value="${interfaceInfoDTO.routerDTO.ip}" />" />
						</span>
					</td>
					<td width="23%" class="top2_btn_area">&nbsp;</td>
				</tr>
				<tr>
					<th align="left" valign="middle" scope="row"><label for="router_type_seq">Router Type</label></th>
					<td align="left" valign="middle">
						<select id="router_type_seq" name="router_type_seq" style="width:238px;height:20px">
							<c:forEach items="${routerTypeList}" var="routerType">
							<option value="<c:out value="${routerType.seq}" />"
							<c:if test="${routerType.seq == interfaceInfoDTO.routerDTO.router_type_seq}">selected</c:if>
							><c:out value="${routerType.name}" /></option>
							</c:forEach>
						</select>
					</td>
					<td width="23%" rowspan="2" class="top2_btn_area">&nbsp;</td>
				</tr>
				<tr>
					<th align="left" valign="middle" scope="row"><label for="primary_ip_str">interface 1 / <s:message code='com.netmng.text.network.bandwidth' /></label></th>
					<td align="left" valign="middle">
						<span class="top2">
							<input type="text" id="primary_ip_str" name="primary_ip_str" 
								value="<s:eval expression='new String().format("%3s.%3s.%3s.%3s", new String(interfaceInfoDTO.primary_ip).split("\\.")[0], new String(interfaceInfoDTO.primary_ip).split("\\.")[1], new String(interfaceInfoDTO.primary_ip).split("\\.")[2], new String(interfaceInfoDTO.primary_ip).split("\\.")[3]).replaceAll(" ", "_")' />"
								class="box_1" style="width:100px;height:19px" />
							<input type="hidden" id="primary_ip" name="primary_ip" value="<c:out value="${interfaceInfoDTO.primary_ip}" />" />
							&nbsp; / <input type="text" id="primary_bw" name="primary_bw" value="<c:out value="${interfaceInfoDTO.primary_bw}" />" style="width:100px;height:19px" />
						</span>
					</td>
				</tr>
				<tr>
					<th align="left" valign="middle" scope="row"><label for="secondary_ip_str">interface 2 / <s:message code='com.netmng.text.network.bandwidth' /></label></th>
					<td align="left" valign="middle">
						<span class="top2">
							<input type="text" id="secondary_ip_str" name="secondary_ip_str" 
								value="<s:eval expression='new String().format("%3s.%3s.%3s.%3s", new String(interfaceInfoDTO.secondary_ip).split("\\.")[0], new String(interfaceInfoDTO.secondary_ip).split("\\.")[1], new String(interfaceInfoDTO.secondary_ip).split("\\.")[2], new String(interfaceInfoDTO.secondary_ip).split("\\.")[3]).replaceAll(" ", "_")' />"
								class="box_1" style="width:100px;height:19px" />
							<input type="hidden" id="secondary_ip" name="secondary_ip" value="<c:out value="${interfaceInfoDTO.secondary_ip}" />" />
							&nbsp;  / <input type="text" id="secondary_bw" name="secondary_bw" value="<c:out value="${interfaceInfoDTO.secondary_bw}" />" style="width:100px;height:19px" />
						</span>
					</td>
					<td width="23%" class="top2_btn_area">&nbsp;</td>
				</tr>
				<tr>
					<th align="left" valign="middle" scope="row"><label for="link_primary_ip_str"><s:message code='com.netmng.text.network.nsa.symmetryInterface1' /></label></th>
					<td align="left" valign="middle">
						<span class="top2">
							<input type="text" id="link_primary_ip_str" name="link_primary_ip_str" 
								value="<s:eval expression='new String().format("%3s.%3s.%3s.%3s", new String(interfaceInfoDTO.link_interface.primary_ip).split("\\.")[0], new String(interfaceInfoDTO.link_interface.primary_ip).split("\\.")[1], new String(interfaceInfoDTO.link_interface.primary_ip).split("\\.")[2], new String(interfaceInfoDTO.link_interface.primary_ip).split("\\.")[3]).replaceAll(" ", "_")' />"
								class="box_1" style="width:100px;height:19px" />
							<input type="hidden" id="link_primary_ip" name="link_primary_ip" value="<c:out value="${interfaceInfoDTO.link_interface.primary_ip}" />" />
						</span>
					</td>
					<td width="23%" class="top2_btn_area">&nbsp;</td>
				</tr>
				<tr>
					<th align="left" valign="middle" scope="row"><label for="link_secondary_ip_str"><s:message code='com.netmng.text.network.nsa.symmetryInterface2' /></label></th>
					<td align="left" valign="middle">
						<span class="top2">
						<input type="text" id="link_secondary_ip_str" name="link_secondary_ip_str" 
							value="<s:eval expression='new String().format("%3s.%3s.%3s.%3s", new String(interfaceInfoDTO.link_interface.secondary_ip).split("\\.")[0], new String(interfaceInfoDTO.link_interface.secondary_ip).split("\\.")[1], new String(interfaceInfoDTO.link_interface.secondary_ip).split("\\.")[2], new String(interfaceInfoDTO.link_interface.secondary_ip).split("\\.")[3]).replaceAll(" ", "_")' />" 
							class="box_1" style="width:100px;height:19px" />
						<input type="hidden" id="link_secondary_ip" name="link_secondary_ip" value="<c:out value="${interfaceInfoDTO.link_interface.secondary_ip}" />" />
						</span>
					</td>
					<td width="23%" class="top2_btn_area">&nbsp;</td>
				</tr>
				</tbody>
				</table>	
			</div>
			</c:forEach>
		</div>
		
		<div class="btn_area6">
			<div class="btn_14">
				<a onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/btn_cancel.gif" alt="취소"></a>
			</div>
			<div class="btn_15">
				<a onClick="confirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/btn_save.gif" alt="저장"></a>
			</div>
		</div>
	</f:form>
	<iframe id="_proc" name="_proc" frameborder="0" scrolling="no" width="0" height="0" ></iframe>
	<div id="confirmDv" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.network.nsa.save' /></b></span></p>
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
	<div id="clone_dv" style="display:none;">
		<div>
			<table cellspacing="0" class="tb_set1">
			<colgroup>
				<col class="col">
			</colgroup>
			<tbody>
			<tr>
				<th width="28%" align="left" valign="middle" class="top1" scope="row"><label for="site_name">Site Name</label></th>
				<td width="49%" align="left" valign="middle" class="top2">
					<input type="text" id="site_name" name="site_name" value="" />
				</td>
				<td width="23%" class="top2_btn">&nbsp;</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="urn_str">urn</label></th>
				<td align="left" valign="middle">
					<div class="userV_counts">
						<div class="urn_1">
							<s:eval expression="@netmngConfig['nsa.urn']" />
						</div>
						<div class="urn_2">
							<input type="text" id="urn_str" name="urn_str" value="" class="box_1" style="width:120px;height:19px" />
							<input type="hidden" id="urn" name="urn" value="" />
						</div>
					</div>
				</td>
				<td width="23%" class="top2_btn_area">&nbsp;</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="ip_str">Router IP</label></th>
				<td align="left" valign="middle">
					<span class="top2">
						<input type="text" id="ip_str" name="ip_str" 
							value="" class="box_1" style="width:100px;height:19px" />
						<input type="hidden" id="ip" name="ip" value="" />
					</span>
				</td>
				<td width="23%" class="top2_btn_area">&nbsp;</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="router_type_seq">Router Type</label></th>
				<td align="left" valign="middle">
					<select id="router_type_seq" name="router_type_seq" style="width:238px;height:20px">
						<c:forEach items="${routerTypeList}" var="routerType">
						<option value="<c:out value="${routerType.seq}" />"><c:out value="${routerType.name}" /></option>
						</c:forEach>
					</select>
				</td>
				<td width="23%" rowspan="2" class="top2_btn_area">
					<div class="top2_btn">
						<a onClick="del(this);"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/btn_delete_2.gif" alt=""></a>
					</div>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="primary_ip_str">interface 1 / <s:message code='com.netmng.text.network.bandwidth' /></label></th>
				<td align="left" valign="middle">
					<span class="top2">
						<input type="text" id="primary_ip_str" name="primary_ip_str" 
							value="" class="box_1" style="width:100px;height:19px" />
						<input type="hidden" id="primary_ip" name="primary_ip" value="" />
						&nbsp; / <input type="text" id="primary_bw" name="primary_bw" value="" style="width:100px;height:19px" />
					</span>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="secondary_ip_str">interface 2 / <s:message code='com.netmng.text.network.bandwidth' /></label></th>
				<td align="left" valign="middle">
					<span class="top2">
						<input type="text" id="secondary_ip_str" name="secondary_ip_str" 
							value="" class="box_1" style="width:100px;height:19px" />
						<input type="hidden" id="secondary_ip" name="secondary_ip" value="" />
						&nbsp; / <input type="text" id="secondary_bw" name="secondary_bw" value="" style="width:100px;height:19px" />
					</span>
				</td>
				<td width="23%" class="top2_btn_area">&nbsp;</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="link_primary_ip_str"><s:message code='com.netmng.text.network.nsa.symmetryInterface1' /></label></th>
				<td align="left" valign="middle">
					<span class="top2">
						<input type="text" id="link_primary_ip_str" name="link_primary_ip_str" 
							value="" class="box_1" style="width:100px;height:19px" />
						<input type="hidden" id="link_primary_ip" name="link_primary_ip" value="" />
					</span>
				</td>
				<td width="23%" class="top2_btn_area">&nbsp;</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><label for="link_secondary_ip_str"><s:message code='com.netmng.text.network.nsa.symmetryInterface2' /></label></th>
				<td align="left" valign="middle">
					<span class="top2">
						<input type="text" id="link_secondary_ip_str" name="link_secondary_ip_str" 
							value="" style="width:100px;height:19px" />
						<input type="hidden" id="link_secondary_ip" name="link_secondary_ip" value="" />
					</span>
				</td>
				<td width="23%" class="top2_btn_area">&nbsp;</td>
			</tr>
			</tbody>
			</table>
		</div>
	</div>
</div>