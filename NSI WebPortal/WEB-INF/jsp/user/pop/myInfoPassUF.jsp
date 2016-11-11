<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>   
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 
<script type="text/javascript">
	function save() {
		$("#userDTO").attr("action", "${pageContext.request.contextPath}/user/proc/myInfoPassUP.do");
		//disableConfirm();
		$("#userDTO").submit();
	}

	function confirm() {
		loadConfirm($("#confirmDv"));
	}
	
	function closePop() {
		parent.disablePop();
	}
</script>
<div class="idDC_member_tit"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/pw_c.gif" alt="패스워드 수정"></div>
<a onClick="closePop();" class="bClose"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_close.gif" alt="닫기"></a>
<f:form name="userDTO" id="userDTO" action="${pageContext.request.contextPath}/user/proc/myInfoPassUP.do" modelAttribute="userDTO" method="POST" class="findform">
	<div class="change_pw">
	<ul>
		<li><label><s:message code='com.netmng.text.join.password' /></label>
			<s:bind path="userDTO.pass">
			<f:password path="${status.expression}" />
			</s:bind>
		</li>
		<li><label><s:message code='com.netmng.text.join.newPassword' /></label>
			<s:bind path="userDTO.newPass">
			<f:password path="${status.expression}" />
			</s:bind>
		</li>
		<li><label><s:message code='com.netmng.text.join.confirmPassword' /> </label>
			<s:bind path="userDTO.passConfirmed">
			<f:password path="${status.expression}" />
			</s:bind>
		</li>
	</ul>
	</div>
</f:form>
<div class="btn_area_c">
	<div class="btn_14">
		<a onClick="closePop();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_close_2.gif" alt="취소"></a>
	</div>
	<div class="btn_15">
		<a onClick="confirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_modify.gif" alt="저장"></a>
	</div>
</div>
<div id="confirmDv" style="display:none;">
	<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
		<form class="confirmform">
			<div class="confirm_member">
				<p><span><b><s:message code='com.netmng.text.join.msg7' /></b></span></p>
			</div>
			<div class="btn_area_con">
				<div class="btn_yes">
					<a onClick="save();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_yes.gif" alt="예"></a>
				</div>
				<div class="btn_no">
					<a onClick="disableConfirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_no.gif" alt="아니오"></a>
				</div>
			</div>
		</form>
	</div>
</div>