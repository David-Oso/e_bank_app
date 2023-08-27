package com.bank.E_Bank_App.config.appConfig;

import com.bank.E_Bank_App.config.adminConfig.AdminConfig;
import com.bank.E_Bank_App.config.mailConfig.MailConfig;
import com.bank.E_Bank_App.dto.request.mailRequest.EmailRequest;
import com.bank.E_Bank_App.dto.request.mailRequest.SendEmailRequest;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AppConfig {
    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    @Value("${cloudinary.api.key}")
    private String apiKey;
    @Value("${cloudinary.api.secret}")
    private String apiSecret;

    @Value("${adminId}")
    private String adminId;

    @Value("${adminFirstName}")
    private String adminFirstName;

    @Value("${adminLastName}")
    private String adminLastName;

    @Value("${adminPhoneNumber}")
    private String adminPhoneNumber;

    @Value("${adminEmail}")
    private String adminEmail;

    @Value("${adminPassword}")
    private String adminPassword;

    @Value("${mail.api.key}")
    private String mailApiKey;


    @Bean
    public AdminConfig adminConfig(){
        return new AdminConfig(adminId, adminFirstName, adminLastName, adminPhoneNumber, adminEmail, adminPassword);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name",cloudName,
                        "api_key",apiKey,
                        "api_secret",apiSecret
                )
        );
    }
    @Bean
//    public EmailRequest emailNotificationRequest(){
//        return new EmailRequest();
//    }
    public SendEmailRequest sendEmailRequest(){
        return new SendEmailRequest();
    }

    @Bean
    public MailConfig mailConfig(){
        return new MailConfig(mailApiKey);
    }
}
