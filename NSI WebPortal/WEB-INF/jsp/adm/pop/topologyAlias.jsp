<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List"%> 
<%@ page import="com.netmng.vo.StpLocal"%> 

<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>    
<%@	taglib prefix="page" 	uri="/WEB-INF/tld/PagingTag.tld"%>

<style type="text/css">
#topologyListTb tbody tr:hover
{
	background-color: LemonChiffon;
}
#siteListTb tbody tr:hover
{
	background-color: LemonChiffon;
}
</style>  
<script type="text/javascript">
	function goList() {
		opener.goList();
		window.close();
	}
	
	function aliasConfig(){
		$("#aliasFrm").attr("action", "${pageContext.request.contextPath}/adm/topologyAliasUP.do").attr("target", "_proc");
		$("#aliasFrm").submit();
	}
</script>


<!-- content -->
<div id="content">
		
	<form name="aliasFrm" id="aliasFrm" action="${pageContext.request.contextPath}/adm/topologyAliasUP.do" method="post">
	
		<h3>▣ <s:message code='com.netmng.text.network.nsa.title.AliasConfig' /></h3>
		<div id="topologyL_1" style="width:700px; text-align:center;">
			
			<table border="0" class="tb_l" style="width:700px;">
				<colgroup>
					<col width="135px">
					<col width="*">
					<col width="280px">
				</colgroup>
				<thead>
					<tr>
						<th scipe="col" style="text-align:left;">&nbsp;</th>
						<th scipe="col">Value</th>
						<th scipe="col">Alias</th>
					</tr>
				</thead>						
				<tbody>
					<tr>
						<td style="text-align:left; font-weight:bold;">&nbsp;ProviderNSA</td>
						<td style="text-align:left;">${providerNSA.nsa_addr}</td>
						<td><f:input path="providerNSA.nsa_nm" value="" cssStyle="width:260px;"/></td>
						<input type="hidden" name="nsa_addr" id="nsa_addr" value="${providerNSA.nsa_addr}">
					</tr>	
					<tr>
						<td style="text-align:left; font-weight:bold;">&nbsp;STP Network Id</td>
						<td style="text-align:left;">${stpNetwork.network_id}</td>
						<td><f:input path="stpNetwork.network_nm" value="" cssStyle="width:260px;"/></td>
						<input type="hidden" name="network_id" id="network_id" value="${stpNetwork.network_id}">
					</tr>
<%
			List<StpLocal> listStpLocal = (List<StpLocal>)request.getAttribute("stpLocalList");
			for(int i=0; i<listStpLocal.size(); i++){
				StpLocal stpLocal = (StpLocal)listStpLocal.get(i);
%>						
					<tr>
<%					if(i==0){ %>						
						<td style="text-align:left; font-weight:bold;" rowspan="<%=listStpLocal.size()%>">&nbsp;STP Local Id</td>
<%					} %>							
						<td style="text-align:left;"><%=stpLocal.getLocal_id()%></td>
						<td><input type="text" name="local_nm" value="<%=stpLocal.getLocal_nm()%>" style="width:260px;"></td>
						<input type="hidden" name="local_id" value="<%=stpLocal.getLocal_id()%>">
					</tr>			
<%				} %>								
				</tbody>
			</table>
		</div>
		
		<br /><br /><br />
			
		<div id="btn_area_topologyL">
			<ul>
				<li class="right">
					<a href="#none" onClick="aliasConfig();">
						설정
					</a>
					&nbsp;&nbsp;
					<a href="#none" onclick="window.close();">
						취소
					</a>
				</li>
			</ul>
		</div>
	
	<%-- </f:form> --%>
	<f:form name="topologyFrm" id="topologyFrm" action="${pageContext.request.contextPath}/adm/proc/nsaInitP.do" method="GET" />
	<iframe id="_proc" name="_proc" frameborder="0" scrolling="no" width="0" height="0" ></iframe>
	<div id="nsaInitDv" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.network.nsa.init' /></b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="nsaInit();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
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