<?php
interface IService
{
	function save();
	
	function update();
	
	function deleteById($id);
	
	function delete();
	
	function getById($id);
	
	function getAll();
}
?>