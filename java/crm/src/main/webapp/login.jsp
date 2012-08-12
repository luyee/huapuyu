<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">

    <title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>

  <body>
  	<h1>123</h1>
  	<%
		if (session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null) {
	%>
	登录失败，请重试。
	<%
		}
	%>
	
	<form id="j_spring_security_check" name="j_spring_security_check" action="j_spring_security_check" method="post">
		<input type="text" name="j_username" id="j_spring_security_check_j_username" />
		<input type="password" name="j_password" id="j_spring_security_check_j_password" />
		<input type="submit" />
	</form>
  </body>
</html>
