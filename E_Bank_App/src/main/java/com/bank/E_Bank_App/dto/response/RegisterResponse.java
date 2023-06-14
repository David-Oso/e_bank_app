package com.bank.E_Bank_App.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterResponse {
    private String message;
    private boolean isSuccess;
}
