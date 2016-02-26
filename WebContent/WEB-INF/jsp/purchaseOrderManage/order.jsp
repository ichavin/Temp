<!-- 订单管理 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="main-contain order">
	<fieldset class="query_div">
		<legend> 查询条件 </legend>
	</fieldset>
	<div id="order_content" class="_content" style="">    
		<table id="order_dg"></table>
	</div>
	<div id="order_window" class="easyui-window order_remove">
		<div class="padding_top_15 clearfix">
			<form id="order_oper_form" method="post">
				<ul class="search_module_ul">
					<li class="search_module_li"><label>客户名称：</label>
						<input id="order_customName" name="customId" class="easyui-combobox" data-options="width:173,panelWidth:170,required:true,panelHeight: 'auto',panelMaxHeight:160,editable: true"/>
					</li>
					<li class="search_module_li"><label>产品名称：</label>
						<input id="order_commodityName" name="commodityId" class="easyui-combobox" data-options="width:173,panelWidth:170,required:true,panelHeight: 'auto',panelMaxHeight:160,editable: true"/>
					</li>
					<li class="search_module_li"><label>单价：</label>
						<input id="order_univalence" name="univalence" class="easyui-textbox" data-options="validType:'number',onChange:function(newValue, oldValue){
							if($(this).textbox('isValid')){
								if($('#order_amount').textbox('isValid') && $('#order_amount').textbox('getValue') != ''){
									$('#order_totlePrice').textbox('setValue',Number.parseInt($(this).textbox('getValue')) * Number.parseInt($('#order_amount').textbox('getValue'))); 
								}
							}
						}"/>
					</li>
					<li class="search_module_li"><label>单组数量：</label>
						<input id="order_singleGroupNum" name="singleGroupNum" class="easyui-textbox" data-options="validType:'number',onChange:function(newValue, oldValue){
							if($(this).textbox('isValid')){
								if($('#order_groupNum').textbox('isValid') && $('#order_groupNum').textbox('getValue') != ''){
									$('#order_amount').textbox('setValue',Number.parseInt($(this).textbox('getValue')) * Number.parseInt($('#order_groupNum').textbox('getValue'))); 
								}
							}
						}"/>
					</li>
					<li class="search_module_li"><label>组数：</label>
						<input id="order_groupNum" name="groupNum" class="easyui-textbox" data-options="validType:'number',onChange:function(newValue, oldValue){
							if($(this).textbox('isValid')){
								if($('#order_singleGroupNum').textbox('isValid') && $('#order_singleGroupNum').textbox('getValue') != ''){
									$('#order_amount').textbox('setValue',Number.parseInt($(this).textbox('getValue')) * Number.parseInt($('#order_singleGroupNum').textbox('getValue'))); 
								}
							}
						}"/>
					</li>
					<li class="search_module_li"><label>交易总数量：</label>
						<input id="order_amount" name="amount" class="easyui-textbox" data-options="validType:'number',onChange:function(newValue, oldValue){
							if($(this).textbox('isValid')){
								if($('#order_univalence').textbox('isValid') && $('#order_univalence').textbox('getValue') != ''){
									$('#order_totlePrice').textbox('setValue',Number.parseInt($(this).textbox('getValue')) * Number.parseInt($('#order_univalence').textbox('getValue'))); 
								}
							}
						}"/>
					</li>
					<li class="search_module_li"><label>总价：</label>
						<input id="order_totlePrice" name="totlePrice" class="easyui-textbox" data-options="validType:'number'" />
					</li>
					<li class="search_module_li"><label>创建时间：</label>
						<input id="order_createTime" name="createTimeStr" class="easyui-datetimebox" data-options="editable: false" />
					</li>
					<li class="search_module_li"><label>交易时间：</label>
						<input id="order_tradeTime" name="tradeTimeStr" class="easyui-datetimebox" data-options="editable: false" />
					</li>
					<li class="search_module_li"><label>备注：</label>
						<input id="order_remark" name="remark" class="easyui-textbox"/>
					</li>
					<li class="search_module_li"><label>交易状态：</label>
						<input id="order_isTradeSuccess" name="isTradeSuccess" class="easyui-combobox" data-options="panelHeight: 'auto',editable: false,valueField: 'value',textField: 'text',value:'0',data: [{value:'0',text:'未交易成功'},{value:'1',text:'交易成功'}]" />
					</li>
					<li class="search_module_li"><label>删除标志：</label>
						<input id="order_deleted" name="deleted" class="easyui-combobox" data-options="panelHeight: 'auto',editable: false,valueField: 'value',textField: 'text',value:'0',data: [{value:'0',text:'未删除'},{value:'1',text:'已删除'}]" />
					</li>
				</ul>
			</form>
			<div class="clear"></div>
		</div>
		<div class="order_search_module_btn_div search_module_btn_div">
			<span>
				<a id="order_oper_btn" href="javascript:void(0);" class="easyui-linkbutton" options="">
					<i class="btn_icon fa fa-check"></i> 确 定
				</a>
			</span>
			<span>
				<a id="order_close_btn" href="javascript:void(0);" class="easyui-linkbutton" options="">
					<i class="btn_icon fa fa-close"></i> 关 闭
				</a>
			</span>
		</div>
	</div>
	
	    <!-- 导入 -->
        <div id="order_import_div" style="display:none;">
        	<div class="import_ps">
        		<p>注：上传文件前请先下载上传文件模板，如未下载 <a id="order_download_template" href="javascript:void(0);" style="text-decoration: underline;color:blue;"> 立即下载上传模板</a></p>
        	</div>
        	<div class="importForm_contain">
	        	<form id="order_importForm" name="importForm" enctype="multipart/form-data" method="post">
	        		<label>上传文件：</label>
	        		<input id="order_importFile" name="importFile" class="easyui-filebox" style="width:200px" data-options="buttonText:'选择文件',required:true">
	        	</form>
	        	<div class="order_show_process_div show_process_div">
		        	<div><span id="order_upload_tips">正在上传...</span>，请勿刷新和关闭！</div>
		        	<div id="order_upload_process" class="easyui-progressbar" style="width:260px;"></div>
		        </div>
	        </div>
        	<div class="order_import_module_btn_div import_module_btn_div">
        		<span id="order_import_result_tips import_result_tips"></span>
				<span><a id="order_import_num_oper_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-check"></i> 确 定</a></span>
				<span><a id="order_import_num_close_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-close"></i> 关 闭</a></span>
			</div>
        </div>
        
        <!-- 导出 -->
        <div id="order_choose_num" style="display:none;">
        	<div class="choose_module_content">
        		<label>导出起始行：</label><input id="order_exportStartNum" name="exportStartNum" class="easyui-textbox" data-options="validType:'moreThanNumber[0]',width:135,required:true"/><br/>
        		<label>导出结束行：</label><input id="order_exportEndNum" name="exportEndNum" class="easyui-textbox" data-options="validType:'number',width:135"/>
        	</div>
			<div class="choose_module_btn_div">
				<span><a id="order_choose_num_oper_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-check"></i> 确 定</a></span>
				<span><a id="order_choose_num_close_btn" href="javascript:void(0);" class="easyui-linkbutton" ><i class="btn_icon fa fa-close"></i> 关 闭</a></span>
			</div>
		</div>
		
		
		<!-- 隐藏显示列 -->
        <div id="order_show_hide_column" class="easyui-dialog order_remove" data-options="resizable : false,width : 290,collapsible : false,minimizable : false,maximizable:false,height : 250,modal : true,cache : false,title : '勾选显示的列',closed: true">
        	<div class="order_all_columns columns_style">

        	</div>
			<div id="hideShowColumnBtn" class="dialog-button">
		     	<a name="checkAll" href="javascript:void(0)" data-modul="order_" onclick="checkAll(this)" class="easyui-linkbutton"><i class="btn_icon fa fa-check-square-o"></i> 全选</a>
		     	<a name="clearAllChecked" href="javascript:void(0)" data-modul="order_" onclick="clearAllChecked(this)" class="easyui-linkbutton"><i class="btn_icon fa fa-square-o"></i> 清空</a> 
		     	<a name="sureChecked" href="javascript:void(0)" data-modul="order_" onclick="settingShowHideColumn(this)" class="easyui-linkbutton"><i class="btn_icon fa fa-check"></i> 确 定</a> 
		     	<a href="javascript:void(0)" class="easyui-linkbutton" data-modul="order_" onclick="javascript:$('#order_show_hide_column').dialog('close')"><i class="btn_icon fa fa-close"></i> 取消</a>
			</div>
		</div>
		
		<!-- 查看 订单样品图片 -->
		<div id="order_show_img" class="easyui-dialog order_remove" data-options="resizable : false,width : 450,collapsible : false,minimizable : false,maximizable:false,height : 300,modal : true,cache : false,title : '样品图片',closed: true">
			<div style="width:100%;height:100%;position:relative">
				<ul class="order_img_ul">
					<%-- <li class="showActive"><img src="${ctx}/resource/images/commodity/消防喷头.jpg"/></li>
					<li><img src="${ctx}/resource/images/commodity/大阪钢.jpg"/></li>
					<li><img src="${ctx}/resource/images/commodity/轴承.jpg"/></li> --%>
				</ul>
				<span class="img_text">第<b class="order_img_text_currentNum">0</b>张，共<b class="order_img_text_totleNum">0</b>张</span>
				<span class="span_left change_span" data-direction='left'>&lt;</span>
				<span class="span_right change_span" data-direction='right'>&gt;</span>
			</div>
			<div class="dialog-button">
				<a href="javascript:void(0)" data-modul="order_" class="easyui-linkbutton" onclick="order_insert_img()"><i class="btn_icon fa fa-plus"></i> 新增图片</a>
				<a href="javascript:void(0)" data-modul="order_" class="easyui-linkbutton" onclick="order_delete_img()"><i class="btn_icon fa fa-minus"></i> 删除图片</a>
		     	<a href="javascript:void(0)" data-modul="order_" class="easyui-linkbutton" onclick="javascript:$('#order_show_img').dialog('close')"><i class="btn_icon fa fa-close"></i> 关闭</a>
			</div>
		</div>
		<div id="order_insert_img_dialog" class="easyui-dialog" data-options="resizable : false,width : 250,collapsible : false,minimizable : false,maximizable:false,height : 120,modal : true,cache : false,title : '新增样品图片',closed: true">
			<div style="margin:10px;">
				<form id="order_insert_img_form">
					<input style="display:none;" name="orderId" id="order_insert_img_id"/>
					<div><input id="order_insert_img_file" name="importFile" class="easyui-filebox" style="width:206px" data-options="buttonText:'选择图片',required:true,validType:'img'"></div>
				</form>
			</div>
			<div class="dialog-button">
		     	<a href="javascript:void(0)" data-modul="order_" class="easyui-linkbutton" onclick="order_insert_img_sure(this)" ><i class="btn_icon fa fa-check"></i> 确 定</a> 
		     	<a href="javascript:void(0)" data-modul="order_" class="easyui-linkbutton" onclick="javascript:$('#order_insert_img_dialog').dialog('close')"><i class="btn_icon fa fa-close"></i> 取消</a>
			</div>
		</div>
        <!-- datagird - toolbar -->
        <div id="order_toolbar">
			<a id="order_dg_insert_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-plus btn_icon"></i> 新 增</a>
			<a id="order_dg_update_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-pencil btn_icon"></i> 编 辑</a>
			<a id="order_dg_delete_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-minus btn_icon"></i> 删 除</a>
			<!-- <a id="order_dg_import_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-arrow-circle-o-up btn_icon"></i> 导 入</a> -->
			<a id="order_dg_export_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-arrow-circle-o-down btn_icon"></i> 导 出</a>
			<a id="order_dg_show_hide_btn" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-server btn_icon"></i> 显示隐藏列</a>
			<a id="order_dg_showImg" href="#" class="easyui-linkbutton" data-options="plain:true"><i class="fa fa-image btn_icon"></i> 查看样品图片</a>
		</div>
	
	<script type="text/javascript" src="${ctx}/resource/js/order.js"></script>
</div>