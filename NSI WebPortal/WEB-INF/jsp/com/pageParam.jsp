<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>
<select name="row" id="row" onchange="goSearch();" style="display: none;">
	<option value="10" <c:if test="${srchParam.row == '10'}">selected</c:if>  >10라인</option>
	<option value="20" <c:if test="${srchParam.row == '20'}">selected</c:if>  >20라인</option>
	<option value="50" <c:if test="${srchParam.row == '50'}">selected</c:if>  >50라인</option>
	<option value="100" <c:if test="${srchParam.row == '100'}">selected</c:if>  >100라인</option>
	<option value="500" <c:if test="${srchParam.row == '500'}">selected</c:if>  >500라인</option>
</select>
<c:if test="${pageContext.response.locale.language == null}">
[ 총 <c:out value="${srchParam.totRow}" /> 건 ]
</c:if>
<c:if test="${pageContext.response.locale.language == ''}">
[ 총 <c:out value="${srchParam.totRow}" /> 건 ]
</c:if>
<c:if test="${pageContext.response.locale.language == 'ko'}">
[ 총 <c:out value="${srchParam.totRow}" /> 건 ]
</c:if>
<c:if test="${pageContext.response.locale.language == 'en'}">
[ total <c:out value="${srchParam.totRow}" />]
</c:if>
<c:if test="${srchParam.pageIdx != null}">
<input type="hidden" name="pageIdx" id="pageIdx" value='<c:out value="${srchParam.pageIdx}" />' />
</c:if>
<input type="hidden" name="sortKey" id="sortKey" value='<c:out value="${srchParam.sortKey}" />' />
<input type="hidden" name="sortType" id="sortType" value='<c:out value="${srchParam.sortType}" />' />