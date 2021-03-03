package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfers;


public interface TransfersDAO {
	
	public List<Transfers> getAllTransfers();

	public double getAmount();
	
	public int getTransferStatusId();
	
	public int getTransferTypeId();
	
	public int transferAmountTo(int fromUserId, int toUserId);
	
	public List<Transfers> getAllTransfersById(int userId);
		
}
