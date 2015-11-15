$(function(){
	
	$("body").on('mouseover','#weixinImg',function(){
		var top = $(this).offset().top;
		var left = $(this).offset().left;
		$("#largerImg").css({position:absolute,top:30,left:300});
		$("#largerImg").show();
	});
	
	$("#weixinImg").mouseover(function(e){
		$("#largerImg").hide();
		$("#txt").text();
		
	});
});