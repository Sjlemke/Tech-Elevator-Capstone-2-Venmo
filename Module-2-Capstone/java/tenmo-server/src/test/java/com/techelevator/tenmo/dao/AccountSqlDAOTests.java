package com.techelevator.tenmo.dao;

import java.sql.SQLException;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.BeforeClass;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

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
		dao = new AccountSqlDAO(dataSource);
	}
}
