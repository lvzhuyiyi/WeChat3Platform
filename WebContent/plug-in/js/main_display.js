
/*
 * 主tab 并打开tab时才加载数据
 * */
var visit=[true,true,true,true,true,true
           ];
function changeTab(m,n){
	//alert("n="+n);
    var menu=document.getElementById("tab"+m).getElementsByTagName("li");  
    var div=$(document.getElementById("tablist"+m)).children("div[class!=bar]");
    for(i=0;i<menu.length;i++)
    {
	  menu[i].className=i==(n-1)?"m2yw_cutli":"";
	  div[i].style.display=i==(n-1)?"block":"none";  
    }
    if(n==5&&visit[4]){   	
    		//alert(options+":"+visit[4]);
        $("#dgs").datagrid({
        	url:'/WeChat3Platform/subscribeController.do?loadResponse'
        }).datagrid("load");
        visit[4]=false;
        //alert(":"+visit[4]);
    }else  if(n==4&&visit[3]){   	
         $("#dgm").datagrid({
            url:'/WeChat3Platform/menuController.do?loadDiyMenu'
         }).datagrid("load");
        visit[3]=false;
    }else  if(n==3&&visit[2]){   	
          $("#dgu").datagrid({
              url:'/WeChat3Platform/weiXinUserController.do?loadRemoteGroup',
           }).datagrid("load");
          visit[2]=false
     }
   
}
/*
 * AutoResponse 纵向Tab
 * */
var btnJson = [ { changeId: "z_brand_title0", selectId: "tagContent0" }, 
		       
		   	    { changeId: "z_brand_title2", selectId: "tagContent2"}
		   	    ];
visita=true;
function selectTag(x) {
  for (var i = 0; i < btnJson.length; i++) {
	if (btnJson[i].changeId == x.parentNode.id) {
	  document.getElementById(x.parentNode.id).className = x.parentNode.id + "_selectTag";
	  document.getElementById(btnJson[i].selectId).style.display = "block";
	}
	else {
	  document.getElementById(btnJson[i].changeId).className = btnJson[i].changeId;
	  document.getElementById(btnJson[i].selectId).style.display = "none";
	}
	if(x.parentNode.id=="z_brand_title2"&&visita){	
	     $("#dg1").datagrid({
	    	 url: '/WeChat3Platform/templateController.do?loadNewsTemplate'
          }).datagrid("load");
         visita=false;
	}
  }
}
/*
 * 用户管理 纵向Tab
 * */
var btnJson1 = [ { changeId: "z_brand_title3", selectId: "tagContent3" }, 
		        { changeId: "z_brand_title4", selectId: "tagContent4" }, 
		   	    { changeId: "z_brand_title5", selectId: "tagContent5"}];
visitb=[true,true]
function selectTag1(x) {
  for (var i = 0; i < btnJson1.length; i++) {
	if (btnJson1[i].changeId == x.parentNode.id) {
	  document.getElementById(x.parentNode.id).className = x.parentNode.id + "_selectTag";
	  document.getElementById(btnJson1[i].selectId).style.display = "block";
	}
	else {
	  document.getElementById(btnJson1[i].changeId).className = btnJson1[i].changeId;
	  document.getElementById(btnJson1[i].selectId).style.display = "none";
	}
  }
  if(x.parentNode.id=="z_brand_title4"&&visitb[0]){	
	     $("#dgu2").datagrid({
	    	 url:'/WeChat3Platform/weiXinUserController.do?query'
       }).datagrid("load");
	     setTimeout("reload2()",100);
	     visitb[0]=false;
	}else if(x.parentNode.id=="z_brand_title5"&&visitb[1]){
			$('#ypg').propertygrid({
				url:'weiXinUserController.do?getYSNums'  
			});
			$('#cpg').propertygrid({
				url:'weiXinUserController.do?getYUSNums' 
			});
			setTimeout("loadt()",100);
			$('#ttpg').propertygrid({
				url:'weiXinUserController.do?getTSNums'
			});
			 
		     visitb[1]=false;
	}
}
function reload2(){
	 $("#dgul").datagrid({
    	 url:'/WeChat3Platform/weiXinUserController.do?loadWeiXinUser'
   }).datagrid("load");
}
function loadt(){
	$('#tpg').propertygrid({
		 url:'weiXinUserController.do?getYASNums' 
	});
}
/*
 * 微官网 纵向Tab
 * */
var btnJson2 = [ { changeId: "z_brand_title6", selectId: "tagContent6" }, 
                 { changeId: "z_brand_title7", selectId: "tagContent7" }, 
                 { changeId: "z_brand_title8", selectId: "tagContent8" }, 
                 { changeId: "z_brand_title9", selectId: "tagContent9" }, 
                 { changeId: "z_brand_title10", selectId: "tagContent10" }, 
                 { changeId: "z_brand_title11", selectId: "tagContent11" }, 
                 { changeId: "z_brand_title12", selectId: "tagContent12" }, 
		   	    { changeId: "z_brand_title13", selectId: "tagContent13"}
		   	    ];
visitw=[true];
function selectTag2(x) {
  for (var i = 0; i < btnJson2.length; i++) {
	if (btnJson2[i].changeId == x.parentNode.id) {
	  document.getElementById(x.parentNode.id).className = x.parentNode.id + "_selectTag";
	  document.getElementById(btnJson2[i].selectId).style.display = "block";
	}
	else {
	  document.getElementById(btnJson2[i].changeId).className = btnJson2[i].changeId;
	  document.getElementById(btnJson2[i].selectId).style.display = "none";
	}
	if(x.parentNode.id=="z_brand_title2"&&visita){	
	    
	}
  }
}
  /*
   * 主菜单控制
   * */
  $("#top1_benefit").hover(
			function(){
				var offset = $(this).offset();
				var intLft = offset.left;
				var intTop = offset.top;
				$(".menuSub").css({"left":intLft,"top":intTop});
				$(".menuSub").show();
			}
		  );
		  $(".menuSub").hover(function(){},function(){$(this).hide();});
/*
 * 用户分析accordion
*/
		  $(document).ready(function(){	  
			  $('.acc_container').hide(); 
			  $('.acc_trigger:first').addClass('active').next().show(); 
			  $('.acc_trigger').click(function(){
				if( $(this).next().is(':hidden') ) { 
				  $('.acc_trigger').removeClass('active').next().slideUp(); 
				  $(this).toggleClass('active').next().slideDown(); 
				}
				return false; 
			  });  
			});
		