 $('#registerForm').submit(function(){
                                      	$(this).ajaxSubmit({
                    	                	url: "/WeChat3Platform/loginController.do?checkregister", 
                                    		dataType:"json",
                    	                	success: function(data){     			
                                                if (data.success){     
                  	                              $.messager.alert("提醒","注册成功");               		        
                                                }else{                                	      
                                        	       $.messager.alert("提醒",data.msg);
                                                }
                    	                	}
                    	                    });         
                    	              return false;
});    
 $('#loginForm').submit(function(){
 	setCookie();
 	$(this).ajaxSubmit({
 		url: "/WeChat3Platform/loginController.do?checklogin", 
 		dataType:"json",
 		success: function(data){
 			 if (data.success){             	           
             	 window.location.href="/WeChat3Platform/loginController.do?login";               	
             }else{
            	 $.messager.alert("提醒",data.msg);
              }
 		}
 	});         
 	return false;
 });    
$(document).ready(function() {
	getCookie();
	
	
	$('#registerForm').validate({
		rules:{
			 username:{
				 required:true
			 },
			 password:{
				 required:true
			 },
			 repass:{
				 required:true,
				 equalTo:"#page3_password1"
			 },
			email:{
				required:true,
				email:true
			}
		},
		messages:{
			 username:{
				 required:"请输入用户名"
			 },
			 password:{
				 required:"请输入密码"
			 },
			 repass:{
				 required:"请再次输入密码",
				 equalTo:"两次密码必须相同"
			 },
			email:{
				required:"请输入邮件",
				email:"请输入正确格式邮件"
			}
		}
	});
});
$("#on_off").button();
$('#randCodeImage').click(function(){
    reloadRandCodeImage();
});
/**
 * 刷新验证码
 */
function reloadRandCodeImage() {
    var date = new Date();
    var img = document.getElementById("randCodeImage");
    img.src='randCodeImage?a=' + date.getTime();
}
$('#on_off').click(function(){
	if ($('#on_off').val() == '1') $('#on_off').val('0');
	else $('#on_off').val('1');
});
//设置cookie
function setCookie()
{    alert($('#on_off').val());
	if ($('#on_off').val() == '1') {
		$("input[iscookie='true']").each(function() {
			/*创建一个持久并带有效路径的cookie：

        $.cookie(‘cookieName’,'cookieValue’，｛expires：7，path：’/'｝);

         注：如果不设置有效路径，在默认情况下，只能在cookie设置当前页面读取该cookie，cookie的路径用于设置能够读取cookie的顶级目录。
			 * */
			$.cookie(this.name, $("#"+this.name).val(), "/",24);
			$.cookie("COOKIE_NAME","true", "/",24);
		});
	} else {
		$("input[iscookie='true']").each(function() {
			$.cookie(this.name,null);
			$.cookie("COOKIE_NAME",null);
		});
	}
}
//读取cookie
function getCookie()
{
	var COOKIE_NAME=$.cookie("COOKIE_NAME");
	if (COOKIE_NAME !=null) {
		$("input[iscookie='true']").each(function() {
			$($("#"+this.name).val( $.cookie(this.name)));
            if("admin" == $.cookie(this.name)) {
                $("#randCode").focus();
            } else {
                $("#password").val("");
                $("#password").focus();
            }
        });
		$("#on_off").attr("checked", true);
		$("#on_off").val("1");
	} 
	else
	{
		$("#on_off").attr("checked", false);
		$("#on_off").val("0");
        $("#randCode").focus();
	}
}