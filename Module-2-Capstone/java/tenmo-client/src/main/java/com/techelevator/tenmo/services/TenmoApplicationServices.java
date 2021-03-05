package com.techelevator.tenmo.services;

import java.util.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.*;

/*******************************************************************************************************
 * This is where you code Application Services required by your solution
 * 
 * Remember:  theApp ==> ApplicationServices  ==>  Controller  ==>  DAO
********************************************************************************************************/

public class TenmoApplicationServices {
	private final String URL;
	private String AUTH_TOKEN;
	private RestTemplate restTemplate = new RestTemplate();
	
	public TenmoApplicationServices(String apiBaseUrl, String userToken) {
		URL = apiBaseUrl;
		AUTH_TOKEN = userToken;
	}
	
	public Double getBalance(Integer userId) {
		return restTemplate.exchange(URL + "/users/" + userId + "/balance", HttpMethod.GET, makeAuthEntity(), Double.class).getBody();
	}
	
	public List<User> getAllUsers() {
		return Arrays.asList(restTemplate.exchange(URL + "/users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody());
	}
	
	public Transfers createTransfers(int userId, int otherId, Double amount) {
		return restTemplate.exchange(URL + "/users/" + userId + "/transfer/" + otherId, HttpMethod.POST, makeAuthEntityWithAmount(amount), Transfers.class).getBody();
	}
	
	public void doTransfer(Integer userId, Integer otherId, Transfers transfer) {
		restTemplate.exchange(URL + "/users/" + userId + "/transfer/" + otherId, HttpMethod.PUT, makeAuthEntityWithTransfer(transfer), Transfers.class);
	}
	
	private HttpEntity<?> makeAuthEntity() {
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(AUTH_TOKEN);
		HttpEntity<?> entity = new HttpEntity<>(header);
		return entity;
	}
	
	private HttpEntity<?> makeAuthEntityWithAmount(Double amount) {
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(AUTH_TOKEN);
		HttpEntity<?> entity = new HttpEntity<>(amount, header);
		return entity;
	}
	
	private HttpEntity<?> makeAuthEntityWithTransfer(Transfers transfer) {
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(AUTH_TOKEN);
		HttpEntity<?> entity = new HttpEntity<>(transfer, header);
		return entity;
	}
}
