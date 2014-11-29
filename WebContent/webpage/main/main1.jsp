<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/security.tld" prefix="sec"%>
 <%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>功能管理</title>
    <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/themes/black/easyui.css">
    <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/demo/demo.css">
    
    <script type="text/javascript" src="/WeChat3Platform/plug-in/jquery-easyui-1.4/jquery.min.js"></script>
    <script type="text/javascript" src="/WeChat3Platform/plug-in/jquery-easyui-1.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/WeChat3Platform/plug-in/json2.js"></script>
       <script  type="text/javascript" src="/WeChat3Platform/plug-in/ckeditor/ckeditor.js"></script>
    <script  type="text/javascript" src="/WeChat3Platform/plug-in/ckeditor/adapters/jquery.js"></script>
    <script type="text/javascript" src="/WeChat3Platform/plug-in/ckeditor/config.js"></script>
    <script type="text/javascript" src="/WeChat3Platform/plug-in/ckfinder/ckfinder.js"></script>
    <script type="text/javascript" src="/WeChat3Platform/plug-in/plug-in/highcharts.js"></script>
     <script type="text/javascript" src="/WeChat3Platform/plug-in/plug-in/gray.js"></script>
      <script type="text/javascript">
      function CKupdate() {
          for (instance in CKEDITOR.instances)
              CKEDITOR.instances[instance].updateElement();
      }
         $(function(){
        	 $('#tt,#tt1').tabs({tabPosition:'left'});
        	// $('#mainContent').ckeditor();
        	 var editor = null;
        	    window.onload = function() {
        	        editor = CKEDITOR.replace( 'mainContent', {
        	            customConfig:'/WeChat3Platform/plug-in/ckeditor/config.js'
        	        });
        	        CKFinder.setupCKEditor( editor, '/WeChat3Platform/plug-in/ckfinder/' );
        	    };
        	 });        
         </script>
</head>
<body>
<h2 style="color:red;font-size : 32px;width:100%;height:30%;text-align:center;">微剑--你犀利的选择</h2>
    <h2 style="width:100%;color:red;font-size : 22px">功能管理   
    <span style="margin-left:85%">
     <a style="font-size:25px;color:green" href="/WeChat3Platform/jspController.do?frame"><sec:user/></a>
     <a style="font-size:15px;color:red" href="/WeChat3Platform/loginController.do?logout">退出</a>
     </span>
    </h2>        
    <img src="http://weisuyun.qiniudn.com/bg.jpg" style="width:1350px;height:900px;position:absolute;left:0;top:0;z-index:-1;"/>
    <div style="margin:20px 0;"></div>
    <div id="mm" class="easyui-tabs" data-options="tabWidth:112" style="width:1250px;height:750px">
        <div title="自动回复" style="padding:10px">

            <div id="tt" class="easyui-tabs" style="width:1250px;height:650px">
               <div title="文本消息模板" style="padding:10px;">
                   
                    <table id="dg" class="easyui-datagrid" title="文本消息定制" style="width:700px;height:auto"
                     data-options=" iconCls: 'icon-edit',autoRowHeight:true,singleSelect: true,toolbar: '#tb',
                                  url: '/WeChat3Platform/templateController.do?loadTextTemplate',method: 'get',onClickRow: onClickRow">
                     <thead>
                        <tr>
                         <th data-options="field:'no',width:80">编号</th>
                         <th data-options="field:'word',width:120,editor:'text'">关键字</th>                                          
                         <th data-options="field:'content',width:250,editor:'textarea'">文本消息</th>      
                         <th data-options="field:'date',width:120,formatter:function(value,row,index){
                                                                         if(value!=null&&value!=undefined){
                                                                        var unixTimestamp = new Date(value);
                                                                        return unixTimestamp.toLocaleString();
                                                                        }else return value;
                                                                                        }">创建时间</th>            
                       </tr>
                     </thead>
                    </table>
 
               <div id="tb" style="height:auto">
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">撤销</a>
                 
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges()">查看改变</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="endEditing()">停止编辑</a>
              </div>
             <script type="text/javascript">
            
                    var editIndex = undefined;
                    function endEditing(){
                    	//editIndex 正在修改的行号
                          if (editIndex == undefined){ return true;}
                    	 
                          if ($('#dg').datagrid('validateRow', editIndex)){
                        	  /*  var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'word'});
                              var word = $(ed.target).text();                                        
                        	  if(word==""){
                        		  $.messager.alert("提醒","关键词不能为空");
                        		  return false;
                        		  
                        	  }
                        	  var ed1 = $('#dg').datagrid('getEditor', {index:editIndex,field:'content'});
                              var content = $(ed1.target).text();  
                        	  if(content==""){
                        		  $.messager.alert("提醒","内容不能为空");
                        		  return false;
                        		  
                        	  }*/
                            $('#dg').datagrid('endEdit', editIndex);
                            editIndex = undefined;
                            return true;
                           } 
                          else {
                             return false;
                           }
                  }
                  function onClickRow(index){
                          if (editIndex != index){
                                if (endEditing()){
                                   $('#dg').datagrid('selectRow', index)
                                   .datagrid('beginEdit', index);
                                  editIndex = index;
                                } else {
                              $('#dg').datagrid('selectRow', editIndex);
                                }
                              }
                 }
                 function append(){
                        if (endEditing()){
                          $('#dg').datagrid('appendRow',{status:'P'});
                          editIndex = $('#dg').datagrid('getRows').length-1;
                          $('#dg').datagrid('selectRow', editIndex)
                          .datagrid('beginEdit', editIndex);
                        }
                 }
                 function removeit(){
                        if (editIndex == undefined){return}
                       $('#dg').datagrid('cancelEdit', editIndex)
                        .datagrid('deleteRow', editIndex);
                      editIndex = undefined;
                 }
                 function accept(){
                  if (endEditing()){               
                      if ($('#dg').datagrid('getChanges').length) {
                    	  alert("保存");
                            var inserted =$('#dg').datagrid('getChanges', "inserted");
                            var deleted = $('#dg').datagrid('getChanges', "deleted");
                            var updated = $('#dg').datagrid('getChanges', "updated");                      
                            var effectRow = new Object();
                            if (inserted.length) {
                                effectRow["inserted"] = encodeURI(JSON.stringify(inserted));
                             }
                            if (deleted.length) {
                                effectRow["deleted"] = encodeURI(JSON.stringify(deleted));
                            }
                             if (updated.length) {
                                effectRow["updated"] = encodeURI(JSON.stringify(updated));
                            }
            	           $.ajax({
            		           url:"/WeChat3Platform/templateController.do?editTextTemplate",
            		           data:effectRow,
            		           success: function(data, textStatus){
            			                  alert("保存成功");
            			                   $('#dg').datagrid('load');
            		                     }
            	                  });
                      }               
            	     $('#dg').datagrid('acceptChanges');            	  
                    }
                 }
                 function reject(){
                  $('#dg').datagrid('rejectChanges');
                  editIndex = undefined;
                  }
                function getChanges(){
                 var rows = $('#dg').datagrid('getChanges');
                 alert(rows.length);
                }
              </script>
              <br><br><br><br><br><br>
                  <form id="moren" class="easyui-form" style="margin-left:10%" method="post">
                          <table cellpadding="5">
                             <thead>
                             <tr>
                              <th style="font-size:15px;text-align:center">修改未匹配回复文本:</th>
                             </tr>
                             </thead>
                              <tr>                                 
                                 <td><input id="defaultText" class="easyui-textbox"  style="width:400px;height:200px;" type="text" name="name" data-options="required:true"></input></td>
                               </tr>             
                          </table>
                   </form>
                   <div style="margin-left:25%;padding:5px">
                         <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submit()">保存</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clear()">重设</a>
                   </div>         
                  <script>
                      $.ajax({
                           url:"/WeChat3Platform/templateController.do?loadDefault",
                           dataType:"json",
                            success:function(data,textstatus){
                               // alert(data.word);
                            	  $('#moren').form("load",{
                                       name:data.word
                                	  });
                                }
                          });
                       function submit(){
                                $('#moren').form('submit',{
                                     url:"/WeChat3Platform/templateController.do?addDefault",
                                     success:function(data){
                                       $.messager.alert("提醒","修改未匹配默认回复成功！");
                                       var msg=eval(data);
                                       $('#defaultText').text(msg);
                                      }
                                    });
                         }
                         function clear(){
                                $('#moren').form('clear');
                         }
                 </script>
               </div>
               <div title="图文消息模板" style="padding:10px;">  
                   
                  <div id="dlg6" closed="true" class="easyui-dialog" title="添加图文消息模板" data-options="iconCls:'icon-save'" style="width:1000px;height:500px;padding:10px">
                   <div style="padding:10px 60px 20px 60px">
                     <form id="ff6" class="easyui-form"  style="margin-left:20%"  method="post" enctype="multipart/form-data" data-options="novalidate:true">
                       <table cellpadding="5">
                          <tr style="display:none">
                            <td style="font-size:15px">编号</td>
                             <td><input style="width:40px;height:40px" class="easyui-textbox" type="text" readonly name="no" data-options="required:true"></input></td>
                          </tr>     
                          <tr>
                            <td style="font-size:15px">关键字</td>
                             <td><input style="width:200px;height:40px" class="easyui-textbox" type="text" name="word" data-options="required:true"></input></td>
                          </tr>          
                           <tr>
                            <td style="font-size:15px">标题</td>
                             <td><input style="width:200px;height:40px" class="easyui-textbox" type="text" name="title" data-options="required:true"></input></td>
                          </tr>
                           <tr>
                             <td style="font-size:15px">简介</td>
                             <td><input style="width:200px;height:60px" class="easyui-textbox" type="text" name="introduce" data-options="required:true"></input></td>
                           </tr>                        
                           <tr>
                             <td style="font-size:15px">图片上传</td>
                             <td><input id="file" style="width:300px;height:40px" class="easyui-filebox" type="file" name="pic" data-options="multiline:true" ></input></td>
                           </tr>
                                                   
                            <tr>
                             <td><h2 color="red">以下只填一个：</h2></td>
                            </tr>
                            <tr>
                             <td style="font-size:15px">网页链接</td>
                             <td>
                                  <input style="width:250px;height:40px" class="easyui-textbox" type="text" name="pageUrl" data-options="multiline:true" style="height:60px"></input>
                             </td>
                             </tr>
                              <tr>                     
                                <td style="font-size:15px">
                                 <label for="mainContent">网页内容：</label> 
                                 </td>
                                 <td>
                                  <textarea  id="mainContent" name="mainContent" style="width:1024px"></textarea>                               
                                 </td>
                             </tr>
                         </table>
                    </form>   
                    <!-- 
                    <ckeditor:replace replace="mainContent" basePath="/WeChat3Platform/plug-in/ckeditor/" />
                       -->
                      <div style="text-align:center;padding:5px">
                           <a id="1" href="javascript:void(0)" class="easyui-linkbutton" >提交</a>
                           <a  id="2" href="javascript:void(0)" class="easyui-linkbutton" >重设</a>
                          </div>
                       </div>
                       <script>
                       var op;
                       $('#file').filebox({
                    	    buttonText: '选择文件'               	  
                    	})
                    	$("#1").click(
                          function submitForm5(){
                        	  CKupdate();
                    	       if(op){
                        	       alert("添加");
                                   $('#ff6').form('submit',{
                                    url:"/WeChat3Platform/templateController.do?addNewsTemplate",
                                   success:function(data){
                                	   var n=eval(data);
                                	  $.messager.alert("提醒",n);
                                      $("#dg1").datagrid('load'); 
                                      $("#dlg6").dialog('close');
                                   }
                                  });
                    	       }else{
                    	    	    alert("修改");
                                    $('#ff6').form('submit',{
                                     url:"/WeChat3Platform/templateController.do?editNewsTemplate",
                                    success:function(data){
                                    	var n=eval(data);
                                    	$.messager.alert("提醒",n);
                                        $("#dg1").datagrid('load'); 
                                        $("#dlg6").dialog('close');
                                    }
                                    });


                        	       }
                          });
                       $("#2").click(
                          function clearForm5(){
                                 $('#ff6').form('clear');
                          });
                      </script>
                 </div>      
                                         
                   <table id="dg1" class="easyui-datagrid" title="图文消息列表" style="width:100%;height:auto"
                     data-options=" iconCls: 'icon-edit',singleSelect: true,toolbar: '#tb1',
                                  url: '/WeChat3Platform/templateController.do?loadNewsTemplate',method: 'get'">
                     <thead>
                        <tr>
                        <th data-options="field:'no',width:80">编号</th>
                        <th data-options="field:'word',width:100,editor:'text'">关键字</th>
                       <th data-options="field:'title',width:100,editor:'text'">标题</th>
                     <th data-options="field:'introduce',width:200,editor:'text'">简介</th>
                     <th data-options="field:'picUrl',width:400,align:'center',editor:'text'">图片链接</th>                   
                      <th data-options="field:'pageUrl',width:400,align:'center',editor:'text'">网页链接</th>
                      <th data-options="field:'mainContent',width:300,height:150,align:'center',editor:'textarea'">网页内容</th>
                        <th data-options="field:'date',width:120,formatter:function(value,row,index){
                                                                         if(value!=null&&value!=undefined){
                                                                        var unixTimestamp = new Date(value);
                                                                        return unixTimestamp.toLocaleString();
                                                                        }else return value;
                                                                                        }">创建时间</th> 
                       </tr>
                    </thead>
                  </table>
                  
               <div id="tb1" style="height:auto">
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append1()">添加</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit1()">删除</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="edit1()">编辑</a>
              </div>
             <script type="text/javascript">
              
             
                 function append1(){
                	// $("#ff5").attr("action","/WeChat3Platform/templateController.do?addNewsTemplate");    
                	 //alert($("#ff5").attr("action"));
                	 op=true;
                	 $('#dlg6').dialog('open');                     
                 }
                 function removeit1(){
                	 var row = $('#dg1').datagrid('getSelected');
                     if (row){
                    	 var data=new Object();
                    	 data["data"]=encodeURI(JSON2.stringify(row));
                    	 var index=$('#dg1').datagrid('getRowIndex',row);
                    	 $('#dg1').datagrid('deleteRow',index);  
                    	 $.ajax({
                    		 url:"/WeChat3Platform/templateController.do?removeNewsTemplate",
                    		 data:data,
                    		 success:function(data1, textStatus){
                    			 if(textStatus=="success")
                    			 $.messager.alert("提醒！",data1);
                    		 }		 
                    	 });
                     }else{
                     	 $.messager.alert('提醒',"你未选中行！" );
                     }                    
                 }
                 function edit1(){
                	// $("#ff5").attr("action","/WeChat3Platform/templateController.do?editNewsTemplate");  
                	
                	 var row = $('#dg1').datagrid('getSelected');
                     if (row){
                         op=false;
                    	 $("#ff6").form('load',row);    
                    	 //$('#mainContent').val(row.mainContent);
                    	 alert(row.mainContent);
                    	 CKEDITOR.instances['mainContent'].setData(row.mainContent);
                    	 $('#dlg6').dialog("open");   
                     }else{
                     	 $.messager.alert('提醒',"你未选中行！" );
                     }      
                 }

              </script>
                  
             
              </div>     
              
                    
            </div>
            
       </div>  
        <div title="关注回复" style="padding:10px">           
                 <div style="margin:20px 0;">
                       <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg').dialog('open')">添加回复模板</a>
                 </div>
                 <table id="dgs" class="easyui-datagrid" title="关注回复" style="width:700px;height:250px"
                         data-options="singleSelect:true,collapsible:true,url:'/WeChat3Platform/subscribeController.do?loadResponse',method:'get'">
                  <thead>
                  <tr>
                         <th data-options="field:'no',width:100">模板编号</th>  
                          <th data-options="field:'type',width:100">模板类型</th>                                     
                         <th data-options="field:'content',width:500,align:'right'">内容</th>                        
                  </tr>
                  </thead>
                </table>      
                 <div id="dlg" closed="true" class="easyui-dialog" title="添加关注回复" data-options="iconCls:'icon-save'" style="width:400px;height:200px;padding:10px">
                         <div style="padding:10px 60px 20px 60px">
                          <form id="ff" method="post">
                              <table cellpadding="5">
                               <tr>
                                  <td>模板编号</td>
                                   <td>  
                                   <select id="noo" style="width:100px" name="no">                                  
                                   </select>
                                   </td>
                               </tr>                                                  
                               </table>
                           </form>
                          <div style="text-align:center;padding:5px">
                           <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submits()">提交</a>
                           <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clears()">重设</a>
                          </div>
                       </div>
                       <script>
                       $("#dlg").dialog({
                          onOpen:function(){
                          $.ajax({
                             url:"/WeChat3Platform/subscribeController.do?loadNo",
                              dataType:"json",
                              success:function(data,textStatus){
                                  var options="";
                                  
                                   $.each(data,function(i,obj){
                                        options+="<option value=\""+obj.value+"\">"+obj.text+"</option>";
                                       });
                                  // alert(options);
                                   $("#noo").append(options);
                                  }
                              });
                    
                          }
                       });
                          function submits(){
                                $('#ff').form('submit',{
                                  url:"/WeChat3Platform/subscribeController.do?selectNo",
                                  success:function(data){
                                      alert(eval(data));
                                      $("#dlg").dialog("close");
                                      $("#dgs").datagrid("load");
                                     }
                                    });
                          }
                          function clears(){
                                 $('#ff').form('clear');
                          }
                      </script>
                 </div>
                  
        </div>
        
        <div title="自定义菜单" style="padding:10px">
                 <h2>注意：主菜单最多三个，二级子菜单最多五个,复合类型菜单只需要填菜单项名！谨慎选择类型！</h2>
                 <script type="text/javascript">
                  var Types = [{ "type": "点击类型", "text": "点击类型" }, { "type": "链接类型", "text": "链接类型" }, { "type": "复合类型", "text": "复合类型" }];
                  </script>
                   <table id="dgm" class="easyui-datagrid" title="菜单项" style="width:800px;height:250px"
                         data-options="singleSelect:true, toolbar: '#tbm',
                                       onClickRow: onClickRowm,collapsible:true,
                                       url:'/WeChat3Platform/menuController.do?loadDiyMenu',
                                       method:'get'">
                    <thead>
                    <tr>
                         <th data-options="field:'name',width:80,editor:'text'">菜单项名</th>
                          <th data-options="field:'type',width:100,editor:{type:'combobox',
                                                                          options:{
                                                                      valueField:'type',
                                                                      textField:'text',
                                                                       data:Types,
                                                                      required:true
                            }}">菜单项类型</th>
                         <th data-options="field:'key',width:150,editor:'text'">菜单项key(点击类型请填)</th>
                         <th data-options="field:'url',width:250,editor:'text'">菜单项url(链接类型请填)</th>                          
                         <th data-options="field:'pname',width:150,align:'right',editor:'text'">父菜单项名(非复合类型请填)</th>                        
                    </tr>
                    </thead>            
                </table>
                <div id="tbm" style="height:auto">
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendm()">添加</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeitm()">删除</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="acceptm()">保存</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="rejectm()">撤销</a>              
              </div>
              <br>
              <h2 style="color:red"  id="errmsg"></h2>
              <div style="margin:20px 0;">
                       <a href="javascript:void(0)" class="easyui-linkbutton" onclick="getMenu()">远程加载公众号菜单</a>                                        
                       <a href="javascript:void(0)" class="easyui-linkbutton" onclick="generateMenu()">生成公众号菜单</a>                                        
                       <a href="javascript:void(0)" class="easyui-linkbutton" onclick="deleteMenu()">清空公众号菜单</a>                                                         
              </div>
              <script type="text/javascript">
                 function getMenu(){
                       $.ajax({
                           url:"/WeChat3Platform/menuController.do?getMenu",
                           dataType:"json",
                           error:function(XMLHttpRequest, textStatus, errorThrown){
                        	   $("#errmsg").html("");
                                alert(textStatus);
                               },
                           success:function(data,textStatus){
                        	   var jsonstr = '{"total":'+data.length+',"rows":'+JSON2.stringify(data)+'}';  
                               //alert(data[0].name);
                               var item = $('#dgm').datagrid('getRows');
                               if(item) {
                                   for (var i = item.length - 1; i >= 0; i--) {
                                         var index = $('#dgm').datagrid('getRowIndex', item[i]);
                                         $('#dgm').datagrid('deleteRow', index);
                                   }
                               }
                               var data1 = $.parseJSON(jsonstr);  
                                 $("#dgm").datagrid("loadData",data1);
                                 $("#errmsg").html("");
                               }
                               
                           });
 
                     }
                 function generateMenu(){
                     $.ajax({
                         url:"/WeChat3Platform/menuController.do?generateMenu",
                         dataType:"html",
                         error:function(XMLHttpRequest, textStatus, errorThrown){
                             alert(textStatus);
                             },
                         success:function(data,textStatus){
                      	   $("#errmsg").html(data);
                         }
                     });

                   }
                 function deleteMenu(){
                     $.ajax({
                         url:"/WeChat3Platform/menuController.do?deleteMenu",
                         dataType:"text",
                         error:function(XMLHttpRequest, textStatus, errorThrown){
                        	 $("#errmsg").html("");
                             alert(textStatus);
                             },
                         success:function(data,textStatus){
                        	 $("#errmsg").html("");
                      	    $.messager.alert("提示",data);                           
                             }
                         });

                   }
              </script>
             <script type="text/javascript">   
                    var editIndexm = undefined;
                    function endEditingm(){
                    	//editIndex 正在修改的行号
                          if (editIndexm == undefined){ return true;}              	 
                          if ($('#dgm').datagrid('validateRow', editIndexm)){
                        	 // var ed = $('#dgm').datagrid('getEditor', {index:editIndexm,field:'type'});
                             //var text = $(ed.target).combobox('getText');
                             // $('#dgm').datagrid('getRows')[editIndexm]['type'] = text;
                        	  $('#dgm').datagrid('endEdit', editIndexm);
                            editIndexm= undefined;
                            return true;
                           } 
                          else {
                             return false;
                           }
                  }
                  function onClickRowm(index){
                          if (editIndexm!= index){
                                if (endEditingm()){
                                   $('#dgm').datagrid('selectRow', index)
                                   .datagrid('beginEdit', index);
                                  editIndexm = index;
                                } else {
                              $('#dgm').datagrid('selectRow', editIndexm);
                                }
                              }
                 }
                 function appendm(){
                        if (endEditingm()){
                          $('#dgm').datagrid('appendRow',{status:'P'});
                          editIndex = $('#dgm').datagrid('getRows').length-1;
                          $('#dgm').datagrid('selectRow', editIndexm)
                          .datagrid('beginEdit', editIndexm);
                        }
                 }
                 function removeitm(){
                        if (editIndexm == undefined){return}
                       $('#dgm').datagrid('cancelEdit', editIndexm)
                        .datagrid('deleteRow', editIndexm);
                      editIndexm = undefined;
                 }
                 function acceptm(){
                  if (endEditingm()){               
                      if ($('#dgm').datagrid('getChanges').length) {
                    	  alert("保存");
                            var inserted =$('#dgm').datagrid('getChanges', "inserted");
                            var deleted = $('#dgm').datagrid('getChanges', "deleted");
                            var updated = $('#dgm').datagrid('getChanges', "updated");                      
                            var effectRow = new Object();
                            if (inserted.length) {
                                effectRow["inserted"] = encodeURI(JSON.stringify(inserted));
                             }
                            if (deleted.length) {
                                effectRow["deleted"] = encodeURI(JSON.stringify(deleted));
                            }
                             if (updated.length) {
                                effectRow["updated"] = encodeURI(JSON.stringify(updated));
                            }
            	           $.ajax({
            		           url:"/WeChat3Platform/menuController.do?saveMenu",
            		           data:effectRow,
            		           success: function(data, textStatus){
            			                  alert("保存成功");
            			                   $('#dg').datagrid('load');
            		                     }
            	                  });
                      }               
            	     $('#dgm').datagrid('acceptChanges');            	  
                    }
                 }
                 function rejectm(){
                  $('#dgm').datagrid('rejectChanges');
                  editIndex = undefined;
                  }
           
              </script>
            </div>
     
        <div title="用户管理" data-options="tabWidth:110" style="padding:10px">
            <div id="tt1" class="easyui-tabs" style="width:1250px;height:650px">
               <div title="分组管理" style="padding:10px;">      
                     <div id="dlgu" closed="true" class="easyui-dialog" title="添加分组" data-options="iconCls:'icon-save'" style="width:400px;height:200px;padding:10px">
                        <div style="padding:10px 60px 20px 60px">
                        <form id="ffu" class="easyui-form"  style="margin-left:20%"  method="post" enctype="multipart/form-data" data-options="novalidate:true">
                         <table cellpadding="5">
                          <tr style="display:none">
                            <td style="font-size:15px">编号</td>
                             <td><input style="width:40px;height:40px" class="easyui-textbox" type="text" readonly name="id" data-options="required:true"></input></td>
                          </tr>     
                          <tr>
                            <td style="font-size:15px">组名</td>
                             <td><input style="width:100px;height:40px" class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
                          </tr>                                           
                         </table>
                         </form>                                          
                         <div style="text-align:center;padding:5px">
                           <a  href="javascript:void(0)" class="easyui-linkbutton" onclick="submitu()">提交</a>
                           <a   href="javascript:void(0)" class="easyui-linkbutton" onclick="clearu()">重设</a>
                         </div>            
                       </div>
                       <script>  
                          var opu;                             	
                          function submitu(){                    	 
                    	       if(opu){
                        	       alert("添加");
                                   $('#ffu').form('submit',{
                                    url:"/WeChat3Platform/weiXinUserController.do?addGroup",
                                   success:function(data){
                                	   var n=eval(data);
                                	  $.messager.alert("提醒",n);
                                      $("#dgu").datagrid('load'); 
                                      $("#dlgu").dialog('close');
                                   }
                                  });
                    	       }else{
                    	    	    alert("修改");
                                    $('#ffu').form('submit',{
                                     url:"/WeChat3Platform/weiXinUserController.do?editGroup",
                                    success:function(data){
                                    	var n=eval(data);
                                    	$.messager.alert("提醒",n);
                                        $("#dgu").datagrid('load'); 
                                        $("#dlgu").dialog('close');
                                    }
                                    });
                        	       }
                          }                  
                          function clearu(){
                                 $('#ffu').form('clear');
                          }
                       </script>
                       </div>
                    <table id="dgu" class="easyui-datagrid" title="分组" style="width:700px;height:250px"
                         data-options="singleSelect:true,toolbar:'#tbu',collapsible:true,
                          url:'/WeChat3Platform/weiXinUserController.do?loadRemoteGroup',method:'get'">
                        <thead>
                           <tr>
                             <th data-options="field:'id',width:200">组号</th>
                             <th data-options="field:'name',width:200">组名</th>                     
                             <th data-options="field:'count',width:200,align:'right'">组成员数</th>                        
                          </tr>
                        </thead>
                    </table>
                    <div id="tbu" style="height:auto">
                         <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendu()">添加分组</a>
                         <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="editu()">编辑分组</a>
                    </div>
                  <script type="text/javascript">                   
                         function appendu(){               	    
                	           opu=true;
                	       $('#dlgu').dialog('open');                     
                         }
                         
                        function editu(){                            	
                	          var row = $('#dgu').datagrid('getSelected');
                              if (row){
                                     opu=false;
                    	             $("#ffu").form('load',row);    
                    	             $('#dlgu').dialog("open");   
                                }else{
                                	 $.messager.alert('提醒',"你未选中行！" );
                                }      
                           }

                    </script>
               </div>
               <div title="用户管理" style="padding:10px;">  
                     <div style="margin-left:30%;padding:5px">      
                           <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg0').dialog('open')">移动用户到指定分组</a>   
                     </div>   
                    <table class="easyui-datagrid" title="查询总揽" style="width:800px;height:250px"
                         data-options="singleSelect:true,collapsible:true,
                         url:'/WeChat3Platform/weiXinUserController.do?query',method:'get'">
                        <thead>
                           <tr>
                             <th data-options="field:'total',width:150">总关注者数</th>
                             <th data-options="field:'count',width:150">本次获取的关注者数</th>                     
                             <th data-options="field:'nextOpenId',width:400,align:'right'">下一个要获取用户openId</th>                        
                          </tr>
                        </thead>
                    </table>
                    <br><br><br><br>
                     <table id="dgul" class="easyui-datagrid" title="用户列表" style="width:1000px;height:250px"
                         data-options="singleSelect:true,collapsible:true,
                         url:'/WeChat3Platform/weiXinUserController.do?loadWeiXinUser',method:'get'">
                        <thead>
                           <tr>
                             <th data-options="field:'openId',width:250">openId</th>
                             <th data-options="field:'subscribe',width:50,formatter:function(value,row,index){
                                                                         if(value=='0'){
                                                                         return '未关注';                      
                                                                        }else if(value=='1'){
                                                                          return '已关注';
                                                                        }else return value;
                                                                                        }">关注状态</th>                     
                             <th data-options="field:'subscribeTime',width:200,align:'right',formatter:function(value,row,index){
                                                                         if(value!=null&&value!=undefined){
                                                                         value=parseInt(value,10);                                 
                                                                        var unixTimestamp = new Date(value*1000);
                                                                        return unixTimestamp.toLocaleString();
                                                                        }else return value;
                                                                                        }">最后关注时间</th>
                             <th data-options="field:'nickname',width:100">昵称</th>
                             <th data-options="field:'sex',width:50,formatter:function(value,row,index){
                                                                         if(value=='0'){
                                                                         return '未知';                      
                                                                        }else if(value=='1'){
                                                                          return '男性';
                                                                        }else if(value=='2'){
                                                                          return '女性';
                                                                        } else return value;
                                                                                        }">性别</th>                     
                             <th data-options="field:'country',width:40,align:'right'">国家</th>                        
                             <th data-options="field:'province',width:40">省份</th>
                             <th data-options="field:'city',width:40">城市</th>                     
                             <th data-options="field:'headImgUrl',width:100,align:'right'">头像地址</th>                          
                             <th data-options="field:'groupId',width:40">组号</th>
                               <th data-options="field:'groupName',width:40">组名</th>                                
                          </tr>
                        </thead>
                    </table>
                    
                    <div id="dlg0" closed="true" class="easyui-dialog" title="用户列表" data-options="iconCls:'icon-save'" style="width:600px;height:300px;padding:10px">
                         <div style="padding:10px 60px 20px 60px">
                          <form id="ff0" method="post">
                              <table cellpadding="5">
                               <tr>
                                  <td>用户openId</td>
                                   <td>
                                  <input class="easyui-textbox" type="text" name="openId" data-options="required:true" style="height:40px;width:300px"></input>
                                   </td>
                               </tr>
                              
                                <tr>
                                   <td>目标分组</td>
                                    <td>
                                     <input class="easyui-textbox" type="text" name="id" data-options="required:true" style="height:40px;width:300px"></input>
                                    </td>
                                </tr>                              
                               </table>
                           </form>
                          <div style="text-align:center;padding:5px">
                           <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm0()">提交</a>
                           <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm0()">清除</a>
                          </div>
                       </div>
                       <script>
                          function submitForm0(){
                                $('#ff0').form('submit',{
                                  url:"/WeChat3Platform/weiXinUserController.do?moveUserToGroup",
                                  dataType:"text",
                                  success:function(data){
                                    $('#dgu').datagrid('load');
                                    $('#dgul').datagrid('load');
                                    $('#dlg0').dialog('close');
                                    $.messager.alert("提醒",data);
                                      }
                                    });
                          }
                          function clearForm0(){
                                 $('#ff0').form0('clear');
                          }
                      </script>
                 </div>
                 
              </div> 
               <div title="用户分析" style="padding:10px;">
                 <div class="easyui-accordion" style="width:100%;height:100%;">
                   <div title="关注统计" data-options="iconCls:'icon-large-chart'" style="overflow:auto;padding:10px;">
                     <table style="width:100%;height:100%">
                       <tr>
                          <td>
                           <table id="ypg" class="easyui-propertygrid" style="width:270px;height:100px" data-options="
                                    url:'weiXinUserController.do?getYSNums',
                                    method:'get',
                                    showGroup:true,
                                     showHeader:false,
                                    scrollbarSize:0
                             ">
                            </table>
                          </td>
                          <td>
                            <table id="cpg" class="easyui-propertygrid" style="width:270px;height:100px" data-options="
                                    url:'weiXinUserController.do?getYUSNums',
                                    method:'get',
                                    showGroup:true,
                                     showHeader:false,
                                    scrollbarSize:0
                             ">
                            </table>
                          </td>
                          <td>
                            <table id="tpg" class="easyui-propertygrid" style="width:270px;height:100px" data-options="
                                    url:'weiXinUserController.do?getYASNums',
                                    method:'get',
                                    showGroup:true,
                                    showHeader:false,
                                    scrollbarSize:0
                             ">
                            </table>
                          </td>
                          <td>
                            <table id="ttpg" class="easyui-propertygrid" style="width:270px;height:100px" data-options="
                                    url:'weiXinUserController.do?getTSNums',
                                    method:'get',
                                    showGroup:true,
                                    showHeader:false,
                                    scrollbarSize:0
                             ">
                            </table>
                          </td>
                       </tr>
                       <tr>
                        <div id="lineChart">
                        </div>
                       </tr>
                     </table>
                     <script id="lineScript" type="text/javascript">                 
                       $.ajax({
                           url:"weiXinUserController.do?countUsers",
                           dataType:"json",
                           success:function(data,status){
                        	   $('#lineChart').highcharts({
                        		   chart: {
                        	            type: 'line'
                        	        },
                        	        title: {
                        	            text: '关注用户数量变化情况'
                        	        },                    	       
                        	        xAxis: {
                        	            categories: ['七天前', '六天前', '五天前', '四天前', '三天前', '前天', '昨天']
                        	        },
                        	        yAxis: {
                        	            title: {
                        	                text: '人数 (个)'
                        	            }
                        	        },
                        	        tooltip: {
                        	            enabled: false,
                        	            formatter: function() {
                        	                return '<b>'+ this.series.name +'</b><br>'+this.x +': '+ this.y +'°C';
                        	            }
                        	        },
                        	        plotOptions: {
                        	            line: {
                        	                dataLabels: {
                        	                    enabled: true
                        	                },
                        	                enableMouseTracking: false
                        	            }
                        	        },
                        	        series: data
                       	    });
                             }
                           });              	
               </script>
                   </div>
                   <div title="分类统计" data-options="iconCls:'icon-large-chart'" style="padding:10px;">
                        <table>
                           <tr><td>
                             <div id="sexChart"></div>
                           </td></tr>
                           <tr><td>
                             <div id="provinceChart"></div>  
                           </td></tr>
                           <tr><td>
                             <div id="languageChart"></div>  
                           </td></tr>
                        </table>
                   </div>
                 </div>
                 <script id="sexScript" type="text/javascript">                 
                       $.ajax({
                           url:"weiXinUserController.do?countSex",
                           dataType:"json",
                           success:function(data,status){
                               alert(data);
                        	   $('#sexChart').highcharts({
                       	        chart: {
                       	            type: 'pie',
                       	            options3d: {
                       	                enabled: true,
                       	                alpha: 45,
                       	                beta: 0
                       	            }
                       	        },
                       	        title: {
                       	            text: '粉丝男女性别比例'
                       	        },
                       	        tooltip: {
                       	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                       	        },
                       	        plotOptions: {
                       	            pie: {
                       	                allowPointSelect: true,
                       	                cursor: 'pointer',
                       	                depth: 35,
                       	                dataLabels: {
                       	                    enabled: true,
                       	                    format: '{point.name}'
                       	                }
                       	            }
                       	        },
                       	        series: [{
                       	            type: 'pie',
                       	            name: '性别所占份额',
                       	            data: data
                       	        }]
                       	    });
                             }
                           });              	
               </script>
               <script id="languageScript" type="text/javascript">
               $.ajax({
                   url:"weiXinUserController.do?countLan",
                   dataType:"json",
                   success:function(data,status){
            	    var chart = new Highcharts.Chart({
            	        chart: {
            	            renderTo: 'languageChart',
            	            type: 'column',
            	            margin: 75,
            	            options3d: {
            	                enabled: true,
            	                alpha: 15,
            	                beta: 15,
            	                depth: 50,
            	                viewDistance: 25
            	            }
            	        },
            	        title: {
            	            text: '语言分布'
            	        },
            	        xAxis: {
            	            categories: ['简体中文', '台湾繁体中文', '香港繁体中文', '英文', '法文','其他']
            	        },
            	        plotOptions: {
            	            column: {
            	                depth: 25
            	            }
            	        },
            	        series: [{
            	            data: data
            	        }]
            	    });          	
                    }
               });
               </script>
               <script id="provinceScript" type="text/javascript">
               $.ajax({
                   url:"weiXinUserController.do?countProvince",
                   dataType:"json",
                   success:function(data,status){
           	    $('#provinceChart').highcharts({
           	        chart: {
           	            type: 'pie',
           	            options3d: {
           	                enabled: true,
           	                alpha: 45,
           	                beta: 0
           	            }
           	        },
           	        title: {
           	            text: '用户各省份分布图'
           	        },
           	        tooltip: {
           	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
           	        },
           	        plotOptions: {
           	            pie: {
           	                allowPointSelect: true,
           	                cursor: 'pointer',
           	                depth: 35,
           	                dataLabels: {
           	                    enabled: true,
           	                    format: '{point.name}'
           	                }
           	            }
           	        },
           	        series: [{
           	            type: 'pie',
           	            name: '本省人数',
           	            data: data
           	        }]
           	    });
                }
           	});
               </script>
               </div>       
            </div>
            
        </div>
        <div title="群发功能" style="padding:10px">
                   <form id="ff5" method="post">
                              <table cellpadding="5">
                                <tr>
                                  <td>群发组名</td>
                                  <td>
                                    <select id="grp"  style="width:600px;height:50px" name="id">                                                                                                          
                                     </select>
                                   </td>
                                </tr>                                                
                               <tr>
                                  <td>群发消息模板</td>
                                   <td>
                                  <select id="tmo" style="width:100px" name="no">                                  
                                   </select>
                                   </td>
                               </tr>      
                                            
                               </table>
                </form>
                
                 <div style="margin-left:30%;padding:5px">
                           <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm5()">提交</a>
                           <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm5()">清除</a>
                 </div>               
                   <script>
                  $(function(){
                       $.ajax({
                          url:"/WeChat3Platform/massDeliverController.do?loadNo",
                           dataType:"json",
                           success:function(data,textStatus){
                               var options="";
                               
                                $.each(data,function(i,obj){
                                     options+="<option value=\""+obj.value+"\">"+obj.text+"</option>";
                                    });
                              //  alert(options);
                                $("#tmo").append(options);
                               }
                           });
                 
                       }
                    );
                  $(function(){
                      $.ajax({
                         url:"/WeChat3Platform/massDeliverController.do?loadGroup",
                          dataType:"json",
                          success:function(data,textStatus){
                              var options="";
                         
                               $.each(data,function(i,obj){
                                    options+="<option value=\""+obj.value+"\">"+obj.text+"</option>";
                                   });
                             //  alert(options);
                               $("#grp").append(options);
                              }
                          });
                
                      }
                   );
                          function submitForm5(){
                                $('#ff5').form('submit',{
                                     url:"/WeChat3Platform/massDeliverController.do?massDeliver",
                                     success:function(data){
                                         var d=eval(data);
                                          $.messager.show({
                                        		title:'提醒',
                                        		msg:d,
                                        		timeout:5000,
                                        		showType:'slide',
                                        		 style:{
                                                     right:'',
                                                     bottom:''
                                                 }
                                        	});
                                         }
                                    });
                          }
                          function clearForm5(){
                                 $('#ff5').form('clear');
                          }
                   </script>
        </div>    
        
    </div>
</body>
</html>