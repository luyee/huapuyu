<?php
class BaseDao2
{
	private $db_host = "127.0.0.1";
	private $db_user = "root333";
	private $db_pwd = "123";
	private $db_name = "db_future123";
	private $mysqli = null;
	
	public function __construct()
	{
		$this->mysqli = new mysqli();
		try 
		{
			$this->mysqli->connect ( $this->db_host, $this->db_user, $this->db_pwd, $this->db_name );
		}
		catch(Exception $e)
		{
			echo $e->getMessage();
		}
	}
	
	public function __destruct()
	{
		if ($this->mysqli != null)
			$this->mysqli->close();
	}
	
	public function ExecuteSqlNoTrans($sql)
	{
		$i = - 1;
		try
		{
			$i = $this->mysqli->query ( $sql );
		}
		catch ( Exception $e )
		{
			echo $e->getMessage ();
		}
		
		return $i;
	}
	
	public function ExecuteSql($sql)
	{
		$i = - 1;
		try
		{
			$this->mysqli->autocommit = false;
			try
			{
				$i = $this->mysqli->query ( $sql );
				$this->mysqli->commit ();
			}
			catch ( Exception $e )
			{
				echo $e->getMessage ();
				$this->mysqli->rollback ();
				$i = - 1;
			}
		}
		catch ( Exception $e )
		{
			echo $e->getMessage ();
		}
		return $i;
	}
	
	public function ExecuteQuery($sql)
	{
		try
		{
			$result = $this->mysqli->query ( $sql );
		}
		catch ( Exception $e )
		{
			echo $e->getMessage ();
		}
		return $result;
	}
}
?>