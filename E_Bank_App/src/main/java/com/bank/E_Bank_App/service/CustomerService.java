package com.bank.E_Bank_App.service;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.dto.request.AuthenticationRequest;
import com.bank.E_Bank_App.dto.request.RegisterRequest;
import com.bank.E_Bank_App.dto.response.AuthenticationResponse;
import com.bank.E_Bank_App.dto.response.RegisterResponse;

public interface CustomerService {
    RegisterResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    Customer getCustomerById(Long customerId);
    Customer getCustomerByEmail(String email);
}
