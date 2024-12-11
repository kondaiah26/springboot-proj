package com.ltimt.bankapplication.serviceimple;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ltimt.bankapplication.entity.Transaction;
import com.ltimt.bankapplication.repository.TransactionRepository;

import lombok.AllArgsConstructor;

//@Component
//@AllArgsConstructor

@Service

public class BankStatement {
	
	
	/*
	 * retrieve list of transactions within given date range for given account number
	 * 
	 * generating pdf file of transactions
	 * 
	 * sending mail with pdf attachment
	 * 
	 * 
	 * */
	
	private TransactionRepository transRepo;
	
	public List<Transaction> generateStatement(String accountNumber, String startDate,String endDate){
		
		LocalDate start=LocalDate.parse(startDate ,DateTimeFormatter.ISO_DATE);
		LocalDate end=LocalDate.parse(endDate,DateTimeFormatter.ISO_DATE);
//		
//		List<Transaction> transactionList= transRepo.findAll().stream()
//				.filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
//				.filter(transaction -> transaction.getCreatedAt().isEqual(start))
//				.filter(transaction -> transaction.getCreatedAt().isEqual(end)).toList();
		
//		return transactionList;
		return transRepo.findByAccountNumberAndCreatedAtBetween(accountNumber, start, end);
    }
	}
	
	


