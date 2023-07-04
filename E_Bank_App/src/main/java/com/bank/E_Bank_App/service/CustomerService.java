package com.bank.E_Bank_App.service;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.dto.request.*;
import com.bank.E_Bank_App.dto.response.AuthenticationResponse;
import com.bank.E_Bank_App.dto.response.RegisterResponse;

public interface CustomerService {
    RegisterResponse register(RegisterRequest request);
    String verifyEmail(EmailVerificationRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    Customer getCustomerById(Long customerId);
    Customer getCustomerByEmail(String email);
    String setUpAccount(SetUpAccountRequest request);
    String makeDeposit(DepositRequest request);
    String makeWithdraw(WithDrawRequest request);
    String makeTransfer(TransferRequest request);
    String resetPassword(ResetPasswordRequest request);
}
