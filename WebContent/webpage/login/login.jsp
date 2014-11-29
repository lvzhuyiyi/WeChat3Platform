<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>微剑-微信第三方平台</title>	
	<!--html5中可以没有type属性申明-->
	<script src="/WeChat3Platform/plug-in/js/html5.js"></script>
	<script  src="/WeChat3Platform/plug-in/js/jquery-2.1.1.js" ></script>
	<script  src="/WeChat3Platform/plug-in/js/jquery.cookie.js" ></script>
	<script src="/WeChat3Platform/plug-in/js/atooltip.jquery.js"></script>
	<script  src="/WeChat3Platform/plug-in/js/kwicks-1.5.1.pack.js"></script>
	<script  src="/WeChat3Platform/plug-in/js/script.js"></script>
	<script  src="/WeChat3Platform/plug-in/js/jquery.form.js"></script>
	<script  src="/WeChat3Platform/plug-in/js/jquery.validate.js"></script>
	<script  src="/WeChat3Platform/plug-in/js/jquery.metadata.js"></script>
	<script type="text/javascript" src="/WeChat3Platform/plug-in/js/jquery-ui-1.10.4.custom.min.js"></script>
	<script type="text/javascript" src="/WeChat3Platform/plug-in/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/WeChat3Platform/plug-in/jquery-easyui-1.4/demo/demo.css">
	<link href="/WeChat3Platform/plug-in/css/reset.css" rel="stylesheet"   media="all"/>
	<link href="/WeChat3Platform/plug-in/css/footer.css" rel="stylesheet"   media="all"/>
	<link href="/WeChat3Platform/plug-in/css/layout.css" rel="stylesheet"   media="all"/>
	<link href="/WeChat3Platform/plug-in/css/index.css" rel="stylesheet"   media="all"/>
</head>
<body id="index">
	<div class="body1">
		<div class="body2">
			<div class="main">
				<header>
						<!--<a href="index.html" id="logo" ><p><span id="wei">微</span><span id="jian">剑</span></p><p id="xili">——你犀利的选择</p></a>-->
						<h1><a href="index.html" id="logo">微剑——你犀利的选择</a></h1>
				</header>
				<section id="content">
					<div class="inner">	
						<ul id="menu">
						<!--page_1开始-->
							<li id="page_1">
								<div class="text">网站简介</div>
								<div class="cont">
									<h2 class="cont_top1">微剑-移动互联网营销的引领者</h2>
									<p class="cont_top1">微剑隶属于浙江大学软件学院，中国最大的微信开发服务商。从2013年4月发展至今，凭借着丰富的产品功能、全新的产品设计、快速的产品创新、超出用户期待的产品价值，基于微信为企业提供开发、运营、培训、推广一体化解决方案，帮助企业实现线上线下互通（O2O），社会化客户关系管理（SCRM），移动电商（Vshop），轻应用（lightapp）WMAPP等多个层面的业务开发，仅以一年时间，成为微营销的翘楚。2014年2月，央视财经频道全面报道微剑，奠定了微剑作为国民微信服务开发商的地位。</p>
									<h2 class="cont_top1">微剑合作客户</h2>
									<p class="cont_top1">截止2014年8月，入驻微剑商户数已突破55万。市场需求日益旺盛，微信已成为移动互联网最大入口。微剑涉及电商、餐饮、汽车、房产、医疗、旅游等多个行业。2014年，微剑将改变商业、颠覆商业业态！</p>
								</div>
							</li>
							<!--page_1结束。page_2开始-->
							<li id="page_2">
								<div class="text">登录</div>
								<div class="cont">
									<form id="loginForm" action="#" method="post"> 
										<h2 class="pad_top1">欢迎你加入微剑，开启你犀利的人生！</h2>
										<section class="control-group">
											<label class="control-label" for="username">用户名：</label>
											<input type="text" id="userName" name="username" required placeholder="请输入用户名"/>
										</section>
										<section class="control-group">
											<label class="control-label" for="password">密码：</label>
											<input type="password" id="password" name="password" required placeholder="请输入密码" />
										</section>
							             <section class="control-group">
											<label class="control-label" for="page3_captcha">验证码：</label>
											<input type="text" id="page3_captcha" name="randCode" required placeholder="请输入验证码"/>
											<span class="star">*</span>
											<span id="show-captcha">
											<img width="60" height="30" id="randCodeImage" src="/WeChat3Platform/randCodeImage" title="看不清?点击切换下一张"/>
											</span>
											<div style="margin-left:20%;margin-top:5px;">
                                                <input type="checkbox" id="on_off" name="remember" checked="true"  value="0" ><label for="on_off">是否记住用户名</label></input>      
                                            </div>
										</section>
										<section class="control-group" ">
										   
											<label class="control-label" for="page2_submit"></label>
											<button type="submit"  id="page2_submit">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
											<p id="submit1">
											<label class="control-label" for="page2_link"></label>
											<a href="#" id="page2_link">新用户注册</a><a href="#">忘记密码？</a>
											</p>
										</section>
									</form>
								</div>
							</li>
							<!--page_2结束。page_3开始-->
							<li id="page_3" >
								<div class="text">用户注册</div>
								<div class="cont">
									<form id="registerForm" action="#" method="post"> 
										<h2 class="pad_top1">欢迎你加入微剑，开启你犀利的人生！</h2>
										<section class="control-group">
											<label class="control-label" for="page3_userName">用户名：</label>
											<!--html5中支持required 为必填属性 pattern为匹配模式 autofocus为自动获取焦点-->
											<input type="text" id="page3_userName" name="username"  required="required" pattern="" placeholder="请输入用户名"/>
											<span class="star">*</span>
										</section>
										<section class="control-group">
											<label class="control-label" for="page3_password1" align="right">设置密码：</label>
											<input type="password" id="page3_password1" name="password" required placeholder="请输入密码"/>
											<span class="star">*</span>
										</section>
										<section class="control-group">
											<label class="control-label" for="page3_password2">确认密码：</label>
											<input type="password" id="page3_password2" name="repass" required placeholder="请再次输入密码"/>
											<span class="star">*</span>
										</section>		
										<section class="control-group">
											<label class="control-label" for="page3_email">邮箱：</label>
											<!--type="email"可以用来验证邮箱格式-->
											<input type="email" id="page3_email" name="email" placeholder="请输入邮箱" required/>
											<span class="star">*</span>
										</section>
										<section class="control-group">
											<label class="control-label" for="page3_submit"></label>
											<button type="submit" id="page3_submit" name="page3_submit" >提&nbsp;&nbsp;&nbsp;&nbsp;交</button>
										</section>
									</form>
								</div>
							</li>
							<!--page_3结束。page_4开始-->
							<li id="page_4">
								<div class="text">功能展示</div>
								<div class="cont">
									<h2 class="pad_top1">欢迎你加入微剑，开启你犀利的人生！</h2>
									<section id="function">
										<ul id="nav_list1">
											<li class="left" id="index_11"><a href="webpage/login/introduce.jsp">群发功能</a></li>
											<li class="right" id="index_12"><a href="webpage/login/introduce.jsp">自动回复</a></li>
											<li class="left" id="index_13"><a href="webpage/login/introduce.jsp">消息管理</a></li>
											<li class="right" id="index_14"><a href="webpage/login/introduce.jsp">用户管理</a></li>
											<li class="left" id="index_15"><a href="webpage/login/introduce.jsp">素材管理</a></li>
											<li class="right" id="index_16"><a href="webpage/login/introduce.jsp">自定义菜单</a></li>
										</ul>
										<ul id="nav_list2">
											<li id="index_21" class="left"><a href="webpage/login/introduce.jsp">微网站</a></li>
											<li id="index_22" class="left"><a href="webpage/login/introduce.jsp">微贺卡</a> </li>
											<!--右浮动式按html顺序执行，所以24在前，23在后-->
											<li id="index_24" class="right"><a href="webpage/login/introduce.jsp">更多功能</a></li>
											<li id="index_23" class="right"><a href="webpage/login/introduce.jsp">微信墙</a></li>	
										</ul>
									</section>
								</div>
							</li>
							<!--page_4结束。page_5开始-->
							<li id="page_5">
								<div class="text">联系我们</div>
								<div class="cont">
									<h2 >欢迎你加入微剑，开启你犀利的人生！</h2>
									<section>
											<p ><span>电话：</span>188888888888</p>
											<p><span>QQ	:</span>1111100000</p>
											<p><span>邮箱：</span>jhyyy@163.com</p>
											<p><span>微信号：</span>jjjjjjj12345</p>
											<p><span>地址：</span>浙江省宁波市江南路1689号浙江大学软件学院</p>
											<p><span id="last">微信二维码：</span><img src="/WeChat3Platform/plug-in/images/weixinma.jpg" width="20%"/>记得要扫一下我呀！</p>		
									</section>
									<a href="webpage/login/introduce.jsp">进入网站<img src=""/> </a>
								</div>							
							</li>
						</ul>
					</div>
				</section>
				<footer>
						<ul>
							<li><a link="index.html">微剑首页</a></li>
							<li><a link="index.html">关于微剑</a></li>
							<li><a link="index.html">进入网站</a></li>
							<li><a link="index.html">申请注册</a></li>
							<li><a link="index.html">联系我们</a></li>
							<li><a link="index.html">功能展示</a></li>
						</ul>
						<p><small>版权所有 浙江大学软件学院 Copyright 2014 <a link="index.html">www.weijian.ccom</a> All Rights Reserved 浙ICP备05074421号</small></p>
						
				</footer>
			</div>
		</div>
	</div>
	<script  src="/WeChat3Platform/plug-in/js/login.js"></script>
</body>
</html>