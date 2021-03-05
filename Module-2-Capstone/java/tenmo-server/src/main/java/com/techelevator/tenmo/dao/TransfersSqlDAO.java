package com.techelevator.tenmo.dao;

import java.util.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Transfers;

@Component
public class TransfersSqlDAO implements TransfersDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public TransfersSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Transfers> getAllTransfersByUserId(int userId) {
		String getAccount = "SELECT * FROM accounts WHERE user_id = ?";
		SqlRowSet fromAccountResults = jdbcTemplate.queryForRowSet(getAccount, userId);
		fromAccountResults.next();
		int accountId = fromAccountResults.getInt("account_id");
		List<Transfers> allTransfers = new ArrayList<Transfers>();
		String getAllTransfersByAccountId = "SELECT * FROM transfers WHERE account_to = ? OR account_from = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(getAllTransfersByAccountId, accountId, accountId);
		while (results.next()) {
			Transfers singleTransfer = mapRowToTransfers(results);
			
			allTransfers.add(singleTransfer);
		}
		return allTransfers;
	}
	
	public void transferAmountTo(Transfers transfer) {
		String getAccount = "SELECT * FROM accounts WHERE account_id = ?";
		SqlRowSet fromAccountResults = jdbcTemplate.queryForRowSet(getAccount, transfer.getAccountFrom());
		fromAccountResults.next();
		double fromBalance = fromAccountResults.getDouble("balance");
		SqlRowSet toAccountResults = jdbcTemplate.queryForRowSet(getAccount, transfer.getAccountTo());
		toAccountResults.next();
		double toBalance = toAccountResults.getDouble("balance");
		
		String transferAmount = "UPDATE accounts SET balance = ? WHERE account_id = ?";
		jdbcTemplate.update(transferAmount, fromBalance - transfer.getAmount(), transfer.getAccountFrom());
		jdbcTemplate.update(transferAmount, toBalance + transfer.getAmount(), transfer.getAccountTo());
	}
	
	public Transfers createTransfers(int fromUserId, int toUserId, double amount) {
		String getAccount = "SELECT * FROM accounts WHERE user_id = ?";
		SqlRowSet fromAccountResults = jdbcTemplate.queryForRowSet(getAccount, fromUserId);
		fromAccountResults.next();
		int fromAccountId = fromAccountResults.getInt("account_id");
		SqlRowSet toAccountResults = jdbcTemplate.queryForRowSet(getAccount, toUserId);
		toAccountResults.next();
		int toAccountId = toAccountResults.getInt("account_id");
		
		Integer nextId = 0;
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_transfer_id')");
		if(nextIdResult.next()) {               
			nextId = nextIdResult.getInt(1);    
		} else {                               
			throw new RuntimeException("Something went wrong while getting an id for the new transfer");
		}
		
		String sqlInsertTransfers = "INSERT INTO transfers(transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
   				"VALUES(?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sqlInsertTransfers, nextId, 2, 2, fromAccountId, toAccountId, amount);
		Transfers newTransfers = new Transfers();
        newTransfers.setAccountFrom(fromAccountId);
        newTransfers.setAccountTo(toAccountId);
        newTransfers.setAmount(amount);
        newTransfers.setTransferId(2);
        newTransfers.setTransferStatusId(2);
        newTransfers.setTransferTypeId(nextId);
        
        return newTransfers;
	}
	
	private Transfers mapRowToTransfers(SqlRowSet results) {
		Transfers singleTransfer = new Transfers();
		singleTransfer.setTransferTypeId(results.getInt("transfer_type_id"));
		singleTransfer.setTransferId(results.getInt("transfer_id"));
		singleTransfer.setTransferStatusId(results.getInt("transfer_status_id"));
		singleTransfer.setAccountFrom(results.getInt("account_from"));
		singleTransfer.setAccountTo(results.getInt("account_to"));
		singleTransfer.setAmount(results.getDouble("amount"));
		return singleTransfer;
	}
}
