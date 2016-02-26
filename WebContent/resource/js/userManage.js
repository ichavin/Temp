$(function(){
	
	/**
	 * 初始化查询模块
	 */
	function autoCreateSearchModule(){
		var lis = new Array();
		lis.push({"lableName":"登录名","type":"textbox","id":null,"name":"loginName"});
		lis.push({"lableName":"真实名","type":"textbox","id":null,"name":"realName"});
		lis.push({"lableName":"email","type":"textbox","id":null,"name":"email"});
		lis.push({"lableName":"手机号码","type":"textbox","id":null,"name":"phone"});
		lis.push({"lableName":"身份证号码","type":"textbox","id":null,"name":"idCard"});
		lis.push({"lableName":"性别","type":"combobox","id":null,"name":"sex","role":"editable: false,panelHeight: 'auto',panelMaxHeight:160,valueField: 'value',textField: 'text',value:'-1',data: [{value:'-1',text:'全部'},{value:'0',text:'女'},{value:'1',text:'男'}]"});
		lis.push({"lableName":"删除标志","type":"combobox","id":null,"name":"deleted","role":"editable: false,panelHeight: 'auto',panelMaxHeight:160,valueField: 'value',textField: 'text',value:'-1',data: [{value:'-1',text:'全部'},{value:'0',text:'未删除'},{value:'1',text:'已删除'}]"});
		var btns = new Array();
		btns.push({"id":"clear_btn","clazz":"easyui-linkbutton","options":"","icon":"circle-o","name":"清空"});
		btns.push({"id":"search_btn","clazz":"easyui-linkbutton","options":"","icon":"search","name":"查询"});
		createSearchModule(lis,btns,"userManage");
		$(".userManage .easyui-validatebox").validatebox();
		$(".userManage .easyui-textbox").textbox();
		$(".userManage .easyui-combobox").combobox();
		$(".userManage .easyui-datetimebox").datetimebox();
		$(".userManage .easyui-linkbutton").linkbutton();
	}
	
	autoCreateSearchModule();

	/**
	 * 查询
	 */
	$(".userManage .query_div").on("click","#userManage_search_btn",function(){
		var $form = $(this).parents(".query_div").find("form");
		var formJSON = $form.serializeJson();
		$("#userManage_dg").datagrid('reload',formJSON);
	});


	/**
	 * 清空 
	 */
    $(document).on("click","#userManage_clear_btn",function(){
    	var $form = $(this).parents(".query_div").find("form");
    	$form.form('reset');
    	$("#userManage_dg").datagrid('reload',{isgys:-1,iscompany:-1});
    });
	
    /**
     * 加载数据表格
     */
	$("#userManage_dg").datagrid({
		title: '用户列表',
		url: ctx + '/userManage/getData',
		queryParams: {
			deleted: -1
		},
		checkbox:true,
		toolbar: '#userManage_toolbar',
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
			{field:'password',title:'id',hidden:true,width:100},
			{field:'checkbox',title:'',width:100,checkbox:true},
			{field:'loginName',title:'登录名',sortable:true,width:120},
			{field:'realName',title:'真实名',sortable:true,width:120},
			{field:'sex',title:'性别',width:60,sortable:true,
				formatter: function(value,row,index){
					if (value == 0){
						return "女";
					} else {
						return "男";
					}
				}
			},    
			{field:'email',title:'邮箱',width:150,sortable:true},
			{field:'phone',title:'手机号码',width:120,sortable:true},
			{field:'idCard',title:'身份证号码',width:150,sortable:true},
			{field:'birthday',title:'生日',width:150,align:'center',sortable:true,
				formatter: function(value,row,index){
					if(value != null && value != '' ){
						return toDateTimeStr(value);
					}
				}
			},
			{field:'lastLoginTime',title:'最后登录时间',width:150,align:'center',sortable:true,
				formatter: function(value,row,index){
					if(value != null && value != '' ){
						return toDateTimeStr(value);
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
			},
			{field:'graduateSchool',title:'毕业学校',width:200,sortable:true}
		]],
		onLoadSuccess: function(data){
			var html = "";
			var columns = ['loginName','realName','sex','email','phone','idCard','birthday','deleted','lastLoginTime','graduateSchool'];
			var CHcolumns = ['登录名','真实名','性别','邮箱','手机号码','身份证号码','生日','删除标志','最后登录时间','毕业学校'];
			for(var i = 0 ; i < columns.length ; i++){
				html += '<li><label class="hideshowFieldslbl"><a class="l-btn l-btn-small column_btn"><input type="checkbox" value="' + columns[i] + '"><span>' + CHcolumns[i] + '</span></a></label></li>';
			}
			$(".userManage_all_columns").empty().html(html);
		}
		
	});
	
	//新增、修改的弹窗属性设置
	$("#userManage_window").window({
		modal : true,
		resizable : false,
		width : 700,
		collapsible : false,
		minimizable : false,
		maximizable:false,
		height : 270,
		modal : true,
		cache : false,
		closed:true
	});
	
	//确定修改和确定新增
	$("#userManage_oper_btn").linkbutton({
		onClick:function(){
			var oper = $(this).data("oper");
			var $form = $("#userManage_oper_form");
			$form.form('submit',{
				url: ctx + '/userManage/operData',
				onSubmit: function(param){    
					param.oper = oper;
					param.operId = $("#userManage_oper_btn").data("id");
					return $form.form('validate');
				},
				success:function(data){
					var data = eval('(' + data + ')');
					if(data.success){
						layer.msg(data.detailInfo, {time: 1000});
						$("#userManage_window").window('close');
						$("#userManage_dg").datagrid('reload');
					}else{
						$.messager.alert('提示',data.detailInfo);
					}
				}
			});
		}
	});
	
	//关闭弹窗
	$("#userManage_close_btn").linkbutton({
		onClick:function(){
			$("#userManage_window").window('close');
		}
	});

	//toolbar-新增
	$("#userManage_dg_insert_btn").linkbutton({
		onClick: function(){
			$("#userManage_window").window({title : '新增用户'});
			$("#userManage_oper_btn").data("oper","insert");
			$("#userManage_oper_form").form("reset");
			$("#userManage_window").window('open');
		}
	});
	
	
	//toolbar-修改
	$("#userManage_dg_update_btn").linkbutton({
		onClick: function(){
			bindSelectedData();
		}
	});
	
	//toolbar-删除
	$("#userManage_dg_delete_btn").linkbutton({
		onClick: function(){
	    	var row = $("#userManage_dg").datagrid('getSelections');
			if(row != null && row.length > 0){
				$.messager.confirm('确认','您确认要删除所选记录吗？',function(r){    
					if (r){    
						var ids = new Array(row.length); 
						for(var i = 0 ; i < row.length ; i++){
							ids[i] = row[i].id
						}
						$.customAjax({
							url: ctx + "/userManage/operData",
							data: {
								'oper': 'delete',
								'deleteIds': ids.toString()
							},
							success: function(data){
								layer.msg(data.detailInfo, {time: 1000});
								if(data.success){
									$("#userManage_dg").datagrid('reload');
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
	$("#userManage_dg_import_btn").click(function(){
		$("#userManage_import_div").window({
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
		$("#userManage_import_div").show();
		$("#userManage_userManage_importFile").filebox('clear');
		$("#userManage_import_result_tips").text('');
	});
	
	$("#userManage_import_num_close_btn").click(function(){
		$("#userManage_import_div").window('close');
	});
	
	//toolbar-导出
	$("#userManage_dg_export_btn").click(function(){
		$("#userManage_choose_num").window({
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
		$("#userManage_choose_num").show();
		$("#userManage_exportStartNum").textbox('setValue','1');
		$("#userManage_exportEndNum").textbox('setValue','');
	});
	
	//确定导出
	$("#userManage_choose_num_oper_btn").click(function(){
		if($("#userManage_exportStartNum").textbox('isValid') && $("#userManage_exportEndNum").textbox('isValid')){
			var $form = $(".userManage .query_div").find("form");
			var formJSON = $form.serializeJson();
			var startNum = $("#userManage_exportStartNum").val();
			var endNum = $("#userManage_exportEndNum").val();
			startNum = Number.parseInt(startNum);
			if(endNum != null && endNum != ''){
				endNum = Number.parseInt(endNum);
			}
			if(startNum > 0){
				startNum --;
			}
			formJSON.startNum = startNum;
			formJSON.endNum = endNum;
			var t = createURL(ctx + '/userManage/exportExcel',formJSON);
			var newwindow = window.open(t);
			newwindow.document.title = "导出报表";
			$("#userManage_choose_num").window('close');
		}else{
			$("#userManage_exportStartNum").textbox('validate');
			$("#userManage_exportEndNum").textbox('validate');
		}
	});
	
	//关闭导出
	$("#userManage_choose_num_close_btn").click(function(){
		$("#userManage_choose_num").window('close');
	});
	
	//显示隐藏列
	$("#userManage_dg_show_hide_btn").click(function(){
		var $tds = $(".userManage .datagrid-view2 .datagrid-header .datagrid-header-row").find("td[field!='checkbox']:visible");
		var $checkboxs = $(".userManage_all_columns").find("input[type='checkbox']");
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
		$("#userManage_show_hide_column").dialog('open');
	});
	
	//修改——给控件绑定对应的值
	function bindSelectedData(){
		$("#userManage_window").window({title : '修改用户信息'});
		var rows = $("#userManage_dg").datagrid('getSelections');
		if(rows != null && rows.length > 0){
			var row = rows[0];
			$("#userManage_oper_btn").data("oper","update");
			$("#userManage_oper_btn").data("id",row.id);
			
			$("#userManage_loginName").textbox('setValue',row.loginName);
			$("#userManage_realName").textbox('setValue',row.realName);
			//$("#userManage_password").textbox('setValue',row.password);
			$("#userManage_email").textbox('setValue',row.email);
			$("#userManage_phone").textbox('setValue',row.phone);
			$("#userManage_idCard").textbox('setValue',row.idCard);
			$("#userManage_school").textbox('setValue',row.graduateSchool);
			if(row.birthday != null && row.birthday != '' ){
				row.birthday = toDateTimeStr(row.birthday);
			}
			$("#userManage_birthday").datetimebox('setValue',row.birthday);
			$("#userManage_sex").combobox('setValue',row.sex);
			$("#userManage_deleted").combobox('setValue',row.deleted);
			$("#userManage_window").window('open');
		}else{
			$.messager.alert('警告','请先选择要修改的行');
		}
	}

	$("#userManage_dg").datagrid('resize');
	
	
	/**
	 * 导出
	 */
	var getprocessField = null;
	$("#userManage_importForm").submit(function(){
		$("#userManage_import_num_oper_btn").linkbutton('disable');
		$("#userManage_import_num_close_btn").linkbutton('disable');
		
		$(".show_process_div").show();
		$("#userManage_import_result_tips").text('');
		$("#userManage_import_div").window({height:230});
		getprocessField = setInterval(function(){
			$.customAjax({
				url: ctx + "/common/getProgress",
				dataType: 'text',
				async:false,
				success:function(data){
					var val = $('#userManage_upload_process').progressbar('getValue');
					if(data && val < 100){
						$("#userManage_upload_process").progressbar('setValue',data);
					}
					if(val == 100){
						$("#userManage_import_result_tips").text('上传成功，正在解析文件...');
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
		    		clearInterval(getprocessField);
					$.messager.alert('提示','获取进度条发生错误');
				}
			});
		},1000);   //每一秒钟获取一次进度条
		$("#userManage_importForm").ajaxSubmit({
			type: "post",
            url: ctx + "/infoManage/uploadCustom",
            success: function (data) {
            	clearInterval(getprocessField);
            	$("#userManage_upload_process").progressbar('setValue',100);
        		$(".userManage_show_process_div").delay(1000).hide(1200,function(){
        			$("#userManage_import_div").window({height:180});
        		});
        		$("#userManage_import_result_tips").text('解析完毕，上传成功!');
        		$("#userManage_import_num_oper_btn").linkbutton('enable');
        		$("#userManage_import_num_close_btn").linkbutton('enable');
            },
            error: function (msg) {
        		clearInterval(getprocessField);
        		$("#userManage_import_result_tips").text('解析完毕，上传失败!');
                $.messager.alert("提示","文件上传失败");
                $("#userManage_import_num_oper_btn").linkbutton('enable');
        		$("#userManage_import_num_close_btn").linkbutton('enable');
            }
		});
		return false;
	});
	
	$("#userManage_import_num_oper_btn").click(function(){
		$("#userManage_import_result_tips").text('');
		if($("#userManage_importFile").filebox('isValid')){
			$("#userManage_importForm").submit();
		}else{
			$("#userManage_importFile").filebox('validate');
		}
	});
	
	$("#userManage_download_template").click(function(){
		var newwindow = window.open(ctx + '/common/downLoadTemplate');
		newwindow.document.title = "导出上传模板";
	});
	
});


