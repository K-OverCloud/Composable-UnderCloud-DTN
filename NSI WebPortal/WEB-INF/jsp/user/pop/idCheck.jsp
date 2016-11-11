<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>   
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 
<html>
<script type="text/javascript">
	function idChk() {
		$("#idCkFrm").attr("action", "${pageContext.request.contextPath}/user/pop/idCheck.do");
		$("#idCkFrm").submit();
	}
	
	function closePop() {
		parent.disablePop();
	}
	
	function idApprove() {
		$("#userDTO", parent.document).find("#idFlag").val("1");
		$("#userDTO", parent.document).find("#id").val($("#idCkFrm").find("#id").val());
		parent.disablePop();
	}
</script>
<body>
<div class="idDC_member_tit"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/tit_idDC.gif" alt="아이디중복확인"></div>
<a class="bClose" onClick="closePop();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_close.gif" alt="닫기"></a>
	<f:form name="idCkFrm" id="idCkFrm" action="${pageContext.request.contextPath}/user/pop/idCheck.do" method="GET" class="idDCform">
	<div class="idDC_member">	
		<p><span><b><s:message code='com.netmng.text.join.msg1' /></b></span></p>
		<input type="text" id="id" name="id" value="<c:out value="${id}" />" title="아이디 입력" class="ipBasic" style="width:130px;" />
		<a onFocus="this.blur()" class="ml10" onClick="idChk();">
			<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_id_check.gif" alt="아이디 중복체크">
		</a>
		<c:if test="${flag == false}">
		<div class="box_content">	
			<div class="txtLine01">
				<span><c:out value="${id}" /></span>은(는) <s:message code='com.netmng.text.join.msg2' />
			</div>
		</div>
		</c:if>
		<c:if test="${flag == true}">
		<div class="box_content">	
			<div class="txtLine01">
				<span><c:out value="${id}" /></span>은(는) <s:message code='com.netmng.text.join.msg3' />
			</div>
		</div>
		</c:if>
	</div>	
	</f:form>
	<c:if test="${flag == true}">
	<div class="btn_idUse">
		<a onClick="idApprove();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_iduse.gif" alt="아이디 적용"></a>
	</div>
	</c:if>
</body>
</html>