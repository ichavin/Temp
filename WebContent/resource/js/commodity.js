/**
 * 获取数据
 */
var commodity_getData = function(data){
	var lis_data = null;
	$.customAjax({
		url:ctx + "/commodity/getData",
		data:data,
		async:false,
		success:function(data){
			lis_data = data;
		}
	});
	var html = "";
	if(lis_data != null){
		var li_data;
		for(var i = 0 ; i < lis_data.length ; i++){
			li_data = lis_data[i];
			if(li_data.imageUrl == null || li_data.imageUrl == ''){
				src = ctx + "/resource/userimg/commodity/none.png";
			}else{
				src = ctx + li_data.imageUrl;
			}
			if(li_data.deleted == 0){
				html += '<li class="commodity_li" data-scheme_name="' + li_data.scheme.name + '" data-numberid="' + li_data.numberId + '" data-scheme_id="' + li_data.scheme.id + '" data-weight="' + li_data.weight + '" data-size="' + li_data.size + '" data-id="' + li_data.id + '" data-deleted="' + li_data.deleted + '">'
				  + '<div class="commodity_img"><img class="img" alt="' + li_data.name + '" src="' + src + '" title="点击查看详细信息"/></div>'
				  + '<span class="commodity_span_text">' + li_data.name + " - " + li_data.scheme.name + '</span>'
				  + '</li>';
			}else{
				html += '<li class="commodity_li" data-scheme_name="' + li_data.scheme.name + '" data-numberid="' + li_data.numberId + '" data-scheme_id="' + li_data.scheme.id + '" data-weight="' + li_data.weight + '" data-size="' + li_data.size + '" data-id="' + li_data.id + '" data-deleted="' + li_data.deleted + '">'
				  + '<div class="commodity_img"><img class="img" alt="' + li_data.name + '" src="' + src + '" title="点击查看详细信息"/></div>'	
				  + '<span class="commodity_span_text red">' + li_data.name + " - " + li_data.scheme.name + '</span>'
				  + '</li>';
			}
		}
	}
	html += '<li class="commodity_li more_commodity">'
		  + '<div class="commodity_img"></div>'
		  + '<span style="display:none;" class="commodity_span_text xzfa">新增产品</span>'
		  + '</li>';
	$("#commodity_ul").html(html);
}

function commodity_rename(){
	var current_id = $("#commodity_update_hidden_id").val();
	var title = $("#commodity_update").dialog('options').title;
	$("#commodity_new_name").textbox("setValue",title.split(" - ")[0]);
	$("#commodity_new_number_id").textbox("setValue",$("#commodity_update_number_id").text());
	$("#commodity_new_weight").textbox("setValue",$("#commodity_update_weight").text());
	$("#commodity_new_size").textbox("setValue",$("#commodity_update_size").text());
	$("#commodity_rename_dialog").dialog('open');
	$(".commodity_updateInfo_msg").hide();
}

function commodity_update_name(){
	if($("#commodity_updateInfo_form").form('validate')){
		var current_id = $("#commodity_update_hidden_id").val();
		$.customAjax({
			url:ctx + "/commodity/updateCommodity",
			data:{
				currentId:current_id,
				newName:$("#commodity_new_name").textbox('getValue'),
				updateNumberId:$("#commodity_new_number_id").textbox('getValue'),
				updateWeight:$("#commodity_new_weight").textbox('getValue'),
				updateSize:$("#commodity_new_size").textbox('getValue')
			},
			success:function(data){
				if(data.success){
					layer.msg('修改成功', {time: 1000});
					
					$("#commodity_update_number_id").text($("#commodity_new_number_id").textbox('getValue'));
					$("#commodity_update_weight").text($("#commodity_new_weight").textbox('getValue'));
					$("#commodity_update_size").text($("#commodity_new_size").textbox('getValue'));
					
					$("#commodity_rename_dialog").dialog('close');
					var title = $("#commodity_update").dialog('options').title;
					$("#commodity_update").dialog({title:$("#commodity_new_name").textbox('getValue') + " - " + title.split(" - ")[1]});
					commodity_getData(null);
				}else{
					if(data.code != null){
						$(".commodity_updateInfo_msg").show().text(data.detailInfo);
					}else{
						layer.msg('修改失败,' + data.detailInfo, {time: 1000});
					}
				}
			}
		});
	}
}


function deleted_current_commodity(ths){
	var current_id = $("#commodity_update_hidden_id").val();
	var deleted = $(ths).data('deleted');
	if(deleted == 0){
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		        $.customAjax({
		        	url:ctx + "/commodity/deleteCommodity",
		        	data:{
		        		currentId:current_id,
		        	},
		        	success:function(data){
		        		if(data.success){
		        			layer.msg('删除成功', {time: 1000});
							$("#commodity_update").dialog('close');
							commodity_getData(null);
						}else{
							layer.msg(data.detailInfo, {time: 3000});
						}
		        	}
		        });    
		    }    
		}); 
	}else{
		$.customAjax({
        	url:ctx + "/commodity/revertDeleteScheme",
        	data:{
        		currentId:current_id,
        	},
        	success:function(data){
        		if(data.success){
        			layer.msg('撤销删除成功', {time: 1000});
					$("#commodity_update").dialog('close');
					commodity_getData(null);
				}else{
					layer.msg(data.detailInfo, {time: 3000});
				}
        	}
        });
	}
}

function commodity_change_img(){
	var src = $("#commodity_update_img").attr("src");
	if(src == null || src == undefined || src == ''){
		src = ctx + "/resource/userimg/commodity/none.png";
	}
	$("#commodity_change_img_id").val($("#commodity_update_hidden_id").val());
	$("#commodity_change_img_dialog").dialog('open');
	$("#commodity_new_img").attr("src",src);
	$("#commodity_change_img_file").filebox('clear');
}



function commodity_change_img_sure(){
	if($("#commodity_change_img_file").filebox('isValid')){
		$("#commodity_change_img_form").submit();
	}else{
		$("#commodity_change_img_file").filebox('validate');
	}
}

function commodity_change_scheme(){
	$("#commodity_change_scheme_dialog").dialog('open');
	$("#commodity_new_scheme_cmb").combobox('clear')
	var title = $("#commodity_update").dialog('options').title;
	$(".commodity_old_schem_name").text(title.split(" - ")[1]);
}

function commodity_change_scheme_sure(){
	if($("#commodity_new_scheme_cmb").combobox('isValid')){
		$.customAjax({
			url: ctx + "/commodity/changeScheme",
			data:{
				commodityId:$("#commodity_update_hidden_id").val(),
				schemeId:$("#commodity_new_scheme_cmb").combobox('getValue')
			},
			success:function(data){
				layer.msg('更换方案成功', {time: 1000});
				$("#commodity_change_scheme_dialog").dialog('close');
				$("#commodity_update").dialog('close');
				commodity_getData(null);
			}
		});
	}else{
		$("#commodity_new_scheme_cmb").combobox('validate')
	}
}


function commodity_add_commodity_sure(){
	if($("#commodity_insert_commodity_form").form('validate')){
		if($("#commodity_add_img_file").filebox('getValue') != ''){
			$("#commodity_insert_commodity_form").submit();
		}else{
			var newName = $("#commodity_add_commodity_name").textbox('getValue');
			var schemId = $("#commodity_add_scheme_cmb").combobox('getValue');
			var numberId = $("#commodity_add_number_id").textbox('getValue');
			var weight = $("#commodity_add_weight").textbox('getValue');
			var size = $("#commodity_add_size").textbox('getValue');
			$.customAjax({
				url: ctx + "/commodity/insertCommodityWithoutImg",
				data:{
					inserCommodityName:newName,
					insertSchemeId:schemId,
					insertNumberId:numberId,
					insertWeight:weight,
					insertSize:size
				},
	            success: function (data) {
	            	console.info(data);
	            	if(data.success){
	            		layer.msg('新增成功', {time: 1000});
	            		$("#commodity_insert_dialog").dialog('close');
	            		commodity_getData(null);
	            	}else{
	            		if(data.code != null){
	            			$(".commodity_add_msg").text(data.detailInfo).show();
	            		}else{
	            			$.messager.alert('警告',data.detailInfo);
	            		}
	            		
	            	}
	            },
	            error: function (msg) {
	            	$.messager.alert('警告',"系统错误");
	            }
			});
		}
	}
}


$(function(){
	
	commodity_getData(null);

	/**
	 * 上传图片
	 */
	$("#commodity_change_img_form").submit(function(){
		$("#commodity_change_img_form").ajaxSubmit({
			type: "post",
            url: ctx + "/commodity/changeImage",
            success: function (data) {
            	if(data.success){
            		layer.msg('修改成功', {time: 1000});
            		$("#commodity_update_img").attr("src",ctx + data.object);
            		$("#commodity_change_img_dialog").dialog('close');
            		commodity_getData(null);
            	}else{
            		$.messager.alert('警告',data.detailInfo);
            	}
            },
            error: function (msg) {
            	$.messager.alert('警告',"系统错误");
            }
		});
		return false;
	});
	
	
	/**
	 * 上传图片
	 */
	$("#commodity_insert_commodity_form").submit(function(){
		$("#commodity_insert_commodity_form").ajaxSubmit({
			type: "post",
            url: ctx + "/commodity/insertCommodity",
            success: function (data) {
            	if(data.success){
            		layer.msg('新增成功', {time: 1000});
            		$("#commodity_insert_dialog").dialog('close');
            		commodity_getData(null);
            	}else{
            		$.messager.alert('警告',data.detailInfo);
            	}
            },
            error: function (msg) {
            	$.messager.alert('警告',"系统错误");
            }
		});
		return false;
	});
	
	$("body").on("mouseover",".more_commodity",function(){
		$(".xzfa").css("display","inline-table");
	}).on("mouseout",".more_commodity",function(){
		$(".xzfa").css("display","none");
	});
	
	/**
	 * 初始化查询模块
	 */
	function autoCreateSearchModule(){
		var lis = new Array();
		var meterialData = new Array();
		var schemeData = new Array();
		$.customAjax({
			url:ctx + '/scheme/getSchemeSelectList',
			async:false,
			success:function(data){
				if(data != null)
					schemeData = data;
			}
		});
		lis.push({"lableName":"产品名称","type":"textbox","id":null,"name":"name"});
		lis.push({"lableName":"包含方案","type":"combobox","id":"includeScheme","name":"scheme_id","role":"panelHeight:'auto',editable:false,panelMaxHeight:160,multiple:true,valueField: 'value',textField: 'text',data: " + JSON.stringify(schemeData).replace(/"/g,'\'')});
		lis.push({"lableName":"删除标志","type":"combobox","id":null,"name":"deleted","role":"panelHeight:'auto',editable:false,panelMaxHeight:160,valueField: 'value',textField: 'text',value:'-1',data: [{value:'-1',text:'全部'},{value:'0',text:'未删除'},{value:'1',text:'已删除'}]"});
		var btns = new Array();
		btns.push({"id":"clear_btn","clazz":"easyui-linkbutton","options":"","icon":"circle-o","name":"清空"});
		btns.push({"id":"search_btn","clazz":"easyui-linkbutton","options":"","icon":"search","name":"查询"});
		createSearchModule(lis,btns,"commodity");
		$(".commodity .easyui-validatebox").validatebox();
		$(".commodity .easyui-textbox").textbox();
		$(".commodity .easyui-combobox").combobox();
		$(".commodity .easyui-linkbutton").linkbutton();
	}
	
	autoCreateSearchModule();
	

	/**
	 * 查询
	 */
	$(".commodity .query_div").on("click","#commodity_search_btn",function(){
		var $form = $(this).parents(".query_div").find("form");
		var formJSON = $form.serialize();
		commodity_getData(formJSON);
	});


	/**
	 * 清空
	 */
    $(document).on("click","#commodity_clear_btn",function(){
    	var $form = $(this).parents(".query_div").find("form");
    	$form.form('reset');
    	$("#includeScheme").combobox('clear');
    	commodity_getData(null);
    });
	
   
    /**
     * panel
     */
	$("#commodity_content").panel({
		  fit:true,
		  title: '产品列表'
	});
	
	/**
	 * panel的大小
	 */
	function panelresize(){
		$("#commodity_contain").css('height',($(".commodity").height() - $(".commodity .query_div").height() - 24) + "px");
		$("#commodity_content").panel('resize',{});
	}
	
	$(window).resize(function(){
		setTimeout(panelresize,100);	
	});
	
	panelresize();
	
	
	/**
	 * 绑定每个产品方案的click事件
	 */
	var editIndex = undefined;
	$("body").on("click",".commodity_li",function(){
		var current_id = $(this).data("id");
		var deleted = $(this).data("deleted");
		if($(this).attr("class").indexOf("more_commodity") != -1){
			$("#commodity_insert_dialog").dialog({title:'新增产品'}).dialog('open');
			$("#commodity_insert_new_img").attr("src","");
			$("#commodity_add_scheme_cmb").combobox('clear');
			$("#commodity_add_img_file").filebox('clear');
			$(".commodity_add_msg").hide().text();
		}else{
			var src = $(this).find("img").attr("src");
			$("#commodity_update_img").attr("src",src); 
			var scheme_id = $(this).data("scheme_id");
			var scheme_name = $(this).data("scheme_name");
			$("#commodity_update_number_id").text($(this).data("numberid"));
			$("#commodity_update_weight").text($(this).data("weight"));
			$("#commodity_update_size").text($(this).data("size"));
			
			$("#commodity_update").dialog({title:$(this).text()}).dialog('open');
			$("#deleted_commodity").data("deleted",deleted);
			if(deleted == 1){
				$("#deleted_commodity").linkbutton({text:'<i class="btn_icon fa fa-share"></i> 撤销删除产品'});
			}else{
				$("#deleted_commodity").linkbutton({text:'<i class="btn_icon fa fa-times-circle"></i> 删除当前产品'});
			}
			$("#commodity_update_hidden_id").val(current_id);
			
			$("#commodity_meterial_dg").datagrid({
				noheader:true,
				url: ctx + '/scheme/getSchemeMeterialData',
				queryParams: {
					scheme_id:scheme_id
				},
				fitColumns: true,
				fit: true,
				singleSelect: true,
				rownumbers: true,
				autoRowHeight:false,
				columns:[[
					{field:'id',title:'id',hidden:true,width:100},
					{field:'meterial.name',title:'材料名称',width:200,
						formatter: function(value,row,index){
							return row.meterial.name;
						}
					},    
					{field:'unit.name',title:'单位',width:120,
						formatter: function(value,row,index){
							return row.meterial.unit.name;
						}
					},
					{field:'amount',title:'数量',width:120
					}
				]]
			});
		}
	});
	
	$("#commodity_add_scheme_cmb").combobox({
		editable:true,
		onHidePanel:function(){
			if(!validCustomSelect("#commodity_add_scheme_cmb")){
				$("#commodity_add_scheme_cmb").combobox('clear');
			}
		}
	});
	
});

