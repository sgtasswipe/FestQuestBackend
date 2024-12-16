package com.example.festquestbackend.util;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class NonceGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();

    public String generateNonce() {
        byte[] nonceBytes = new byte[32];
        secureRandom.nextBytes(nonceBytes);
        return Base64.getEncoder().encodeToString(nonceBytes);
    }
}
