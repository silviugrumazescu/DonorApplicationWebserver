package com.example.donationapp.service;

import com.example.donationapp.dto.AppointmentDoctorPreviewDTO;
import com.example.donationapp.dto.DoctorEditDTO;
import com.example.donationapp.dto.DoctorPreview;
import com.example.donationapp.model.*;
import com.example.donationapp.repository.BloodBankRepository;
import com.example.donationapp.repository.DoctorRepository;
import com.example.donationapp.utils.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
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

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors;
    }

    public List<Doctor> getDoctorsByDistrict(District district) {
        List<Doctor> doctors = doctorRepository.findDoctorByDistrict(district);

        return doctors;
    }

    public Doctor getDoctor(String email) {
        Doctor d = doctorRepository.findById(email).get();
        return d;
    }

    public void updateDoctor(String email, String CNP, String name, String district, Integer bloodbankId) {
        Doctor d = doctorRepository.findById(email).get();

        d.setCNP(CNP);
        d.setName(name);
        try {
            d.setDistrict(dataParser.parseDistrict(district));
        } catch(DataParser.InvalidInputException ex) {
            System.out.println(ex.getMessage());
        }
        BloodBank selectedBloodbank = bloodBankRepository.findById(bloodbankId).get();
        d.setBloodBank1(selectedBloodbank);
        doctorRepository.save(d);
    }

    public void createDoctor(String name,
                             String email,
                             String password,
                             District district,
                             Integer bloodbankId,
                             String CNP) throws Exception {

        if(doctorRepository.existsById(email)) {
            throw new Exception("Email already in use");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPass = bCryptPasswordEncoder.encode(password);
        BloodBank bloodBank = bloodBankRepository.findById(bloodbankId).get();

        Doctor doctor = new Doctor(name, email, encodedPass, district, Role.Doctor, bloodBank, CNP);

        doctorRepository.save(doctor);
    }

    public void deleteDoctor(String email) throws Exception {
        if(!doctorRepository.existsById(email)) {
            throw new Exception("Doctor doesn't exist");
        }
        doctorRepository.deleteById(email);
    }

}
