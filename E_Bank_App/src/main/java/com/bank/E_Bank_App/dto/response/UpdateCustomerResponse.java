package com.bank.E_Bank_App.dto.response;

import com.bank.E_Bank_App.data.model.Gender;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateCustomerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int age;
    private Gender gender;
}
