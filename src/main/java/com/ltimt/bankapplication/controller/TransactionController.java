package com.ltimt.bankapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ltimt.bankapplication.entity.Transaction;
import com.ltimt.bankapplication.serviceimple.BankStatement;

import lombok.AllArgsConstructor;

@RestController
//@AllArgsConstructor

@RequestMapping("/bankStatement")
public class TransactionController {
	

	private BankStatement bankStatement;
	
	@GetMapping("/statement")
	public List<Transaction> generateStatement(@RequestParam String accountNumber,
												@RequestParam String startDate,
												@RequestParam String endDate
												){
		return bankStatement.generateStatement(accountNumber, startDate, endDate);
		
	}
	

}
