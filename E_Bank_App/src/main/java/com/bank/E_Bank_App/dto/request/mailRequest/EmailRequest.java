package com.bank.E_Bank_App.dto.request.mailRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.bank.E_Bank_App.utils.E_BankUtils.BANK_EMAIL;
import static com.bank.E_Bank_App.utils.E_BankUtils.BANK_NAME;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    private final Sender sender = new Sender(BANK_NAME, BANK_EMAIL);
    private List<Recipient> to = new ArrayList<>();
    private String subject;
    private String htmlContent;
}
