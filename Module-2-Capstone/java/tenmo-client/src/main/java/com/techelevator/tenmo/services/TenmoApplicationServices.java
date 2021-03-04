package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

/*******************************************************************************************************
 * This is where you code Application Services required by your solution
 * 
 * Remember:  theApp ==> ApplicationServices  ==>  Controller  ==>  DAO
********************************************************************************************************/

public class TenmoApplicationServices {
	private final String URL;
	private RestTemplate restTemplate = new RestTemplate();
	
	public TenmoApplicationServices(String apiBaseUrl) {
		URL = apiBaseUrl;
	}
	
	public Double getBalance(Integer userId) {
		return restTemplate.getForObject(URL + "/users/" + userId + "/balance", Double.class);
	}
}
