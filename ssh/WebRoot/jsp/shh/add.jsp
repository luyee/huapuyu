<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx" %>
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

	<sx:head/>
  </head>
  
  <body>
  	<s:form action="addAction" namespace="/shh">
	  <s:textfield name="serviceRequest.content" key="request.add.content"/>
	  <s:textfield name="serviceRequest.remark" key="request.add.remark"/>
	  <s:select list="priorityList" listKey="id" listValue="name" name="priorityId" key="request.add.priority" />  
	  <sx:datetimepicker name="serviceRequest.timeLimit" key="request.add.time.limit" displayFormat="yyyy-MM-dd"/>
	  <s:submit key="request.add.save" method="addAction"/>
	  <s:submit key="request.add.create.work.order" method="createWorkOrder"/>
	  <s:submit key="request.add.create.service.activity" method="createServiceActivity"/>
	</s:form>
  </body>
</html>
