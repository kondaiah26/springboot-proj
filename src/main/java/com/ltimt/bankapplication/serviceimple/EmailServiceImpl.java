package com.ltimt.bankapplication.serviceimple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ltimt.bankapplication.dto.EmailDetails;
import com.ltimt.bankapplication.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	
//	private static final String SimpleMailMessage = null;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String senderMail;

	
	public void sendEmailAlert(EmailDetails emailDetails) {
		
		try {
			SimpleMailMessage mailMessage =new SimpleMailMessage();
			mailMessage.setFrom(senderMail);
			mailMessage.setTo(emailDetails.getRecipient());
		 	mailMessage.setText(emailDetails.getMessageBody());
			mailMessage.setSubject(emailDetails.getSubject());
			
			javaMailSender.send(mailMessage);
		}
		catch(MailException e) {
			throw new RuntimeException(e);
		}
		
		
	}

}
