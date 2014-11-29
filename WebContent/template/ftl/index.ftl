<!DOCTYPE html>
<html>
	<head>
		<title>微剑-你犀利的选择</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/demo/demo.css">
    <script type="text/javascript" src="/WeChat3Platform/plug-in/jquery-easyui-1.4/jquery.min.js"></script>
    <script type="text/javascript" src="/WeChat3Platform/plug-in/jquery-easyui-1.4/jquery.easyui.min.js"></script>
   </head>
<body>
      <h2>${title}</h2>
      <p>微剑-你犀利的选择</p>
      <div style="margin:20px 0;"></div>
      <div class="easyui-layout" style="width:700px;height:350px;">
        <div id="p" data-options="region:'west'" title="描述" style="width:30%;padding:10px">
            <p style="font-size:15px">${desc}</p>
        </div>
        <div style="font-size:15px" data-options="region:'center'" title="详细">
           ${content}
        </div>
    </div>
	
	
</body>
</html>