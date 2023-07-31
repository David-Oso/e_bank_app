package com.bank.E_Bank_App.service.myToken;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.otp.OtpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OtpEntityServiceImplTest {
    @Autowired
    OtpService otpService;

    @Test
    void generateAndSaveMyToken() {
        System.out.println(otpService.generateAndSaveOtp(new Customer()));
    }

    @Test
    void validateReceivedToken() {
    }

    @Test
    void deleteToken() {
    }
}