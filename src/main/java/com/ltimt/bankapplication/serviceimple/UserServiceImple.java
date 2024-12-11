package com.ltimt.bankapplication.serviceimple;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltimt.bankapplication.dto.AccountInfo;
import com.ltimt.bankapplication.dto.BankResponse;
import com.ltimt.bankapplication.dto.CreditDebitRequest;
import com.ltimt.bankapplication.dto.EmailDetails;
import com.ltimt.bankapplication.dto.EnquiryRequest;
import com.ltimt.bankapplication.dto.TransactionDto;
import com.ltimt.bankapplication.dto.TransferRequest;
import com.ltimt.bankapplication.dto.UserRequest;
import com.ltimt.bankapplication.entity.User;
import com.ltimt.bankapplication.repository.TransactionRepository;
import com.ltimt.bankapplication.repository.UserRepository;
import com.ltimt.bankapplication.service.EmailService;
import com.ltimt.bankapplication.service.TransactionService;
import com.ltimt.bankapplication.service.UserService;
import com.ltimt.bankapplication.util.AccountUtils;

@Service
public class UserServiceImple implements UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	EmailService emailService;
	
	@Autowired
	TransactionService tranService;
	
	@Autowired
	TransactionRepository transRepo;

	@Override
	public BankResponse createAccount(UserRequest userRequest) {

		/*
		 * creating account - saving user into db checking if the user exists or not
		 */

		if (userRepo.existsByEmail(userRequest.getEmail())) {

			BankResponse response = BankResponse.builder().responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE).accountInfo(null).build();

			return response;
		}

		User user = new User().builder().firstName(userRequest.getFirstName()).lastName(userRequest.getLastName())
				.otherName(userRequest.getOtherName()).gender(userRequest.getGender()).address(userRequest.getAddress())
				.stateOfOrigin(userRequest.getStateOfOrigin()).accountNumber(AccountUtils.generateAccountNumber())
				.accountBalance(BigDecimal.ZERO).email(userRequest.getEmail()).phoneNumber(userRequest.getPhoneNumber())
				.alternativePhoneNumber(userRequest.getAlternativePhoneNumber()).status("ACTIVE").build();

		User savedUser = userRepo.save(user);

		// send email after creating account

		EmailDetails emailDetails = EmailDetails.builder().recipient(savedUser.getEmail()).subject("ACCOUNT CREATION")
				.messageBody(
						"Congratulations !! Your Account has been created successfully.. \n Your Account Details are"
								+ "Account Name :" + savedUser.getFirstName() + " " + savedUser.getLastName()
								+ "\n AccountNumber :" + savedUser.getAccountNumber())
				.build();
		emailService.sendEmailAlert(emailDetails);

		return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
				.responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
				.accountInfo(
						AccountInfo.builder().accountName(savedUser.getFirstName() + "  " + savedUser.getLastName())
						.accountBalance(savedUser.getAccountBalance())

						.accountNumber(AccountUtils.generateAccountNumber()).build())
				.build();
	}

	@Override
	public BankResponse balanceEnquiry(EnquiryRequest request) {

		// first check the account number exists or not

		boolean isAccountExist = userRepo.existsByAccountNumber(request.getAccountNumber());

		// if user not exist with provided account number, this sec wll execute here

		if (!isAccountExist) {
			return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_NOT_EXIST)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE).accountInfo(null).build();
		}

		User foundUser = userRepo.findByAccountNumber(request.getAccountNumber());

		return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
				.responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
				.accountInfo(AccountInfo.builder().accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
						.accountBalance(foundUser.getAccountBalance()).accountNumber(foundUser.getAccountNumber())
						.build())
				.build();
	}

	@Override
	public String nameEnquiry(EnquiryRequest request) {

		boolean isAccountExist = userRepo.existsByAccountNumber(request.getAccountNumber());

		if (!isAccountExist) {
			return AccountUtils.ACCOUNT_NOT_EXIST;
		}
		User foundUser = userRepo.findByAccountNumber(request.getAccountNumber());

		return foundUser.getFirstName() + " " + foundUser.getLastName();
	}

	@Override
	public BankResponse creditAccount(CreditDebitRequest request) {

		// checking if the account exists or not

		boolean isAccountExist = userRepo.existsByAccountNumber(request.getAccountNumber());

		if (!isAccountExist) {
			return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_NOT_EXIST)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE).accountInfo(null).build();
		}

		User userToCredit = userRepo.findByAccountNumber(request.getAccountNumber());

		userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));

		userRepo.save(userToCredit);

		EmailDetails creditAlertNotification = EmailDetails.builder().subject("Amount Credited")
				.recipient(userToCredit.getEmail())
				.messageBody("Your Account was credited with Rs: " + request.getAmount()).build();

		emailService.sendEmailAlert(creditAlertNotification);
		
		TransactionDto transDto= TransactionDto.builder()
						.accountNumber(request.getAccountNumber())
						.transactionType("Credit")
						.amount(request.getAmount())
						.Status("SUCCESS")
				
				
				.build();
		tranService.saveTransaction(transDto);
		return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_CREDITED_CODE)
				.responseMessage(AccountUtils.ACCOUNT_CREDITED_MESSAGE)
				.accountInfo(AccountInfo.builder()
						.accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName())
						.accountBalance(userToCredit.getAccountBalance()).accountNumber(userToCredit.getAccountNumber())
						.build())
				.build();
	}

	@Override
	public BankResponse debitAccount(CreditDebitRequest request) {

		boolean isAccountExist = userRepo.existsByAccountNumber(request.getAccountNumber());

		if (!isAccountExist) {
			return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_NOT_EXIST)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE).accountInfo(null).build();
		}
		User userToDebit = userRepo.findByAccountNumber(request.getAccountNumber());

		BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();

		BigInteger debitAmount = request.getAmount().toBigInteger();

		if (availableBalance.intValue() < debitAmount.intValue()) {
			TransactionDto transDto= TransactionDto.builder()
					.accountNumber(request.getAccountNumber())
					.transactionType("Debit")
					.amount(request.getAmount())
					.Status("FAILED")
			
			
			.build();
	tranService.saveTransaction(transDto);
			return BankResponse.builder().responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
					.responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE).accountInfo(null).build();
		} else {

			userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
			userRepo.save(userToDebit);

			EmailDetails debitAlertNotification = EmailDetails.builder().subject("Amount Credited")
					.recipient(userToDebit.getEmail())
					.messageBody("Your Account was debited with Rs: " + request.getAmount()).build();

			emailService.sendEmailAlert(debitAlertNotification);
			
			TransactionDto transDto= TransactionDto.builder()
					.accountNumber(request.getAccountNumber())
					.transactionType("Debit")
					.amount(request.getAmount())
					.Status("SUCCESS")
			
			
			.build();
	tranService.saveTransaction(transDto);

			return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_DEBITED_CODE)
					.responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
					.accountInfo(AccountInfo.builder()
							.accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName())
							.accountBalance(userToDebit.getAccountBalance())
							.accountNumber(userToDebit.getAccountNumber()).build())
					.build();
		}

	}

	@Override
	public BankResponse transferAmount(TransferRequest request) {

		boolean isAccountExist = userRepo.existsByAccountNumber(request.getSourceAccount());

		boolean isDestAccountExist = userRepo.existsByAccountNumber(request.getDestAccount());

		if (!isAccountExist) {
			return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_NOT_EXIST)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE).accountInfo(null).build();

		}

		User userToTransfer = userRepo.findByAccountNumber(request.getSourceAccount());

		User userToReceive = userRepo.findByAccountNumber(request.getDestAccount());

		BigInteger availableBalance = userToTransfer.getAccountBalance().toBigInteger();

		if (availableBalance.intValue() < request.getAmount().intValue()) {
			return BankResponse.builder().responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
					.responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE).accountInfo(null).build();
		}

		else {

			userToTransfer.setAccountBalance(userToTransfer.getAccountBalance().subtract(request.getAmount()));
			userRepo.save(userToTransfer);
			userToReceive.setAccountBalance(userToReceive.getAccountBalance().add(request.getAmount()));
			userRepo.save(userToReceive);

			EmailDetails debitAlertNotification = EmailDetails.builder().subject("Amount Transfered")
					.recipient(userToTransfer.getEmail())
					.messageBody("Your Account was debited with Rs: " + request.getAmount()).build();

			emailService.sendEmailAlert(debitAlertNotification);

			return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_TRANSFERED_CODE)
					.responseMessage(AccountUtils.ACCOUNT_TRANSFERED_MESSAGE)
					.accountInfo(AccountInfo.builder()
							.accountName(userToTransfer.getFirstName() + " " + userToTransfer.getLastName())
							.accountBalance(userToTransfer.getAccountBalance())
							.accountNumber(userToTransfer.getAccountNumber()).build())
					.build();
		}

	}

}
