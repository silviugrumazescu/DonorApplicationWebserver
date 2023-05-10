package com.example.donationapp.controllers;

import com.example.donationapp.dto.AppointmentDTO;
import com.example.donationapp.dto.AppointmentDonorPreviewDTO;
import com.example.donationapp.dto.DonorEditDTO;
import com.example.donationapp.facade.AppointmentFacade;
import com.example.donationapp.facade.DonorFacade;
import com.example.donationapp.service.*;
import org.apache.coyote.Response;
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
    DonorFacade donorFacade;
    @Autowired
    AppointmentFacade appointmentFacade;
    @Autowired
    AppointmentService appointmentService;

    @GetMapping("/donor/getDonor")
    public ResponseEntity<?> getDonor(@RequestParam String email) {
        DonorEditDTO dto = donorFacade.getDonor(email);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/donor/updateDonor")
    public ResponseEntity<?> updateDonor(@RequestBody DonorEditDTO donorEditDTO) {
        try {
            donorFacade.updateDonor(donorEditDTO);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok("Update succesfull");
    }

    @PostMapping("/donor/createAppointment")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            appointmentFacade.createAppointment(appointmentDTO);

        } catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok("Appointment created successfully");
    }

    @GetMapping("/donor/getAppointments")
    public ResponseEntity<?> getDonorAppointments(@RequestParam String email) {
        List<AppointmentDonorPreviewDTO> appointmentDonorPreviewDTOList =
                appointmentFacade.getAppointmentsByDonor(email);

        return ResponseEntity.ok(appointmentDonorPreviewDTOList);
    }

    @DeleteMapping("/donor/deleteAppointment/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Integer appointmentId) {
        try {
            appointmentFacade.cancelAppointment(appointmentId);
            return ResponseEntity.ok("Succesfully deleted appointment!");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("donor/getUnavailableDays/{bloodbankId}")
    public ResponseEntity<?> getUnavailableDays(@PathVariable Integer bloodbankId) {
        List<Date> dates = appointmentFacade.getUnavailableDaysForBloodbank(bloodbankId);

        return ResponseEntity.ok(dates);
    }

}
