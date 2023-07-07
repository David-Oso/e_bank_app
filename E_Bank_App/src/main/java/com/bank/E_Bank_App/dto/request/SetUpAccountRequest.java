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
public class SetUpAccountRequest {
    @NotNull(message = "field customer id cannot be null")
    private Long customerId;

    @NotNull(message = "field pin cannot be null")
    @NotEmpty(message = "field pin cannot be empty")
    @NotBlank(message = "field pin cannot be blank")
    @Pattern(regexp = E_BankUtils.PIN_REGEX, message = "Enter a 4 digit pin")
    private String pin;
}
