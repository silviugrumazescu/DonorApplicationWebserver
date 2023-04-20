package com.example.donationapp.service;

import com.example.donationapp.dto.AppointmentDoctorPreviewDTO;
import com.example.donationapp.dto.DoctorPreview;
import com.example.donationapp.model.*;
import com.example.donationapp.repository.BloodBankRepository;
import com.example.donationapp.repository.DoctorRepository;
import com.example.donationapp.utils.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    BloodBankRepository bloodBankRepository;
    @Autowired
    DataParser dataParser;

    public DoctorService() {
    }

    public ArrayList<DoctorPreview> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        ArrayList<DoctorPreview> doctorPreviews = new ArrayList<>();
        doctors.forEach( (d) -> {
            doctorPreviews.add(new DoctorPreview(
                    d.getEmail(),
                    d.getName(),
                    d.getDistrict().name()));
        });

        return doctorPreviews;
    }

    public List<AppointmentDoctorPreviewDTO> getDoctorAppointments(String email) {
        Doctor doctor = doctorRepository.findById(email).get();

        List<Appointment> appointments = doctor.getAppointments();
        List<AppointmentDoctorPreviewDTO> appointmentDoctorPreviewDTOS = new ArrayList<>();

        appointments.forEach(app -> {
            appointmentDoctorPreviewDTOS.add(new AppointmentDoctorPreviewDTO(
                    app.getId(),
                    app.getDonor().getName(),
                    app.getDate(),
                    dataParser.formatDate(app.getDate()),
                    app.getConfirmed()
            ));
        });

        return appointmentDoctorPreviewDTOS;
    }


}
