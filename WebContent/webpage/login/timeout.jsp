<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;   
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
  <head>  
      <title>用户失效</title>  
  </head>  
<body>  
你的登录已经失效，请重新登录。   
<br />  
<a href="/WeChat3Platform/webpage/login/login.jsp" >  
点击这里登录</a>  
</body>  
</html>  