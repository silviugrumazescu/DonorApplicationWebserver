package com.example.donationapp.facade;

import com.example.donationapp.dto.DoctorEditDTO;
import com.example.donationapp.dto.DoctorPreview;
import com.example.donationapp.mappers.DoctorMapper;
import com.example.donationapp.model.BloodBank;
import com.example.donationapp.model.District;
import com.example.donationapp.model.Doctor;
import com.example.donationapp.model.Role;
import com.example.donationapp.service.DoctorService;
import com.example.donationapp.utils.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorFacade {

    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    DoctorService doctorService;
    @Autowired
    DataParser dataParser;

    public List<DoctorPreview> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return doctorMapper.mapDoctorsForAdmin(doctors);
    }

    public List<DoctorPreview> getDoctorsByDistrict(District district) {
        List<Doctor> doctors = doctorService.getDoctorsByDistrict(district);

        return doctorMapper.mapDoctorsToPreview(doctors);
    }

    public DoctorEditDTO getDoctor(String email) {
        Doctor d = doctorService.getDoctor(email);
        return new DoctorEditDTO(d.getEmail(), d.getCNP(), d.getName(), d.getPassword(), d.getDistrict(), d.getBloodBank1().getId());
    }

    public void updateDoctor(DoctorEditDTO doctorEditDTO) {
        doctorService.updateDoctor(
                doctorEditDTO.email,
                doctorEditDTO.CNP,
                doctorEditDTO.name,
                doctorEditDTO.district,
                doctorEditDTO.bloodbankId
        );
    }

    public void createDoctor(DoctorEditDTO doctorEditDTO) throws Exception {
        District district = dataParser.parseDistrict(doctorEditDTO.district);

        doctorService.createDoctor(doctorEditDTO.name, doctorEditDTO.email, doctorEditDTO.password, district, doctorEditDTO.bloodbankId, doctorEditDTO.CNP);
    }

    public void deleteDoctor(String email) throws Exception {
        doctorService.deleteDoctor(email);
    }



}
