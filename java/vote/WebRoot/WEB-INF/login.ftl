<#include "./common/doctype.ftl">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<base href="${path}">
	<#include "./common/meta.ftl">
	<title></title>
	${res("login.name")}
</head>

<body>
    <form id="login" name="login" action="loginx.do" method="post">
		<input type="text" name="userVO.userName" id="userVO.userName" value="" />
		<input type="password" name="userVO.password" id="userVO.password" value="" />
		<input type="submit" />
	</form>
</body>

</html>

