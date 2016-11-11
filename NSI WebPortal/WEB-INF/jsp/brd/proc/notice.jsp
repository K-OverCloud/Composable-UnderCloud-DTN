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
<c:if test="${mode == 'I'}">
<script type="text/javascript">
	alert("<s:message code='com.netmng.ctrl.brd.notice.insert' />");
	$("#listFrm", parent.document).find("#seq").val("<c:out value='${seq}' />");
	parent.view();
</script>
</c:if>
<c:if test="${mode == 'U'}">
<script type="text/javascript">
	alert("<s:message code='com.netmng.ctrl.brd.notice.update' />");
	parent.view();
</script>
</c:if>
<c:if test="${mode == 'D'}">
<script type="text/javascript">
	alert("<s:message code='com.netmng.ctrl.brd.notice.delete' />");
	parent.goList();
</script>
</c:if>
</head>
<body>
</body>
</html>