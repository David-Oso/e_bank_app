package com.bank.E_Bank_App.service.implementations;

import com.bank.E_Bank_App.dto.request.mailRequest.EmailRequest;
import com.bank.E_Bank_App.dto.request.mailRequest.Recipient;
import com.bank.E_Bank_App.dto.request.mailRequest.SendEmailRequest;
import com.bank.E_Bank_App.dto.request.mailRequest.Sender;
import com.bank.E_Bank_App.service.mail.MailService;
import com.bank.E_Bank_App.utils.E_BankUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MailServiceImplTest {
    @Autowired MailService mailService;
//
//    @Test
//    void sendHtmlMailTest() {
//        String name = "David";
//        String email = "osodavid001@gmail.com";
//        String subject = "Testing whether the send mail is working";
//        String template = E_BankUtils.GET_EMAIL_VERIFICATION_MAIL_TEMPLATE;
//        String htmlContent = String.format(template, name, "123456");
//
//        EmailRequest emailRequest = new EmailRequest();
//        emailRequest.setRecipientEmail(email);
//        emailRequest.setHtmlContent(htmlContent);
//        emailRequest.setSubject(subject);
//        mailService.sendHtmlMail(emailRequest);
//    }

    @Test
    void sendMailTest(){
        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setContent("<h1> This it just testing</h1>");
        sendEmailRequest.setSubject("Testing");
        Recipient recipient = new Recipient("temx", "osodavid001@gmail.com");
        sendEmailRequest.getRecipients().add(recipient);

        String response = mailService.sendHtmlMail(sendEmailRequest);
        assertThat(response).isNotNull();
    }
}