package com.bank.E_Bank_App.controller;

import com.bank.E_Bank_App.dto.request.LoginRequest;
import com.bank.E_Bank_App.dto.request.RegisterRequest;
import com.bank.E_Bank_App.dto.response.LoginResponse;
import com.bank.E_Bank_App.dto.response.OtpVerificationResponse;
import com.bank.E_Bank_App.dto.response.RegisterResponse;
import com.bank.E_Bank_App.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
@AllArgsConstructor

public class AuthController {
    private final CustomerService customerService;

    @PostMapping(value = "v1/customer/register", produces = "application/json")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = customerService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }
    @PostMapping(value = "v1/verify", produces = "application/json")
    public ResponseEntity<?> verify(@Valid @RequestParam String otp){
        OtpVerificationResponse verificationResponse = customerService.verifyEmail(otp);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(verificationResponse);
    }
    @PostMapping(value = "v1/customer/login", produces = "application/json")
    public ResponseEntity<?>  customerLogin(@Valid @RequestBody LoginRequest loginRequest){
        LoginResponse customerLoginResponse = customerService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(customerLoginResponse);
    }

    @PostMapping(value = "v1/resend_verification_mail", produces = "application/json")
    public ResponseEntity<?> resendVerificationMail(@Valid @RequestParam String email){
        String mailResponse = customerService.resendVerificationMail(email);
        return ResponseEntity.ok(mailResponse);
    }
}
