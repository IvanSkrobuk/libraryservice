package com.example.books.provider;

import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    public void setToken(String token) {
        tokenHolder.set(token);
    }

    public String getToken() {
        return tokenHolder.get();
    }

    public void clear() {
        tokenHolder.remove();
    }
}
