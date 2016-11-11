<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/${pageContext.response.locale.language}/style_sub.css">
<style type="text/css">@import "${pageContext.request.contextPath}/css/jquery.datepick.css";</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.1.js"></script>
<!-- http://digitalbush.com/projects/masked-input-plugin/ -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.maskedinput-1.3.js"></script>
<!-- http://keith-wood.name/datepick.html -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/jquery.datepick.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/jquery.datepick.lang.js"></script>
<!-- http://p.sohei.org/jquery-plugins/columnhover/ -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.columnhover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$.datepick.setDefaults($.datepick.regional['${pageContext.response.locale.language}']);
	});
	// 로그인 팝업용
	var popStatus = 0;
	
	function centerConfirm(){
		var w_w = $(window).width();
		var w_h = $(window).height();
		var p_w = $("#confirmContent").width();
		var p_h = $("#confirmContent").height();

		$("#confirmContent").css({
			"position": "absolute",
			"top": (w_h-p_h)/2,
			"left": (w_w-p_w)/2
		});
	}
	
	function loadConfirm(obj){
		$("#confirmContent").html($(obj).html());
		centerConfirm();
		if(popStatus==0){
			$("#background").css({
				"opacity": "0.2"
			});
			$("#confirmContent", "body").css({
				"background": "#FFFFFF"
			});
			$("#background").fadeIn("slow");
			$("#confirmContent").fadeIn("slow");

			/*
			$("table").eq(5).css({
				"visibility": "hidden"
			});
			*/
			popStatus = 1;
		}
	}
	function disableConfirm(){
		if(popStatus==1){
			$("#background").fadeOut("fast");
			$("#confirmContent").fadeOut("fast");
			/*
			$("table").eq(5).css({		// 팝업 컨텐츠 히든
				"visibility": "block"
			});
			*/
			popStatus = 0;
			$("#confirmContent").html("");
		}
	}
</script>