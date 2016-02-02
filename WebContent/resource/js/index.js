
$(function(){
	
	//获取系统时间
	$.customAjax({
		url: ctx + "/home/getServerTime",
		dataType: "text",
		success: function(data){
			if(data != null && data != ''){
				showServerTime("#server_time",data);
			}
		}
	});	
	
	//获取天气
	$.customAjax({
		url: ctx + "/home/getWeather",
		data:{
			"city_name":"新宁"
		},
		dataType: "json",
		success: function(data){
			if(data){
				var weather = data.weather;
				var l_h_tmp = data.l_tmp + "~" + data.h_tmp;
				var temp = data.temp;
				$("#city_weather_info").text("新宁 " + l_h_tmp + "℃ " + weather + " ");
				src = "/Temp/resource/images/weather-day/" + data.imgName + ".png";
				$("#weather_img").attr("src",src);
				$("#weather_img").show();
			}
		}
	});
	
	$("#menu_accordion").on("click",".menu_li",function(){
		$this = $(this);
		var iconClass = $this.find("i").attr("class");
		$this.parents("#menu_accordion").find(".actived_menu").removeClass("actived_menu");
		$this.addClass("actived_menu");
		
		var $mainTab = $("#mainTab");
		//var title = $this.text();
		var htmltitle = $this.html();
		if($mainTab.tabs('exists',htmltitle)){
			$mainTab.tabs('select',htmltitle);
			return;
		}
		$mainTab.tabs('add',{
			title : htmltitle,
			selected : true,
			closable : true,
			href : ctx + "/" + $this.data("url")
		});
		
	});
	
	//用户资料
	$("#userName").click(function(){
		var $mainTab = $("#mainTab");
		if($mainTab.tabs('exists','个人资料')){
			$mainTab.tabs('select','个人资料');
			return;
		}
		
		$mainTab.tabs('add',{
			title : '个人资料',
			iconCls: 'icon-gerenxinxi',
			selected : true,
			closable : true,
			href : ctx + "/user/userInfo"
		});
	});
	
	
	//退出登录	
	$(".logout").click(function(){
		var me = $(this);
		$.messager.confirm('退出提示？','您确认要退出吗？',function(r){    
		    if (r){    
		    	me.prev("form").submit();
		    }    
		});  
	});
	
	//微信放大缩小
	$("#weixinImg").mouseover(function(e){
		if($("#largerImg").is(":hidden")){
			var top = ($(this).offset().top - 170) + 'px';
			var left = ($(this).offset().left - 70) + 'px';
			$("#largerImg").show().css({'position':'absolute','top':top,'left':left});
		}
	});
	
	$("#weixinImg").mouseout(function(e){
		$("#largerImg").hide();
	});
	
	//tab绑定菜单
	$("#mainTab").find(".tabs-header").bind('contextmenu',function(e){
		e.preventDefault();
		$('#tab-menu').menu('show', {
			left: e.pageX,
			top: e.pageY
		});
	});
	
	//刷新
	$("#tabupdate").bind("click",function(){
		var tab = $('#mainTab').tabs('getSelected');
		tab.panel('refresh');
	});
	
    //关闭当前标签页
    $("#tabclose").bind("click",function(){
    	delTab(1);
    });
    //关闭所有标签页
    $("#tabcloseall").bind("click",function(){
    	delTab(2);
    });
    //关闭非当前标签页（先关闭右侧，再关闭左侧）
    $("#tabcloseother").bind("click",function(){
    	delTab(3);
    });
    //关闭当前标签页左侧标签页
    $("#tabcloseleft").bind("click",function(){
    	delTab(4);
    });
    //关闭当前标签页右侧标签页
    $("#tabcloseright").bind("click",function(){
    	delTab(5);
    });

    /**
     * flag
     *  1:关闭当前标签页
     *  2:关闭所有标签页
     *  3:关闭非当前标签页（先关闭右侧，再关闭左侧）
     *  4:关闭当前标签页左侧标签页
     *  5:关闭当前标签页右侧标签页
     */
    function delTab(flag){
    	var tab = $('#mainTab').tabs('getSelected');
    	var index = $('#mainTab').tabs('getTabIndex',tab);
    	var title = tab.panel('options').title;
    	var tablist = $('#mainTab').tabs('tabs');
    	switch(flag){
    		case 1:
    			$('#mainTab').tabs('close',index);
    			break;
    		case 2:
    			for(var i = tablist.length ; i > 0 ; i--){
    	        	$('#mainTab').tabs('close',i);
    	        }
    			break;
    		case 3:
    			for(var i = tablist.length - 1 ; i > index ; i --){
    	        	$('#mainTab').tabs('close', i);
    	        }
    	        for(var i = index -1 ; i > 0 ; i--){
    	    		$('#mainTab').tabs('close', i);
    	    	}
    	        break;
    		case 4:
    			for(var i = index -1 ; i > 0 ; i--){
    	    		$('#mainTab').tabs('close', i);
    	    	}
    	    	break;
    		case 5:
    			for(var i = tablist.length - 1 ; i > index ; i --){
		        	$('#mainTab').tabs('close', i);
		        }
		        break;
    		default:
    				break;
    	}
    	if(title != null && typeof(title) != 'undefined' && title != '')
    		$("#mainTab").tabs('select',title);
    }

    $("#menu_accordion").find(".menu_div").hide();$($("#menu_accordion").find("div[class='panel']").get(0)).find(".menu_div").show().find(".menu_li:first").trigger("click");
});

