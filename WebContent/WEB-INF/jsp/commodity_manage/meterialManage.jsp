<!-- 材料管理-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="main-contain meterialManage">
	<fieldset class="query_div">
		<legend> 查询条件 </legend>
	</fieldset>
	<div style="width:100%;min-height: 475px;">
		<table id="meterialManage_dg"></table>
	</div>
	<div id="meterialManage_window" class="easyui-window meterialManage_remove">
		<div class="padding_top_15">
			<form id="meterialManage_oper_form" method="post">
				<ul class="search_module_ul">
					<li class="search_module_li"><label>材料名称：</label>
						<input id="meterialManage_name" name="name" class="easyui-textbox" data-options="required:true" />
					</li>
					<li class="search_module_li"><label>单位：</label>
						<input id="meterialManage_unit" name="unit_id" class="easyui-combobox" data-options="required:true,width:173,panelWidth:170,panelHeight:'auto',editable:true,panelMaxHeight:160" />
					</li>
					<li class="search_module_li"><label>供货商：</label>
						<input id="meterialManage_gys" name="custom_id" class="easyui-combobox" data-options="required:true,width:173,panelWidth:170,panelHeight:'auto',editable:true,panelMaxHeight:160" />
					</li>
					<li class="search_module_li"><label>删除标志：</label>
						<input id="meterialManage_deleted" name="deleted" class="easyui-combobox" data-options="required:true,panelHeight: 'auto',editable: false,valueField: 'value',textField: 'text',value:'0',data: [{value:'0',text:'未删除'},{value:'1',text:'已删除'}]" />
					</li>
				</ul>
			</form>
			<div class="clear"></div>
		</div>
		<div class="meterialManage_search_module_btn_div search_module_btn_div">
			<span>
				<a id="meterialManage_oper_btn" href="javascript:void(0);" class="easyui-linkbutton" options="">
					<i class="btn_icon fa fa-check"></i> 确 定
				</a>
			</span>
			<span>
				<a id="meterialManage_close_btn" href="javascript:void(0);" class="easyui-linkbutton" options="">
					<i class="btn_icon fa fa-close"></i> 关 闭
				</a>
			</span>
		</div>
	</div>
	
	    <!-- 导入 -->
        <div id="meterialManage_import_div" style="display:none;">
        	<div class="import_ps">
        		<p>注：上传文件前请先下载上传文件模板，如未下载 <a id="meterialManage_download_template" href="javascript:void(0);" style="text-decoration: underline;color:blue;"> 立即下载上传模板</a></p>
        	</div>
        	<div class="importForm_contain">
	        	<form id="meterialManage_importForm" name="importForm" enctype="multipart/form-data" method="post">
	        		<label>上传文件：</label>
	        		<input id="meterialManage_importFile" name="importFile" class="easyui-filebox" style="width:200px" data-options="buttonText:'选择文件',required:true">
	        	</form>
	        	<div class="meterialManage_show_process_div show_process_div">
		        	<div><span id="meterialManage_upload_tips">正在上传...</span>，请勿刷新和关闭！</div>
		        	<div id="meterialManage_upload_process" class="easyui-progressbar" style="width:260px;"></div>
		        </div>
	        </div>
        	<div class="meterialManage_import_module_btn_div import_module_btn_div">
        		<span id="meterialManage_import_result_tips import_result_tips"></span>
				<span><a id="meterialManage_import_num_oper_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-check"></i> 确 定</a></span>
				<span><a id="meterialManage_import_num_close_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-close"></i> 关 闭</a></span>
			</div>
        </div>
        
        <!-- 导出 -->
        <div id="meterialManage_choose_num" style="display:none;">
        	<div class="choose_module_content">
        		<label>导出起始行：</label><input id="meterialManage_exportStartNum" name="exportStartNum" class="easyui-textbox" data-options="validType:'moreThanNumber[0]',width:135,required:true"/><br/>
        		<label>导出结束行：</label><input id="meterialManage_exportEndNum" name="exportEndNum" class="easyui-textbox" data-options="validType:'number',width:135"/>
        	</div>
			<div class="choose_module_btn_div">
				<span><a id="meterialManage_choose_num_oper_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-check"></i> 确 定</a></span>
				<span><a id="meterialManage_choose_num_close_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-close"></i> 关 闭</a></span>
			</div>
		</div>
		
		<!-- 隐藏显示列 -->
        <div id="meterialManage_show_hide_column" class="easyui-dialog meterialManage_remove" data-options="resizable : false,width : 290,collapsible : false,minimizable : false,maximizable:false,height : 250,modal : true,cache : false,title : '勾选显示的列',closed: true">
        	<div class="meterialManage_all_columns columns_style">

        	</div>
			<div id="hideShowColumnBtn" class="dialog-button">
		     	<a name="checkAll" href="javascript:void(0)" data-modul="meterialManage_" onclick="checkAll(this)" class="easyui-linkbutton"><i class="btn_icon fa fa-check-square-o"></i> 全选</a>
		     	<a name="clearAllChecked" href="javascript:void(0)" data-modul="meterialManage_" onclick="clearAllChecked(this)" class="easyui-linkbutton"><i class="btn_icon fa fa-square-o"></i> 清空</a> 
		     	<a name="sureChecked" href="javascript:void(0)" data-modul="meterialManage_" onclick="settingShowHideColumn(this)" class="easyui-linkbutton"><i class="btn_icon fa fa-check"></i> 确 定</a> 
		     	<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="meterialManage_" onclick="javascript:$('#meterialManage_show_hide_column').dialog('close')"><i class="btn_icon fa fa-close"></i> 取消</a>
			</div>
		</div>
		
        <!-- datagird - toolbar -->
        <div id="meterialManage_toolbar">
			<a id="meterialManage_dg_insert_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-plus btn_icon"></i> 新 增</a>
			<a id="meterialManage_dg_update_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-pencil btn_icon"></i> 编 辑</a>
			<a id="meterialManage_dg_delete_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-minus btn_icon"></i> 删 除</a>
			<!-- <a id="meterialManage_dg_import_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-arrow-circle-o-up btn_icon"></i> 导 入</a> -->
			<a id="meterialManage_dg_export_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-arrow-circle-o-down btn_icon"></i> 导 出</a>
			<a id="meterialManage_dg_show_hide_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-server btn_icon"></i> 显示隐藏列</a>
		</div>
	
	<script type="text/javascript" src="${ctx}/resource/js/meterial-manage.js"></script>
</div>