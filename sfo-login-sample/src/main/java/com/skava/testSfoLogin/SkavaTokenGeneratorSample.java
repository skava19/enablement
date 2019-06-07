package com.skava.testSfoLogin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.security.core.token.Sha512DigestUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class SkavaTokenGeneratorSample {
  
  
  public static String testTokenGen(StorefrontDetails sfd) 
        {
	    String sharedSecret = sfd.getSharedSecret();
	    String businessId = ((Long)sfd.getBusiness()).toString();
	    return generateKey(sharedSecret, businessId);
        }

  public static String generateKey(String sharedSecret, String businessId)	    
{
        String userid = "xxx";	   
        String businessAdminClaims = "[{\"roles\":{\"ROLE_BUSINESS_ADMIN\":{\"business\":{\""+businessId+"\":{}},\"type\":\"STANDARD\"}}}]";
	    Map<String, Object> claims = new HashMap<String, Object>();  
	    claims.put("authorities",businessAdminClaims );
	    claims.put("username", userid);
	    claims.put("created", Long.valueOf(System.currentTimeMillis()));
	    String key = generateKeyWithClaims(sharedSecret, claims, userid);
	    System.out.println("admin key with claims " + key);
	    return key;
	  }
  
  private static String generateKeyWithClaims(String sharedSecret, Map<String, Object> claims, String userid) {
    return Jwts.builder().setSubject(userid).setClaims(claims)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + 86400000))
      .setClaims(claims)
      .signWith(SignatureAlgorithm.HS512, Sha512DigestUtils.shaHex(sharedSecret)).compact();

  }


  public static void main(String[] args) throws URISyntaxException, IOException {
    //String sharedSecret = "9ac264f002157fb239599b5832f637e1d82b8387ed2bb2901ef088a24d9619f555530cec027f9579157d94316001b5733709eb46b5898e4c7d34bdac6a1c282a";
    String sharedSecret = "7f4e5027d923007c58ac4179a2bf210d50d32703d2504e417a777885dbc9bd88b9da364ceb376fe54aa6dfff2ff5565de9f5c787067cf1a56d701166ac315a87";
    String businessId = "50";
    String authToken = generateKey(sharedSecret, businessId);
    }

}
