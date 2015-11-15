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
        <div data-options="region:'north',border:false" style="height:50px;background-color: #e9e9e9;"></div>
	    <div data-options="region:'west',split:true,title:'导航菜单'" style="width:15%;min-width:200px;background-color: #f9f9f9;">
		    <div class="easyui-accordion" data-options="fit:true">
		        <div title="About" data-options="iconCls:'icon-ok'" style="overflow:auto;padding:10px;">
		            <h3 style="color:#0099FF;">Accordion for jQuery</h3>
		            <p>Accordion is a part of easyui framework for jQuery. It lets you define your accordion component on web page more easily.</p>
		        </div>
		        <div title="Help" data-options="iconCls:'icon-help'" style="padding:10px;">
		            <p>The accordion allows you to provide multiple panels and display one or more at a time. Each panel has built-in support for expanding and collapsing. Clicking on a panel header to expand or collapse that panel body. The panel content can be loaded via ajax by specifying a 'href' property. Users can define a panel to be selected. If it is not specified, then the first panel is taken by default.</p>      
		        </div>
		        <div title="TreeMenu" data-options="iconCls:'icon-search'" style="padding:10px;">
		            <ul class="easyui-tree">
		                <li>
		                    <span>Foods</span>
		                    <ul>
		                        <li>
		                            <span>Fruits</span>
		                            <ul>
		                                <li>apple</li>
		                                <li>orange</li>
		                            </ul>
		                        </li>
		                        <li>
		                            <span>Vegetables</span>
		                            <ul>
		                                <li>tomato</li>
		                                <li>carrot</li>
		                                <li>cabbage</li>
		                                <li>potato</li>
		                                <li>lettuce</li>
		                            </ul>
		                        </li>
		                    </ul>
		                </li>
		            </ul>
		        </div>
		    </div>
	    </div>
	    <div data-options="region:'center'" style="background-color: #e9e9e9;">
	       <div id="mainTab" class="easyui-tabs" data-options="fit:true">   
			    <div title="首  页" >   
			        tab1    
			    </div>   
			    <div title="Tab2" data-options="closable:true">
			        tab2    
			    </div>   
			    <div title="Tab3" data-options="iconCls:'icon-reload',closable:true">   
			        tab3    
			    </div>   
			</div> 
	    </div>
	    <!-- <div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;"></div> -->
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
	                   <span id="txt">1</span>
	                   <img alt="微信二维码" id="weixinImg" src="${ctx}/resource/images/weixin.png" width="40">
	               </div>
	           </div>
	       </div>
	    </div>
	    <div id="largerImg">
            <img alt="微信二维码" id="largerweixinImg" src="${ctx}/resource/images/weixin.png" width="160">
        </div>
	</body>
</html>
