package com.bank.E_Bank_App.service.customer;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.model.Transaction;
import com.bank.E_Bank_App.dto.request.*;
import com.bank.E_Bank_App.dto.response.AuthenticationResponse;
import com.bank.E_Bank_App.dto.response.RegisterResponse;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {
    RegisterResponse register(RegisterRequest request);
    String verifyEmail(EmailVerificationRequest request);
    void resendVerificationMail(Customer customer);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    Customer getCustomerById(Long customerId);
    Customer getCustomerByEmail(String email);
    Customer getCustomerByAccountNumber(String accountNumber);
    String setUpAccount(SetUpAccountRequest request);
    String makeDeposit(DepositRequest request);
    String makeWithdraw(WithDrawRequest request);
    String makeTransfer(TransferRequest request);
    BigDecimal getBalance(Long customerId, String pin);
    String updateCustomer(UpdateCustomerRequest request);
    String sendRequestPasswordMail(Long customerId);
    String resetPassword(ResetPasswordRequest request);
    String uploadImage(UploadImageRequest request);
    Transaction getTransactionByCustomerIdAndTransactionId(Long customerId, Long transactionId);
    List<Transaction> getAllTransactionsByCustomerId(Long customerId);
    String deleteTransactionByCustomerIdAndTransactionId(Long customerId, Long transactionId);
    String deleteAllTransactionsByCustomerId(Long customerId);
}
