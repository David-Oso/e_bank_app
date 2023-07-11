package com.bank.E_Bank_App.service.mail;


public interface MailService {
    String sendHtmlMail(String name, String email, String subject, String htmlContent);
}
