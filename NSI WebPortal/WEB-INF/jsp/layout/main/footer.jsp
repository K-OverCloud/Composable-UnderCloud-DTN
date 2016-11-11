<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<!--FOOTER START-->
<div id="footerarea">
	<div class="footer">
		<p class="f_img"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/logo_footer.gif"></p>
		<div class="utilLink">
			<ul title="사이트 관련 링크">
				<li><a href="<s:message code='com.netmng.url.info' />" target="_blank"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/footer_menu1.gif" alt="개인정보보호방침"></a></li>
				<li><a href="<s:message code='com.netmng.url.email' />" target="_blank"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/footer_menu2.gif" alt="이메일무단수집거부"></a></li>
				<li><a href="<s:message code='com.netmng.url.map' />" target="_blank"><img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/footer_menu3.gif" alt="찾아오시는길"></a></li>
			</ul>
		</div>
		<div class="copyright" >
			<img src="${pageContext.request.contextPath}/images/${pageContext.response.locale.language}/footer_copyright.gif">
		</div>
	</div>
</div>
<!--FOOTER END-->	