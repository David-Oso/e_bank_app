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
    @NotEmpty(message = "field user id cannot be empty")
    @NotBlank(message = "field user id cannot be blank")
    private Long userId;

    @NotNull(message = "field amount cannot be null")
    @NotEmpty(message = "field amount cannot be empty")
    @NotBlank(message = "field amount cannot be blank")
    @Pattern(regexp= E_BankUtils.AMOUNT_REGEX, message = "enter digit")
    private BigDecimal amount;

    @NotNull(message = "field pin cannot be null")
    @NotEmpty(message = "field pin cannot be empty")
    @NotBlank(message = "field pin cannot be blank")
    @Min(200)
    @Max(10000)
    private String pin;
}
