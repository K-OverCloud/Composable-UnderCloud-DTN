<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.1.js"></script>
<title></title>
<script type="text/javascript">
	alert('${result.errMsg}');
</script>	
<c:if test='${result.objectError == true}'>
<%System.out.println("dataError: result.objectError == true"); %>
<script type="text/javascript">	
	$("#${result.result.fieldError.objectName}", parent.document).find("#${result.result.fieldError.field}").focus();
</script>
</c:if>
<c:if test='${result.objectError == false}'>
<%System.out.println("dataError: result.objectError == false"); %>
<a  onClick="history.go(-1)">이전으로</a>
</c:if>
</head>
<body>
</body>
</html>