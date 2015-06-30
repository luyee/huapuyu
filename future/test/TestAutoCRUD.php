<?php

require_once 'PHPUnit\Framework\TestCase.php';
require_once 'lib\autocrud\autocrud.php';

class TestAutoCRUD extends PHPUnit_Framework_TestCase
{
	var $crud;
	
	protected function setUp()
	{
		parent::setUp();
		
		$this->crud = new AutoCRUD;
		$result = $this->crud->connect ('root', '123', 'AutoCRUD');
		$value = $this->crud->generate();
		if ($value)
			echo 'ok';
	}
	
	protected function tearDown()
	{
		parent::tearDown();
	}
	
	public function __construct()
	{
	}
	
	public function testFunction1()
	{
		$newRow = array('name' => 'zhuzhen', 'age' => 27);
		$result = $this->crud->tb_test->insert($newRow);
	}
}

