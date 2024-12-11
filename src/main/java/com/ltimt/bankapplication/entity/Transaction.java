package com.ltimt.bankapplication.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@Entity(name = "transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String transactionId;
	private String accountNumber;
	private String transactionType;
	private BigDecimal amount;
	private String Status;
	
	@CreationTimestamp
	private LocalDate createdAt;
	

}
