<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="my" uri="http://ssh.com/myfunction" %>
<%@ taglib prefix="tag" uri="http://ssh.com/mytag" %>
<%@ taglib prefix="tagFile" tagdir="/WEB-INF/tags"%><!-- 一定注意这个地方，他要的是根目录，我还以为是整个文件的地址呢, 而且一定要是这个目录 -->

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
	List record = new ArrayList();
	for(int i=0;i<10;i++){
		record.add("wo shi "+i);
	}
	pageContext.setAttribute("record",record);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	test2输出${1+2} 
	<%out.print("222"); %><br/>
	${my:reverse ("！力努好好后以 ")}<br/>
	
	<br/>
	<tagFile:jspTag one="#00FA9A" two="我是jsp2的tag file 我很强大"></tagFile:jspTag>
</body>
</html>