<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />

<html>
	<head>
		<title></title>
	</head>

	<body>
		<@s.form action="addAction" namespace="/request">
 			<@s.textfield name="name" label="name"/>
 			<@s.textfield name="age" label="age"/>
 			<@s.submit value="submit"/>
		</@s.form>
	</body>
</html>