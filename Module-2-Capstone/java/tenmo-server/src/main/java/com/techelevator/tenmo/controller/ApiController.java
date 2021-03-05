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
@PreAuthorize("isAuthenticated()")
public class ApiController {
	private AccountDAO accountDAO;
	private UserDAO userDAO;
	private TransfersDAO transfersDAO;
	
	public ApiController(AccountDAO accountDAO, UserDAO userDAO, TransfersDAO transfersDAO) {
		this.accountDAO = accountDAO;
		this.userDAO = userDAO;
		this.transfersDAO = transfersDAO;
	}
	
	@RequestMapping(path = "/users/{user_id}/balance", method = RequestMethod.GET)
	public Double getBalance(@PathVariable(value = "user_id") int id) {
		return accountDAO.getBalanceByUserId(id);
	}
	
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userDAO.findAll();
	}
	
	@RequestMapping(path = "/users/{user_id}/transfer/{other_id}", method = RequestMethod.POST)
	public Transfers createTransfers(@PathVariable(value = "user_id") int fromUserId, @PathVariable(value = "other_id") int toUserId, @RequestBody Double amount) {
		return transfersDAO.createTransfers(fromUserId, toUserId, amount);
	}
	
	@RequestMapping(path = "/users/{user_id}/transfer/{other_id}", method = RequestMethod.PUT)
	public void doTransfer(@PathVariable(value = "user_id") int fromId, @PathVariable(value = "other_id") int toId,
						   @RequestBody Transfers transfer) {
		transfersDAO.transferAmountTo(transfer);
	}
}
