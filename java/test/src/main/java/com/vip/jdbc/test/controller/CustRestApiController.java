package com.vip.jdbc.test.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vip.jdbc.test.entity.Cust;
import com.vip.jdbc.test.service.CustService;

@Controller
@RequestMapping("/test")
public class CustRestApiController {

	@Autowired
	private CustService custService;

	@RequestMapping(value = "/custs/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Cust>> getByIds(@PathVariable long id) {
		List<Long> list = new ArrayList<Long>(4);
		list.add(id);
		list.add(id + 1);
		list.add(id + 2);
		list.add(id + 3);

		List<Cust> custs = custService.selectByIds(list);

		return new ResponseEntity<List<Cust>>(custs, HttpStatus.OK);
	}

	@RequestMapping(value = "/cust/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cust> getById(@PathVariable long id) {
		Cust cust = null;
		// try {
		cust = custService.selectById(id);
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return new ResponseEntity<Cust>(cust, HttpStatus.OK);
	}

	@RequestMapping(value = "/cust", method = RequestMethod.POST)
	public ResponseEntity<Cust> createUser(@RequestBody Cust cust)  {
		// Cust cust = new Cust();
		// cust.setId(id);
		// cust.setName("zhuzhen");
		 try {
			custService.insert(cust);
		 } catch (IOException e) {
		 e.printStackTrace();
		 }

		return new ResponseEntity<Cust>(cust, HttpStatus.OK);
	}

	@RequestMapping(value = "/cust/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Long> deleteUserByPrimaryKey(@PathVariable long id) throws InterruptedException {
		custService.deleteById(id);
		return new ResponseEntity<Long>(id, HttpStatus.OK);
	}

	@RequestMapping(value = "/cust", method = RequestMethod.PUT)
	public ResponseEntity<Cust> updateUserByPrimaryKeySelective(@RequestBody Cust cust) throws InterruptedException {
		custService.update(cust);
		return new ResponseEntity<Cust>(cust, HttpStatus.OK);
	}

}
