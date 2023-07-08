package com.bank.E_Bank_App.service;

import com.bank.E_Bank_App.dto.request.AdminLoginRequest;
import com.bank.E_Bank_App.dto.response.AuthenticationResponse;

public interface AdminService {
    AuthenticationResponse adminLogin(AdminLoginRequest request);
}
