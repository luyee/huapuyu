<?php
/**
* AutoCRUD_Relationship Class File
*
* This file holds the AutoCRUD_Relationship class, used for joins
*
* @license http://www.opensource.org/licenses/mit-license.php MIT License
* @package autocrud
*/

/**
* AutoCRUD_Relationship Class
*
* This class is used to handle the joins and relationships between tables
* 
* @package autocrud
*/
Class AutoCRUD_Relationship {

	// Private vars:
	/**#@+
	* @access private
	*/
	var $_owner_obj;
	var $_other_obj;

	var $_owner_field;
	var $_other_field;

	var $_type;
	var $_reverse_type;
	/**#@-*/

	/**
	* Used to pass in the owner CRUD object and the CRUD object of the other table
	*
	* @access public
	* @param AutoCRUD_CRUD $owner the owner CRUD object
	* @param AutoCRUD_CRUD $other the CRUD object of the other table
	*/
	function AutoCRUD_Relationship(&$owner, &$other) {
		if (strtolower(get_class($owner)) != 'autocrud_crud') {
			return false;
		}

		if (strtolower(get_class($other)) != 'autocrud_crud') {
			return $this->halt ('invalid-object', 'Invalid object passed to relationship');
		}

		$this->_owner_obj =& $owner;
		$this->_other_obj =& $other;

		return true;
	}

	/**
	* Used to get related records of a result set
	*
	* @access public
	* @param array $results the result set (an array of records)
	* @return array the result set with related records
	*/
	function doJoin($results) {
		if ($this->_type == 'one-to-one') {
			$results = $this->_joinOne2One($results);
		} elseif ($this->_type == 'child-parent') {
			$results = $this->_joinChildParent($results);
		} else { 
			$results = $this->_joinOne2Many($results);
		}
		
		return $results;
	}

	/**
	* Used to get related records of a result set in a Many-to-Many relationship
	*
	* @access public
	* @param array $results the result set (an array of records)
	* @param string $table the name of the other table
	* @return array the result set with related records
	*/
	function joinMany2Many($results, $table) {
		// Get relationship
		$other_rel = $this->_other_obj->getRelationship($table);
		
		if (autocrud_is_error($other_rel)) { return $this->halt('no-relationship'); }

		// Get other object and other fields
		$other_obj =& $other_rel->getOther();
		$owner_field = $other_rel->getOwnerField();
		$other_field = $other_rel->getOtherField();

		$ids_list = array();
		$newresults = array();
		$keys = array();

		// Get ID's from results
		foreach ($results as $key => $row) {
			// Get ID
			$id = $row[$this->_owner_field];
			$ids_list[] = $id;

			// Copy to a new array, and add it under its own ID
			$newresults[$id] = $row;

			// Save key (in case a specific key was being used)
			$keys[] = $key;
		}
		$results = $newresults;
		$ids_list = array_unique($ids_list);
		$ids_list = implode("', '", $ids_list);

		// Get records from in between table
		$this->_other_obj->where = "`" . $this->_other_field . "` IN ('" . $ids_list . "')";
		$between = $this->_other_obj->select();

		// Collect ID's from in between table
		$ids_list = array();
		foreach ($between as $row) {
			// Get ID
			$id = $row[$table];
			$ids_list[] = $id;

		}
		$ids_list = array_unique($ids_list);
		$ids_list = implode("', '", $ids_list);

		// Get records from other table
		$crud =& $this->_other_obj->getParent();
		$other_obj->where = "`" . $other_field . "` IN ('" . $ids_list . "')";
		$other = $other_obj->select($other_field);

		// Match up results with other records
		$name = $other_obj->getName();
		foreach ($between as $row) {
			$id_owner = $row[$this->_other_field];
			$id_other = $row[$owner_field];

			// Owner exists?
			if (!isset($results[$id_owner])) { continue; }

			// Other exists?
			if (!isset($other[$id_other])) { continue; }

			$owner_row =& $results[$id_owner];
			$other_row =& $other[$id_other];

			// Make sure owner table array property exists
			if (!isset($owner_row[$name])) { $owner_row[$name] = array(); }

			// Add other row to owner
			$owner_row[$name][] = $other_row;
		}
		
		// Return keys
		$newresults = array();
		$i = 0;
		foreach ($results as $row) {
			$key = $keys[$i];
			$newresults[$key] = $row;
			$i++;
		}
		$results = $newresults;

		return $results;
	}

	/**
	* Used to add a relationship on the other CRUD object type (in reverse)
	*
	* @access public
	*/
	function createReverseRelationship() {
		// Create relationship on other object
		$this->_other_obj->addRelationship(&$this->_owner_obj, $this->_other_field, $this->_owner_field, $this->_reverse_type);
	}

	/**
	* Returns the owner CRUD object
	* @access public
	* @return AutoCRUD_CRUD
	*/
	function &getOwner() { return $this->_owner_obj; }

	/**
	* Returns the other CRUD object
	* @access public
	* @return AutoCRUD_CRUD
	*/
	function &getOther() { return $this->_other_obj; }

	/**
	* Used to set the fields used in the relationship
	*
	* @access public
	* @param string $owner name of the owner field
	* @param string $other name of the other field
	*/
	function setFields($owner, $other) {
		$this->_owner_field = $owner;
		$this->_other_field = $other;

		return true;
	}

	/**
	* Returns the field of the owner table
	* @access public
	* @return string
	*/
	function getOwnerField() { return $this->_owner_field; }

	/**
	* Returns the field of the other table
	* @access public
	* @return string
	*/
	function getOtherField() { return $this->_other_field; }

	/**
	* Used to set the type of relationship
	*
	* @access public
	* @param string $type the type of relationship, valid types are one-to-one, 
	* many-to-one, one-to-many, many-to-many and child-parent
	* @return mixed true on success, error object on failure
	*/
	function setType($type) {
		// Check type of relationship:
		// - one-to-one: both tables only have one record, e.g. a article table with an author in the author table (and each article has only one author).
		// - many-to-one: the first table ($this->table) has many records which link to one record in the second table, e.g. comments table and a article table.
		// - one-to-many: the first table ($this->table) has one records which links to many records in the second table, basically the many-to-one relationship turned around
		// - many-to-many: the first table ($this->table) has many records which links to many records in the second table. This type of relationship must use a third table for the relation ship, for example a users table and groups table needs a membership table which links both tables.
		// - child-parent: this relation only concerns one category and is used when rows can have sub-rows. Field 1 refers to the 'parent' field and field 2 to the primary key field (the parent identifier anyway)
		$types = array('one-to-one', 'many-to-one', 'one-to-many', 'many-to-many', 'child-parent');

		if (in_array($type, $types) == false) {
			return $this->halt ('invalid-type', 'Invalid relationship type passed');
		}

		// Detect reverse type
		if ($type == 'many-to-one') { 
			$reverse_type = 'one-to-many';
		} elseif ($type == 'one-to-many') {
			$reverse_type = 'many-to-one';
		} else {
			$reverse_type = $type;
		}

		// Many-to-one is really a one-to-one relationship (for this table, for the other table it's one-to-many)
		if ($type == 'many-to-one') { $type = 'one-to-one'; }

		// Set type and reverse type
		$this->_type = $type;
		$this->_reverse_type = $reverse_type;
	}

	/**
	* Returns the relationship type
	* @access public
	* @return string
	*/
	function getType() {
		return $this->_type;
	}
	
	/**
	* Returns the reverse relationship type (e.g. many-to-one when the relationship type is one-to-many)
	* @access public
	* @return string
	*/
	function getReverseType() {
		return $this->_reverse_type;
	}

	
	//================================================================================
	// Private functions start here
	//================================================================================

	/**#@+
	* @access private
	*/

	function halt($code, $msg='') {
		return $this->_owner_obj->halt($code, $msg);
	}

	function _joinChildParent($results) {
		// Get ID's from results
		$ids_list = array();
		$newresults = array();
		$keys = array();

		foreach ($results as $key => $row) {
			$id = $row[$this->_other_field];
			$ids_list[] = $id;
			$ids_list[] = $row[$this->_other_field];
			$newresults[$id] = $row;
			$keys[] = $key;
		}
		$results = $newresults;
		$ids_list = array_unique($ids_list);
		$ids_list = implode("', '", $ids_list);

		// Get rows from other table
		$this->_other_obj->where = $this->_owner_field . " IN ('" . $ids_list . "') OR " . $this->_other_field . " IN ('" . $ids_list . "')";
		$other = $this->_other_obj->select($this->_other_field);

		// Match every row from other with correct parent
		foreach ($other as $row) {
			$parent = $row[$this->_owner_field];
			if (isset($results[$parent]) == false) { continue; }
			
			if (isset($results[$parent]['children']) == false) { 
				$results[$parent]['children'] = array();
			}

			$results[$parent]['children'][] = $row;
		}

		// Find parents and return keys
		$newresults = array();
		$i = 0;
		foreach ($results as $row) {
			$parent = $row[$this->_owner_field];

			// Try and find parent
			if (isset($other[$parent]) == true) {
				$row[$this->_owner_field] = $other[$parent];
			}

			// Return original key
			$key = $keys[$i];
			$newresults[$key] = $row;
			$i++;
		}
		$results = $newresults;

		return $results;
	}

	function _joinOne2Many ($results) {
		$ids_list = array();
		$newresults = array();
		$keys = array();

		// Get ID's from results
		foreach ($results as $key => $row) {
			// Get ID
			$id = $row[$this->_owner_field];
			$ids_list[] = $id;

			// Copy to a new array, and add it under its own ID
			$newresults[$id] = $row;

			// Save key (in case a specific key was being used)
			$keys[] = $key;
		}
		$results = $newresults;
		$ids_list = array_unique($ids_list);
		$ids_list = implode("', '", $ids_list);

		// Get rows from other table
		$this->_other_obj->where = "`" . $this->_other_field . "` IN ('" . $ids_list . "')";
		$other = $this->_other_obj->select();

		// Match other results with results
		$name = $this->_other_obj->getName();
		foreach ($other as $row) {
			$id = $row[$this->_other_field];

			// Make sure that array for other rows exist on owner
			if (isset($results[$id][$name]) == false OR !is_array($results[$id][$name])) {
				$results[$id][$name] = array();
			}
			
			// Add row to owner
			$results[$id][$name][] = $row;	
		}

		// Return keys
		$newresults = array();
		$i = 0;
		foreach ($results as $row) {
			$key = $keys[$i];
			$newresults[$key] = $row;
			$i++;
		}
		$results = $newresults;

		return $results;
	}

	function _joinOne2One ($results) {
		// Get ID's from results
		$ids_list = array();
		foreach ($results as $row) {
			$ids_list[] = $row[$this->_owner_field];
		}
		$ids_list = array_unique($ids_list);
		$ids_list = implode("', '", $ids_list);

		// Now select rows from other table
		$this->_other_obj->where = "`" . $this->_other_field . "` IN ('" . $ids_list . "')";
		$other = $this->_other_obj->select($this->_other_field);

		// Now match each result up
		$newresults = array();
		foreach ($results as $key => $row) {
			// Get id of row
			$id = $row[$this->_owner_field];

			// Does other row exist?
			if (isset($other[$id])) {
				// Copy other row
				$row[$this->_owner_field] = $other[$id];
			}

			$newresults[$key] = $row;
		}
		$results = $newresults;

		return $results;
	}

	/**#@-*/

}


?>