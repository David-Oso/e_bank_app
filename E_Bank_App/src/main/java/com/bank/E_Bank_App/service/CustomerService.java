package com.bank.E_Bank_App.service;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.dto.request.*;
import com.bank.E_Bank_App.dto.response.AuthenticationResponse;
import com.bank.E_Bank_App.dto.response.RegisterResponse;

import java.math.BigDecimal;

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
    BigDecimal getBalance(Long userId, String pin);
    String updateCustomer(UpdateCustomerRequest request);
    String sendRequestPasswordMail(Long userId);
    String resetPassword(ResetPasswordRequest request);
}
