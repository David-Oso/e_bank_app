package com.bank.E_Bank_App.customerService;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.dto.request.EmailVerificationRequest;
import com.bank.E_Bank_App.dto.response.ApiResponse;

public interface CustomerService {
    ApiResponse registerUser(EmailVerificationRequest request);

    void resendVerificationLinkMail(Customer customer);

    Customer getCustomerById(Long customerId);
    Customer getCustomerByEmail(String email);
}
