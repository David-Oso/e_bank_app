package com.bank.E_Bank_App.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

public class E_BankUtils {
    public static final String EMAIL_REGEX_STRING="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String PASSWORD_REGEX_STRING = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String NAME_REGEX = "^[a-zA-Z\\s]+$";
    public static final String PHONE_NUMBER_REGEX = "^(\\+?234|0)[789]\\d{9}$";
    public static final String PIN_REGEX = "^\\d{4}$";

    public static final String BANK_NAME = "E_Bank Nigeria";
    public static final String BANK_LOCATION = "No 1, Olowo road, Sabo Lagos, Nigeria.";
    public static final String BANK_EMAIL = "noreply@ebank.net";
    public static final String BANK_PHONE_NUMBER = "+2349012345678";

    public static final String USER_VERIFICATION_BASE_URL = "localhost:9000/api/v1/customer/account/verify";
    private static final String VERIFICATION_MAIL_TEMPLATE_LOCATION = "C:\\Users\\User\\IdeaProjects\\E_Bank_App\\E_Bank_App\\src\\main\\resources\\verificationMail.txt";
    public static String getMailTemplate(){
        try(BufferedReader reader =
                    new BufferedReader(new FileReader(VERIFICATION_MAIL_TEMPLATE_LOCATION))){
            return reader.lines().collect(Collectors.joining());
        }catch (IOException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }


    public static String generateVerificationLink(Long userId){
        return USER_VERIFICATION_BASE_URL+"?id/"+userId+"&token="+generateVerificationToken();
    }

    private static String generateVerificationToken() {
        Key key = Keys.hmacShaKeyFor("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=".getBytes());

        return Jwts.builder()
                .setIssuer(BANK_NAME)
                .signWith(key)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(1800L)))
                .compact();
    }
    public static String generateRandomString(int length){
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}


