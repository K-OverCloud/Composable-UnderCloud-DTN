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
				$("#top_"+"menu_"+i).attr("style","text-align:left; color:yellow; font-family:Tahoma; font-size:10pt; font-weight:bold; vertical-align:middle;");
				$("#"+"menu_"+i).show();
			}else{
				$("#top_"+"menu_"+i).attr("style","text-align:left; color:white; font-family:Tahoma; font-size:10pt; font-weight:bold; vertical-align:middle;");
				$("#"+"menu_"+i).hide();
			}
		}
	}
</script>

<div id="background" style="display:none;position:fixed;_position:absolute; height:100%;width:100%;top:0;left:0;background:#000000;border:0;z-index:1;"></div>
<div id="popContent" style="display:none;position:fixed;_position:absolute; background:transportation;border:0;z-index:2;padding:0;font-size:13px;">
	<iframe id="_popFrame" name="_popFrame" src="about:blank"  frameborder="0" scrolling="no"></iframe>
</div>
<div id="confirmContent" style="display:none;position:fixed;_position:absolute; background:transportation;border:0;z-index:3;padding:0;font-size:13px;">
</div>
<div class="global" style="margin:0 auto; width:1022px; z-index:3;">
	<div style="position:absolute; width:183px; height:15px; right:0px;z-index:1;  text-align:right;">
		<ul>
			<li class="global">
				<a>
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/global_k.png" width="131" height="17" usemap="#Map" align="right" border="0">
					<map name="Map">
						<area shape="rect" coords="3,1,63,16" href="#" onClick="localeSet('ko')">
						<area shape="rect" coords="74,3,135,18" href="#" onClick="localeSet('en')">
					</map>
				</a>
			</li>
		</ul>
	</div>
	<div style="position:absolute; width:990px; height:15px; right:0px;z-index:1;  text-align:right;">
		<ul>
			<li class="global">
				<a>
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/top_title_sub.gif" width="370" height="22" usemap="#Map3" align="right" border="0">
					<map name="Map3">
						<area shape="rect" coords="3,1,373,23" href="http://dynamickl.kreonet.re.kr">
					</map>
				</a>
			</li>
		</ul>
	</div>
</div>
<!-- header -->
<div id="sub_bg">
	<div class="sub_v">
		<object id="FlashID" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="986" height="165">
			<param name="movie" value="${pageContext.request.contextPath}/swf/${pageContext.response.locale.language}/sub_1.swf">
			<param name="quality" value="high">
			<param name="wmode" value="transparent">
			<param name="swfversion" value="6.0.65.0">
			<!-- 이 param 태그는 Flash Player 6.0 r65 이후 버전 사용자에게 최신 버전의 Flash Player를 다운로드하라는 메시지를 표시합니다. 사용자에게 이러한 메시지를 표시하지 않으려면 이 태그를 삭제하십시오. -->
			<param name="expressinstall" value="${pageContext.request.contextPath}/scripts/expressInstall.swf">
			<!-- 다음 객체 태그는 IE 이외의 브라우저에 사용됩니다. IECC를 사용하여 IE에서 이 태그를 숨기십시오. -->
			<!--[if !IE]>-->
			<object type="application/x-shockwave-flash" data="${pageContext.request.contextPath}/swf/${pageContext.response.locale.language}/sub_1.swf" width="986" height="165">
				<!--<![endif]-->
				<param name="quality" value="high">
				<param name="wmode" value="transparent">
				<param name="swfversion" value="6.0.65.0">
				<param name="expressinstall" value="${pageContext.request.contextPath}/scripts/expressInstall.swf">
				<!-- Flash Player 6.0 이전 버전 사용자의 브라우저에는 다음과 같은 대체 내용이 표시됩니다. -->
				<div>
					<h4>이 페이지의 내용을 보려면 최신 버전의 Adobe Flash Player가 필요합니다.</h4>
					<p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Adobe Flash Player 내려받기" width="112" height="33" /></a></p>
				</div>
				<!--[if !IE]>-->
			</object>
			<!--<![endif]-->
		</object>
	</div>
</div>
<!-- //header -->
<div id="wrapper">
	<form id="headerFrm" name="headerFrm">
	<div class="unit" style="margin:0 auto; width:1022px; z-index:1;">
		<div id="logOutDv" style="<c:if test="${SESS_USER != null}">display:none;</c:if>position:absolute; width:183px; height:20px; right:0px; z-index:1; text-align:right;">
		<ul>
			<li class="unit">
				<a href="${pageContext.request.contextPath}/user/userJoinF.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/join.png" width="49" height="11" align="right"></a>
			</li>
			<li>
				<a id="loginBT"  onClick="loginPop();"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/login.png" width="51" height="11" align="right"></a>
			</li>
		</ul>
		</div>
		<div id="logInDv" style="<c:if test="${SESS_USER == null}">display:none;</c:if>position:absolute; width:183px; height:20px; right:0px;z-index:1;  text-align:right;">
		<ul>
			<!-- <li><label id="memberTxt"><c:out value="${SESS_USER.id}" />(<c:out value="${SESS_USER.name}" /> 님)<c:out value="${SESS_USER.grade.title}" /></label></li> -->
			<li class="unit">
				<a href="${pageContext.request.contextPath}/user/myInfoUF.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/user.png" width="49" height="11" align="right"></a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/user/logoutP.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/logout.png" width="51" height="11" align="right"></a>
			</li>
		</ul>
		</div>
	</div>
	<div id="sessInfoDv" class="user" style="<c:if test="${SESS_USER == null}">display:none;</c:if>margin:0 auto; width:620px; z-index:1;">
		<div style="position:absolute; width:235px; height:20px; right:0px;z-index:1;  text-align:right;">
			<ul>
				<li class="user_img"><img id="memberImg" src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/user_type_${SESS_USER.grade.name}.png"></li>
				<li id="memberTxt" class="user_id"><c:out value="${SESS_USER.id}" />(<c:out value="${SESS_USER.name}" /> 님)</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="menu_flash">  
	<div style="width:100%; height:65px; z-index:0">
		
		<jsp:include page="../topMenuInc.jsp" flush="true" />
		
		<%-- <object id="FlashID2" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="1583" height="65" align="left">
			<param name="movie" value="${pageContext.request.contextPath}/swf/${pageContext.response.locale.language}/sub_menu1<c:if test="${sessionScope['SESS_USER'].grade_seq == '1'}">_adm</c:if>.swf">
			<param name="quality" value="high">
			<param name="wmode" value="opaque">
			<param name="swfversion" value="8.0.35.0">
			<!-- 이 param 태그는 Flash Player 6.0 r65 이후 버전 사용자에게 최신 버전의 Flash Player를 다운로드하라는 메시지를 표시합니다. 사용자에게 이러한 메시지를 표시하지 않으려면 이 태그를 삭제하십시오. -->
			<param name="expressinstall" value="${pageContext.request.contextPath}/scripts/expressInstall.swf">
			<!-- 다음 객체 태그는 IE 이외의 브라우저에 사용됩니다. IECC를 사용하여 IE에서 이 태그를 숨기십시오. -->
			<!--[if !IE]>-->
			<object id="nonIe" type="application/x-shockwave-flash" data="${pageContext.request.contextPath}/swf/${pageContext.response.locale.language}/sub_menu1<c:if test="${sessionScope['SESS_USER'].grade_seq == '1'}">_adm</c:if>.swf" width="1583" height="65" align="left">
				<!--<![endif]-->
				<param name="quality" value="high">
				<param name="wmode" value="opaque">
				<param name="swfversion" value="8.0.35.0">
				<param name="expressinstall" value="${pageContext.request.contextPath}/scripts/expressInstall.swf">
				<!-- Flash Player 6.0 이전 버전 사용자의 브라우저에는 다음과 같은 대체 내용이 표시됩니다. -->
				<div>
					<h4>이 페이지의 내용을 보려면 최신 버전의 Adobe Flash Player가 필요합니다.</h4>
					<p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Adobe Flash Player 내려받기" width="112" height="33" /></a></p>
				</div>
				<!--[if !IE]>-->
			</object>
			<!--<![endif]-->
		</object> --%>
	</div>
</div>
<script type="text/javascript">
	swfobject.registerObject("FlashID2");
	swfobject.registerObject("FlashID");
</script>