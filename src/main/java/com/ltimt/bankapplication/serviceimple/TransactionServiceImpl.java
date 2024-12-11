package com.ltimt.bankapplication.serviceimple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltimt.bankapplication.dto.TransactionDto;
import com.ltimt.bankapplication.entity.Transaction;
import com.ltimt.bankapplication.repository.TransactionRepository;
import com.ltimt.bankapplication.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	TransactionRepository transRepository;
	
	
	public void saveTransaction(TransactionDto transDto) {
		
		 Transaction transaction = Transaction.builder()
				 
				 .accountNumber(transDto.getAccountNumber())
				 .transactionType(transDto.getTransactionType())
				 .amount(transDto.getAmount())
				 .Status(transDto.getStatus()) 
				 .build();
		transRepository.save(transaction);
	}

}
