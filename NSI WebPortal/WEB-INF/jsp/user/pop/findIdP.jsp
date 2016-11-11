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
<div class="idDC_member_tit"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/find_id.gif" alt="로그인"></div>
<a onClick="closePop();" class="bClose"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_close.gif" alt="닫기"></a>
<c:if test='${mode == "findId"}'>
<form class="infor_idform">
	<div class="infor_id">
		<c:if test='${result == true}'>
		<br><p><span><b><s:message code='com.netmng.text.login.msg4' /></b></span><br />
		<div class="box_content_info">
			<div class="txtLine01"><c:out value="${user.id}" /></div>
		</div>
		</c:if>
		<c:if test='${result != true}'>
		<br><p><span><b><s:message code='com.netmng.text.login.msg3' /></b></span><br />
		<a href="${pageContext.request.contextPath}/user/pop/findIdF.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_id.gif" alt="아이디찾기" ></a>
		</c:if>
	</div>
</form>
</c:if>