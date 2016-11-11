<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags" %>   

<!--CONTENTS START -->
<div id="wrap">
	<div id="wrap_main">
		<div class="contents">
			<div class="notice">
				<h2><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/txt_notice.gif" alt="notice"></h2>
				<ul>
				<c:forEach items="${brdList}" varStatus="status" var="brd" end="4">
					<li>
						<a href="${pageContext.request.contextPath}/brd/noticeV.do?seq=<c:out value="${brd.seq}" />">
							<s:eval expression='new String(brd.title).length() > 30 ? new String(brd.title).substring(0, 30) : new String(brd.title)' />
							<s:eval expression='new String(brd.title).length() > 30 ? new String("...") : new String("") ' />
						</a>
						<em><s:eval expression='new java.text.SimpleDateFormat("yyyy년 MM월 dd일").format(brd.createTime)' /></em>
					</li>
				</c:forEach>
				</ul>
				<span class="btn_more"><a href="${pageContext.request.contextPath}/brd/noticeL.do"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/btn_more.gif" alt="more"></a></span>
			</div>
			<div class="family_site">
				<h2>
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/txt_familysite.gif" alt="패밀리사이트">
				</h2>
				<p>
					<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/img_familysite.gif" border="0" usemap="#Map">
             		<map name="Map">
               			<area shape="rect" coords="6,6,255,67" target="_blank" href="http://www.kisti.re.kr/">
               			<area shape="rect" coords="268,8,518,67" target="_blank" href="http://www.kreonet.re.kr/">
             		</map>
				</p>	
			</div>
		</div>
	</div>
</div>
<!--CONTENTS END -->