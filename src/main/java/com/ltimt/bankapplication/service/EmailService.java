package com.ltimt.bankapplication.service;

import com.ltimt.bankapplication.dto.EmailDetails;

public interface EmailService {

	void sendEmailAlert(EmailDetails emailDetails);
}
