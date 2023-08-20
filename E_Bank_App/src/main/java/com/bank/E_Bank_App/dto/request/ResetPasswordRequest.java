package com.bank.E_Bank_App.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.bank.E_Bank_App.utils.E_BankUtils.PASSWORD_REGEX_STRING;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResetPasswordRequest {
    @NotNull(message = "field otp cannot be null")
    @NotEmpty(message = "field otp cannot be empty")
    @NotBlank(message = "field otp cannot be blank")
    private String otp;

    @NotNull(message = "field new password cannot be null")
    @NotEmpty(message = "field new password cannot be empty")
    @NotBlank(message = "field new password cannot be blank")
    @Pattern(regexp = PASSWORD_REGEX_STRING, message = "Password must " +
            "contain at least one capital letter, one small letter, a number and special character(@$!%*?&)")
    private String newPassword;

    private String confirmPassword;
}
