<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<!-- 
 struts1的登陆
 -->
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html"/>
		<title>首页</title>
	</head>
	
	<body>
		<!-- 
			form这么写实错误的。为什么？因为这样直接点击执行的是/struts1jsp/LoginAction.do
			也就是说，struts.xml中配置的Action是指向根目录的。
			<form action="/LoginAction.do" method="post">
		 -->
		<form action="<%=request.getContextPath()%>/LoginAction.do" method="post">
			name : <input type="text" name="name">
				<font color="red"> <html:errors property="name" /></font><br />	
			pass : <input type="passWord" name="passWord"/>
			<font color="red"><html:errors	property="passWord" /></font>
					<input type="submit" value="确定">
		</form>
	</body>
</html>