package com.bank.E_Bank_App.service.mail;


import com.bank.E_Bank_App.dto.request.mailRequest.SendEmailRequest;

public interface MailService {
//    void sendHtmlMail(EmailRequest emailRequest);
    String sendHtmlMail(SendEmailRequest sendEmailRequest);
}
