<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.1.js"></script>

<c:if test="${mode == 'APPROVE'}">
<script type="text/javascript">
	alert("<s:message code='com.netmng.ctrl.adm.user.approve' />");
	parent.goList();
</script>
</c:if>

<c:if test="${mode == 'DROP'}">
<script type="text/javascript">
	alert("<s:message code='com.netmng.ctrl.adm.user.drop' />");
	parent.goList();
</script>
</c:if>

<c:if test="${mode == 'insert'}">
<script type="text/javascript">
	alert("<s:message code='com.netmng.ctrl.adm.user.insert' />");
	parent.goView(<c:out value="${seq}" />);
</script>
</c:if>

<c:if test="${mode == 'update'}">
<script type="text/javascript">
	alert("<s:message code='com.netmng.ctrl.user.user.update' />");
	parent.goView(<c:out value="${seq}" />);
</script>
</c:if>

<c:if test="${mode == 'passU'}">
<script type="text/javascript">
	alert("<s:message code='com.netmng.ctrl.user.user.update.pass' />");
	parent.goView(<c:out value="${seq}" />);
	parent.disablePop();
</script>
</c:if>

passU

</head>
<body>
</body>
</html>