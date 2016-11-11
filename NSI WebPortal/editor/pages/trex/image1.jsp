<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>        
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="../../js/popup.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="../../css/popup.css" type="text/css"  charset="utf-8"/>
<script type="text/javascript">
// <![CDATA[
	function upload() {
		document.image_upload.submit();
	}
	
	function done() {
		if (typeof(execAttach) == 'undefined') { //Virtual Function
	        return;
	    }
		
		var _mockdata = {
			'imageurl': '${pageContext.request.contextPath}${requestScope.i_path}',
			'filename': 'editor_bi.gif',
			'filesize': '${requestScope.i_size}',
			'imagealign': 'C',
			'originalurl': '${pageContext.request.contextPath}${requestScope.i_path}',
			'thumburl': '${pageContext.request.contextPath}${requestScope.i_path}'
		};
		execAttach(_mockdata);
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
// ]]>
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body onload="initUploader();">
<div class="wrapper">
	<div class="header">
		<h1>사진 첨부</h1>
	</div>
	<c:choose>
		<c:when test="${requestScope.i_original == null}">
			<div class="body">
			  	<form name="image_upload" id="image_upload" action="uploadImage.jsp" method="post" enctype="multipart/form-data" accept-charset="utf-8">
					<dl class="alert">
					    <dt>사진 첨부 하기</dt>
					    <dd>
			    	    	<input type="file" name="uploadImg" size="30">vvvvvvvvvvvvvvvv
						</dd>
					</dl>
				</form>
			</div>
			<div class="footer">
				<p><a href="#" onclick="closeWindow();" title="닫기" class="close">닫기</a></p>
				<ul>
					<li class="submit"><a href="#" onclick="upload();" title="업로드" class="btnlink">업로드</a> </li>
					<li class="cancel"><a href="#" onclick="closeWindow();" title="취소" class="btnlink">취소</a></li>
				</ul>
			</div>
		</c:when>
		<c:otherwise>
			<div class="body">
				<dl class="alert">
				    <dt>사진 첨부 확인</dt>
				    <dd>
				    	확인을 누르시면 <b>"${requestScope.i_original}"</b> 가 사진첨부 됩니다.<br /> 
					</dd>
				</dl>
			</div>
			<div class="footer">
				<p><a href="#" onclick="closeWindow();" title="닫기" class="close">닫기</a></p>
				<ul>
					<li class="submit"><a href="#" onclick="done();" title="등록" class="btnlink">등록</a> </li>
					<li class="cancel"><a href="#" onclick="closeWindow();" title="취소" class="btnlink">취소</a></li>
				</ul>
			</div>
		</c:otherwise>
	</c:choose>	
</div>

</body>
</html>