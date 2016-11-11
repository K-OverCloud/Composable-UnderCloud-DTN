<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>  
<script type="text/javascript">
	function approve() {
		$("#userDTO").attr("action", "${pageContext.request.contextPath}/adm/proc/userApproveP.do").attr("target", "_proc").submit();
	}
	function goMod(){
		$("#userDTO").attr("action", "${pageContext.request.contextPath}/adm/userUF.do").submit();
	}
	function del() {
		$("#userDTO").attr("action", "${pageContext.request.contextPath}/adm/proc/userDP.do").attr("target", "_proc").submit();
	}
	function goList() {
		$("#listFrm").attr("action", "${pageContext.request.contextPath}/adm/userL.do").submit();
	}
	function confirm() {
		loadConfirm($("#confirmDv"));
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
				<a><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_1_on.gif" alt=""></a>
			</li>
			<li><a href="${pageContext.request.contextPath}/adm/userMonitorL.do"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image22','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_2_on.gif',1)"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_2_off.gif" name="Image22" width="167" height="22" border="0"></a></li>	
			<li><a href="${pageContext.request.contextPath}/adm/topologyL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image23','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_3_on.gif',1)"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_3_off.gif" name="Image23" width="167" height="22" border="0"></a></li>	
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
		<span class="now"><s:message code='com.netmng.menu.admin1' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/title_3_1.gif" alt=""></h2>
	
	<f:form name="userDTO" id="userDTO" action="${pageContext.request.contextPath}/adm/userUF.do" modelAttribute="userDTO" method="POST">
	<!-- userV -->
	<div id="userV">	
		<table cellspacing="0" class="tb_userV">
		<colgroup>
			<col class="col">
		</colgroup>
		<tbody>
			<tr>
				<th width="30%" align="left" valign="middle" class="top1" scope="row"><s:message code='com.netmng.text.join.id' /></th>
				<td colspan="2" width="70%" align="left" valign="middle" class="top2">
					<s:bind path="userDTO.seq">
						<f:hidden path="${status.expression}" /> 
					</s:bind>
					<s:bind path="userDTO.id">
						<f:label path="${status.expression}"><c:out value="${status.value}" /></f:label>
						<f:hidden path="${status.expression}" /> 
					</s:bind>
				</td>
			</tr>
			<tr>
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.join.name' /></th>
				<td colspan="2" align="left" valign="middle">
					<s:bind path="userDTO.name">
					<f:label path="${status.expression}"><c:out value="${status.value}" /></f:label>
					</s:bind>
				</td>
			</tr>
			<!-- 
			<tr>
				<th align="left" valign="middle" scope="row">주민등록번호</th>
				<td colspan="2" align="left" valign="middle">
					<s:bind path="userDTO.jumin_no1">
					<f:label path="${status.expression}"><c:out value="${status.value}" /></f:label>
					</s:bind>
					&nbsp;-&nbsp;
					<s:bind path="userDTO.jumin_no2">
					<f:label path="${status.expression}">*******</f:label>
					</s:bind>
				</td>
			</tr>
			 -->
			<tr>
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.join.organization' /></th>
				<td colspan="2" align="left" valign="middle">
					<s:bind path="userDTO.company">
					<f:label path="${status.expression}"><c:out value="${status.value}" /></f:label>
					</s:bind>
				</td>
			</tr> 
			<tr>
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.join.email' /></th>
				<td colspan="2" align="left" valign="middle">
					<s:bind path="userDTO.email">
					<f:label path="${status.expression}"><c:out value="${status.value}" /></f:label>
					</s:bind>
				</td>
			</tr> 
			<tr class="bottom1">
				<th align="left" valign="middle" scope="row"><s:message code='com.netmng.text.join.status' /></th>
				<td colspan="2" align="left" valign="middle">
					<div class="userV_counts">
						<div class="counts_txt">
							<s:bind path="userDTO.grade.comment">
							<f:label path="${status.expression}"><c:out value="${status.value}" /></f:label>
							</s:bind>
						</div>
						<div class="counts_img">
						<c:if test="${userDTO.grade.step == '99'}">
							<a onClick="approve();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/btn_approval.gif"></a>
						</c:if>
						</div>        
					</div>
				</td> 
			</tr> 
		</tbody>
		</table>
		<div class="btn_area_user">
			<div class="btn_12">
				<a onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_list.gif" alt="목록"></a>
			</div>
			<div class="btn_13">
				<a onClick="confirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_withdrawal.gif" alt="삭제"></a>
			</div>
			<div class="btn_7">
				<a  onClick="goMod();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_modify.gif" alt="수정"></a>
			</div>
		</div>
	</div>
	<!-- // userV -->
	</f:form>
	<iframe id="_proc" name="_proc" frameborder="0" scrolling="no" width="0" height="0" ></iframe>
	<div id="confirmDv" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b>회원을 삭제하시겠습니까?</b></span></p>
				</div>
				<div class="btn_area_con">
					<div class="btn_yes">
						<a onClick="del();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
					</div>
					<div class="btn_no">
						<a onClick="disableConfirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_no.gif" alt="아니오"></a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<f:form name="listFrm" id="listFrm" action="${pageContext.request.contextPath}/adm/userL.do" method="GET" modelAttribute="srchParam">
		<input type="hidden" id="seq" name="seq" value="<c:out value='${userDTO.seq}' />" />
		<s:bind path="srchParam.mode">
		<f:hidden path="${status.expression}" />
		</s:bind>
		<s:bind path="srchParam.srchTxt">
		<f:hidden path="${status.expression}" />
		</s:bind>
		<%@ include file="/WEB-INF/jsp/com/pageParamH.jsp"%>
	</f:form>
</div>
<!-- content -->