package com.bank.E_Bank_App.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepositRequest {
    @NotNull(message = "field customer id cannot be null")
    private Long customerId;

    @NotNull(message = "field amount cannot be null")
    @NotBlank(message = "field amount cannot be blank")
    @NotEmpty(message = "field amount cannot be empty")
    private BigDecimal amount;
}
