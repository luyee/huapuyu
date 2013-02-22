<#include "./common/const.ftl">

<#include "./common/doctype.ftl">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<#include "./common/meta.ftl">
	<base href="${path}">
	<title>${titlePrefix}${res("add_poll.title")}</title>
	<#include "./common/js.ftl">
	<#include "./common/css.ftl">
		
		<!-- css -->
		<style type="text/css"><!--
			body {
				text-align:center;
				margin:0;
				font-size:12px; 
				font-family:"SimSun","宋体","Arial Narrow";
			}

			#container {
				width:100%;
				height:100%;
				margin:0 auto;
			}
			
			#top {
				float:left;
				width:100%;
				height:60px;
				margin:0;
				clear:both;
				background-color:#ef8c08;
			}
			
			#logo {
				float:left;
				width:100px;
				height:50px;
				margin:0;
				clear:both;
				background-image:url(<?php echo base_url();?>images/logo.png);
			}
			
			#sep {
				float:left;
				width:100%;
				height:50px;
				margin:0;
				clear:both;
			}
			
			#line {
				float:left;
				width:100%;
				height:2px;
				margin:0;
				clear:both;
				background-color:#1C94C4;
			}
			
			#center {
				float:left;
	   			width:100%;
				text-align:center;
	    		margin:0;
	  			clear:both;
			}
			
			.title {
				width:600px;
				margin:0 auto;
				background-color:white;
				border:0px solid black;
			}
			
			.items {
				width:600px;
				margin:0 auto;
				background-color:white;
				border:0px solid black;
			}
			
			.others {
				width:600px;
				margin:0 auto;
				background-color:white;
				border:0px solid black;
			}
			
			.btns {
				width:300px;
				margin:0 auto;
				background-color:white;
				border:0px solid black;
			}
			
			#title, #remark {
				width: 400px;
				height: 22px;
				font-size:16px; 
				font-family:"SimSun","宋体","Arial Narrow";
			}
			
			.item {
				width: 400px;
				height: 22px;
				font-size:16px; 
				font-family:"SimSun","宋体","Arial Narrow";
			} 
			p {
				font-weight: bold;
				color: #ef8c08;
			}
		--></style>
		
		<!-- js -->
		<script language="JavaScript" type="text/javascript">
			$(function() {
				modifyButtonStyle();
			});
	
			// 修改Button样式
			function modifyButtonStyle() {
				$("button").button({
		            icons: {
		                primary: "ui-icon-circle-minus"
		            }
		        });

				$("#newItem").button({
		            icons: {
		                primary: "ui-icon-circle-plus"
		            }
		        });

				$("#save").button({
		            icons: {
		                primary: "ui-icon-circle-check"
		            }
		        });
			}
		</script>
</head>

<body>
	<div id="container">
		<div id="top">
			<div id="logo"></div>
		</div>
		<div id="line"></div>
		<div id="sep"></div>
		
		<div id="center">
    		<form action="http://127.0.0.1:8000/poll/save/" method="post">
    			<#-- 标题 -->
    			<div class="title">
    				<table>
        				<tr>
            				<td><label for="title">标题：</label></td>
            				<td><input type="text" name="title" value="" id="title"/></td>
            				<td></td>
        				</tr>
        				<tr>
            				<td><label for="remark">备注：</label></td>
            				<td><input type="text" name="remark" value="" id="remark"/></td>
            				<td></td>
						</tr>
    				</table>
    			</div>
    			
    			<div id="sep"></div>
    			
    			<#-- 选项 -->
    			<div class="items">
    				<table id="items">
        				<tr id="item1">
            				<td>选项：</td>
            				<td><input type="text" name="itemTitle1" value="" id="itemTitle1" class="item"/></td>
            				<td><button name="itemBtn1" type="button" id="itemBtn1" onclick="deleteItem(1);">删除选项</button></td>
        				</tr>
        				<tr id="item2">
            				<td>选项：</td>
           					<td><input type="text" name="itemTitle2" value="" id="itemTitle2" class="item"/></td>
            				<td><button name="itemBtn2" type="button" id="itemBtn2" onclick="deleteItem(2);">删除选项</button></td>
        				</tr>
        				<tr id="item3">
            				<td>选项：</td>
            				<td><input type="text" name="itemTitle3" value="" id="itemTitle3" class="item"/></td>
            				<td><button name="itemBtn3" type="button" id="itemBtn3" onclick="deleteItem(3);">删除选项</button></td>
        				</tr>
				        <tr id="item4">
				            <td>选项：</td>
				            <td><input type="text" name="itemTitle4" value="" id="itemTitle4" class="item"/></td>
				            <td><button name="itemBtn4" type="button" id="itemBtn4" onclick="deleteItem(4);">删除选项</button></td>
				        </tr>
    				</table>
    			</div>
    			
    			<div id="sep"></div>
    			
    			<#-- 其他 -->
    			<div class="others"></div>
    			
    			<div id="sep"></div>
    			
    			<#-- 按钮 -->
    			<div class="btns">
				    <input type="hidden" name="itemMax" id="itemMax" value="4"/>
				    <input type="hidden" name="itemIds" id="itemIds" value="1,2,3,4"/>
				    <button name="newItem" type="button" id="newItem">添加新选项</button>
				    <input type="submit" name="submit" value="保存"/>
				</div>
			</form>
		</div>
	</div>
</body>

</html>
<!-- js -->
		<script language="JavaScript" type="text/javascript">
  			$('#newItem').click(function() {
  				var itemMax = $("#itemMax").val();
  				itemMax = parseInt(itemMax) + 1;
  				var itemIds = $("#itemIds").val();
  				addItem(itemMax);
  				$("#itemIds").val(itemIds + "," + itemMax);
  				$("#itemMax").val(itemMax);
  				modifyButtonStyle();
			});

			function addItem(num) {
				var inputCount = $("form input:text").length;
				if (inputCount < 12) {
					$('#items').append(
						'<tr id="item'+num+'">' +
							'<td>选项：</td>' +
							'<td><input type="text" name="itemTitle'+num+'" id="itemTitle'+num+'" class="item"/></td>' +
							'<td><button name="itemBtn'+num+'" type="button" id="itemBtn'+num+'" onclick="deleteItem('+num+');" >删除选项</button></td>' +
						'</tr>'
					);
				}
				else {
					alert("同学，需要这么多个选项吗？");
					return;
				}
			}

			function deleteItem(num) {
				var inputCount = $("form input:text").length;
				if (inputCount > 4) {
					$('#item' + num).remove();
					var itemIds = $("#itemIds").val();
					var ids = itemIds.split(",");
					ids = removeElementOfArray(num, ids);
					var newIds = ids.join(",");
					$("#itemIds").val(newIds);
				}
				else {
					alert("同学，至少要有两个选项哦！");
					return;
				}
			}

			function removeElementOfArray(value, arr) {  
		        var index = $.inArray(value.toString(), arr);
				for (var i = index; i < arr.length; i++)  
					arr[i] = arr[i + 1];      
				arr.length = arr.length - 1;      
		        return arr;  
		    }
		</script>