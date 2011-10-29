<?php
if (!defined('BASEPATH'))
	exit('No direct script access allowed');

class Poll extends CI_Controller
{
	public function saveInput()
	{
		$this->load->helper('language');
		$this->load->helper('url');
//		$this->load->library('jquery');
		$this->lang->load('message');
		$this->load->helper('form');
		$this->load->view('poll/save');
	}
	
	public function save()
	{
		$this->load->model('MPoll', '', TRUE);
		$pollId = $this->MPoll->insert();
		$this->load->model('MPollItem', '', TRUE);
		$this->MPollItem->insert($pollId);
	}
}
?>