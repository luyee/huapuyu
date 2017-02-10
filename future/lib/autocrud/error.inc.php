<?php
/**
* Error Class File
*
* This file contains all the error handling classes and functions
*
* @license http://www.opensource.org/licenses/mit-license.php MIT License
* @package autocrud
*/


/**
* Used to check if a variable hold an error object or not
*
* @access public
* @param mixed $var
* @return bool returns true when it's a error object, and false when it's not
*/
function autocrud_is_error($var) {
	if (is_object($var) AND strtolower(get_class($var)) == 'autocrud_error') {
		return true;
	} else {
		return false;
	}
}

/**
* AutoCRUD Error Class
*
* This class is used to create a new error object, like this
* <code>
* $error = new AutoCRUD_Error;
* </code>
* 
* @package autocrud
*/
Class AutoCRUD_Error {

	// Private vars:
	/**#@+
	* @access private
	*/
	var $_codes = array();
	var $_message = array();
	/**#@-*/

	/**
	* The constructor will also immediately add the object to the error stack
	*
	* @access public
	*/
	function AutoCRUD_Error() {
		$stack =& AutoCRUD_Error::stack();
		$stack[] =& $this;
	}

	/**
	* Used to set an error message
	*
	* @access public
	* @param string $msg the error message
	* @param integer $count the index number of the error message (if you want to use multiple error messages)
	*/
	function setMessage($msg, $count=1) {
		if (!is_numeric($count)) { $count = 1; }

		$this->_messages[$count] = $msg;
	}

	/**
	* Used to set an error code
	*
	* @access public
	* @param string $code the error code
	* @param integer $count the index number of the error code (if you want to use multiple error codes)
	*/
	function setCode($code, $count=1) {
		if (!is_numeric($count)) { $count = 1; }

		$this->_codes[$count] = $code;
	}

	/**
	* Used to retrieve an error message
	*
	* @access public
	* @param integer $count the index number of the error message
	* @return string the error message
	*/
	function getMessage($which=1) {
		if (!is_numeric($which)) { $which = 1; }
		if (!isset($this->_messages[$which])) { return NULL; }

		return $this->_messages[$which];
	}

	/**
	* Used to retrieve an error code
	*
	* @access public
	* @param integer $count the index number of the error code
	* @return string the error code
	*/
	function getCode($which=1) {
		if (!is_numeric($which)) { $which = 1; }
		if (!isset($this->_codes[$which])) { return NULL; }

		return $this->_codes[$which];
	}

	/**
	* Used to get the code of the last error that happened
	*
	* @static should only be called statically, i.e. AutoCRUD_Error::get_last_errno();
	* @param integer $count the index number of the error code
	* @return string the error code
	*/
	function get_last_errno($which=1) {
		$last_error =& AutoCRUD_Error::get_last_error();
		if (!is_object($last_error)) { return false; }

		$errno = $last_error->getCode($which);
		return $errno;
	}

	/**
	* Alias of the get_last_errno() function
	*
	* @see AutoCRUD_Error::get_last_errno()
	*/
	function get_last_errcode($which=1) { return AutoCRUD_Error::get_last_errno($which); }

	/**
	* Used to get the message of the last error that happened
	*
	* @static should only be called statically, i.e. AutoCRUD_Error::get_last_errmsg();
	* @param integer $count the index number of the error code
	* @return string the error message
	*/
	function get_last_errmsg($which=1) {
		$last_error =& AutoCRUD_Error::get_last_error();
		if (!is_object($last_error)) { return false; }

		$errmsg = $last_error->getMessage($which);
		return $errmsg;
	}

	/**
	* Used to get the last error that happened
	*
	* @static should only be called statically, i.e. AutoCRUD_Error::get_last_error();
	* @return AutoCRUD_Error the last error
	*/
	function &get_last_error() {
		$stack =& AutoCRUD_Error::stack();

		// No errors? return false
		if (count($stack) == 0) { $false = false; return $false; }

		$last_error =& $stack[count($stack)-1];
		return $last_error;
	}

	/**
	* Used to get an array of all the errors that have happened
	*
	* @static should only be called statically, i.e. AutoCRUD_Error::stack();
	* @return array the errors
	*/
	function &stack() {
		static $stack = array();
		return $stack;
	}

}

?>