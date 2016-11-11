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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
<c:if test="${mode == 'RESERVE'}">
<script type="text/javascript">
	//alert("<s:message code='com.netmng.ctrl.nsa.reserve.success' />");
	sleep(200);
	parent.goListSeq(<c:out value="${seq}" />);
</script>
</c:if>
<c:if test="${mode == 'PROVISION'}">
<script type="text/javascript">
	//alert("<s:message code='com.netmng.ctrl.nsa.provision.success' />");
	sleep(200);
 	parent.goListSeq(<c:out value="${seq}" />);
</script>
</c:if>
<c:if test="${mode == 'RELEASE'}">
<script type="text/javascript">
	//alert("<s:message code='com.netmng.ctrl.nsa.release.success' />");
	sleep(200);
	parent.goListSeq(<c:out value="${seq}" />);
</script>
</c:if>
<c:if test="${mode == 'TERMINATE'}">
<script type="text/javascript">
	//alert("<s:message code='com.netmng.ctrl.nsa.terminate.success' />");
	sleep(200);
	parent.goListSeq(<c:out value="${seq}" />);
</script>
</c:if>
</head>
<body>
</body>
</html>