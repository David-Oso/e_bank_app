package com.bank.E_Bank_App.service.implementations;

import com.bank.E_Bank_App.data.model.Gender;
import com.bank.E_Bank_App.dto.request.RegisterRequest;
import com.bank.E_Bank_App.dto.response.RegisterResponse;
import com.bank.E_Bank_App.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class CustomerServiceImplTest {
    @Autowired CustomerService customerService;
    private RegisterRequest registerRequest1;
    private RegisterRequest registerRequest2;

    @BeforeEach
    void setUp() {
        registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstName("Dave");
        registerRequest1.setLastName("Temz");
        registerRequest1.setEmail("osodavid001@gmail.com");
        registerRequest1.setPassword("Password");
        registerRequest1.setPhoneNumber("+2345098767845");
        registerRequest1.setGender(Gender.MALE);
        registerRequest1.setDateOfBirth("09/08/2003");

        registerRequest2 = new RegisterRequest();
        registerRequest2.setFirstName("Tolu");
        registerRequest2.setLastName("Tope");
        registerRequest2.setEmail("osodavid272@gmail.com");
        registerRequest2.setPassword("Password");
        registerRequest2.setPhoneNumber("+2345098767867");
        registerRequest2.setGender(Gender.FEMALE);
        registerRequest2.setDateOfBirth("10/08/2003");
    }

    @Test
    void register() {
        RegisterResponse response = customerService.register(registerRequest1);
        assertThat(response.getMessage()).isEqualTo("Check your mail for verification token to activate your account");
        assertThat(response.isSuccess()).isEqualTo(true);

        RegisterResponse response1 = customerService.register(registerRequest2);
        assertThat(response.getMessage()).isEqualTo("Check your mail for verification token to activate your account");
        assertThat(response.isSuccess()).isEqualTo(true);
    }

    @Test
    void resendVerificationMail() {
    }

    @Test
    void authenticate() {
    }

    @Test
    void getCustomerById() {
    }

    @Test
    void getCustomerByEmail() {
    }
}