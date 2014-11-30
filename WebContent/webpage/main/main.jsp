<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微剑--功能管理</title>
<script src="/WeChat3Platform/plug-in/js/html5.js"></script>
<link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/css/main.css"/>
<link href="/WeChat3Platform/plug-in/css/z_index_style2.css" type="text/css" rel="stylesheet" />
<link href="/WeChat3Platform/plug-in/css/main_sub_tab.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/css/public.css" />
<link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/css/thickbox.css" />
<link href="/WeChat3Platform/plug-in/css/footer.css" rel="stylesheet"   media="all"/>
 
 
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
    <script type="text/javascript" src="/WeChat3Platform/plug-in/highcharts.js"></script>
     <script type="text/javascript" src="/WeChat3Platform/plug-in/gray.js"></script>
 
</head>
<body >
<div class="body">
<header>	
     <h1><a href="" id="logo">微剑——你犀利的选择</a></h1>
</header>

<div class="contain">
  <div class="pagMenu">
    <div class="menuMst">
      <ul>
        <li id="top1_index" class="on"><a id="top1_linkIndex" href="/WeChat3Platform/webpage/login/login.jsp">首页</a> </li>
        <li id="top1_mobile"><a id="top1_linkMobile" href="jspController.do?frame">账号管理</a> </li>
        <li id="top1_fitting"><a id="top1_linkFitting" href="#">功能管理</a> </li>
        <li id="top1_benefit"><a id="top1_linkBenefit" href="/WeChat3Platform/webpage/login/introduce.jsp">功能介绍</a> </li>
        <li><a href="" target="_blank">联系我们</a> </li>
       </ul>
     </div>
    <div class="menuSub">
      <h3 id="top1_menuSubTit">功能介绍</h3>
      <ul>
        <li><a id="top1_HyperLink1" href="/WeChat3Platform/webpage/login/introduce.jsp">自动回复</a> </li>
        <li><a id="top1_HyperLink2" href="/WeChat3Platform/webpage/login/introduce.jsp">关注回复</a> </li>
        <li><a id="top1_HyperLink3" href="/WeChat3Platform/webpage/login/introduce.jsp">自定义菜单</a> </li>
        <li><a id="top1_HyperLink4" href="/WeChat3Platform/webpage/login/introduce.jsp">用户管理</a> </li>
        <li><a id="top1_HyperLink5" href="/WeChat3Platform/webpage/login/introduce.jsp">群发管理</a> </li>
        <li><a id="top1_HyperLink6" href="/WeChat3Platform/webpage/login/introduce.jsp">微餐饮</a> </li>
      </ul>
    </div>
  </div>
</div>


<div class="bannerx">
<img src="/WeChat3Platform/plug-in/images/inkeBan_1336437715.jpg"  
    title="bannerx" alt="微剑banner" width="1133" height="200" /><div class="clear">
</div>
 </div>
 
 
<div class="main_tab">
<div class="m2yw_right">

  <div class="m2yw_tab">
    <ul id="tab2">
       <li  onmousemove="changeTab(2,1)">微餐饮</li>
      <li  onmousemove="changeTab(2,2)">群发功能</li>
      <li onmousemove="changeTab(2,3)">用户管理</li>
      <li onmousemove="changeTab(2,4)">自定义菜单</li>
      <li onmousemove="changeTab(2,5)">关注回复</li>
      <li class="m2yw_cutli" onmousemove="changeTab(2,6)">自动回复</li>
    </ul>
  </div>
  <div id="tablist2">
    <div class="bar"></div>
    <div id="1" class="m2yw_pic hidden">
         <div id="z_index_201208y">
               <div class="clear" id="z_brandy">
                   <div id="tagsy">
                         <h3 class="z_brand_title6_selectTag" id="z_brand_title6"><a class="z_brand_oumei" onmouseover="selectTag2(this)" href="javascript:void(0)">添加门店</a></h3>
                         <h3 class="z_brand_title7" id="z_brand_title7"><a class="z_brand_guochan" onmouseover="selectTag2(this)" href="javascript:void(0)">版式设置</a></h3>
                         <h3 class="z_brand_title8" id="z_brand_title8"><a class="z_brand_rihan" onmouseover="selectTag2(this)" href="javascript:void(0)">底部菜单</a></h3>
                         <h3 class="z_brand_title9" id="z_brand_title9"><a class="z_brand_oumei1" onmouseover="selectTag2(this)" href="javascript:void(0)">订单管理</a></h3>
                         <h3 class="z_brand_title10" id="z_brand_title10"><a class="z_brand_guochan1" onmouseover="selectTag2(this)" href="javascript:void(0)">账单管理</a></h3>
                         <h3 class="z_brand_title11" id="z_brand_title11"><a class="z_brand_rihan1" onmouseover="selectTag2(this)" href="javascript:void(0)">顾客管理</a></h3>
                         <h3 class="z_brand_title12" id="z_brand_title12"><a class="z_brand_oumei2" onmouseover="selectTag2(this)" href="javascript:void(0)">菜品管理</a></h3>
                         <h3 class="z_brand_title13" id="z_brand_title13"><a class="z_brand_guochan2" onmouseover="selectTag2(this)" href="javascript:void(0)">菜品分类</a></h3>
                   </div>
                  <div id="tagContenty">
                         <ul class="z_brand_list_om clear" id="tagContent6" >
                          
                         </ul>
                         <ul class="z_brand_list_gc clear" id="tagContent7">
                        
                         </ul>
                         <ul class="z_brand_list_rh clear" id="tagContent8" >
                         
                         </ul>
                         <ul class="z_brand_list_1 clear" id="tagContent9" >
                        
                         </ul>
                          <ul class="z_brand_list_2 clear" id="tagContent10" >
                        
                         </ul>
                         <ul class="z_brand_list_3 clear" id="tagContent11">
                        
                         </ul>
                         <ul class="z_brand_list_4 clear" id="tagContent12" >
                        
                         </ul>
                         <ul class="z_brand_list_5 clear" id="tagContent13" >
                        
                         </ul>
                  </div>
               </div>
         </div>
    </div>
    <div id="2" class="m2yw_pic hidden">
         <div id="tagsy1">            
           <h3 class="z_brand_title" ><a class="z_brand_guochan"  href="javascript:void(0)">群发管理</a></h3>    
         </div>
         <div class="easyui-panel" title="群发消息" style="width:75%;height:98%">
               <div style="padding:10px 60px 60px 60px">
                  <form style="margin-top:20%;margin-left:20%" id="ff5" method="post" >
                              <table cellpadding="5">
                                <tr>
                                  <td>群发组名</td>
                                  <td>
                                    <select id="grp"  style="width:400px;height:100px" name="id">                                                                                                          
                                     </select>
                                   </td>
                                </tr>                                                
                               <tr >
                                  <td>群发消息模板</td>
                                   <td>
                                  <select id="tmo" style="width:400px;height:100px" name="no">                                  
                                   </select>
                                   </td>
                               </tr>      
                                            
                               </table>
                  </form>
                
                 <div style="margin-left:45%;padding:5px">
                           <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm5()">提交</a>
                           <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm5()">清除</a>
                 </div>    
            </div>
          </div>      
    </div>
    <div id="3" class="m2yw_pic hidden">
       <div id="z_index_201208x">
               <div class="clear" id="z_brandx">
  
                   <div id="tagsx">
                         <h3 class="z_brand_title3_selectTag" id="z_brand_title3"><a class="z_brand_oumei" onmouseover="selectTag1(this)" href="javascript:void(0)">分组管理</a></h3>
                         <h3 class="z_brand_title4" id="z_brand_title4"><a class="z_brand_guochan" onmouseover="selectTag1(this)" href="javascript:void(0)">用户管理</a></h3>
                         <h3 class="z_brand_title5" id="z_brand_title5"><a class="z_brand_rihan" onmouseover="selectTag1(this)" href="javascript:void(0)">用户分析</a></h3>
                   </div>
    
                  <div id="tagContentx">
                         <ul class="z_brand_list_om clear" id="tagContent3" >
                              <div id="dlgu" closed="true" class="easyui-dialog" title="添加分组" data-options="iconCls:'icon-save'" style="width:600px;height:300px;padding:10px">
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
                              </div>
                                <!-- /WeChat3Platform/weiXinUserController.do?loadRemoteGroup -->
                               <table id="dgu" class="easyui-datagrid" title="分组" style="width:100%;height:100%"
                                     data-options="singleSelect:true,toolbar:'#tbu',collapsible:true,
                                      method:'get'">
                                    <thead>
                                       <tr>
                                          <th data-options="field:'id',width:300">组号</th>
                                          <th data-options="field:'name',width:300">组名</th>                     
                                          <th data-options="field:'count',width:400,align:'right'">组成员数</th>                        
                                       </tr>
                                     </thead>
                                 </table>
                                <div id="tbu" style="height:auto">
                                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendu()">添加分组</a>
                                       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="editu()">编辑分组</a>
                                 </div>
                         </ul>
                         <!--  url:'/WeChat3Platform/weiXinUserController.do?query' -->
                          <ul class="z_brand_list_gc clear" id="tagContent4">
                                    
                                   <table id="dgu2" class="easyui-datagrid" title="查询总揽" style="width:100%;height:320px"
                                          data-options="singleSelect:true,collapsible:true,
                                        method:'get'">
                                      <thead>
                                      <tr>
                                       <th data-options="field:'total',width:250">总关注者数</th>
                                        <th data-options="field:'count',width:250">本次获取的关注者数</th>                     
                                        <th data-options="field:'nextOpenId',width:600,align:'right'">下一个要获取用户openId</th>                        
                                      </tr>
                                     </thead>
                                  </table>
                                   <br>
                                  <div style="margin-left:41%;padding:5px">      
                                          <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg0').dialog('open')">移动用户到指定分组</a>   
                                    </div>  
                                    <br>
                    
                                  <!-- url:'/WeChat3Platform/weiXinUserController.do?loadWeiXinUser' -->
                                  <table id="dgul" class="easyui-datagrid" title="用户列表" style="margin-top:0;width:100%;height:317px"
                                        data-options="singleSelect:true,collapsible:true,
                                        method:'get'">
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
                                       </div>
                          </ul>
                         <ul class="z_brand_list_rh clear" id="tagContent5" >
                             
                             <div class="accordion">  
                                 <h2 class="acc_trigger" style="width:10"><a style="width:1000px" 
                                    href="#">关注统计</a></h2>
                                 <div class="acc_container">
                                       <div class="block">
                                            <table style="width:100%;height:100%">
                                        <tr  style="margin-top:20px">
                                             <td>
                                               <!--url:'weiXinUserController.do?getYSNums',-->
                                                <table id="ypg" class="easyui-propertygrid" style="width:150px;height:200px" data-options="
                                                          
                                                          method:'get',
                                                          showGroup:true,
                                                          showHeader:false,
                                                          scrollbarSize:0
                                                     ">
                                                 </table>
                                              </td>
                                              
                                              <td >
                                                  <!-- url:'weiXinUserController.do?getYUSNums' -->
                                                   <table id="cpg" class="easyui-propertygrid" style="width:150px;height:200px" data-options="
                                                      
                                                       method:'get',
                                                       showGroup:true,
                                                        showHeader:false,
                                                        scrollbarSize:0
                                                      ">
                                                   </table>
                                                 </td>
                                                 <td>
                                                     <!-- url:'weiXinUserController.do?getYASNums', -->
                                                     <table id="tpg" class="easyui-propertygrid" style="width:150px;height:200px" data-options="
                                                       
                                                        method:'get',
                                                        showGroup:true,
                                                        showHeader:false,
                                                        scrollbarSize:0
                                                      ">
                                                      </table>
                                                   </td>
                                                    <td>
                                                       <!-- url:'weiXinUserController.do?getTSNums' -->
                                                         <table id="ttpg" class="easyui-propertygrid" style="width:150px;height:200px" data-options="
                                                              
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
                                       </div>
                                 </div>      
                                 
                                <h2 class="acc_trigger"><a href="#">分析统计</a></h2>
                                <div class="acc_container">
                                   <div class="block">
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
                             </div>
                              
                          </ul>
                  </div>
    
              </div>
        </div>
    </div>
    <div id="4" class="m2yw_pic hidden">
       <div id="tagsz">            
           <h3 class="z_brand_title" ><a class="z_brand_guochan"  href="javascript:void(0)">自定义菜单</a></h3>    
       </div>
       <br><br>
       <h2 style="text-color:gray;text-align:center">注意：主菜单最多三个，二级子菜单最多五个,复合类型菜单只需要填菜单项名！谨慎选择类型！</h2>
                 <script type="text/javascript">
                  var Types = [{ "type": "点击类型", "text": "点击类型" }, { "type": "链接类型", "text": "链接类型" }, { "type": "复合类型", "text": "复合类型" }];
                  </script>
                  <br>
                  <!-- url:'/WeChat3Platform/menuController.do?loadDiyMenu' -->
                   <table id="dgm" class="easyui-datagrid" title="菜单项" style="width:90.9%;height:80%"
                         data-options="singleSelect:true, toolbar: '#tbm',
                                       onClickRow: onClickRowm,collapsible:true,
                                       method:'get'">
                    <thead>
                    <tr>
                         <th data-options="field:'name',width:150,editor:'text'">菜单项名</th>
                          <th data-options="field:'type',width:150,editor:{type:'combobox',
                                                                          options:{
                                                                      valueField:'type',
                                                                      textField:'text',
                                                                       data:Types,
                                                                      required:true
                            }}">菜单项类型</th>
                         <th data-options="field:'key',width:200,editor:'text'">菜单项key(点击类型请填)</th>
                         <th data-options="field:'url',width:250,editor:'text'">菜单项url(链接类型请填)</th>                          
                         <th data-options="field:'pname',width:250,align:'right',editor:'text'">父菜单项名(非复合类型请填)</th>                        
                    </tr>
                    </thead>            
                </table>
                <div id="tbm" style="height:auto;margin-top:5px">
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendm()">添加</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeitm()">删除</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="acceptm()">保存</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="rejectm()">撤销</a>              
              </div>
              <br>
              <h2 style="color:red"  id="errmsg"></h2>
              <div style="margin:20px 30%;">
                       <a href="javascript:void(0)" class="easyui-linkbutton" onclick="getMenu()">远程加载公众号菜单</a>                                        
                       <a style="margin-left:60px" href="javascript:void(0)" class="easyui-linkbutton" onclick="generateMenu()">生成公众号菜单</a>                                        
                       <a style="margin-left:60px" href="javascript:void(0)" class="easyui-linkbutton" onclick="deleteMenu()">清空公众号菜单</a>                                                         
              </div>
    </div>
    <div id="5" class="m2yw_pic hidden">
       <div id="tagsw">            
           <h3 class="z_brand_title" ><a class="z_brand_guochan"  href="javascript:void(0)">关注回复</a></h3>    
       </div>
         <br>
         <div style="margin-left:48%;margin-bottom:20px">
                       <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg').dialog('open')">添加回复模板</a>
         </div>
         <!-- url:'/WeChat3Platform/subscribeController.do?loadResponse' -->
          <table id="dgs" class="easyui-datagrid" title="关注回复" style="width:90.5%;height:640px;margin-top:50px"
                         data-options="singleSelect:true,collapsible:true,method:'get'">
                  <thead>
                  <tr>
                         <th data-options="field:'no',width:200">模板编号</th>  
                          <th data-options="field:'type',width:300">模板类型</th>                                     
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
                </div>
    </div>
    <div id="6" class="m2yw_pic display">
       <div id="z_index_201208">
               <div class="clear" id="z_brand">
  
                   <div id="tags">
                         <h3 class="z_brand_title0_selectTag" id="z_brand_title0"><a class="z_brand_oumei" onmouseover="selectTag(this)" href="javascript:void(0)">文本消息模板</a></h3>          
                         <h3 class="z_brand_title2" id="z_brand_title2"><a class="z_brand_rihan" onmouseover="selectTag(this)" href="javascript:void(0)">图文消息模板</a></h3>
                   </div>
    
                  <div id="tagContent">
                         <ul class="z_brand_list_om clear" id="tagContent0" >
                                <!-- 文本消息模板 -->
                                 <table id="dg" class="easyui-datagrid" title="文本消息定制" style="width:100%;height:45%"
                                      data-options=" iconCls: 'icon-edit',autoRowHeight:true,singleSelect: true,toolbar: '#tb',
                                      url: '/WeChat3Platform/templateController.do?loadTextTemplate',method: 'get',onClickRow: onClickRow">
                                   <thead>
                                      <tr>
                                          <th data-options="field:'no',width:200">编号</th>
                                          <th data-options="field:'word',width:300,editor:'text'">关键字</th>                                          
                                          <th data-options="field:'content',width:300,editor:'textarea'">文本消息</th>      
                                          <th data-options="field:'date',width:140,formatter:function(value,row,index){
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
                              <br>
                               <!-- 修改默认未匹配文本 -->
                                <form id="moren" class="easyui-form" style="margin-top:0;margin-left:0px;background-color:black;width:1026px;height:330px" method="post">
                                    <table style="margin-left:270px" cellpadding="5">
                                        <thead>
                                           <tr>
                                                <th style="font-size:20px;height:80px;text-align:center">修改未匹配回复文本:</th>
                                            </tr>
                                        </thead>
                                         <tr>                                 
                                               <td><input id="defaultText" class="easyui-textbox"  style="width:500px;height:200px;" type="text" name="name" data-options="required:true"></input></td>
                                         </tr>             
                                   </table>
                              </form>
                              <div style="margin-left:37%;padding:5px">
                                   <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submit()">保存</a>
                                   <a style="margin-left:120px" href="javascript:void(0)" class="easyui-linkbutton" onclick="clear()">重设</a>
                             </div>         
                         </ul>          
                         <ul class="z_brand_list_rh clear" id="tagContent2" >
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
                    
                                              <div style="text-align:center;padding:5px">
                                                   <a id="11" href="javascript:void(0)" class="easyui-linkbutton" >提交</a>
                                                   <a  id="22" href="javascript:void(0)" class="easyui-linkbutton" >重设</a>
                                               </div>
                                          </div>
                                     </div>
                                     <!--  url: '/WeChat3Platform/templateController.do?loadNewsTemplate' -->
                                     <table id="dg1" class="easyui-datagrid" title="图文消息列表" style="width:91%;height:98%;overflow:scroll;"
                                          data-options=" iconCls: 'icon-edit',singleSelect: true,toolbar: '#tb1',collapsible:true,
                                           method: 'get'">
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
                         </ul>
                  </div>
    
              </div>
        </div>
    </div>
  </div>
  
</div>
</div>

<footer>
						<ul>
							<li><a style="color:gray" link="index.html">微剑首页</a></li>
							<li><a style="color:gray" link="index.html">关于微剑</a></li>
							<li><a style="color:gray"  link="index.html">进入网站</a></li>
							<li><a style="color:gray"  link="index.html">申请注册</a></li>
							<li><a style="color:gray" link="index.html">联系我们</a></li>
							<li><a style="color:gray"  link="index.html">功能展示</a></li>
						</ul>
						<p><small style="color:gray" >版权所有 浙江大学软件学院 Copyright 2014 <a style="color:gray" link="index.html">www.weijian.ccom</a> All Rights Reserved 浙ICP备05074421号</small></p>
						
</footer>
</div>
<script src="/WeChat3Platform/plug-in/js/main_display.js" language="javascript" type="text/javascript"></script>
<script src="/WeChat3Platform/plug-in/js/main_functional.js" language="javascript" type="text/javascript"></script>
</body>
</html>
