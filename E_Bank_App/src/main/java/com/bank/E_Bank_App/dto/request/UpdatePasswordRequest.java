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
public class UpdatePasswordRequest {
    @NotNull(message = "field customer id cannot be null")
    private Long userId;

    @NotNull(message = "field password cannot be null")
    @NotBlank(message = "field password cannot be blank")
    @NotEmpty(message = "field password cannot be empty")
    @Pattern(regexp = E_BankUtils.PASSWORD_REGEX_STRING, message = "enter a valid password")
    private String password;

    @NotNull(message = "field new password cannot be null")
    @NotBlank(message = "field new password cannot be blank")
    @NotEmpty(message = "field new password cannot be empty")
    @Pattern(regexp = E_BankUtils.PASSWORD_REGEX_STRING, message = "enter a valid password")
    private String newPassword;
}
