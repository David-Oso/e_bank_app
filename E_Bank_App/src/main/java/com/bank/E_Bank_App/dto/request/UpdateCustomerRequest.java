package com.bank.E_Bank_App.dto.request;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.model.Gender;
import com.bank.E_Bank_App.utils.E_BankUtils;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateCustomerRequest {
    @NotNull(message = "field customer id cannot be null")
    private Long userId;

    @Pattern(regexp = E_BankUtils.NAME_REGEX, message = "enter a valid name")
    private String firstName;

    @Pattern(regexp = E_BankUtils.NAME_REGEX, message = "enter a valid name")
    private String lastName;

    private Gender gender;

    @Pattern(regexp = E_BankUtils.DATE_OF_BIRTH_REGEX, message = "incorrect date of birth format")
    private String dateOfBirth;
}
