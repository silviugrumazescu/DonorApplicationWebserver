package com.example.donationapp.dto;

public class LoginResponse {

    public String email;
    public String name;
    public String accessToken;
    public String district;
    public String CNP;
    public String role;

    public LoginResponse(String email, String name, String token, String district, String CNP, String role) {
        this.email = email;
        this.name = name;
        this.accessToken = token;
        this.district = district;
        this.CNP = CNP;
        this.role = role;
    }

}
