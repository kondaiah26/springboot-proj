package com.ltimt.bankapplication.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {

	private String accountNumber;
	private String transactionType;
	private BigDecimal amount;
	private String Status;
}
