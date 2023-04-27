package com.example.donationapp.service;

import com.example.donationapp.dto.AppointmentDoctorPreviewDTO;
import com.example.donationapp.dto.DoctorAppointmentsPageDTO;
import com.example.donationapp.mappers.AppointmentMapper;
import com.example.donationapp.model.Appointment;
import com.example.donationapp.model.BloodBank;
import com.example.donationapp.model.Doctor;
import com.example.donationapp.repository.AppointmentRepository;
import com.example.donationapp.repository.BloodBankRepository;
import com.example.donationapp.repository.DoctorRepository;
import com.example.donationapp.utils.DataParser;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class AppointmentService {

    @Autowired
    AppointmentMapper appointmentMapper;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    BloodBankRepository bloodBankRepository;
    @Autowired
    DataParser dataParser;

    public List<AppointmentDoctorPreviewDTO> getAppointmentsByDoctorAndDate(String email, Date date) {
        List<Appointment> appointments = appointmentRepository.findAll().stream().filter(appointment -> {
            return DateUtils.isSameDay(appointment.getDate(), date) &&
                    appointment.getDoctor().getEmail().equals(email);
        }).collect(Collectors.toList());

        return appointmentMapper.mapAppointmentsForDoctor(appointments);
    }

    public void confirmAppointment(Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).get();
        appointment.setConfirmed(true);
        appointmentRepository.save(appointment);
    }

    public List<AppointmentDoctorPreviewDTO> getDoctorAppointments(String email) {
        Doctor doctor = doctorRepository.findById(email).get();
        List<Appointment> appointments = doctor.getAppointments();
        return appointmentMapper.mapAppointmentsForDoctor(appointments);
    }

    public DoctorAppointmentsPageDTO getDoctorAppointments(String email, Integer page, Integer numberOfEntries) {
        Pageable pageable = (Pageable) PageRequest.of(page, numberOfEntries);
        Page<Appointment> appointments = appointmentRepository.findAllByDoctorEmail(email, pageable);

        return appointmentMapper.mapAppointmentsForDoctorPage(appointments);
    }

    public ArrayList<Date> getUnavailableDaysForBloodbank(Integer bloodbankId) {
        BloodBank bloodBank = bloodBankRepository.findById(bloodbankId).get();
        List<Appointment> appointments = bloodBank.getAppointments();

        Map<Date, Integer> appointmentsCount = new HashMap<>();

        appointments.forEach(ap -> {
            if(appointmentsCount.containsKey(ap.getDate())) {
                System.out.println("Incremented date " + ap.getDate() + "count: " + (appointmentsCount.get(ap.getDate()) + 1));
                appointmentsCount.replace(ap.getDate(), appointmentsCount.get(ap.getDate()) + 1);
            } else {
                System.out.println("Created new entry for " + ap.getDate());
                appointmentsCount.put(ap.getDate(), 1);
            }
        });
        Integer maxAppointments = bloodBank.getMaxAppointments();
        ArrayList<Date> disabledDates = new ArrayList<>();

        for(Map.Entry<Date,Integer> entry : appointmentsCount.entrySet()) {
            if(entry.getValue() == maxAppointments) {
                disabledDates.add(entry.getKey());
            }
        }
        return disabledDates;
    }

}
