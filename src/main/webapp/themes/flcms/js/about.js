$(function(){
	$(".sidebar .close").click(function(){
		document.cookie="vist";
	});
	if(document.cookie.indexOf("vist") == -1){
		$(".sidebar .ad").addClass("active");
	};
	
	$("[data-toggle-class]").on("click",function(){
		var attr = $(this).data();
		var target = this;
		if(attr.target){
			target = attr.target;
		}
		$(target).toggleClass(attr.toggleClass);
	});
	
	
	//animation-banner
	var bannerLength;
	var bannerShow;
	function setNew(){
		bannerLength = $(".animation-banner .img	-box .img").length;
		bannerShow = $(".animation-banner").data("show")?
				$(".animation-banner").data("show"):1;
		$(".animation-banner .img-box .img").css({"width":10/bannerShow + "%"});
		$(".animation-banner .img-box").css({"left":"-" + 100/bannerShow + "%"});
	}
	setNew();
	$(window).resize(function(){
		setNew();
	});
	function bannerMove(a){
		var b = Math.abs(a);
		for(var c = b; c>0; c=c-1){
			dotMove(a);
			if(a>0){
				$(".animation-banner .img-box").animate({"left":"-" + 200/bannerShow + "%"}, 500/b, "linear", function(){
					$(".animation-banner .img-box .img:first").insertAfter(".animation-banner .img-box .img:last");
					$(".animation-banner .img-box").css("left",-100/bannerShow + "%");
				});
				$(".animation-banner .content").animate({top: "-" + 200/bannerShow + "%"}, 500/b, "linear", function(){
					$(".animation-banner .content div:first").insertAfter(".animation-banner .content div:last");
					$(".animation-banner .content").css("top",-100/bannerShow + "%");
				});
			}else if(a<0){
				$(".animation-banner .img-box").animate({left: 0}, 500/b, "linear", function(){
					$(".animation-banner .img-box .img:last").insertBefore(".animation-banner .img-box .img:first");
					$(".animation-banner .img-box").css("left",-100/bannerShow + "%");
				});
				$(".animation-banner .content").animate({top: 0}, 500/b, "linear", function(){
					$(".animation-banner .content div:last").insertBefore(".animation-banner .content div:first");
					$(".animation-banner .content").css("top",-100/bannerShow + "%");
				});
			}
		}
	}
	function dotMove(a){
		var oldIndex = $(".animation-banner .dots > div.active").index();
		if(a>0 && oldIndex >= bannerLength - 1){
			oldIndex = 0;
		}else if(a>0 && oldIndex < bannerLength - 1){
			oldIndex = oldIndex + 1;
		}else if(a<0 && oldIndex <= 0){
			oldIndex = bannerLength - 1;
		}else if(a<0 && oldIndex > 0){
			oldIndex = oldIndex - 1;
		}
		$(".animation-banner .dots > div").eq(oldIndex).addClass("active").siblings().removeClass("active");
	}
	$(".animation-banner .btn-left").click(function(){
		bannerMove(-1)
	});
	$(".animation-banner .btn-right").click(function(){
		bannerMove(1)
	});
	$(".animation-banner .dots > div").click(function(){
		var oldIndex = $(".animation-banner .dots > div.active").index();
		var newIndex = $(this).index();
		var a = newIndex - oldIndex;
		bannerMove(a);
	});
	var timer = setInterval(function(){
		bannerMove(1);
	},5000);
	$(".animation-banner").hover(function(){
		clearInterval(timer);
	},function(){
		timer = setInterval(function(){
			bannerMove(1);
		},5000);
	});
	
	//new显示
	function stringToDate(fDate){  
	    var fullDate = fDate.split("-");  
	    return new Date(fullDate[0], fullDate[1]-1, fullDate[2], 0, 0, 0);  
	} 
	var times;
	var nowTime;
	var eachTime;
	$(".sm-list li").each(function(){
		if(!$(this).data("time")) return;
		nowTime = new Date().getTime();
		times = stringToDate($(this).data("time")).getTime();
		eachTime = (nowTime - times)/(24 * 60 * 60 * 1000);
		if(eachTime < 5){
			$(this).addClass("new");
		}
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
})