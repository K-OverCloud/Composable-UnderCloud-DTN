
	// gnb
	$(function(){
	
	var gnb = $("#gnb > .gnb");	
	var gnbBar = $(".bar_gnb");
	var gnbDep1 = gnb.find(">li");
	var gnbDep2 = gnbDep1.find(">ul>li");
	var shadow2dep = '<span class="l"></span><span class="r"></span>';
	gnb.mouseenter(function(){
		gnbBar.stop().animate({height:34},150,function(){
			$(this).addClass("dep2_on"); //마우스가 하단에서 접근할때 동작 오류 방지용
		});
	});
	gnb.mouseleave(function(){
		gnbBar.stop().animate({height:5},150,function(){
			$(this).removeClass("dep2_on"); //마우스가 하단에서 접근할때 동작 오류 방지용
		});
	});
	gnbDep1.mouseenter(function(){//원뎁쓰 오버
		$(this).find(">ul").show();
		$(this).find(">a>div").fadeIn(300);
		
	});
	gnbDep1.mouseleave(function(){//원뎁쓰 아웃
		$(this).find(">ul").hide();
		$(this).find(">a>div").fadeOut(300);
		$(this).find(">ul>li").removeClass("hover");	
	
	});
	gnbDep2.mouseenter(function(){//투뎁쓰 오버
	
		var num3dep = parseInt($(this).find(">div>ul").length) * 23 + 90; //하위메뉴 갯수를 세어 높이를 정함
		if(gnbBar.hasClass("dep2_on")){ //gnb bar가 다 펼쳐진 경우에만 반응
			$(this).addClass("hover");
			$(this).find(">div").stop().animate({height:num3dep},300);
			$(this).find(">div>ul").show();
			$(this).find(">div>div").fadeIn(200);
			$(this).append(shadow2dep);
			$(this).find(">p").addClass("sh").show(); //투뎁쓰 없을경우 그림자
		}
	});
	gnbDep2.mouseleave(function(){//투뎁쓰 아웃
		$(this).find(">div>div").fadeOut(50);
		$(this).find(">div>ul").hide();
		$(this).removeClass("hover");	
		$(this).find(">div").stop().animate({height:0},200);
		$(this).find(">span").remove();
		$(this).find(">p").removeClass("sh_none").hide(); //투뎁쓰 없을경우 그림자
	});
	
});

 