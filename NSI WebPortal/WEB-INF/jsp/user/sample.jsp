<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<form action="http://localhost:8080/netmng/user/myInfoUF.do" method="POST">
	<input type="text" name="aaa" value="aaa1" />
	<input type="text" name="aaa" value="aaa2" />
	<input type="text" name="aaa" value="aaa3" />
	<input type="text" name="aaa" value="aaa4" />
	<input type="text" name="bbb" value="bbb1" />
	<input type="text" name="ccc" value="ccc1" />
	<input type="text" name="ccc" value="ccc2" />
	<input type="text" name="ccc" value="ccc3" />
	<input type="text" name="ccc" value="ccc4" />
	<input type="text" name="ccc" value="ccc5" />
	<input type="submit" value="입력">
</form>
</body>
</html>