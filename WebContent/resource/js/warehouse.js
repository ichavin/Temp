$(function(){
	
	/**
	 * 初始化查询模块
	 */
	function autoCreateSearchModule(){
		var lis = new Array();
		lis.push({"lableName":"名称","type":"textbox","id":null,"name":"name"});
		lis.push({"lableName":"类型","type":"combobox","id":null,"name":"type","role":"editable: false,panelHeight: 'auto',panelMaxHeight:160,valueField: 'value',textField: 'text',value:'-1',data: [{value:'-1',text:'全部'},{value:'0',text:'产品'},{value:'1',text:'材料'},{value:'2',text:'其它'}]"});
		lis.push({"lableName":"入仓时间","type":"rangeTime","id":"produceTime","name":"time","role":"editable: false"});
		var btns = new Array();
		btns.push({"id":"clear_btn","clazz":"easyui-linkbutton","options":"","icon":"circle-o","name":"清空"});
		btns.push({"id":"search_btn","clazz":"easyui-linkbutton","options":"","icon":"search","name":"查询"});
		createSearchModule(lis,btns,"warehouse");
		$(".warehouse .easyui-textbox").textbox();
		$(".warehouse .easyui-combobox").combobox();
		$(".warehouse .easyui-datetimebox").datetimebox();
		$(".warehouse .easyui-linkbutton").linkbutton();
	}
	
	autoCreateSearchModule();

	/**
	 * 查询
	 */
	$(".warehouse .query_div").on("click","#warehouse_search_btn",function(){
		var $form = $(this).parents(".query_div").find("form");
		var formJSON = $form.serializeJson();
		
		if(formJSON.produceTimestartTime && formJSON.produceTimeendTime){
			var starttime = new Date(formJSON.produceTimestartTime);
			starttime.setHours(0);
			starttime.setMinutes(0);
			starttime.setSeconds(0);
			formJSON.createTimestartTime = toDateTimeStr(starttime);
			var endtime = new Date(formJSON.produceTimeendTime);
			endtime.setHours(23);
			endtime.setMinutes(59);
			endtime.setSeconds(59);
			formJSON.createTimeendTime = toDateTimeStr(endtime);
		}
		delete formJSON.produceTimestartTime;
		delete formJSON.produceTimeendTime;
		
		
		$("#warehouse_dg").datagrid('reload',formJSON);
	});


	/**
	 * 清空 
	 */
    $(document).on("click","#warehouse_clear_btn",function(){
    	var $form = $(this).parents(".query_div").find("form");
    	$form.form('reset');
    	$("#warehouse_dg").datagrid('reload',{isgys:-1,iscompany:-1});
    });
	
    /**
     * 加载数据表格
     */
	$("#warehouse_dg").datagrid({
		title: '用户列表',
		url: ctx + '/warehouse/getData',
		queryParams: {
			deleted: -1
		},
		checkbox:true,
		toolbar: '#warehouse_toolbar',
		fitColumns: false,
		fit: true,
		singleSelect: false,
		pagination: true,
		rownumbers: true,
		pageList: [10,20,30,50,100],
		pageSize: 10,
		autoRowHeight:false,
		height:446,
		columns:[[
			{field:'id',title:'id',hidden:true,width:100},
			{field:'checkbox',title:'',width:100,checkbox:true},
			{field:'name',title:'名称',sortable:true,width:120},
			{field:'uId',title:'uid',hidden:true,width:120,
				formatter: function(value,row,index){
					if(row.unit != null){
						return row.unit.id;
					}
				}
			},
			{field:'unitName',title:'单位',sortable:true,width:120,
				formatter: function(value,row,index){
					if(row.unit != null){
						return row.unit.name;
					}
				}
			},
			{field:'type',title:'类型',sortable:true,width:100,
				formatter: function(value,row,index){
					if (value == 0){
						return "产品";
					}else if(value == 1){
						return "材料";
					}else{
						return "其他";
					}
				}
			},
			{field:'defectiveAmount',title:'次品数量',width:100,sortable:true},    
			{field:'qualityAmount',title:'正品数量',width:150,sortable:true},
			{field:'totleAmount',title:'总数量',width:120,sortable:true},
			{field:'produceTime',title:'入仓时间',width:150,align:'center',sortable:true,
				formatter: function(value,row,index){
					if(value != null && value != '' ){
						return toDateTimeStr(value);
					}
				}
			}
		]],
		onLoadSuccess: function(data){
			var html = "";
			var columns = ['name','type','unitName','defectiveAmount','qualityAmount','totleAmount','produceTime'];
			var CHcolumns = ['名称','类型','单位','次品数量','正品数量','总数量','入仓时间'];
			for(var i = 0 ; i < columns.length ; i++){
				html += '<li><label class="hideshowFieldslbl"><a class="l-btn l-btn-small column_btn"><input type="checkbox" value="' + columns[i] + '"><span>' + CHcolumns[i] + '</span></a></label></li>';
			}
			$(".warehouse_all_columns").empty().html(html);
		}
		
	});
	
	//新增、修改的弹窗属性设置
	$("#warehouse_window").window({
		modal : true,
		resizable : false,
		width : 700,
		collapsible : false,
		minimizable : false,
		maximizable:false,
		height : 230,
		modal : true,
		cache : false,
		closed:true
	});
	
	//确定修改和确定新增
	$("#warehouse_oper_btn").linkbutton({
		onClick:function(){
			var oper = $(this).data("oper");
			var $form = $("#warehouse_oper_form");
			$form.form('submit',{
				url: ctx + '/warehouse/operData',
				onSubmit: function(param){    
					param.oper = oper;
					param.operId = $("#warehouse_oper_btn").data("id");
					return $form.form('validate');
				},
				success:function(data){
					var data = eval('(' + data + ')');
					if(data.success){
						layer.msg(data.detailInfo, {time: 1000});
						$("#warehouse_window").window('close');
						$("#warehouse_dg").datagrid('reload');
					}else{
						$.messager.alert('提示',data.detailInfo);
					}
				}
			});
		}
	});
	
	//关闭弹窗
	$("#warehouse_close_btn").linkbutton({
		onClick:function(){
			$("#warehouse_window").window('close');
		}
	});

	//toolbar-新增
	$("#warehouse_dg_insert_btn").linkbutton({
		onClick: function(){
			$("#warehouse_window").window({title : '新增库存'});
			warehouse_oper_bind_select(null);
			$("#warehouse_oper_btn").data("oper","insert");
			$("#warehouse_oper_form").form("reset");
			$("#warehouse_defectiveAmount").textbox('setValue',0);
			$("#warehouse_qualityAmount").textbox('setValue',0);
			$("#warehouse_totleAmount").textbox('setValue',0);
			$("#warehouse_window").window('open');
		}
	});
	
	
	//toolbar-修改
	$("#warehouse_dg_update_btn").linkbutton({
		onClick: function(){
			bindSelectedData();
		}
	});
	
	//toolbar-删除
	$("#warehouse_dg_delete_btn").linkbutton({
		onClick: function(){
	    	var row = $("#warehouse_dg").datagrid('getSelections');
			if(row != null && row.length > 0){
				$.messager.confirm('确认','您确认要删除所选记录吗？',function(r){    
					if (r){    
						var ids = new Array(row.length); 
						for(var i = 0 ; i < row.length ; i++){
							ids[i] = row[i].id
						}
						$.customAjax({
							url: ctx + "/warehouse/operData",
							data: {
								'oper': 'delete',
								'deleteIds': ids.toString()
							},
							success: function(data){
								layer.msg(data.detailInfo, {time: 1000});
								if(data.success){
									$("#warehouse_dg").datagrid('reload');
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
	$("#warehouse_dg_import_btn").click(function(){
		$("#warehouse_import_div").window({
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
		$("#warehouse_import_div").show();
		$("#warehouse_warehouse_importFile").filebox('clear');
		$("#warehouse_import_result_tips").text('');
	});
	
	$("#warehouse_import_num_close_btn").click(function(){
		$("#warehouse_import_div").window('close');
	});
	
	//toolbar-导出
	$("#warehouse_dg_export_btn").click(function(){
		$("#warehouse_choose_num").window({
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
		$("#warehouse_choose_num").show();
		$("#warehouse_exportStartNum").textbox('setValue','1');
		$("#warehouse_exportEndNum").textbox('setValue','');
	});
	
	//确定导出
	$("#warehouse_choose_num_oper_btn").click(function(){
		if($("#warehouse_exportStartNum").textbox('isValid') && $("#warehouse_exportEndNum").textbox('isValid')){
			var $form = $(".warehouse .query_div").find("form");
			var formJSON = $form.serializeJson();
			
			if(formJSON.produceTimestartTime && formJSON.produceTimeendTime){
				var starttime = new Date(formJSON.produceTimestartTime);
				starttime.setHours(0);
				starttime.setMinutes(0);
				starttime.setSeconds(0);
				formJSON.createTimestartTime = toDateTimeStr(starttime);
				var endtime = new Date(formJSON.produceTimeendTime);
				endtime.setHours(23);
				endtime.setMinutes(59);
				endtime.setSeconds(59);
				formJSON.createTimeendTime = toDateTimeStr(endtime);
			}
			delete formJSON.produceTimestartTime;
			delete formJSON.produceTimeendTime;
			
			var startNum = $("#warehouse_exportStartNum").val();
			var endNum = $("#warehouse_exportEndNum").val();
			startNum = Number.parseInt(startNum);
			if(endNum != null && endNum != ''){
				endNum = Number.parseInt(endNum);
			}
			if(startNum > 0){
				startNum --;
			}
			formJSON.startNum = startNum;
			formJSON.endNum = endNum;
			var t = createURL(ctx + '/warehouse/exportExcel',formJSON);
			var newwindow = window.open(t);
			newwindow.document.title = "导出报表";
			$("#warehouse_choose_num").window('close');
		}else{
			$("#warehouse_exportStartNum").textbox('validate');
			$("#warehouse_exportEndNum").textbox('validate');
		}
	});
	
	//关闭导出
	$("#warehouse_choose_num_close_btn").click(function(){
		$("#warehouse_choose_num").window('close');
	});
	
	//显示隐藏列
	$("#warehouse_dg_show_hide_btn").click(function(){
		var $tds = $(".warehouse .datagrid-view2 .datagrid-header .datagrid-header-row").find("td[field!='checkbox']:visible");
		var $checkboxs = $(".warehouse_all_columns").find("input[type='checkbox']");
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
		$("#warehouse_show_hide_column").dialog('open');
	});
	
	
	function warehouse_oper_bind_select(unit_update_function){
		$("#warehouse_unit").combobox({
			width:173,
			panelWidth:170,
			editable:true,
			panelHeight:'auto',
			panelMaxHeight:160,
			url:ctx + '/unit/getUnitSelectList',
			onLoadSuccess:unit_update_function,
			onHidePanel:function(){
				if(!validCustomSelect("#warehouse_unit")){
					$("#warehouse_unit").combobox('clear');
				}
			}
		});
	}
	
	//修改——给控件绑定对应的值
	function bindSelectedData(){
		$("#warehouse_window").window({title : '修改用户信息'});
		var rows = $("#warehouse_dg").datagrid('getSelections');
		if(rows != null && rows.length > 0){
			var row = rows[0];
			$("#warehouse_oper_btn").data("oper","update");
			$("#warehouse_oper_btn").data("id",row.id);
			$("#warehouse_name").textbox('setValue',row.name);
			$("#warehouse_type").combobox('setValue',row.type);
			$("#warehouse_defectiveAmount").textbox('setValue',row.defectiveAmount);
			$("#warehouse_qualityAmount").textbox('setValue',row.qualityAmount);
			$("#warehouse_totleAmount").textbox('setValue',row.totleAmount);
			if(row.produceTime != null && row.produceTime != '' ){
				row.produceTime = toDateTimeStr(row.produceTime);
			}
			$("#warehouse_produceTime").datetimebox('setValue',row.produceTime);
			
			//单位select回调函数
			var unit_update = function(data){
				var flag = false;
				var u_id = row.unit.id;
				for(var i = 0 ; i < data.length ; i++){
					if(data[i].value == u_id){
						flag = true;
						break;
					}
				}
				if(flag){
					$("#warehouse_unit").combobox('setValue',u_id);
				}else{
					data.push({'value':row.unit.id,'text':row.unit.name,'selected':true});
					$("#warehouse_unit").combobox('loadData',data);
				}
			}
			warehouse_oper_bind_select(unit_update);
			
			$("#warehouse_window").window('open');
		}else{
			$.messager.alert('警告','请先选择要修改的行');
		}
	}

	$("#warehouse_dg").datagrid('resize');
	
	
	/**
	 * 导入
	 */
	var getprocessField = null;
	$("#warehouse_importForm").submit(function(){
		$("#warehouse_import_num_oper_btn").linkbutton('disable');
		$("#warehouse_import_num_close_btn").linkbutton('disable');
		
		$(".show_process_div").show();
		$("#warehouse_import_result_tips").text('');
		$("#warehouse_import_div").window({height:230});
		getprocessField = setInterval(function(){
			$.customAjax({
				url: ctx + "/common/getProgress",
				dataType: 'text',
				async:false,
				success:function(data){
					var val = $('#warehouse_upload_process').progressbar('getValue');
					if(data && val < 100){
						$("#warehouse_upload_process").progressbar('setValue',data);
					}
					if(val == 100){
						$("#warehouse_import_result_tips").text('上传成功，正在解析文件...');
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
		    		clearInterval(getprocessField);
					$.messager.alert('提示','获取进度条发生错误');
				}
			});
		},1000);   //每一秒钟获取一次进度条
		$("#warehouse_importForm").ajaxSubmit({
			type: "post",
            url: ctx + "/infoManage/uploadCustom",
            success: function (data) {
            	clearInterval(getprocessField);
            	$("#warehouse_upload_process").progressbar('setValue',100);
        		$(".warehouse_show_process_div").delay(1000).hide(1200,function(){
        			$("#warehouse_import_div").window({height:180});
        		});
        		$("#warehouse_import_result_tips").text('解析完毕，上传成功!');
        		$("#warehouse_import_num_oper_btn").linkbutton('enable');
        		$("#warehouse_import_num_close_btn").linkbutton('enable');
            },
            error: function (msg) {
        		clearInterval(getprocessField);
        		$("#warehouse_import_result_tips").text('解析完毕，上传失败!');
                $.messager.alert("提示","文件上传失败");
                $("#warehouse_import_num_oper_btn").linkbutton('enable');
        		$("#warehouse_import_num_close_btn").linkbutton('enable');
            }
		});
		return false;
	});
	
	$("#warehouse_import_num_oper_btn").click(function(){
		$("#warehouse_import_result_tips").text('');
		if($("#warehouse_importFile").filebox('isValid')){
			$("#warehouse_importForm").submit();
		}else{
			$("#warehouse_importFile").filebox('validate');
		}
	});
	
	$("#warehouse_download_template").click(function(){
		var newwindow = window.open(ctx + '/common/downLoadTemplate');
		newwindow.document.title = "导出上传模板";
	});
	
});


