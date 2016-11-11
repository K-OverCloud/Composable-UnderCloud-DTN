<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tiles" 	uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	<tiles:getAsString name="title" />
</title>
<tiles:insertAttribute name="script" />
</head>
<body>
<div class="memberPop" style="width:458px;display:block" id="div_name">
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="body" />
</div>
</body>
</html>