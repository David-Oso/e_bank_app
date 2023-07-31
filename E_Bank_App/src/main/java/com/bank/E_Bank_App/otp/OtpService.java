package com.bank.E_Bank_App.otp;

import com.bank.E_Bank_App.data.model.Customer;

public interface OtpService {
    String generateAndSaveOtp(Customer customer);
    String generateAndSaveToken(Customer customer);
    OtpEntity validateReceivedOtp(String token);
    void deleteToken(OtpEntity otpEntity);
}
