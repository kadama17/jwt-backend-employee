package com.example.expenses.security;

public class JwtResponse {

    private String jwtToken;

    public JwtResponse(String jwt) {
        jwtToken = jwt;
    }

    public String getJwtToken() {

        return jwtToken;
    }

    public JwtResponse setJwtToken(String jwtToken) {

        this.jwtToken = jwtToken;
        return this;
    }
}