package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfers;


public interface TransfersDAO {
	
	public List<Transfers> getAllTransfersByUserId(int userId);

	public Transfers createTransfers(int userFromId, int userToId, double amount);
//	
//	public int getTransferStatusId();
//	
//	public int getTransferTypeId();
	
	public void transferAmountTo(Transfers transfer);
	
//	public List<Transfers> getAllTransfersById(int userId);
		
}
