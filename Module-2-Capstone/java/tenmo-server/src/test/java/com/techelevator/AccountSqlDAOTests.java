package com.techelevator;

import org.junit.*;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import java.sql.SQLException;

import com.techelevator.tenmo.dao.AccountSqlDAO;

public class AccountSqlDAOTests {

	private static SingleConnectionDataSource dataSource;
	private AccountSqlDAO dao;
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		
		dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		dao = new AccountSqlDAO(jdbcTemplate);
	}
	
	@Test
	public void test() {
		
	}
}
