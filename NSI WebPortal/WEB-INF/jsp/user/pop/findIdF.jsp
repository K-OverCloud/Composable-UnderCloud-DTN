<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>   
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 
<script type="text/javascript">
	$(document).ready(function() {
	});
	function closePop() {
		location.href="${pageContext.request.contextPath}/user/pop/loginClose.do";
	}
	function findId() {
		if(cf_trim($("#findIdFrm").find("#name").val()) == "") {
			alert("<s:message code='com.netmng.vo.User.name' />");
			$("#findIdFrm").find("#name").focus();
			return false;
		} else if(cf_trim($("#findIdFrm").find("#email").val()) == "") {
			alert("<s:message code='com.netmng.vo.User.email.incorrected' />");
			$("#findIdFrm").find("#email").focus();
			return false;
		}
		$("#findIdFrm").submit();
	}
</script>
<c:if test='${SESS_USER != null}'>
<script type="text/javascript">
	alert("<s:message code='com.netmng.exception.logout' />");
	//location.href="${pageContext.request.contextPath}/user/pop/loginClose.do";
	closePop();
</script>
</c:if>
<div class="idDC_member_tit"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/find_id.gif" alt="로그인"></div>
	<a onClick="closePop();" class="bClose">
	<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_close.gif" alt="닫기"></a>
	<f:form name="findIdFrm" id="findIdFrm" action="${pageContext.request.contextPath}/user/pop/findIdP.do" method="POST" class="findform">
		<div class="find_txt">
			<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/id_find_tit.gif">
		</div>
		<div class="find_id">
			<ul>
				<li><label ><s:message code='com.netmng.text.join.name' /></label><input type="text" id="name" name="name" /></li>
				<li><label ><s:message code='com.netmng.text.join.email' /></label><input type="text" id="email" name="email" /></li>
			</ul>
			<div class="btn_idFind">
				<a onClick="findId();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_check.gif" alt="확인" /></a>
			</div>
		</div>
	</f:form>