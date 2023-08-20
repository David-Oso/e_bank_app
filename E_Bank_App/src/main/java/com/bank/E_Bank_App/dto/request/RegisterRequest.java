package com.bank.E_Bank_App.dto.request;

import com.bank.E_Bank_App.data.model.Gender;
import jakarta.validation.constraints.*;
import lombok.*;

import static com.bank.E_Bank_App.utils.E_BankUtils.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    @NotEmpty(message = "field first name cannot be empty")
    @NotBlank(message = "field first name cannot be blank")
    @Pattern(regexp = NAME_REGEX, message = "first name must start " +
            "with capital letter and it cant be only letters")
    private String firstName;

    @NotEmpty(message = "field last name cannot be empty")
    @NotBlank(message = "field last name cannot be blank")
    @Pattern(regexp = NAME_REGEX, message = "last name must " +
            "start with capital letter and it must be only letters")
    private String lastName;

    @NotEmpty(message = "field phone number cannot be empty")
    @NotBlank(message = "field phone number cannot be blank")
    @Pattern(regexp = PHONE_NUMBER_REGEX, message = "phone number must start with +234")
    private String phoneNumber;

    @NotEmpty(message = "field email cannot be empty")
    @NotBlank(message = "field email cannot be blank")
    @Email(regexp = EMAIL_REGEX_STRING, message = "Invalid email")
    private String email;

    @NotEmpty(message = "field password cannot be empty")
    @NotBlank(message = "field password cannot be blank")
    @Pattern(regexp = PASSWORD_REGEX_STRING, message = "Password must " +
            "contain at least one capital letter, one small letter, a number and special character(@$!%*?&)")
    private String password;

    @NotNull(message = "field gender cannot be null")
    private Gender gender;

    @NotBlank(message = "field date of birth cannot be blank")
    @NotEmpty(message = "field date of birth cannot be empty")
//    @Pattern(regexp = DATE_OF_BIRTH_REGEX, message = "Date should be in the format dd/MM/yyyy")
    private String dateOfBirth;
}
