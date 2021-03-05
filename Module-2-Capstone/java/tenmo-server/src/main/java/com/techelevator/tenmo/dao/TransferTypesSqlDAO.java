package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class TransferTypesSqlDAO implements TransferTypesDAO {
	private JdbcTemplate jdbcTemplate;
	
	public TransferTypesSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public String getTransferTypeById(int typeId) {
		String getDescription = "SELECT transfer_type_desc FROM transfer_types WHERE transfer_type_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(getDescription, typeId);
		results.next();
		return results.getString("transfer_type_desc");
	}
	
}
