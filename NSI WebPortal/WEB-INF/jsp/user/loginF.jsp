<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>   
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 
<script type="text/javascript">
	alert("<s:message code='com.netmng.exception.login' />");
	location.href="${pageContext.request.contextPath}/index.do";
	//location.href="/";
	function login() {
		$("#userDTO").attr("action", "${pageContext.request.contextPath}/user/loginP.do");
		$("#userDTO").submit();
	}
	
	function indexView() {
		location.href="${pageContext.request.contextPath}/index.do";
	}
</script>
<f:form name="userDTO" id="userDTO" action="${pageContext.request.contextPath}/user/loginP.do" modelAttribute="userDTO" method="POST">
	
	<s:bind path="userDTO.id">
	<label for="${status.expression}">id : </label>
	<f:input path="${status.expression}" />
	</s:bind>
	<br />
	<s:bind path="userDTO.pass">
	<label for="${status.expression}">password : </label>
	<f:password path="${status.expression}" />
	</s:bind>
	<br />
	<input type="button" value="로그인" onClick="login();" />
</f:form>