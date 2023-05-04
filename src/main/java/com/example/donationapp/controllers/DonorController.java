package com.example.donationapp.controllers;

import com.example.donationapp.dto.AppointmentDTO;
import com.example.donationapp.dto.AppointmentDonorPreviewDTO;
import com.example.donationapp.dto.DonorEditDTO;
import com.example.donationapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DonorController {

    @Autowired
    DonorService donorService;
    @Autowired
    AppointmentService appointmentService;

    @GetMapping("/donor/getDonor")
    public ResponseEntity<?> getDonor(@RequestParam String email) {
        DonorEditDTO dto = donorService.getDonor(email);

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/donor/updateDonor")
    public ResponseEntity<?> updateDonor(@RequestBody DonorEditDTO donorEditDTO) {
        try {
            donorService.updateDonor(donorEditDTO);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok("Update succesfull");
    }

    @PostMapping("/donor/createAppointment")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            donorService.createAppointment(appointmentDTO);

        } catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok("Appointment created successfully");
    }

    @GetMapping("/donor/getAppointments")
    public ResponseEntity<?> getDonorAppointments(@RequestParam String email) {
        List<AppointmentDonorPreviewDTO> appointmentDonorPreviewDTOList = donorService.getDonorAppointments(email);

        return ResponseEntity.ok(appointmentDonorPreviewDTOList);
    }

    @DeleteMapping("/donor/deleteAppointment/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Integer appointmentId) {
        try {
            donorService.cancelAppointment(appointmentId);
        } catch (DonorService.PastDateException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok("Succesfully deleted appointment!");
    }

    @GetMapping("donor/getUnavailableDays/{bloodbankId}")
    public ResponseEntity<?> getUnavailableDays(@PathVariable Integer bloodbankId) {
        List<Date> dates = appointmentService.getUnavailableDaysForBloodbank(bloodbankId);

        return ResponseEntity.ok(dates);
    }

}
