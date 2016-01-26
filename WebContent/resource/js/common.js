
//全选
function checkAll(ths){
	var $checkboxs = $("." + $(ths).data("modul") + "all_columns").find("input[type='checkbox']");
	var columns = new Array();
	$checkboxs.each(function(){
		if(!$(this).is(":checked")){
			$(this).prop("checked", true)
		}
	});
}

//撤销全选
function clearAllChecked(ths){
	var $checkboxs = $("." + $(ths).data("modul") + "all_columns").find("input[type='checkbox']");
	var columns = new Array();
	$checkboxs.each(function(){
		if($(this).is(":checked")){
			$(this).removeAttr("checked");
		}
	});
}

//设置显示隐藏列
function settingShowHideColumn(ths){
	var id = $(".datagrid:visible").find(".datagrid-f").attr("id");
	var $checkboxs = $("." + $(ths).data("modul") + "all_columns").find("input[type='checkbox']");
	var $ths = $("#" + id);
	$checkboxs.each(function(){
		if($(this).is(":checked")){
			$ths.datagrid('showColumn',$(this).attr("value"));
		}else{
			$ths.datagrid('hideColumn',$(this).attr("value"));
		}
	});
	$('#' + $(ths).data("modul") + 'show_hide_column').dialog('close');
}

/**
 * 判断字符串是否为空
 * @param strVal
 * @returns
 */
function isNullOrEmpty(strVal) {
	if (strVal == '' || strVal == null || strVal == undefined) {
		return true;
	} else {
		return false;
	}
}

/**
 * JSON转为URL后的参数
 * @param url
 * @param object
 * @returns
 */
function createURL (url,object){
	var param = "";
	for(var key in object){
		if(object[key] != null && object[key] != '')
			param +=  key + "=" + object[key] + "&";
	}
    if(param.lastIndexOf("&") == param.length - 1){
    	param = param.substring(0 , param.length-1);
    }
    if(param != ''){
    	return (url + "?" + param).replace(' ','');
    }else{
    	return url;
    }
}

/**
 * 去除前后空格
 * @param str
 * @returns
 */
function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g,"");
}

function toDateTimeStr(time){
	var date = new Date(time);  //时间
	var year = date.getFullYear();//年
	var month = date.getMonth() + 1; //月
	month = month < 10 ? ("0" + month) : month;
	var day = date.getDate(); //日
	day = day < 10 ? ("0" + day) : day;
	var hour = date.getHours();//时
	hour = hour < 10 ? ("0" + hour) : hour;
	var minute = date.getMinutes();//分
	minute = minute < 10 ? ("0" + minute) : minute;
	var second = date.getSeconds(); //秒
	second = second < 10 ? ("0" + second) : second;
	return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
}


/**
 * 自己封装了一下ajax
 * 为所有的ajax请求添加全局的默认选项
 */
var loading = null;
var defaults = {
	'isLoading' : null,
	'type' : 'post',
	'dataType' : "json",
	'async' : 'true',
	'data' : {},
	'dataFilter' : function(data,type){
		if(loading != null)
			layer.close(loading);
		return data;
	},
	'success' : function(data){},
	'error' : function(XMLHttpRequest, textStatus, errorThrown){
		layer.open({
		    content: '系统错误，如有需要请联系管理员',
		    icon: 2,
		    title:'系统错误'
		});
	},
	'complete' : function(XMLHttpRequest, textStatus){
		var data = XMLHttpRequest.responseText;
		var content = '';
		if(data){
			switch (data){
			case "timeout":
				content="长时间未操作，请重新登录";
				break;
			case "autoLoginTimeOut":
				content="自动登录超过有效期，请重新登录";
				break;
			case "autoLoginInvalid":
				content="自动登录失败，请重新登录";
				break;
			default:
				break;
			}
			if(content){
				layer.alert(content, function(index){
	        		window.location.href = ctx;
	        		layer.close(index);
	        		return;
	        	});
			}
		}
	}
};

jQuery.customAjax = function(options){
	var opts = $.extend({}, defaults , options||{});
	if(opts.isLoading != null ){
		loading = layer.msg(opts.isLoading, {icon: 16,shade: [0.4, '#393D49'], time: 0});
	}
	$.ajax({
		url : opts.url,
		type : opts.type,
		dataType : opts.dataType,
		async : opts.async,
		dataFilter : opts.dataFilter,
		data : opts.data,
		success : opts.success,
		complete : opts.complete,
		error : opts.error
	});
}

//表单序列化成JSON
$.fn.serializeJson =  function(){  
    var serializeObj={};  
    var array=this.serializeArray();  
    var str=this.serialize();  
    $(array).each(function(){  
        if(serializeObj[this.name]){  
            if($.isArray(serializeObj[this.name])){  
                serializeObj[this.name].push(this.value);  
            }else{  
                serializeObj[this.name]=[serializeObj[this.name],this.value];  
            }  
        }else{  
            serializeObj[this.name]=this.value;   
        }  
    });  
    return serializeObj;  
};  


//验证用户输入的值是否存在于下拉列表中
function validCustomSelect(selectId){
	var customValue = $(selectId).combobox('getText');
	var objs = $(selectId).combobox('getData');
	for(var i = 0 ; i < objs.length ; i++){
		if(objs[i].text.trim() == customValue){
			return true;
		}
	}
	return false;
}

//验证下拉列表是否有此值，如果没有则手动加进去，如果有则选中它
function validComboboxValExist(selectId,val){
	var objs = $(selectId).combobox('getData');
	var flag = false;
	for(var i =  0 ; i< objs.length ; i++){
		if(objs[i] == val){
			$(selectId).combobox('select',val);
			flag = true;
			break;
		}
	}
	if(!flag){
		
	}
}

 
/**
 * 生成查询条件模块
 */
function createSearchModule(lis,btns,modulId){
	var $query_div = $("." + modulId + " .query_div");
	var html = '<legend><i class="fa fa-filter"></i> 查询条件 </legend><div><form method="post"><ul class="search_module_ul">';
	if(lis){
		var li;
		for(var i = 0 ; i < lis.length ; i++){
			li = lis[i];
			html += '<li class="search_module_li"><label>' + li.lableName + '：</label>' + createControl(li.type,li.name,li.id,li.role,null,modulId) + '</li>';
		}
	}
	html += '</ul></form></div><div class="clearfix"></div>';
	html += createSearchBtn(btns,modulId) + '</div>';
	$query_div.html(html);
	$query_div.show();
}

/**
 * 
 * @param type 控件类型
 * @param name 表单输入框的name
 * @param role 控件校验规则及属性
 * @returns 拼接好的控件的html代码
 */
function createControl(type,name,id,role,val,modulId){
	var html;
	switch(type){
		case("textbox"):
			html ='<input style="width:170px;" name="' + name + '" class="easyui-textbox"';
			break;
		case("validatebox"):
			html ='<input style="width:170px;" name="' + name + '" class="easyui-validatebox easyui-textbox"';
			break;
		case("datebox"):
			html ='<input style="width:170px;" name="' + name + '" class="easyui-datebox"';
			break;
		case("datetimebox"):
			html ='<input style="width:170px;" name="' + name + '" class="easyui-datetimebox"';
			break;			
		case("combobox"):
			html = '<input style="width:170px;" name="' + name + '" class="easyui-combobox"';
			break;
		case("rangeTime"):
			html = '<input style="width:170px;" id="' + id + 'startTime" name="' + id + 'startTime" class="easyui-datebox" data-options="' + role + '"/>' + ' - ' + '<input style="width:170px; id="' + id + 'endTime" name="' + id + 'endTime" class="easyui-datebox" data-options="' + role + '"/>';
			break;
		default:
			html = '<input style="width:170px;" name="' + name + '" class="easyui-textbox"';
			break;
	
	}
	if(id != null && type != 'rangeTime'){
		html += ' id="' + id + '"';
	}
	if(type != 'rangeTime'){
		if(role == null || role == '' || typeof(role) == 'undefined'){
			html += '/>';
		}else{
			html += ' data-options="' + role + '"/>';
		}
	}
	return html;
}

/**
 * 生成btn
 * @param btns 按钮组
 * @returns {String}
 */
function createSearchBtn(btns,modulId){
	var html = '<div class="search_module_btn_div">';
	var btn;
	for(var i = 0 ; i < btns.length ; i ++){
		btn = btns[i];
		html += '<span><a id="' + modulId + "_" + btn.id + '" href="javascript:void(0);" class="' + btn.clazz + '" options="' + btn.options + '"><i class="btn_icon fa fa-' + btn.icon + '"></i> ' + btn.name + '</a></span>';
	}
	html += '</div>';
	return html;
}

/**
 * 生成
 * @param lis
 * @param btns
 * @returns {String}
 */
function createAddUpdateModule(lis,btns,modulId){
	var html = '<div class="padding_top_15"><form method="post"><ul class="search_module_ul">';
	if(lis){
		var li;
		for(var i = 0 ; i < lis.length ; i++){
			li = lis[i];
			html += '<li class="search_module_li"><label>' + li.lableName + '：</label>' + createControl(li.type,li.name,li.id,li.role,li.val,modulId) + '</li>';
		}
	}
	html += '</ul></form><div class="clear"></div></div>';
	html += createSearchBtn(btns,modulId);
	return html;
}

/**
 * 扩展easyui的校验规则
 */
$.extend($.fn.validatebox.defaults.rules, { 
	ch: {  
        validator: function (value, param) {  
            return /^[\u0391-\uFFE5]+$/.test(value);  
        },  
        message: '请输入汉字'  
    },  
    img:{
    	validator : function(value){
    		value = value.toLowerCase();
    		return (value.indexOf(".jpeg") != -1 || value.indexOf(".jpg") != -1 || value.indexOf(".png") != -1);
    	},
    	message: '请选择.jpeg、.jpg或.png图片'
    },
    english : {// 验证英语  
        validator : function(value) {  
            return /^[A-Za-z]+$/.test(value);  
        },  
        message : '请输入英文'  
    },  
    number: {  
        validator: function (value, param) {  
            return /^\d+(\.\d+)?$/.test(value);  
        },  
        message: '请输入数字'
    },
    moreThanNumber:{  
        validator:function(value,param){  
            if(/^[1-9]\d*$/.test(value)){  
                return value > param[0];
            }else{  
                return false;  
            }  
        },  
        message:'输入大于{0}的数字'
    },
    phone: {  
        validator: function (value, param) {  
            return /^(13|14|15|17|18)\d{9}$/.test(value);  
        },
        message: '手机号码不正确'  
    },  
    telNum: {  
        validator:function(value,param){  
            return /^(0\d{2,3}-\d{7})$/.test(value);  
        },  
        message:'电话号码不正确格式为：{xxx-xxx}'  
    },  
    money:{  
        validator: function (value, param) {  
            return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);  
         },  
         message:'请输入正确的金额'
    }, 
    int:{  
        validator:function(value,param){  
            return /^[+]?[0-9]\d*$/.test(value);  
        },  
        message: '请输入正整数'
    },
    range:{  
        validator:function(value,param){  
            if(/^[1-9]\d*$/.test(value)){  
                return value >= param[0] && value <= param[1]  
            }else{  
                return false;  
            }  
        },  
        message:'输入的数字在{0}到{1}之间'
    },  
    minLength:{  
        validator:function(value,param){  
            return value.length >=param[0]  
        },  
        message:'至少输入{0}个字'  
    },  
    maxLength:{  
        validator:function(value,param){  
            return value.length<=param[0]  
        },  
        message:'最多{0}个字'  
    },  
    idCode:{  
        validator:function(value,param){  
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },  
        message: '请输入正确的身份证号'  
    },
    compareDate:{
        validator: function (value, param) {
        	var d1 = new Date($(param[0]).datebox('getValue'));   //开始日期
        	var d2 = new Date(value);                             //结束日期
        	if(d1.getTime() > d2.getTime()){
        		return false;
        	}else{
        		return true;
        	}
        },
        message: '开始日期不能大于结束日期'
    }
    
});

//----------------------------------百度地图--------------------------------------------
//显示地址信息窗口
function showLocationInfo(BMap,marker,pt, rs){
    var opts = {
      width : 250,     //信息窗口宽度
      height: 100,     //信息窗口高度
      title : ""  //信息窗口标题
    }
     
    var addComp = rs.addressComponents;   //地址解析
    
    var addr = "当前位置：" + addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber + "<br/>";
    addr += "纬度: " + pt.lat + ", " + "经度：" + pt.lng;
    $("#ditu_update_sxdz").textbox('setValue',addr.substr(5,addr.indexOf("<br/>") -5));
    var infoWindow = new BMap.InfoWindow(addr, opts);  //创建信息窗口对象
    marker.openInfoWindow(infoWindow);
   
}


function showMap(x,y){
	var map = new BMap.Map("contain_map");//初始化地图                    
	map.addControl(new BMap.NavigationControl());  //初始化地图控件              
	map.addControl(new BMap.ScaleControl());                   
	map.addControl(new BMap.OverviewMapControl());              
	var point=new BMap.Point(x,y);
	map.centerAndZoom(point, 15);//初始化地图中心点
	var marker = new BMap.Marker(point); //初始化地图标记
	marker.enableDragging(); //标记开启拖拽
	 
	var gc = new BMap.Geocoder();//地址解析类
	//添加标记拖拽监听
	marker.addEventListener("dragend", function(e){
	    //获取地址信息
	    gc.getLocation(e.point, function(rs){
	    	showLocationInfo(BMap,marker,e.point, rs);
	    });
	});
	 
	//添加标记点击监听
	marker.addEventListener("click", function(e){
	   gc.getLocation(e.point, function(rs){
	        showLocationInfo(BMap,marker,e.point, rs);
	    });
	});
	 
	map.centerAndZoom(point, 15); //设置中心点坐标和地图级别
	map.addOverlay(marker); //将标记添加到地图中
}

function getValAndShow(val){
	var x = 116.404;
	var y = 39.915;
	if(val){
		// 创建地址解析器实例
		var myGeo = new BMap.Geocoder();
		// 将地址解析结果显示在地图上,并调整地图视野
		myGeo.getPoint(val, function(point){
			if (point) {
	          x = point.lng;
	          y = point.lat;
	          showMap(x,y);
			}else{
				/*layer.alert('未搜索到此地址，请检查后重新输入',{
				    icon: 2,
				    title:'提示'
				});*/
				$.messager.alert('警告','未搜索到此地址，请检查后重新输入');
				return;
			}
		});
	}else{
		showMap(x,y);
	}
}
//----------------------------------百度地图--------------------------------------------






















