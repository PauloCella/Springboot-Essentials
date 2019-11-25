package br.com.devdojo.config;

import java.util.concurrent.TimeUnit;

public class SecurityConstants {
    //Authorization Bearer  asjanks8asda41asfkvwebwekbkjasjdka
    static final String SECRET = "DevDojoFoda";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/users/sign-up";
    static final long EXPIRATION_TIME = 86400000000L;
}
