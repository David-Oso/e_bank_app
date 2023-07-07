package com.bank.E_Bank_App.dto.request;

import com.bank.E_Bank_App.utils.E_BankUtils;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WithDrawRequest {

    @NotNull(message = "field user id cannot be null")
    private Long customerId;

    @NotNull(message = "field amount cannot be null")
    @NotEmpty(message = "field amount cannot be empty")
    @NotBlank(message = "field amount cannot be blank")
    @Min(value = 200, message = "minimum withdraw amount exceeded")
    @Max(value = 10000, message = "maximum withdraw amount exceeded")
    @Pattern(regexp= E_BankUtils.AMOUNT_REGEX, message = "enter digit")
    private BigDecimal amount;

    @NotNull(message = "field pin cannot be null")
    @NotEmpty(message = "field pin cannot be empty")
    @NotBlank(message = "field pin cannot be blank")
    private String pin;
}
