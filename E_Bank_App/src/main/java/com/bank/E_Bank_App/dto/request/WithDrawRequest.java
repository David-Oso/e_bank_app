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

    @NotNull(message = "field customer id cannot be null")
    private Long customerId;

    @NotNull(message = "field amount cannot be null")
    @DecimalMin(value = "200.00", message = "minimum withdraw amount is 200.00")
    @DecimalMax(value = "10000.00", message = "maximum withdraw amount is 10000.00")
    private BigDecimal amount;

    @NotNull(message = "field pin cannot be null")
    @NotEmpty(message = "field pin cannot be empty")
    @NotBlank(message = "field pin cannot be blank")
    private String pin;

    private String description;
}
