package com.example.donationapp.controllers;

import com.example.donationapp.dto.BloodbankPreview;
import com.example.donationapp.dto.DoctorEditDTO;
import com.example.donationapp.dto.DoctorPreview;
import com.example.donationapp.facade.BloodbankFacade;
import com.example.donationapp.facade.DoctorFacade;
import com.example.donationapp.model.*;
import com.example.donationapp.repository.BloodBankRepository;
import com.example.donationapp.repository.DoctorRepository;
import com.example.donationapp.service.AdminService;
import com.example.donationapp.service.BloodbankService;
import com.example.donationapp.service.DoctorService;
import com.example.donationapp.utils.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    DoctorService doctorService;
    @Autowired
    DoctorFacade doctorFacade;
    @Autowired
    BloodbankFacade bloodbankFacade;
    @Autowired
    AdminService adminService;
    @Autowired
    BloodBankRepository bloodBankRepository;
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    DataParser dataParser;

    @GetMapping("/admin/getDoctorsPreview")
    public ResponseEntity<?> getDoctors() {
        return ResponseEntity.ok(doctorFacade.getAllDoctors());
    }

    @GetMapping("/admin/getDoctorsByDistrict")
    public ResponseEntity<?> getDoctorsByDistrict(@RequestParam String district) {
        District d;
        try {
            d = dataParser.parseDistrict(district);
        } catch (DataParser.InvalidInputException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        List<DoctorPreview> doctorPreviews = doctorFacade.getDoctorsByDistrict(d);

        return ResponseEntity.ok().body(doctorPreviews);
    }

    @GetMapping("/admin/getBloodbanksByDistrict")
    public ResponseEntity<?> getBloodBanksByDistrict(@RequestParam String district) {
        District d;
        try {
            d = dataParser.parseDistrict(district);
        } catch (DataParser.InvalidInputException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        List<BloodbankPreview> bloodbankPreviews = bloodbankFacade.getBloodbanksByDistrict(d);
        return ResponseEntity.ok(bloodbankPreviews);
    }

    @GetMapping("/admin/getDoctor")
    public ResponseEntity<?> getDoctor(@RequestParam String email) {
        DoctorEditDTO u = doctorFacade.getDoctor(email);
        if(u != null) {
            return ResponseEntity.ok(u);
        } else {
            return ResponseEntity.badRequest().body("Invalid email");
        }
    }

    @GetMapping("/admin/getDistricts")
    public ResponseEntity<?> getDistricts() {
        ArrayList<String> districts = new ArrayList<>();
        Arrays.stream(District.values()).forEach(district -> districts.add(district.name()));
        return ResponseEntity.ok(districts);
    }

    @PostMapping("/admin/updateDoctorAccount")
    public ResponseEntity<?> updateDoctorAccount(@RequestBody DoctorEditDTO doctorEditDTO) {
        doctorFacade.updateDoctor(doctorEditDTO);
        return ResponseEntity.ok("Succesfully updated admin");
    }

    @PostMapping("/admin/createDoctorAccount")
    public ResponseEntity<?> createDoctorAccount(@RequestBody DoctorEditDTO doctorEditDTO) {
        try {
            doctorFacade.createDoctor(doctorEditDTO);
        } catch (Exception ex) {
            ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok("User succesfully created");
    }

    @DeleteMapping("/admin/deleteDoctorAccount/{email}")
    public ResponseEntity<?> deleteDoctorAccount(@PathVariable String email) {
        try {
            doctorFacade.deleteDoctor(email);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok("Doctor succesfully deleted!");
    }

    @PostMapping("/admin/insert")
    public ResponseEntity<?> insertDoctors() {
        BloodBank b1 = new BloodBank("Spitalul 1", District.SUCEAVA, true, "str 1");
        BloodBank b2 = new BloodBank("Spitalul 2", District.SUCEAVA, true, "str 2");
        BloodBank b3 = new BloodBank("Spitalul 3", District.SUCEAVA, true, "str 3");

        Doctor d1 = new Doctor("DocAurel", "docaurel@gmail.com", "aurel", District.SUCEAVA, Role.Doctor, b1, "412341234132");
        Doctor d2 = new Doctor("DocCostel", "doccostel@gmail.com", "aurel", District.SUCEAVA, Role.Doctor, b1, "412341234132");
        Doctor d3 = new Doctor("DocMircea", "docmircea@gmail.com", "aurel", District.SUCEAVA, Role.Doctor, b2, "412341234132");
        Doctor d4 = new Doctor("DocVasile", "docvasile@gmail.com", "aurel", District.SUCEAVA, Role.Doctor, b2, "412341234132");
        Doctor d5 = new Doctor("DocViorel", "docviorel@gmail.com", "aurel", District.SUCEAVA, Role.Doctor, b3, "412341234132");

        bloodBankRepository.save(b1);
        bloodBankRepository.save(b2);
        bloodBankRepository.save(b3);

        doctorRepository.save(d1);
        doctorRepository.save(d2);
        doctorRepository.save(d3);
        doctorRepository.save(d4);
        doctorRepository.save(d5);

        return ResponseEntity.ok("Success");
    }

}