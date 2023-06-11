package com.bank.E_Bank_App.customerService.implementations;

import com.bank.E_Bank_App.customerService.CustomerService;
import com.bank.E_Bank_App.dto.request.EmailVerificationRequest;
import com.bank.E_Bank_App.dto.response.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class CustomerServiceImplTest {
    @Autowired CustomerService customerService;
    private EmailVerificationRequest emailVerificationRequest1;
    @BeforeEach
    void setUp() {
        emailVerificationRequest1 = new EmailVerificationRequest();
        emailVerificationRequest1.setEmail("osodavid001@gmail.com");
    }

    @Test
    void registerUser() {
        ApiResponse response = customerService.registerUser(emailVerificationRequest1);
        assertThat(response.getMessage()).isEqualTo("Check your email for the verification link to activate your E_Bank Account");
        assertThat(response.isSuccess()).isEqualTo(true);
    }

    @Test
    void resendVerificationLinkMail() {
    }

    @Test
    void getCustomerById() {
    }

    @Test
    void getCustomerByEmail() {
    }
}