<?php
class MPoll extends CI_Model
{
	var $id;
	var $title;
	var $remark;
	var $create_time;
	var $update_time;
	var $enable = true;
	
	function __construct()
	{
		parent::__construct();
	}
	
	function insert()
	{
		$this->title = $this->input->post('title');
		$this->remark = $this->input->post('remark');
		$this->create_time = date('Y-m-d H:i:s', time());
		$this->db->insert('tb_poll', $this);
		return $this->db->insert_id('id');
	}
	
	function update()
	{
		$this->title = $this->input->post('title');
		$this->remark = $this->input->post('remark');
		$this->update_time = date('Y-m-d H:i:s', time());
		$this->db->update('tb_poll', $this, array('id' => $this->input->post('id')));
	}
}
?>