//공백제거
function cf_trim(str){
	return str.replace(/^\s+|\s+$/g,'');
}

//바이트 수 구하기
function getByteLength(s){

	var len = 0;
	
	if(s == null) return 0;

	for(var i=0;i<s.length;i++){   							//문장의 길이만큼 LOOP
		var c = escape(s.charAt(i));  						// escape()함수는 문자를 아스키코드값으로 문자로 변환
		if(c.length == 1) len ++;         					// 아스키코드값 문자의 길이가 1이면 len값 1증가
		else if(c.indexOf("%u") != -1) len += 2;
		else if(c.indexOf("%") != -1) len += c.length/3;
	}

	return len;

}

function getRegexStr(str) {
	if(str == "amount") {
		return /(^[^0-9])|(^0.{1,})/;
	} else  if(str == "ip") {
		return /^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/;
	} else {
		return "";
	}
}

function sleep(num){
	var now = new Date();
	var stop = now.getTime() + num;
	while(true){
		now = new Date();
		if(now.getTime() > stop)return;
	}
}

function thisMovie(movieName) {
    if (navigator.appName.indexOf("Microsoft") != -1) {
    	var ua = navigator.userAgent;
    	var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
    	if (re.exec(ua) != null) {
    		rv = parseFloat(RegExp.$1);
    		if(rv == 9) 
    			return document[movieName];
    		
    		else 
    			return document[movieName];
    	}
    	else 
    		return window[movieName];
    }
    else {
        return document[movieName];
    }
}
	 