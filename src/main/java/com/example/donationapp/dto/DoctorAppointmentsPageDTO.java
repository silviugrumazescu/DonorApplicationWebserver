package com.example.donationapp.dto;

import java.util.List;

public class DoctorAppointmentsPageDTO {
    public List<AppointmentDoctorPreviewDTO> appointments;
    public Integer numberOfRecords;

    public DoctorAppointmentsPageDTO(List<AppointmentDoctorPreviewDTO> appointments, Integer numberOfRecords) {
        this.appointments = appointments;
        this.numberOfRecords = numberOfRecords;
    }
}
