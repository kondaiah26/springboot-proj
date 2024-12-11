package com.ltimt.bankapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltimt.bankapplication.dto.BankResponse;
import com.ltimt.bankapplication.dto.CreditDebitRequest;
import com.ltimt.bankapplication.dto.EnquiryRequest;
import com.ltimt.bankapplication.dto.TransactionDto;
import com.ltimt.bankapplication.dto.TransferRequest;
import com.ltimt.bankapplication.dto.UserRequest;
import com.ltimt.bankapplication.service.TransactionService;
import com.ltimt.bankapplication.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	 TransactionService transService; 
	
	@PostMapping
	public BankResponse createAccount(@RequestBody UserRequest userRequest) {
		
		return userService.createAccount(userRequest);
		
	}
	
	@GetMapping("/balance")
	public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiry) {
		return userService.balanceEnquiry(enquiry);
		
	}
	@GetMapping("/nameEnquiry")
	public String nameEnquiry(@RequestBody EnquiryRequest enquiry) {
		return userService.nameEnquiry(enquiry);
		
	}
	
	@PostMapping("/credit")
	public BankResponse creditAmount(@RequestBody CreditDebitRequest req) {
		
		return userService.creditAccount(req);
		
	}
	
	@PostMapping("/debit")
	public BankResponse debitAmount(@RequestBody CreditDebitRequest req) {
		
		return userService.debitAccount(req);
		
	}
	
	@PostMapping("/transfer")
	public BankResponse transerAmount(@RequestBody TransferRequest req) {
		
		return userService.transferAmount(req);
		
	}
	@PostMapping("/transaction")
	public void transferAmount(@RequestBody TransactionDto transDto) {
		
		transService.saveTransaction(transDto);
	}
	
}
