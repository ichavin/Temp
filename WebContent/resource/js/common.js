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
 * 去除前后空格
 * @param str
 * @returns
 */
function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g,"");
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
		console.info(XMLHttpRequest);
		console.info(textStatus);
		console.info(errorThrown);
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
				break
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

 
/**
 * 生成查询条件模块
 */
function createSearchModule(lis,btns){
	var $query_div = $(".query_div");
	var html = '<legend><i class="fa fa-filter"></i> 查询条件 </legend><div><form method="post"><ul class="search_module_ul">';
	if(lis){
		var li;
		for(var i = 0 ; i < lis.length ; i++){
			li = lis[i];
			html += '<li class="search_module_li"><label>' + li.lableName + '：</label>' + createControl(li.type,li.name,li.role) + '</li>';
		}
	}
	html += '</ul></form></div><div class="clear"></div>';
	html += createSearchBtn(btns) + '</div>';
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
function createControl(type,name,role){
	var html = '<input name="' + name + '" ';
	switch(type){
		case("textbox"):
			html += 'class="easyui-textbox"';
			break;
		case("validatebox"):
			html += 'class="easyui-validatebox easyui-textbox"';
			break;
		case("datebox"):
			html += 'class="easyui-datebox"';
			break;
		case("datetimebox"):
			html += 'class="easyui-datetimebox"';
			break;			
		case("combobox"):
			html += 'class="easyui-combobox"';
			role = "panelHeight: 'auto'," + role;
			if(role.lastIndexOf(",") == role.length - 1)
				role = role.substring(0,role.length - 1);
			break;
		default:
			html += 'class="easyui-textbox"';
			break;
	
	}
	if(role == null || role == '' || typeof(role) == 'undefined'){
		html += '/>';
	}else{
		html += ' data-options="' + role + '"/>';
	}
	return html;
}

/**
 * 生成btn
 * @param btns 按钮组
 * @returns {String}
 */
function createSearchBtn(btns){
	var html = '<div class="search_module_btn_div">';
	var btn;
	for(var i = 0 ; i < btns.length ; i ++){
		btn = btns[i];
		html += '<span><a id="' + btn.id + '" href="javascript:void(0);" class="' + btn.clazz + '" options="' + btn.options + '"><i class="btn_icon fa fa-' + btn.icon + '"></i> ' + btn.name + '</a></span>';
	}
	html += '</div>';
	return html;
}

/**
 * 自定义easyui表单验证
 */









