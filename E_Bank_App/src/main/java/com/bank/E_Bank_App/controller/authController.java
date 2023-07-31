package com.bank.E_Bank_App.controller;

import com.bank.E_Bank_App.dto.request.AdminLoginRequest;
import com.bank.E_Bank_App.dto.request.AuthenticationRequest;
import com.bank.E_Bank_App.dto.request.RegisterRequest;
import com.bank.E_Bank_App.dto.response.AuthenticationResponse;
import com.bank.E_Bank_App.dto.response.RegisterResponse;
import com.bank.E_Bank_App.service.admin.AdminService;
import com.bank.E_Bank_App.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth/")
@AllArgsConstructor

public class authController {
    private final CustomerService customerService;
    private final AdminService adminService;

    private final String mailResponse = "Check your mail to continue your registration";
    @PostMapping("user/register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = customerService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }
    @PostMapping("verify")
    public ResponseEntity<?> verify(@Valid @RequestBody EmailVerificationRequest emailVerificationRequest){
        String verificationResponse = customerService.verifyEmail(emailVerificationRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(verificationResponse);
    }
    @PostMapping("customer/login")
    public ResponseEntity<?>  customerLogin(@Valid @RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse customerLoginResponse = customerService.authenticate(authenticationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(customerLoginResponse);
    }

    @PostMapping("admin/login")
    public ResponseEntity<?> adminLogin(@Valid @RequestBody AdminLoginRequest adminLoginRequest){
        AuthenticationResponse adminLoginResponse = adminService.authenticate(adminLoginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(adminLoginResponse);
    }

    @PostMapping("send_verification_mail/{customer_id}")
    public ResponseEntity<?> sendVerificationMail(@Valid @PathVariable Long customer_id){
        customerService.sendVerificationMail(customer_id);
        return ResponseEntity.ok(mailResponse);
    }
    @PostMapping("resend_verification_mail/{customer_id}")
    public ResponseEntity<?> resendVerificationMail(@Valid @PathVariable Long customer_id){
        customerService.resendVerificationMail(customer_id);
        return ResponseEntity.ok(mailResponse);
    }

    @GetMapping("send_reset_password_mail/{customer_id}")
    public ResponseEntity<?> sendResetPasswordMail(@Valid @PathVariable Long customer_id){
        String resetPasswordMailResponse = customerService.sendResetPasswordMail(customer_id);
        return ResponseEntity.ok(resetPasswordMailResponse);
    }
}
