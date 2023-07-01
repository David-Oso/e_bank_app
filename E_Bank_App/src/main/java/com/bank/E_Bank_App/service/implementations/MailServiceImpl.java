package com.bank.E_Bank_App.service.implementations;

import com.bank.E_Bank_App.dto.request.mailRequest.EmailRequest;
import com.bank.E_Bank_App.dto.request.mailRequest.Recipient;
import com.bank.E_Bank_App.service.MailService;
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
    public String sendHtmlMail(String email, String name, String subject, String htmlContent) {
        try{
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
        }catch (Exception exception){
            throw new RuntimeException("Error sending mail");
        }
    }

    @Override
    @Async
    public void sendHtml(String recipientName, String recipientEmail, String subject, String htmlContent) {
        try{
            EmailRequest emailRequest = new EmailRequest();
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
            log.info(":::::::::::::::::::EMAIL SENT SUCCESSFULLY:::::::::::::::::::");
        }catch (Exception exception){
            throw new RuntimeException("Error sending mail");
        }
    }
}
