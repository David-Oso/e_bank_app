package com.bank.E_Bank_App.controller;

import com.bank.E_Bank_App.dto.request.AdminLoginRequest;
import com.bank.E_Bank_App.dto.request.AuthenticationRequest;
import com.bank.E_Bank_App.dto.request.RegisterRequest;
import com.bank.E_Bank_App.dto.response.AuthenticationResponse;
import com.bank.E_Bank_App.dto.response.OtpVerificationResponse;
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

    @PostMapping("user/register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = customerService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }
    @PostMapping("verify")
    public ResponseEntity<?> verify(@Valid @RequestParam String otp){
        OtpVerificationResponse verificationResponse = customerService.verifyEmail(otp);
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

    @PostMapping("resend_verification_mail/{customer_id}")
    public ResponseEntity<?> resendVerificationMail(@Valid @PathVariable Long customer_id){
        String mailResponse = customerService.resendVerificationMail(customer_id);
        return ResponseEntity.ok(mailResponse);
    }

    @GetMapping("send_reset_password_mail/{customer_id}")
    public ResponseEntity<?> sendResetPasswordMail(@Valid @PathVariable Long customer_id){
        String resetPasswordMailResponse = customerService.sendResetPasswordMail(customer_id);
        return ResponseEntity.ok(resetPasswordMailResponse);
    }
}
