package com.bank.E_Bank_App.service.mail;

import com.bank.E_Bank_App.config.mailConfig.MailConfig;
import com.bank.E_Bank_App.dto.request.mailRequest.EmailRequest;
import com.bank.E_Bank_App.dto.request.mailRequest.SendEmailRequest;
import com.bank.E_Bank_App.exception.E_BankException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
//    private final JavaMailSender javaMailSender;
    private final MailConfig mailConfig;
//    @Value("${spring.mail.username}")
//    private String senderEmail;
    @Value("${brevo.mail.url}")
    private String mailUrl;

//    @Override
//    @Async
//    public void sendHtmlMail(EmailRequest emailRequest){
//        try{
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
//        messageHelper.setFrom(senderEmail, emailRequest.getSenderName());
//        messageHelper.setTo(emailRequest.getRecipientEmail());
//        messageHelper.setSubject(emailRequest.getSubject());
//        messageHelper.setText(emailRequest.getHtmlContent(), true);
//        javaMailSender.send(message);
//        log.info(":::::::::::::::::::EMAIL SENT SUCCESSFULLY:::::::::::::::::::");
//        }catch (MessagingException | UnsupportedEncodingException exception){
//            throw new E_BankException("Error Sending Email");
//        }
//    }
    @Async
    @Override
    public String sendHtmlMail(SendEmailRequest sendEmailRequest) {
        try{
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("api-key", mailConfig.getApiKey());
        httpHeaders.set("accept", APPLICATION_JSON_VALUE);
        RequestEntity<SendEmailRequest> entity =
                new RequestEntity<>(sendEmailRequest, httpHeaders, HttpMethod.POST, URI.create(mailUrl));
        ResponseEntity<String> response = restTemplate.postForEntity(mailUrl, entity, String.class);
        log.info(":::::::::::::::::::EMAIL SENT SUCCESSFULLY:::::::::::::::::::");
        return response.getBody();
        }catch (Exception exception){
            throw new RuntimeException("Error sending mail");
        }
    }
}