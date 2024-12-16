package com.example.festquestbackend.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.festquestbackend.util.NonceGenerator;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CSPFilter implements Filter {

    @Autowired
    private NonceGenerator nonceGenerator;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if(response instanceof HttpServletResponse httpResp) {
            String nonce = nonceGenerator.generateNonce();
            ((HttpServletRequest) request).getSession().setAttribute("cspNonce", nonce);

            httpResp.setHeader("Content-Security-Policy", 
                "default-src 'self'; " +
                "connect-src 'self' http://localhost:8080 https://api.unsplash.com; " +
                "img-src 'self' data: https://*.unsplash.com http://127.0.0.1:* http://localhost:*; " +
                "style-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net; " +
                "style-src-elem 'self' 'unsafe-inline' https://cdn.jsdelivr.net; " +
                "script-src 'self' 'unsafe-inline' 'nonce-" + nonce + "' 'unsafe-eval'; " +
                "script-src-elem 'self' 'unsafe-inline' 'nonce-" + nonce + "'; " +
                "font-src 'self' https://cdn.jsdelivr.net; " +
                "frame-src 'self';");
        }
        chain.doFilter(request, response);
    }
}