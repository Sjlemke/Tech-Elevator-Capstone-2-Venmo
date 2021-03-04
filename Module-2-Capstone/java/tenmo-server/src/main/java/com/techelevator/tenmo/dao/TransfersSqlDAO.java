package com.techelevator.tenmo.dao;

import java.util.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfers;

@Component
public class TransfersSqlDAO implements TransfersDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public TransfersSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Transfers> getAllTransfersByAccountId(int accountId) {
		List<Transfers> allTransfers = new ArrayList<Transfers>();
		String getAllTransfersByAccountId = "SELECT * FROM transfers WHERE account_to = ? OR account_from = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(getAllTransfersByAccountId, accountId, accountId);
		while (results.next()) {
			Transfers singleTransfer = mapRowToTransfers(results);
			
			allTransfers.add(singleTransfer);
		}
		return allTransfers;
	}
	
	public void transferAmountTo(Transfers transfer, Double fromBalance, Double toBalance) {
		if (transfer.getAmount() > fromBalance) {
			String transferAmount = "UPDATE accounts SET balance = ? WHERE account_id = ?";
			jdbcTemplate.update(transferAmount, fromBalance - transfer.getAmount(), transfer.getAccountFrom());
			jdbcTemplate.update(transferAmount, toBalance + transfer.getAmount(), transfer.getAccountTo());
		}
	}
	
	public Transfers createTransfers(int accountFrom, int accountTo, double amount) {
		Integer nextId = 0;
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_transfer_id')");
		if(nextIdResult.next()) {               
			nextId = nextIdResult.getInt(1);    
		} else {                               
			throw new RuntimeException("Something went wrong while getting an id for the new transfer");
		}
		
		String sqlInsertTransfers = "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
   				"VALUES(?, ?, ?, ?, ?)";
		jdbcTemplate.update(sqlInsertTransfers, 2, 2, accountFrom, accountTo, amount);
		Transfers newTransfers = new Transfers();
        newTransfers.setAccountFrom(accountFrom);
        newTransfers.setAccountTo(accountTo);
        newTransfers.setAmount(amount);
        newTransfers.setTransferId(2);
        newTransfers.setTransferStatusId(2);
        newTransfers.setTransferTypeId(nextId);
        
        return newTransfers;
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
