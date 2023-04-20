package com.example.donationapp.dto;

import com.example.donationapp.model.District;
import com.example.donationapp.model.Role;

public class DoctorEditDTO {
    public String email;
    public String CNP;
    public String name;
    public String password;
    public String district;
    public Integer bloodbankId;

    public DoctorEditDTO(String email, String CNP, String name, String password, District district, Integer bloodbankId) {
        this.email = email;
        this.CNP = CNP;
        this.name = name;
        this.password = password;
        this.district = district.name();
        this.bloodbankId = bloodbankId;
    }
}
