<?php
/**
* Main file
*
* This file is the main file for the library, and should be included in your script, e.g.
* <code>
* <?php 
* include ('lib/autocrud.php');
* </code>
*
* @license http://www.opensource.org/licenses/mit-license.php MIT License
* @package autocrud
*/

// All other necessary library files
include ('error.inc.php');
include ('mysql.inc.php');
include ('crud.inc.php');
include ('relationship.inc.php');
include ('activerecord.inc.php');

/**
* AutoCRUD Main Class
*
* This class is used to create a new AutoCRUD object, and is used like this:
* <code>
* $crud = new AutoCRUD;
* </code>
* 
* @package autocrud
*/
Class AutoCRUD {
	// Public vars:
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
	var $_mysql;
	var $_php;
	var $_prefix;
	var $_connected = false;
	var $_tables = array();
	var $_setup = false;
	/**#@-*/

	/**
	* It's possible to immediately pass the mysql info to the constructor to setup a connection
	*
	* @access public
	* @param string $user the MySQL user
	* @param string $password the password of the MySQL user
	* @param string $database the name of the database you want to use
	* @param string $server the MySQL host (usually localhost)
	* @param object $mysql strictly used for testing purposes (to pass in Mock MySQL object)
	*/
	function AutoCRUD ($user='', $password='', $database='', $server='localhost', $mysql=null) {
		if (isset($mysql) == false OR !is_object($mysql)) {
			$this->_mysql = new AutoCRUD_MySQL;
		} else {
			$this->_mysql =& $mysql;
		}

		if (!empty($database)) {
			$result = $this->connect ($user, $password, $database, $server);
		}
	}

	/**
	* Setups up a connection with the MySQL database
	*
	* @access public
	* @param string $user the MySQL user
	* @param string $password the password of the MySQL user
	* @param string $database the name of the database you want to use
	* @param string $server the MySQL host (usually localhost)
	* @return mixed will either return true on success or an error object on failure
	*/
	function connect($user='', $password='', $database='', $server='localhost') {
		if (!empty($user)) { $this->user = $user; }
		if (!empty($password)) { $this->password = $password; }
		if (!empty($server)) { $this->server = $server; }
		if (!empty($database)) { $this->database = $database; }

		$result = $this->_mysql->connect ($this->user, $this->password, $this->database);

		// Successful?
		if ($result !== true) { 
			return $result; 
		}

		$this->_connected = true;

		return true;
	}

	/**
	* Include an external CRUD definition file to create the CRUD objects. Can be used to
	* increase performance of the AutoCRUD library, because the database itself isn't inspected
	* at runtime.
	*
	* @access public
	* @param string $file the path of the CRUD definition file
	* @return mixed will either return true on success or an error object on failure
	*/
	function include_crud ($file) {
		if ($this->isConnected() == false) {
			return $this->halt('no-connection', 'There is no connection to the MySQL server');
		}

		if (!is_readable($file)) {
			return $this->halt('invalid-file', 'The CRUD file you tried to include doesn\'t exist');
		}

		// Include file
		include ($file);

		$this->_setup = true;
	}

	/**
	* Whether there is a connection or not
	*
	* @access public
	* @return bool true when connected, false when not connected
	*/
	function isConnected() {
		return $this->_mysql->is_connected();
	}

	/**
	* Whether CRUD objects have been setup or not
	*
	* @access public
	* @return bool true when objects have been setup, false when they haven't been setup
	*/
	function isSetup() {
		return $this->_setup;
	}

	/**
	* When you are using table prefixes, set the prefix using this function, and the library will
	* automatically create aliases for the tables without prefix
	*
	* @access public
	* @param string the table prefix
	*/
	function setTablePrefix($str) {
		$this->_prefix = $str;
	}

	/**
	* Returns the table prefix that has been set
	*
	* @access public
	* @return string the table prefix
	*/
	function getTablePrefix() {
		return $this->_prefix;
	}

	/**
	* Used to create all the necessary CRUD objects for each table. This or 
	* the include_crud function must always be called before you can use the library
	*
	* @access public
	* @param $return should the library return the CRUD definitions or run them (thereby creating the CRUD objects)
	* @return mixed true on success, an error object on failure
	*/
	function generate($return = false) {
		$nl = "\n";

		if ($this->isConnected() == false) {
			return $this->halt('no-connection', 'There is no connection to the MySQL server');
		}

		// Get a list of tables
		$tables = $this->_mysql->sql_query ("SHOW TABLES");
		if (count($tables) == 0) { 
			return $this->halt('no-tables', 'There are no tables in the database, so no CRUD to generate'); 
		}

		$arr = array();
		foreach ($tables as $t) {
			$table = array_shift($t);
			$arr[] = $table;
		}
		$tables = $arr;

		$code = '';
		foreach ($tables as $table) {
			$this->_tables[] = $table;

			$code .= '$this->' . $table . ' = new AutoCRUD_CRUD(\'' . addslashes($table) . '\');' . $nl;
			$code .= '$this->' . $table . '->set_parent (&$this);' . $nl;

			// Get table fields and generate code for fields
			$fields = $this->_mysql->sql_query ("SHOW COLUMNS FROM `" . $table . "`");

			if (count($fields) > 0) {
				$code = $this->_generate_fields($table, $fields, $code);
			}

			// Get table indexes and generate code for fields
			$indexes = $this->_mysql->sql_query ("SHOW INDEX FROM `" . $table . "`");
			
			if (count($indexes) > 0) {
				$code = $this->_generate_indexes($table, $indexes, $code);
			}			

			$code .= $nl;
		}

		if ($return == false) {
			eval($code);
		} else {
			$code = '<?php' . $nl . $code . '?>';
			return $code;
		}

		// Create table aliases
		$this->_createAliases();

		$this->_setup = true;
		return true;
	}

	// These are the functions for 'raw' MySQL access

	/**#@+
	* Raw MySQL function
	*
	* Simply a shortcut to the raw MySQL function
	*
	* @access public
	* @see AutoCRUD_MySQL
	*/
	function query ($querystring) { return $this->_mysql->query($querystring); }
	function sql_query ($querystring, $key='') { return $this->_mysql->sql_query($querystring, $key); }
	function query_first ($querystring) { return $this->_mysql->query_first($querystring); }
	function insert_id() { return $this->_mysql->insert_id(); }
	function query_num() { return $this->_mysql->query_num(); }
	function queries() { return $this->_mysql->queries(); }
	function quote($str) { return $this->_mysql->quote($str); }
	function quoteInto($stack, $needle) { return $this->_mysql->quoteInto($stack, $needle); }
	function close() { return $this->_mysql->close(); }
	function affected_rows() { return $this->_mysql->affected_rows(); }
	function halt($code, $msg='') { return $this->_mysql->halt($code, $msg); }
	/**#@-*/

	/**#@+
	* @access private
	*/
	function _createAliases() {
		if (empty($this->_prefix)) { return true; }			

		foreach ($this->_tables as $table) {
			$alias = substr($table, strlen($this->_prefix));
			$this->$table->addAlias ($alias);
		}

		return true;
	}

	function _generate_indexes($table, $indexes, $code) {
		$nl = "\n";

		// Loop through indexes
		$index_arr = '$this->' . $table . '->indexes = Array(';

		foreach ($indexes as $key => $i) {
			$key = intval($key) + 1;
			$name = $i['Key_name'];

			$index_arr .= "'" . $key . "' => '" . addslashes($name) . "', ";
		}

		$index_arr .= ');' . $nl;
		$code .= $index_arr;

		return $code;
	}

	function _generate_fields($table, $fields, $code) {
		$nl = "\n";

		// Loop through fields
		$field_arr = '$this->' . $table . '->fields = Array(';
		$required_arr = '$this->' . $table . '->required = Array(';
		$numeric_arr = '$this->' . $table . '->numeric = Array(';

		foreach ($fields as $f) {
			$field = addslashes($f['Field']);
			$field_arr .= '\'' . $field . '\', ';

			if ($f['Extra'] == 'auto_increment') {
				$code .= '$this->' . $table . '->key = \'' . $field . '\';' . $nl;
				continue;
			}

			if ($f['Null'] != 'YES') {
				$required_arr .= '\'' . $field . '\', ';
			}

			if ($this->_field_is_numeric($f['Type']) == true) {
				$numeric_arr .= '\'' . $field . '\', ';
			}

		}
		$field_arr .= ');';
		$required_arr .= ');';
		$numeric_arr .= ');';

		$code .= $field_arr . $nl;
		$code .= $required_arr . $nl;
		$code .= $numeric_arr . $nl;

		return $code;
	}

	// Helper functions
	// ---------------------------------------------------------------------

	function _field_is_numeric($type) {
		$types = array('tinyint', 'smallint', 'mediumint', 'int', 'bigint', 'float', 'double', 'decimal');
		$type = strtolower($type);
		$type = substr($type, 0, strpos($type, '('));

		if (in_array($type, $types) == true) {
			return true;
		} else {
			return false;
		}
	}

	/**#@-*/

}

?>