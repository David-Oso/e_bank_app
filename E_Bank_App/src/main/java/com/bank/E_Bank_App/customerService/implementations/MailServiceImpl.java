package com.bank.E_Bank_App.customerService.implementations;

import com.bank.E_Bank_App.dto.request.mailRequest.EmailNotificationRequest;
import com.bank.E_Bank_App.dto.request.mailRequest.Recipient;
import com.bank.E_Bank_App.customerService.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired WebClient.Builder webclient;
    @Value("${mail.api.key}")
    private String mailApiKey;

    @Value("${sendinblue.mail.url}")
    private String mailUrl;

    @Override
    @Async
    public String sendHtmlMail(EmailNotificationRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", mailApiKey);
        HttpEntity<EmailNotificationRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(mailUrl, requestEntity, String.class);
        log.info("res->{}", response);
        return response.getBody();
    }

    @Override
    @Async
    public String sendHtml(String recipientName, String recipientEmail, String subject, String htmlContent) {
        EmailNotificationRequest emailRequest = new EmailNotificationRequest();
        emailRequest.setSubject(subject);
        Recipient recipient = new Recipient(recipientName, recipientEmail);
        emailRequest.getTo().add(recipient);
        emailRequest.setHtmlContent(htmlContent);
        webclient
        .baseUrl(mailUrl)
        .defaultHeader("api-key", mailApiKey)
          .build()
          .post()
          .bodyValue(emailRequest)
          .retrieve()
          .bodyToMono(String.class)
          .block();
        log.info(":::::::::::::::Email Sent Successfully:::::::::::::::");
        return "Mail sent";
    }
}
