<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>   
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 
<%@	taglib prefix="page" 	uri="/WEB-INF/tld/PagingTag.tld"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#srchParam").find("#createBegin").mask("9999.99.99");
		$("#srchParam").find("#createEnd").mask("9999.99.99");
		$("#srchParam").find("#createBegin").datepick({showOnFocus: false, showTrigger: '#startBt', dateFormat: 'yyyy.mm.dd'});
		$("#srchParam").find("#createEnd").datepick({showOnFocus: false, showTrigger: '#endBt', dateFormat: 'yyyy.mm.dd'});
	});
	function view(seq) {
		$("#srchParam").find("#seq").val(seq);
		$("#srchParam").attr("action", "${pageContext.request.contextPath}/brd/noticeV.do").submit();
	}
	function goSearch() {
		$("#srchParam").find("#pageIdx").val("1");
		$("#srchParam").submit();
	}
	function goPage(pageIdx){
		$("#srchParam").find("#pageIdx").val(pageIdx);
		$("#srchParam").submit();
	}
	function newPost() {
		$("#srchParam").attr("action", "${pageContext.request.contextPath}/brd/noticeIF.do").submit();
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

<!-- content -->
<div id="content">
<!-- //left_column -->	
	<div id="location">
		<a href="${pageContext.request.contextPath}/" class="loc">Home</a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<span class="now"><s:message code='com.netmng.menu.notice' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/title_1.gif" alt=""></h2>
	
	
	<f:form name="srchParam" id="srchParam" action="${pageContext.request.contextPath}/brd/noticeL.do" modelAttribute="srchParam" method="GET">
	<!-- // search -->
	<div id="search">	
		<div class="search_tit">
			<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/search_tit.gif" > 
		</div>
		<div style="display: none;">
			<input type="hidden" id="seq" name="seq" />
			<s:bind path="srchParam.createBegin">
			<label for="createdAtBegin">시작 : </label>
			<f:input path="${status.expression}" />
			<div style="display: none;">
			<a href="#self" id="startBt"><img src="${pageContext.request.contextPath}/img/calendar.gif"/></a>
			</div>
			</s:bind>
			<s:bind path="srchParam.createEnd">
			<label for="createdAtEnd">종료 : </label>
			<f:input path="${status.expression}" /> 
			<div style="display: none;">
			<a href="#self" id="endBt"><img src="${pageContext.request.contextPath}/img/calendar.gif"/></a>
			</div>
			</s:bind>
		</div>
		<div class="select_3">	
			<s:bind path="srchParam.mode">
				<c:if test="${pageContext.response.locale.language == null}">
				<f:select path="${status.expression}" value="${status.value}" cssStyle="width:60px;height:20px">
					<f:option value="all" label="전체" />
					<f:option value="title" label="제목" />
					<f:option value="content" label="내용" />
				</f:select>
				</c:if>
				<c:if test="${pageContext.response.locale.language == ''}">
				<f:select path="${status.expression}" value="${status.value}" cssStyle="width:60px;height:20px">
					<f:option value="all" label="전체" />
					<f:option value="title" label="제목" />
					<f:option value="content" label="내용" />
				</f:select>
				</c:if>
				<c:if test="${pageContext.response.locale.language == 'ko'}">
				<f:select path="${status.expression}" value="${status.value}" cssStyle="width:60px;height:20px">
					<f:option value="all" label="전체" />
					<f:option value="title" label="제목" />
					<f:option value="content" label="내용" />
				</f:select>
				</c:if>
				<c:if test="${pageContext.response.locale.language == 'en'}">
				<f:select path="${status.expression}" value="${status.value}" cssStyle="width:60px;height:20px">
					<f:option value="all" label="all" />
					<f:option value="title" label="title" />
					<f:option value="content" label="content" />
				</f:select>
				</c:if>
			</s:bind>
		</div>
		<div class="inputbox_3">
			<s:bind path="srchParam.srchTxt">
				<f:input path="${status.expression}" cssStyle="width:300px;height:17px" /> 
			</s:bind>
		</div>
		<div class="btn_search">
			<a  onClick="goSearch();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_search.gif" ></a> 
		</div>
	</div>	
	<!--  search //-->
	
	
	<!-- notice -->
	<div id="notice">
		<div class="notice_count">
			<%@ include file="/WEB-INF/jsp/com/pageParam.jsp"%>
		</div>
		<table cellspacing="0" class="tb_l">
		<colgroup>
			<col width="8%">
			<col width="*">
			<col width="12%">
			<col width="15%">
			<col width="10%">
		</colgroup>
		<thead>
			<tr>
				<th scope="col"><s:message code='com.netmng.text.notice.number' /></th>
				<th scope="col"><s:message code='com.netmng.text.notice.title' /></th>
				<th scope="col"><s:message code='com.netmng.text.notice.writer' /></th>
				<th scope="col"><s:message code='com.netmng.text.notice.date' /></th>
				<th scope="col"><s:message code='com.netmng.text.notice.visit' /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${brdList}" varStatus="status" var="brd">
			<tr>
				<td><c:out value="${srchParam.totRow-(status.index+srchParam.sIdx)}" /></td>
				<td class="notice_head"><a onClick='view(<c:out value="${brd.seq}" />);'><c:out value="${brd.title}" /></a></td>
				<td><c:out value="${brd.user.name}" /><c:if test="${brd.user.name == null}">&nbsp;</c:if></td>
				<td><s:eval expression='new java.text.SimpleDateFormat("yyyy년 MM월 dd일").format(brd.createTime)' /></td>
				<td><c:out value="${brd.visit}" /></td>
			</tr>
			</c:forEach>
		</tbody>
		</table>
		<!-- 페이징 --> 
		<div class="paging">
			<page:pagingTag totRow="${srchParam.totRow}" row="${srchParam.row}" pageIdx="${srchParam.pageIdx}" func="goPage" cxtPath="${pageContext.request.contextPath}/" />
		</div>
		<!-- //페이징 -->
		<c:if test="${sessionScope['SESS_USER'].grade_seq == '1'}">
		<div class="btn_area2">
			<div class="btn_5">
				<a  onClick="newPost();">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_write.gif" alt="등록">
				</a>
			</div>
		</div>
		</c:if>
	</div>
	<!--//notice-->
	</f:form>

</div>
<!--//notice-->