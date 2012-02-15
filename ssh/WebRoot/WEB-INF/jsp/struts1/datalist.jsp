<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="/WEB-INF/struts1/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts1/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts1/struts-logic.tld" prefix="logic"%>
<html:html>
<head>
	<title>Struts1 Test</title>
	<html:base />
</head>

<body>
	<html:errors/>
	<html:form action="/dataList" >
	<table>
		<tr>
			<td>
				<bean:message key="data.name" /><html:text property="name" maxlength="20" />
			</td>
			<td>
				<html:submit value="²éÑ¯" />
			</td>
		</tr>
	</table>
	</html:form>
</body>
</html:html>