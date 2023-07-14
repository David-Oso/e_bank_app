package com.bank.E_Bank_App.service.myToken;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.model.MyToken;

import java.util.Optional;

public interface MyTokenService {
    String generateAndSaveMyToken(Customer customer);
    Optional<MyToken> validateReceivedToken(String token);
    void deleteToken(MyToken myToken);
//    Customer getCustomerByToken(String token);
}
