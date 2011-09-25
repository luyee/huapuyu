<?php
class MPoll extends CI_Model
{
	var $id;
	var $title;
	
	function __construct()
	{
		parent::__construct();
	}
	
	function insert()
	{
		$this->title = $this->input->post('title');
		$this->db->insert('tb_poll', $this);
	}
	
	function update()
	{
		$this->title = $this->input->post('title');
		$this->db->update('tb_poll', $this, array('id' => $this->input->post('id')));
	}
}
?>