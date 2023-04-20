package com.example.donationapp.service;

import com.example.donationapp.dto.BloodbankPreview;
import com.example.donationapp.dto.DoctorEditDTO;
import com.example.donationapp.dto.DoctorPreview;
import com.example.donationapp.model.*;
import com.example.donationapp.repository.BloodBankRepository;
import com.example.donationapp.repository.DoctorRepository;
import com.example.donationapp.repository.UserRepository;
import com.example.donationapp.utils.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BloodBankRepository bloodBankRepository;
    @Autowired
    DataParser dataParser;

    public DoctorEditDTO getDoctor(String email) {
        Doctor d = doctorRepository.findById(email).get();
        return new DoctorEditDTO(d.getEmail(), d.getCNP(), d.getName(), d.getPassword(), d.getDistrict(), d.getBloodBank1().getId());
    }

    public ArrayList<DoctorPreview> getDoctorsByDistrict(District district) {
        List<Doctor> doctors = doctorRepository.findDoctorByDistrict(district);

        ArrayList<DoctorPreview> doctorPreviews = new ArrayList<DoctorPreview>();
        doctors.forEach(doctor -> {doctorPreviews.add(new DoctorPreview(
                doctor.getEmail(),
                doctor.getName(),
                doctor.getDistrict().name()
        ));});
        return doctorPreviews;
    }

    public ArrayList<BloodbankPreview> getBloodbanksByDistrict(District district) {
        List<BloodBank> bloodBanks = bloodBankRepository.findBloodBankByDistrict(district);

        ArrayList<BloodbankPreview> bloodbankPreviews = new ArrayList<>();
        bloodBanks.forEach(b -> {bloodbankPreviews.add(new BloodbankPreview(
                b.getId(),
                b.getName(),
                b.getDistrict()
        ));});

        return bloodbankPreviews;
    }

    public void updateDoctor(DoctorEditDTO doctorEditDTO) {
        Doctor d = doctorRepository.findById(doctorEditDTO.email).get();

        d.setCNP(doctorEditDTO.CNP);
        d.setName(doctorEditDTO.name);
        try {
            d.setDistrict(dataParser.parseDistrict(doctorEditDTO.district));
        } catch(DataParser.InvalidInputException ex) {
            System.out.println(ex.getMessage());
        }
        BloodBank selectedBloodbank = bloodBankRepository.findById(doctorEditDTO.bloodbankId).get();

        d.setBloodBank1(selectedBloodbank);

        doctorRepository.save(d);
    }

    public void createDoctor(DoctorEditDTO doctorEditDTO) throws Exception {

        if(doctorRepository.existsById(doctorEditDTO.email)) {
            throw new Exception("Email already in use");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String encodedPass = bCryptPasswordEncoder.encode(doctorEditDTO.password);
        District district = dataParser.parseDistrict(doctorEditDTO.district);

        BloodBank selectedBloodbank = bloodBankRepository.findById(doctorEditDTO.bloodbankId).get();

        Doctor d = new Doctor(
                doctorEditDTO.name,
                doctorEditDTO.email,
                encodedPass, district,
                Role.Doctor,
                selectedBloodbank,
                doctorEditDTO.CNP);

        doctorRepository.save(d);
    }

    public void deleteDoctor(String email) throws Exception {

        if(!doctorRepository.existsById(email)) {
            throw new Exception("Doctor doesn't exist");
        }

        doctorRepository.deleteById(email);
    }

}
