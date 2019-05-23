/* 
 * (C) Copyright Kalidus DBA Skava, 2018 all rights reserved
 * 
 * This code is provided on an as-is basis for the illustration of invoking the Skava API. It is not warranted to be fit for any other purpose. 
 * This code is not optimized for production purposes. 
 *  
 * 
 */
package com.skava.testSfoLogin;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author David Levine
 * 
 * This class does a simple hash of the password to match hashed passwords created by our front end. 
 *
 */

public class HashPW {

public static String hash512(String inputToHash) throws NoSuchAlgorithmException
		{
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		byte[] digest = md.digest(inputToHash.getBytes());
		String hashedString = String.format("%0128x", new BigInteger(1, digest));
		System.out.println("Hashed String"+hashedString);
		return hashedString;
		}

public static String createFrontendHash(String inputToHash) throws NoSuchAlgorithmException
   {
   String fullHash = hash512(inputToHash);
   String trimmedHash = fullHash.substring(0,27);
   return trimmedHash+"@1aZ";
   }

   public static void main(String args[])
		    {
	        try {
				String jw = createFrontendHash("Skava@15");
				System.out.println(jw);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Could not locate SHA-512 hash in system libs");
			}
			   
		    }
		
	}

