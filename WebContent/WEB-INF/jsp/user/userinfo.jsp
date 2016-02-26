<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link type="text/css" rel="stylesheet" href="${ctx}/resource/css/user-info.css"/>
<div class="main-contain">
	<h1 class="user-info-title"><i class="fa fa-minus"></i> 基本资料</h1>
	<input class="none" id="user_id" value="${user.id }"/>
	<div class="content clear">
		<ul class="clear">
			<li><label>用户名：</label><span>${user.loginName }</span></li>
			<!-- <li><label>密码：</label><span class="deco_a">修改密码</span></li> -->
			<li><label>姓名：</label><span>${user.realName }</span></li>
			<li><label>性别：</label><span>${user.sex == 1 ? '男':'女' }</span></li>
			<%-- <li><label>地址：</label><span id="address">${user.address }</span>（<span class="deco_a updateAddress">修改地址</span>）</li> --%>
			<li><label>邮箱：</label><span>${user.email }</span>
			<!-- （<span class="deco_a">修改绑定</span>） -->
			</li>
			<li><label>密码等级：</label><span>安全</span></li>
			<li><label>手机号码：</label><span>18588266016</span></li>
			<li></li>
			<!-- <div class="btn_li">
				<a id="btn" href="#" style="width:80px;" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">修改</a>
				<a id="btn" href="#" style="width:80px;" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
				<a id="btn" href="#" style="width:80px;" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">取消</a>
			</div>	 -->
		</ul>
	</div>
	<div class="line"></div>
	<h1 class="user-info-title"><i class="fa fa-navicon"></i> 详细资料</h1>
	<div class="content">
		<ul class="clear">
			<li><label>身份证号码：</label><span>${user.idCard }</span></li>
			<li><label>出生日期：</label><span>${user.birthday }</span></li>
			<li><label>毕业学校：</label><span>${user.graduateSchool }</span></li>
			<li></li>
			<li></li>
			<!-- <li class="btn_li">
				<a id="btn" href="#" style="width:80px;" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">修改</a>
				<a id="btn" href="#" style="width:80px;" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
				<a id="btn" href="#" style="width:80px;" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">取消</a>
			</li> -->								
		</ul>
	</div>

	<script type="text/javascript" src="${ctx}/resource/js/userinfo.js"></script>
</div>