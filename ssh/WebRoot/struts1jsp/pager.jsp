<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//String url = (String)request.getAttribute("url");
String url = "SubmitAction.do?method=retrieve";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script language="JavaScript" type="text/javascript">
		function jump(pageSize){
    		 var startIndex = document.getElementById("toPage").value;
    		 window.location.href="SubmitAction.do?method=retrieve&startIndex="+(startIndex-1)*pageSize +"&pageSize="+pageSize;
    		 //form = document.forms[0];
    		 //form.action = "<%=url%>&"&startIndex=" + (startIndex-1)*10;
    		 //form.submit();
		}
		function huiche(e){
			if(e == 13 || e == 32)
			{
				var select = document.getElementById("select").value;
				jump(select);
			}			
		}
		function change(){
			var select = document.getElementById("select").value;
			window.location.href="SubmitAction.do?method=retrieve&pageSize="+select;
		}
		function onSubmit(){
			if(sent) {
 				return false;
			}else{
 				sent=true;
 				return true;
			}
		}
		/*
		function toJump(startIndex){
    		form = document.forms[0];
    		form.action = "<%=url%>&"&startIndex=" + startIndex;
    		form.submit();
		}
		*/
    </script>
    <title>My JSP 'pager.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <html:form action="SubmitAction.do?method=retrieve" method="post" >
		
  	<logic:iterate id="items" name="pager" indexId="index" property="itemList">
  		<td><bean:write name="items"/>   </td><br/>
  	</logic:iterate>
  	<!-- 
    <a onclick="toJump(0)">首页</a>
    <a onclick="toJump('<bean:write name="pager" property="preStartIndex" />')">上一页</a>  
    <a onclick="toJump('<bean:write name="pager" property="nextStartIndex" />')">下一页</a> 
    <a onclick="toJump('<bean:write name="pager" property="endStartIndex" />')">尾页</a>
    <a onclick="toPage()">跳转至</a><input type="text" id="toPage" size="5"><br>
     -->
     <html:select property="pageSize" onchange="change()" styleId="select">
     	<html:option value="10">10</html:option>
     	<html:option value="20">20</html:option>
     	<html:option value="30">30</html:option>
     	<html:option value="40">40</html:option>
     	<html:option value="50">50</html:option>
     </html:select>
     <a href="SubmitAction.do?method=retrieve&startIndex=0&pageSize=<bean:write name="pager" property="pageSize"/>">首页</a>
    <a href='SubmitAction.do?method=retrieve&startIndex=<bean:write name="pager" property="preStartIndex"/>&pageSize=<bean:write name="pager" property="pageSize"/>'>上一页</a>  
    <a href='SubmitAction.do?method=retrieve&startIndex=<bean:write name="pager" property="nextStartIndex"/>&pageSize=<bean:write name="pager" property="pageSize"/>'>下一页</a> 
    <a href='SubmitAction.do?method=retrieve&startIndex=<bean:write name="pager" property="endStartIndex"/>&pageSize=<bean:write name="pager" property="pageSize"/>'>尾页</a>
    <input type="button" name="sub" value="确定" onclick="jump('<bean:write name="pager" property="pageSize"/>')" size="3"><input type="text" id="toPage" size="3" onkeydown="huiche(event.keyCode||event.which)"><br>
    当前页是<bean:write name="pager" property="currentPage"/>
 	</html:form>
  </body>
  
</html>
