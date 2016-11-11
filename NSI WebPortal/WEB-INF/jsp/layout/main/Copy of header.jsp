<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>

<div id="background" style="display:none;position:fixed;_position:absolute; height:100%;width:100%;top:0;left:0;background:#000000;border:0;z-index:1;"></div>
<div id="popContent" style="display:none;border:0;z-index:2;">
	<iframe id="_popFrame" name="_popFrame" src="about:blank"  frameborder="0" scrolling="no" ></iframe>
</div>
<div id="confirmContent" style="display:none;position:fixed;_position:absolute; background:transportation;border:0;z-index:3;padding:0;font-size:13px;"></div>
<!--HEADER START-->
<div class="global" style="margin:0 auto; width:1022px; z-index:3;">
	<div style="position:absolute; width:201px; height:15px; right:0px;z-index:1;  text-align:right;">
		<ul>
			<li class="global">
				<a>
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/global_k.png" width="131" height="17" usemap="#Map2" align="right" border="0">
					<map name="Map2">
						<area shape="rect" coords="4,3,63,15" href="#" onClick="localeSet('ko')">
						<area shape="rect" coords="73,2,128,16" href="#" onClick="localeSet('en')">
					</map>
				</a>
			</li>
		</ul>
	</div>
</div>

<div id="main_flash">
	<div class="flash">
		<object id="FlashID2" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="395">
    		<param name="movie" value="${pageContext.request.contextPath}/swf/${pageContext.response.locale.language}/main_1.swf">
			<param name="quality" value="high">
			<param name="wmode" value="transparent">
			<param name="swfversion" value="6.0.65.0">
			<!-- 이 param 태그는 Flash Player 6.0 r65 이후 버전 사용자에게 최신 버전의 Flash Player를 다운로드하라는 메시지를 표시합니다. 사용자에게 이러한 메시지를 표시하지 않으려면 이 태그를 삭제하십시오. -->
			<param name="expressinstall" value="${pageContext.request.contextPath}/scripts/expressInstall.swf">

   				<!-- 다음 객체 태그는 IE 이외의 브라우저에 사용됩니다. IECC를 사용하여 IE에서 이 태그를 숨기십시오. -->
   				<!--[if !IE]>-->
   				<object type="application/x-shockwave-flash" data="${pageContext.request.contextPath}/swf/${pageContext.response.locale.language}/main_1.swf" width="100%" height="395">
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
	<form id="headerFrm" name="headerFrm">	
	<div class="unit" style="margin:0 auto; width:1022px; z-index:1;">
		<div id="logOutDv" style="<c:if test="${SESS_USER != null}">display:none</c:if>; position:absolute; width:200px; height:20px; right:0px;z-index:1;  text-align:right;">
			<ul>
				<li class="unit"><a ><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/join.png" width="49" height="11" align="right" onClick="location.href='${pageContext.request.contextPath}/user/userJoinF.do'"></a></li>
				<li><a ><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/login.png" width="51" height="11" align="right" onClick="loginPop();"></a></li>
			</ul>
		</div>
		<div id="logInDv"    style="<c:if test="${SESS_USER == null}">display:none;</c:if> position:absolute; width:200px; height:20px; right:0px;z-index:1;  text-align:right;">
			<ul>
				<li class="unit"><a ><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/user.png" width="49" height="11" align="right" onClick="location.href='${pageContext.request.contextPath}/user/myInfoUF.do'"></a></li>
				<li><a ><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/logout.png" width="51" height="11" align="right" onClick="location.href='${pageContext.request.contextPath}/user/logoutP.do'"></a></li>
			</ul>
		</div>
	</div>
	<div id="sessInfoDv" class="user" style="<c:if test="${SESS_USER == null}">display:none;</c:if>margin:0 auto; width:620px; z-index:1;">
		<div style="position:absolute; width:235px; height:20px; right:0px; z-index:1; text-align:right;">
			<ul>
				<li class="user_img"><img id="memberImg" src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/user_type_${SESS_USER.grade.name}.png"></li>
				<li id="memberTxt" class="user_id"><c:out value="${SESS_USER.id}" />(<c:out value="${SESS_USER.name}" /> 님)</li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="menu_flash">  
	<div style="width:100%; z-index:0">
		<object id="FlashID" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="65" >
       			<param name="movie" value="${pageContext.request.contextPath}/swf/${pageContext.response.locale.language}/menu1<c:if test="${sessionScope['SESS_USER'].grade_seq == '1'}">_adm</c:if>.swf">
       			<param name="quality" value="high">
       			<param name="wmode" value="Transparent">	
       			<param name="swfversion" value="8.0.35.0">
       			<!-- 이 param 태그는 Flash Player 6.0 r65 이후 버전 사용자에게 최신 버전의 Flash Player를 다운로드하라는 메시지를 표시합니다. 사용자에게 이러한 메시지를 표시하지 않으려면 이 태그를 삭제하십시오. -->
       			<param name="expressinstall" value="${pageContext.request.contextPath}/scripts/expressInstall.swf">
       			<param name="PLAY" value="false">
       			<!-- 다음 객체 태그는 IE 이외의 브라우저에 사용됩니다. IECC를 사용하여 IE에서 이 태그를 숨기십시오. -->
       			<!--[if !IE]>-->
       			<object id="nonIe" type="application/x-shockwave-flash" data="${pageContext.request.contextPath}/swf/${pageContext.response.locale.language}/menu1<c:if test="${sessionScope['SESS_USER'].grade_seq == '1'}">_adm</c:if>.swf" width="100%" height="65">
					<!--<![endif]-->
					<param name="quality" value="high">
					<param name="wmode" value="Transparent">
					<param name="swfversion" value="8.0.35.0">
					<param name="expressinstall" value="${pageContext.request.contextPath}/scripts/expressInstall.swf">
					<param name="PLAY" value="false">
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

<script type="text/javascript"> swfobject.registerObject("FlashID2"); swfobject.registerObject("FlashID");</script>
<!--HEADER END-->