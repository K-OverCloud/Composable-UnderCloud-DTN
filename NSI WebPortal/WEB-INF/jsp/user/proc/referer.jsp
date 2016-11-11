<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>   
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.1.js"></script>
<script type="text/javascript">	
	$(document).ready(function() {
		$("#paramMap").submit();
	});
</script>
<f:form name="paramMap" id="paramMap" action="${referer}" modelAttribute="paramMap" method="POST">
<c:forEach var="item" items="${paramMap}">
	<c:forEach var="itemValue" items="${item.value}">
		<input type="hidden" name="${item.key}" value="${itemValue}" />
	</c:forEach>
</c:forEach>
</f:form>