<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib uri="/WEB-INF/struts1/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts1/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts1/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts1/struts-nested.tld" prefix="nested"%>
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
		<nested:present name="dataList" scope="request">
			<nested:iterate id="dataList" name="dataList" type="com.anders.ssh.model.xml.Data" indexId="index">
					<nested:hidden name="dataList" property="id" indexed="true"></nested:hidden>
					<nested:text name="dataList" property="name" indexed="true"></nested:text><br/>
			</nested:iterate>
		</nested:present>
	</html:form>
</body>
</html:html>