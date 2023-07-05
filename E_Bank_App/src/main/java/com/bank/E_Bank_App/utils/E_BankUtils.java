package com.bank.E_Bank_App.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.stream.Collectors;

public class E_BankUtils {
    public static final String EMAIL_REGEX_STRING = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String PASSWORD_REGEX_STRING = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String NAME_REGEX = "^[A-Z][a-zA-Z]{0,39}$";
    public static final String PHONE_NUMBER_REGEX = "^(\\+?234|0)[789]\\d{9}$";
    public static final String PIN_REGEX = "^\\d{4}$";
    public static final String DATE_OF_BIRTH_REGEX = "dd/MM/yyy";
    public static final String BANK_NAME = "E_Bank Nigeria";
    public static final String BANK_LOCATION = "No 1, Olowo road, Sabo Lagos, Nigeria.";
    public static final String BANK_EMAIL = "noreply@ebank.net";
    public static final String BANK_PHONE_NUMBER = "+2349030400837";
    public static final String USER_VERIFICATION_BASE_URL = "localhost:8000/api/v1/customer/verify";

    private static final String EMAIL_VERIFICATION_TEMPLATE_LOCATION = "C:\\Users\\User\\IdeaProjects\\E_Bank_App\\E_Bank_App\\src\\main\\resources\\templates\\emailVerification.txt";
    private static final String RESET_PASSWORD_TEMPLATE_LOCATION = "C:\\Users\\User\\IdeaProjects\\E_Bank_App\\E_Bank_App\\src\\main\\resources\\templates\\ResetPasswordMail.html";
    private static final String DEPOSIT_NOTIFICATION_MAIL_TEMPLATE_LOCATION = "C:\\Users\\User\\IdeaProjects\\E_Bank_App\\E_Bank_App\\src\\main\\resources\\templates\\depositNotificationMail.html";
    public static String getTemplate(String templateLocation){
        try(BufferedReader reader =
                    new BufferedReader(new FileReader(templateLocation))){
            return reader.lines().collect(Collectors.joining());
        }catch (IOException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }
    public static String GET_EMAIL_VERIFICATION_MAIL_TEMPLATE = getTemplate(EMAIL_VERIFICATION_TEMPLATE_LOCATION);
    public static String GET_DEPOSIT_NOTIFICATION_MAIL_TEMPLATE = getTemplate(DEPOSIT_NOTIFICATION_MAIL_TEMPLATE_LOCATION);
    public static String GET_RESET_PASSWORD_MAIL_TEMPLATE = getTemplate(RESET_PASSWORD_TEMPLATE_LOCATION);

//    public static String generateVerificationLink(String email) {
//        return USER_VERIFICATION_BASE_URL + "?id/0*&token=" + generateVerificationToken(email);
//    }

//    private static Key getSignInKey() {
//        return Keys.hmacShaKeyFor("Yn2kjibddFAWtnPJ2AFlL8WXmohJMC1RT4657YHGTHDUIjuehdy8764JJUYIU76YGHHH3KN4vigQggaEypa5E=".getBytes());
//    }

//    private static String generateVerificationToken(String email) {
//        return Jwts.builder()
//                .setIssuer(BANK_NAME)
//                .setSubject(email)
//                .signWith(getSignInKey())
//                .setIssuedAt(new Date())
//                .setExpiration(Date.from(Instant.now().plusSeconds(1800L)))
//                .compact();
//    }


    public static String generateRandomString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}