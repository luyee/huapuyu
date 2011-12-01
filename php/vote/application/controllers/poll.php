<?php
if (!defined('BASEPATH'))
	exit('No direct script access allowed');
	
include_once 'lib/HessianPHP_v2.0.3/src/HessianService.php';
include_once 'lib/HessianPHP_v2.0.3/src/HessianClient.php';

class Poll extends CI_Controller
{
	public function saveInput()
	{
		// $this->load->helper('language');
		// $this->load->helper('form');
		// $this->load->helper('url');
		$this->load->helper(array('language', 'form', 'url'));
		$this->lang->load('message');
		$this->load->library('table');
		// $this->load->library('jquery');
		$this->load->view('poll/save');
	}
	
	public function save()
	{
		$this->load->helper('language');
		$this->lang->load('message');
		$this->load->library('form_validation');
		$this->form_validation->set_rules('title', lang('poll_save_label_title'), 'required');
		$this->form_validation->set_rules('remark', lang('poll_save_label_remark'), 'required');
		
		if ($this->form_validation->run() == FALSE)
		{
			$this->load->helper(array('form', 'url'));
			$this->load->library('table');
			$this->load->view('poll/save');
		}
		else
		{ 
			$this->load->model('MPoll', '', TRUE);
			$pollId = $this->MPoll->insert();
			$this->load->model('MPollItem', '', TRUE);
			$this->MPollItem->insert($pollId);
			$this->load->view('poll/save');
		}
	}
	
	public function ws()
	{
//		$service = new HessianService(new Math());
//$service->handle();
		$wrapper = &new HessianService(); 
		$userService = &new Math(); 
$wrapper->registerObject($userService); 
$wrapper->displayInfo = true;
$wrapper->service(); 
	}
}

class Math{    
  function add($n1,$n2) {        
    return $n1+$n2;    
  }    
  function sub($n1,$n2) {        
    return $n1-$n2;    
  }    
  function mul($n1,$n2) {        
    return $n1*$n2;    
  }    
  function div($n1,$n2) {        
    return $n1/$n2;    
  }
}
?>