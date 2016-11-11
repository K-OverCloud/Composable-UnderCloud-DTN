<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>   
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 
<script type="text/javascript">
	function login() {
		$("#userDTO").attr("action", "${pageContext.request.contextPath}/user/pop/loginP.do");
		$("#userDTO").submit();
	}
	
	function closePop() {
		location.href="${pageContext.request.contextPath}/user/pop/loginClose.do";
	}
</script>
<c:if test='${result.objectError == true}'>
<c:if test="${result.errMsg != null}">
<script type="text/javascript">
	alert('${result.errMsg}');
	closePop();
</script>
</c:if>
</c:if>
<c:if test='${SESS_USER != null}'>
<script type="text/javascript">
	location.href="${pageContext.request.contextPath}/user/pop/loginClose.do";
	closePop();
</script>
</c:if>
<div class="logo"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/login_tit.gif" alt="로그인"></div>
<a class="bClose" onClick="closePop();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_close.gif" alt="닫기"></a>
<f:form name="userDTO" id="userDTO" action="${pageContext.request.contextPath}/user/pop/loginP.do" modelAttribute="userDTO" method="POST" cssClass="loginform">
	<div class="login_idpw">
		<ul>
			<li>
				<s:bind path="userDTO.id">
				<label for="${status.expression}"><s:message code='com.netmng.text.join.id' /></label><f:input path="${status.expression}" maxlength="30" />
				</s:bind>
			</li>
			<li>
				<s:bind path="userDTO.pass">
				<label for="${status.expression}"><s:message code='com.netmng.text.join.password' /></label><f:password path="${status.expression}" maxlength="30" />
				</s:bind>
			</li>
		</ul>
		<div class="btn_login"><input type="image" src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/login_btn.gif" alt="로그인" onClick="login();"></div>
	</div>
	<div class="login_member">
	<c:if test='${result.objectError == false}'>
	<c:if test="${result.errMsg != null}">
		<p><span><c:out value="${result.errMsg}" /></span></p>
		<p>
			<span><s:message code='com.netmng.text.login.msg2' /></span>
			<span><a href="${pageContext.request.contextPath}/user/pop/findIdF.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_id.gif" alt="아이디찾기" ></a></span>
			<span><a href="${pageContext.request.contextPath}/user/pop/findPassF.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_pw.gif" alt="비밀번호찾기" ></a></span>
			<%-- <span><a href="#none;" onclick="alert('<s:message code='com.netmng.text.find.pw' />')");"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_pw.gif" alt="비밀번호찾기" ></a></span> --%>
		</p>
	</c:if>
	</c:if>
</div>
</f:form>