<?php

require_once 'PHPUnit\Framework\TestCase.php';
require_once '..\dao\BaseDao.php';

/**
 * BaseDao test case.
 */
class BaseDaoTest extends PHPUnit_Framework_TestCase
{
	
	/**
	 * @var BaseDao
	 */
	private $BaseDao;
	
	/**
	 * Prepares the environment before running a test.
	 */
	protected function setUp()
	{
		parent::setUp ();
		
		// TODO Auto-generated BaseDaoTest::setUp()
		

		$this->BaseDao = new BaseDao(/* parameters */);
	
	}
	
	/**
	 * Cleans up the environment after running a test.
	 */
	protected function tearDown()
	{
		// TODO Auto-generated BaseDaoTest::tearDown()
		

		$this->BaseDao = null;
		
		parent::tearDown ();
	}
	
	/**
	 * Constructs the test case.
	 */
	public function __construct()
	{
		// TODO Auto-generated constructor
	}
	
	/**
	 * Tests BaseDao->__construct()
	 */
	public function test__construct()
	{
		// TODO Auto-generated BaseDaoTest->test__construct()
		$this->markTestIncomplete ( "__construct test not implemented" );
		
		$this->BaseDao->__construct(/* parameters */);
	
	}
	
	/**
	 * Tests BaseDao->__destruct()
	 */
	public function test__destruct()
	{
		// TODO Auto-generated BaseDaoTest->test__destruct()
		$this->markTestIncomplete ( "__destruct test not implemented" );
		
		$this->BaseDao->__destruct(/* parameters */);
	
	}
	
	/**
	 * Tests BaseDao->ExecuteSqlNoTrans()
	 */
	public function testExecuteSqlNoTrans()
	{
		// TODO Auto-generated BaseDaoTest->testExecuteSqlNoTrans()
		$this->markTestIncomplete ( "ExecuteSqlNoTrans test not implemented" );
		
		$this->BaseDao->ExecuteSqlNoTrans(/* parameters */);
	
	}
	
	/**
	 * Tests BaseDao->ExecuteSql()
	 */
	public function testExecuteSql()
	{
		// TODO Auto-generated BaseDaoTest->testExecuteSql()
		$this->markTestIncomplete ( "ExecuteSql test not implemented" );
		
		$this->BaseDao->ExecuteSql(/* parameters */);
	
	}
	
	/**
	 * Tests BaseDao->ExecuteQuery()
	 */
	public function testExecuteQuery()
	{
		// TODO Auto-generated BaseDaoTest->testExecuteQuery()
		$this->markTestIncomplete ( "ExecuteQuery test not implemented" );
		
		$this->BaseDao->ExecuteQuery(/* parameters */);
	
	}

}

