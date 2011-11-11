<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><?php echo lang('poll_save_title');?></title>
		<script src="<?php echo base_url();?>js/jqueryui/development-bundle/jquery-1.6.2.js"></script>
		<script src="<?php echo base_url();?>js/jqueryui/development-bundle/ui/jquery.ui.core.js"></script>
		<script src="<?php echo base_url();?>js/jqueryui/development-bundle/ui/jquery.ui.widget.js"></script>
		<script src="<?php echo base_url();?>js/jqueryui/development-bundle/ui/jquery.ui.button.js"></script>
		<link rel="stylesheet" href="<?php echo base_url();?>js/jqueryui/development-bundle/themes/ui-lightness/jquery.ui.all.css" />
		
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
				height:100px;
				margin:0;
				clear:both;
				background-color:red;
			}
			
			#center {
				float:left;
	   			width:100%;
				text-align:center;
	    		margin:0;
	  			clear:both;
			}
			
			.title {
				width:480px;
				margin:0 auto;
				background-color:white;
			}
			
			.items {
				width:530px;
				margin:0 auto;
				background-color:white;
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
		--></style>
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
			<div id="top"></div>
			<div id="center">
				<?php
				echo form_open('poll/save');
				
				echo '<div class="title">';
				$this->table->add_row(form_label(lang('poll_save_label_title').lang('poll_save_label_colon'), 'title'), form_input(array('name' => 'title', 'id' => 'title')), ''); 
				$this->table->add_row(form_label(lang('poll_save_label_remark').lang('poll_save_label_colon'), 'remark'), form_input(array('name' => 'remark', 'id' => 'remark')), ''); 
				echo $this->table->generate();
				echo '</div>';
				
				echo '<div class="items">';
				echo '<table id="items">';
				
				function printTr($num) {
					echo "<tr id=\"item$num\">";
					echo '<td>';
					echo '</td>';
					echo '<td>';
					echo form_input(array('name' => "itemTitle$num", 'id' => "itemTitle$num", 'class' => "item"));
					echo '</td>';
					echo '<td>';	
					echo form_button(array('name' => "itemBtn$num", 'id' => "itemBtn$num", 'onclick' => "deleteItem($num);"), lang('poll_save_btn_delete_item'));
					echo '</td>';
					echo '</tr>';
				}
				
				for ($i = 1; $i <= 4; $i++)
					printTr($i);
				
				echo '</table>';
				echo '</div>';
				
				echo '<div class="others">';
				echo '</div>';
				
				echo '<div class="btns">';
				// 保存最大的item编号值，初始化为4
				echo '<input type="hidden" name="itemMax" id="itemMax" value="4"/>';
				// 保存页面显示的items编号值，初始化为1,2,3,4
				echo '<input type="hidden" name="itemIds" id="itemIds" value="1,2,3,4"/>';
				echo form_button(array('name' => 'newItem', 'id' => 'newItem'), lang('poll_save_btn_new_item'));
				echo form_button(array('name' => 'save', 'id' => 'save', 'onclick' => 'javascript:document.forms[0].submit();'), lang('poll_save_btn_save'));
				echo '</div>';
				
				echo form_close();
				?>	
			</div>
		</div>
		
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
				$('#items').append('<tr id="item'+num+'"><td></td><td><input type="text" name="itemTitle'+num+'" id="itemTitle'+num+'" class="item"/></td><td><button name="itemBtn'+num+'" type="button" id="itemBtn'+num+'" onclick="deleteItem('+num+');" ><?php echo lang('poll_save_btn_delete_item');?></button></td></tr>');
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
					alert("<?php echo lang('poll_save_error_min_items');?>");
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
	</body>
</html>
