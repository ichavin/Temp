$(function(){
	
	/**
	 * 初始化查询模块
	 */
	function autoCreateSearchModule(){
		var lis = new Array();
		lis.push({"lableName":"名称","type":"textbox","id":null,"name":"name"});
		lis.push({"lableName":"供货商名称","type":"textbox","id":null,"name":"custom_name"});
		lis.push({"lableName":"删除标志","type":"combobox","id":null,"name":"deleted","role":"editable: false,panelHeight: 'auto',panelMaxHeight:160,valueField: 'value',textField: 'text',value:'-1',data: [{value:'-1',text:'全部'},{value:'0',text:'未删除'},{value:'1',text:'已删除'}]"});
		var btns = new Array();
		btns.push({"id":"clear_btn","clazz":"easyui-linkbutton","options":"","icon":"circle-o","name":"清空"});
		btns.push({"id":"search_btn","clazz":"easyui-linkbutton","options":"","icon":"search","name":"查询"});
		createSearchModule(lis,btns,"meterialManage");
		$(".meterialManage .easyui-validatebox").validatebox();
		$(".meterialManage .easyui-textbox").textbox();
		$(".meterialManage .easyui-combobox").combobox();
		$(".meterialManage .easyui-linkbutton").linkbutton();
	}
	
	autoCreateSearchModule();

	/**
	 * 查询
	 */
	$(".meterialManage .query_div").on("click","#meterialManage_search_btn",function(){
		var $form = $(this).parents(".query_div").find("form");
		var formJSON = $form.serializeJson();
		$("#meterialManage_dg").datagrid('reload',formJSON);
	});


	/**
	 * 清空 
	 */
    $(document).on("click","#meterialManage_clear_btn",function(){
    	var $form = $(this).parents(".query_div").find("form");
    	$form.form('reset');
    	$("#meterialManage_dg").datagrid('reload',{isgys:-1,iscompany:-1});
    });
	
    /**
     * 加载数据表格
     */
	$("#meterialManage_dg").datagrid({
		title: '材料列表',
		url: ctx + '/meterialManage/getData',
		queryParams: {
			deleted: -1
		},
		checkbox:true,
		toolbar: '#meterialManage_toolbar',
		fitColumns: false,
		fit: true,
		singleSelect: false,
		pagination: true,
		rownumbers: true,
		pageList: [10,20,30,50,100],
		pageSize: 10,
		autoRowHeight:false,
		columns:[[
			{field:'id',title:'id',hidden:true,width:100},
			{field:'u.id',title:'u.id',hidden:true,width:100,
				formatter: function(value,row,index){
					return row.unit.id;
				}
			},
			{field:'c.id',title:'c.id',hidden:true,width:100,
				formatter: function(value,row,index){
					return row.custom.id;
				}	
			},
			{field:'checkbox',title:'',width:100,checkbox:true},
			{field:'name',title:'材料名称',width:100,sortable:true},    
			{field:'u.name',title:'单位',width:120,sortable:true,
				formatter: function(value,row,index){
					return row.unit.name;
				}
			},
			{field:'c.name',title:'供货商',width:120,sortable:true,
				formatter: function(value,row,index){
					return row.custom.name;
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
			var columns = ['name','u.name','c.name','deleted'];
			var CHcolumns = ['材料名称','单位','供货商','删除标志'];
			for(var i = 0 ; i < columns.length ; i++){
				html += '<li><label class="hideshowFieldslbl"><a class="l-btn l-btn-small column_btn"><input type="checkbox" value="' + columns[i] + '"><span>' + CHcolumns[i] + '</span></a></label></li>';
			}
			$(".meterialManage_all_columns").empty().html(html);
		}
		
	});
	
	//新增、修改的弹窗属性设置
	$("#meterialManage_window").window({
		modal : true,
		resizable : false,
		width : 700,
		collapsible : false,
		minimizable : false,
		maximizable:false,
		height : 170,
		modal : true,
		cache : false,
		closed:true
	});
	
	//确定修改和确定新增
	$("#meterialManage_oper_btn").linkbutton({
		onClick:function(){
			var oper = $(this).data("oper");
			var $form = $("#meterialManage_oper_form");
			$form.form('submit',{
				url: ctx + '/meterialManage/operData',
				onSubmit: function(param){    
					param.oper = oper;
					param.operId = $("#meterialManage_oper_btn").data("id");
					return $form.form('validate');
				},
				success:function(data){
					var data = eval('(' + data + ')');
					if(data.success){
						layer.msg(data.detailInfo, {time: 1000});
						$("#meterialManage_window").window('close');
						$("#meterialManage_dg").datagrid('reload');
					}else{
						$.messager.alert('提示',data.detailInfo);
					}
				}
			});
		}
	});
	
	//关闭弹窗
	$("#meterialManage_close_btn").linkbutton({
		onClick:function(){
			$("#meterialManage_window").window('close');
		}
	});

	//toolbar-新增
	$("#meterialManage_dg_insert_btn").linkbutton({
		onClick: function(){
			$("#meterialManage_window").window({title : '新增材料'});
			oper_bind_select(null,null);
			$("#meterialManage_oper_btn").data("oper","insert");
			$("#meterialManage_oper_form").form("reset");
			$("#meterialManage_window").window('open');
		}
	});
	
	
	//toolbar-修改
	$("#meterialManage_dg_update_btn").linkbutton({
		onClick: function(){
			bindSelectedData();
		}
	});
	
	//toolbar-删除
	$("#meterialManage_dg_delete_btn").linkbutton({
		onClick: function(){
	    	var row = $("#meterialManage_dg").datagrid('getSelections');
			if(row != null && row.length > 0){
				$.messager.confirm('确认','您确认要删除所选记录吗？',function(r){    
					if (r){    
						var ids = new Array(row.length); 
						for(var i = 0 ; i < row.length ; i++){
							ids[i] = row[i].id
						}
						$.customAjax({
							url: ctx + "/meterialManage/operData",
							data: {
								'oper': 'delete',
								'deleteIds': ids.toString()
							},
							success: function(data){
								$.messager.alert('提示',data.detailInfo);
								if(data.success){
									$("#meterialManage_dg").datagrid('reload');
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
	$("#meterialManage_dg_import_btn").click(function(){
		$("#meterialManage_import_div").window({
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
		$("#meterialManage_import_div").show();
		$("#meterialManage_meterialManage_importFile").filebox('clear');
		$("#meterialManage_import_result_tips").text('');
	});
	
	$("#meterialManage_import_num_close_btn").click(function(){
		$("#meterialManage_import_div").window('close');
	});
	
	//toolbar-导出
	$("#meterialManage_dg_export_btn").click(function(){
		$("#meterialManage_choose_num").window({
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
		$("#meterialManage_choose_num").show();
		$("#meterialManage_exportStartNum").textbox('setValue','1');
		$("#meterialManage_exportEndNum").textbox('setValue','');
	});
	
	//确定导出
	$("#meterialManage_choose_num_oper_btn").click(function(){
		if($("#meterialManage_exportStartNum").textbox('isValid') && $("#meterialManage_exportEndNum").textbox('isValid')){
			var $form = $(".meterialManage .query_div").find("form");
			var formJSON = $form.serializeJson();
			var startNum = $("#meterialManage_exportStartNum").val();
			var endNum = $("#meterialManage_exportEndNum").val();
			startNum = Number.parseInt(startNum);
			if(endNum != null && endNum != ''){
				endNum = Number.parseInt(endNum);
			}
			if(startNum > 0){
				startNum --;
			}
			formJSON.startNum = startNum;
			formJSON.endNum = endNum;
			var t = createURL(ctx + '/meterialManage/exportExcel',formJSON);
			var newwindow = window.open(t);
			newwindow.document.title = "导出报表";
			$("#meterialManage_choose_num").window('close');
		}else{
			$("#meterialManage_exportStartNum").textbox('validate');
			$("#meterialManage_exportEndNum").textbox('validate');
		}
	});
	
	//关闭导出
	$("#meterialManage_choose_num_close_btn").click(function(){
		$("#meterialManage_choose_num").window('close');
	});
	
	//显示隐藏列
	$("#meterialManage_dg_show_hide_btn").click(function(){
		var $tds = $(".meterialManage .datagrid-view2 .datagrid-header .datagrid-header-row").find("td[field!='checkbox']:visible");
		var $checkboxs = $(".meterialManage_all_columns").find("input[type='checkbox']");
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
		$("#meterialManage_show_hide_column").dialog('open');
	});
	
	//修改——给控件绑定对应的值
	function bindSelectedData(){
		$("#meterialManage_window").window({title : '修改信息'});
		var rows = $("#meterialManage_dg").datagrid('getSelections');
		if(rows != null && rows.length > 0){
			var row = rows[0];
			$("#meterialManage_oper_btn").data("oper","update");
			$("#meterialManage_oper_btn").data("id",row.id);
			$("#meterialManage_name").textbox('setValue',row.name);
			
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
					$("#meterialManage_unit").combobox('setValue',u_id);
				}else{
					data.push({'value':row.unit.id,'text':row.unit.name,'selected':true});
					$("#meterialManage_unit").combobox('loadData',data);
				}
			}
			
			//供货商select回调函数
			var gys_update = function(data){
				var flag = false;
				var c_id = row.custom.id;
				for(var i = 0 ; i < data.length ; i++){
					if(data[i].value == c_id){
						flag = true;
						break;
					}
				}
				if(flag){
					$("#meterialManage_gys").combobox('setValue',c_id);
				}else{
					data.push({'value':row.custom.id,'text':row.custom.name,'selected':true});
					$("#meterialManage_gys").combobox('loadData',data);
				}
			}
			oper_bind_select(unit_update,gys_update);
			$("#meterialManage_deleted").combobox('setValue',row.deleted);
			$("#meterialManage_window").window('open');
		}else{
			$.messager.alert('警告','请先选择要修改的行');
		}
	}

	$("#meterialManage_dg").datagrid('resize');
	
	
	function oper_bind_select(unit_update_function,gys_update_function){
		$("#meterialManage_unit").combobox({
			url:ctx + '/unit/getUnitSelectList',
			valueField: 'value',
			textField: 'text',
			onLoadSuccess:unit_update_function,
			onHidePanel:function(){
				if(!validCustomSelect("#meterialManage_unit")){
					$("#meterialManage_unit").combobox('clear');
				}
			}
		});
		
		$("#meterialManage_gys").combobox({
			url:ctx + '/infoManage/getgysSelectList',
			valueField: 'value',
			textField: 'text',
			onLoadSuccess:gys_update_function,
			onHidePanel:function(){
				if(!validCustomSelect("#meterialManage_gys")){
					$("#meterialManage_gys").combobox('clear');
				}
			}
		});
		
		$("#meterialManage_deleted").combobox({
			width:173,
			panelWidth:170,
			panelHeight:'auto',
			editable:true,
			panelMaxHeight:160,
			onHidePanel:function(){
				if(!validCustomSelect("#meterialManage_deleted")){
					$("#meterialManage_deleted").combobox('clear');
				}
			}
		});
	}
	
	
	
	
	/**
	 * 导出
	 */
	var getprocessField = null;
	$("#meterialManage_importForm").submit(function(){
		$("#meterialManage_import_num_oper_btn").linkbutton('disable');
		$("#meterialManage_import_num_close_btn").linkbutton('disable');
		
		$(".show_process_div").show();
		$("#meterialManage_import_result_tips").text('');
		$("#meterialManage_import_div").window({height:230});
		getprocessField = setInterval(function(){
			$.customAjax({
				url: ctx + "/common/getProgress",
				dataType: 'text',
				async:false,
				success:function(data){
					var val = $('#meterialManage_upload_process').progressbar('getValue');
					if(data && val < 100){
						$("#meterialManage_upload_process").progressbar('setValue',data);
					}
					if(val == 100){
						$("#meterialManage_import_result_tips").text('上传成功，正在解析文件...');
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
		    		clearInterval(getprocessField);
					$.messager.alert('提示','获取进度条发生错误');
				}
			});
		},1000);   //每一秒钟获取一次进度条
		$("#meterialManage_importForm").ajaxSubmit({
			type: "post",
            url: ctx + "/infoManage/uploadCustom",
            success: function (data) {
            	clearInterval(getprocessField);
            	$("#meterialManage_upload_process").progressbar('setValue',100);
        		$(".meterialManage_show_process_div").delay(1000).hide(1200,function(){
        			$("#meterialManage_import_div").window({height:180});
        		});
        		$("#meterialManage_import_result_tips").text('解析完毕，上传成功!');
        		$("#meterialManage_import_num_oper_btn").linkbutton('enable');
        		$("#meterialManage_import_num_close_btn").linkbutton('enable');
            },
            error: function (msg) {
        		clearInterval(getprocessField);
        		$("#meterialManage_import_result_tips").text('解析完毕，上传失败!');
                $.messager.alert("提示","文件上传失败");
                $("#meterialManage_import_num_oper_btn").linkbutton('enable');
        		$("#meterialManage_import_num_close_btn").linkbutton('enable');
            }
		});
		return false;
	});
	
	$("#meterialManage_import_num_oper_btn").click(function(){
		$("#meterialManage_import_result_tips").text('');
		if($("#meterialManage_importFile").filebox('isValid')){
			$("#meterialManage_importForm").submit();
		}else{
			$("#meterialManage_importFile").filebox('validate');
		}
	});
	
	$("#meterialManage_download_template").click(function(){
		var newwindow = window.open(ctx + '/common/downLoadTemplate');
		newwindow.document.title = "导出上传模板";
	});
	
});


