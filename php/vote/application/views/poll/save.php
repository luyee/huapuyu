<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><?php echo lang('poll_save_title');?></title>
		<script type="text/javascript" src="<?php echo base_url();?>js/jquery-1.6.3.js"></script>
	</head>

	<body>
		<?php
		echo form_open('poll/save');
		
		echo '<table>';
		echo '<tr>';
		echo '<td>';	
		echo form_label(lang('poll_save_label_title').lang('poll_save_label_colon'), 'title');
		echo '</td>';
		echo '<td>';
		$title = array('name' => 'title', 'id' => 'title');
		echo form_input($title);
		echo '</td>';
		echo '<td>';	
		echo '</td>';
		echo '</tr>';

		echo '<tr>';
		echo '<td>';
		echo form_label(lang('poll_save_label_remark').lang('poll_save_label_colon'), 'remark');
		echo '</td>';
		echo '<td>';
		$remark = array('name' => 'remark', 'id' => 'remark');
		echo form_input($remark);
		echo '</td>';
		echo '<td>';	
		echo '</td>';
		echo '</tr>';
		echo '</table>';
		
		echo '<table id="items">';
		echo '<tr id="item1">';
		echo '<td>';
		echo '</td>';
		echo '<td>';
		$itemTitle1 = array('name' => 'item_title1', 'id' => 'item_title1');
		echo form_input($itemTitle1);
		echo '</td>';
		echo '<td>';
		$itemBtn1 = array('name' => 'item_btn1', 'id' => 'item_btn1', 'onclick' => 'deleteItem(1);');
		echo form_button($itemBtn1, lang('poll_save_btn_delete_item'));
		echo '</td>';
		echo '</tr>';
		
		echo '<tr id="item2">';
		echo '<td>';
		echo '</td>';
		echo '<td>';
		$itemTitle2 = array('name' => 'item_title2', 'id' => 'item_title2');
		echo form_input($itemTitle2);
		echo '</td>';
		echo '<td>';	
		$itemBtn2 = array('name' => 'item_btn2', 'id' => 'item_btn2', 'onclick' => 'deleteItem(2);');
		echo form_button($itemBtn2, lang('poll_save_btn_delete_item'));
		echo '</td>';
		echo '</tr>';
		
		echo '<tr id="item3">';
		echo '<td>';
		echo '</td>';
		echo '<td>';
		$itemTitle3 = array('name' => 'item_title3', 'id' => 'item_title3');
		echo form_input($itemTitle3);
		echo '</td>';
		echo '<td>';
		$itemBtn3 = array('name' => 'item_btn3', 'id' => 'item_btn3', 'onclick' => 'deleteItem(3);');
		echo form_button($itemBtn3, lang('poll_save_btn_delete_item'));	
		echo '</td>';
		echo '</tr>';
		
		echo '<tr id="item4">';
		echo '<td>';
		echo '</td>';
		echo '<td>';
		$itemTitle4 = array('name' => 'item_title4', 'id' => 'item_title4');
		echo form_input($itemTitle4);
		echo '</td>';
		echo '<td>';
		$itemBtn4 = array('name' => 'item_btn4', 'id' => 'item_btn4', 'onclick' => 'deleteItem(4);');
		echo form_button($itemBtn4, lang('poll_save_btn_delete_item'));
		echo '</td>';
		echo '</tr>';
		echo '</table>';
		
		echo '<input type="hidden" name="item_max" id="item_max" value="4"/>';
		echo '<input type="hidden" name="item_ids" id="item_ids" value="1,2,3,4"/>';
		$newItem = array('name' => 'new_item', 'id' => 'new_item');
		echo form_button($newItem, lang('poll_save_btn_new_item'));
		echo form_submit('submit', lang('poll_save_btn_save'));
		
		echo form_close();
		?>	
		
		<script type="text/javascript">
  			$('#new_item').click(function() {
  				var itemMax = $("#item_max").val();
  				itemMax = parseInt(itemMax) + 1;
  				var itemIds = $("#item_ids").val();
  				addItem(itemMax);
  				$("#item_ids").val(itemIds + "," + itemMax);
  				$("#item_max").val(itemMax);
  				
			});

			function addItem(num) {
				$('#items').append('<tr id="item'+num+'"><td></td><td><input type="text" name="item_title'+num+'" id="item_title'+num+'"/></td><td><button name="item_btn'+num+'" type="button" id="item_btn'+num+'" onclick="deleteItem('+num+');" ><?php echo lang('poll_save_btn_delete_item');?></button></td></tr>');
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
