<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 	
<%@	taglib prefix="page" 	uri="/WEB-INF/tld/PagingTag.tld"%>

<style type="text/css">
#reserveListTb tbody tr:hover
{
	background-color: LemonChiffon;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		//$('#tableone').columnHover(); 
		
	});
	function goInsert(){
		$("#srchParam").attr("action", "${pageContext.request.contextPath}/nsa/reserveFavI.do").submit();
	}
	function view(seq) {
		$("#srchParam").find("#seq").val(seq);
		$("#srchParam").attr("action", "${pageContext.request.contextPath}/nsa/reserveFavU.do").submit();
	}
	function goPage(pageIdx){
		$("#srchParam").find("#pageIdx").val(pageIdx);
		$("#srchParam").submit();
	}
	function goSort(sortKey) {
		if($("#srchParam").find("#sortKey").val() == sortKey) {
			if($("#srchParam").find("#sortType").val() == "DESC") {
				if($("#srchParam").find("#sortKey").val() == "SEQ") {
					
				} else {
					$("#srchParam").find("#sortType").val("ASC");
				}
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
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_title_2.gif" alt="">
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/nsa/reserveIF.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image23','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_1_on.gif',1)"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_1_off.gif" name="Image23" width="167" height="22" border="0"></a> 
			</li>	
			<li>
				<a href="${pageContext.request.contextPath}/nsa/nsaL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('left_menu_2_2','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_2_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_2_off.gif" name="left_menu_2_2" width="167" height="22" border="0">
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/nsa/reserveL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('left_menu_2_3','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_3_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_3_off.gif" name="left_menu_2_3" width="167" border="0">
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/nsa/reserveLogL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('left_menu_2_4','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_4_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_4_off.gif" name="left_menu_2_4" width="167" border="0">
				</a> 
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/left_menu_2_5_on.gif" width="167"> 
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
		<a href="#" class="loc"><s:message code='com.netmng.menu.network' /></a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<span class="now"><s:message code='com.netmng.menu.network5' /></span>
	</div>
	<h2 id="request_h2">
		<%-- <img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/network/title_2_3.gif" alt=""> --%>
		<s:message code='com.netmng.menu.network5' />
	</h2>
	
	<!-- notice -->
	<div id="notice">
		<f:form name="srchParam" id="srchParam" action="${pageContext.request.contextPath}/nsa/reserveL.do" modelAttribute="srchParam" method="GET">
			<input type="hidden" id="seq" name="seq" />
			<div class="notice_count">
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
			</div>
			
			<table id="reserveListTb" cellspacing="0" class="tb_r">
			<colgroup>
				<col width="5%">
				<col width="30%">
				<col width="*">
			</colgroup>
			<thead>
			<tr>
				<th scope="col"><s:message code='com.netmng.text.network.number' /></th>
				<th scope="col">Favorite Name</th>
				<th scope="col">ProviderNSA</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${reservationFavList}" varStatus="status" var="reservation">
				<tr onclick="view(<c:out value="${reservation.seq}" />);" 
					style="cursor:pointer">
					<td><c:out value="${srchParam.totRow-(status.index+srchParam.sIdx)}" /></td>
					<td class="tb_r_head" style="word-break:break-all"><c:out value="${reservation.nm}" /></td>
					<td style="word-break:break-all"><c:out value="${reservation.providerNsa.nsa_nm}" /> (<c:out value="${reservation.providerNsa.nsa_addr}" />)</td>
				</tr>
				</c:forEach>
			</tbody>
			</table>
			<!-- 페이징 -->
			<%-- <div class="paging">
				<page:pagingTag totRow="${srchParam.totRow}" row="${srchParam.row}" pageIdx="${srchParam.pageIdx}" func="goPage" cxtPath="" />
			</div> --%>
			<!-- //페이징 -->
			<div class="btn_area2">
				<div class="btn_5">
					<a onclick="goInsert();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/notice/btn_write.gif" alt="등록"></a>
				</div>
			</div>
		</f:form>
	</div>
</div>  
<c:if test="${reservationMsg != null}">
<script type="text/javascript">
	alert("<s:message code='${reservationMsg}' />");
</script>
</c:if>
<!--//content-->
