package com.example.donationapp.dto;

public class AppointmentDTO {

    public String donorEmail;
    public Integer bloodbankId;
    public String date;

    public AppointmentDTO(String donorEmail, Integer bloodbanKId, String date) {
        this.donorEmail = donorEmail;
        this.bloodbankId = bloodbanKId;
        this.date = date;
    }
}
