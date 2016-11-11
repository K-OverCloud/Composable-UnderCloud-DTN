<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>   
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 
<script type="text/javascript">
	function closePop() {
		location.href="${pageContext.request.contextPath}/user/pop/loginClose.do";
	}
</script>
<c:if test='${SESS_USER != null}'>
<script type="text/javascript">
	alert("<s:message code='com.netmng.exception.logout' />");
	//location.href="${pageContext.request.contextPath}/user/pop/loginClose.do";
	closePop();
</script>
</c:if>
<c:if test='${mode == "findPass"}'>
<div class="idDC_member_tit"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/find_pw.gif" alt="로그인"></div>
<a onClick="closePop();" class="bClose"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_close.gif" alt="닫기"></a>
	<form class="infor_idform">
		<div class="infor_id">
			<c:if test='${result == true}'>
			<p><span><b><s:message code='com.netmng.text.login.msg5' /></b></span></p>
			<div class="box_content_info">
				<div class="txtLine01"><c:out value="${user.email}" /></div>
			</div>
			</c:if>
			<c:if test='${result != true}'>
			<p><span><b><s:message code='com.netmng.text.login.msg3' /></b></span></p><br />
			<a href="${pageContext.request.contextPath}/user/pop/findPassF.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_pw.gif" alt="비밀번호찾기" ></a>
			</c:if>
		</div>
	</form>
</c:if>