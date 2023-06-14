package com.bank.E_Bank_App.service;

import com.bank.E_Bank_App.dto.request.mailRequest.EmailRequest;

public interface MailService {
    void sendHtmlMail(String email, String name, String subject, String htmlContent);
//    void sendHtml(String recipientEmail, String recipientName, String subject, String htmlContent);
}
