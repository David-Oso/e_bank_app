package com.bank.E_Bank_App.customerService;

import com.bank.E_Bank_App.dto.request.mailRequest.EmailNotificationRequest;

public interface MailService {
    String sendHtmlMail(EmailNotificationRequest request);
    String sendHtml(String recipientEmail, String recipientName, String subject, String htmlContent);
}
