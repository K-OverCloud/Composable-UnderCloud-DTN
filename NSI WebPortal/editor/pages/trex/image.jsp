<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" 		uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Daum에디터 - 이미지 첨부</title> 
<script src="../../js/popup.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="../../css/popup.css" type="text/css"  charset="utf-8"/>
<script type="text/javascript">
// <![CDATA[
	
	function done() {
		/*
		if (typeof(execAttach) == 'undefined') { //Virtual Function
	        return;
	    }
		var _mockdata = {
			'imageurl': "${fileInfo.url}",
			'filename': "${fileInfo.name}",
			'filesize': "${fileInfo.size}",
			'imagealign': 'C',
			'originalurl': "${fileInfo.url}",
			'thumburl': "${fileInfo.url}"
		};
		execAttach(_mockdata);
		*/
		opener.appendContent('<p style="text-align: center; "><img src="${fileInfo.url}" class="txc-image" style="clear:none;float:none;" /></p>');
		closeWindow();
	}

	function initUploader(){
	    var _opener = PopupUtil.getOpener();
	    if (!_opener) {
	        alert('잘못된 경로로 접근하셨습니다.');
	        return;
	    }
	    
	    var _attacher = getAttacher('image', _opener);
	    registerAction(_attacher);
	}
	
	function imageSave() {
		document.insFrm.submit();
	}
// ]]>
</script>
</head>
<body onload="initUploader();">
<form name="insFrm" id="insFrm" action="${pageContext.request.contextPath}/editor/pages/trex/imageUpload.do" method="post" enctype="multipart/form-data">
<div class="wrapper">
	<div class="header">
		<h1>사진 첨부</h1>
	</div>	
	<c:if test="${msgInfo == null}">
	<div class="body">
		<dl class="alert">
		    <dt>사진 첨부 하기</dt>
		    <dd>
		    	사진 파일을 선택하세요.
			</dd>
			<dd><input type="file" name="file" /></dd>
		</dl>
	</div>
	</c:if>
	<c:if test="${msgInfo != null}">
	<div class="body">
		<dl class="alert">
		    <dt>사진 첨부 확인</dt>
		    <dd>
		    	확인을 누르시면 <b>[${fileInfo.name}]</b> 가 사진첨부 됩니다.<br /> 
			</dd>
		</dl>
	</div>
	</c:if>
	<div class="footer">
		<p><a href="#" onclick="closeWindow();" title="닫기" class="close">닫기</a></p>
		<ul>
			<li class="submit">
			<c:if test='${msgInfo == null}'>
				<a href="#" onclick="imageSave();" title="등록" class="btnlink">등록</a>
			</c:if>
			<c:if test='${msgInfo != null}'>
				<a href="#" onclick="done();" title="등록" class="btnlink">등록</a>
			</c:if>
			</li>
			<li class="cancel"><a href="#" onclick="closeWindow();" title="취소" class="btnlink">취소</a></li>
		</ul>
	</div>
</div>
</form>
<c:if test="${msgInfo != null}">
<script type="text/javascript">
	if("${msgInfo.result}") {
	} else {
		alert("${msgInfo.msg}");	
	}
</script>	
</c:if>
</body>
</html>