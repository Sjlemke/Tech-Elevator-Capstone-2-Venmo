package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfers;


public interface TransfersDAO {
	
	public List<Transfers> getAllTransfers();

	public Transfers createTransfers(int userFromId, int userToId, double amount);
//	
//	public int getTransferStatusId();
//	
//	public int getTransferTypeId();
	
	public void transferAmountTo(Transfers transfer, Double fromBalance, Double toBalance);
	
//	public List<Transfers> getAllTransfersById(int userId);
		
}
