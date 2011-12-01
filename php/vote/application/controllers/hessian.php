<?php
if (!defined('BASEPATH'))
	exit('No direct script access allowed');
	
include_once 'lib/HessianPHP_v2.0.3/src/HessianClient.php';

class Hessian extends CI_Controller
{
	public function wsclient()
	{
		$testurl = 'http://127.0.0.1/vote/poll/ws.html';
		$proxy = new HessianClient($testurl);
		try {
    		echo $proxy->div(2,5); 
		} catch (Exception $ex){
			echo $ex;
		}
	}
}
?>