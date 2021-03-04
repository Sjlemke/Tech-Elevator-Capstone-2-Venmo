package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.*;

/*******************************************************************************************************
 * This is where you code any API controllers you may create
********************************************************************************************************/

@RestController
public class ApiController {
	private AccountDAO accountDAO;
	private UserDAO userDAO;
	
	public ApiController(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	public ApiController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	
	@RequestMapping(path = "/users/{user_id}/balance", method = RequestMethod.GET)
	public Double getBalance(@PathVariable(value = "user_id") int id) {
		return accountDAO.getBalanceByUserId(id);
	}
	
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userDAO.findAll();
	}
	    
}
