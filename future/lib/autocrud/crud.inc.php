<?php
/**
* CRUD Class File
*
* This file holds the AutoCRUD_CRUD class
*
* @license http://www.opensource.org/licenses/mit-license.php MIT License
* @package autocrud
*/

/**
* AutoCRUD CRUD Class
*
* This class is used for the CRUD functionality (such as insert, update, delete)
* 
* @package autocrud
*/
Class AutoCRUD_CRUD {
	// Public vars:
	/**
	* Table Name
	* Holds the table name of the CRUD object
	* @var string
	* @access public
	*/
	var $table;

	/**
	* Object Name
	* Holds the name of the CRUD object (mainly used to handle aliases)
	* @var string
	* @access public
	*/
	var $name;

	/**
	* Primary Key Field
	* Holds the name of the primary key field of the table
	* @var string
	* @access public
	*/
	var $key;

	/** 
	* Fields Array
	* An array of all the fields in the table
	* @var array
	* @access public
	*/
	var $fields = array();

	/**
	* Required Fields
	* An array of all the fields that are required for this table (set to 'NOT NULL')
	* @var array
	* @access public
	*/
	var $required = array();

	/**
	* Numeric Fields
	* An array of all the numeric fields of this table
	* @var array
	* @access public
	*/
	var $numeric = array();

	/**
	* Indexes
	* An array of all the indexes of this table
	* @var array
	* @access public
	*/
	var $indexes = array();

	/**
	* Paginate Results
	* Set to true if you want to paginate the results, and false if you don't
	* @var bool
	* @access public
	*/
	var $paging = false;

	/**
	* Records Per Page
	* Set how many records should be selected per page (when paging is enabled)
	* @var integer
	* @access public
	*/
	var $perpage = 25;

	/**
	* Current Page
	* Set the current page of records to be selected
	* @var integer
	* @access public
	*/
	var $currentpage = 1;

	/**
	* Total Pages
	* The total number of pages that are available
	* @var integer
	* @access public
	*/
	var $totalpages;

	/** 
	* Total Records
	* The total number of records that matched the last query
	* @var integer
	* @access public
	*/
	var $totalrecords;

	/**
	* Custom WHERE
	* This property should be used to set a custom WHERE clause when updating, deleting or selecting
	* @var string
	* @access public
	*/
	var $where;

	/**
	* Custom ORDER BY
	* This property should be used to set a custom ORDER BY clause when selecting records
	* @var string
	* @access public
	*/
	var $orderby;

	// Private vars:
	/**#@+
	* @access private
	*/
	var $_php;
	var $_parent;
	var $_relationships = array();
	var $_joins = array();
	var $_aliases = array();
	/**#@-*/

	/**
	* Sets the table name of the CRUD object
	*
	* @access public
	* @param string $table the table name
	*/
	function AutoCRUD_CRUD ($table) {
		$this->table = $table;
		$this->name = $table;
	}

	/**
	* Sets the parent of this CRUD object
	* 
	* @access public
	* @param AutoCRUD $obj the parent of the CRUD object
	*/
	function set_parent (&$obj) {
		$this->setParent(&$obj);
	}

	/**
	* @access public
	* @see AutoCRUD_CRUD::set_parent()
	*/
	function setParent(&$obj) {
		$this->_parent =& $obj;
	}

	/**
	* Returns the parent of the CRUD object
	*
	* @access public
	* @return AutoCRUD the parent of the CRUD object
	*/
	function &getParent() {
		return $this->_parent;
	}

	/**
	* Used to insert a new record, like this:
	* <code>
	* $crud->table->insert (array('title' => 'My test record', 'description' => 'A test!'));
	* </code>
	*
	* @access public
	* @param array $args array of the record data
	* @return mixed ID of the record inserted on success, an error object on failure
	*/
	function insert ($args) {
		// Make sure fields are valid
		$check = $this->_check_fields(&$args);
		if ($check !== true) return $check;

		// SQLSafe fields
		$args = $this->_sqlsafe($args);

		// Begin building SQL query
		$sql = "INSERT INTO `" . $this->table . "` (";

		$chop = false;
		foreach ($args as $key => $val) {
			$sql .= "`" . $key . "`, ";
			$chop = true;
		}
		
		if ($chop == true) { $sql = substr($sql, 0, -2); }
		$sql .= ") VALUES (";

		$chop = false;
		foreach ($args as $key => $val) {
			$sql .= $val . ", ";
			$chop = true;
		}

		if ($chop == true) { $sql = substr($sql, 0, -2); }
		$sql .= ")";

		// Execute query
		$result = $this->_parent->query($sql);

		// Got error?
		if (autocrud_is_error($result)) { return $result; }

		return $this->_parent->insert_id();
	}

	/**
	* Used to update an existing record, like this:
	* <code>
	* $crud->table->update (array('title' => 'Updated'), 23);
	* </code>
	*
	* @access public
	* @param array $args the new data of the record
	* @param mixed record ID of the record you want to update
	* @return mixed true on success, error object on failure
	*/
	function update($args, $id='') {
		// Make sure fields are valid
		$check = $this->_check_fields(&$args, false);
		if ($check !== true) return $check;

		// SQLSafe fields
		$args = $this->_sqlsafe($args);

		// Any args? If not, exit function
		if (count($args) == 0) { return false; }

		// Begin building SQL query
		$sql = "UPDATE `" . $this->table . "` SET ";

		foreach ($args as $key => $val) {
			$sql .= "`$key` = $val, ";
		}
		$sql = substr($sql, 0, -2);

		// Id been passed?
		if (!empty($id)) {
			$sql .= " WHERE `" . $this->key . "` = " . $this->quote($id) . " ";
		}

		// WHERE property set?
		if (!empty($this->where)) {
			if (empty($id)) $sql .= " WHERE ";
			$sql .= $this->where;
			$this->where = '';
		}

		// Execute query
		$result = $this->_parent->query($sql);

		// Got error?
		if (autocrud_is_error($result)) { return $result; }

		return true;
	}

	/**
	* Used to delete an existing record, like this:
	* <code>$crud->table->delete (23);</code>
	*
	* @access public
	* @param mixed $id the ID of the record you want to delete
	* @return mixed true on success, error object on failure
	*/
	function delete($id='') {
		// Begin SQL query
		$sql = "DELETE FROM `" . $this->table . "`";

		// Id been passed?
		if (!empty($id)) {
			$sql .= " WHERE `" . $this->key . "` = " . $this->quote($id) . " ";
		}

		// WHERE property set?
		if (!empty($this->where)) {
			if (empty($id)) $sql .= " WHERE ";
			$sql .= $this->where;
			$this->where = '';
		}

		// Execute query
		$result = $this->_parent->query($sql);

		if (autocrud_is_error($result)) { return $result; }

		return true;
	}

	/**
	* Used to select a list of records, like this:
	* <code>$records = $crud->table->select();</code>
	*
	* @access public
	* @param string $key the field name under which the records should be stored
	* @return array an array of records (or an empty array when there are no records)
	*/
	function select($key='') {
		if (empty($this->perpage)) { $this->perpage = 25; }
		if (empty($this->currentpage)) { $this->currentpage = 1; }
		if (!empty($this->select_key)) { $key = $this->select_key; $this->select_key = ''; }

		// Paging enabled? get count first
		if ($this->paging == true AND ($this->currentpage != 1 OR $this->perpage != 1)) {
			$sql = "SELECT COUNT(*) AS count FROM `" . $this->table . "`";

			if (!empty($this->where)) {
				$sql .= " WHERE " . $this->where;
			}

			$count = $this->_parent->query_first ($sql);
			$this->totalrecords = $count['count'];

			// Validate perpage and current page
			
			$this->totalpages = ceil($this->totalrecords/$this->perpage);
			if ($this->currentpage > $this->totalpages) { $this->currentpage = $this->totalpages; }
		}

		if ($this->paging == true) {
			// Write LIMIT clause
			$limit = " LIMIT " . ($this->perpage*($this->currentpage-1)) . ", " . $this->perpage;
		}

		// Begin SQL
		$sql = "SELECT `" . implode('`, `', $this->fields) . "` FROM `" . $this->table . "`";

		// WHERE?
		if (!empty($this->where)) {
			$sql .= " WHERE " . $this->where;
		}

		// ORDER BY?
		$this->orderby = trim($this->orderby);
		if (!empty($this->orderby)) {
			$sql .= " ORDER BY " . $this->orderby;
		}

		// LIMIT?
		if (isset($limit)) {
			$sql .= $limit;
		}

		// Get records
		$results = $this->_parent->sql_query ($sql, $key);

		$this->where = '';
		$this->orderby = '';
		$this->paging = false;

		if ($results == false) { $results = array(); }

		// Do joins
		$results = $this->_do_joins($results);

		return $results;
	}

	/**
	* Used to get a single record, like this:
	* <code>$record = $crud->table->get(23);</code>
	*
	* @access public
	* @param mixed $id you can pass the record ID to this function to get a specified record (optional)
	* @return array the record, as an associative array
	*/
	function get () {
		// Get function arguments, and check if ID has been passed
		$args = func_get_args();
		if (array_key_exists('0', $args)) {
			$id = $args['0'];
			$this->where = "`" . $this->key . "` = " . $this->quote($id) . " " . $this->where;
		}

		// Get results
		$this->paging = true;
		$this->current_page = 1;
		$this->perpage = 1;
		$results = $this->select();

		// Not an array -> probably an error, return it
		if (!is_array($results)) { return $results; }

		// Any results?
		if (count($results) == 0) {
			return false;
		} else {
			return array_shift($results);
		}
	}

	/**
	* Used to add a new alias for the CRUD object
	*
	* @access public
	* @param string $str the name of the new alias (mustn't already exist or be used for another table)
	* @return mixed true on success, error object on failure
	*/
	function addAlias($str) {
		if (in_array($str, $this->_aliases)) { return true; }
		if (empty($str)) { return $this->halt ('invalid-alias', 'The alias you tried to add is invalid'); }

		// Save alias
		$this->_aliases[] = $str;

		// Make myself available under the alias
		$this->_parent->$str =& $this;
		$this->_parent->$str->name = $str;

		return true;
	}

	/**
	* Returns the name of the CRUD object (mainly used with aliases)
	*
	* @access public
	* @return string the name of the CRUD object
	*/
	function getName() {
		return $this->name;
	}

	/**
	* Returns all the relationships that have been set with other table
	*
	* @access public
	* @return array the relationships
	*/
	function getRelationships() {
		return $this->_relationships;
	}

	/**
	* Used to setup a new relationship with another table
	*
	* @access public
	* @param AutoCRUD_CRUD $crud_obj the CRUD object of the other table
	* @param string $field1 the field in this table that's used in the relationship
	* @param string $field2 the field in the other table that's used in the relationship
	* @param string $type the type of relationship (one-to-one, one-to-many, many-to-many, child-parent)
	* @return mixed true on success, error object on failure
	*/
	function addRelationship (&$crud_obj, $field1, $field2, $type) {
		// Make sure crud obj has been passed
		if (strtolower(get_class($crud_obj)) != 'autocrud_crud') {
			return $this->halt ('invalid-object', 'Invalid object passed to relationship');
		}
		
		// Another relation ship already exists?
		if (isset($this->_relationships[$crud_obj->table]) == true) {
			return $this->halt ('relationship-exists', 'A relationship with this table already exists: `' . $crud_obj->table . '`');
		}

		// Create new relationship
		$rel = new AutoCRUD_Relationship(&$this, &$crud_obj);
		$this->_relationships[$crud_obj->table] =& $rel;

		// Set type
		$result = $rel->setType($type);
		
		// Set fields
		$rel->setFields($field1, $field2);

		// Create reverse relationship
		$rel->createReverseRelationship();

		return true;
	}

	/**
	* Returns a relationship object with another table
	*
	* @access public
	* @param string $table the name of the other table 
	* @return mixed the relationship object on success, an error object on failure
	*/
	function &getRelationship($table) {
		// Check if crud object exists
		if (!isset($this->_parent->$table) OR !is_object($this->_parent->$table)) {
			return $this->halt('no-relationship', 'No relationship with table/alias `' . $table . '`');
		}

		// Get real table name (alias could've been passed to this function);
		$table = $this->_parent->$table->table;	

		if ($this->relationship_exists($table) == false) {
			return $this->halt('no-relationship', 'No relationship with table/alias `' . $table . '`');
		}

		return $this->_relationships[$table];

	}

	/**
	* Used to check whether a relationship with another table exists
	*
	* @access public
	* @param string $table the name of the other table
	* @return bool whether the relationship exists or not
	*/
	function relationship_exists($table) {
		return isset($this->_relationships[$table]);
	}

	/**
	* Used to get related records from another table when selecting
	*
	* @access public
	* @param string $table the name of the other table
	* @return true on success, error object on failure
	*/
	function join($table) {
		// Check if crud object exists
		if (!isset($this->_parent->$table) OR !is_object($this->_parent->$table)) {
			return $this->halt('no-relationship', 'No relationship with table/alias `' . $table . '`');
		}

		// Get real table name (alias could've been passed to this function);
		$table = $this->_parent->$table->table;	

		// Does a many-to-many relationship exist?
		foreach ($this->_relationships as $r) {
			if ($r->getType() != 'many-to-many') continue;

			// Check if other object has relationship with the table passed
			$other = $r->getOther();

			if ($other->relationship_exists($table) == true) {
				$this->_joins[] = $table;
				return true;
			}
		}
		
		// Does a regular relationship exist?
		if (isset($this->_relationships[$table]) == true) {
			$this->_joins[] = $table;	
			return true;
		}

		// No relationships at all
		return $this->halt('no-relationship', 'No relationship with table `' . $table . '`. Unable to join.');
	}

	
	/**
	* Used to check if a unique error happened
	*
	* @access public
	* @param string $keyname the name of a specific index
	* @return bool whether a unique error has happened or not
	*/
	function unique_error ($keyname=null) {
		$errno = AutoCRUD_Error::get_last_errno(2);
		$errmsg = AutoCRUD_Error::get_last_errmsg(2);

		// No unique error?
		if ($errno != 1062) {
			return false;
		}
		
		// No keyname?
		if (!isset($keyname)) {
			return true;
		}

		// Parse key
		$split = explode(' ', $errmsg);
		$key = $split[count($split)-1];

		// Check if it's a valid index
		if (!isset($this->indexes[$key])) {
			$this->halt('invalid-index', 'Invalid index during unique error');
			return false;
		}

		// Check if they match
		if ($this->indexes[$key] != $keyname) {
			return false;
		}

		return true;
	}

	/**
	* Raw MySQL Function
	* @see AutoCRUD_MySQL::quote()
	*/
	function quote($str) { return $this->_parent->quote($str); }
	/**
	* Raw MySQL Function
	* @see AutoCRUD_MySQL::quoteInto()
	*/
	function quoteInto($stack, $needle) { return $this->_parent->quoteInto($stack, $needle); }

	/**#@+
	* @access private
	*/

	function _do_joins($results) {
		// No joins?
		if (count($this->_joins) == 0) { return $results; }

		// Copy joins and clear them
		$joins = $this->_joins;
		$this->_joins = array();

		foreach ($joins as $table) {
			// Many-to-many join?
			if (!isset($this->_relationships[$table])) {
				$results = $this->_join_many2many($results, $table);
				continue;
			}

			// Do join
			$results = $this->_relationships[$table]->doJoin($results);
		}
		
		return $results;
	}

	function _join_many2many($results, $table) {
		// Loop through relationships
		// and find one that is linked with the table we want
		reset($this->_relationships);
		while (list($key, $rel) = each($this->_relationships)) {
			// Get other CRUD object of relationship
			$other = $rel->getOther();

			// Does other CRUD object have a relationship with the table we're looking for?
			if ($other->relationship_exists($table) == true) {
				// Do many-to-many join
				$results = $rel->joinMany2Many($results, $table);
			}
		}

		return $results;
	}

	function _sqlsafe($args) {
		if (!is_array($args)) { return false; }

		$arr = array();
		foreach ($args as $key => $val) {
			$key = strval($key);
			if (in_array($key, $this->fields) == false) continue;

			$val = $this->quote($val);
			$arr[$key] = $val;
		}

		return $arr;
	}

	function _check_fields ($args, $insert=true) {
		if (!is_array($args)) { 
			$this->halt(10, 'Missing Field'); 
			return false;
		}

		// Check if all required fields are there (necessary when inserting a new record)
		if ($insert == true) {
			foreach ($this->required as $f) {
				if (!isset($args[$f])) {
					return $this->halt('missing-field', 'Missing field in data: `' . $f . '`');
				}
			}
		}

		// Check if required fields aren't empty
		foreach ($args as $key => $val) {
			$key = strval($key);
			if (in_array($key, $this->required) == false) continue;

			// Empty or not?
			if (empty($val) AND $val != '0') {
				return $this->halt('missing-field', 'Missing field in data: `' . $key . '`');
			}
		}

		// Check numeric fields
		foreach ($args as $key => $val) {
			$key = strval($key);
			if (in_array($key, $this->numeric) == false) continue;

			// Numeric or not?
			if (is_numeric($val) == false) {
				return $this->halt('not-numeric', 'Non-numeric value in a numeric field: `' . $key . '`');
			}
		}

		// Remove primary key
		if (isset($args[$this->key])) {
			unset($args[$this->key]);
		}

		return true;
	}

	function halt($errno, $errmsg) {
		$this->errno = $errno;
		$this->errmsg = $errmsg;
		$this->error = true;

		$error =& new AutoCRUD_error;
		$error->setMessage($errmsg);
		$error->setCode($errno);

		return $error;
	}

	/**#@-*/

}

?>