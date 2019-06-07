package com.skava.testSfoLogin;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.skava.commerce.catalog.client.api.SkUsApi;
import com.skava.commerce.catalog.client.invoker.ApiClient;
import com.skava.commerce.catalog.client.model.Sku;

public class GetSku {
	
	private static ApiClient setupApi(StorefrontDetails sfDetails)
	{
	ApiClient apiClient = new ApiClient();
	apiClient.setBasePath("https://"+sfDetails.getInstanceName()+"/catalogservices");
	apiClient.setDebugging(true);
	apiClient.addDefaultHeader("x-api-key",sfDetails.getApiKey());
	return apiClient;
	}
	
	public static void getSku(StorefrontDetails sfDetails, String sku)
	{
		ApiClient apiClient = setupApi(sfDetails);
		SkUsApi skuApi = new SkUsApi(apiClient);
		Long xCollectionId = sfDetails.getCollection();
        String xAuthToken = sfDetails.getAuthToken();
        String catalogId = sfDetails.getCatalog();
        String projectId = sfDetails.getProject();
        String skuId = sku;
        String xVersion = null;
        String locale = null;
        Sku skuResponse = null; 
        try
         {
          skuResponse = skuApi.getSku(xCollectionId, xAuthToken, catalogId, projectId, skuId, xVersion, locale); 			 
          }
      catch (HttpClientErrorException e)
        {
  	    System.out.println("Error "+e.getRawStatusCode()+" getting "+sku+" From catalog "+catalogId);
        System.out.println(e.getResponseBodyAsString());
        return;
        }
      catch (ResourceAccessException e)
        {
     	System.out.println("Resource Access Exception");   	e.printStackTrace();
     	System.out.println(e.getMessage());
     	return;
     	}
      catch (Exception e)
        {
        e.printStackTrace();
        return;
        }
       
      System.out.println("Sku response: "+skuResponse.toString());
      return;
      }	
	
	 public static void main(String[] args) throws URISyntaxException, IOException {
		   
		    String sharedSecret = "7f4e5027d923007c58ac4179a2bf210d50d32703d2504e417a777885dbc9bd88b9da364ceb376fe54aa6dfff2ff5565de9f5c787067cf1a56d701166ac315a87";
		    String businessId = "50";
		    String authToken = SkavaTokenGeneratorSample.generateKey(sharedSecret, businessId);
		    StorefrontDetails sfd = new StorefrontDetails();
		    sfd.setAuthToken(authToken);
		    sfd.setBusiness(50l);
		    sfd.setCollection(268);
		    sfd.setCatalog("master");
		    sfd.setProject("default");
		    sfd.setInstanceName("stratus.skavacommerce.com");
		    sfd.setApiKey("okmOHLVAiP8WCzDKOivPQ81kCQb6WmDQ3dqFQfcS");
		    String sku = "110007150";
		    getSku(sfd,sku);
		    }
	

}
