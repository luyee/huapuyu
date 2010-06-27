<%@ page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!-- 
<%@ taglib uri="/tag/first.tld" prefix="first"%>
<%@ taglib uri="/tag/TagHaveProperty.tld" prefix="second"%>
<%@ taglib uri="/tag/TagHaveBody.tld" prefix="third"%>
 -->
<!-- 这边显示出错很正常，由于web.xml没有配置的缘故 还是先注释了 -->
<%
	List<String> list = new ArrayList<String>();
	for(int i=0;i<10;i++){
		list.add("wo shi "+i);
	}
	pageContext.setAttribute("list",list);
%>
<!-- 
    JSTL 是SUN提供的标签库http://baike.baidu.com/view/73527.htm?fr=ala0_1_1
    DisplayTag 是Apache下的开源标签库 http://www.javaeye.com/topic/24869
           有个事情要注意一下对于标签tld的文档格式定义。
    , Inc.//D  这个Inc前面一定有空格否则报错。（原因是:老外规定的）
 -->

<html>
<head>
	<title>自定义标签</title>
</head>
<body>
	标签输出的内容是<br/>
	<font size="3" face="arial" color="red">标签一：无参数无标签体</font><br/>
	<first:hello />	
	<br/>
	<br/>
	<font size="3" face="arial" color="red">标签二：有参数无标签体</font><br/>
	<second:property name="朱振" pass="羊肉串"/>
	<br/>
	<br/>
	<font size="3" face="arial" color="red">标签三：有参数有标签体</font><br/>
	<table border="1" bordercolor="dddd99">
		<third:iterator bean="list" item="item">
			<tr>
				<td>
					<third:iteratorSun item="item"></third:iteratorSun>
				</td>
			</tr>
		</third:iterator>
	</table>
	<br/>
	<br/>
	
</body>
</html>

