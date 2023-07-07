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
public class WithDrawRequest {

    @NotNull(message = "field user id cannot be null")
    @NotEmpty(message = "")
    @NotBlank(message = "")
    private Long userId;

    @NotNull(message = "")
    @NotEmpty(message = "")
    @NotBlank(message = "")
    private BigDecimal amount;

    @NotNull(message = "")
    @NotEmpty(message = "")
    @NotBlank(message = "")
    private String pin;
}
