$(function(){
		
	$(".updateAddress").click(function(){
		$("#baiduditu").window({    
		    width:600,    
		    height:400,    
		    modal:true,
		    title:'选择地址',
		    minimizable:false,
		    maximizable:true,
		    collapsible:false,
		    closable:true,
		    draggable:true
		});
		$(".contain_buttom").show();
		$("#ditu_search_btn").bind('click', function(){
			$("#ditu_update_sxdz").textbox('setValue','');
			getValAndShow($("#ditu_search_ssdz").textbox('getValue'));
		});
		
		$("#ditu_update_btn").bind('click',function(){
			var address = $("#ditu_update_sxdz").textbox('getValue');
			if(address){
				$.customAjax({
					url: ctx + '/user/updateUserInfo',
					data: {
						'address': address,
						'id':$("#user_id").val()
					},
					dataType: 'text',
					success:function(data){
						if(data == 'success'){
							$("#address").text(address);
							$("#baiduditu").window('close');
						}else{
							layer.alert('修改失败',{
							    icon: 2,
							    title:'提示'
							});
						}
					}
				});
			}else{
				layer.alert('地址不能为空',{
				    icon: 2,
				    title:'提示'
				});
			}
		});
	
		getValAndShow($("#ditu_search_ssdz").textbox('getValue'));
	});
	
	
});