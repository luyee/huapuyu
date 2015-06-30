<?php
class MPollItem extends CI_Model
{
	var $id;
	var $title;
	var $enable = true;
	var $poll_id;
	
	function __construct()
	{
		parent::__construct();
	}
	
	function insert($pollId)
	{
		$itemIds = $this->input->post("item_ids");
		$ids = explode(',', $itemIds);
		foreach ($ids as $id) { 
			$this->title = $this->input->post("item_title$id");
			$this->poll_id = $pollId;
			$this->db->insert('tb_poll_item', $this);
		}
	}
	
	function update($num)
	{
		$this->title = $this->input->post("item_title$num");
		$this->db->update('tb_poll_item', $this, array('id' => $this->input->post('item_id')));
	}
}
?>