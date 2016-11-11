<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %> 	
<%@	taglib prefix="page" 	uri="/WEB-INF/tld/PagingTag.tld"%>

<style type="text/css">
#globalMonitorTb tbody tr:hover
{
	background-color: LemonChiffon;
}
</style>  
<script type="text/javascript">
	$(document).ready(function() {
		//$('#tableone').columnHover(); 
	});
	
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
				<a href="${pageContext.request.contextPath}/adm/userL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image24','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_1_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_1_off.gif" width="167" height="22" id="Image24">
				</a> 
			</li>
			<li>
				<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_2_on.gif" width="167" height="22" border="0">
			</li>	
			<li>
				<a href="${pageContext.request.contextPath}/adm/topologyL.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image23','','${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_3_on.gif',1)">
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/left_title_3_3_off.gif" name="Image23" width="167" height="22" border="0">
				</a>
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
		<a href="#" class="loc"><s:message code='com.netmng.menu.admin' /></a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<a href="#" class="loc"><s:message code='com.netmng.menu.admin2' /></a> 
		<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/location.gif" width="3" height="10" align="bottom"> 
		<span class="now"><s:message code='com.netmng.menu.admin23' /></span>
	</div>
	<h2 id="request_h2"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/title_3_2.gif" alt=""></h2>
	<f:form name="srchParam" id="srchParam" action="${pageContext.request.contextPath}/adm/userMonitorL.do" modelAttribute="srchParam" method="GET">
	<!-- tab_area -->
	<div id="tab_area">
		<div class="tab_Monitor_1">
			<a href="${pageContext.request.contextPath}/adm/userMonitorL.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_2_1_off.gif" ></a> 
		</div>
		<div class="tab_Monitor_2">
			<a href="${pageContext.request.contextPath}/adm/interMonitorL.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_2_2_off.gif" ></a>
		</div>
		<div class="tab_Monitor_3">
			<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/tab_2_3_on.gif" >
		</div>
	</div>
	<!-- //tab_area -->
	
	
	<!--  us link -->
	<div id="us_link">
		<div class="img_area">
			<iframe width=690 height=690 src="http://kote-ps-1.ps.jgn-x.jp/ps/autoearth-nsi/" frameborder="0" scrolling="no"></iframe>
			<!-- 
			<a><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/admin/link_area.gif"></a>
			 -->
		</div>
	</div>
	<!-- us link //-->
	</f:form>
</div>
<!-- //content -->