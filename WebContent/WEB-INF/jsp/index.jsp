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
<link type="text/css" rel="stylesheet" href="${ctx}/resource/css/custom-easyui.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/resource/jquery-easyui-1.4.3/themes/icon.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/resource/css/index.css"/>
<script type="text/javascript" src="${ctx}/resource/jquery-easyui-1.4.3/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/showServerTime.js"></script>
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
        <div data-options="region:'north',border:false" style="height:40px;background-color: #e9e9e9">
        	<div class="left logo_contain">
        		<p class="logo"><a href="javascript:void(0);"><img alt='logo' src='${ctx}/resource/images/logo0.png' width='105' height='40'/></a></p>
        	</div>
        	<div class="left mid">
        	</div>
        	<div class="right user_info">
        		<ul>
        			<li class="weather_li"><span id="city_weather_info"></span> <img id="weather_img" alt="天气图片" src="/Temp/resource/images/weather-day/undefined.png" height="35" /></li></li>
        			<li>服务器时间 <i class="fa fa-clock-o"></i>：<span id="server_time"></span></li>
        			<li class="user">
        				欢迎您，<a id="userName" href="javascript:void(0);">${USER.realName }</a>     <form style="display:none;" action="${ctx}/login/logout"></form><span class='logout'><i class="fa fa-sign-out"></i> 退出</span>
        			</li>
        		</ul>
        	</div>
        </div>
        <!-- north end -->
        
        
        
        <!-- west nav start -->
	    <div data-options="region:'west',split:true,title:' '" style="width:15%;min-width:200px;">
		    <div id="aa" class="easyui-accordion" data-options="fit:true" style="width:300px;height:200px;">   
			    <div title=" 订单管理</li>" data-options="iconCls:'icon-documents-line',selected:true" style="overflow:auto;padding:10px;">   
			        <ul id="menuTree" class="easyui-tree"></ul> 
			    </div>   
			    <div title=" 商品管理" data-options="iconCls:'icon-kongxiangzi'" style="padding:10px;">   
			        content2    
			    </div>   
			    <div title=" 系统管理" data-options="iconCls:'icon-shezhi'" style="padding:10px;">  
			        content3    
			    </div>
			</div>  
	    </div>
	    <!-- west nav end -->
	    
	    
	    <!-- center start -->
	    <div data-options="region:'center'" style="background-color: #e9e9e9;">
	       <div id="mainTab" class="easyui-tabs" data-options="fit:true,border:false">   
			    <div title="首  页" data-options="closable:false,iconCls:'icon-home'">
					
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
        <div id="tab-menu" class="easyui-menu">
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
