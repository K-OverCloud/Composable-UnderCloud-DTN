<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Exception 객체 사용 -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	500에러
	<!-- 
		에러페이지의 크기가 513바이트보다 작을 경우에는 익스플로러에서 제공하는 에러페이지가 뜬다네. 씨발 익스플로러다.
		하여간 익스플로러가 문제야. 진짜 익스플로러 쓰레기다. ㅠ_ㅠ
	-->
	
	에러타입 : <%= exception != null? exception.getClass().getName() : "" %><p/>
	에러메시지 : ${result.errMsg}
<%
	//StackTraceElement[] errorStack = exception.getStackTrace();
	//for(int i=0; i<errorStack.length; i++) {
	//	out.print(errorStack[i] + "<br/>");	
	//}
%>
</body>
</html>