package br.com.devdojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoder1 {

    @Autowired
    private static PasswordEncoder passwordEncoder;

    public static void main(String[] args){

        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        System.out.println(passwordEncoder.encode("pcella"));
    }

}
