package com.example.donationapp.dto;

public class AppointmentDTO {

    public String donorEmail;
    public Integer bloodbankId;
    public String date;
    public String notifyType;

    public AppointmentDTO(String donorEmail, Integer bloodbanKId, String date, String notifyType) {
        this.donorEmail = donorEmail;
        this.bloodbankId = bloodbanKId;
        this.date = date;
        this.notifyType = notifyType;
    }
}
