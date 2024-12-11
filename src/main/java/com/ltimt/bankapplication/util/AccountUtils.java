package com.ltimt.bankapplication.util;

import java.time.Year;

public class AccountUtils {
	
	/*
	 *  2024+randomSixDigits 
	 *  
	 *  here we are creating random accountNumber with current year prefix  followed by six other digits
	 *  
	 *  
	 *  we are creating variables with static and final , bcz we can use these varialbes wherever we want becauese of static
	 *  
	 *  and no one can change the variable value due final
	 *  
	 *  
	 *  
	 */	
	
	public static final String ACCOUNT_EXISTS_CODE= "001";
	
	public static final String ACCOUNT_EXISTS_MESSAGE="THIS USER ALREADY HAS AN ACCOUNT CREATED!!!";
	
	public static final String ACCOUNT_CREATION_SUCCESS="002";
	
	public static final String ACCOUNT_CREATION_MESSAGE="Account has been successfully created!!!";
	
	public static final String ACCOUNT_NOT_EXIST ="003";
	
	public static final String ACCOUNT_NOT_EXIST_MESSAGE="User with provided Account Number does not exist!!";
	
	public static final String ACCOUNT_FOUND_CODE="004";
	
	public static final String ACCOUNT_FOUND_SUCCESS="User Account Found";
	
	public static final String INSUFFICIENT_BALANCE_CODE="006";
	
	public static final String INSUFFICIENT_BALANCE_MESSAGE="Insufficient Balance";
	
	public static final String ACCOUNT_DEBITED_CODE="007";
	
	public static final String ACCOUNT_DEBITED_MESSAGE=" Amount has been debited from Account Successfully!!";
	
	public static final String ACCOUNT_CREDITED_CODE="008";
	
	public static final String ACCOUNT_CREDITED_MESSAGE=" Amount has been credited to Account Successfully!!";
	
	public static final String ACCOUNT_TRANSFERED_CODE="009";
	
	public static final String ACCOUNT_TRANSFERED_MESSAGE="Amount has been transfered from your Account Successfully!!";
	
	public static String generateAccountNumber() {
		Year currentyear = Year.now();
		
		int min=100000;
		
		int max=999999;
		
		int randomNumber=(int) Math.floor(Math.random()*(max-min+1)+min);
		
		//converting the current year and random number to string , then concatenate them
		
		String year=String.valueOf(currentyear);
		
		String randNum= String.valueOf(randomNumber);
		
		StringBuilder accountNumber= new StringBuilder();
		
		return accountNumber.append(year).append(randNum).toString();
		
	}
	

}
