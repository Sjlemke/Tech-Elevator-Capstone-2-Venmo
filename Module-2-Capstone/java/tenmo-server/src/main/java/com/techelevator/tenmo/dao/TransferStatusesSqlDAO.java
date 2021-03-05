package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class TransferStatusesSqlDAO implements TransferStatusesDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public TransferStatusesSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public String getStatusDescById(int statusId) {
		String getDescription = "SELECT transfer_status_desc FROM transfer_statuses WHERE transfer_status_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(getDescription, statusId);
		results.next();
		return results.getString("transfer_status_desc");
	}
}
