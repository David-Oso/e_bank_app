package com.bank.E_Bank_App.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResetPasswordRequest {
    private String email;
    private String token;
    private String newPassword;
    private String confirmPassword;
}
