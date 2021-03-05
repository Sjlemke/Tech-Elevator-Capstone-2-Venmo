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
	
	public List<Transfers> getAllTransfersByUserId(int userId) {
		return Arrays.asList(restTemplate.exchange(URL + "/users/" + userId + "/transfer_history",  HttpMethod.GET, makeAuthEntity(), Transfers[].class).getBody());
		
		
	}
	public Transfers getSingleTransfer(int userId, int transferId) {
		return restTemplate.exchange(URL + "/users/" + userId + "/wantedTransfer/" + transferId, HttpMethod.GET, makeAuthEntity(), Transfers.class).getBody();
	}
	
	public int getAccountIdByUserId(int userId) {
		return restTemplate.exchange(URL + "/users/" + userId + "/account", HttpMethod.GET, makeAuthEntity(), int.class).getBody();
	}
	
	public String getUsernameByAccountId(int accountId) {
		return restTemplate.exchange(URL + "/accounts/" + accountId, HttpMethod.GET, makeAuthEntity(), String.class).getBody();
	}
	
	public String getDescByStatusId(int statusId) {
		return restTemplate.exchange(URL + "/statuses/" + statusId, HttpMethod.GET, makeAuthEntity(), String.class).getBody();
	}
	
	public String getDescByTypeId(int typeId) {
		return restTemplate.exchange(URL + "/transferTypes/" + typeId, HttpMethod.GET, makeAuthEntity(), String.class).getBody();
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
