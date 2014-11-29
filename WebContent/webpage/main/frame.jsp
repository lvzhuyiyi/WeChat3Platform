<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/security.tld" prefix="sec"%><!-- 注意这两个taglib的使用-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户管理</title>
<link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/themes/sunny/easyui.css">
    <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/demo/demo.css">
    <script type="text/javascript" src="/WeChat3Platform/plug-in/jquery-easyui-1.4/jquery.min.js"></script>
    <script type="text/javascript" src="/WeChat3Platform/plug-in/jquery-easyui-1.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/WeChat3Platform/plug-in/json2.js"></script>
     <script type="text/javascript">
      
         $(function(){
        	 $('#tt').tabs({tabPosition:'left'})
        	 console.log("sb");
        	 
        	 });
           
         </script>
</head>
<body style="background-image:url('/WeChat3Platform/image/bg2.jpg');">
     
    <h2 style="color:blue;height:100px;width:100%;text-align:center;font-size:40px;font-family:SimSun">微剑---你犀利的选择</h2>                
     <h2 style="width:100%">用户管理     
     <span style="margin-left:85%">
     <a style="font-size:25px" href="/WeChat3Platform/jspController.do?frame"><sec:user/></a>
     <a style="font-size:15px;color:red" href="/WeChat3Platform/loginController.do?logout">退出</a>
     </span></h2>        
      
    <div id="tt" class="easyui-tabs" style="width:100%;height:600px">
        <div title="个人信息" style="padding:10px">
             <table id="dg"class="easyui-datagrid" style="width:800px;height:50px"
                         data-options="url:'/WeChat3Platform/userController.do?loadCurrentUserArray',fitColumns:true,singleSelect:true">
                   <thead>
                     <tr>
                        <th data-options="field:'username',width:100">用户名</th>
                        <th data-options="field:'email',width:100">电邮</th>                   
                     </tr>
                  </thead>
            </table>
            <br> <br> <br> <br>
             <div >
               
               <a href="javascript:void(0)" style="width:100px;height:40px" class="easyui-linkbutton" onclick="loadRemote()">加载个人信息</a>
                <a href="javascript:void(0)" style="width:100px;height:40px" class="easyui-linkbutton" onclick="subForm()">提交修改</a>
            </div>
            <div  class="easyui-panel" title="个人信息" style="width:800px">
            <div style="margin-left:20%;padding:10px 60px 20px 60px">
                 <form id="ff" method="post" action="/WeChat3Platform/userController.do?modifyUser">
                       <table cellpadding="5">
                            <tr>
                                <td>名字</td>
                                <td><input class="easyui-textbox" type="text" name="username" data-options="required:true"></input></td>
                            </tr>
                            <tr>
                                 <td>电邮</td>
                                   <td><input class="easyui-textbox" type="text" name="email" data-options="required:true,validType:'email'"></input></td>
                            </tr>               
                            <tr>
                                 <td>密码</td>
                                   <td><input class="easyui-textbox" type="text" name="password" data-options="required:true"></input></td>
                            </tr>     
                            <tr>
                                 <td>再次输入密码</td>
                                   <td><input class="easyui-textbox" type="text" name="repass" data-options="required:true"></input></td>
                            </tr>     
                       </table>
                  </form>
                  <h2 style="color:red" id="msg"></h2>
             </div>
            </div>
            <script>
                      
                       function loadRemote(){
                           $('#ff').form('load', '/WeChat3Platform/userController.do?loadCurrentUser');
                         }
                       function subForm(){
                           $('#ff').form('submit',{
                        	   success:function(data){
                        		   var s=eval(data);
                        	        $('#msg').text(s);
                        	        S('#dg').datagrid("load");
                        	    }
                           });
                       }
              </script>
        </div>
        <div title="我的公众号">
            <script type="text/javascript">
            var Types = [{ "value": "订阅号", "text": "订阅号" }, { "value": "服务号", "text": "服务号" }, { "value": "测试账号", "text": "测试账号" }];
            </script>
            <table id="dg1" style="margin-left:20%" class="easyui-datagrid" title="我的公众号" style="width:800px;height:50px"
                   data-options=" iconCls: 'icon-edit',
                                   singleSelect: true,
                                   toolbar: '#tb',                                          
                                   onClickRow: onClickRow,
                               url:'/WeChat3Platform/userController.do?loadWeixin',method:'get'">
                    <thead>
                       <tr>
                       <th data-options="field:'id',width:80">公众号编号</th>
                       <th data-options="field:'status',width:80">默认状态</th>
                        <th data-options="field:'name',width:80,editor:'text'">公众号名</th>
                         <th data-options="field:'weiXinNo',width:100,editor:'text'">微信号</th>
                         <th data-options="field:'type',width:100,editor:{type:'combobox',
                                                                          options:{
                                                                      valueField:'value',
                                                                      textField:'text',
                                                                       data:Types,
                                                                      required:true
                            }}">微信类型</th>
                         <th data-options="field:'desc',width:100,editor:'textarea'">描述</th>
                         <th data-options="field:'originId',width:80,align:'right',editor:'text'">原始id</th>
                         <th data-options="field:'appId',width:80,align:'right',editor:'text'">APPID</th>
                         <th data-options="field:'appSecret',width:250,editor:'text'">APPSECRET</th> 
                          <th data-options="field:'email',width:250,editor:'text'">电子邮箱</th>   
                           <th data-options="field:'url',width:250,editor:'text'">URL</th> 
                            <th data-options="field:'token',width:250,editor:'text'">TOKEN</th>            
                    </tr>
                  </thead>
           </table>
            <div id="tb" style="height:auto">                                  
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">撤销</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="selectDefault()">选择默认公众号</a>
              </div>
             <script type="text/javascript">
                    var editIndex = undefined;
                    //检测上次修改是否可停止
                    function endEditing(){
                    	//editIndex  上次双击修改的行号
                          if (editIndex == undefined){ return true;}
                          if ($('#dg1').datagrid('validateRow', editIndex)){
                           
                            $('#dg1').datagrid('endEdit', editIndex);
                            editIndex = undefined;
                            return true;
                           } 
                          else {
                             return false;
                           }
                  }
                    function selectDefault(){
                   
                   	 var row = $('#dg1').datagrid('getSelected');
                        if (row){
                        	 var data=new Object();
                        	 data["data"]=encodeURI(JSON2.stringify(row));
                        	 $.ajax({
                        		 url:"/WeChat3Platform/userController.do?selectDefault",
                        		 data:data,
                        		 success:function(data, textStatus){
                        			 if(textStatus=="success")
                        			 $.messager.alert("提醒",eval(data));
                        		 }		 
                        	 });  
                        }else{
                        	 $.messager.alert('提醒',"你未选中行！" );
                        }      
                    }
                  function onClickRow(index){
                	    //如果此次双击与上次不同行
                          if (editIndex != index){
                                if (endEditing()){
                                	
                                   $('#dg1').datagrid('selectRow', index)
                                   .datagrid('beginEdit', index);
                                	//记录index
                                  editIndex = index;
                                } else {
                                	//上次修改行不能停止 则选中状态为上次修改行
                              $('#dg1').datagrid('selectRow', editIndex);
                                }
                              }
                 }
           
                 function accept(){
                  if (endEditing()){            	  
                	  var rows = $('#dg1').datagrid('getChanges');
                	  var data=new Object();
                	  data["data"]=encodeURI(JSON2.stringify(rows));
                	  $.ajax({
                		  url:"/WeChat3Platform/userController.do?editWeixin",
                		  data:data,
                		  success: function(data, textStatus){
                			  alert("保存成功");
                			  $('#dg1').datagrid('load');
                		  }
                	  });
                	  
                  $('#dg1').datagrid('acceptChanges');
                    }
                 }
                 function reject(){
                  $('#dg1').datagrid('rejectChanges');
                  editIndex = undefined;
                  }
               
              </script>
           <br><br><br>
            <sec:check authority="ROLE_VIP">
            <a href="javascript:void(0)" style="margin-left:5%;width:200px;height:60px;font-size:30px" onclick="goMain()" class="easyui-linkbutton" >前往功能管理</a>
            </sec:check>
            <sec:ignore authority="ROLE_USER">
              <a href="#" style="margin-left:5%;width:200px;height:60px;font-size:30px" class="easyui-linkbutton" >成为VIP</a>
            </sec:ignore>
            <sec:ignore authority="ROLE_VIP">
               <a href="#" style="margin-left:5%;width:200px;height:60px;font-size:30px" class="easyui-linkbutton" >升级VIP</a>
            </sec:ignore>
            <script type="text/javascript">
               function goMain(){
            	   $.ajax({
            		  url:"/WeChat3Platform/jspController.do?goMain",
            		  dataType:"json",
            		  success:function(data,status){
            			  if(data.status=="success"){
            				  window.location.href="/WeChat3Platform/jspController.do?main";
            			  }else{
            				  $.messager.alert("提醒","你尚未添加公众号！")
            			  }
            		  }
            	   });
               }
            </script>
        </div>
        <sec:check authority="ROLE_VIP">
        <div title="添加公众号">
               <form id="ff1" class="easyui-form"  style="margin-left:20%" method="post" data-options="novalidate:true">
                     <table cellpadding="5">
                      <tr>
                            <td style="font-size:15px">微信名</td>
                             <td><input style="width:200px;height:40px" class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
                       </tr>
                      <tr>
                            <td style="font-size:15px">微信号</td>
                             <td><input style="width:200px;height:40px" class="easyui-textbox" type="text" name="weiXinNo" data-options="required:true"></input></td>
                        </tr>
                        <tr>
                            <td style="font-size:15px">微信类型</td>
                             <td>
                             <select style="width:200px;height:40px" class="easyui-combobox" name="language">
                                  <option value="订阅号">订阅号</option>
                                   <option value="服务号">服务号</option>
                                    <option value="测试账号">测试账号</option>
                             </select>
                             </td>
                        </tr>
                        <tr>
                            <td style="font-size:15px">描述</td>
                             <td><input style="width:200px;height:40px" class="easyui-textbox" type="text" name="desc" data-options="required:true"></input></td>
                        </tr>
                        <tr>
                             <td style="font-size:15px">原始id</td>
                             <td><input style="width:200px;height:40px" class="easyui-textbox" type="text" name="originId" data-options="required:true"></input></td>
                        </tr>
                        <tr>
                             <td style="font-size:15px">APPID</td>
                             <td><input style="width:200px;height:40px" class="easyui-textbox" type="text" name="appId" data-options="multiline:true" style="height:60px"></input></td>
                        </tr>
                         <tr>
                            <td style="font-size:15px">APPSECRET</td>
                             <td>
                                  <input style="width:250px;height:40px" class="easyui-textbox" type="text" name="appSecret" data-options="multiline:true" style="height:60px"></input>
                             </td>
                        </tr>
                         <tr>
                            <td style="font-size:15px">电子邮箱</td>
                             <td>
                                  <input style="width:200px;height:40px" class="easyui-textbox" type="text" name="email" data-options="multiline:true" style="height:60px"></input>
                             </td>
                        </tr>
                     </table>
                 </form>               
                 <br><br><br>
                 <h2 style="color:red" id="msg1"></h2>
               <div style="margin-left:30%;padding:5px">
                     <a href="javascript:void(0)" style="width:100px;height:40px" class="easyui-linkbutton" onclick="submitForm1()">添加</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                   <a href="javascript:void(0)" style="width:100px;height:40px" class="easyui-linkbutton" onclick="clearForm1()">重设</a>
                </div>
                <script>
                       function submitForm1(){
                             $('#ff1').form('submit',{
                            	        url:"/WeChat3Platform/userController.do?addWeixin",
                                       onSubmit:function(){
                                           return $(this).form('enableValidation').form('validate');
                                          },
                                       success:function(data){
                                    	   var s=eval(data);
                                    	   $('#msg1').text(data);
                                    	   clearForm1();
                                    	   $('#dg1').datagrid("load");
                                    	   $('#tt').tabs("select","我的公众号");
                                       }
                                        });
                           }
                         function clearForm1(){
                            $('#ff1').form('clear');
                      }
                </script>
        </div>
        </sec:check>
    </div>
  
 
</body>
</html>