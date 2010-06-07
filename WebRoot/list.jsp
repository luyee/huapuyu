<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Tb_user|List</title>
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
  	<a href="Tb_useradd">添加</a>
  	<br/>
   	<s:iterator value="users" var="u">
   		<s:property value="#u.name"/> |
		<s:property value="#u.pwd"/> |
		<a href="Tb_userdelete?id=<s:property value="#u.id"/>">删除</a> |
		<a href="Tb_userupdateInput?id=<s:property value="#u.id"/>">更新</a>
		<br/>
   	</s:iterator>
   	<s:debug></s:debug>
  </body>
</html>
