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
public class TransferRequest {
    @NotNull(message = "field customer id must not be null")
    private Long customerId;

    @NotNull(message = "field customer account number cannot be null")
    @NotBlank(message = "field customer account number cannot be blank")
    @NotEmpty(message = "field customer account number cannot be empty")
    @Pattern(regexp = E_BankUtils.ACCOUNT_NUMBER_REGEX)
    private String recipientAccountNumber;

    @NotNull(message = "field amount cannot be null")
    @DecimalMin(value = "200.00", message = "minimum withdraw amount is 200.00")
    @DecimalMax(value = "50000000.00", message = "maximum withdraw amount is 50000000.00")
//    @Pattern(regexp = E_BankUtils.AMOUNT_REGEX, message = "amount must be digit")
    private BigDecimal amount;

    @NotNull(message = "field pin cannot be null")
    @NotBlank(message = "field pin cannot be blank")
    @NotEmpty(message = "field pin cannot be empty")
    @Pattern(regexp = E_BankUtils.PIN_REGEX)
    private String pin;
}
