package com.bank.E_Bank_App.otp;

import com.bank.E_Bank_App.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
   OtpEntity findMyTokenByCustomer(Customer customer);
    OtpEntity findByToken(String token);
}
