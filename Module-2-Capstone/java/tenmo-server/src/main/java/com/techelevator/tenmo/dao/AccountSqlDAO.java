package com.techelevator.tenmo.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class AccountSqlDAO implements AccountDAO {
	private JdbcTemplate jdbcTemplate;

    public AccountSqlDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
    @Override
    public Double getBalanceByUserId(Integer userId) {
    	String getBalance = "SELECT balance " +
    						"FROM accounts " +
    						"JOIN users ON users.user_id = accounts.user_id " +
    						"WHERE accounts.user_id = ?";
    	SqlRowSet results = jdbcTemplate.queryForRowSet(getBalance, userId);
    	results.next();
    	return results.getDouble(1);
    }
}
