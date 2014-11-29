<!DOCTYPE html>
<html>
	<head>
		<title>${cmsData.title}</title>		
	 <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/demo/demo.css">
    <script type="text/javascript" src="/WeChat3Platform/plug-in/jquery-easyui-1.4/jquery.min.js"></script>
    <script type="text/javascript" src="/WeChat3Platform/plug-in/jquery-easyui-1.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/WeChat3Platform/plug-in/json2.js"></script>
	    <style type="text/css">
	    body {
			padding: 0;
			font-family: '宋体';
			background: #fff;
		}
	    .title{
		    display: block;
			font-size: 2em;
			-webkit-margin-before: 0.67em;
			-webkit-margin-after: 0.67em;
			-webkit-margin-start: 0px;
			-webkit-margin-end: 0px;
			font-weight: bold;
			text-align:center;
	    }
	    .activity-info {
			border-top: 1px dotted #ccc;
			text-align:center;
	    }
	    .activity-meta {
			display: inline-block;
			line-height: 16px;
			vertical-align: middle;
			text-align:center;
			margin-left: 8px;
			padding-top: 2px;
			padding-bottom: 2px;
			color: #8c8c8c;
			font-size: 11px;
		}
		.content{
			width: 95%;
			margin: 0 auto;
			padding: 5px 5px;
		}
	    </style>
	</head>
	<body>
		<header class="w-header" mon="type=header">
			<a class="arrow-wrap" href="javascript:history.back()" mon="content=back">
			<span class="arrow-left"></span>
			</a>
			<div class="text">${cmsData.title}</div>
		</header>
		<div class="detail-area bulk_order_details">
			<div class="title">${cmsData.article.title }</div>
			<div class="text"><img width="100%" src="${cmsData.article.imageHref }"/></div>
			<div class="activity-info">
				<span id="post-date" class="activity-meta no-extra">${cmsData.article.createDate?string("yyyy-MM-dd HH:mm:ss") }</span>
			</div>
			<div class="content">${cmsData.article.content }</div>
		</div>
	</body>
</html>