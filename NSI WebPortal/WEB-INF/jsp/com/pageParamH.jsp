<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>
<c:if test="${srchParam.row != null}">
<input type="hidden" name="row" id="row" value='<c:out value="${srchParam.row}" />' />
</c:if>
<c:if test="${srchParam.pageIdx != null}">
<input type="hidden" name="pageIdx" id="pageIdx" value='<c:out value="${srchParam.pageIdx}" />' />
</c:if>
<input type="hidden" name="sortKey" id="sortKey" value='<c:out value="${srchParam.sortKey}" />' />
<input type="hidden" name="sortType" id="sortType" value='<c:out value="${srchParam.sortType}" />' />