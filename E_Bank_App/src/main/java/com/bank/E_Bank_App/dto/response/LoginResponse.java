package com.bank.E_Bank_App.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse {
    private String message;
    private boolean isAuthenticated;
    private JwtResponse jwtResponse;
}
