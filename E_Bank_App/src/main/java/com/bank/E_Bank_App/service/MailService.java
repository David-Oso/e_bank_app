package com.bank.E_Bank_App.service;

import com.bank.E_Bank_App.dto.request.mailRequest.EmailRequest;

public interface MailService {
    String sendHtmlMail(String name, String email, String subject, String htmlContent);
}
