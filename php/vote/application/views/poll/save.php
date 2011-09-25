<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title>投票--创建新投票</title>
	</head>
	
	<body>
		<?php
		echo form_open('poll/save');
		echo form_label('标题：', 'title');
		$title = array('name' => 'title', 'id' => 'title');
		echo form_input($title);
		echo form_submit('submit', '提交保存');
		echo form_close();
		?>	
	</body>
</html>
