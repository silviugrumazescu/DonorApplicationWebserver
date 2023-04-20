package com.example.donationapp.dto;

public class DonorEditDTO {

    public String email;
    public String CNP;
    public String name;
    public String password;
    public String district;
    public String bloodType;

    public DonorEditDTO(String email, String CNP, String name, String password, String district, String bloodType) {
        this.email = email;
        this.CNP = CNP;
        this.name = name;
        this.password = password;
        this.district = district;
        this.bloodType = bloodType;
    }
}
