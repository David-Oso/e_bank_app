package com.bank.E_Bank_App.controller;

import com.bank.E_Bank_App.service.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
}

// RegisterResponse register(RegisterRequest request);
//    String verifyEmail(EmailVerificationRequest request);
//    void resendVerificationMail(Customer customer);
//    AuthenticationResponse authenticate(AuthenticationRequest request);
//    Customer getCustomerById(Long customerId);
//    Customer getCustomerByEmail(String email);
//    Customer getCustomerByAccountNumber(String accountNumber);
//    String setUpAccount(SetUpAccountRequest request);
//    String makeDeposit(DepositRequest request);
//    String makeWithdraw(WithDrawRequest request);
//    String makeTransfer(TransferRequest request);
//    BigDecimal getBalance(Long customerId, String pin);
//    String updateCustomer(UpdateCustomerRequest request);
//    String sendRequestPasswordMail(Long customerId);
//    String resetPassword(ResetPasswordRequest request);
//    String uploadImage(UploadImageRequest request);
//    Transaction getTransactionByCustomerIdAndTransactionId(Long customerId, Long transactionId);
//    List<Transaction> getAllTransactionsByCustomerId(Long customerId);
//    String deleteTransactionByCustomerIdAndTransactionId(Long customerId, Long transactionId);
//    String deleteAllTransactionsByCustomerId(Long customerId);
