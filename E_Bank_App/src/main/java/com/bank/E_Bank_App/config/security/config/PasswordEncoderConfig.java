package com.bank.E_Bank_App.config.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

//    @Bean
//    @Profile("admin")
//    public PasswordEncoder adminPasswordEncoder(){
//        return new Argon2PasswordEncoder(80, 64, 3, 16,  8);
//    }

    @Bean
//    @Profile("customer")
    public PasswordEncoder customerPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
