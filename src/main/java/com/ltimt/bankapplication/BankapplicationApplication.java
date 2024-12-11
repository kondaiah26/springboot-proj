package com.ltimt.bankapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				
				title="THE JAVA BANK APPLICATION",
				description="BACKEND Rest APIs for BANK APPLICATION",
				version="v1.0"   
				
				
				
				
				)
		
		
		
		)
public class BankapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankapplicationApplication.class, args);
	}

}
