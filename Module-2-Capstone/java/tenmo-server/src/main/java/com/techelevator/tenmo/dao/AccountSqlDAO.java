package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Account;

@Component
public class AccountSqlDAO implements AccountDAO {
	private JdbcTemplate jdbcTemplate;

    public AccountSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public Double getBalanceByUserId(Integer userId) {
    	String getBalance = "SELECT balance " +
    						"FROM accounts " +
    						"JOIN users ON users.user_id = accounts.user_id " +
    						"WHERE accounts.user_id = ?";
    	SqlRowSet results = jdbcTemplate.queryForRowSet(getBalance, userId);
    	results.next();
    	return results.getDouble("balance");
    }
    
    @Override
    public int getAccountIdByUserId(int userId) {
    	String getAccountId = "SELECT account_id FROM accounts JOIN users ON users.user_id = accounts.user_id WHERE users.user_id = ?";
    	SqlRowSet results = jdbcTemplate.queryForRowSet(getAccountId, userId);
    	results.next();
    	return results.getInt("account_id");
    }
}
