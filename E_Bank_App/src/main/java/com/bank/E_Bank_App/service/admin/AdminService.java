package com.bank.E_Bank_App.service.admin;

import com.bank.E_Bank_App.dto.request.AdminLoginRequest;
import com.bank.E_Bank_App.dto.response.LoginResponse;

public interface AdminService {
    LoginResponse authenticate(AdminLoginRequest request);
}
