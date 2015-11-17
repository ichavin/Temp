<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="application" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script> var ctx = "${ctx}"; </script>
<!--[if IE]>
<script src=”http://html5shiv.googlecode.com/svn/trunk/html5.js”></script>
< ![endif]-->
<link type="text/css" rel="stylesheet" href="${ctx}/resource/css/font-awesome.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/resource/jquery-easyui-1.4.3/themes/default/easyui.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/resource/jquery-easyui-1.4.3/themes/icon.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/resource/css/index.css"/>
<script type="text/javascript" src="${ctx}/resource/jquery-easyui-1.4.3/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script>
 var ctx = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}/resource/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/common.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/index.js"></script>
<script type="text/javascript"></script>
<!--[if lte IE 8]>
<script src="${contextPath}/resource/js/html5shiv.js"></script>
<script src="${contextPath}/resource/js/respond.js"></script>
<![endif]-->

</head>
	<body class="easyui-layout">
		<!-- north start -->
        <div data-options="region:'north',border:false" style="height:40px;">
        	<div class="left logo_contain">
        		<p class="logo"><a href="javascript:void(0);"><img alt='logo' src='${ctx}/resource/images/logo.jpg' width='150' height='40'/></a></p>
        	</div>
        	<div class="left mid">
        	</div>
        	<div class="right user_info">
        		<ul>
        			<li>深圳市 多云转晴 23-29℃</li>
        			<li>2015年11月15日 星期日 18:41:40</li>
        			<li class="user">
        				欢迎您，管理员     <form style="display:none;" action="${ctx}/login/logout"></form><span class='logout'><i class="font-icon-share userpwdicon"></i> 退出</span>
        			</li>
        		</ul>
        	</div>
        </div>
        <!-- north end -->
        
        
        
        <!-- west nav start -->
	    <div data-options="region:'west',split:true,title:'导航菜单'" style="width:15%;min-width:200px;">
		    <ul id="menuTree" class="easyui-tree"></ul>
	    </div>
	    <!-- west nav end -->
	    
	    
	    <!-- center start -->
	    <div data-options="region:'center'" style="background-color: #e9e9e9;">
	       <div id="mainTab" class="easyui-tabs" data-options="fit:true,border:false">   
			    <div title="首  页" data-options="closable:false">
			                
					
			    </div>
			    <div title="About" style="padding:10px">
					<p style="font-size:14px">jQuery EasyUI framework helps you build your web pages easily.</p>
					<ul>
						<li>easyui is a collection of user-interface plugin based on jQuery.</li>
						<li>easyui provides essential functionality for building modem, interactive, javascript applications.</li>
						<li>using easyui you don't need to write many javascript code, you usually defines user-interface by writing some HTML markup.</li>
						<li>complete framework for HTML5 web page.</li>
						<li>easyui save your time and scales while developing your products.</li>
						<li>easyui is very easy but powerful.</li>
					</ul>
				</div>
				<div title="My Documents" style="padding:10px">
					<ul class="easyui-tree" data-options=""></ul>
				</div>
				<div title="Help" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
					This is the help content.
				</div>
			</div> 
	    </div>
	    <!-- center end -->
	    
	    
	    <!-- <div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;"></div> -->
	    
	    
	    <!-- south start -->
	    <div data-options="region:'south',border:false" style="height:50px;background-color: #e9e9e9">
	       <div class="footer">
	           <div class="left">
	               <ul class="footer_ul">
	                   <li><span>© 2015 Chavin, Inc. All rights reserved.</span></li>
	                   <li><span class="font-icon-phone"> 电话热线：400-800-8888</span></li>
	               </ul>
	           </div>
	           <div class="left">
                   <ul class="footer_ul">
                       <li><span class="font-icon-comment"> 微信公众号：chavin (扫一扫右侧二维码)</span></li>
                       <li><span class="font-icon-envelope"> Email: 1020185741@qq.com</span></li>
                   </ul>
               </div>
	           <div class="left">
	               <div class="wximgdiv">
	                   <img alt="微信二维码" id="weixinImg" src="${ctx}/resource/images/weixin.jpg" width="40">
	               </div>
	           </div>
	       </div>
	    </div>
	    <!-- south end -->
	    
	    <!-- 二维码 -->
	    <div id="largerImg">
            <img alt="微信二维码" id="largerweixinImg" src="${ctx}/resource/images/weixin.jpg" width="160">
        </div>

	 	<!-- menu -->
        <div id="tab-menu" class="easyui-menu" data-options="onClick:menuHandler">
	         <div id="tabupdate">刷新</div>
	         <div class="menu-sep"></div>
	         <div id="tabclose" data-options="iconCls:'icon-close'">关闭</div>
	         <div id="tabcloseall">全部关闭</div>
	         <div class="menu-sep"></div>
	         <div id="tabcloseother">关闭其他</div>
	         <div id="tabcloseleft">关闭左侧选项卡</div>
	         <div id="tabcloseright">关闭右侧选项卡</div>
		</div>
        
        
	</body>
</html>
