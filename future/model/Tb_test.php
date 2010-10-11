<?php
require_once 'IModel.php';

class Tb_test implements IModel
{
	private $id;
	private $name;
	
	public function getId()
	{
		return $this->id;
	}
	
	public function getName()
	{
		return $this->name;
	}
	
	public function setId($id)
	{
		$this->id = $id;
	}
	
	public function setName($name)
	{
		$this->name = $name;
	}
	
	public function __construct()
	{
		$this->name = 'zhuzhen';
		$this->id = 23;
	}
	
	public function toString()
	{
		return "id: $this->id; name: $this->name";
	}
}
?>