package com.bank.E_Bank_App.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticationResponse {
    private String message;
    private boolean isAuthenticated;
    private JwtResponse jwtResponse;
}
