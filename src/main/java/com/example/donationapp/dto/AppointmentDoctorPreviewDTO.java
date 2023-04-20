package com.example.donationapp.dto;

import java.util.Date;

public class AppointmentDoctorPreviewDTO {

    public Integer appointmentId;
    public String donorName;
    public Date date;
    public String formatedDate;
    public Boolean isConfirmed;

    public AppointmentDoctorPreviewDTO(Integer appointmentId, String donorName, Date date, String formatedDate, Boolean isConfirmed) {
        this.appointmentId = appointmentId;
        this.donorName = donorName;
        this.date = date;
        this.formatedDate = formatedDate;
        this.isConfirmed = isConfirmed;
    }

}
