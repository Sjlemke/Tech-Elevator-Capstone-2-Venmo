package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfers;


public interface TransfersDAO {
	
	public List<Transfers> getAllTransfers();

//	public double getAmount();
//	
//	public int getTransferStatusId();
//	
//	public int getTransferTypeId();
	
	public void transferAmountTo(Transfers transfer, Account fromAccount, Account toAccount);
	
//	public List<Transfers> getAllTransfersById(int userId);
		
}
