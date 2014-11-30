/*ckeditor
 * */
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
/*
 *自动回复 文本消息模板 
 **/
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
   /*
    * 自动回复 修改未匹配回复文本
    * */
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
  /*
   * 自动回复 图文消息模板 表单
   */
       var op;
       $('#file').filebox({
    	    buttonText: '选择文件'               	  
    	})
    	$("#11").click(
          function submitForm5(){
        	  CKupdate();
    	       if(op){
        	       alert("添加");
                   $('#ff6').form('submit',{
                    url:"/WeChat3Platform/templateController.do?addNewsTemplate",
                   success:function(data){
                	   var n=eval(data);
                	  $.messager.alert("提醒",n);
                	  $("#dg1").datagrid({
             	    	 url: '/WeChat3Platform/templateController.do?loadNewsTemplate'
                       }).datagrid("load");
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
                    	$("#dg1").datagrid({
                	    	 url: '/WeChat3Platform/templateController.do?loadNewsTemplate'
                          }).datagrid("load");
                        $("#dlg6").dialog('close');
                    }
                    });


        	       }
          });
       $("#22").click(
          function clearForm5(){
                 $('#ff6').form('clear');
          });
       /*
        * 自动回复 图文消息模板 datagrid
        */
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
        /*
         * 关注回复 对话框
         */
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
                        $("#dgs").datagrid({
                        	url:'/WeChat3Platform/subscribeController.do?loadResponse'
                        }).datagrid("load");
                       }
                      });
            }
            function clears(){
                   $('#ff').form('clear');
            }
 /*
  * 自定义菜单 按钮方法
  */
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
          /*
           * 自定义菜单 datagrid
           */
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
  			                $("#dgm").datagrid({
  			                  url:'/WeChat3Platform/menuController.do?loadDiyMenu'
  			               }).datagrid("load");
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
 /*
  * 用户管理 分组管理 对话框表单
  */
       var opu;                             	
       function submitu(){                    	 
 	       if(opu){
     	       alert("添加");
                $('#ffu').form('submit',{
                 url:"/WeChat3Platform/weiXinUserController.do?addGroup",
                success:function(data){
             	   var n=eval(data);
             	  $.messager.alert("提醒",n);
             	 $("#dgu").datagrid({
                     url:'/WeChat3Platform/weiXinUserController.do?loadRemoteGroup',
                  }).datagrid("load"); 
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
                 	 $("#dgu").datagrid({
                         url:'/WeChat3Platform/weiXinUserController.do?loadRemoteGroup',
                      }).datagrid("load");
                 	 
                     $("#dlgu").dialog('close');
                 }
                 });
     	       }
       }                  
       function clearu(){
              $('#ffu').form('clear');
       }
  /*
   * 用户管理 分组管理 datagrid和表单的联动
   */
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
/*
 * 分组管理 移动分组
 */
    function submitForm0(){
        $('#ff0').form('submit',{
          url:"/WeChat3Platform/weiXinUserController.do?moveUserToGroup",
          dataType:"text",
          success:function(data){
        	  $("#dgu").datagrid({
                  url:'/WeChat3Platform/weiXinUserController.do?loadRemoteGroup',
               }).datagrid("load");
        	setTimeout("reload()",100);     	 
            $('#dlg0').dialog('close');
            $.messager.alert("提醒",data);
              }
            });
  }
  function reload(){
	  $("#dgul").datagrid({
	    	 url:'/WeChat3Platform/weiXinUserController.do?loadWeiXinUser'
     }).datagrid("load");
  }
  function clearForm0(){
         $('#ff0').form0('clear');
  }
  /*
   * 用户分析 关注分析 线图
   */
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
  /*
   * 分类统计 语言分析
   */
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
  /*
   * 分类统计 省份分析
   */
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
  /*
  分类统计 性别分析
  */
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
  /*
   * 群发消息 加载组号
   */
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