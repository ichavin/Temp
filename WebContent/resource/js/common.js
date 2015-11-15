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
 * 为所有的ajax请求添加全局的默认选项
 */
//$.ajaxSetup({
//	error : function(XMLHttpRequest, textStatus, errorThrown) {
//		layer.open({
//		    content: '系统错误，请联系管理员',
//		    icon: 2,
//		    title:'系统错误'
//		});
//	},
//	complete: function (XMLHttpRequest, textStatus) {
//        var data = XMLHttpRequest.responseText;
//        if (data == "timeout") {
//        	layer.alert('长时间未操作，请重新登录', function(index){
//        		window.location.href = ctx;
//        	});
//        }
//    }
//});
var loading = null;
var defaults = {
	'isLoading' : 'true',
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
        if (data == "timeout") {
        	layer.alert('长时间未操作，请重新登录', function(index){
        		window.location.href = ctx;
        		return;
        	});
        }
	}
};

	
jQuery.customAjax = function(options){
	var opts = $.extend({}, defaults , options||{});
	if(opts.isLoading != null ){
		//loading = layer.load(2,{shade: [0.3, '#bbb'],offset: '220px'});
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













