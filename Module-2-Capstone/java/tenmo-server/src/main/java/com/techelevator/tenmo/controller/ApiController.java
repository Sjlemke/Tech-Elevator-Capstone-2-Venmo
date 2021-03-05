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
	private TransferStatusesDAO transferStatusesDAO;
	private TransferTypesDAO transferTypesDAO;
	
	public ApiController(AccountDAO accountDAO, UserDAO userDAO, TransfersDAO transfersDAO, TransferStatusesDAO transferStatusesDAO, TransferTypesDAO transferTypesDAO) {
		this.accountDAO = accountDAO;
		this.userDAO = userDAO;
		this.transfersDAO = transfersDAO;
		this.transferStatusesDAO = transferStatusesDAO;
		this.transferTypesDAO = transferTypesDAO;
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
	@RequestMapping(path = "/users/{user_id}/wantedTransfer/{transfer_id}", method = RequestMethod.GET)
	public Transfers getSingleTransfers(@PathVariable(value = "user_id") int fromUserId, @PathVariable(value = "transfer_id") int transferId) { 
		return transfersDAO.getSingleTransfer(transferId);
	}

	@RequestMapping(path = "/users/{user_id}/transfer_history", method = RequestMethod.GET)
	public List<Transfers> getAllTransfersByUserId(@PathVariable(value = "user_id") int userId) {
		
		return transfersDAO.getAllTransfersByUserId(userId);
	}
	
	@RequestMapping(path = "/users/{user_id}/account", method = RequestMethod.GET)
	public int getAccountIdByUserid(@PathVariable(value = "user_id") int userId) {
		return accountDAO.getAccountIdByUserId(userId);
	}
	
	@RequestMapping(path = "/accounts/{account_id}", method = RequestMethod.GET)
	public String getUsernameByAccountId(@PathVariable(value = "account_id") int accountId) {
		return userDAO.getUsernameByAccountId(accountId);
	}
	
	@RequestMapping(path = "/statuses/{status_id}", method = RequestMethod.GET)
	public String getDescriptionbyStatusId(@PathVariable(value = "status_id") int statusId) {
		return transferStatusesDAO.getStatusDescById(statusId);
	}
	
	@RequestMapping(path = "/transferTypes/{type_id}", method = RequestMethod.GET)
	public String getDescriptionbyTypeId(@PathVariable(value = "type_id") int typeId) {
		return transferTypesDAO.getTransferTypeById(typeId);
	}
}
