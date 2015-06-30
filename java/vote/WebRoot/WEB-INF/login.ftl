<#include "./common/const.ftl">

<#include "./common/doctype.ftl">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<#include "./common/meta.ftl">
	<base href="${path}">
	<title>${titlePrefix}${res("login.title")}</title>
	<#include "./common/css.ftl">
	<#include "./common/js.ftl">
</head>

<body>
	<form id="login" name="login" action="loginx.do" method="post">
		${res("login.user_name")}${res("common.colon")}<input type="text" name="userVO.userName" id="userName" />
		${res("login.password")}${res("common.colon")}<input type="password" name="userVO.password" id="password" />
		<input type="submit" value="${res("login.login")}" />
	</form>
</body>

</html>

<script type="text/javascript">
	$('#userName').focus();
</script>