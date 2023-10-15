package com.bank.E_Bank_App.service.customer;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.model.Transaction;
import com.bank.E_Bank_App.dto.request.*;
import com.bank.E_Bank_App.dto.response.*;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {
    RegisterResponse register(RegisterRequest registerRequest);
    String resendVerificationMail(String email);
    OtpVerificationResponse verifyEmail(String otp);
    LoginResponse login(LoginRequest loginRequest);
    Customer getCustomerById(Long customerId);
    Customer getCustomerByEmail(String email);
    Customer getCustomerByAccountNumber(String accountNumber);
    SetUpAccountResponse setUpAccount(SetUpAccountRequest setUpAccountRequest);
    String makeDeposit(DepositRequest depositRequest);
    String makeWithdraw(WithDrawRequest withDrawRequest);
    String makeTransfer(TransferRequest transferRequest);
    BigDecimal getBalance(Long customerId, String pin);
    UpdateCustomerResponse updateCustomer(UpdateCustomerRequest updateCustomerRequest);
    String changePassword(ChangePasswordRequest changePasswordRequest);
    String sendResetPasswordMail(String email);
    String resetPassword(ResetPasswordRequest resetPasswordRequest);
    String uploadImage(UploadImageRequest uploadImageRequest);
    Transaction getTransactionByCustomerIdAndTransactionId(Long customerId, Long transactionId);
    List<Transaction> getAllTransactionsByCustomerId(Long customerId);
    String deleteTransactionByCustomerIdAndTransactionId(Long customerId, Long transactionId);
    String deleteAllTransactionsByCustomerId(Long customerId);
}
