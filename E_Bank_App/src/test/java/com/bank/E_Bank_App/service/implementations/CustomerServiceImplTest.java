package com.bank.E_Bank_App.service.implementations;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.model.Gender;
import com.bank.E_Bank_App.dto.request.AuthenticationRequest;
import com.bank.E_Bank_App.dto.request.EmailVerificationRequest;
import com.bank.E_Bank_App.dto.request.RegisterRequest;
import com.bank.E_Bank_App.dto.request.SetUpAccountRequest;
import com.bank.E_Bank_App.dto.response.AuthenticationResponse;
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
    private EmailVerificationRequest emailVerificationRequest1;
    private EmailVerificationRequest emailVerificationRequest2;
    private AuthenticationRequest authenticationRequest;
    private SetUpAccountRequest setUpAccountRequest1;
    private SetUpAccountRequest setUpAccountRequest2;


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

        emailVerificationRequest1 = new EmailVerificationRequest();
        emailVerificationRequest1.setEmail("osodavid001@gmail.com");
        emailVerificationRequest1.setToken("z-twfxrB");
        emailVerificationRequest2 = new EmailVerificationRequest();
        emailVerificationRequest2.setEmail("osodavid272@gmail.com");
        emailVerificationRequest2.setToken("JWJdJ3dz");

        authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("osodavid001@gmail.com");
        authenticationRequest.setPassword("Passwordit commit");

        setUpAccountRequest1 = new SetUpAccountRequest();
        setUpAccountRequest1.setUserId(1L);
        setUpAccountRequest1.setPin("1234");

        setUpAccountRequest2 = new SetUpAccountRequest();
        setUpAccountRequest2.setUserId(2L);
        setUpAccountRequest2.setPin("1245");
    }

    @Test
    void registerUserTest() {
        RegisterResponse response = customerService.register(registerRequest1);
        assertThat(response.getMessage()).isEqualTo("Check your mail for verification token to activate your account");
        assertThat(response.isSuccess()).isEqualTo(true);

        RegisterResponse response1 = customerService.register(registerRequest2);
        assertThat(response1.getMessage()).isEqualTo("Check your mail for verification token to activate your account");
        assertThat(response1.isSuccess()).isEqualTo(true);
    }

    @Test
    void resendVerificationMailTest() {
//        customerService.resendVerificationMail();
    }

    @Test
    void verifyEmailTest(){
        String response1 = customerService.verifyEmail(emailVerificationRequest1);
        assertThat(response1).isEqualTo("Verification successful");

        String response2 = customerService.verifyEmail(emailVerificationRequest2);
        assertThat(response2).isEqualTo("Verification successful");
    }

    @Test
    void authenticateUserTest() {
        AuthenticationResponse response = customerService.authenticate(authenticationRequest);
        assertThat(response.getMessage()).isEqualTo("Authentication successful");
        assertThat(response.isAuthenticated()).isEqualTo(true);
    }

    @Test
    void getCustomerById() {
        Customer foundCustomer = customerService.getCustomerById(1L);
        assertThat(foundCustomer.getEmail()).isEqualTo(registerRequest1.getEmail());
    }

    @Test
    void getCustomerByEmail() {
        Customer foundCustomer = customerService.getCustomerByEmail("osodavid001@gmail.com");
        assertThat(foundCustomer.getFirstName()).isEqualTo("Dave");
        assertThat(foundCustomer.getLastName()).isEqualTo("Temz");
    }

}