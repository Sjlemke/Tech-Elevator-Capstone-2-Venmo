package com.techelevator.tenmo.dao;

import java.util.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfers;

public class TransfersSqlDAO implements TransfersDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public TransfersSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Transfers> getAllTransfers() {
		List<Transfers> allTransfers = new ArrayList<Transfers>();
		String getAllTransfers = "SELECT * FROM transfers";
		SqlRowSet results = jdbcTemplate.queryForRowSet(getAllTransfers);
		
		while (results.next()) {
			Transfers singleTransfer = mapRowToTransfers(results);
			
			allTransfers.add(singleTransfer);
		}
		return allTransfers;
	}
	
	public void transferAmountTo(Transfers transfer, Account fromAccount, Account toAccount) {
		if (transfer.getAmount() > fromAccount.getBalance()) {
			String transferAmount = "UPDATE accounts SET balance = ? WHERE account_id = ?";
			jdbcTemplate.update(transferAmount, fromAccount.getBalance() - transfer.getAmount(), transfer.getAccountFrom());
			jdbcTemplate.update(transferAmount, fromAccount.getBalance() + transfer.getAmount(), transfer.getAccountTo());
		}
	}
	
	private Transfers mapRowToTransfers(SqlRowSet results) {
		Transfers singleTransfer = new Transfers();
		singleTransfer.setTransferTypeId(results.getInt("transfer_type_id"));
		singleTransfer.setTransferStatusId(results.getInt("transfer_status_id"));
		singleTransfer.setAccountFrom(results.getInt("account_from"));
		singleTransfer.setAccountTo(results.getInt("account_to"));
		singleTransfer.setAmount(results.getDouble("amount"));
		return singleTransfer;
	}
}
