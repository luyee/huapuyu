<?php
if (!defined('BASEPATH'))
	exit('No direct script access allowed');

class Poll extends CI_Controller
{
	public function save()
	{
		$this->load->view('poll/save');
	}
}