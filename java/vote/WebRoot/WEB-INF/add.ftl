<#include "./common/const.ftl">

<#include "./common/doctype.ftl">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<#include "./common/meta.ftl">
	<base href="${path}">
	<title>${titlePrefix}${res("add_poll.title")}</title>
	
		
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
			}
			
			.btns {
				width:300px;
				margin:0 auto;
				background-color:white;
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
    <h1>add</h1>
	
</body>

</html>