package com.ltimt.bankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmailDetails {
	
	private String recipient;
	
	private String messageBody;
	
	private String subject;
	
	private String attachment;

}
