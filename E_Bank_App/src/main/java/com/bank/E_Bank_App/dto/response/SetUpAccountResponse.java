package com.bank.E_Bank_App.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SetUpAccountResponse {
    private String message;
    private String accountName;
    private String accountNumber;
}
