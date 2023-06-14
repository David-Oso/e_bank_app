package com.bank.E_Bank_App.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    @NotBlank(message = "field email cannot be blank")
    @NotEmpty(message = "field email cannot be empty")
    private String email;

    @NotEmpty(message = "field password cannot be empty")
    @NotBlank(message = "field password cannot be blank")
    private String password;
}
