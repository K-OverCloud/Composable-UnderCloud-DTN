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
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/swfobject_modified.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$.datepick.setDefaults($.datepick.regional['${pageContext.response.locale.language}']);
	});
	// 로그인 팝업용
	var popStatus = 0;
	function centerPop(){
		var w_w = $(window).width();
		var w_h = $(window).height();
		var p_w = $("#popContent").width();
		var p_h = $("#popContent").height();

		$("#popContent").css({
			"position": "absolute",
			"top": (w_h-p_h)/2,
			"left": (w_w-p_w)/2
		});
	}
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
	function loadPop(url){
		centerPop();
		if(popStatus==0){
			$("#background").css({
				"opacity": "0.2"
			});
			$("#popContent").css({
				"opacity": "1"
			});
			$("#_popFrame").attr('src', url);
			$("#_popFrame", "body").css({
				"background": "#FFFFFF"
			});
			$("#background").fadeIn("slow");
			$("#popContent").fadeIn("slow");

			/*
			$("table").eq(5).css({
				"visibility": "hidden"
			});
			*/
			popStatus = 1;
		}
	}
	function disablePop(){
		if(popStatus==1){
			$("#background").fadeOut("slow");
			$("#popContent").fadeOut("slow");
			/*
			$("table").eq(5).css({		// 팝업 컨텐츠 히든
				"visibility": "block"
			});
			*/
			popStatus = 0;
			$("#_popFrame").attr('src','');
		}
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
	
	function localeSet(locale) {
		$.ajax( 
			{
				type : "GET",
				url : "${pageContext.request.contextPath}/localeChange.json?cache=" + Math.round(Math.random()* (new Date().getTime())),
				contentType : "application/json; charset=utf-8",
				dataType: "json",
				data : ({lang : locale}),
		        success : 	function(data) {
		        				window.location.reload(true);
		        			},
				error : 	function(data) {
							}
			}
		);
	}
	
	function loginPop() {
		$("#popContent").find("#_popFrame").width(458);
		$("#popContent").find("#_popFrame").height(450);
		loadPop('${pageContext.request.contextPath}/user/pop/loginF.do');
	}
</script>
<script type="text/javascript">
	var curr1 ="01"; 
	var curr2 = "header_about".replace("header_","");
	function SetDefaultMenuOn() {
		var ClassNM = "rollover";
		var obj = document.getElementsByName(ClassNM );
		for(i=0; i<obj.length;i++) {
			if(obj[i].getAttribute("src").indexOf(curr1)>0 && obj[i].getAttribute("src").indexOf(curr2)>0) {
				obj[i].src = obj[i].src.replace("_off.gif", "_on.gif");  
				obj[i].setAttribute("class", ClassNM+"_OFF");
				break;
			}
		}
	}
	
	function MM_swapImgRestore() { //v3.0
		var i, x, a = document.MM_sr; 
		for(i=0; a&&i<a.length&&(x=a[i])&&x.oSrc; i++) 
			x.src=x.oSrc;
	}
	
	function MM_preloadImages() { //v3.0
		var d=document; 
		if(d.images) { 
			if(!d.MM_p) 
				d.MM_p = new Array();
			var i, j = d.MM_p.length, a = MM_preloadImages.arguments; 
			for(i=0; i<a.length; i++)
				if (a[i].indexOf("#")!=0) { 
					d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];
				}
		}
	}

	function MM_findObj(n, d) { //v4.01
		var p,i,x;  
		if(!d) 
			d=document; 
		if((p=n.indexOf("?"))>0&&parent.frames.length) {
			d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);
		}
		if(!(x=d[n])&&d.all) 
			x=d.all[n]; 
		for (i=0; !x&&i<d.forms.length; i++) 
			x=d.forms[i][n];
		for(i=0; !x&&d.layers&&i<d.layers.length; i++) 
			x=MM_findObj(n,d.layers[i].document);
		if(!x && d.getElementById) 
			x=d.getElementById(n); 
		return x;
	}

	function MM_swapImage() { //v3.0
		var i, j = 0, x, a = MM_swapImage.arguments; 
		document.MM_sr=new Array; 
		for(i=0; i<(a.length-2); i+=3)
			if ((x=MM_findObj(a[i]))!=null) {
				document.MM_sr[j++] = x; 
				if(!x.oSrc) 
					x.oSrc = x.src; 
				x.src=a[i+2];
			}
	}
</script>