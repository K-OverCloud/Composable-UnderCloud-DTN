<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>   
<script type="text/javascript">
	function save() {
		/* if($("#user_pass").val()!=$("#pass").val()){
			disableConfirm();
			alert("<s:message code='com.netmng.text.join.msg8' />");
		}else{
			$("#userDTO").attr("action", "${pageContext.request.contextPath}/adm/proc/userUP.do").attr("target", "_proc");
			disableConfirm();
			$("#userDTO").submit();
		} */
		$("#userDTO").attr("action", "${pageContext.request.contextPath}/adm/proc/userUP.do").attr("target", "_proc");
		disableConfirm();
		$("#userDTO").submit();
	}

	function confirm() {		
		loadConfirm($("#confirmDv"));			
	}
	
	function passMod(seq, id) {
		$("#popContent").find("#_popFrame").width(458);
		$("#popContent").find("#_popFrame").height(350);
		loadPop("${pageContext.request.contextPath}/adm/pop/userPassUF.do?user_seq="+seq+"&user_id="+id);
	}
	
	function goView(seq){
		document.location.href = "${pageContext.request.contextPath}/adm/userV.do?seq="+seq;
	}
</script>

<!-- left_column -->
<div id="left_column">
	<!-- left_area  -->
	<div id="left_area">
		<ul class="left_menu">
			<li class="left_tit">
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3.gif" alt="">
			</li>
			<li>
				<a><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_1_on.gif" alt=""></a>
			</li>
			<li><a href="${pageContext.request.contextPath}/adm/userMonitorL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image22','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_2_on.gif',1)"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_2_off.gif" name="Image22" width="167" height="22" border="0"></a></li>	
			<li><a href="${pageContext.request.contextPath}/adm/topologyL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image23','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_3_on.gif',1)"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_3_off.gif" name="Image23" width="167" height="22" border="0"></a></li>	
		</ul>
	</div>
	<!-- //left_area  -->
</div>
<!-- //left_column -->

<!-- content -->
<div id="content">	
	<div id="location">
		<a href="#" class="loc">Home</a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<a href="#" class="loc"><s:message code='com.netmng.menu.admin' /></a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<span class="now"><s:message code='com.netmng.menu.admin1' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/title_3_1.gif" alt=""></h2>
	
	<!-- member_form  -->
	<div id="member_form">
		<!-- <h3><font class="blue">현재 ‘승인 대기 중’ 입니다. </font></h3> -->
		<f:form name="userDTO" id="userDTO" action="${pageContext.request.contextPath}/user/proc/myInfoUP.do" modelAttribute="userDTO" method="POST">
		
		<s:bind path="userDTO.seq"><f:hidden path="${status.expression}" /></s:bind>
		<s:bind path="userDTO.id"><f:hidden path="${status.expression}" /></s:bind>
	
		<table summary="기본정보입력,필수입력" class="tb_member02 mt10" cellpadding="0">
        	<colgroup>
            	<col width="17%">
                <col width="*">
            </colgroup>
            <tbody>
            	<tr>
                	<th><s:message code='com.netmng.text.join.id' /></th>
                    <td class="last"><s:bind path="userDTO.id"><c:out value="${status.value}" /></s:bind></td>
                </tr>
                <tr>
                    <th><s:message code='com.netmng.text.join.name' /></th>
                    <td class="last"><s:bind path="userDTO.name"><f:input path="${status.expression}" cssStyle="width:140px;" cssClass="input_text" /></s:bind></td>
                </tr>
                <%-- <tr>
                    <th><s:message code='com.netmng.text.join.password' /></th>
                    <td class="last">
                    	<input type="password" id="user_pass" name="user_pass" value="" class="ipBasic" style="width:140px;" />
                    </td>
                </tr>
                <tr>
                    <th><s:message code='com.netmng.text.join.confirmPassword' /></th> 
                    <td class="last">
                    	<s:bind path="userDTO.pass"><f:password path="${status.expression}" cssClass="ipBasic" cssStyle="width:140px;" /></s:bind>
                        <a onClick="passMod('${userDTO.seq}','${userDTO.id}');"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_modify_3.gif" /></a>
                    </td>
                </tr> --%>
                <tr>
                    <th><s:message code='com.netmng.text.join.password' /></th>
                    <td class="last">
                    	******** &nbsp;&nbsp;&nbsp;
                    	<a onClick="passMod('${userDTO.seq}','${userDTO.id}');"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_modify_3.gif" /></a>
                    </td>
                
                <tr>
                    <th><s:message code='com.netmng.text.join.organization' /></th>
                    <td class="last">
                    	<s:bind path="userDTO.company"><f:input path="${status.expression}" cssClass="input_text" cssStyle="width:180px;" /></s:bind>
                    </td>
                </tr>
                <tr>
                    <th><s:message code='com.netmng.text.join.email' /></th>
                    <td class="last">
                        <%-- <span class="last"><s:bind path="userDTO.email"><f:label path="${status.expression}"><c:out value="${status.value}" /></f:label></s:bind></span> --%>
                        <span class="last"><s:bind path="userDTO.email"><f:input path="${status.expression}" cssClass="input_text" cssStyle="width:200px;" /></s:bind></span>
                    </td>
                </tr>
                <tr>
                    <th class="bdmember vaTop"><s:message code='com.netmng.text.join.grade.step' /></th>
                    <td class="last bdmember">
                        <select id="grade_seq" name="grade_seq">
                        	<option value="99"	<c:if test="${userDTO.grade_seq=='99'}">selected</c:if>	><s:message code='com.netmng.text.join.grade.step.99' /></option>
                        	<option value="2"	<c:if test="${userDTO.grade_seq=='2'}">selected</c:if>	><s:message code='com.netmng.text.join.grade.step.2' /></option>
                        	<option value="1"	<c:if test="${userDTO.grade_seq=='1'}">selected</c:if>	><s:message code='com.netmng.text.join.grade.step.1' /></option>
                        	<option value="100"	<c:if test="${userDTO.grade_seq=='100'}">selected</c:if>><s:message code='com.netmng.text.join.grade.step.100' /></option>
                        </select>
                    </td>
                </tr>
            </tbody>
    	</table>
		</f:form>
    	<div class="btn_area6">
			<div class="btn_14"><a onClick="goView(${userDTO.seq});"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_cancel.gif" alt="취소"></a></div>
            <div class="btn_15"><a onClick="confirm();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/member/btn_modify_2.gif" alt="수정"></a></div>
		</div>
	</div> 
	<!-- // member_form  -->
	
	<iframe id="_proc" name="_proc" frameborder="0" scrolling="no" width="0" height="0" ></iframe>
	<div id="confirmDv" style="display:none;">
		<div class="confirmPop" style="width:418px;height:148px; display:block" id="div_name">
			<form class="confirmform">
				<div class="confirm_member">
					<p><span><b><s:message code='com.netmng.text.join.msg6' /></b></span></p>
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
	
</div>

<!-- //content -->