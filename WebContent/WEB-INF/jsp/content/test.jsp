<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div>
	hello
</div>
<div class="login_m">
    <div class="login_boder">
        <div class="login_padding">
            <h2>&nbsp;</h2>
            <label class="po_relative">
                <input type="text" autofocus="autofocus" id="userName" name="userName" class="txt_input txt_input1" placeholder="请输入用户名">
                <i class="font-icon-user userpwdicon"></i>
                <span id="usererrorinfo"></span>
            </label>
            <label class="po_relative">
                <input type="password" name="password" id="password" class="txt_input txt_input1" placeholder="******" >
                <i class="font-icon-key userpwdicon"></i>
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