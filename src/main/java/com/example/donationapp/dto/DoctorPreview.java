package com.example.donationapp.dto;

import com.example.donationapp.repository.DoctorRepository;

public class DoctorPreview {

    private String email;
    private String name;
    private String district;

    public DoctorPreview(String email, String name, String district) {
        this.email = email;
        this.name = name;
        this.district = district;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }



}
