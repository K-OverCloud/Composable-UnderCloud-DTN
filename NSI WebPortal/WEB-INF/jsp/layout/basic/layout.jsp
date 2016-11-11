<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tiles" 	uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head id="ctl00_Head1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	<tiles:getAsString name="title" />
</title>
<tiles:insertAttribute name="script" />
</head>
<body onLoad="MM_preloadImages('${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_2_on.gif','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_3_on.gif')">
<script type="text/javascript">
	var theForm = document.forms['aspnetForm'];
	if (!theForm) {
		theForm = document.aspnetForm;
	}
	function __doPostBack(eventTarget, eventArgument) {
		if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
			theForm.__EVENTTARGET.value = eventTarget;
			theForm.__EVENTARGUMENT.value = eventArgument;
			theForm.submit();
		}
	}
</script>
<tiles:insertAttribute name="header" />
<div id="container">
	<div id="default_container">	
		<tiles:insertAttribute name="body" />
	</div>
</div>
<div>
	<tiles:insertAttribute name="footer" />
</div>
</body>
</html>