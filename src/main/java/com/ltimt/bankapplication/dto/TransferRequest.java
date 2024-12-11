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
public class TransferRequest {

	private String sourceAccount;
	
	private String destAccount;
	
	private BigDecimal amount;
}
