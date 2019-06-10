/* 
 * (C) Copyright Kalidus DBA Skava, 2018-2019 all rights reserved
 * 
 * This code is provided on an as-is basis for the illustration of invoking the Skava API. It is not warranted to be fit for any other purpose. 
 * This code is not optimized for production purposes. 
 *  
 * 
 */
package com.skava.testSfoLogin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import org.springframework.web.client.HttpClientErrorException;

import com.skava.commerce.orchestration.client.api.CustomersApi;
import com.skava.commerce.orchestration.client.invoker.ApiClient;
import com.skava.commerce.orchestration.client.model.CustomerDetails;
import com.skava.commerce.orchestration.client.model.CustomerLoginRequest;
import com.skava.commerce.orchestration.client.model.CustomerLoginResponse;

public class TestSFOLogin {
	
/** 
 * This method uses the Skava SDK to log in the user and returns the sessions ID for same 
 * 
 * @param identity
 * @param password
 * @param businessId
 * @param storeId
 * @return
 * @throws Exception 
 */
	public static String getSessionForUser(String identity, String password, String storeId) throws Exception
	{
		// This block sets up the SDK to call our instance. 
		// We need a apiClient to connect to the instance
		// We need an api-key to penetrate the firewall. You should update it to match your instance, it is found on the admin business page. 
		
		ApiClient apiClient = new ApiClient();
		apiClient.setBasePath("https://stratus.skavacommerce.com/orchestrationservices/storefront/");
		apiClient.addDefaultHeader("x-api-key","okmOHLVAiP8WCzDKOivPQ81kCQb6WmDQ3dqFQfcS");
		apiClient.setDebugging(false); // Set to true to see details on calls 
		CustomersApi customersApi = new CustomersApi(apiClient);
		
		// These are ignored in the base call 
        String xVersion = null;
        String locale = null;
        
        // We want a new session, so pass in null
        String xSkSessionId = null;
        
        // We're going to make a request on Customer to Login 
        
        // Set the Userid, and PW here. 
        CustomerLoginRequest body = new CustomerLoginRequest();
        body.setIdentity(identity);
        // Hash the PW to match the front end's password creation 
        String hashedPw = HashPW.createFrontendHash(password);
        body.setPassword(hashedPw);
        body.setIdentityType(null);
       
        // Call skava instance
        CustomerLoginResponse loginResponse = null;
        try 
          {
          loginResponse = customersApi.login(storeId, body, locale, xSkSessionId, xVersion);
          }
        catch (HttpClientErrorException e)
        {
        	 System.out.println("Error "+e.getRawStatusCode()+" when calling login");
             System.out.println(e.getResponseBodyAsString());
             return null;
        }
        String sessionForUser = loginResponse.getSessionId();
        System.out.println("Valid login, session id:"+sessionForUser);
        return sessionForUser;
	}
	
	/**
	 * 
	 * This function uses the Skava SDK to retrieve the logged in user's details. 
	 * 
	 * @param session
	 * @param businessId
	 * @param storeId
	 */
	
	public static void getCustomer(String session, String businessId, String storeId)
	
	{
		
		
		ApiClient apiClient = new ApiClient();
		apiClient.setBasePath("https://stratus.skavacommerce.com/orchestrationservices/storefront/");
		apiClient.addDefaultHeader("x-api-key","okmOHLVAiP8WCzDKOivPQ81kCQb6WmDQ3dqFQfcS");
		apiClient.setDebugging(false); // Set to true to see details on calls 
		CustomersApi customersApi = new CustomersApi(apiClient);
		String locale = null;
		String version=null;
	    CustomerDetails response = customersApi.getCustomer(storeId, session, locale, version);
	    System.out.println("Customer details:"+response.toString());
	}

    /**
     * 
     * Main for testing 
     * 
     * @param args
     * @throws Exception
     */
	
	public static void main(String[] args) throws Exception {
		System.out.println("Starting customer driver");
		String password = "Skava@123";
		String identity = "skavattesting@gmail.com";
	   	String storeId = "132";
		String sessionId = getSessionForUser(identity,password, storeId);
		getCustomer(sessionId,"50","132");
	}
	
	
}
