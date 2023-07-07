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
    @NotNull(message = "field user id cannot be null")
    @NotEmpty(message = "field user id cannot be empty")
    @NotBlank(message = "field user id cannot be blank")
    private Long userId;

    @NotNull(message = "field amount cannot be null")
    @NotBlank(message = "field amount cannot be blank")
    @NotEmpty(message = "field amount cannot be empty")
    private BigDecimal amount;
}
