package com.techelevator;

import org.junit.jupiter.api.*;
//import org.junit.*;
import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import java.sql.SQLException;

import com.techelevator.tenmo.dao.*;

public class AccountSqlDAOTests {

	private static SingleConnectionDataSource dataSource;
	private AccountDAO dao;
	
	@BeforeAll
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		
		dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterAll
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@BeforeEach
	public void setup() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		dao = new AccountSqlDAO(jdbcTemplate);
	}
	
	@AfterEach
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void checkGetBalanceCorrect() {
		Double correct = 1000.00;
		Double toCheck = dao.getBalanceByUserId(1);
		assertEquals(correct, toCheck);
	}
}
