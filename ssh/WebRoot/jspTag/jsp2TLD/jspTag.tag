<!-- 可以引用其他的tag -->
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="one"%>
<%@ attribute name="two"%>

<table border="1" bgcolor="${one}" bordercolor="000000">
	<tr>
		<td><b>${two}</b></td>	
	</tr>
</table>