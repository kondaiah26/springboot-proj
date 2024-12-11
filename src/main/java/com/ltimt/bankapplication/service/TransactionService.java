package com.ltimt.bankapplication.service;

import com.ltimt.bankapplication.dto.TransactionDto;
import com.ltimt.bankapplication.entity.Transaction;

public interface TransactionService {
	
	void saveTransaction(TransactionDto transDto);
}
