//新增样品图片
function order_insert_img(){
	$("#order_insert_img_dialog").dialog('open');
}

//确定新增样品图片
function order_insert_img_sure(){
	if($("#order_insert_img_form").form('validate')){
		$("#order_insert_img_id").val($("#order_show_img").data("id"));
		$("#order_insert_img_form").submit();
	}else{
		$("#order_insert_img_file").filebox('validate');
	}
}

//删除样品图片
function order_delete_img(){
	if($(".order_img_ul .showActive").length == 0)
		return;
	var url = $(".order_img_ul .showActive").data("imgurl");
	var id = $("#order_show_img").data("id");
	if($(".order_img_text_totleNum").text() == 0){
		return;
	}
	if(url != undefined && id != undefined){
		$.customAjax({
			url: ctx + "/order/DeleteCommodityImg",
			data:{
				orderId:id,
				commodityImgUrl:url
			},
			success:function(data){
				if(data.success){
					layer.msg(data.detailInfo, {time: 1000});
					var length = $(".order_img_ul li").length;
					if(length == 1){
						$(".order_img_text_currentNum").text("0");
						$(".order_img_text_totleNum").text("0");
						var html = '<li class="showActive" data-imgurl="none"><img src="' + ctx + '/resource/userimg/commodity/none.png"/></li>'
						$(".order_img_ul").html(html);
					}else{
						$(".order_img_text_currentNum").text("1");
						$(".order_img_text_totleNum").text(length - 1);
						$(".order_img_ul .showActive").remove();
						$(".order_img_ul li:first").addClass("showActive");
					}
					$("#order_dg").datagrid('reload');
				}else{
					$.messager.alert('警告',data.detailInfo);
				}
			}
		});
	}
}


$(function(){
	
	$("#order_content").css('height',$(".order").height() - $(".order .query_div").height() - 24 + "px");
	
	/**
	 * 初始化查询模块
	 */
	function autoCreateSearchModule(){
		var lis = new Array();
		lis.push({"lableName":"客户名称","type":"textbox","id":null,"name":"customName"});
		lis.push({"lableName":"产品名称","type":"textbox","id":null,"name":"commodityName"});
		lis.push({"lableName":"备注信息","type":"textbox","id":null,"name":"remark"});
		lis.push({"lableName":"交易状态","type":"combobox","id":null,"name":"isTradeSuccess","role":"panelHeight:'auto',editable:false,panelMaxHeight:160,valueField: 'value',textField: 'text',value:'-1',data: [{value:'-1',text:'全部'},{value:'0',text:'未交易完成'},{value:'1',text:'交易成功'}]"});
		lis.push({"lableName":"删除标志","type":"combobox","id":null,"name":"deleted","role":"panelHeight:'auto',editable:false,panelMaxHeight:160,valueField: 'value',textField: 'text',value:'-1',data: [{value:'-1',text:'全部'},{value:'0',text:'未删除'},{value:'1',text:'已删除'}]"});		
		lis.push({"lableName":"创建时间","type":"rangeTime","id":"createTime","name":"createTime","role":"editable: false"});
		lis.push({"lableName":"交易时间","type":"rangeTime","id":"tradeTime","name":"tradeTime","role":"editable: false"});
		var btns = new Array();
		btns.push({"id":"clear_btn","clazz":"easyui-linkbutton","options":"","icon":"circle-o","name":"清空"});
		btns.push({"id":"search_btn","clazz":"easyui-linkbutton","options":"","icon":"search","name":"查询"});
		createSearchModule(lis,btns,"order");
		$(".order .easyui-textbox").textbox();
		$(".order .easyui-combobox").combobox();
		$(".order .easyui-datetimebox").datetimebox();
		$(".order .easyui-linkbutton").linkbutton();
	}
	
	autoCreateSearchModule();

	/**
	 * 查询
	 */
	$(".order .query_div").on("click","#order_search_btn",function(){
		var $form = $(this).parents(".query_div").find("form");
		var formJSON = $form.serializeJson();
		if(formJSON.createTimestartTime && formJSON.createTimeendTime){
			var starttime = new Date(formJSON.createTimestartTime);
			starttime.setHours(0);
			starttime.setMinutes(0);
			starttime.setSeconds(0);
			formJSON.createTimestartTime = toDateTimeStr(starttime);
			var endtime = new Date(formJSON.createTimeendTime);
			endtime.setHours(23);
			endtime.setMinutes(59);
			endtime.setSeconds(59);
			formJSON.createTimeendTime = toDateTimeStr(endtime);
		}
		if(formJSON.tradeTimestartTime && formJSON.tradeTimeendTime){
			var starttime = new Date(formJSON.tradeTimestartTime);
			starttime.setHours(0);
			starttime.setMinutes(0);
			starttime.setSeconds(0);
			formJSON.tradeTimestartTime = toDateTimeStr(starttime);
			var endtime = new Date(formJSON.tradeTimeendTime);
			endtime.setHours(23);
			endtime.setMinutes(59);
			endtime.setSeconds(59);
			formJSON.tradeTimeendTime = toDateTimeStr(endtime);
		}
		
		$("#order_dg").datagrid('reload',formJSON);
	});


	/**
	 * 清空 
	 */
    $(document).on("click","#order_clear_btn",function(){
    	var $form = $(this).parents(".query_div").find("form");
    	$form.form('reset');
    	$("#order_dg").datagrid('reload',{isgys:-1,iscompany:-1});
    });
	
    /**
     * 加载数据表格
     */
	$("#order_dg").datagrid({
		title: '订单列表',
		url: ctx + '/order/getData',
		queryParams: {
			deleted: -1,
			isTradeSuccess: -1
		},
		checkbox:true,
		toolbar: '#order_toolbar',
		fitColumns: false,
		fit: true,
		singleSelect: false,
		pagination: true,
		rownumbers: true,
		pageList: [10,20,30,50,100],
		pageSize: 10,
		autoRowHeight:false,
		height:400,
		columns:[[
			{field:'id',title:'id',hidden:true,width:100},
			{field:'checkbox',title:'',width:100,checkbox:true},
			{field:'customName',title:'客户名称',width:140,sortable:true,
				formatter: function(value,row,index){
					return row.custom.name;
				}
			},
			{field:'commodityName',title:'产品名称',width:180,sortable:true,
				formatter: function(value,row,index){
					return row.commodity.name;
				}
			},
			{field:'univalence',title:'单价',width:120,sortable:true},
			{field:'singleGroupNum',title:'单组数量',width:120,sortable:true},
			{field:'groupNum',title:'组数',width:120,sortable:true},
			{field:'amount',title:'总数量',width:120,sortable:true},
			{field:'totlePrice',title:'总价',width:120,sortable:true},
			{field:'createTime',title:'创建时间',width:180,sortable:true,
				formatter: function(value,row,index){
					if(value != null && value != '' ){
						return toDateTimeStr(value);
					}
				}
			},
			{field:'tradeTime',title:'交易时间',width:180,sortable:true,
				formatter: function(value,row,index){
					if(value != null && value != '' ){
						return toDateTimeStr(value);
					}
				}
			},
			{field:'remark',title:'备注信息',width:300,sortable:true},
			{field:'isTradeSuccess',title:'交易状态',width:80,align:'center',sortable:true,
				formatter: function(value,row,index){
					if (value == 0){
						return "<b class='red'>未交易完成</b>";
					} else {
						return "<b class='green'>交易成功</b>";
					}
				}
			},
			{field:'deleted',title:'删除标志',width:80,align:'center',sortable:true,
				formatter: function(value,row,index){
					if (value == 0){
						return "未删除";
					} else {
						return "<b class='red'>已删除</b>";
					}
				}
			}
		]],
		onLoadSuccess: function(data){
			var html = "";
			var columns = ['customName','commodityName','univalence','singleGroupNum','groupNum','amount','totlePrice','createTime','tradeTime','remark','isTradeSuccess','deleted'];
			var CHcolumns = ['客户名称','产品名称','单价','单组数量','组数','总数量','总价格','创建时间','交易时间','备注信息','交易状态','删除标志'];
			for(var i = 0 ; i < columns.length ; i++){
				html += '<li><label class="hideshowFieldslbl"><a class="l-btn l-btn-small column_btn"><input type="checkbox" value="' + columns[i] + '"><span>' + CHcolumns[i] + '</span></a></label></li>';
			}
			$(".order_all_columns").html(html);
		}
	});
	
	function checkImg(e){
		e.stopPropagation();
	}
	
	$("body").on('click','.change_span',function(){
		var direction = $(this).data('direction');
		var current_li = $(this).siblings('ul').find('.showActive');
		var prev = current_li.prev();
		var next = current_li.next();
		if(prev.length == 0 && next.length == 0)
			return;
		var li = $(this).siblings('ul').find("li");
		current_li.removeClass("showActive");
		if(direction == 'right'){
			if(next.length == 0){
				current_li.siblings(':first').addClass("showActive");
				$(".order_img_text_currentNum").text(current_li.siblings(':first').index() + 1);
			}else{
				next.addClass("showActive");
				$(".order_img_text_currentNum").text(next.index() + 1);
			}
		}else if(direction == 'left'){
			if(prev.length == 0){
				current_li.siblings(':last').addClass("showActive");
				$(".order_img_text_currentNum").text(current_li.siblings(':last').index() + 1);
			}else{
				prev.addClass("showActive");
				$(".order_img_text_currentNum").text(prev.index() + 1);
			}
		}
	});
	
	//新增、修改的弹窗属性设置
	$("#order_window").window({
		modal : true,
		resizable : false,
		width : 700,
		collapsible : false,
		minimizable : false,
		maximizable:false,
		height : 300,
		modal : true,
		cache : false,
		closed:true
	});
	
	//确定修改和确定新增
	$("#order_oper_btn").linkbutton({
		onClick:function(){
			var oper = $(this).data("oper");
			var $form = $("#order_oper_form");
			$form.form('submit',{
				url: ctx + '/order/operData',
				onSubmit: function(param){    
					param.oper = oper;
					param.operId = $("#order_oper_btn").data("id");
					return $form.form('validate');
				},
				success:function(data){
					var data = eval('(' + data + ')');
					if(data.success){
						layer.msg(data.detailInfo, {time: 1000});
						$("#order_window").window('close');
						$("#order_dg").datagrid('reload');
					}else{
						$.messager.alert('提示',data.detailInfo);
					}
				}
			});
		}
	});
	
	//关闭弹窗
	$("#order_close_btn").linkbutton({
		onClick:function(){
			$("#order_window").window('close');
		}
	});

	//toolbar-新增
	$("#order_dg_insert_btn").linkbutton({
		onClick: function(){
			order_oper_bind_select(null,null);
			$("#order_window").window({title : '新增订单'});
			$("#order_oper_btn").data("oper","insert");
			$("#order_oper_form").form("reset");
			$("#order_window").window('open');
		}
	});
	
	//toolbar-修改
	$("#order_dg_update_btn").linkbutton({
		onClick: function(){
			bindSelectedData();
		}
	});
	
	//toolbar-显示样品图片
	$("#order_dg_showImg").linkbutton({
		onClick: function(){
	    	var row = $("#order_dg").datagrid('getSelections');
			if(row != null && row.length > 0){
				$("#order_show_img").data("id",row[0].id);
				var specimenImg = row[0].specimenImg;
				if(specimenImg !=null && specimenImg != ''){
					var arrs = specimenImg.split(';');
					if(arrs.length > 0){
						$(".order_img_text_currentNum").text("1");
						var html = "";
						var imgsize = 0;
						for(var i = 0 ; i < arrs.length ; i++){
							if(arrs[i] == null || arrs[i] == '')
								continue;
							if(i == 0){
								html += '<li class="showActive" data-imgurl="' + arrs[i] + '"><img src="' + ctx + arrs[i] + '"/></li>'
							}else{
								html += '<li data-imgurl="' + arrs[i] + '"><img src="' + ctx + arrs[i] + '"/></li>'
							}
							imgsize ++;
						}
						$(".order_img_text_totleNum").text(imgsize);
						$(".order_img_ul").html(html);
						$("#order_show_img").dialog('open');
						return;
					}
				}
				$(".order_img_text_currentNum").text("0");
				$(".order_img_text_totleNum").text("0");
				var html = '<li class="showActive" data-imgurl="none"><img src="' + ctx + '/resource/userimg/commodity/none.png"/></li>'
				$(".order_img_ul").html(html);
				$("#order_show_img").dialog('open');
			}else{
				$.messager.alert('警告','请先选择要查看的行');
			}
		}  
	});
	
	//toolbar-删除
	$("#order_dg_delete_btn").linkbutton({
		onClick: function(){
	    	var row = $("#order_dg").datagrid('getSelections');
			if(row != null && row.length > 0){
				$.messager.confirm('确认','您确认要删除所选记录吗？',function(r){
					if (r){    
						var ids = new Array(row.length); 
						for(var i = 0 ; i < row.length ; i++){
							ids[i] = row[i].id
						}
						$.customAjax({
							url: ctx + "/order/operData",
							data: {
								'oper': 'delete',
								'deleteIds': ids.toString()
							},
							success: function(data){
								$.messager.alert('提示',data.detailInfo);
								if(data.success){
									$("#order_dg").datagrid('reload');
								}
							}
						});
					}
				}); 
			}else{
				$.messager.alert('警告','请先选择要删除的行');
			}
		}  
	});
	//toolbar-导入
	$("#order_dg_import_btn").click(function(){
		$("#order_import_div").window({
			modal : true,
			resizable : false,
			width : 350,
			collapsible : false,
			minimizable : false,
			maximizable:false,
			height : 180,
			modal : true,
			cache : false,
			title : '导入',
			closed: false
		});
		$("#order_import_div").show();
		$("#order_importFile").filebox('clear');
		$("#order_import_result_tips").text('');
	});
	
	$("#order_import_num_close_btn").click(function(){
		$("#order_import_div").window('close');
	});
	
	//toolbar-导出
	$("#order_dg_export_btn").click(function(){
		$("#order_choose_num").window({
			modal : true,
			resizable : false,
			width : 260,
			collapsible : false,
			minimizable : false,
			maximizable:false,
			height : 145,
			modal : true,
			cache : false,
			title : '导出',
			closed: false
		});
		$("#order_choose_num").show();
		$("#order_exportStartNum").textbox('setValue','1');
		$("#order_exportEndNum").textbox('setValue','');
	});
	
	//确定导出
	$("#order_choose_num_oper_btn").click(function(){
		if($("#order_exportStartNum").textbox('isValid') && $("#order_exportEndNum").textbox('isValid')){
			var $form = $(".order .query_div").find("form");
			var formJSON = $form.serializeJson();
			if(formJSON.createTimestartTime && formJSON.createTimeendTime){
				var starttime = new Date(formJSON.createTimestartTime);
				starttime.setHours(0);
				starttime.setMinutes(0);
				starttime.setSeconds(0);
				formJSON.createTimestartTime = toDateTimeStr(starttime);
				var endtime = new Date(formJSON.createTimeendTime);
				endtime.setHours(23);
				endtime.setMinutes(59);
				endtime.setSeconds(59);
				formJSON.createTimeendTime = toDateTimeStr(endtime);
			}
			
			if(formJSON.tradeTimestartTime && formJSON.tradeTimeendTime){
				var starttime = new Date(formJSON.tradeTimestartTime);
				starttime.setHours(0);
				starttime.setMinutes(0);
				starttime.setSeconds(0);
				formJSON.tradeTimestartTime = toDateTimeStr(starttime);
				var endtime = new Date(formJSON.tradeTimeendTime);
				endtime.setHours(23);
				endtime.setMinutes(59);
				endtime.setSeconds(59);
				formJSON.tradeTimeendTime = toDateTimeStr(endtime);
			}
			
			var startNum = $("#order_exportStartNum").val();
			var endNum = $("#order_exportEndNum").val();
			startNum = Number.parseInt(startNum);
			if(endNum != null && endNum != ''){
				endNum = Number.parseInt(endNum);
			}
			if(startNum > 0){
				startNum --;
			}
			formJSON.startNum = startNum;
			formJSON.endNum = endNum;
			var t = createURL(ctx + '/order/exportExcel',formJSON);
			var newwindow = window.open(t);
			newwindow.document.title = "导出报表";
			$("#order_choose_num").window('close');
		}else{
			$("#order_exportStartNum").textbox('validate');
			$("#order_exportEndNum").textbox('validate');
		}
	});
	
	//关闭导出
	$("#order_choose_num_close_btn").click(function(){
		$("#order_choose_num").window('close');
	});
	
	//显示隐藏列
	$("#order_dg_show_hide_btn").click(function(){
		var $tds = $(".order .datagrid-view2 .datagrid-header .datagrid-header-row").find("td[field!='checkbox']:visible");
		var $checkboxs = $(".order_all_columns").find("input[type='checkbox']");
		$checkboxs.removeAttr("checked");
		var showColumn;
		$tds.each(function(){
			showCoumn = $(this).attr("field");
			$checkboxs.each(function(){
				if(showCoumn == $(this).attr("value")){
					$(this).prop("checked", true)
					return;
				}
			});
		});
		
		$("#order_show_hide_column").dialog('open');
	});
	
	/**
	 * 新增样品图片form表单提交
	 */
	$("#order_insert_img_form").submit(function(){
		$("#order_insert_img_form").ajaxSubmit({
			type: "post",
            url: ctx + "/order/InsertCommodityImg",
            success: function (data) {
            	if(data.success){
            		layer.msg(data.detailInfo, {time: 1000});
            		if($(".order_img_ul .showActive").data("imgurl") == 'none'){
            			$(".order_img_ul .showActive").remove();
            		}
            		$(".order_img_ul .showActive").removeClass("showActive");
            		var html = '<li class="showActive" data-imgurl="' + data.object + '"><img src="' + ctx + data.object + '"/></li>'
            		$(".order_img_ul").append(html);
					$(".order_img_text_currentNum").text($(".order_img_ul .showActive").index() + 1);
					$(".order_img_text_totleNum").text($(".order_img_ul li").length);
					$("#order_dg").datagrid('reload');
            		$("#order_insert_img_dialog").dialog('close');
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
	
	
	function order_oper_bind_select(custom_oper,commodity_oper){
		$("#order_customName").combobox({
			valueField: 'value',
			textField: 'text',
			url:ctx + '/infoManage/getkhSelectList',
			onLoadSuccess:custom_oper,
			onHidePanel:function(){
				if(!validCustomSelect("#order_customName")){
					$("#order_customName").combobox('clear');
				}
			}
		});
		
		$("#order_commodityName").combobox({
			valueField: 'value',
			textField: 'text',
			url:ctx + '/commodity/getCommoditySelectList',
			onLoadSuccess:commodity_oper,
			onHidePanel:function(){
				if(!validCustomSelect("#order_commodityName")){
					$("#order_commodityName").combobox('clear');
				}
			}
		});
	}
	
	
	//修改——给控件绑定对应的值
	function bindSelectedData(){
		$("#order_window").window({title : '修改信息'});
		var rows = $("#order_dg").datagrid('getSelections');
		if(rows != null && rows.length > 0){
			$("#order_oper_form").form("reset");
			var row = rows[0];
			$("#order_oper_btn").data("oper","update");
			$("#order_oper_btn").data("id",row.id);
			$("#order_univalence").textbox('setValue',row.univalence);
			$("#order_singleGroupNum").textbox('setValue',row.singleGroupNum);
			$("#order_groupNum").textbox('setValue',row.groupNum);
			$("#order_amount").textbox('setValue',row.amount);
			$("#order_totlePrice").textbox('setValue',row.totlePrice);
			$("#order_remark").textbox('setValue',row.remark);
			$("#order_isTradeSuccess").combobox('setValue',row.isTradeSuccess);
			$("#order_deleted").combobox('setValue',row.deleted);
			if(row.createTime != null && row.createTime != '' ){
				$("#order_createTime").datetimebox('setValue',toDateTimeStr(row.createTime));
			}
			if(row.tradeTime != null && row.tradeTime != '' ){
				$("#order_tradeTime").datetimebox('setValue',toDateTimeStr(row.tradeTime));
			}
			
			//单位select回调函数
			var custom_oper = function(data){
				var flag = false;
				var u_id = row.custom.id;
				for(var i = 0 ; i < data.length ; i++){
					if(data[i].value == u_id){
						flag = true;
						break;
					}
				}
				if(flag){
					$("#order_customName").combobox('setValue',u_id);
				}else{
					data.push({'value':row.custom.id,'text':row.custom.name,'selected':true});
					$("#order_customName").combobox('loadData',data);
				}
			}
			
			//供应商select回调函数
			var commodity_oper = function(data){
				var flag = false;
				var c_id = row.commodity.id;
				for(var i = 0 ; i < data.length ; i++){
					if(data[i].value == c_id){
						flag = true;
						break;
					}
				}
				if(flag){
					$("#order_commodityName").combobox('setValue',c_id);
				}else{
					data.push({'value':row.commodity.id,'text':row.commodity.name,'selected':true});
					$("#order_commodityName").combobox('loadData',data);
				}
			}
			order_oper_bind_select(custom_oper,commodity_oper);
			
			
			$("#order_window").window('open');
		}else{
			$.messager.alert('警告','请先选择要修改的行');
		}
	}

	
	/**
	 * 导入
	 */
	var getprocessField = null;
	$("#order_importForm").submit(function(){
		$("#order_import_num_oper_btn").linkbutton('disable');
		$("#order_import_num_close_btn").linkbutton('disable');
		
		$(".show_process_div").show();
		$("#order_import_result_tips").text('');
		$("#order_import_div").window({height:230});
		getprocessField = setInterval(function(){
			$.customAjax({
				url: ctx + "/common/getProgress",
				dataType: 'text',
				async:false,
				success:function(data){
					var val = $('#order_upload_process').progressbar('getValue');
					if(data && val < 100){
						$("#order_upload_process").progressbar('setValue',data);
					}
					if(val == 100){
						$("#order_import_result_tips").text('上传成功，正在解析文件...');
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
		    		clearInterval(getprocessField);
					$.messager.alert('提示','获取进度条发生错误');
				}
			});
		},1000);   //每一秒钟获取一次进度条
		$("#order_importForm").ajaxSubmit({
			type: "post",
            url: ctx + "/order/uploadOrder",
            success: function (data) {
            	clearInterval(getprocessField);
            	$("#order_upload_process").progressbar('setValue',100);
        		$(".order_show_process_div").delay(1000).hide(1200,function(){
        			$("#order_import_div").window({height:180});
        		});
        		$("#order_import_result_tips").text('解析完毕，上传成功!');
        		$("#order_import_num_oper_btn").linkbutton('enable');
        		$("#order_import_num_close_btn").linkbutton('enable');
            },
            error: function (msg) {
        		clearInterval(getprocessField);
        		$("#order_import_result_tips").text('解析完毕，上传失败!');
                $.messager.alert("提示","文件上传失败");
                $("#order_import_num_oper_btn").linkbutton('enable');
        		$("#order_import_num_close_btn").linkbutton('enable');
            }
		});
		return false;
	});
	
	$("#order_import_num_oper_btn").click(function(){
		$("#order_import_result_tips").text('');
		if($("#order_importFile").filebox('isValid')){
			$("#order_importForm").submit();
		}else{
			$("#order_importFile").filebox('validate');
		}
	});
	
	$("#order_download_template").click(function(){
		var newwindow = window.open(ctx + '/common/downLoadTemplate');
		newwindow.document.title = "导出上传模板";
	});
	
	$("#order_content").css('height',$(".order").height() - $(".order .query_div").height() - 24 + "px");
	$("#order_dg").datagrid('resize');

});
