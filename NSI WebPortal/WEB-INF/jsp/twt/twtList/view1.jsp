<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>
<%@	taglib prefix="page" 	uri="/WEB-INF/tld/PagingTag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script src="/twt/js/jquery-1.7.1.js" type="text/javascript"></script>
<script type="text/javascript">
	function goSearch() {
		$("#srchFrm").find("#pageIdx").val("1");
		$("#srchFrm").submit();
	}
	function goPage(pageIdx){
		$("#srchFrm").find("#pageIdx").val(pageIdx);
		$("#srchFrm").submit();
	}
</script>
</head>
<body>
	<f:form name="srchFrm" id="srchFrm" action="/twt/twtList/view1.do" modelAttribute="paramClass">
	<div>
		<s:bind path="paramClass.srchTxt">
		<label for="srchTxt">검색어 : </label>	
			<f:input path="${status.expression}" />	
		</s:bind>
		<s:bind path="paramClass.createdAtBegin">
		<label for="createdAtBegin">시작 : </label>
			<f:input path="${status.expression}" />
		</s:bind>
		<label for="createdAtEnd">종료 : </label>
			<f:input path="createdAtEnd" /> 
			<!-- <input type="text" id="createdAtEnd" name="createdAtEnd" value="<s:eval expression="paramClass.createdAtEnd" />" /> -->	
		<input type="button" value="검색" onClick="goSearch();" />
		<p>
		<%@ include file="/WEB-INF/jsp/com/pageParam.jsp"%>
		</p>
	</div>
	<table>
	<tr>
		<td>아뒤</td>
		<td>작성일자</td>
		<td>내용</td>
	</tr>
	<c:forEach items="${twtList}" varStatus="status" var="twt">
	<tr>
		<td>${twt.searresd_twt_from_user}</td>
		<td>${twt.searresd_twt_created_at}</td>
		<td>${twt.searresd_twt_htmlText}</td>
	</tr>
	</c:forEach>
	</table>
	<page:pagingTag totRow="${paramClass.totRow}" row="${paramClass.row}" pageIdx="${paramClass.pageIdx}" func="goPage" cxtPath="" />
	</f:form>
	<f:form name="viewFrm" id="viewFrm" action="/twtList/view1/view">
		<%@ include file="/WEB-INF/jsp/com/pageParamH.jsp"%>
	</f:form>
</body>
</html>