<!-- 供货商资料 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="main-contain custom" >
	<fieldset class="query_div">
		<legend> 查询条件 </legend>
		<!-- 		<div>
			<form method="post">   
				<ul class="search_module_ul">
					<li class="search_module_li"><label>姓名：</label><input id="vv" class="easyui-validatebox easyui-textbox"/></li>
					<li class="search_module_li"><label>年龄：</label><input id="vv" class="easyui-validatebox easyui-textbox"/></li>
					<li class="search_module_li"><label>生日：</label><input id="dd" type="text" class="easyui-datebox"></input></li>
					<li class="search_module_li"><label>地址：</label><input id="vv" class="easyui-validatebox  easyui-textbox"/></li>
					<li class="search_module_li"><label>是否删除：</label><input class="cc" name="dept" value="aa"></li>
					<li class="search_module_li"><label>公司性质：</label><input class="cc" name="dept" value="aa"></li>
					<li class="search_module_li"><label>创建时间：</label><input id="dd" type="text" class="easyui-datebox"></input></li>
				</ul>
			</form>
		</div>
		<div class="clear"></div>
		<div class="search_module_btn_div">
			<span><a id="search_btn" href="javascript:void(0);" class="easyui-linkbutton"><i class="fa fa-search btn_icon"></i> 查 询</a></span>
			<span><a id="clear_btn" href="javascript:void(0);" class="easyui-linkbutton"><i class="fa fa fa-circle-o btn_icon"></i> 清 空</a></span>
			<span><a id="import_btn" href="javascript:void(0);" class="easyui-linkbutton"><i class="fa fa-arrow-circle-o-up btn_icon"></i> 导 入</a></span>
			<span><a id="export_btn" href="javascript:void(0);" class="easyui-linkbutton"><i class="fa fa-arrow-circle-o-down btn_icon"></i> 导 出</a></span>
		</div> -->
		<!-- </div> -->
	</fieldset>
	<div style="width:100%;min-height:491px;">
		<table id="custom_dg"></table>
	</div>
	<div id="custom_window" class="easyui-window hzsInfo_remove">
		<div class="padding_top_15 clearfix">
			<form id="custom_oper_form" method="post">
				<ul class="search_module_ul">
					<li class="search_module_li"><label>姓名：</label>
						<input id="custom_name" name="name" class="easyui-textbox" data-options="required:true" />
					</li>
					<li class="search_module_li"><label>手机号码：</label>
						<input id="custom_phone" name="phone" class="easyui-textbox" data-options="validType:'phone'"/>
					</li>
					<li class="search_module_li"><label>电话号码：</label>
						<input id="custom_telNumb" name="telNumb" class="easyui-textbox" data-options="validType:'telNum'"/>
					</li>
					<li class="search_module_li"><label>公司名称：</label>
						<input id="custom_company" name="company" class="easyui-textbox"/>
					</li>
					<li class="search_module_li"><label>公司地址：</label>
						<input id="custom_address" name="address" class="easyui-textbox"/>（<span class="deco_a updateAddress">修改地址</span>）
					</li>
					<li class="search_module_li"><label>备注信息：</label>
						<input id="custom_mark" name="mark" class="easyui-textbox"/>
					</li>
					<li class="search_module_li"><label>删除标志：</label>
						<input id="custom_deleted" name="deleted" class="easyui-combobox" data-options="panelHeight: 'auto',editable: false,valueField: 'value',textField: 'text',value:'0',data: [{value:'0',text:'未删除'},{value:'1',text:'已删除'}]" />
					</li>
					<li class="search_module_li"><label>创建时间：</label>
						<input id="custom_createTime" name="createTimeStr" class="easyui-datebox" data-options="editable:false" />
					</li>
					<li class="search_module_li"><label>合作角色：</label>
						<input id="custom_hzs" name="isgys" class="easyui-combobox" data-options="panelHeight: 'auto',panelMaxHeight:160,editable: false,valueField: 'value',textField: 'text',value:'0',data: [{value:'0',text:'客户'},{value:'1',text:'供货商'}]" />
					</li>
					<li class="search_module_li"><label>性质类型：</label>
						<input id="custom_xzlx" name="iscompany" class="easyui-combobox" data-options="panelHeight: 'auto',panelMaxHeight:160,editable: false,valueField: 'value',textField: 'text',value:'0',data: [{value:'0',text:'个人'},{value:'1',text:'单位'}]" />
					</li>
				</ul>
			</form>
			<div class="clear"></div>
		</div>
		<div class="custom_search_module_btn_div search_module_btn_div">
			<span>
				<a id="custom_oper_btn" href="javascript:void(0);" class="easyui-linkbutton" options="">
					<i class="btn_icon fa fa-check"></i> 确 定
				</a>
			</span>
			<span>
				<a id="custom_close_btn" href="javascript:void(0);" class="easyui-linkbutton" options="">
					<i class="btn_icon fa fa-close"></i> 关 闭
				</a>
			</span>
		</div>
	</div>
	
	    <!-- 导入 -->
        <div id="custom_import_div" style="display:none;">
        	<div class="import_ps">
        		<p>注：上传文件前请先下载上传文件模板，如未下载 <a id="custom_download_template" href="javascript:void(0);" style="text-decoration: underline;color:blue;"> 立即下载上传模板</a></p>
        	</div>
        	<div class="importForm_contain">
	        	<form id="custom_importForm" name="importForm" enctype="multipart/form-data" method="post">
	        		<label>上传文件：</label>
	        		<input id="custom_importFile" name="importFile" class="easyui-filebox" style="width:200px" data-options="buttonText:'选择文件',required:true">
	        	</form>
	        	<div class="custom_show_process_div show_process_div">
		        	<div><span id="custom_upload_tips">正在上传...</span>，请勿刷新和关闭！</div>
		        	<div id="custom_upload_process" class="easyui-progressbar" style="width:260px;"></div>
		        </div>
	        </div>
        	<div class="custom_import_module_btn_div import_module_btn_div">
        		<span id="custom_import_result_tips import_result_tips"></span>
				<span><a id="custom_import_num_oper_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-check"></i> 确 定</a></span>
				<span><a id="custom_import_num_close_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-close"></i> 关 闭</a></span>
			</div>
        </div>
        
        <!-- 导出 -->
        <div id="custom_choose_num" style="display:none;">
        	<div class="choose_module_content">
        		<label>导出起始行：</label><input id="custom_exportStartNum" name="exportStartNum" class="easyui-textbox" data-options="validType:'moreThanNumber[0]',width:135,required:true"/><br/>
        		<label>导出结束行：</label><input id="custom_exportEndNum" name="exportEndNum" class="easyui-textbox" data-options="validType:'number',width:135"/>
        	</div>
			<div class="choose_module_btn_div">
				<span><a id="custom_choose_num_oper_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-check"></i> 确 定</a></span>
				<span><a id="custom_choose_num_close_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-close"></i> 关 闭</a></span>
			</div>
		</div>
		
		
		<!-- 隐藏显示列 -->
        <div id="custom_show_hide_column" class="easyui-dialog hzsInfo_remove" data-options="resizable : false,width : 290,collapsible : false,minimizable : false,maximizable:false,height : 250,modal : true,cache : false,title : '勾选显示的列',closed: true">
        	<div class="custom_all_columns columns_style">

        	</div>
			<div id="hideShowColumnBtn" class="dialog-button">
		     	<a name="checkAll" href="javascript:void(0)" data-modul="custom_" onclick="checkAll(this)" class="easyui-linkbutton"><i class="btn_icon fa fa-check-square-o"></i> 全选</a>
		     	<a name="clearAllChecked" href="javascript:void(0)" data-modul="custom_" onclick="clearAllChecked(this)" class="easyui-linkbutton"><i class="btn_icon fa fa-square-o"></i> 清空</a> 
		     	<a name="sureChecked" href="javascript:void(0)" data-modul="custom_" onclick="settingShowHideColumn(this)" class="easyui-linkbutton"><i class="btn_icon fa fa-check"></i> 确 定</a> 
		     	<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="custom_" onclick="javascript:$('#custom_show_hide_column').dialog('close')"><i class="btn_icon fa fa-close"></i> 取消</a>
			</div>
		</div>
		
        <!-- datagird - toolbar -->
        <div id="custom_toolbar">
			<a id="custom_dg_insert_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-plus btn_icon"></i> 新 增</a>
			<a id="custom_dg_update_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-pencil btn_icon"></i> 编 辑</a>
			<a id="custom_dg_delete_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-minus btn_icon"></i> 删 除</a>
			<!-- <a id="custom_dg_import_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-arrow-circle-o-up btn_icon"></i> 导 入</a> -->
			<a id="custom_dg_export_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-arrow-circle-o-down btn_icon"></i> 导 出</a>
			<a id="custom_dg_show_hide_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-server btn_icon"></i> 显示隐藏列</a>
		</div>
	
	<script type="text/javascript" src="${ctx}/resource/js/info-manage.js"></script>
</div>