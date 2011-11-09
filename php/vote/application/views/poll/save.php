<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><?php echo lang('poll_save_title');?></title>
		<link rel="stylesheet" href="<?php echo base_url();?>js/jqueryui/development-bundle/themes/ui-lightness/jquery.ui.all.css" />
		<script src="<?php echo base_url();?>js/jqueryui/development-bundle/jquery-1.6.2.js"></script>
		<script src="<?php echo base_url();?>js/jqueryui/development-bundle/ui/jquery.ui.core.js"></script>
		<script src="<?php echo base_url();?>js/jqueryui/development-bundle/ui/jquery.ui.widget.js"></script>
		<script src="<?php echo base_url();?>js/jqueryui/development-bundle/ui/jquery.ui.button.js"></script>
		<link rel="stylesheet" href="<?php echo base_url();?>js/jqueryui/development-bundle/demos/demos.css" />
		<script>
		$(function() {
			modifyButtonStyle();
		});

		// 修改Button样式
		function modifyButtonStyle()
		{
			$("button").button({
	            icons: {
	                primary: "ui-icon-locked"
	            }
	        });
		}
		</script>
	</head>

	<body style="margin: 0;">
		<div style="background-color: red;">hello world</div>
		<div>
		<?php
		echo form_open('poll/save');
		
		$this->table->add_row(form_label(lang('poll_save_label_title').lang('poll_save_label_colon'), 'title'), form_input(array('name' => 'title', 'id' => 'title')), ''); 
		$this->table->add_row(form_label(lang('poll_save_label_remark').lang('poll_save_label_colon'), 'remark'), form_input(array('name' => 'remark', 'id' => 'remark')), ''); 
		echo $this->table->generate();
		
		echo '<table id="items">';
		
		function printTr($num)
		{
			echo "<tr id=\"item$num\">";
			echo '<td>';
			echo '</td>';
			echo '<td>';
			echo form_input(array('name' => "item_title$num", 'id' => "item_title$num"));
			echo '</td>';
			echo '<td>';	
			echo '<div class="demo">';
			echo form_button(array('name' => "item_btn$num", 'id' => "item_btn$num", 'onclick' => "deleteItem($num);"), lang('poll_save_btn_delete_item'));
			echo '</div>';
			echo '</td>';
			echo '</tr>';
		}
		
		for ($i = 1; $i <= 4; $i++)
			printTr($i);
		
		echo '</table>';
		
		echo '<input type="hidden" name="item_max" id="item_max" value="4"/>';
		echo '<input type="hidden" name="item_ids" id="item_ids" value="1,2,3,4"/>';
		
		echo '<div class="demo">';
		echo form_button(array('name' => 'new_item', 'id' => 'new_item'), lang('poll_save_btn_new_item'));
		echo form_button(array('name' => 'save', 'id' => 'save', 'onclick' => 'javascript:document.forms[0].submit();'), lang('poll_save_btn_save'));
		echo '</div>';
		
		echo form_close();
		?>	
		</div>
		
		<script type="text/javascript">
  			$('#new_item').click(function() {
  				var itemMax = $("#item_max").val();
  				itemMax = parseInt(itemMax) + 1;
  				var itemIds = $("#item_ids").val();
  				addItem(itemMax);
  				$("#item_ids").val(itemIds + "," + itemMax);
  				$("#item_max").val(itemMax);
  				modifyButtonStyle();
			});

			function addItem(num) {
				$('#items').append('<tr id="item'+num+'"><td></td><td><input type="text" name="item_title'+num+'" id="item_title'+num+'"/></td><td><div class="demo"><button name="item_btn'+num+'" type="button" id="item_btn'+num+'" onclick="deleteItem('+num+');" ><?php echo lang('poll_save_btn_delete_item');?></button></div></td></tr>');
			}

			function deleteItem(num) {
				var inputCount = $("form input:text").length;
				if (inputCount > 4) {
					$('#item' + num).remove();
					var itemIds = $("#item_ids").val();
					var ids = itemIds.split(",");
					ids = removeElementOfArray(num, ids);
					var newIds = ids.join(",");
					$("#item_ids").val(newIds);
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
