<?php
//define ( "DB_HOST", "222.73.49.104" );
//define ( "DB_NAME", "a0331203734" );
//define ( "DB_USER", "a0331203734" );
//define ( "DB_PWD", "869483811" );


define("BEGIN_TRANSACTION", "START TRANSACTION");
define("COMMIT_TRANSACTION", "COMMIT");
define("ROLLBACK_TRANSACTION", "ROLLBACK");

final class BaseDao
{
	private $db_host = "127.0.0.1";
	private $db_user = "root";
	private $db_pwd = "123";
	private $db_name = "db_future";
	private $db_link;
	
	public function __construct()
	{
		$this->db_link = @mysql_connect($this->db_host, $this->db_user, $this->db_pwd) or die("Could not connect to server!");
		@mysql_select_db($this->db_name, $this->db_link) or die("Could not select database!");
	}
	
	public function __destruct()
	{
		if ($this->db_link != null && ! $this->db_link)
			@mysql_close($this->db_link) or die("Could not close the link!");
	}
	
	public function ExecuteSql($sql)
	{
		//TODO Need to edit the error message, sql must be not appear in error message!
		@mysql_query($sql, $this->db_link) or die("Could not execute sql : $sql!");
		return @mysql_affected_rows($this->db_link) or die("Could not fetch the affected row number!");
	}
	
	public function ExecuteQuery($sql)
	{
		return @mysql_query($sql, $this->db_link) or die("Could not execute query : $sql!");
		//@mysql_num_rows($this->db_link) or die ("Could not fetch the selected row number!");
	}
	
	public function BeginTransaction()
	{
		@mysql_query(BEGIN_TRANSACTION, $this->db_link) or die("Could not begin a transaction!");
	}
	
	public function CommitTransaction()
	{
		@mysql_query(COMMIT_TRANSACTION, $this->db_link) or die("Could not commit a transaction!");
	}
	
	public function RollbackTransaction()
	{
		@mysql_query(ROLLBACK_TRANSACTION, $this->db_link) or die("Could not rollback a transaction!");
	}
}
?>