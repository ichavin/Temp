<!-- 方案管理-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="main-contain scheme">
	<fieldset class="query_div">
		<legend> 查询条件 </legend>
	</fieldset>
	
	<div>
		<div id="scheme_contain">
			<div id="scheme_content" style="padding:10px;overflow-y:scroll;">    
		    	<ul id="scheme_ul">
		    		<!-- <li class="scheme_li">
		    			<span class="scheme_span_text">收到了加法</span>
		    		</li>
		    		<li class="scheme_li more_scheme">
		    			<span style="display:none;" class="scheme_span_text hello">新增方案</span>
		    		</li> -->
		    	</ul>
			</div>
			<div id="scheme_update" class="easyui-dialog scheme_remove" data-options="resizable:false,width:520,collapsible:false,minimizable:false,maximizable:false,height:280,modal:true,cache:false,title:'操作',closed:true">
				<div style="width:100%;height:100%;">
					<table id="scheme_meterial_dg"></table>
					<input id="scheme_update_hidden_id" style="display:none;"/>
				</div>
				<div id="hideShowColumnBtn" class="dialog-button">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="meterialManage" onclick="rename()"><i class="btn_icon fa fa-magic"></i> 重命名</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="meterialManage" onclick="deleted_current_scheme(this)" id="deleted_scheme"><i class="btn_icon fa fa-times-circle"></i> 删除此方案</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="meterialManage" onclick="append()"><i class="btn_icon fa fa-plus-circle"></i> 新增行</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="meterialManage" onclick="removeit()"><i class="btn_icon fa fa-minus-circle"></i> 移除行</a>
			     	<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="meterialManage_" onclick="getChanges()"><i class="btn_icon fa fa-check"></i> 保存</a>
			     	<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="meterialManage_" onclick="javascript:$('#scheme_update').dialog('close')"><i class="btn_icon fa fa-close"></i> 取消</a>
				</div>
			</div>
			
			<div id="scheme_insert" class="easyui-dialog scheme_remove" data-options="resizable:false,width:430,collapsible:false,minimizable:false,maximizable:false,height:280,modal:true,cache:false,title:'操作',closed:true">
				<div>
				<form id="scheme_insert_form">
					<div style="margin:3px 0px 3px 10px;">
						<label>方案名称：</label><input id="add_scheme_name" class="easyui-textbox" data-options="required:true" />
						<span class="scheme_insert_msg" style="vertical-align: middle;color:red;display:none;">该名称已存在！</span>
					</div>
					<table id="scheme_insert_table" cellspacing="1">
						<thead>
							<tr>
								<td></td>
								<td>材料</td>
								<td>单位</td>
								<td>数量</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<a href="javascript:void(0);" class="insert_tr_deleted"><i class="fa fa-trash-o" style="color:#49A4F0;font-size:15px;"></i></a>
								</td>
								<td>
									<input id="scheme_insert_meter_cob" class="easyui-combobox cob11 width110" data-options="panelHeight: 'auto',panelMaxHeight:160,required:true,editable:false,valueField:'value',textField:'text',url:'${ctx}/meterialManage/getMeterialSelectList',onSelect:insertChangeUnit" />
								</td>
								<td>
									<%-- <input id="scheme_insert_unit_cob" class="easyui-combobox cob21 width110" data-options="panelHeight:'auto',panelMaxHeight:160,required:true,editable:false,valueField:'value',textField:'text',url:'${ctx}/unit/getUnitSelectList'" /> --%>
									<input class="easyui-textbox tb11 width110" data-options="editable:false"/>
								</td>
								<td>
									<input class="easyui-textbox tb21 width110" data-options="required:true,validType:'number'"/> 
								</td>
							</tr>
						</tbody>
					</table>
					</form>
				</div>
				<div id="hideShowColumnBtn" class="dialog-button">
			     	<!-- <label>方案名称：</label><input id="add_scheme_name" class="easyui-textbox" data-options="required:true,width:120" /> -->
			     	<a id="seche_insert_add" href="javascript:void(0)" data-modul="meterialManage_" class="easyui-linkbutton"><i class="btn_icon fa fa-plus-square"></i> 新增</a>
			     	<a id="seche_insert_sure" href="javascript:void(0)" data-modul="meterialManage_" class="easyui-linkbutton"><i class="btn_icon fa fa-check"></i> 确 定</a> 
			     	<a href="javascript:void(0)" data-modul="meterialManage_" class="easyui-linkbutton" onclick="javascript:$('#scheme_insert').dialog('close')"><i class="btn_icon fa fa-close"></i> 取消</a>
				</div>
			</div>
		</div>
		<div id="scheme_rename_dialog" class="easyui-dialog" data-options="resizable:false,width:330,height:150,collapsible:false,minimizable:false,maximizable:false,modal:true,cache:false,title:'重命名',closed:true">
			<div style="padding:5px;">
				<label>旧名称：</label><span class="scheme_old_name"></span>
				<p><label>新名称：</label><input id="scheme_new_name" class="easyui-textbox" data-options="required:true,width:160"/><span id="rename_exist" class="rename_exist"></span></p>
			</div>
			<div class="dialog-button">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="meterialManage_" onclick="update_name()"><i class="btn_icon fa fa-check"></i> 确定</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" data-modul="meterialManage_" onclick="javascript:$('#scheme_rename_dialog').dialog('close')"><i class="btn_icon fa fa-close"></i> 取消</a>
			</div>
		</div>
	</div> 

	<script type="text/javascript" src="${ctx}/resource/js/scheme.js"></script>
</div>