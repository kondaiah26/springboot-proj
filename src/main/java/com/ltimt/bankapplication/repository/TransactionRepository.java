package com.ltimt.bankapplication.repository;

import java.time.LocalDate;
import java.util.List;

import org.eclipse.angus.mail.imap.protocol.ID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ltimt.bankapplication.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

	List<Transaction> findByAccountNumberAndCreatedAtBetween(String accountNumber, LocalDate start, LocalDate end);

}
