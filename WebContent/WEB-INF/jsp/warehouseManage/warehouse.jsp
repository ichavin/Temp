<!-- 仓库库存管理-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="main-contain warehouse">
	<fieldset class="query_div">
		<legend> 查询条件 </legend>
	</fieldset>
	<div style="min-height: 498px;">
		<table id="warehouse_dg"></table>
	</div>
	<div id="warehouse_window" class="easyui-window warehouse_remove">
		<div class="padding_top_15">
			<form id="warehouse_oper_form" method="post">
				<ul class="search_module_ul">
					<li class="search_module_li"><label>名称：</label>
						<input id="warehouse_name" name="name" class="easyui-textbox" data-options="required:true" />
					</li>
					<li class="search_module_li"><label>类型：</label>
						<input id="warehouse_type" name="type" class="easyui-combobox" data-options="required:true,panelHeight: 'auto',panelMaxHeight:160,editable: false,valueField: 'value',textField: 'text',value:'0',data: [{value:'0',text:'产品'},{value:'1',text:'材料'},{value:'2',text:'其它'}]" />
					</li>
					<li class="search_module_li"><label>单位：</label>
						<input id="warehouse_unit" name="unit_id" class="easyui-textbox" data-options="required:true,editable: false,valueField: 'value',textField: 'text'" />
					</li>
					<li class="search_module_li"><label>次品数量：</label>
						<input id="warehouse_defectiveAmount" name="defectiveAmount" class="easyui-textbox" data-options="required:true,validType:'number',onChange:function(newValue, oldValue){
						if($(this).textbox('isValid')){
								if($('#warehouse_qualityAmount').textbox('isValid') && $('#warehouse_qualityAmount').textbox('getValue') != ''){
									$('#warehouse_totleAmount').textbox('setValue',Number.parseInt($(this).textbox('getValue')) + Number.parseInt($('#warehouse_qualityAmount').textbox('getValue'))); 
								}
							}
						}"/>
					</li>
					<li class="search_module_li"><label>正品数量：</label>
						<input id="warehouse_qualityAmount" name="qualityAmount" class="easyui-textbox" data-options="required:true,validType:'number',onChange:function(newValue, oldValue){
						if($(this).textbox('isValid')){
								if($('#warehouse_defectiveAmount').textbox('isValid') && $('#warehouse_defectiveAmount').textbox('getValue') != ''){
									$('#warehouse_totleAmount').textbox('setValue',Number.parseInt($(this).textbox('getValue')) + Number.parseInt($('#warehouse_defectiveAmount').textbox('getValue'))); 
								}
							}
						}"/>
					</li>
					<li class="search_module_li"><label>总数量：</label>
						<input id="warehouse_totleAmount" name="totleAmount" class="easyui-textbox" data-options="required:true,validType:'number'" />
					</li>
					<li class="search_module_li"><label>入仓时间：</label>
						<input id="warehouse_produceTime" name="produceTimeStr" class="easyui-datetimebox" data-options="editable:false" />
					</li>
				</ul>
			</form>
			<div class="clear"></div>
		</div>
		<div class="warehouse_search_module_btn_div search_module_btn_div" style="clear:both;">
			<span>
				<a id="warehouse_oper_btn" href="javascript:void(0);" class="easyui-linkbutton" options="">
					<i class="btn_icon fa fa-check"></i> 确 定
				</a>
			</span>
			<span>
				<a id="warehouse_close_btn" href="javascript:void(0);" class="easyui-linkbutton" options="">
					<i class="btn_icon fa fa-close"></i> 关 闭
				</a>
			</span>
		</div>
	</div>
	
	    <!-- 导入 -->
        <div id="warehouse_import_div" style="display:none;">
        	<div class="import_ps">
        		<p>注：上传文件前请先下载上传文件模板，如未下载 <a id="warehouse_download_template" href="javascript:void(0);" style="text-decoration: underline;color:blue;"> 立即下载上传模板</a></p>
        	</div>
        	<div class="importForm_contain">
	        	<form id="warehouse_importForm" name="importForm" enctype="multipart/form-data" method="post">
	        		<label>上传文件：</label>
	        		<input id="warehouse_importFile" name="importFile" class="easyui-filebox" style="width:200px" data-options="buttonText:'选择文件',required:true">
	        	</form>
	        	<div class="warehouse_show_process_div show_process_div">
		        	<div><span id="warehouse_upload_tips">正在上传...</span>，请勿刷新和关闭！</div>
		        	<div id="warehouse_upload_process" class="easyui-progressbar" style="width:260px;"></div>
		        </div>
	        </div>
        	<div class="warehouse_import_module_btn_div import_module_btn_div">
        		<span id="warehouse_import_result_tips import_result_tips"></span>
				<span><a id="warehouse_import_num_oper_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-check"></i> 确 定</a></span>
				<span><a id="warehouse_import_num_close_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-close"></i> 关 闭</a></span>
			</div>
        </div>
        
        <!-- 导出 -->
        <div id="warehouse_choose_num" style="display:none;">
        	<div class="choose_module_content">
        		<label>导出起始行：</label><input id="warehouse_exportStartNum" name="exportStartNum" class="easyui-textbox" data-options="validType:'moreThanNumber[0]',width:135,required:true"/><br/>
        		<label>导出结束行：</label><input id="warehouse_exportEndNum" name="exportEndNum" class="easyui-textbox" data-options="validType:'number',width:135"/>
        	</div>
			<div class="choose_module_btn_div">
				<span><a id="warehouse_choose_num_oper_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-check"></i> 确 定</a></span>
				<span><a id="warehouse_choose_num_close_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-close"></i> 关 闭</a></span>
			</div>
		</div>
		
		<!-- 隐藏显示列 -->
        <div id="warehouse_show_hide_column" class="easyui-dialog warehouse_remove" data-options="resizable : false,width : 290,collapsible : false,minimizable : false,maximizable:false,height : 250,modal : true,cache : false,title : '勾选显示的列',closed: true">
        	<div class="warehouse_all_columns columns_style">

        	</div>
			<div id="hideShowColumnBtn" class="dialog-button">
		     	<a name="checkAll" href="javascript:void(0)" data-modul="warehouse_" onclick="checkAll(this)" class="easyui-linkbutton"><i class="btn_icon fa fa-check-square-o"></i> 全选</a>
		     	<a name="clearAllChecked" href="javascript:void(0)" data-modul="warehouse_" onclick="clearAllChecked(this)" class="easyui-linkbutton"><i class="btn_icon fa fa-square-o"></i> 清空</a> 
		     	<a name="sureChecked" href="javascript:void(0)" data-modul="warehouse_" onclick="settingShowHideColumn(this)" class="easyui-linkbutton"><i class="btn_icon fa fa-check"></i> 确 定</a> 
		     	<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="warehouse_" onclick="javascript:$('#warehouse_show_hide_column').dialog('close')"><i class="btn_icon fa fa-close"></i> 取消</a>
			</div>
		</div>
		
        <!-- datagird - toolbar -->
        <div id="warehouse_toolbar">
			<a id="warehouse_dg_insert_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-plus btn_icon"></i> 新 增</a>
			<a id="warehouse_dg_update_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-pencil btn_icon"></i> 编 辑</a>
			<a id="warehouse_dg_delete_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-minus btn_icon"></i> 删 除</a>
			<!-- <a id="warehouse_dg_import_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-arrow-circle-o-up btn_icon"></i> 导 入</a> -->
			<a id="warehouse_dg_export_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-arrow-circle-o-down btn_icon"></i> 导 出</a>
			<a id="warehouse_dg_show_hide_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-server btn_icon"></i> 显示隐藏列</a>
		</div>
	
	<script type="text/javascript" src="${ctx}/resource/js/warehouse.js"></script>
</div>