package com.bank.E_Bank_App.config.appConfig;

import com.bank.E_Bank_App.dto.request.mailRequest.EmailNotificationRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public EmailNotificationRequest emailNotificationRequest(){
        return new EmailNotificationRequest();
    }


}
