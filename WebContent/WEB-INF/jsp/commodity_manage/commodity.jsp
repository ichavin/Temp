<!-- 资料管理-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="main-contain commodity">
	<fieldset class="query_div">
		<legend> 查询条件 </legend>
	</fieldset>
	
	<div>
		<div id="commodity_contain" class="_contain">
			<div id="commodity_content" class="_content" style="padding:10px;overflow-y:scroll;">    
		    	<ul id="commodity_ul">
		    	</ul>
			</div>
			<div id="commodity_update" class="easyui-dialog commodity_remove" data-options="resizable:false,width:980,collapsible:false,minimizable:false,maximizable:false,height:300,modal:true,cache:false,title:'操作',closed:true">
				<div style="width:25%;height:100%;border: 1px solid #95B8E7;border-right: 0px;box-sizing: border-box;height:100%;" class="left" >
					<img id="commodity_update_img" class="img">
				</div>
				<div style="width:50%;height:100%;" class="left">
					<table id="commodity_meterial_dg"></table>
					<input id="commodity_update_hidden_id" style="display:none;"/>
				</div>
				<div style="width:25%;border: 1px solid #95B8E7;border-left: 0px;box-sizing: border-box;height:100%;" class="left">
					<p class="commodity_update_p"><label>产品编号：</label><span id="commodity_update_number_id"></span></p>
					<p class="commodity_update_p"><label>产品重量：</label><span id="commodity_update_weight"></span></p>
					<p class="commodity_update_p"><label>产品尺寸：</label><span id="commodity_update_size"></span></p>
				</div>
				<div id="hideShowColumnBtn" class="dialog-button">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="commodity" onclick="commodity_change_img()"><i class="btn_icon fa fa-exchange"></i> 更换图片</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="commodity" onclick="commodity_change_scheme()"><i class="btn_icon fa fa-exchange"></i> 更换方案</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="commodity" onclick="commodity_rename()"><i class="btn_icon fa fa-magic"></i> 编辑产品信息</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="commodity" onclick="deleted_current_commodity(this)" id="deleted_commodity"><i class="btn_icon fa fa-times-circle"></i> 删除此产品</a>
			     	<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="commodity_" onclick="javascript:$('#commodity_update').dialog('close')"><i class="btn_icon fa fa-close"></i> 取消</a>
				</div>
			</div>
			
			<div id="commodity_insert_dialog" class="easyui-dialog commodity_remove commodity_remove" data-options="resizable:false,width:430,collapsible:false,minimizable:false,maximizable:false,height:321,modal:true,cache:false,title:'',closed:true">
				<div>
					<form id="commodity_insert_commodity_form">
						<div class="left">
							<div style="width:100%;height:225px;"><img id="commodity_insert_new_img" class="img" src="" alt="产品图片" width="100%" height="100%"/></div>
							<div><span><input id="commodity_add_img_file" name="importFile" class="easyui-filebox" style="width:206px" data-options="buttonText:'选择图片',validType:'img'"></span></div>
						</div>
						<div class="left" style="margin:20px 0px 0px 10px;">
							<p><label>产品名称：</label><input id="commodity_add_commodity_name" name="inserCommodityName" class="easyui-textbox width110" data-options="required:true"/></p>
							<p><span id="commodity_add_commodity_name_msg" style="display:none;"></span></p>
							<p><label>选择方案：</label><input id="commodity_add_scheme_cmb" name="insertSchemeId" class="easyui-combobox cob11 width110" data-options="panelHeight: 'auto',required:true,editable:false,valueField:'value',textField:'text',url:'${ctx}/scheme/getSchemeSelectList'"/></p>
							<p><label>产品编号：</label><input id="commodity_add_number_id" name="insertNumberId" class="easyui-textbox width110" data-options="required:true"/></p>
							<p><label>重量(kg)：</label><input id="commodity_add_weight" name="insertWeight" class="easyui-textbox width110" data-options="required:true,validType:'number'"/></p>
							<p><label>产品尺寸：</label><input id="commodity_add_size" name="insertSize" class="easyui-textbox width110" data-options="required:true"/></p>
							<p><span class="commodity_add_msg red"></span><p>
						</div>
					</form>
				</div>
				<div class="dialog-button">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="commodity_" onclick="commodity_add_commodity_sure()"><i class="btn_icon fa fa-check"></i> 确定</a>
				    <a href="javascript:void(0)" class="easyui-linkbutton" data-modul="commodity_" onclick="javascript:$('#commodity_insert_dialog').dialog('close')"><i class="btn_icon fa fa-close"></i> 取消</a>
				</div>
			</div>
		</div>
		<div id="commodity_rename_dialog" class="easyui-dialog commodity_remove" data-options="resizable:false,width:330,height:260,collapsible:false,minimizable:false,maximizable:false,modal:true,cache:false,title:'信息修改',closed:true">
			<div style="padding:5px;">
				<form id="commodity_updateInfo_form">
					<p><label>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</label><input id="commodity_new_name" class="easyui-textbox" data-options="required:true,width:160"/></p>
					<p><label>编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</label><input id="commodity_new_number_id" class="easyui-textbox" data-options="required:true,width:160"/></p>
					<p><label>重量(kg)：</label><input id="commodity_new_weight" class="easyui-textbox" data-options="required:true,width:160,validType:'number'"/></span></p>
					<p><label>尺&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;寸：</label><input id="commodity_new_size" class="easyui-textbox" data-options="required:true,width:160"/></p>
					<p><span class="commodity_updateInfo_msg red"></span></p>
				</form>
			</div>
			<div class="dialog-button">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="commodity_" onclick="commodity_update_name()"><i class="btn_icon fa fa-check"></i> 确定</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" data-modul="commodity_" onclick="javascript:$('#commodity_rename_dialog').dialog('close')"><i class="btn_icon fa fa-close"></i> 取消</a>
			</div>
		</div>
		<div id="commodity_change_img_dialog" class="easyui-dialog commodity_remove" data-options="resizable:false,width:220,height:321,collapsible:false,minimizable:false,maximizable:false,modal:true,cache:false,title:'更换产品图片',closed:true">
			<div>
				<form id="commodity_change_img_form">
					<div style="width:100%;height:225px;"><img id="commodity_new_img" class="img" src="${ctx}/resource/userImg/commodity/none.png" alt="产品图片" width="100%" height="100%"/></div>
					<input style="display:none;" name="commodityId" id = "commodity_change_img_id"/>
					<div><span><input id="commodity_change_img_file" name="importFile" class="easyui-filebox" style="width:206px" data-options="buttonText:'选择图片',required:true,validType:'img'"></span></div>
				</form>
			</div>
			<div class="dialog-button">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="commodity_" onclick="commodity_change_img_sure()"><i class="btn_icon fa fa-check"></i> 确定</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" data-modul="commodity_" onclick="javascript:$('#commodity_change_img_dialog').dialog('close')"><i class="btn_icon fa fa-close"></i> 取消</a>
			</div>
		</div>
		<div id="commodity_change_scheme_dialog" class="easyui-dialog commodity_remove" data-options="resizable:false,width:330,height:150,collapsible:false,minimizable:false,maximizable:false,modal:true,cache:false,title:'更换方案',closed:true">
			<div style="padding:5px;">
				<label>现方案：</label><span class="commodity_old_schem_name"></span>
				<p><label>新方案：</label><input id="commodity_new_scheme_cmb" class="easyui-combobox cob11 width110" data-options="width:173,panelWidth:170,required:true,panelHeight: 'auto',panelMaxHeight:120,editable:false,valueField:'value',textField:'text',url:'${ctx}/scheme/getSchemeSelectList'"/></p>
			</div>
			<div class="dialog-button">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="commodity_" onclick="commodity_change_scheme_sure()"><i class="btn_icon fa fa-check"></i> 确定</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" data-modul="commodity_" onclick="javascript:$('#commodity_change_scheme_dialog').dialog('close')"><i class="btn_icon fa fa-close"></i> 取消</a>
			</div>
		</div>
	</div> 

	<script type="text/javascript" src="${ctx}/resource/js/commodity.js"></script>
</div>