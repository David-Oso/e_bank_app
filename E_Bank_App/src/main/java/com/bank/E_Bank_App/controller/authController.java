package com.bank.E_Bank_App.controller;

import com.bank.E_Bank_App.dto.request.AdminLoginRequest;
import com.bank.E_Bank_App.dto.request.AuthenticationRequest;
import com.bank.E_Bank_App.dto.request.EmailVerificationRequest;
import com.bank.E_Bank_App.dto.request.RegisterRequest;
import com.bank.E_Bank_App.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth/")
@AllArgsConstructor
public class authController {
    private final CustomerService customerService;
//    private final AdminService adminService;


    @PostMapping("user/register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody RegisterRequest registerRequest){
        return null;
    }
    @PostMapping("verify")
    public ResponseEntity<?> verify(@Valid @RequestBody EmailVerificationRequest emailVerificationRequest){
        return null;
    }
    @PostMapping("customer/login")
    public ResponseEntity<?>  customerLogin(@Valid @RequestBody AuthenticationRequest authenticationRequest){
        return null;
    }

    @PostMapping("admin/login")
    public ResponseEntity<?> adminLogin(@Valid @RequestBody AdminLoginRequest adminLoginRequest){
        return null;
    }


    @GetMapping("send_verification_mail/{customer_id}")
    public ResponseEntity<?> resendVerificationMail(@Valid @PathVariable Long customer_id){
        return null;
    }

    @GetMapping("send_reset_password_mail/{customer_id}")
    public ResponseEntity<?> sendResetPasswordMail(@Valid @PathVariable Long customer_id){
        return null;
    }

}
