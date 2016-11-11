<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>   
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>    
<script type="text/javascript">
	function save() {
		/*if($("#brdDTO").find("#title").length < 0
		|| $("#brdDTO").find("#title").length >= 100) {
			alert("제목을 입력하세요.");
			return false;
		} else*/ 
		if(!$("#brdDTO").find("#editor")[0].contentWindow.validateContent()) {
			return false;
		} else {
			$("#brdDTO").find("#content").val($("#editor")[0].contentWindow.getEditContent());
			$("#brdDTO").find("#file").val($("#brdDTO").find("#editor")[0].contentWindow.getFileList());
			$("#brdDTO").attr("action", "${pageContext.request.contextPath}/brd/proc/noticeIP.do").attr("target", "_proc");
			$("#brdDTO").submit();
		}
	}
	function view() {
		$("#listFrm").attr("action", "${pageContext.request.contextPath}/brd/noticeV.do").submit();
	}
	function goList() {
		$("#listFrm").attr("action", "${pageContext.request.contextPath}/brd/noticeL.do").submit();
	}
</script>
<!-- left_column -->
<div id="left_column">
	<!-- left_area  -->
	<div id="left_area">
		<ul class="left_menu">
			<li class="left_tit">
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/left_title_1.gif" alt="">
			</li>
			<li>
				<a ><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/left_title_1_1.gif" alt=""></a>
			</li>
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
		<span class="now"><s:message code='com.netmng.menu.notice' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/title_1.gif" alt=""></h2>
	<!-- notice -->
	<div id="notice">
	
		<f:form name="brdDTO" id="brdDTO" action="${pageContext.request.contextPath}/brd/proc/noticeIP.do" modelAttribute="brdDTO" method="POST">
			<s:bind path="brdDTO.seq">
			<f:hidden path="${status.expression}" />
			</s:bind>
			<s:bind path="brdDTO.cate_seq">
			<f:hidden path="${status.expression}" />
			</s:bind>
			
			
			
			<table cellspacing="0" class="tbl_edit">
			<colgroup>
				<col width="11%">
				<col width="*">
				<col width="10%">
				<col width="*">
			</colgroup>
			<thead>
				<tr>
					<th class="label">
						<s:message code='com.netmng.text.notice.title' />
					</th>
					<th colspan="3">
						<s:bind path="brdDTO.title">
						<f:input path="${status.expression}" cssStyle="width:550px;height:19px" />	
						</s:bind>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr class="head">
					<td class="label"><s:message code='com.netmng.text.notice.writer' /></td>
					<td class="name">
						<c:out value="${sessionScope['SESS_USER'].name}" />
					</td>
					<td class="label"><s:message code='com.netmng.text.notice.date' /></td>
					<td class="data"></td>
				</tr>
			</tbody>
			</table>
			<iframe id="editor" name="editor" src="${pageContext.request.contextPath}/editor/editor.html" style="width: 700px;height: 700px;" frameborder="0" scrolling="no"></iframe>
			<s:bind path="brdDTO.content">
			<f:textarea path="${status.expression}" htmlEscape="false" style="display:none" />
			</s:bind>
			<input type="hidden" id="file" name="file" />
			<div class="btn_area2">
				<div class="btn_5">
					<a  onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_list.gif" alt="목록"></a>
				</div>
				<div class="btn_6"><a  onClick="save();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_save.gif" alt="저장"></a></div>
			</div>
			
			
			
		</f:form>
		<iframe id="_proc" name="_proc" frameborder="0" scrolling="no" width="0" height="0" ></iframe>
		<f:form name="listFrm" id="listFrm" action="${pageContext.request.contextPath}/brd/noticeL.do" method="GET" modelAttribute="srchParam">
			<input type="hidden" id="seq" name="seq" value="<c:out value='${brdDTO.seq}' />" />
			<s:bind path="srchParam.createBegin">
			<f:hidden path="${status.expression}" />
			</s:bind>
			<s:bind path="srchParam.createEnd">
			<f:hidden path="${status.expression}" />
			</s:bind>
			<s:bind path="srchParam.mode">
			<f:hidden path="${status.expression}" />
			</s:bind>
			<s:bind path="srchParam.srchTxt">
			<f:hidden path="${status.expression}" />
			</s:bind>
			<%@ include file="/WEB-INF/jsp/com/pageParamH.jsp"%>
		</f:form>
	
	</div>
	<!--//notice-->
</div>
<!--//content-->