//form  userName  password  loginbtn  errorinfo
$(function() {
	/**
	 * 用户名验证
	 */
	$("#userName").change(function(){
		$("#usererrorinfo").text("");
		var userName = $("#userName").val().trim();
		if(isNullOrEmpty(userName)){
			return;
		}
		if (!isNullOrEmpty(userName) && (userName.length < 5 || userName.length > 20)) {
			$("#userName").focus();
			$("#usererrorinfo").text("用户名长度为5~20");
			return;
		}
		
		$.customAjax({
			url : ctx + "/login/getLoginCount",
			data : {
				"userName" : userName
			},
			dataType : "json",
			type : 'post',
			success : function(data){
				if(data >= 3){
					if($(".validcodeshow").length <= 0){
						html = '<p class="validcodeshow">'
							 +  '<img id="validCode" src="' + ctx + '/login/validCode" alt="验证码" width="80" height="30" class="left hand" />' 
							 +  '<input type="text" id="validcodeipt" class="txt_input left" style="float: left;width: 80px;height: 28px;margin-left:5px;">'
	                         +   '</p>';
							$("#losePwd").before(html);
					}
				}else if($(".validcodeshow").length > 0){
					$(".validcodeshow").remove();
				}
			}
	     });
	});
	
	$("#password").change(function(){
		$("#pwderrorinfo").text("");
		var userName = $("#userName").val().trim();
		var password = $("#password").val().trim();
		if(isNullOrEmpty(userName))
			return;
		if (isNullOrEmpty(password)) {
			$("#password").focus();
			$("#pwderrorinfo").text("密码不能为空");
			return;
		}
	});
	
	function trigger_login(event){
		switch(event.keyCode) {
		  case 13:
			  $("#loginbtn").trigger("click"); 
			  break;
		  }
	}
	
	$(window).keydown(trigger_login);

	/**
	 * 表单提交验证
	 */
	$("#loginbtn").click(function() {
		$("#usererrorinfo").text("");
		$("#pwderrorinfo").text("");
		var userName = $("#userName").val().trim();
		var password = $("#password").val().trim();
		if (isNullOrEmpty(userName)) {
			$("#userName").focus();
			$("#usererrorinfo").text("用户名不能为空");
			return;
		}
		if (userName.length < 5 || userName.length > 20) {
			$("#userName").focus();
			$("#usererrorinfo").text("用户名长度为5~20");
			return;
		}
		if (isNullOrEmpty(password)) {
			$("#password").focus();
			$("#pwderrorinfo").text("密码不能为空");
			return;
		}
		var validCode = "";
		if($("#validCode").length > 0){
			validCode = $("#validcodeipt").val().trim();
			if(isNullOrEmpty(validCode.trim()) || validCode.length != 4){
				$("#validcodeipt").focus();
				return;
			}
		}
		var autoLogin = $("#save_me").is(":checked");
		
		$.customAjax({
			isLoading : '正在登录...',
			url : ctx + "/login/login",
			data : {
				"userName" : userName,
				"password" : password,
				"validCode" : validCode,
				"autoLogin" : autoLogin
			},
			success : function(data) {
				if(data.code != "LOGIN_SUCCESS"){
					$(window).unbind('keydown');
					$.messager.alert('登录失败',data.detailInfo,'error',function(){
						$(window).bind('keydown',trigger_login);
					});
					if(data.object >= 3){
						if($(".validcodeshow").length <= 0){
							html = '<p class="validcodeshow">'
								 +  '<img id="validCode" src="' + ctx + '/login/validCode" alt="验证码" width="80" height="30" class="left hand" />' 
								 +  '<input type="text" id="validcodeipt" class="txt_input left" style="float: left;width: 80px;height: 28px;margin-left:5px;">'
		                         +   '</p>';
								$("#losePwd").before(html);
						}
					}else{
						$(".validcodeshow").remove();
					}
					$("#validCode").attr("src",$("#validCode").attr("src") + "?" + new Date().getTime());
				}else{
					window.location.href= ctx + "/home/index";
				}
			}
		});

	});
	
	$("body").on('click',"#validCode",function(){
		$(this).attr("src",$(this).attr("src") + "?" + new Date().getTime());
	});
	
});