package com.bank.E_Bank_App.dto.request.twilio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SmsRequest {
    @JsonProperty("phoneNumber")
    private String recipientPhoneNumber;
    @JsonProperty("message")
    private String message;
}
