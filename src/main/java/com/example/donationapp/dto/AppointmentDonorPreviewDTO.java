package com.example.donationapp.dto;

public class AppointmentDonorPreviewDTO {
    public Integer appointmentId;
    public String doctorName;
    public String locationName;
    public String address;
    public String date;
    public Boolean isConfirmed;

    public AppointmentDonorPreviewDTO(Integer appointmentId, String doctorName, String locationName, String address, String date, Boolean isConfirmed) {
        this.appointmentId = appointmentId;
        this.doctorName = doctorName;
        this.locationName = locationName;
        this.address = address;
        this.date = date;
        this.isConfirmed = isConfirmed;
    }
}
