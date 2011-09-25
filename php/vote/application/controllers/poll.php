<?php
if (!defined('BASEPATH'))
	exit('No direct script access allowed');

class Poll extends CI_Controller
{
	public function saveInput()
	{
		$this->load->helper('form');
		$this->load->view('poll/save');
	}
	
	public function save()
	{
		$this->load->model('MPoll', '', TRUE);
		$this->MPoll->insert();
	}
}
?>