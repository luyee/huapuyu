package com.anders.youyou.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anders.youyou.entity.Cust;

@Controller
@RequestMapping("/youyou")
public class CustRestApiController {

	@RequestMapping(value = "/cust/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cust> getById(@PathVariable long id) {
		Cust cust = new Cust();
		cust.setId(id);
		return new ResponseEntity<Cust>(cust, HttpStatus.OK);
	}

	@RequestMapping(value = "/cust", method = RequestMethod.POST)
	public ResponseEntity<Cust> create(@RequestBody Cust cust)  {
		return new ResponseEntity<Cust>(cust, HttpStatus.OK);
	}

	@RequestMapping(value = "/cust/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Long> deleteById(@PathVariable long id) {
		return new ResponseEntity<Long>(id, HttpStatus.OK);
	}

	@RequestMapping(value = "/cust", method = RequestMethod.PUT)
	public ResponseEntity<Cust> update(@RequestBody Cust cust) {
		return new ResponseEntity<Cust>(cust, HttpStatus.OK);
	}

}