<?php

require_once 'model/Tb_test.php';

class Counter 
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
		return 'hello world';
	}
}

// Create an instance of the ReflectionClass class
$class = new ReflectionClass('Counter');

$method = $class->getMethod('toString');

echo $method->getName();
$c = new Counter();
//$class = new ReflectionMethod('Counter', 'toString');
echo $method->invoke($c, null);

$c11 = new ReflectionClass('Tb_test');
$c22 = $c11->newInstance(null);
echo $c22->toString();
?>