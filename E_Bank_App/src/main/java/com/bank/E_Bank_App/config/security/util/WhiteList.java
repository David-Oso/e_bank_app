package com.bank.E_Bank_App.config.security.util;

import lombok.Getter;

@Getter
public class WhiteList {
    public static  String[] freeAccess(){
        return new String[]{
                "/auth/v1/customer/register",
                "/auth/v1/verify",
                "/auth/v1/customer/login",
                "/admin/v1/login"
        };
    }

    public static String[] swagger() {
        return new String[]{
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "webjars/**",
                "/swagger-ui.html"
        };
    }
}
