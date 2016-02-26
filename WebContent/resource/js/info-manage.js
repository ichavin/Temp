$(function(){
	
	/**
	 * 初始化查询模块
	 */
	function autoCreateSearchModule(){
		var lis = new Array();
		lis.push({"lableName":"姓名","type":"textbox","id":null,"name":"name"});
		lis.push({"lableName":"手机号码","type":"textbox","id":null,"name":"phone"});
		lis.push({"lableName":"电话号码","type":"textbox","id":null,"name":"telNumb"});
		lis.push({"lableName":"公司名称","type":"textbox","id":null,"name":"company"});
		lis.push({"lableName":"公司地址","type":"textbox","id":null,"name":"address"});
		lis.push({"lableName":"备注信息","type":"textbox","id":null,"name":"mark"});
		lis.push({"lableName":"删除标志","type":"combobox","id":null,"name":"deleted","role":"panelHeight:'auto',editable:false,panelMaxHeight:160,valueField: 'value',textField: 'text',value:'-1',data: [{value:'-1',text:'全部'},{value:'0',text:'未删除'},{value:'1',text:'已删除'}]"});
		lis.push({"lableName":"合作角色","type":"combobox","id":null,"name":"isgys","role":"panelHeight:'auto',editable:false,panelMaxHeight:160,valueField: 'value',textField: 'text',value:'-1',data: [{value:'-1',text:'全部'},{value:'0',text:'客户'},{value:'1',text:'供货商'}]"});
		lis.push({"lableName":"性质类型","type":"combobox","id":null,"name":"iscompany","role":"panelHeight:'auto',editable:false,panelMaxHeight:160,valueField: 'value',textField: 'text',value:'-1',data: [{value:'-1',text:'全部'},{value:'0',text:'个人'},{value:'1',text:'单位'}]"});
		lis.push({"lableName":"创建时间","type":"rangeTime","id":"createTime","name":"time","role":"editable: false"});
		var btns = new Array();
		btns.push({"id":"clear_btn","clazz":"easyui-linkbutton","options":"","icon":"circle-o","name":"清空"});
		btns.push({"id":"search_btn","clazz":"easyui-linkbutton","options":"","icon":"search","name":"查询"});
		createSearchModule(lis,btns,"custom");
		$(".custom .easyui-validatebox").validatebox();
		$(".custom .easyui-textbox").textbox();
		$(".custom .easyui-datebox").datebox();
		$(".custom .easyui-combobox").combobox();
		$(".custom .easyui-datetimebox").datetimebox();
		$(".custom .easyui-linkbutton").linkbutton();
	}
	
	autoCreateSearchModule();

	/**
	 * 查询
	 */
	$(".custom .query_div").on("click","#custom_search_btn",function(){
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
		$("#custom_dg").datagrid('reload',formJSON);
	});


	/**
	 * 清空 
	 */
    $(document).on("click","#custom_clear_btn",function(){
    	var $form = $(this).parents(".query_div").find("form");
    	$form.form('reset');
    	$("#custom_dg").datagrid('reload',{isgys:-1,iscompany:-1});
    });
	
    /**
     * 加载数据表格
     */
	$("#custom_dg").datagrid({
		title: '合作商列表',
		url: ctx + '/infoManage/getData',
		queryParams: {
			isgys: -1,
			iscompany: -1,
			deleted: -1
		},
		checkbox:true,
		toolbar: '#custom_toolbar',
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
			{field:'checkbox',title:'',width:100,checkbox:true},
			{field:'name',title:'姓名',width:100,sortable:true},    
			{field:'phone',title:'手机号码',width:120,sortable:true},
			{field:'telNumb',title:'电话号码',width:120,sortable:true},
			{field:'company',title:'公司名称',width:200,sortable:true},
			{field:'createTime',title:'创建时间',width:180,sortable:true,
				formatter: function(value,row,index){
					if(value != null && value != '' ){
						return toDateTimeStr(value);
					}
				}
			},
			{field:'address',title:'公司地址',width:300,sortable:true},
			{field:'mark',title:'备注信息',width:300,sortable:true},
			{field:'isgys',title:'合作角色',width:80,align:'center',sortable:true,
				formatter: function(value,row,index){
					if (value == 0){
						return "客户";
					} else {
						return "供货商";
					}
				}
			},
			{field:'iscompany',title:'性质类型',align:'center',width:80,sortable:true,
				formatter: function(value,row,index){
					if (value == 0){
						return "个人";
					} else {
						return "单位";
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
			var columns = ['name','phone','telNumb','company','createTime','address','mark','isgys','iscompany','deleted'];
			var CHcolumns = ['姓名','手机号码','电话号码','公司名称','创建时间','公司地址','备注信息','合作角色','性质类型','删除标志'];
			for(var i = 0 ; i < columns.length ; i++){
				html += '<li><label class="hideshowFieldslbl"><a class="l-btn l-btn-small column_btn"><input type="checkbox" value="' + columns[i] + '"><span>' + CHcolumns[i] + '</span></a></label></li>';
			}
			$(".custom_all_columns").html(html);
		},
		
	});
	
	//新增、修改的弹窗属性设置
	$("#custom_window").window({
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
	$("#custom_oper_btn").linkbutton({
		onClick:function(){
			var oper = $(this).data("oper");
			var $form = $("#custom_oper_form");
			$form.form('submit',{
				url: ctx + '/infoManage/operData',
				onSubmit: function(param){    
					param.oper = oper;
					param.operId = $("#custom_oper_btn").data("id");
					return $form.form('validate');
				},
				success:function(data){
					var data = eval('(' + data + ')');
					if(data.success){
						layer.msg(data.detailInfo, {time: 1000});
						$("#custom_window").window('close');
						$("#custom_dg").datagrid('reload');
					}else{
						layer.msg(data.detailInfo, {time: 1000});
					}
				}
			});
		}
	});
	
	//关闭弹窗
	$("#custom_close_btn").linkbutton({
		onClick:function(){
			$("#custom_window").window('close');
		}
	});

	//toolbar-新增
	$("#custom_dg_insert_btn").linkbutton({
		onClick: function(){
			$("#custom_window").window({title : '新增合作商'});
			$("#custom_oper_btn").data("oper","insert");
			$("#custom_oper_form").form("reset");
			$("#custom_window").window('open');
		}
	});
	
	//toolbar-修改
	$("#custom_dg_update_btn").linkbutton({
		onClick: function(){
			bindSelectedData();
		}
	});
	
	//toolbar-删除
	$("#custom_dg_delete_btn").linkbutton({
		onClick: function(){
	    	var row = $("#custom_dg").datagrid('getSelections');
			if(row != null && row.length > 0){
				$.messager.confirm('确认','您确认要删除所选记录吗？',function(r){    
					if (r){    
						var ids = new Array(row.length); 
						for(var i = 0 ; i < row.length ; i++){
							ids[i] = row[i].id
						}
						$.customAjax({
							url: ctx + "/infoManage/operData",
							data: {
								'oper': 'delete',
								'deleteIds': ids.toString()
							},
							success: function(data){
								layer.msg(data.detailInfo, {time: 1000});
								if(data.success){
									$("#custom_dg").datagrid('reload');
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
	$("#custom_dg_import_btn").click(function(){
		$("#custom_import_div").window({
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
		$("#custom_import_div").show();
		$("#custom_importFile").filebox('clear');
		$("#custom_import_result_tips").text('');
	});
	
	$("#custom_import_num_close_btn").click(function(){
		$("#custom_import_div").window('close');
	});
	
	//toolbar-导出
	$("#custom_dg_export_btn").click(function(){
		$("#custom_choose_num").window({
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
		$("#custom_choose_num").show();
		$("#custom_exportStartNum").textbox('setValue','1');
		$("#custom_exportEndNum").textbox('setValue','');
	});
	
	//确定导出
	$("#custom_choose_num_oper_btn").click(function(){
		if($("#custom_exportStartNum").textbox('isValid') && $("#custom_exportEndNum").textbox('isValid')){
			var $form = $(".custom .query_div").find("form");
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
			var startNum = $("#custom_exportStartNum").val();
			var endNum = $("#custom_exportEndNum").val();
			startNum = Number.parseInt(startNum);
			if(endNum != null && endNum != ''){
				endNum = Number.parseInt(endNum);
			}
			if(startNum > 0){
				startNum --;
			}
			formJSON.startNum = startNum;
			formJSON.endNum = endNum;
			var t = createURL(ctx + '/infoManage/exportExcel',formJSON);
			var newwindow = window.open(t);
			newwindow.document.title = "导出报表";
			$("#custom_choose_num").window('close');
		}else{
			$("#custom_exportStartNum").textbox('validate');
			$("#custom_exportEndNum").textbox('validate');
		}
	});
	
	//关闭导出
	$("#custom_choose_num_close_btn").click(function(){
		$("#custom_choose_num").window('close');
	});
	
	//显示隐藏列
	$("#custom_dg_show_hide_btn").click(function(){
		var $tds = $(".custom .datagrid-view2 .datagrid-header .datagrid-header-row").find("td[field!='checkbox']:visible");
		var $checkboxs = $(".custom_all_columns").find("input[type='checkbox']");
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
		
		$("#custom_show_hide_column").dialog('open');
	});
	
	//修改——给控件绑定对应的值
	function bindSelectedData(){
		$("#custom_window").window({title : '修改信息'});
		var rows = $("#custom_dg").datagrid('getSelections');
		if(rows != null && rows.length > 0){
			$("#custom_oper_form").form("reset");
			var row = rows[0];
			$("#custom_oper_btn").data("oper","update");
			$("#custom_oper_btn").data("id",row.id);
			$("#custom_name").textbox('setValue',row.name);
			$("#custom_phone").textbox('setValue',row.phone);
			$("#custom_telNumb").textbox('setValue',row.telNumb);
			$("#custom_address").textbox('setValue',row.address);
			$("#custom_company").textbox('setValue',row.company);
			$("#custom_mark").textbox('setValue',row.mark);
			$("#custom_deleted").combobox('setValue',row.deleted);
			$("#custom_hzs").combobox('setValue',row.isgys);
			$("#custom_xzlx").combobox('setValue',row.iscompany);			
			if(row.createTime != null && row.createTime != '' ){
				row.createTime = toDateTimeStr(row.createTime);
			}
			$("#custom_createTime").datebox('setValue', row.createTime);
			$("#custom_window").window('open');
		}else{
			$.messager.alert('警告','请先选择要修改的行');
		}
	}

	$("#custom_dg").datagrid('resize');
	
	
	//修改地址
	$("#custom_window .updateAddress").click(function(){
		$("#baiduditu").window({    
		    width:600,    
		    height:420,    
		    modal:true,
		    title:'选择地址',
		    minimizable:false,
		    maximizable:true,
		    collapsible:false,
		    closable:true,
		    draggable:true
		});
		
		$(".contain_buttom").show();
		$("#ditu_update_sxdz").textbox('setValue',$("#custom_address").textbox('getValue'));
		$("#ditu_search_ssdz").textbox('setValue',$("#custom_address").textbox('getValue'));
		
		$("#ditu_search_btn").bind('click', function(){
			$("#ditu_update_sxdz").textbox('setValue','');
			getValAndShow($("#ditu_search_ssdz").textbox('getValue'));
		});
		
		$("#ditu_update_btn").bind('click',function(){
			var address = $("#ditu_update_sxdz").textbox('getValue');
			$("#custom_address").textbox('setValue',address);
			$("#baiduditu").window('close');
		});
	
		getValAndShow($("#custom_address").textbox('getValue'));
	});
	
	
	/**
	 * 导入
	 */
	var getprocessField = null;
	$("#custom_importForm").submit(function(){
		$("#custom_import_num_oper_btn").linkbutton('disable');
		$("#custom_import_num_close_btn").linkbutton('disable');
		
		$(".show_process_div").show();
		$("#custom_import_result_tips").text('');
		$("#custom_import_div").window({height:230});
		getprocessField = setInterval(function(){
			$.customAjax({
				url: ctx + "/common/getProgress",
				dataType: 'text',
				async:false,
				success:function(data){
					var val = $('#custom_upload_process').progressbar('getValue');
					if(data && val < 100){
						$("#custom_upload_process").progressbar('setValue',data);
					}
					if(val == 100){
						$("#custom_import_result_tips").text('上传成功，正在解析文件...');
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
		    		clearInterval(getprocessField);
					$.messager.alert('提示','获取进度条发生错误');
				}
			});
		},1000);   //每一秒钟获取一次进度条
		$("#custom_importForm").ajaxSubmit({
			type: "post",
            url: ctx + "/infoManage/uploadCustom",
            success: function (data) {
            	clearInterval(getprocessField);
            	$("#custom_upload_process").progressbar('setValue',100);
        		$(".custom_show_process_div").delay(1000).hide(1200,function(){
        			$("#custom_import_div").window({height:180});
        		});
        		$("#custom_import_result_tips").text('解析完毕，上传成功!');
        		$("#custom_import_num_oper_btn").linkbutton('enable');
        		$("#custom_import_num_close_btn").linkbutton('enable');
            },
            error: function (msg) {
        		clearInterval(getprocessField);
        		$("#custom_import_result_tips").text('解析完毕，上传失败!');
                $.messager.alert("提示","文件上传失败");
                $("#custom_import_num_oper_btn").linkbutton('enable');
        		$("#custom_import_num_close_btn").linkbutton('enable');
            }
		});
		return false;
	});
	
	$("#custom_import_num_oper_btn").click(function(){
		$("#custom_import_result_tips").text('');
		if($("#custom_importFile").filebox('isValid')){
			$("#custom_importForm").submit();
		}else{
			$("#custom_importFile").filebox('validate');
		}
	});
	
	$("#custom_download_template").click(function(){
		var newwindow = window.open(ctx + '/common/downLoadTemplate');
		newwindow.document.title = "导出上传模板";
	});
	
	
});
