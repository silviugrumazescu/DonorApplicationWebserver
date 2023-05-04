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




}
