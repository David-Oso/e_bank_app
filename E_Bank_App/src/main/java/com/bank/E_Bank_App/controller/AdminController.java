package com.bank.E_Bank_App.controller;

import com.bank.E_Bank_App.dto.request.AdminLoginRequest;
import com.bank.E_Bank_App.dto.response.LoginResponse;
import com.bank.E_Bank_App.service.admin.AdminService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/api/v1/admin/")
@RestController
public class AdminController {
    private final AdminService adminService;

    @PostMapping("login")
    public ResponseEntity<?> adminLogin(@Valid @RequestBody AdminLoginRequest adminLoginRequest){
        LoginResponse adminLoginResponse = adminService.authenticate(adminLoginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(adminLoginResponse);
    }
}
