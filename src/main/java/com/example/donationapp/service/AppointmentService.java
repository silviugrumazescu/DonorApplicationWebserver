package com.example.donationapp.service;

import com.example.donationapp.dto.AppointmentDoctorPreviewDTO;
import com.example.donationapp.model.Appointment;
import com.example.donationapp.repository.AppointmentRepository;
import com.example.donationapp.utils.DataParser;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    DataParser dataParser;

    public List<AppointmentDoctorPreviewDTO> getAppointmentsByDoctorAndDate(String email, Date date) {
        List<Appointment> appointments = appointmentRepository.findAll().stream().filter(appointment -> {
            return DateUtils.isSameDay(appointment.getDate(), date) &&
                    appointment.getDoctor().getEmail().equals(email);
        }).collect(Collectors.toList());

        List<AppointmentDoctorPreviewDTO> appointmentDoctorPreviewDTOS = new ArrayList<>();

        appointments.forEach(appointment -> {
            appointmentDoctorPreviewDTOS.add(new AppointmentDoctorPreviewDTO(
                    appointment.getId(),
                    appointment.getDonor().getName(),
                    appointment.getDate(),
                    dataParser.formatDate(appointment.getDate()),
                    appointment.getConfirmed()
            ));
        });

        return appointmentDoctorPreviewDTOS;
    }

    public void confirmAppointment(Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).get();
        appointment.setConfirmed(true);
        appointmentRepository.save(appointment);
    }




}
