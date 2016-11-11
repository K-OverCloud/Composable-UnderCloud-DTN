<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>  
<script type="text/javascript">
	function mod() {
		$("#listFrm").attr("action", "${pageContext.request.contextPath}/brd/noticeUF.do").submit();
	}
	function del() {
		$("#brdDTO").attr("action", "${pageContext.request.contextPath}/brd/proc/noticeDP.do").attr("target", "_proc").submit();
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
	<f:form name="brdDTO" id="brdDTO" action="${pageContext.request.contextPath}/brd/noticeUF.do" modelAttribute="brdDTO" method="GET">
		<s:bind path="brdDTO.seq">
		<f:hidden path="${status.expression}" />
		</s:bind>
		<s:bind path="brdDTO.cate_seq">
		<f:hidden path="${status.expression}" />
		</s:bind>
		<table cellspacing="0" class="tbl_v">
		<colgroup>
			<col width="11%">
			<col width="*">
			<col width="10%">
			<col width="*">
		</colgroup>
		<thead>
			<tr>
				<th colspan="4">
				<s:bind path="brdDTO.title">
				<label><c:out value="${status.value}" /></label>	
				</s:bind>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr class="head"> 
				<td class="label"><s:message code='com.netmng.text.notice.writer' /></td>
				<td class="name">
					<s:bind path="brdDTO.user.name">
					<f:label path="${status.expression}">${status.value}</f:label>
					</s:bind>
				</td>
				<td class="label"><s:message code='com.netmng.text.notice.date' /></td>
				<td class="data">
					<s:eval expression='new java.text.SimpleDateFormat("yyyy년 MM월 dd일").format(brdDTO.createTime)' />
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div class="counts">
						<s:eval expression='new String(brdDTO.content).replaceAll("images", "/images").replaceAll("//images", "/images")' htmlEscape="false" />
					</div>
				</td>
			</tr>
			<tr class="head_file"> 
				<td class="label"><s:message code='com.netmng.text.notice.file' /></td>
				<td colspan="3" class="file">
					<div class="file_area">
						<ul id="file" style="margin: 0px">
						<c:forEach items="${brdDTO.fileInfoList}" varStatus="status" var="fileInfo">
							<li><a href="${pageContext.request.contextPath}/com/filedown.do?seq=${fileInfo.seq}">${fileInfo.name}</a></li>
						</c:forEach>
						</ul>
					</div>
				</td>
			</tr>
		</tbody>
		</table>
		<div class="btn_area2">
			<div class="btn_5">
				<a  onClick="goList();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_list.gif" alt="목록"></a>
			</div>
			<c:if test="${sessionScope['SESS_USER'].seq == brdDTO.user_seq}">
			<c:if test="${sessionScope['SESS_USER'].grade_seq == '1'}">
			<div class="btn_6">
				<a  onClick="del();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_delete.gif" alt="삭제"></a>
			</div>
			<div class="btn_7">
				<a  onClick="mod();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_modify.gif" alt="수정"></a>
			</div>
			</c:if>
			</c:if>
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