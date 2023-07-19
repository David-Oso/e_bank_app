package com.bank.E_Bank_App.service.mail;


import com.bank.E_Bank_App.dto.request.mailRequest.EmailRequest;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(EmailRequest emailRequest);
}
