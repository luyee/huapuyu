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
  		<table style="width:100%;" cellpadding="0" cellspacing="0" border="1px">
			<tr>
				<th><s:text name="data.id"/></th>
				<th><s:text name="data.name"/></th>
				<th><s:text name="data.update"/></th>
				<th><s:text name="data.delete"/></th>
			</tr>
			
			<s:iterator value="dataList">
			<tr>
				<td><s:property value="id"/></td>
				<td><s:property value="name"/></td>
				<td>
					<s:url id="updateData" action="updateInput" namespace="/data"><s:param name="id" value="%{id}"/></s:url>
					<s:a href="%{updateData}"><s:text name="data.update"/></s:a>
				</td>
				<td>
					<s:url id="deleteData" action="delete" namespace="/data"><s:param name="id" value="%{id}"/></s:url>
					<s:a href="%{deleteData}"><s:text name="data.delete"/></s:a>
				</td>
			</tr>
			</s:iterator>
		</table>
  </body>
</html>
