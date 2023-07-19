package com.bank.E_Bank_App.service.mail;

import com.bank.E_Bank_App.dto.request.mailRequest.EmailRequest;
import com.bank.E_Bank_App.exception.E_BankException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    @Async
    public void sendHtmlMail(EmailRequest emailRequest){
        try{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(senderEmail, emailRequest.getSenderName());
        messageHelper.setTo(emailRequest.getRecipientEmail());
        messageHelper.setSubject(emailRequest.getSubject());
        messageHelper.setText(emailRequest.getHtmlContent(), true);
        javaMailSender.send(message);
        log.info(":::::::::::::::::::EMAIL SENT SUCCESSFULLY:::::::::::::::::::");
        }catch (MessagingException | UnsupportedEncodingException exception){
            throw new E_BankException("Error Sending Email");
        }
    }
}