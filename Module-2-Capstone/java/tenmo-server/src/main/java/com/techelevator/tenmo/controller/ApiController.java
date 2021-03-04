package com.techelevator.tenmo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.techelevator.tenmo.dao.*;

/*******************************************************************************************************
 * This is where you code any API controllers you may create
********************************************************************************************************/

@RestController
public class ApiController {
	private AccountDAO accountDAO;
	
	public ApiController(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	
	@RequestMapping(path = "/users/{user_id}/balance", method = RequestMethod.GET)
	public Double getBalance(@PathVariable(value = "user_id") int id) {
		return accountDAO.getBalanceByUserId(id);
	}
}
