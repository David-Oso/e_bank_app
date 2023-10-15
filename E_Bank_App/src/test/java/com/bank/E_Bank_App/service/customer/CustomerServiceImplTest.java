package com.bank.E_Bank_App.service.customer;

import com.bank.E_Bank_App.data.model.Gender;
import com.bank.E_Bank_App.dto.request.LoginRequest;
import com.bank.E_Bank_App.dto.request.RegisterRequest;
import com.bank.E_Bank_App.dto.response.LoginResponse;
import com.bank.E_Bank_App.dto.response.OtpVerificationResponse;
import com.bank.E_Bank_App.dto.response.RegisterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerServiceImplTest {
    @Autowired CustomerService customerService;
    RegisterRequest registerRequest1;
    RegisterRequest registerRequest2;

    @BeforeEach
    void setUp() {
        registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstName("Dave");
        registerRequest1.setLastName("Temz");
        registerRequest1.setEmail("pehey92995@mugadget.com");
        registerRequest1.setPassword("Password");
        registerRequest1.setPhoneNumber("+2345098767849");
        registerRequest1.setGender(Gender.MALE);
        registerRequest1.setDateOfBirth("09/08/2003");

        registerRequest2 = new RegisterRequest();
        registerRequest2.setFirstName("Tolu");
        registerRequest2.setLastName("Tope");
        registerRequest2.setEmail("jepoxeg565@ksyhtc.com");
        registerRequest2.setPassword("Password");
        registerRequest2.setPhoneNumber("+2345098767867");
        registerRequest2.setGender(Gender.FEMALE);
        registerRequest2.setDateOfBirth("10/08/2003");
    }

    @Test
    void register() {
        RegisterResponse response = customerService.register(registerRequest1);
        assertThat(response.getMessage()).isEqualTo("Check your mail for otp to activate your account");
        assertThat(response.isSuccess()).isEqualTo(true);

        RegisterResponse response1 = customerService.register(registerRequest2);
        assertThat(response1.getMessage()).isEqualTo("Check your mail for otp to activate your account");
        assertThat(response1.isSuccess()).isEqualTo(true);
    }

    @Test
    void verifyEmail() {
        OtpVerificationResponse response1 = customerService.verifyEmail("172262");
        assertThat(response1.getEmail()).isEqualTo(registerRequest1.getEmail());
    }

    @Test
    void resendVerificationMail() {
        String response = customerService.resendVerificationMail(registerRequest1.getEmail());
        assertThat(response).isEqualTo("Another otp has sent to your mail proceed by checking your email");
    }

    @Test
    void login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("pehey92995@mugadget.com");
        loginRequest.setPassword("Password");
        LoginResponse loginResponse = customerService.login(loginRequest);
        assertThat(loginResponse.getJwtResponse()).isNotNull();
    }

    @Test
    void getCustomerById() {
    }

    @Test
    void getCustomerByEmail() {
    }

    @Test
    void getCustomerByAccountNumber() {
    }

    @Test
    void setUpAccount() {
    }

    @Test
    void makeDeposit() {
    }

    @Test
    void makeWithdraw() {
    }
}