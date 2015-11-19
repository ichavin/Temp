
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
			"city_name":"深圳"
		},
		dataType: "json",
		success: function(data){
			if(data){
				var weather = data.weather;
				var l_h_tmp = data.l_tmp + "~" + data.h_tmp;
				var temp = data.temp;
				$("#city_weather_info").text("深圳 " + l_h_tmp + "℃ " + weather + " ");
				src = "/Temp/resource/images/weather-day/" + data.imgName + ".png";
				$("#weather_img").attr("src",src);
				$("#weather_img").show();
			}
		}
	});
	
	//初始化菜单
	$("#menuTree").tree({
		//url : '',
		data : [{    
		    "id":1,    
		    "text":"Folder1",    
		    "children":[{    
		    	"id" : "11",
		        "text":"File1",    
		        "url":"/demo1/a",
		    },{    
		        "text":"Books",    
		        "url":"/demo1/b"
		    }]    
		},{    
			"id":2,
		    "text":"Languages",    
		    "state":"closed",    
		    "children":[{    
		    	"id" : 21,
		    	"text":"Java",
		        "url":"/demo2/a"
		    },{    
		    	"id":22,
		    	"text":"C#",
		    	"url":"/demo2/b"
		    }]    
		}],
		method : 'POST',
		animate : true,
		onClick: function(node){
			var $tree = $("#menuTree");
			var $mainTab = $("#mainTab");
			if($tree.tree('isLeaf',node.target)){
				if($mainTab.tabs('getTab',node.text) != null){
					return ;
				}
				$mainTab.tabs('add',{
					title : node.text,
					selected : true,
					closable : true,
					href : ctx + "/login/test"
				});
			}else{
				$tree.tree('toggle',node.target);
			}
		}
	});
	
	
	//退出登录	
	$(".logout").click(function(){
		var me = $(this);
		layer.confirm('确定退出？',{icon:3,title:'提示'},function(index){
			me.prev("form").submit();
			layer.close(index);
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

});

