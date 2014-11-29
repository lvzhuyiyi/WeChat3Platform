<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page  isELIgnored="false"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>微剑--你犀利的选择</title>
<link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/demo/demo.css">
    <script type="text/javascript" src="/WeChat3Platform/plug-in/jquery-easyui-1.4/jquery.min.js"></script>
    
    <script type="text/javascript" src="/WeChat3Platform/plug-in/jquery-easyui-1.4/jquery.easyui.min.js"></script>
</head>
<body style="background-image:url('/WeChat3Platform/image/bg1.jpg');">
                  <br><br><br><br><br><br>
                  <h2 style="color:blue;height:100px;width:100%;text-align:center;font-size:90px;font-family:SimSun">微剑---你犀利的选择</h2>                
                   <br><br><br><br><br><br>
                  <div style="margin-left:30%;align:center;">  
                      <a href="javascript:void(0)" class="easyui-linkbutton " onclick="$('#dlg1').dialog('open')" style="background:none;color:blue;height:60px;width:200px;"><h2 style="font-size:25px;font-family:SimSun">登录</h2></a>
                      <a href="javascript:void(0)" class="easyui-linkbutton " onclick="$('#dlg').dialog('open')" style="background:none;color:blue;height:60px;width:200px;"><h2 style="font-size:25px;font-family:SimSun">注册</h2></a>
                      <a href="javascript:void(0)" class="easyui-linkbutton " onclick="window.location.href='/WeChat3Platform/webpage/login/introduce.jsp'" style="background:none;color:blue;height:60px;width:200px;"><h2 style="font-size:25px;font-family:SimSun">功能介绍</h2></a>               
                  </div>
        
          <div id="dlg"  closed="true" class="easyui-dialog" title="注册" data-options="iconCls:'icon-save'" style="background-color:black;width:800px;height:400px;padding:10px">
          <div style="padding:10px 60px 20px 60px">
            <form id="ff" class="easyui-form"  method="post" >
             <table cellpadding="5">
                   <tr>
                       <td style="font-size:20px;color:blue">用户名:</td>
                        <td><input class="easyui-textbox" type="text" name="username" style="width:200px;height:40px" data-options="required:true"></input></td>
                   </tr>
                    <tr>
                    <td style="font-size:20px;color:blue">密码:</td>
                    <td><input  class="easyui-textbox" type="text" name="password" style="width:200px;height:40px" data-options="required:true"></input></td>
                    </tr>
                    <tr>
                       <td style="font-size:20px;color:blue">再次输入密码:</td>
                       <td><input  class="easyui-textbox" type="text" name="repass" style="width:200px;height:40px" data-options="required:true"></input></td>
                   </tr>
                    <tr>
                    <td style="font-size:20px;color:blue">邮箱:</td>
                    <td><input  class="easyui-textbox" type="email" name="email" style="width:200px;height:40px" data-options="required:true,validType:'email',invalidMessage:'请填写正确的邮件格式'"></input></td>
                    </tr>
            </table>
        </form>
        <h2 style="color:red" id="msg1"></h2>
        <div style="margin-left:20%;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:50px" onclick="submitForm()">提交</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:50px" onclick="clearForm()">重设</a>
        </div>
        </div>
    </div>
    
     <script>
        function submitForm(){
            $('#ff').form('submit',{
            	url:"/WeChat3Platform/loginController.do?checkregister",
                onSubmit:function(){
                    return $(this).form('enableValidation').form('validate');
                },
                success: function(data){
                	alert("/WeChat3Platform/loginController.do?checkregister");
                	alert(data);
               	 var data = eval('(' + data + ')');  // change the JSON string to javascript object
                    if (data.success){     
           	         alert("register success");
                   	 $.messager.progress('close');	// hide progress bar while submit successfully
           	         $('#dlg').dialog('close');
           	      $('#dlg1').dialog('open');
                   }else{
                   	    $("#msg").text(data.msg);
                    }
                   }
           });
           
        }
        function clearForm(){
            $('#ff').form('clear');
        }
    </script>
     <div id="dlg1"  closed="true" class="easyui-dialog" title="登录" data-options="iconCls:'icon-save'" style="background-color:black;width:600px;height:300px;padding:10px">
          <h2 style="color:red;width:100%;text-align:center" id="msg"></h2>
          <div style="padding:10px 60px 20px 60px">
            <form id="ff1" class="easyui-form" action="/loginController.do?login" method="post" data-options="novalidate:true">
             <table cellpadding="5">
                   <tr>
                       <td style="font-size:20px;;color:blue">用户名:</td>
                        <td><input  class="easyui-textbox" type="text" name="username" style="width:300px;height:40px" data-options="required:true,missingMessage:'用户名必须填写'"></input></td>
                   </tr>
                    <tr>
                    <td style="font-size:20px;color:blue">密码:</td>
                    <td><input   class="easyui-textbox" type="text" name="password" style="width:300px;height:40px" data-options="required:true,missingMessage:'密码必须填写'"></input></td>
                    </tr>                 
            </table>
           </form>
             <h2 style="color:red" id="msg"></h2>
            <div style="margin-left:20%;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:50px" onclick="submitForm1()">提交</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px;height:50px" onclick="clearForm1()">重设</a>
            
           </div>
        </div>
    </div>
   <script>
        function submitForm1(){
        	//$.messager.progress();
            $('#ff1').form('submit',{
            	url: "/WeChat3Platform/loginController.do?checklogin",
                onSubmit:function(){
                    return $(this).form('enableValidation').form('validate');
                },
                success: function(data){
                	//alert("loginController.do?checklogin");
                	//alert(data);
                	
                	 var data = eval('(' + data + ')');  // change the JSON string to javascript object
                     if (data.success){     
            	
                    	 $.messager.progress('close');	// hide progress bar while submit successfully
                    	 window.location.href="/WeChat3Platform/loginController.do?login";
                    	 /*
            	        //这样发送ajax请求 返回是整个页面的string 并不会跳转 ，不要发送ajax请求，用一般请求
                    	 $.ajax({
            	    	       url:"loginController.do?login",
            	              error:function(){
            	               	console.log("sb hahahaha");
            	            },
            	    	    success:function(data){
            	    	    	alert(data);
            	    		    $.messager.progress('close');
            	    		    window.location.href="webpage/"+data+".jsp";
            	    	    }
            	           });*/
                    }else{
                    	    $("#msg").text(data.msg);
                     }
                    }
            });
        }
        function clearForm1(){
            $('#ff').form('clear');
        }
    </script>
</body>
</html>