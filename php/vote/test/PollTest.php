<?php

require_once 'PHPUnit\Framework\TestCase.php';
require_once 'application/models/poll.php';

class PollTest extends PHPUnit_Framework_TestCase
{
	protected function setUp()
	{
		parent::setUp();
	}
	
	protected function tearDown()
	{
		parent::tearDown();
	}
	
	public function __construct()
	{
	}
	
	public function test1()
	{
		$poll = new Poll();
		$poll->insert();
	}
}
?>