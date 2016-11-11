<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
	function viewSubMenu(submenu){
		var cntMenu = 3;
		for(i=1; i<=cntMenu; i++){
			if(submenu==("menu_"+i)){
				$("#top_"+"menu_"+i).attr("style","text-align:center; color:yellow; font-family:Tahoma; font-size:10pt; font-weight:bold; vertical-align:middle;");
				$("#"+"menu_"+i).show();
			}else{
				$("#top_"+"menu_"+i).attr("style","text-align:center; color:white; font-family:Tahoma; font-size:10pt; font-weight:bold; vertical-align:middle;");
				$("#"+"menu_"+i).hide();
			}
		}
	}
</script>
	
		<table width="100%" cellspacing="0" cellpadding="0" style="background-image:url('${pageContext.request.contextPath}/images/topMenuBg.gif'); ">
			<colgroup>
				<col width="200px">
				<col width="75px">	<!-- 공지사항 -->
				<col width="230px">	<!-- 네트워크 자원관리 -->
				<col width="105px">	<!-- 관리자메뉴 -->
				<col width="766px">		
			</colgroup>
			<tbody>
			<tr height="35px">
				<td>&nbsp;</td>
				<td style="text-align:center; color:white; font-family:Tahoma; font-size:10pt; font-weight:bold; vertical-align:middle;" 
					id="top_menu_1"
					onmouseout="viewSubMenu('menu_0')"
					onmouseover="viewSubMenu('menu_1')">
					<s:message code='com.netmng.menu.notice' />		<!-- 공지사항 -->
				</td>
				<td style="text-align:center; color:white; font-family:Tahoma; font-size:10pt; font-weight:bold; vertical-align:middle;" 
					id="top_menu_2"
					onmouseout="viewSubMenu('menu_0')"
					onmouseover="viewSubMenu('menu_2')">
					<s:message code='com.netmng.menu.network' />	<!-- 네트워크 자원관리 -->
				</td>
				<c:if test="${sessionScope['SESS_USER'].grade_seq == '1'}">
				<td style="text-align:center; color:white; font-family:Tahoma; font-size:10pt; font-weight:bold; vertical-align:middle;" 
					id="top_menu_3"
					onmouseout="viewSubMenu('menu_0')"
					onmouseover="viewSubMenu('menu_3')">
					<s:message code='com.netmng.menu.admin' />		<!-- 관리자메뉴 -->
				</td>
				</c:if>
				<c:if test="${sessionScope['SESS_USER'].grade_seq != '1'}">
				<td>&nbsp;</td>
				</c:if>	
				<td>&nbsp;</td>
			</tr>
			<tr height="30" 
				id="menu_1" 
				style="display:none;" 
				onmouseover="viewSubMenu('menu_1')" 
				onmouseout="viewSubMenu('menu_0')">
				<td>&nbsp;</td>
				<td style="text-align:left;" colspan="4">
					<a	href="<%=request.getContextPath()%>/brd/noticeL.do" 
						style="color:white; font-family:Tahoma; font-size:8pt; font-weight:bold; vertical-align:middle;">
						<s:message code='com.netmng.menu.notice' />	<!-- 공지사항 -->
					</a>&nbsp;&nbsp;&nbsp;
				</td>
				<!-- <td>&nbsp;</td> -->
			</tr>
			<tr height="30" 
				id="menu_2" 
				style="display:none;" 
				onmouseover="viewSubMenu('menu_2')" 
				onmouseout="viewSubMenu('menu_0')">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td style="text-align:left;" colspan="3">
					<a	href="<%=request.getContextPath()%>/nsa/reserveIF.do" 
						style="color:white; font-family:Tahoma; font-size:8pt; font-weight:bold; vertical-align:middle;">
						<s:message code='com.netmng.menu.network1' />	<!-- 예약서비스 -->
					</a>&nbsp;&nbsp;&nbsp;
					<a	href="<%=request.getContextPath()%>/nsa/nsaL.do" 
						style="color:white; font-family:Tahoma; font-size:8pt; font-weight:bold; vertical-align:middle;">
						<s:message code='com.netmng.menu.network2' />	<!-- 조회서비스 -->
					</a>&nbsp;&nbsp;&nbsp;
					<a	href="<%=request.getContextPath()%>/nsa/reserveL.do" 
						style="color:white; font-family:Tahoma; font-size:8pt; font-weight:bold; vertical-align:middle;">
						<s:message code='com.netmng.menu.network3' />	<!-- 구성/해제/종결서비스 -->
					</a>&nbsp;&nbsp;&nbsp;
					<a	href="<%=request.getContextPath()%>/nsa/reserveLogL.do" 
						style="color:white; font-family:Tahoma; font-size:8pt; font-weight:bold; vertical-align:middle;">
						<s:message code='com.netmng.menu.network4' />	<!-- 이력조회서비스 -->
					</a>&nbsp;&nbsp;&nbsp;
					<a	href="<%=request.getContextPath()%>/nsa/reserveFavL.do" 
						style="color:white; font-family:Tahoma; font-size:8pt; font-weight:bold; vertical-align:middle;">
						<s:message code='com.netmng.menu.network5' />	<!-- 즐겨찿는사이트 -->
					</a>&nbsp;&nbsp;&nbsp;
				</td>
				<!-- <td>&nbsp;</td> -->
			</tr>
			<tr height="30" 
				id="menu_3" 
				style="display:none;" 
				onmouseover="viewSubMenu('menu_3')" 
				onmouseout="viewSubMenu('menu_0')">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td style="text-align:left;" colspan="2">
					<a	href="<%=request.getContextPath()%>/adm/userL.do" 
						style="color:white; font-family:Tahoma; font-size:8pt; font-weight:bold; vertical-align:middle;">
						<s:message code='com.netmng.menu.admin1' />	<!-- 사용자관리 -->
					</a>&nbsp;&nbsp;&nbsp;
					<a	href="<%=request.getContextPath()%>/adm/userMonitorL.do" 
						style="color:white; font-family:Tahoma; font-size:8pt; font-weight:bold; vertical-align:middle;">
						<s:message code='com.netmng.menu.admin2' />	<!-- 네트워크모니터링 -->
					</a>&nbsp;&nbsp;&nbsp;
					<a	href="<%=request.getContextPath()%>/adm/topologyL.do" 
						style="color:white; font-family:Tahoma; font-size:8pt; font-weight:bold; vertical-align:middle;">
						<s:message code='com.netmng.menu.admin3' />	<!-- 네트워크관리 -->
					</a>&nbsp;&nbsp;&nbsp;
				</td>
				<!-- <td>&nbsp;</td> -->
			</tr>
			</tbody>
		</table>
		