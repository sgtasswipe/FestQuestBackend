package com.example.festquestbackend.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class NonceControllerAdvice {
    
    @ModelAttribute("cspNonce")
    public String getCspNonce(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("cspNonce");
    }
}
