<%@ page language="java" contentType="text/html; charset=utf-8" %>
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
			name : <input type="text" name="name"><br/>
			pass : <input type="passWord" name="passWord"/>
					<input type="submit" value="确定">
		</form>
	</body>
</html>