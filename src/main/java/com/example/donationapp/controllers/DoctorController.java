package com.example.donationapp.controllers;

import com.example.donationapp.dto.AppointmentDoctorPreviewDTO;
import com.example.donationapp.service.AppointmentService;
import com.example.donationapp.service.DoctorService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DoctorController {

    @Autowired
    DoctorService doctorService;
    @Autowired
    AppointmentService appointmentService;

    @GetMapping("/doctor/getAppointments")
    public ResponseEntity<?> getDoctorAppointments(@RequestParam String email) {
        List<AppointmentDoctorPreviewDTO> appointmentDoctorPreviewDTOS = doctorService.getDoctorAppointments(email);
        return ResponseEntity.ok(appointmentDoctorPreviewDTOS);
    }

    @GetMapping("doctor/getAppointmentsByDate")
    public ResponseEntity<?> getDoctorAppointmentsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @RequestParam String email) {

        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctorAndDate(email, date));
    }

    @PostMapping("doctor/confirmAppointment")
    public ResponseEntity<?> confirmAppointment(@RequestParam Integer appointmentId) {
        appointmentService.confirmAppointment(appointmentId);
        return ResponseEntity.ok("Appointment confirmed");
    }
}
