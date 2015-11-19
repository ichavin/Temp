<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="application" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--[if IE]>
<script src=”http://html5shiv.googlecode.com/svn/trunk/html5.js”></script>
< ![endif]-->
<link type="text/css" rel="stylesheet" href="${ctx}/resource/css/login.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/resource/css/font-awesome.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/resource/layer/skin/layer.css" />
<script type="text/javascript" src="${ctx}/resource/jquery-easyui-1.4.3/jquery.min.js"></script>
<script>
 var ctx = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}/resource/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/common.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/login.js"></script>
<!--[if lte IE 8]>
<script src="${contextPath}/resource/js/html5shiv.js"></script>
<script src="${contextPath}/resource/js/respond.js"></script>
<![endif]-->
</head>
<body class="login">
<div class="login_m">
    <div class="login_boder">
        <div class="login_padding">
            <h2>&nbsp;</h2>
            <label class="po_relative">
                <input type="text" autofocus="autofocus" id="userName" value="admin" name="userName" class="txt_input txt_input1" placeholder="请输入用户名">
                <i class="fa fa-user userpwdicon"></i>
                <span id="usererrorinfo"></span>
            </label>
            <label class="po_relative">
                <input type="password" name="password" id="password" value="123456" class="txt_input txt_input1" placeholder="******" >
                <i class="fa fa-keyboard-o userpwdicon"></i>
                <span id="pwderrorinfo"></span>
            </label>
            <div class="forgot">
                <%-- <p class="validcodeshow">
                    <img id="validCode" src="${ctx}/login/validCode" alt="验证码" width="80" height="30" class="left hand" />
                    <input type="text" id="validcodeipt" class="txt_input left" style="float: left;width: 80px;height: 28px;margin-left:5px;">
                </p> --%>            
                <a id="losePwd" href="javascript:void(0);" class="right">忘记密码?</a>
            </div>
            
            <div class="rem_sub">
                <div class="rem_sub_l">
                    <input type="checkbox" name="checkbox" id="save_me">
                    <label for="save_me" class="hand">自动登录</label>
                </div>
                <label>
                    <input type="submit" class="sub_button" id="loginbtn" value="登录" style="opacity: 0.7;">
                </label>
            </div>
        </div>
    </div><!--login_boder end-->
</div><!--login_m end-->
</body>
</html>
