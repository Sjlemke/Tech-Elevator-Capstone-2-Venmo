package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

public interface AccountDAO {	
	Double getBalanceByUserId(Integer userId);
	
	int getAccountIdByUserId(int userId);
}
