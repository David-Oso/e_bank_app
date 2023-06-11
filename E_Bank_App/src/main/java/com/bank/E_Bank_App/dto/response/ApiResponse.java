package com.bank.E_Bank_App.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ApiResponse {
    private String message;
    private boolean isSuccess;
}
