package com.bank.E_Bank_App.dto.request;

import com.bank.E_Bank_App.data.model.Customer;
import com.bank.E_Bank_App.data.model.Gender;
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
    private Long userId;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String dateOfBirth;
    private String newPassword;
}
