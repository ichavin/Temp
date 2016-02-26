/**
 * 获取数据
 */
var getData = function(data){
	var lis_data = null;
	$.customAjax({
		url:ctx + "/scheme/getData",
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
			if(li_data.deleted == 0){
				html += '<li class="scheme_li" data-id="' + li_data.id + '" data-deleted="' + li_data.deleted + '">'
				  + '<span class="scheme_span_text">' + li_data.name + '</span>'
				  + '</li>';
			}else{
				html += '<li class="scheme_li" data-id="' + li_data.id + '" data-deleted="' + li_data.deleted + '">'
				  + '<span class="scheme_span_text red">' + li_data.name + '</span>'
				  + '</li>';
			}
		}
	}
	html += '<li class="scheme_li more_scheme">'
		  + '<span style="display:none;" class="scheme_span_text xzfa">新增方案</span>'
		  + '</li>';
	$("#scheme_ul").html(html);
}

var editIndex = undefined;
var arrays = undefined;

function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#scheme_meterial_dg').datagrid('validateRow', editIndex)){
		var meterial = $('#scheme_meterial_dg').datagrid('getEditor', {index:editIndex,field:'meterial.name'});
		var unit = $('#scheme_meterial_dg').datagrid('getEditor', {index:editIndex,field:'unit.name'});
		var meterial_name = $(meterial.target).combobox('getText');
		var meterial_id = $(meterial.target).combobox('getValue');
		var unit_name = $(unit.target).textbox('getValue');
		var row = $('#scheme_meterial_dg').datagrid('getRows')[editIndex]['meterial'];
		row.name = meterial_name;
		row.id = meterial_id;
		var unit = {};
		unit.name = unit_name;
		row.unit = unit;
		$('#scheme_meterial_dg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function append(){
	if (endEditing()){
		var row = {};
		row.id='';
		row.amount = '';
		var scheme = {}
		scheme.id = '';
		scheme.name = '';
		var meterial = {}
		meterial.id = '';
		meterial.name = '';
		var unit = {};
		unit.id = '';
		unit.name = '';
		meterial.unit = unit;
		row.scheme = scheme;
		row.meterial = meterial;
		$('#scheme_meterial_dg').datagrid('appendRow',row);
		editIndex = $('#scheme_meterial_dg').datagrid('getRows').length-1;
		$('#scheme_meterial_dg').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
	}
}

function removeit(){
	if (editIndex == undefined){return}
	$('#scheme_meterial_dg').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
	editIndex = undefined;
}

function getChanges(){
	if(endEditing()){
		var rows = $('#scheme_meterial_dg').datagrid('getRows');
		var obj = new Object();
		var objs = new Array();
		var current_id = $("#scheme_update_hidden_id").val();
		var scheme = new Object();
		scheme.id = current_id;
		for(var i = 0 ; i < rows.length ; i++){
			row = rows[i];
			obj = new Object();
			obj.scheme = scheme;
			obj.meterial = row.meterial;
			obj.amount = row.amount;
			objs.push(obj);
		}
		if(objs != null){
			$.customAjax({
				url:ctx + "/scheme/operScheme",
				data:{
					schemeId:current_id,
					schemeMeterialStr:JSON.stringify(objs)
				},
				success:function(data){
					if(data.success){
						layer.msg('修改成功', {time: 1000}); 
						$("#scheme_update").dialog('close');
					}else{
						layer.msg(data.detailInfo, {time: 1000});
					}
				}
			});
		}
	}
}

function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#scheme_meterial_dg').datagrid('selectRow', index).datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#scheme_meterial_dg').datagrid('selectRow', editIndex);
		}
	}
}

function resetIndex(){
	editIndex = undefined;
}

function rename(){
	var current_id = $("#scheme_update_hidden_id").val();
	$(".scheme_old_name").text($("#scheme_update").dialog('options').title);
	$("#scheme_rename_dialog").dialog('open');
	$("#scheme_new_name").textbox('setValue','');
	$("#rename_exist").hide();
}

function update_name(){
	if($("#scheme_new_name").textbox('isValid')){
		var current_id = $("#scheme_update_hidden_id").val();
		$.customAjax({
			url:ctx + "/scheme/updateSchemeName",
			data:{
				currentId:current_id,
				newName:$("#scheme_new_name").textbox('getValue')
			},
			success:function(data){
				if(data.success){
					layer.msg('修改成功', {time: 1000});
					$("#scheme_rename_dialog").dialog('close');
					$("#scheme_update").dialog({title:$("#scheme_new_name").textbox('getValue')});
					getData(null);
				}else{
					$("#rename_exist").show().text(data.detailInfo);
				}
			}
		});
	}
}


function deleted_current_scheme(ths){
	var current_id = $("#scheme_update_hidden_id").val();
	var deleted = $(ths).data('deleted');
	if(deleted == 0){
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		        $.customAjax({
		        	url:ctx + "/scheme/deleteScheme",
		        	data:{
		        		currentId:current_id,
		        	},
		        	success:function(data){
		        		if(data.success){
		        			layer.msg('删除成功', {time: 1000});
							$("#scheme_update").dialog('close');
							getData(null);
						}else{
							layer.msg(data.detailInfo, {time: 3000});
						}
		        	}
		        });    
		    }    
		}); 
	}else{
		$.customAjax({
        	url:ctx + "/scheme/revertDeleteScheme",
        	data:{
        		currentId:current_id,
        	},
        	success:function(data){
        		if(data.success){
        			layer.msg('撤销删除成功', {time: 1000});
					$("#scheme_update").dialog('close');
					getData(null);
				}else{
					layer.msg(data.detailInfo, {time: 3000});
				}
        	}
        });
	}
}

function getMeterialUnit(){
	$.customAjax({
		url:ctx + '/meterialManage/getMeterialUnitList',
		success:function(data){
			arrays = data;
		}
	});
}

function changeUnit(record){
	if(arrays == undefined){
		getMeterialUnit();
	}
	$(this).parents("td[field='meterial.name']").next().find("input:first").textbox('setValue',arrays[record.value]);
}

function insertChangeUnit(record){
	if(arrays == undefined){
		getMeterialUnit();
	}
	$(this).parent().next().find("input:first").textbox("setValue",arrays[record.value]);
}


$(function(){
	
	getData(null);
	
	
	$("body").on("mouseover",".more_scheme",function(){
		$(".xzfa").css("display","inline-table");
	}).on("mouseout",".more_scheme",function(){
		$(".xzfa").css("display","none");
	});
	
	/**
	 * 初始化查询模块
	 */
	function autoCreateSearchModule(){
		var lis = new Array();
		var meterialData = new Array();
		$.customAjax({
			url:ctx + '/meterialManage/getMeterialSelectList',
			async:false,
			success:function(data){
				if(data != null)
					meterialData = data;
			}
		});
		lis.push({"lableName":"方案名称","type":"textbox","id":null,"name":"name"});
		lis.push({"lableName":"包含材料","type":"combobox","id":"includeMeterial","name":"meterial_id","role":"panelHeight:'auto',editable:false,panelMaxHeight:160,multiple:true,valueField: 'value',textField: 'text',data: " + JSON.stringify(meterialData).replace(/"/g,'\'')});
		lis.push({"lableName":"删除标志","type":"combobox","id":null,"name":"deleted","role":"panelHeight:'auto',editable:false,panelMaxHeight:160,valueField: 'value',textField: 'text',value:'-1',data: [{value:'-1',text:'全部'},{value:'0',text:'未删除'},{value:'1',text:'已删除'}]"});
		var btns = new Array();
		btns.push({"id":"clear_btn","clazz":"easyui-linkbutton","options":"","icon":"circle-o","name":"清空"});
		btns.push({"id":"search_btn","clazz":"easyui-linkbutton","options":"","icon":"search","name":"查询"});
		createSearchModule(lis,btns,"scheme");
		$(".scheme .easyui-validatebox").validatebox();
		$(".scheme .easyui-textbox").textbox();
		$(".scheme .easyui-combobox").combobox();
		$(".scheme .easyui-linkbutton").linkbutton();
	}
	
	autoCreateSearchModule();
	

	/**
	 * 查询
	 */
	$(".scheme .query_div").on("click","#scheme_search_btn",function(){
		var $form = $(this).parents(".query_div").find("form");
		var formJSON = $form.serialize();
		getData(formJSON);
	});


	/**
	 * 清空 
	 */
    $(document).on("click","#scheme_clear_btn",function(){
    	var $form = $(this).parents(".query_div").find("form");
    	$form.form('reset');
    	$("#includeMeterial").combobox('clear');
    	getData(null);
    });
	
   
    /**
     * panel
     */
	$("#scheme_content").panel({
		  fit:true,
		  title: '产品方案'
	});
	
	/**
	 * panel的大小
	 */
	function panelresize(){
		$("#scheme_contain").css('height',($(".scheme").height() - $(".scheme .query_div").height() - 24) + "px");
		$("#scheme_content").panel('resize',{});
	}
	
	$(window).resize(function(){
		setTimeout(panelresize,100);	
	});
	
	panelresize();
	
	
	/**
	 * 绑定每个产品方案的click事件
	 */
	var editIndex = undefined;
	$("body").on("click",".scheme_li",function(){
		getMeterialUnit();
		var current_id = $(this).data("id");
		var deleted = $(this).data("deleted");
		if($(this).attr("class").indexOf("more_scheme") != -1){
			$("#scheme_insert").dialog({title:'新增产品方案'}).dialog('open');
			$(".scheme_insert_msg").text("").hide();
		}else{
			resetIndex();
			$("#scheme_update").dialog({title:$(this).text()}).dialog('open');
			$("#deleted_scheme").data("deleted",deleted);
			if(deleted == 1){
				$("#deleted_scheme").linkbutton({text:'<i class="btn_icon fa fa-share"></i> 撤销删除方案'});
			}else{
				$("#deleted_scheme").linkbutton({text:'<i class="btn_icon fa fa-times-circle"></i> 删除当前方案'});
			}
			$("#scheme_update_hidden_id").val(current_id);
			$("#scheme_meterial_dg").datagrid({
				noheader:true,
				url: ctx + '/scheme/getSchemeMeterialData',
				queryParams: {
					scheme_id:current_id
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
						},
						editor:{
							type:'combobox',
							options:{
								panelHeight:'auto',
								required:true,
								editable:false,
								valueField:'value',
								textField:'text',
								panelMaxHeight: 170,
								url:ctx + '/meterialManage/getMeterialSelectList',
								onSelect:changeUnit
							}
						}
					},    
					{field:'unit.name',title:'单位',width:120,
						formatter: function(value,row,index){
							return row.meterial.unit.name;
						},
						editor:{
							type:'textbox',
							options:{
								required:true,
								editable:false
							}
						}
					},
					{field:'amount',title:'数量',width:120,
						editor:{
							type:'textbox',
							options:{
								required:true,
								validType:'number'
							}
						}
					}
				]],
				onClickRow:onClickRow,
				onBeginEdit:function(rowIndex, rowData){
					var meterialDom = $("#scheme_update").find("td[field='meterial.name'] .datagrid-editable-input");
					var unitDom = $("#scheme_update").find("td[field='unit.name'] .datagrid-editable-input");
					setTimeout(function(){
						var objs = meterialDom.combobox('getData');
						var meterial_id = rowData.meterial.id;
						var unit_name = rowData.meterial.unit.name;
						for(var i = 0 ; i < objs.length ; i ++){
							if(objs[i].value == meterial_id){
								meterialDom.combobox('setValue',meterial_id);
								break;
							}
						}
						unitDom.textbox('setValue',unit_name);
					},300);
				}
			});
		}
	});
	
	$("#seche_insert_add").click(function(){
		var num = $("#scheme_insert_table").find("tbody tr").length + 1;
		var html = "<tr><td><a href='javascript:void(0);' class='insert_tr_deleted'><i class='fa fa-trash-o' style='color:#49A4F0;font-size:15px;'></i></a></td>"
				+ " <td><input class='easyui-combobox cob1" + num + " width110' data-options=\"panelHeight:'auto',panelMaxHeight:160,required:true,editable:false,valueField:'value',textField:'text',url:'" + ctx + "/meterialManage/getMeterialSelectList'\" /></td>"
				/*+ "<td><input class='easyui-combobox cob2" + num + " width110' data-options=\"panelHeight:'auto',panelMaxHeight:160,required:true,editable:false,valueField:'value',textField:'text',url:'" + ctx + "/unit/getUnitSelectList'\" /></td>"*/
				+ "<td><input class='easyui-textbox tb1" + num + " width110' data-options='editable:false'/></td>"
				+ "<td><input class='easyui-textbox tb2" + num + " width110' data-options=\"required:true,validType:'number'\"/></td></tr>";
		$(html).appendTo("#scheme_insert_table tbody");
		$(".cob1" + num).combobox({
			onSelect:insertChangeUnit
		});
		/*$(".cob2" + num).combobox();*/
		$(".tb1" + num).textbox();
		$(".tb2" + num).textbox();
	});
	
	$("body").on("click",".insert_tr_deleted",function(){
		$(this).parent().parent().remove();
	});
	
	/**
	 * 插入新产品方案
	 */
	$("#seche_insert_sure").click(function(){
		var $form = $("#scheme_insert_form");
		if($form.form('validate')){
			var trs = $("#scheme_insert_table").find("tbody tr");
			var objs = new Array();
			var obj;
			var unit;
			$(trs).each(function(index){
				++index;
				var cob1 = $(".cob1" + index).combobox('getValue');
				/*var cob2 = $(".cob2" + index).combobox('getValue');*/
				var tb2 = $(".tb2" + index).textbox('getValue');
				obj = new Object();
				meterial = new Object();
				meterial.id = cob1;
				obj.meterial = meterial;
				obj.unit = null;
				obj.amount = tb2;
				obj.id = index;
				objs.push(obj);
			});
			if(objs.length >= 0){
				$.customAjax({
					url: ctx + "/scheme/insertScheme",
					data: {
						schemeMeterialStr:JSON.stringify(objs),
						schemeName:$("#add_scheme_name").textbox('getValue')
					},
					type:"POST",
					success:function(data){
						if(data.success){
							layer.msg('新增成功', {time: 1000}); 
							$("#scheme_insert").dialog('close');
							getData(null);
						}else{
							if(data.code == null || data.code == ''){
								$.messager.alert('警告',data.detailInfo);
							}else{
								$(".scheme_insert_msg").text(data.detailInfo).show();
							}
						}
					}
				});
			}
		}
	});
	
	$("#scheme_insert_meter_cob").combobox({
		editable:true,
		onHidePanel:function(){
			if(!validCustomSelect("#scheme_insert_meter_cob")){
				$("#scheme_insert_meter_cob").combobox('clear');
			}
		}
	});
	/*$("#scheme_insert_unit_cob").combobox({
		editable:true,
		onHidePanel:function(){
			if(!validCustomSelect("#scheme_insert_unit_cob")){
				$("#scheme_insert_unit_cob").combobox('clear');
			}
		}
	});*/
	
	
});

