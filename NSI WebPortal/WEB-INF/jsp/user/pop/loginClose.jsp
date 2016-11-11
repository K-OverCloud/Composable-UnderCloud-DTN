<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>   
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 
<c:if test='${SESS_USER != null}'>
<script type="text/javascript">
	$("#headerFrm", parent.document).find("#sessInfoDv").find("#memberTxt").text('<c:out value="${SESS_USER.id}" />(<c:out value="${SESS_USER.name}" /> 님)');
	$("#headerFrm", parent.document).find("#sessInfoDv").find("#memberImg").attr('src', '${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/user_type_${SESS_USER.grade.name}.png');
	$("#headerFrm", parent.document).find("#logOutDv").hide();
	$("#headerFrm", parent.document).find("#logInDv").show();
	$("#headerFrm", parent.document).find("#sessInfoDv").show();
</script>
<c:if test="${sessionScope['SESS_USER'].grade_seq == '1'}">
<script type="text/javascript">
	parent.window.location.reload(true);
	//$("#FlashID2 param[name=movie]", parent.document).attr("value", "${pageContext.request.contextPath}/swf/${pageContext.response.locale.language}/menu1_adm.swf");
	//$("#FlashID2", parent.document).find("#nonIe").attr("data", "${pageContext.request.contextPath}/swf/${pageContext.response.locale.language}/menu1_adm.swf");
</script>
</c:if>
</c:if>
<script type="text/javascript">
	function closePop() {
		parent.disablePop();
	}
	closePop();
</script>