<?php
/**
* ActiveRecord File
*
* This file contains the ActiveRecord class
*
* @license http://www.opensource.org/licenses/mit-license.php MIT License
* @package autocrud
*/

/**
* AutoCRUD ActiveRecord Class
*
* This class is used to create a new ActiveRecord, and should be created like this:
* <code>
* $record = new AutoCRUD_ActiveRecord (&$crud->table);
* </code>
* 
* @package autocrud
*/
Class AutoCRUD_ActiveRecord {
	// Public vars:

	// Private vars:
	/**#@+
	* @access private
	*/
	var $_crud_obj;
	var $_loaded = false;
	var $_default = array();
	/**#@-*/


	/**
	* Use the constructor to pass the right CRUD object
	*
	* @access public
	* @param AutoCRUD_CRUD $crud_obj the CRUD object of the table you want to use
	*/
	function AutoCRUD_ActiveRecord (&$crud_obj) {
		if (strtolower(get_class($crud_obj)) != 'autocrud_crud') {
			return $this->halt ('invalid-object', 'You need to pass a AutoCrud_CRUD object to the ActiveRecord');
		}

		$this->_crud_obj =& $crud_obj;
	}

	/**
	* Used to check whether a record has been loaded or not
	*
	* @access public
	* @return bool
	*/
	function isLoaded() {
		return $this->_loaded;
	}

	/**
	* Used to load an existing record from the table
	*
	* @access public
	* @param mixed $id the record ID of the record you want to load
	* @return mixed returns true on success or an error object on failure
	*/
	function load($id) {
		// Try and get record
		$record = $this->_crud_obj->get($id);

		if ($record == false) {
			return $this->halt ('invalid-record', 'Invalid Record');
		}

		// Load fields
		foreach ($this->_crud_obj->fields as $f) {
			if (!isset($record[$f])) continue;

			$this->$f = $record[$f];
		}

		$this->_loaded = true;
		$this->_default = $record;

		return true;
	}

	/**
	* Used to save the record (insert or update)
	*
	* @access public
	* @return mixed true on success or error object on failure
	*/
	function save() {
		// Get args
		$vars = get_object_vars($this);
		$args = array();

		foreach ($vars as $key => $val) {
			if (in_array($key, $this->_crud_obj->fields) == false) { continue; }
			$args[$key] = $val;
		}

		// Update or insert?
		if ($this->_loaded == false) {
			// Insert:
			$result = $this->_crud_obj->insert ($args);

			if (is_object($result) == false) {
				$this->load($result);
			}
		} else {
			// Update:
			$result = $this->_crud_obj->update ($args, $args[$this->_crud_obj->key]);
		}

		return $result;
	}

	/**
	* Used to delete a record
	*
	* @access public
	* @return mixed true on success or error object on failure
	*/
	function delete () {
		// Is a record loaded?
		if ($this->_loaded == false) {
			return $this->halt('no-record', 'You haven\'t loaded a record yet');
		}

		// Get id
		$key = $this->_crud_obj->key;
		$id = $this->$key;

		// Delete record
		$result = $this->_crud_obj->delete ($id);

		// Clear object
		$this->_loaded = false;
		$this->reset(array());

		return $result;
	}

	/**
	* Used to reset all the values of the record when you've made any changes
	*
	* @access public
	* @return bool always returns true
	*/
	function reset($default=null) {
		if (isset($default)) {
			$this->_default = $default;
		}

		foreach ($this->_crud_obj->fields as $f) {
			if (empty($this->_default[$f])) {
				$this->$f = '';
			} else {
				$this->$f = $this->_default[$f];
			}
		}

		return true;
	}

	/**
	* Used to return an error
	* 
	* @access private
	* @return AutoCRUD_Error
	*/
	function &halt($errno, $errmsg) {
		$error =& new AutoCRUD_Error();
		$error->setCode($errno);
		$error->setMessage ($errmsg);

		return $error;
	}

}


?>