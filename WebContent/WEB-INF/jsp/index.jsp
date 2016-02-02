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
<title>管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<link rel='icon' href='${ctx}/resource/images/little_icon.ico' type='image/x-ico' /> 
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
<script type="text/javascript" src="${ctx}/resource/js/jquery-form.js"></script>
<script type="text/javascript" src="${ctx}/resource/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/jquery-easyui-1.4.3/datagrid-scrollview.js"></script>
<script type="text/javascript" src="${ctx}/resource/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/showServerTime.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=vchCE9iRnO0TBjK1z8GVezwf"></script>
<script>
 var ctx = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}/resource/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/common.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/index.js"></script>
<%-- <script type="text/javascript" src="${ctx}/resource/js/info-manage.js"></script> --%>
<script type="text/javascript"></script>
<!--[if lte IE 8]>
<script src="${contextPath}/resource/js/html5shiv.js"></script>
<script src="${contextPath}/resource/js/respond.js"></script>
<![endif]-->

</head>
	<body class="easyui-layout">
		<!-- north start -->
        <div data-options="region:'north',border:false" style="height:40px;background-color: #e9e9e9">
        	<%-- <div class="left logo_contain">
        		<p class="logo"><a href="javascript:void(0);"><img alt='logo' src='${ctx}/resource/images/logo0.png' width='105' height='40'/></a></p>
        	</div> --%>
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
		    <div id="menu_accordion" class="easyui-accordion" data-options="fit:true,border:false" style="width:300px;height:200px;">   
			    <c:forEach items="${menuList}" var="item">
				    <div title="<i class='${item.iconClass}'></i> ${item.name }" class="menu_div">   
				        <div>
				        	<ul>
				        		<c:forEach items="${item.subList}" var="sub_menu">
				        			<li class="menu_li" data-url="${sub_menu.url }"><i class="${sub_menu.iconClass }"></i> ${sub_menu.name }</li>
				        		</c:forEach>
							</ul>
				        </div>
				    </div>   
			    </c:forEach>
			</div>  
	    </div>
	    <!-- west nav end -->
	    
	    
	    <!-- center start -->
	    <div data-options="region:'center'" style="background-color: #e9e9e9;width:100%;height:100%;">
	       <div id="mainTab" class="easyui-tabs" data-options="fit:true,
	       				border:false,
	       				cache:false,
	       				onClose:function(title,index){
	       					var url = $('#menu_accordion').find('.actived_menu').data('url');
	       					if(url != null && url != ''){
	       						var cla_prefix = url.substring(url.lastIndexOf('/') + 1) + '_remove';
		       					var cla = $('.' + cla_prefix).attr('class');
		       					if(cla != null && cla != ''){
		       						if(cla.indexOf('easyui-window') != -1 || cla.indexOf('easyui-dialog') != -1){
							    		$('.' + cla_prefix).window('destroy');
							    	}else{
							    		$('.' + cla_prefix).combobox('destroy');					    		
							    	}
		       					}
	       					}
	       					if($('#mainTab').tabs('tabs').length <= 1){
					    		$('#menu_accordion').find('.actived_menu').removeClass('actived_menu');
					    	}else{
					    		var clazz = title.substring(title.indexOf('class=') + 10,title.indexOf('></i>') - 1);
					    		$('#menu_accordion').find('i.' + clazz).parent().removeClass('actived_menu');
					    	}
	       				},
	       				onSelect:function(title,index){
	       					var clazz = title.substring(title.indexOf('class=') + 10,title.indexOf('></i>') - 1);
							$('#menu_accordion').find('.actived_menu').removeClass('actived_menu');
				    		$('#menu_accordion').find('i.' + clazz).parent().addClass('actived_menu');
	       				}">   
			    <div title='<i class="fa fa-home"></i> 首  页' data-options="closable:false">

			    </div>
			</div> 
	    </div>
	    <!-- center end -->
	    
	    
	    <!-- <div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;"></div> -->
	    
	    
	    <!-- south start -->
	    <div data-options="region:'south',border:false" style="height:30px;background-color: #e9e9e9">
	       <div class="footer clearfix">
	           <div class="left">
	               <ul class="footer_ul">
	                   <li><span>© 2015 Chavin, Inc. All rights reserved.</span><span class="font-icon-phone" style="margin-left:20px;"> 联系电话：400-800-8888</span></li>
	                   <!-- <li><span class="font-icon-phone"> 电话热线：400-800-8888</span></li> -->
	               </ul>
	           </div>
	           
	          <!--  <div class="left">
                   <ul class="footer_ul">
                       <li><span class="font-icon-phone"> 电话热线：400-800-8888</span></li>
                   </ul>
               </div> -->
	           
<%-- 	           <div class="left">
                   <ul class="footer_ul">
                       <li><span class="font-icon-comment"> 微信公众号：chavin (扫一扫右侧二维码)</span></li>
                       <li><span class="font-icon-envelope"> Email: 1020185741@qq.com</span></li>
                   </ul>
               </div>
	           <div class="left">
	               <div class="wximgdiv">
	                   <img alt="微信二维码" id="weixinImg" src="${ctx}/resource/images/weixin.jpg" width="40">
	               </div>
	           </div> --%>
	       </div>
	    </div>
	    <!-- south end -->
	    
	    <!-- 二维码 -->
	    <div id="largerImg">
            <img alt="微信二维码" id="largerweixinImg" src="${ctx}/resource/images/weixin.jpg" width="160">
        </div>

	 	<!-- tab-menu -->
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
        
        <div id="dialog" >
        	
        </div>
        
		<!-- 弹窗提示 -->
        <div id="dialog_alert"></div>
        
		<!-- 修改地址，百度地图 -->
        <div>
			<div id="baiduditu">
				<div id="contain_map"></div>
				<div class="contain_buttom hide">
					<div id="map_tips">（点击红色小图标滑动到目的地点）</div>
					<div id="ditu_search">
						搜索地址：<input id="ditu_search_ssdz" class="easyui-textbox" style="width:400px"> 
						<a id="ditu_search_btn" href="#" style="width:80px;" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
					</div>
					<div id="ditu_update">
						当前地址：<input id="ditu_update_sxdz" class="easyui-textbox" style="width:400px"> 
						<a id="ditu_update_btn" href="#" style="width:80px;" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确定</a>
					</div>
				</div>
			 </div>
		</div>
	</body>
</html>
