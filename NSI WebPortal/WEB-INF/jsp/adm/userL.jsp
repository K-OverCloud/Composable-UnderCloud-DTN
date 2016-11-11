<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>    
<%@	taglib prefix="page" 	uri="/WEB-INF/tld/PagingTag.tld"%>
<style type="text/css">
#userListTb tbody tr:hover
{
	background-color: LemonChiffon;
}
</style>
<script type="text/javascript">
	function goReg(){
		$("#srchParam").attr("action", "${pageContext.request.contextPath}/adm/userIF.do").submit();
	}
	function view(seq) {
		$("#srchParam").find("#seq").val(seq);
		$("#srchParam").attr("action", "${pageContext.request.contextPath}/adm/userV.do").submit();
	}
	function goSearch() {
		$("#srchParam").find("#pageIdx").val("1");
		$("#srchParam").submit();
	}
	function goPage(pageIdx){
		$("#srchParam").find("#pageIdx").val(pageIdx);
		$("#srchParam").submit();
	}
	function goSort(sortKey) {
		if($("#srchParam").find("#sortKey").val() == sortKey) {
			if($("#srchParam").find("#sortType").val() == "DESC") {
				$("#srchParam").find("#sortType").val("ASC");
			} else {
				$("#srchParam").find("#sortType").val("DESC");
			}
		} else {
			$("#srchParam").find("#sortKey").val(sortKey);
			$("#srchParam").find("#sortType").val("DESC");
		}
		$("#srchParam").find("#pageIdx").val("1");
		goPage("1");
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
<f:form name="srchParam" id="srchParam" action="${pageContext.request.contextPath}/adm/userL.do" modelAttribute="srchParam" method="GET">

	<div id="location">
		<a href="#" class="loc">Home</a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<a href="#" class="loc"><s:message code='com.netmng.menu.admin' /></a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<span class="now"><s:message code='com.netmng.menu.admin1' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/title_3_1.gif" alt=""></h2>

	<!-- search -->
	<div id="search">	
		<div class="search_tit">
			<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/search_tit.gif" > 
		</div>	
		<div class="select_3">
			<input type="hidden" id="seq" name="seq" />
			<s:bind path="srchParam.mode">			
			<c:if test="${pageContext.response.locale.language == null}">
			<f:select path="${status.expression}" value="${status.value}" cssStyle="width:60px;height:20px">
				<f:option value="name" label="이름" />
				<f:option value="id" label="id" />
				<f:option value="company" label="소속기관" />
			</f:select>
				</c:if>
			<c:if test="${pageContext.response.locale.language == ''}">
			<f:select path="${status.expression}" value="${status.value}" cssStyle="width:60px;height:20px">
				<f:option value="name" label="이름" />
				<f:option value="id" label="id" />
				<f:option value="company" label="소속기관" />
			</f:select>
			</c:if>
			<c:if test="${pageContext.response.locale.language == 'ko'}">
			<f:select path="${status.expression}" value="${status.value}" cssStyle="width:60px;height:20px">
				<f:option value="name" label="이름" />
				<f:option value="id" label="id" />
				<f:option value="company" label="소속기관" />
			</f:select>
			</c:if>
			<c:if test="${pageContext.response.locale.language == 'en'}">
			<f:select path="${status.expression}" value="${status.value}" cssStyle="width:60px;height:20px">
				<f:option value="name" label="name" />
				<f:option value="id" label="id" />
				<f:option value="company" label="organization" />
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
			<a onClick="goSearch();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_search.gif" ></a>
		</div>
	</div>	
	<!-- // search -->
	
	<!-- notice -->
	<div id="notice">
		<div class="notice_count">
			<%@ include file="/WEB-INF/jsp/com/pageParam.jsp"%>
		</div>
		<table id="userListTb" cellspacing="0" class="tb_l">
		<colgroup>
			<col width="8%">
			<col width="16%">
			<col width="16%">
			<col width="18%">
			<col width="16%">
			<col width="16%">
		</colgroup>
		<thead>
		<tr style="cursor:pointer">
			<th scope="col" onClick="goSort('SEQ')"><s:message code='com.netmng.text.network.number' /></th>
			<th scope="col" onClick="goSort('ID')"><s:message code='com.netmng.text.join.id' /></th>
			<th scope="col" onClick="goSort('NAME')"><s:message code='com.netmng.text.join.name' /></th>
			<th scope="col" onClick="goSort('COMPANY')"><s:message code='com.netmng.text.join.organization' /></th>
			<th scope="col" onClick="goSort('GRADE_SEQ')"><s:message code='com.netmng.text.join.status' /></th>
			<th scope="col" onClick="goSort('CREATETIME')"><s:message code='com.netmng.text.join.joinDate' /></th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${userList}" varStatus="status" var="user">
		<tr style="cursor:pointer" onClick='view(<c:out value="${user.seq}" />);'>
			<%-- <td><c:out value="${user.seq}" /></td> --%>
			<td><c:out value="${srchParam.totRow-(status.index+srchParam.sIdx)}" /></td>
			<td class="user_head"><c:out value="${user.id}" /></td>
			<td><c:out value="${user.name}" /></td>
			<td><c:out value="${user.company}" /></td>
			<td><c:out value="${user.grade.comment}" /></td>
			<td><s:eval expression='new java.text.SimpleDateFormat("yyyy년 MM월 dd일").format(user.createTime)' /></td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
		<!-- 페이징 --> 
		<div class="paging">
			<page:pagingTag totRow="${srchParam.totRow}" row="${srchParam.row}" pageIdx="${srchParam.pageIdx}" func="goPage" cxtPath="" />
		</div>
		<!-- //페이징 -->
		<c:if test="${sessionScope['SESS_USER'].grade_seq == '1'}">
		<div class="btn_area2">
			<div class="btn_5">
				<a  onClick="goReg();">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_write.gif" alt="등록">
				</a>
			</div>
		</div>
		</c:if>
	</div>
	<!-- //notice -->

</f:form>
</div>
<!-- //content -->