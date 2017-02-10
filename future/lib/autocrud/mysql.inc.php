<?php
/**
* MySQL Class File
*
* This file contains the MySQL class
*
* @license http://www.opensource.org/licenses/mit-license.php MIT License
* @package autocrud
*/

/**
* AutoCRUD MySQL Class
*
* This class is used by the AutoCRUD object to query MySQL
* 
* @package autocrud
*/
Class AutoCRUD_MySQL {
	// Public vars
	/**
	* Use Persistant Connections
	* Set to true if you want to use persistant MySQL connections, false for regular connections
	* @access public
	* @var bool
	*/
	var $usepconnect = false;

	/**
	* MySQL User
	* Can be used to set the MySQL user instead of passing it as a function argument
	* @access public
	* @var string
	*/
	var $user;

	/**
	* MySQL Password
	* Can be used to set the MySQL password instead of passing it as a function argument
	* @access public
	* @var string
	*/
	var $password;

	/**
	* Database Name
	* Can be used to set the name of the database you want to use instead of passing it as a function argument
	* @access public
	* @var string
	*/
	var $database;

	/**
	* MySQL Host
	* Can be used to set the MySQL host instead of passing it as a function argument
	* @access public
	* @var string
	*/
	var $server;

	// Private vars:
	/**#@+
	* @access private
	*/
	var $_link_id = false;
	var $_queries = array();
	var $_php;
	/**#@-*/

	/**
	* Used to setup a connection with the MySQL server
	*
	* @access public
	* @param string $user MySQL user
	* @param string $password MySQL password
	* @param string $database name of the database you want to select
	* @param string $server hostname of the MySQL server (usually localhost)
	* @return mixed true on success, error object on failure
	*/
	function connect($user=null, $password=null, $database=null, $server='localhost') {
		// Has a connection already been established?
		if ($this->_link_id != false) { return false; }

		if (isset($user)) { $this->user = $user; }
		if (isset($password)) { $this->password = $password; }
		if (isset($server)) { $this->server = $server; }
		if (isset($database)) { $this->database = $database; }

		$time_start = $this->_microtime_float();

		// Connect to MySQL server
		if ($this->usepconnect == true) {
			$this->_link_id = @mysql_pconnect($this->server,$this->user,$this->password);
		} else {
			$this->_link_id = @mysql_connect($this->server,$this->user,$this->password);
		}

		// Has a connection been established?
		if ($this->_link_id == false) {
			return $this->halt('no-connection', "Unable to connect to MySQL server; incorrect server, username or password.");
		}

		$time_end = $this->_microtime_float();
		$time = $time_end - $time_start;
		$this->_queries[] = array('Connection established', $time);

		// Lose the sensitive data
		unset($this->user);
		unset($this->password);

		// Database already specified? If so, select it
		if (!empty($this->database)) {
			return $this->select_db($this->database);
		}	
		
		return true;
    }

	/**
	* Used to select a database you want to use
	*
	* @access public
	* @param string $database name of the database you want to use
	* @return mixed true on success, error object on failure
	*/
	function select_db($database=null) {
		// Make sure there is a connection
		$this->connect();

		// select database
		if (isset($database)) { $this->database = $database; }

		if(mysql_select_db($this->database, $this->_link_id) == false) {
			return $this->halt('invalid-db', "Cannot use database " .  $this->database);
		}

		return true;
	}

	/**
	* Used to execute a query on the database
	*
	* @access public
	* @param string $query_string the query you want to execute
	* @return mixed resource ID on success, error object on failure
	*/
	function query($query_string) {
		$time_start = $this->_microtime_float();

		// Make sure there is a connection
		$this->connect();

		// Execute query
		$query_id = mysql_query($query_string, $this->_link_id);

		if (!$query_id) {
			return $this->halt('invalid-query', 'Invalid query: ' . $query_string);
		}

		$time_end = $this->_microtime_float();
		$time = $time_end - $time_start;
		$this->_queries[] = array($query_string, $time);

		return $query_id;
	}

	/**
	* Used to select a list of records
	*
	* @access public
	* @param string $query_string the query you want to execute
	* @param string $key the key under which the records should be stored (should be a 
	* field in the table you're selecting from
	* @return mixed array of records on success, error object on failure
	*/
	function sql_query($query_string, $key='') {
		// Get results
		$queryid = $this->query($query_string);

		// Not a resource -> we can't do anything with it here
		if (is_resource($queryid) == false) { return $queryid; }

		// Store each row in array
		$results = array();
		while ($row = $this->_fetch_array($queryid)) {
			$results[] = $row;
		}

		// Remove query resource
		$this->_free_result($queryid);

		// Create a list of data:
		$return_array = array();
		foreach ($results as $row) {
			if (!empty($key) AND isset($row[$key])) {
				$return_array[$row[$key]] = $row;
			} else {
				$return_array[] = $row;
			}
		}		

		return $return_array; 
	}

	/**
	* Used to select a single record
	*
	* @access public
	* @param string $query_string the query you want to execute
	* @return mixed returns record (as an array) on success, false when there is no record, and an error object on failure
	*/
	function query_first($query_string) {
		// does a sql query and returns first row
		$result = $this->sql_query ($query_string);

		// No array? probably error, just return it
		if (!is_array($result)) { return $result; }

		if (count($result) == 0) {
			return false;
		} else {
			return array_shift($result);
		}
	}

	/**
	* Used to return an error (mainly used internally)
	*
	* @access public
	* @param string $code the error code
	* @param string $msg the error message
	* @return AutoCRUD_Error an error object
	*/
	function halt ($code, $msg='') {
		$this->errno = mysql_errno();
		$this->errmsg = mysql_error();
		$this->error = true;

		$error =& New AutoCRUD_error;
		$error->setMessage ($msg);
		$error->setMessage($this->errmsg, 2);
		$error->setCode ($code);
		$error->setCode ($this->errno, 2);

		return $error;
	}

	/**
	* Used to get the record ID of the last inserted record
	*
	* @access public
	* @return mixed the record ID of the last inserted record
	*/
	function insert_id() { 
		return mysql_insert_id($this->_link_id); 
	}

	/**
	* Used to close the MySQL connection
	*
	* @access public
	* @return bool true on success, false on failure
	*/
	function close() {
		if (!empty($this->_link_id)) {
			$this->_link_id = false;
			return mysql_close(); 
		}
	}

	/**
	* Can be used to check whether there is a connection or not
	*
	* @access public
	* @return bool true when connected, false when not
	*/
	function is_connected() {
		if ($this->_link_id == false) { 
			return false; 
		} else {
			return true;
		}
	}

	/**
	* Returns the number of queries that have been executed
	*
	* @access public
	* @return integer number of queries that have been executed
	*/
	function query_num() {
		return count($this->_queries);
	}

	/** 
	* Returns an array of all the queries that have been executed
	*
	* @access public
	* @return array the queries
	*/
	function queries() {
		return $this->_queries;
	}

	/**
	* Used to prevent SQL injection, will escape all the 'dangerous' characters and put quotes around it
	*
	* @access public
	* @param string the text that should be escaped
	* @return string the escaped and quoted text (safe for SQL queries)
	*/
	function quote($str) {
		if (function_exists('mysql_real_escape_string') AND $this->_link_id != false) {
			$str = mysql_real_escape_string($str);
		} else {
			$str = mysql_escape_string($str);
		}

		$str = "'" . $str . "'";

		return $str;
	}

	/**
	* Used to quote multiple variables into one statement
	*
	* @access public
	* @param string $stack the statement (use ? as placeholders for the variables)
	* @param mixed $needle a single replacement variable, or an array of multiple replacements
	* @return string the statement with the data in it
	*/
	function quoteInto($stack, $needle) {
		// Single needle:
		if (!is_array($needle)) {
			$args = func_get_args();
			array_shift($args);

			// Only one arg? replace it in string
			if (count($args) == 1) {
				$stack = str_replace("?", $this->quote($needle), $stack);
				return $stack;
			} else {
				// Multiple args, so set needle to array of args
				$needle = $args;
			}
		}

		// Multiple (array'ed) needles:
		$stack = explode('?', $stack);

		// Params match with values?
		if (count($stack)-1 != count($needle)) {
			return false;
		}

		// Match each parameter with the right value
		$newstack = '';
		for ($i=0; $i < count($stack); $i++) {
			$newstack .= $stack[$i];

			if (array_key_exists($i, $needle)) {
				$newstack .= $this->quote($needle[$i]);
			}
		}
		$stack = $newstack;

		return $stack;
	}

	/**
	* Returns the number of rows affected by the last query
	*
	* @access public
	* @return integer the number of rows affected
	*/
	function affected_rows() {
		return mysql_affected_rows($this->_link_id); 
	}


	// Helper Functions:
	// ------------------------------------------------------

	/**#@+
	* @access private
	*/
	function _free_result($query_id) {
		return @mysql_free_result($query_id);
	}

	function _fetch_array($query_id) {
		$this->record = mysql_fetch_array($query_id, MYSQL_ASSOC);
		return $this->record;
	}

	function _microtime_float() {
		list($usec, $sec) = explode(" ", microtime());
		return ((float)$usec + (float)$sec);
	}
	/**#@-*/
}

?>