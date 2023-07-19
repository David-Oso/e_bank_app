package com.bank.E_Bank_App.service.myToken;

import com.bank.E_Bank_App.data.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MyTokenServiceImplTest {
    @Autowired MyTokenService myTokenService;

    @Test
    void generateAndSaveMyToken() {
        System.out.println(myTokenService.generateAndSaveMyToken(new Customer()));
    }

    @Test
    void validateReceivedToken() {
    }

    @Test
    void deleteToken() {
    }
}