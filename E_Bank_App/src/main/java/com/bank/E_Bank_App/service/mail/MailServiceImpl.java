package com.bank.E_Bank_App.service.mail;

import com.bank.E_Bank_App.dto.request.mailRequest.EmailRequest;
import com.bank.E_Bank_App.dto.request.mailRequest.Recipient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Value("${mail.api.key}")
    private String mailApiKey;

    @Value("${sendinblue.mail.url}")
    private String mailUrl;

    @Override
    @Async
    public String sendHtmlMail(String name, String email, String subject, String htmlContent) {
        EmailRequest request = new EmailRequest();
        Recipient recipient = new Recipient(name, email);
        request.getTo().add(recipient);
        request.setSubject(subject);
        request.setHtmlContent(htmlContent);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", mailApiKey);
        HttpEntity<EmailRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(mailUrl, requestEntity, String.class);
        log.info(":::::::::::::::::::EMAIL SENT SUCCESSFULLY:::::::::::::::::::");
        return response.getBody();
    }
}
