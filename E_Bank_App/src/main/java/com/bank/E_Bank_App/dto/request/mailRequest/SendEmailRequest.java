package com.bank.E_Bank_App.dto.request.mailRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendEmailRequest {
    @JsonProperty("sender")
    private final Sender sender = new Sender("E_Bank", "noreply@ebank.net");
    @JsonProperty("to")
    private List<Recipient> recipients = new ArrayList<>();
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("htmlContent")
    private String content;
}
