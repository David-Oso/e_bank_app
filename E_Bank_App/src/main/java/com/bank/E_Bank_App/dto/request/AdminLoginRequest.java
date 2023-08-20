package com.bank.E_Bank_App.dto.request;

import com.bank.E_Bank_App.utils.E_BankUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminLoginRequest {
    @NotNull(message = "field id cannot be null")
    @NotBlank(message = "field id cannot be blank")
    @NotEmpty(message = "field id cannot be empty")
    private String identity;

    @NotNull(message = "field email cannot be null")
    @NotBlank(message = "field email cannot be blank")
    @NotEmpty(message = "field email cannot be empty")
    @Pattern(regexp = E_BankUtils.EMAIL_REGEX_STRING, message = "Enter a valid email")
    private String email;

    @NotNull(message = "field password cannot be null")
    @NotBlank(message = "field password cannot be blank")
    @NotEmpty(message = "field password cannot be empty")
//    @Pattern(regexp = E_BankUtils.PASSWORD_REGEX_STRING, message = "Enter a valid password")
    private String password;
}
